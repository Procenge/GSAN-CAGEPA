<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="displaytag" %>

<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.atendimentopublico.ordemservico.bean.ManterDadosAtividadesOrdemServicoHelper"%>
<%@ page import="gcom.atendimentopublico.ordemservico.bean.OSAtividadePeriodoExecucaoHelper"%>
<%@ page import="gcom.atendimentopublico.ordemservico.bean.OSExecucaoEquipeHelper"%>
<%@ page import="gcom.atendimentopublico.ordemservico.OrdemServicoInterrupcaoExecucao"%>
<%@ page import="gcom.atendimentopublico.ordemservico.OsAtividadeMaterialExecucao"%>
<%@ page import="gcom.gui.GcomAction"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<script language="JavaScript">

	function limparAtividade(){

		var form = document.forms[0];
		
		form.idAtividade.value = "";
		form.descricaoAtividade.value = "";
		form.idAtividade.focus();
	}

	function limparDescricaoAtividade(){
		
		var form = document.forms[0];
		form.descricaoAtividade.value = "";
	}

	function removerAtividade(idAtividade){

		var form = document.forms[0];
		form.action = "/gsan/exibirEncerrarOrdemServicoAction.do?removerAtividade=" + idAtividade;
		if (confirm("Confirma remoção?")){

			form.submit();
		}
	}

	function adicionarAtividade(){

		var form = document.forms[0];
		form.action = "/gsan/exibirEncerrarOrdemServicoAction.do?adicionarAtividade=OK";
		form.submit();
	}

	function apropriarHorasAtividade(idAtividade){

		var form = document.forms[0];
		
		if (form.mostrarHorasExecucao.value == '2' || form.mostrarHorasExecucao.value == ''){

			form.mostrarHorasExecucao.value = '1';
			form.mostrarMateriais.value = '2';
			form.idAtividadeSelecionada.value = idAtividade;
		}else{

			if (idAtividade == form.idAtividadeSelecionada.value){

				form.mostrarHorasExecucao.value = '2';
			}else{

				form.idAtividadeSelecionada.value = idAtividade;
			}
		}
		
		roteiro = form.dataRoteiro.value;
		encerramento = form.dataEncerramento.value;
		
		caminhoAction = "/gsan/exibirEncerrarOrdemServicoAction.do";
		
		if (roteiro.length > 0){

			caminhoAction = caminhoAction + "?dataRoteiro=" + roteiro;

			if (encerramento.length > 0){

				caminhoAction = caminhoAction + "&dataEncerramento=" + encerramento;
			}
			
		}else if (encerramento.length > 0){

			caminhoAction = caminhoAction + "?dataEncerramento=" + encerramento;
		}

		form.action = caminhoAction;
		form.submit();
	}

	function apropriarMateriaisAtividade(idAtividade){

		var form = document.forms[0];
		
		if (form.mostrarMateriais.value == '2' || form.mostrarMateriais.value == ''){

			form.mostrarMateriais.value = '1';
			form.mostrarHorasExecucao.value = '2';
			form.idAtividadeSelecionada.value = idAtividade;
		}else{

			if (idAtividade == form.idAtividadeSelecionada.value){

				form.mostrarMateriais.value = '2';
			}else{

				form.idAtividadeSelecionada.value = idAtividade;
			}
		}
		
		roteiro = form.dataRoteiro.value;
		encerramento = form.dataEncerramento.value;
		
		caminhoAction = "/gsan/exibirEncerrarOrdemServicoAction.do";
		
		if (roteiro.length > 0){

			caminhoAction = caminhoAction + "?dataRoteiro=" + roteiro;

			if (encerramento.length > 0){

				caminhoAction = caminhoAction + "&dataEncerramento=" + encerramento;
			}
			
		}else if (encerramento.length > 0){

			caminhoAction = caminhoAction + "?dataEncerramento=" + encerramento;
		}

		form.action = caminhoAction;
		form.submit();
	}

	function adicionarPeriodoExecucao(){

		var form = document.forms[0];
		//if (validateManterHorasExecucaoEquipeOSActionForm(form)){
		if (validateEncerrarOrdemServicoActionForm(form)){
			
			if(form.dataExecucao.value == ''){
				
				alert('Informe Data de Execução');
				form.dataExecucao.focus();
			}else if(form.horaInicioExecucao.value == ''){
				
				alert('Informe Hora Início de Execução');
				form.horaInicioExecucao.focus();				
			}else if(form.horaFimExecucao.value == ''){
				
				alert('Informe Hora Fim de Execução');
				form.horaFimExecucao.focus();				
			}else if (form.horaInicioExecucao.value.length < 5){
	
				alert("Hora Início de Execução inválida.");
				form.horaInicioExecucao.focus();
			}else if (form.horaFimExecucao.value.length < 5){
	
				alert("Hora Fim de Execução inválida.");
				form.horaFimExecucao.focus();
			}else if (form.idEquipeProgramada.value.length < 1 && 
				form.idEquipeNaoProgramada == undefined){
	
				alert("Informe Equipe Programada");
				form.idEquipeNaoProgramada.focus();
			}else if (form.idEquipeProgramada.value.length < 1 && 
				(form.idEquipeNaoProgramada != undefined && form.idEquipeNaoProgramada.value.length < 1)){
	
				alert("Informe Equipe Programada ou Outra Equipe");
				form.idEquipeNaoProgramada.focus();
			}else if (form.idEquipeProgramada.value.length > 0 && 
				(form.idEquipeNaoProgramada != undefined && form.idEquipeNaoProgramada.value.length > 0)){
	
				alert("Informe Equipe Programada ou Outra Equipe");
				form.idEquipeNaoProgramada.focus();
			}else{
				//var url = 'exibirEncerrarOrdemServicoAcompanhamentoRoteiroAction.do?numeroOS='+
				//os+'&dataRoteiro='+data+'&retornoTela=exibirAcompanharRoteiroProgramacaoOrdemServicoAction.do?menu=sim&dataRoteiro='+data;
				form.action = "/gsan/exibirEncerrarOrdemServicoAction.do?adicionarPeriodoEquipe=OK";
				form.submit();
			}
		}
	}

	function removerPeriodoExecucao(timestampEquipe){

		var form = document.forms[0];
	 	
		form.action = "/gsan/exibirEncerrarOrdemServicoAction.do?removerPeriodoEquipe=" + timestampEquipe;
		if (confirm("Confirma remoção?")){

			form.submit();
		}
	}

	function adicionarInterrupcao() {

		var form = document.forms[0];
		if (form.horaInicioInterrupcaoExecucao.value.length < 5) {

			alert("Hora Início de Intervalo inválida.");
			form.horaInicioInterrupcaoExecucao.focus();
		} else if (form.horaFimInterrupcaoExecucao.value.length < 5) {

			alert("Hora Fim de Intervalo inválida.");
				form.horaFimInterrupcaoExecucao.focus();
		} else if (form.horaInicioInterrupcaoExecucao.value == ""
			|| form.horaFimInterrupcaoExecucao.value == ""
			|| form.idMotivoInterrupcaoExecucao.value == "-1") {

			alert("Informe intervalo e motivo da interrupção."); 
		} else {

			form.action = "/gsan/exibirEncerrarOrdemServicoAction.do?adicionarInterrupcao=OK";
			form.submit();
		}
	}

	function removerInterrupcao(timestampEquipe) {

		var form = document.forms[0];
	 	
		form.action = "/gsan/exibirEncerrarOrdemServicoAction.do?removerInterrupcao=" + timestampEquipe;
		
		if (confirm("Confirma remoção?")) {
			form.submit();
		}
	}

	function limparMaterial(){

		var form = document.forms[0];
		
		form.idMaterialNaoProgramado.value = "";
		form.descricaoMaterialNaoProgramado.value = "";
		form.idMaterialNaoProgramado.focus();
	}

	function limparDescricaoMaterial(){
		
		var form = document.forms[0];
		form.descricaoMaterialNaoProgramado.value = "";
	}

	function adicionarMaterial(){

		var form = document.forms[0];
		//if (validateManterMaterialExecucaoOSActionForm(form)){
		if(validateEncerrarOrdemServicoActionForm(form)){
								
			if (form.idMaterialProgramado.value.length < 1 && 
				form.idMaterialNaoProgramado.value.length < 1){
				alert("Informe Material Padrão ou Outro Material");
				form.idMaterialProgramado.focus();
			}
			else if (form.idMaterialProgramado.value.length > 0 && 
					 form.idMaterialNaoProgramado.value.length > 0){
				alert("Informe Material Padrão ou Outro Material");
				form.idMaterialProgramado.focus();
			}
			else{
				form.action = "/gsan/exibirEncerrarOrdemServicoAction.do?adicionarMaterial=OK";
				form.submit();
			}
					
		}	
	} 

	function removerMaterial(objetoRemocao){

		var form = document.forms[0];
		form.action = "/gsan/exibirEncerrarOrdemServicoAction.do?removerMaterial=" + objetoRemocao;
		form.submit();
	}

	function componentes(idEquipe, dataExecucao, horaInicio, horaFim, descricaoAtividade, idAtividade){
		
		var form = document.forms[0];
	  	
	  	window.location.href="/gsan/exibirManterComponenteExecucaoOSAction.do?numeroOS=" + form.numeroOS.value 
	  	+ "&idAtividade=" + idAtividade
	  	+ "&descricaoAtividade=" + descricaoAtividade + "&idEquipe=" + idEquipe 
	  	+ "&dataExecucaoEquipeComponente=" + dataExecucao + "&horaInicioExecucaoEquipeComponente=" + horaInicio 
	  	+ "&horaFimExecucaoEquipeComponente=" + horaFim
	  	+ "&dataRoteiro=" + form.dataRoteiro.value;
	}
	
</script>
<body>

	<html:hidden property="mostrarHorasExecucao" />
	<html:hidden property="idAtividadeSelecionada"/>
	<html:hidden property="mostrarMateriais"/>
	
	<table width="100%" border="0" bgcolor="#99CCFF">
		
		<tr bgcolor="#99CCFF">
			
			<td height="18" colspan="2">
	
				<div id="layerShowAtividades" style="display:block;">
	
					<table width="100%" border="0" bgcolor="#99CCFF">
	
						<tr bgcolor="#99CCFF">
							<td align="center">
								<span class="style2"> 
								<a href="javascript:extendeTabela('Atividades',false);" /><b>Dados das Atividades</b> </a> 
								</span>
							</td>
						</tr>
						
						<tr bgcolor="#cbe5fe">
							<td>
								
								<table border="0" width="100%">
									<tr>
							          <td width="20%"><strong>Roteiro: </strong></td>
							          <td width="80%" colspan="3">
							          	<html:text property="dataRoteiro" 
							          		size="11" readonly="true" 
							          		style="background-color:#EFEFEF; border:0; color: #000000"/>
							          </td>
							        </tr>
									<tr>
							      		<td width="20%"><strong>Atividade:</strong></td>
							        	<td width="80%" colspan="3">
											<html:text property="idAtividade" 
												size="7" 
												maxlength="6" 
												onkeypress="limparDescricaoAtividade();validaEnterComMensagem(event, 'exibirEncerrarOrdemServicoAction.do', 'idAtividade', 'Atividade');"/>
											<a href="javascript:abrirPopup('exibirPesquisarAtividadeAction.do');">
												<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" 
													width="23" height="21" alt="Pesquisar" border="0">
											</a>
							
										<logic:present name="corAtividade">
							
											<logic:equal name="corAtividade" value="exception">
												<html:text property="descricaoAtividade" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
											</logic:equal>
							
											<logic:notEqual name="corAtividade" value="exception">
												<html:text property="descricaoAtividade" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
											</logic:notEqual>
							
										</logic:present>
							
										<logic:notPresent name="corAtividade">
							
											<logic:empty name="EncerrarOrdemServicoActionForm" property="idAtividade">
												<html:text property="descricaoAtividade" value="" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
											</logic:empty>
											<logic:notEmpty name="EncerrarOrdemServicoActionForm" property="idAtividade">
												<html:text property="descricaoAtividade" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
											</logic:notEmpty>
											
							
										</logic:notPresent>
							        	
							        	<a href="javascript:limparAtividade();">
							        	<img src="<bean:message key='caminho.imagens'/>limparcampo.gif" alt="Apagar" border="0"></a>
							       	</td>
								  	</tr>
							       	<tr>
							          <td colspan="4" height="10"></td>
							        </tr>
							        <tr>
								      	<td colspan="4">
								      		
								      		<table width="98%">
												<tr>
													<td><strong>Execução das Atividades:</strong></td>
													<td align="right"><input type="button" class="bottonRightCol"
													value="Adicionar" id="ButtonAdicionar" style="width: 80px" onclick="adicionarAtividade();"></td>
												</tr>
											</table>
								      		<table width="98%" border="0">
										  		<tr> 
									          		<td colspan="4">
														<table width="100%" cellpadding="0" cellspacing="0">
															<tr> 
										                		<td> 
																	<table width="100%" bgcolor="#90c7fc">
																		<tr bgcolor="#90c7fc"> 
											
																			<td align="center" width="10%"><font color="#000000"><strong>Remover</strong></font></td>
																			<td align="center" width="15%"><font color="#000000"><strong>Código</strong></font></td>
																			<td align="center" width="45%"><font color="#000000"><strong>Atividade</strong></font></td>
																			<td align="center" width="15%"><font color="#000000"><strong>Horas</strong></font></td>
																			<td align="center" width="15%"><font color="#000000"><strong>Material</strong></font></td>																			
																		</tr>
										                    		</table>
																</td>
										            		</tr>
										            		
										            		<tr> 
																<td> 
																
																<% String cor = "#cbe5fe";%>
																
																	<table width="100%" align="center"  bgcolor="#90c7fc">
																		<logic:present name="colecaoManterDadosAtividadesOrdemServicoHelper" scope="session">
																			<logic:iterate name="colecaoManterDadosAtividadesOrdemServicoHelper" id="atividadeHelper" type="ManterDadosAtividadesOrdemServicoHelper">
											                            
																				<%	if (cor.equalsIgnoreCase("#FFFFFF")){
																						cor = "#cbe5fe";%>
																					<tr bgcolor="#cbe5fe">
																				<%} else{
																						cor = "#FFFFFF";%>
																					<tr bgcolor="#FFFFFF">
																				<%}%> 
																				
																					<td align="center" width="10%">
																						<a href="javascript:removerAtividade('<bean:write name="atividadeHelper" property="ordemServicoAtividade.atividade.id"/>');">
																							<img src="<bean:message key='caminho.imagens'/>Error.gif" border="0" >
																						</a>
																					</td>
																					
																					<td align="center" width="15%"><bean:write name="atividadeHelper" property="ordemServicoAtividade.atividade.id"/></td>
																					
																					<td width="45%"><bean:write name="atividadeHelper" property="ordemServicoAtividade.atividade.descricao"/></td>
																					
																					<td align="center" valign="middle" width="15%">
																						<a href="javascript:apropriarHorasAtividade('<bean:write name="atividadeHelper" property="ordemServicoAtividade.atividade.id"/>');" title="Apropiar Horas de Execução da Atividade">
																							<img src="<bean:message key='caminho.imagens'/>relogioTransparente.gif" border="0">
																						</a>
																					</td>
																					
																					<td align="center" valign="middle" width="15%">
																						<a href="javascript:apropriarMateriaisAtividade('<bean:write name="atividadeHelper" property="ordemServicoAtividade.atividade.id"/>');" title="Apropiar Materias da Atividade">
																							<img src="<bean:message key='caminho.imagens'/>marteloTransparente3.gif" border="0">
																						</a>
																					</td>
																				</tr>
																				<logic:equal name="EncerrarOrdemServicoActionForm" property="mostrarHorasExecucao"
																								value="<%=""+ConstantesSistema.SIM.toString()%>">
																								
																					<logic:equal name="EncerrarOrdemServicoActionForm" property="idAtividadeSelecionada" 
																						value="<%=""+atividadeHelper.getOrdemServicoAtividade().getAtividade().getId().toString()%>">
																						
																						<c:if test="${sessionScope.ordemServicoEncerrar.servicoTipo.indicadorHorariosExecucao != 2}">
																						
																							<tr bgcolor="#cbe5fe" height="20"><td colspan="5"></td>
																							</tr>
																							
																							<tr bgcolor="#cbe5fe"><td colspan="5"><strong>Dados das Execuções:</strong></td>
																							</tr>
																							
																							<tr bgcolor="#cbe5fe">
																					          <td colspan="2"><strong>Data de Execução:<span style="color:#FF0000">*</span></strong></td>
																					          <td colspan="3">
																					          	
																					          	<logic:present name="desabilitarDataExecucao">
																						          	<html:text property="dataExecucao" size="11" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
																						          	<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif"
																									width="20" border="0" align="absmiddle" alt="Exibir Calendário" />&nbsp;<strong>(dd/mm/aaaa)</strong>
																					          	</logic:present>
																					          	
																					          	<logic:notPresent name="desabilitarDataExecucao">
																						          	<html:text property="dataExecucao" size="11" maxlength="10" onkeyup="mascaraData(this, event)"/>
																						          	<a href="javascript:abrirCalendario('EncerrarOrdemServicoActionForm', 'dataExecucao')">
																									<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif"
																									width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>&nbsp;<strong>(dd/mm/aaaa)</strong>
																					          	</logic:notPresent>
																					          	
																					          </td>
																					        </tr>
																					        
																					        <tr bgcolor="#cbe5fe">
																					          <td colspan="2"><strong>Intervalo de Execução:<span style="color:#FF0000">*</span></strong></td>
																					          <td colspan="3">
																					          	<html:text property="horaInicioExecucao" size="6" maxlength="5" onkeyup="mascaraHora(this, event)"/><strong>&nbsp;&nbsp;(hh:mm) às&nbsp;&nbsp;</strong> 
																					          	<html:text property="horaFimExecucao" size="6" maxlength="5" onkeyup="mascaraHora(this, event)"/><strong>&nbsp;&nbsp;(hh:mm)</strong>
																					          </td>
																					        </tr>
																					        
																					        <tr bgcolor="#cbe5fe">
																					          <td colspan="2"><strong>Equipe Programada:</strong></td>
																					          <td colspan="3">
																					          	<html:select property="idEquipeProgramada" style="width: 200px;" >
																									<html:option value="">&nbsp;</html:option>
																									<logic:present name="colecaoEquipe">
																										<html:options collection="colecaoEquipe" labelProperty="nome" property="id"/>
																									</logic:present>
																								</html:select>
																					          </td>
																					        </tr>
																					        
																					        <logic:present name="documentoCobranca">
																					        
																						        <tr bgcolor="#cbe5fe">
																						          <td height="10" colspan="2"></td>
																						          <td colspan="3"><strong>OU</strong></td>
																						        </tr>
																						        
																						        <tr bgcolor="#cbe5fe">
																						      		<td colspan="2"><strong>Outra Equipe:</strong></td>
																						        	<td colspan="3">
																						        	
																						        		<html:text property="idEquipeNaoProgramada" size="4" maxlength="5" onkeypress="limparDescricao();validaEnterComMensagem(event, 'exibirEncerrarOrdemServicoAction.do', 'idEquipeNaoProgramada', 'Equipe não Programada');"/>
																										<a href="javascript:abrirPopup('exibirPesquisarEquipeAction.do');">
																										<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23" height="21" alt="Pesquisar" border="0"></a>
																							
																										<logic:present name="corEquipe">
																							
																											<logic:equal name="corEquipe" value="exception">
																												<html:text property="descricaoEquipeNaoProgramada" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
																											</logic:equal>
																							
																											<logic:notEqual name="corEquipe" value="exception">
																												<html:text property="descricaoEquipeNaoProgramada" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
																											</logic:notEqual>
																							
																										</logic:present>
																							
																										<logic:notPresent name="corEquipe">
																							
																											<logic:empty name="EncerrarOrdemServicoActionForm" property="idEquipeNaoProgramada">
																												<html:text property="descricaoEquipeNaoProgramada" value="" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
																											</logic:empty>
																											<logic:notEmpty name="EncerrarOrdemServicoActionForm" property="idEquipeNaoProgramada">
																												<html:text property="descricaoEquipeNaoProgramada" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
																											</logic:notEmpty>
																											
																							
																										</logic:notPresent>
																							        	
																							        	<a href="javascript:limpar();">
																							        	<img src="<bean:message key='caminho.imagens'/>limparcampo.gif" alt="Apagar" border="0"></a>
																							        
																							       </td>
																							  	</tr>
																						  	
																						  	</logic:present>
																						  	
																						  	<logic:present name="registroAtendimento">
																							  	<tr bgcolor="#cbe5fe">
																						      		<td colspan="2"><strong>Outra Equipe:</strong></td>
																						        	<td colspan="3">
																						        	
																						        		<html:select property="idEquipeNaoProgramada" style="width: 200px;" >
																											<html:option value="">&nbsp;</html:option>
																											<logic:present name="colecaoEquipesPorUnidade">
																												<html:options collection="colecaoEquipesPorUnidade" labelProperty="nome" property="id"/>
																											</logic:present>
																										</html:select>
																										
																						        	</td>
																							  	</tr>
																					        </logic:present>
																					        
																					        <tr bgcolor="#cbe5fe">
																					          <td colspan="2"><strong>Horas de execução:</strong></td>
																					          <td colspan="3" align="right"><input type="button" class="bottonRightCol" value="Adicionar"
																								onclick="adicionarPeriodoExecucao();" id="btnAdicionarPeriodo"></td>
																					        </tr>
																					        
																					        <tr> 
																		                		<td colspan="5"> 
																									<table width="100%" bgcolor="#90c7fc">
																										<tr bgcolor="#90c7fc"> 
																			
																											<td align="center" width="10%"><font color="#000000"><strong>Remover</strong></font></td>
																											<td align="center" width="15%"><font color="#000000"><strong>Data</strong></font></td>
																											<td align="center" width="35%"><font color="#000000"><strong>Período</strong></font></td>
																											<td align="center" width="20%"><font color="#000000"><strong>Equipe Prog.</strong></font></td>
																											<td align="center" width="20%"><font color="#000000"><strong>Equipe Outra</strong></font></td>
																								
																										</tr>
																										
																										<logic:notEmpty name="atividadeHelper" property="colecaoOSAtividadePeriodoExecucaoHelper">
																								        <logic:iterate name="atividadeHelper" property="colecaoOSAtividadePeriodoExecucaoHelper" id="periodoExecucaoHelper" type="OSAtividadePeriodoExecucaoHelper">
															                            
																											<%	if (cor.equalsIgnoreCase("#FFFFFF")){
																													cor = "#cbe5fe";%>
																												<tr bgcolor="#cbe5fe">
																											<%} else{
																													cor = "#FFFFFF";%>
																												<tr bgcolor="#FFFFFF">
																											<%}%> 
																											
																												<td align="center" width="16%">
																													<a href="javascript:removerPeriodoExecucao('<bean:write name="periodoExecucaoHelper" property="ordemServicoExecucaoEquipeHelper.osExecucaoEquipe.equipe.ultimaAlteracaoFormatada"/>');">
																														<img src="<bean:message key='caminho.imagens'/>Error.gif" border="0" >
																													</a>
																												</td>
																												
																												<td align="center" width="16%"><bean:write name="periodoExecucaoHelper" property="osAtividadePeriodoExecucao.dataInicioFormatada"/></td>
																												
																												<td align="center" width="16%">
																													<bean:write name="periodoExecucaoHelper" 
																														property="osAtividadePeriodoExecucao.horaInicioFormatada"/> às
																													<bean:write name="periodoExecucaoHelper" 
																														property="osAtividadePeriodoExecucao.horaFimFormatada"/> horas
																												</td>
																												
																												<td align="center" width="26%">
																													<logic:equal name="periodoExecucaoHelper" property="ordemServicoExecucaoEquipeHelper.indicadorEquipeProgramada"  value="1">
																														
																														<a href="javascript:componentes('<bean:write name="periodoExecucaoHelper" property="ordemServicoExecucaoEquipeHelper.osExecucaoEquipe.equipe.id"/>',
																														 '<bean:write name="periodoExecucaoHelper" property="osAtividadePeriodoExecucao.dataInicioFormatadaSemBarra"/>', 
																														'<bean:write name="periodoExecucaoHelper" property="osAtividadePeriodoExecucao.horaInicioFormatada"/>', 
																														 '<bean:write name="periodoExecucaoHelper" property="osAtividadePeriodoExecucao.horaFimFormatada"/>',
																														 '<bean:write name="atividadeHelper" property="ordemServicoAtividade.atividade.descricao"/>',
																														 '<bean:write name="atividadeHelper" property="ordemServicoAtividade.atividade.id"/>')">
																														 
																														<bean:write name="periodoExecucaoHelper" 
																															property="ordemServicoExecucaoEquipeHelper.osExecucaoEquipe.equipe.nome"/>
																														</a>
																													</logic:equal>
																												</td>
																												
																												<td align="center" width="26%">
																													<logic:equal name="periodoExecucaoHelper" property="ordemServicoExecucaoEquipeHelper.indicadorEquipeProgramada"  value="2">
																														
																														<a href="javascript:componentes('<bean:write name="periodoExecucaoHelper" property="ordemServicoExecucaoEquipeHelper.osExecucaoEquipe.equipe.id"/>',
																														 '<bean:write name="periodoExecucaoHelper" property="osAtividadePeriodoExecucao.dataInicioFormatadaSemBarra"/>', 
																														'<bean:write name="periodoExecucaoHelper" property="osAtividadePeriodoExecucao.horaInicioFormatada"/>', 
																														 '<bean:write name="periodoExecucaoHelper" property="osAtividadePeriodoExecucao.horaFimFormatada"/>',
																														 '<bean:write name="atividadeHelper" property="ordemServicoAtividade.atividade.descricao"/>',
																														 '<bean:write name="atividadeHelper" property="ordemServicoAtividade.atividade.id"/>')">
																														 
																														<bean:write name="periodoExecucaoHelper" 
																															property="ordemServicoExecucaoEquipeHelper.osExecucaoEquipe.equipe.nome"/>
																														</a>
																													</logic:equal>
																												</td>
																											</tr>
																										</logic:iterate>
																									</logic:notEmpty>
																		                    		</table>
																								</td>
																		            		</tr>																		            																				            		
																							
																							<tr bgcolor="#cbe5fe" height="20"><td colspan="5"></td>
																							</tr>
																							
																							<tr bgcolor="#cbe5fe"><td colspan="5"><strong>Dados das Interrupções:</strong></td>
																							</tr>
																							
																							<tr bgcolor="#cbe5fe">
																						         <td width="25%" colspan="2">
																						         	<strong>Intervalo:<span style="color:#FF0000">*</span></strong>
																						         </td>
																						         <td width="75%" colspan="3">
																						         	<html:text property="horaInicioInterrupcaoExecucao" 
																						         		size="6" maxlength="5" onkeyup="mascaraHora(this, event)"/><strong>&nbsp;&nbsp;
																						         		(hh:mm) às&nbsp;&nbsp;</strong> 
																						         	<html:text property="horaFimInterrupcaoExecucao" 
																						         		size="6" maxlength="5" onkeyup="mascaraHora(this, event)"/><strong>&nbsp;&nbsp;
																						         		(hh:mm)</strong>
																						         </td>
																					      	</tr>
																					      	
																					      	<tr bgcolor="#cbe5fe" >
																					         	<td colspan="2"><strong>Motivo<span style="color:#FF0000">*</span></strong></td>
																					         	<td colspan="3"> 
																					         		<select name="idMotivoInterrupcaoExecucao">
																					     			    <option value="-1">&nbsp;</option>
																					     			    <c:forEach items="${colecaoMotivoInterrupcao}" var="motivo" >
																					     			    	<option value="${motivo.id}">${motivo.descricao}</option>
																					     			    </c:forEach>
																									</select>
																				        	 	</td>
																					      	</tr>
																					      	
																					      	<tr bgcolor="#cbe5fe">
																				     	  		<td colspan="2"><strong>Horários Interrupção:</strong></td>
																						  		<td align="right" colspan="3">
																									<input type="button" 
																										class="bottonRightCol" value="Adicionar" id="btnAdicionarInterrupcao"
																										style="width: 80px" onclick="adicionarInterrupcao();">
																						  		</td>
																					      	</tr>
																					      	
																					      	<tr> 
																		                		<td colspan="5"> 
																									<table width="100%" bgcolor="#90c7fc">
																										<tr bgcolor="#90c7fc"> 
																			
																											<td align="center" width="10%"><font color="#000000"><strong>Remover</strong></font></td>
																											<td align="center" width="25%"><font color="#000000"><strong>Data/Hora Início</strong></font></td>
																											<td align="center" width="25%"><font color="#000000"><strong>Data/Hora Fim</strong></font></td>
																											<td align="center" width="40%"><font color="#000000"><strong>Motivo</strong></font></td>
																								
																										</tr>
																		                    		</table>
																								</td>
																		            		</tr>
																		            		
																		            		<logic:notEmpty name="atividadeHelper" property="colecaoInterrupcao">
																						        <logic:iterate name="atividadeHelper" property="colecaoInterrupcao" id="periodoInterrupcao" type="OrdemServicoInterrupcaoExecucao">
													                            
																									<%	if (cor.equalsIgnoreCase("#FFFFFF")){
																											cor = "#cbe5fe";%>
																										<tr bgcolor="#cbe5fe">
																									<%} else{
																											cor = "#FFFFFF";%>
																										<tr bgcolor="#FFFFFF">
																									<%}%> 
																									
																										<td align="center" width="15%">
																											<a href="javascript:removerInterrupcao('<bean:write name="periodoInterrupcao" 
																												property="ultimaAlteracaoFormatada"/>');">
																												<img src="<bean:message key='caminho.imagens'/>Error.gif" border="0" >
																											</a>
																										</td>
																										
																										<td align="center" width="20%"><bean:write name="periodoInterrupcao" 
																											property="interrupcaoInicioFormatada"/></td>
																										
																										<td align="center" width="20%">
																											<bean:write name="periodoInterrupcao" 
																												property="interrupcaoFimFormatada"/> 
																										</td>
																										
																										<td colspan="2" align="center" width="45%">
																											<bean:write name="periodoInterrupcao" 
																												property="motivoInterrupcao.descricao"/>
																										</td>
																										
																									</tr>
																								</logic:iterate>
																							</logic:notEmpty>
																					      	
																					      	<tr bgcolor="#cbe5fe" height="20"><td colspan="5"></td>
																							</tr>
																						</c:if>
																					</logic:equal>
																				</logic:equal>
																				<logic:equal name="EncerrarOrdemServicoActionForm" property="mostrarMateriais"
																								value="<%=""+ConstantesSistema.SIM.toString()%>">
																								
																					<logic:equal name="EncerrarOrdemServicoActionForm" property="idAtividadeSelecionada" 
																						value="<%=""+atividadeHelper.getOrdemServicoAtividade().getAtividade().getId().toString()%>">
																							
																							<c:if test="${sessionScope.ordemServicoEncerrar.servicoTipo.indicadorMaterial != 2}">
																								<tr bgcolor="#cbe5fe"><td colspan="5"><strong>Materias Utilizados:</strong></td>
																								</tr>
																								
																								<tr bgcolor="#cbe5fe">
																								  <td><strong>Material Padrão:</strong></td>
																								  <td colspan="4">
																									<html:select property="idMaterialProgramado" style="width: 200px;">
																										<html:option value="">&nbsp;</html:option>
																											<logic:present name="colecaoMaterialProgramdo">
																												<html:options collection="colecaoMaterialProgramdo" labelProperty="descricao" property="id"/>
																											</logic:present>
																									</html:select>
																								  </td>
																								</tr>
																								
																								<tr bgcolor="#cbe5fe">
																								  <td height="10"></td>
																								  <td colspan="4"><strong>OU</strong></td>
																								</tr>
																								
																								<tr bgcolor="#cbe5fe">
																									<td ><strong>Outro Material:</strong></td>
																									<td colspan="4">
																									
																										<html:text property="idMaterialNaoProgramado" 
																											size="4" 
																											maxlength="5" 
																											onkeypress="limparDescricaoMaterial();validaEnterComMensagem(event, 'exibirEncerrarOrdemServicoAction.do', 'idMaterialNaoProgramado', 'Outro Material');"/>
																										
																										<a href="javascript:abrirPopup('exibirPesquisarMaterialAction.do');">
																											<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" 
																												width="23" height="21" alt="Pesquisar" border="0">
																										</a>
																								
																										<logic:present name="corMaterial">
																								
																											<logic:equal name="corMaterial" value="exception">
																												<html:text property="descricaoMaterialNaoProgramado" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
																											</logic:equal>
																								
																											<logic:notEqual name="corMaterial" value="exception">
																												<html:text property="descricaoMaterialNaoProgramado" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
																											</logic:notEqual>
																								
																										</logic:present>
																								
																										<logic:notPresent name="corMaterial">
																								
																											<logic:empty name="EncerrarOrdemServicoActionForm" property="idMaterialNaoProgramado">
																												<html:text property="descricaoMaterialNaoProgramado" value="" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
																											</logic:empty>
																											<logic:notEmpty name="EncerrarOrdemServicoActionForm" property="idMaterialNaoProgramado">
																												<html:text property="descricaoMaterialNaoProgramado" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
																											</logic:notEmpty>
																											
																								
																										</logic:notPresent>
																										
																										<a href="javascript:limparMaterial();">
																										<img src="<bean:message key='caminho.imagens'/>limparcampo.gif" alt="Apagar" border="0"></a>
																									
																								   </td>
																								</tr>
																								
																								<tr bgcolor="#cbe5fe">
																						          <td><strong>Quantidade:</strong></td>
																						          <td colspan="4">
																						          	<html:text property="quantidade" size="7" maxlength="7" onkeyup="javascript:formataValorDecimal(this, 2, event);" onblur="javascript:formataValorDecimal(this, 2, null);"/>
																						          </td>
																						        </tr>
																								
																						      	<tr bgcolor="#cbe5fe">
																								  	<td><strong>Material de execução:</strong></td>
																								  	<td colspan="4" align="right">
																								  		<input type="button" class="bottonRightCol" 
																								  		value="Adicionar"
																										style="width: 80px" onclick="adicionarMaterial();" 
																										name="botaoAdicionarMaterial" id="btnAdicionarMaterial">
																									</td>
																								</tr>
																								
																								<tr> 
																			                		<td colspan="5"> 
																										<table width="100%" bgcolor="#90c7fc">
																											<tr bgcolor="#90c7fc"> 																			
																												<td align="center" width="10%"><font color="#000000"><strong>Remover</strong></font></td>
																												<td align="center" width="65%"><font color="#000000"><strong>Material</strong></font></td>
																												<td align="center" width="25%"><font color="#000000"><strong>Qtd</strong></font></td>																																																			
																											</tr>
																			                    		</table>
																									</td>
																			            		</tr>
																			            		
																			            		<logic:notEmpty name="atividadeHelper" property="colecaoOsAtividadeMaterialExecucao">
																							        <logic:iterate name="atividadeHelper" property="colecaoOsAtividadeMaterialExecucao" id="osMaterial" type="OsAtividadeMaterialExecucao">
														                            
																										<%	if (cor.equalsIgnoreCase("#FFFFFF")){
																												cor = "#cbe5fe";%>
																											<tr bgcolor="#cbe5fe">
																										<%} else{
																												cor = "#FFFFFF";%>
																											<tr bgcolor="#FFFFFF">
																										<%}%> 
																										
																											<td align="center" width="10%">
																												<a href="javascript:removerMaterial('<bean:write name="osMaterial" 
																													property="ultimaAlteracaoFormatada"/>');">
																													<img src="<bean:message key='caminho.imagens'/>Error.gif" border="0" >
																												</a>
																											</td>
																											
																											<td align="center" colspan="3" width="65%"><bean:write name="osMaterial" 
																												property="material.descricao"/></td>
																											
																											<td align="center" width="25%">
																												<bean:write name="osMaterial" 
																													property="quantidadeMaterial"/> 
																											</td>																																																				
																											
																										</tr>
																										
																								</logic:iterate>
																							</logic:notEmpty>
																							
																							<tr bgcolor="#cbe5fe"><td colspan="5"><hr></hr></td>
																							</tr>
																						</c:if>
																					</logic:equal>
																				</logic:equal>
																			</logic:iterate>
																		</logic:present>
																	</table>
																</td>
										            		</tr>
														</table>
													</td>
												</tr>
											</table>
							      		</td>
						      		</tr>
								</table>
							</td>
						</tr>
					</table>	
				</div>
				<div id="layerHideAtividades" style="display:none">
					
					<table width="100%" border="0" bgcolor="#99CCFF">
						
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('Atividades',true);" /> <b>Dados
							das Atividades</b> </a> </span></td>
						</tr>
						
					</table>
					
				</div>
			</td>
		</tr>
	</table>
	
</body>
</html>