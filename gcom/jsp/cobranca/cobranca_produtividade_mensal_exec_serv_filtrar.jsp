<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="gcom.util.ConstantesSistema"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ include file="/jsp/util/titulo.jsp"%>
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
	
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/AjaxService.js'> </script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script>
	var $jq = jQuery.noConflict(); // Remove conflito do JQuery com DWR devido a utilização do "$" pelo DWR.
	$jq(document).ready(
			function(){
				inicializarCampos();
			}
	);
	
</script>
</head>
<body>
<html:form action="/produtividadeMensalExecucaoServicoAction" name="produtividadeMensalExecucaoServicoForm" type="org.apache.struts.action.DynaActionForm">
<html:hidden property="acao" value="gerarRelatorioProdutividadeMensal"/>
<table width="100%" border="0">
					<tr>
						<td width="100%" colspan=3>
							<table width="100%" border="0">
								<tr>
									<td>Para filtrar os dados do Relatório de Fechamento da Cobran&ccedil;a, informe os dados abaixo:</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td width="90"><strong>Tipo de Comando<font color="red">*</font>:</strong></td>
						<td colspan="2">
							<span class="style2">
								<html:radio property="tipoComando" value="3" onclick="recarregaCampos();"/><strong>Ambos</strong>
								<html:radio property="tipoComando" value="1" onclick="recarregaCampos();"/><strong>Cronograma</strong>
								<html:radio property="tipoComando" value="2" onclick="recarregaCampos();"/><strong>Eventual</strong>
	 						</span>
	 					</td>
					</tr>
					<tr id="comandoEventualTR" style="display: none;">
						<td><strong>Comando Eventual:</strong></td>
        				<td colspan="2">
           					<html:hidden property="idCobrancaAcaoAtividadeComando"/>
        					<html:text property="tituloCobrancaAcaoAtividadeComando" size="35" maxlength="52" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"/>
							<a href="javascript:abrirPopup('exibirPesquisarComandoAcaoCobrancaAction.do?limparForm=OK', 400, 750);" title="Pesquisar">
								<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23" height="21" border="0"></a>
							<a href="javascript:limparTitulo();">
								<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"	border="0" title="Apagar" /></a>
					    </td>
					</tr>
					<tr id="comandoCronogramaTR" style="display: none;">
						<td><Strong>Comando Cronograma:</Strong></td>
						<td colspan="2">
							<html:hidden property="idCobrancaAcaoAtividadeCronograma"/>
							<html:text property="descricaoGrupo" size="20" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>&nbsp;
							<a href="javascript:abrirPopup('exibirPesquisarCronogramaAcaoCobrancaAction.do', 400, 750);" title="Pesquisar">
								<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23" height="21" border="0"></a>
							<a href="javascript:limparGrupo();">
								<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"	border="0" title="Apagar" /></a>
						</td>
					</tr>
					<tr>
						<td><strong>Ação:</strong></td>
						<td colspan="2" align="left"><html:select property="acaoSelecionada">
							<html:option value="-1">&nbsp;</html:option>
							<html:options collection="colecaoCobrancaAcao" labelProperty="descricaoCobrancaAcao" property="id" />
						</html:select></td>
					</tr>
					<tr>
						<td><strong>Empresa<font color="red">*</font>:</strong>:</strong></td>
						<td colspan="2" align="left">
							<html:select property="empresaSelecionada" multiple="true"  style="width: 280px; height: 100px" onchange="validaMultiSelecaoRequired('empresaSelecionada');">
								<html:options collection="colecaoEmpresa" labelProperty="descricao" property="id" />
							</html:select>
						</td>
					</tr>
					<tr>
						<td><strong>Padrão do Periodo:</strong></td>
						<td>
						<html:radio property="padraoPeriodo" value="1" onclick="exibePadraoPeriodo();"></html:radio>dd/mm/aaaa
						<html:radio property="padraoPeriodo" value="2" onclick="exibePadraoPeriodo();"></html:radio>Mês - Ano
						</td>
					</tr>
					<tr id="calendarioInicio">
						<td><strong>Período Inicio:<font color="red">*</font>:</strong></td>
						<td>
							<html:text maxlength="10" property="periodoInicio" size="10" onkeyup="javascript:mascaraData(this,event);"/>
                   			<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" onclick="abrirCalendario('produtividadeMensalExecucaoServicoForm', 'periodoInicio')" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /> dd/mm/aaaa
						</td>
					</tr>
					<tr id="calendarioFim">
						<td><strong>Período Fim:<font color="red">*</font>:</strong></td>
						<td colspan="2">
							<html:text maxlength="10" property="periodoFim" size="10" onkeyup="javascript:mascaraData(this,event);"/>
                      		<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" onclick="abrirCalendario('produtividadeMensalExecucaoServicoForm', 'periodoFim')" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /> dd/mm/aaaa
						</td>
					</tr>		
					<tr id="combosPeriodoInicio" style="display: none;">
					 <td><strong>Período Inicio:<font color="red">*</font>:</strong></strong></td>
					 <td>
					    <html:select property="periodoMesInicio">
					      <html:option value="-1">&nbsp;</html:option>
					      <html:options collection="listaMeses" labelProperty="nomeMes" property="numeroCorrespondente" />
						</html:select>
						<html:select property="periodoAnoInicio">
							<html:option value="-1">&nbsp;</html:option>
							<c:forEach var="ano" items="${listaAnos}">
							   <html:option value="${ano}">${ano}</html:option>
							</c:forEach>
    					</html:select>
					 </td>
					</tr>	
					<tr id="combosPeriodoFim" style="display: none;">
					<td><strong>Período Fim:<font color="red">*</font>:</strong></strong></td>
					 <td>
					    <html:select property="periodoMesFim">
					    	<html:option value="-1">&nbsp;</html:option>
							<html:options collection="listaMeses" labelProperty="nomeMes" property="numeroCorrespondente" />
						</html:select>
						<html:select property="periodoAnoFim">
							<html:option value="-1">&nbsp;</html:option>
							<c:forEach var="ano" items="${listaAnos}">
							   <html:option value="${ano}">${ano}</html:option>
							</c:forEach>
						</html:select>
					 </td>
					</tr>	
					<tr>
						<td>
							
						</td>
						<td colspan="2" align="right"></td>
					</tr>
				</table>
				<table>
				    <tr>
				        <td colspan="2">
							<hr>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<strong>Informe os dados:</strong>
						</td>
					</tr>
					<tr>
					<td colspan="3" >
							<strong>Localidade:</strong>
						</td>
					</tr>
					<tr>
						<td colspan="3" >
							<html:select property="localidade" onchange="filtrarSetor();">
								<html:option value="-1">&nbsp;</html:option>
								<html:options collection="colecaoLocalidade" property="id" labelProperty="descricao" />		
							</html:select>
						</td>					
					</tr>
					<tr>
						<td colspan="3">
							<table cellpadding="0" cellspacing="0" border="0">
								<tr valign="top">
									<td>
										<table cellpadding="0" cellspacing="2" border="0">
											<tr>
												<td>
													<strong>Grupo:</strong>
												</td>
											</tr>
											<tr>
												<td>
													<html:select property="grupo" multiple="true" style="width: 280px; height: 100px" 
													onchange="validarMultiSelecao('grupo');">
														<html:option value="-1">&nbsp;</html:option>
														<logic:present name="colecaoCobrancaGrupo">
															<html:options collection="colecaoCobrancaGrupo" property="id" labelProperty="descricao" />
														</logic:present>
													</html:select>
												</td>
											</tr>
											
											<tr>
												<td colspan="2"> 
													<table cellpadding="0" cellspacing="2">
														<tr>
															<td>
																<strong>Setor:</strong>						
															</td>
															
														</tr>
														<tr>
															<td>
																<html:select property="setor" multiple="true" style="width: 280px; height: 100px" 
																onchange="validarMultiSelecao('setor');">
																<html:option value="-1">&nbsp;</html:option>
																<logic:present name="colecaoQuadras">
																	<html:options collection="colecaoSetores" property="id" labelProperty="codigo"/>
																</logic:present>
																</html:select>						
															</td>
														</tr>
													</table>
												</td>											
											</tr>
										</table>
									</td>
									<td>
										<table cellpadding="0" cellspacing="2" border="0">
											<tr>
												<td colspan="2" style="width: 280px; height: 120px" >
												</td>
											</tr>
											<tr>
												<td>
													<strong>Bairro</strong>						
												</td>
											</tr>
											<tr>
												<td> 
													<html:select property="bairro" multiple="true" style="width: 280px; height: 100px" 
													onchange="validarMultiSelecao('bairro');">
													<html:option value="-1">&nbsp;</html:option>
														<logic:present name="colecaoBairros">
															<html:options collection="colecaoBairros" property="id" labelProperty="nome" />
														</logic:present>
													</html:select>													  
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
								  <td colspan="2">
								      <hr/>
 								  </td>
     							</tr>
     							    <tr>
     							        <td>
										   <strong>Grupo de Serviço:</strong>						
										</td>
										<td>
										   <strong>Subgrupo de Serviço:</strong>						
										</td>
     							    </tr>
     								<tr>
									  <td>
											<html:select property="grupoServico" multiple="true" style="width: 280px; height: 100px" 
													onchange="filtrarSubGrupoServico();validarMultiSelecao('grupoServico');">
													<html:option value="-1">&nbsp;</html:option>
										   	  <logic:present name="colecaoServicoTipoGrupo">
												<html:options collection="colecaoServicoTipoGrupo" property="id" labelProperty="descricao" />
											  </logic:present>
											</html:select>
									   </td>
									  <td>
											<html:select property="subGrupoServico" multiple="true" style="width: 280px; height: 100px" onchange="validarMultiSelecao('subGrupoServico');">
											<html:option value="-1">&nbsp;</html:option>
										   	  <logic:present name="colecaoBairros">
												<html:options collection="colecaoBairros" property="id" labelProperty="nome" />
											  </logic:present>
											</html:select>
									   </td>
									</tr>
									<tr>
								  <td colspan="2">
								      <hr/>
 								  </td>
     							</tr>
									<tr>
									  <td>
										   <strong>Serviço<font color="red">*</font>:</strong>						
										</td>
										<td>
										   						
										</td>
									</tr>
									<tr>
									  <td>
											<html:select property="servico" multiple="true" style="width: 280px; height: 100px">
										   	  <logic:present name="colecaoServicoTipo">
												<html:options collection="colecaoServicoTipo" property="id" labelProperty="descricao" />
											  </logic:present>
											</html:select>
									   </td>
									  <td><%-- 
											<html:select property="tipoCorte" multiple="true" style="width: 280px; height: 100px" 
													onchange="validarBairro();">
										   	  <logic:present name="colecaoBairros">
												<html:options collection="colecaoBairros" property="id" labelProperty="nome" />
											  </logic:present>
											</html:select>
											--%>
									   </td>
									</tr>
									<tr>
									<td>
										<strong>Tipo de Supressão:</strong>						
									</td>
									<td>
										   <strong>Tipo de Corte:</strong>						
										</td>
									</tr>
									<tr>
									  <td>
											<html:select property="tipoSupressao" multiple="true" style="width: 280px; height: 100px" 
													onchange="validarMultiSelecao('tipoSupressao');">
											   <html:option value="-1">&nbsp;</html:option>
										   	  <logic:present name="colecaoSupressaoTipo">
												<html:options collection="colecaoSupressaoTipo" property="id" labelProperty="descricao" />
											  </logic:present>
											</html:select>
									   </td>
									   <td>
											<html:select property="tipoCorte" multiple="true" style="width: 280px; height: 100px" 
													onchange="validarMultiSelecao('tipoCorte');">
											<html:option value="-1">&nbsp;</html:option>
										   	  <logic:present name="colecaoCorteTipo">
												<html:options collection="colecaoCorteTipo" property="id" labelProperty="descricao" />
											  </logic:present>
											</html:select>
									   </td>
									</tr>
							</table>
						</td>
					</tr>
				</table>
				<table cellpadding="0" cellspacing="2" border="0" width="100%">
					<tr>
						<td align="left">
							<strong> 
								<font color="#FF0000"> 
									<input type="button" name="Submit" class="bottonRightCol" value="Desfazer" onClick="limparFormulario();">
														
									<input type="button" name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
										onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
								</font> 
							</strong>							
						</td>
						<td align="right">
							<input type="submit" name="Submit2" class="bottonRightCol" value="Gerar Relatório">
						</td>
					</tr>
				</table>
</html:form>
</body>
<script type='text/javascript'>

function validaCamposObrigatorios(){
	var empresa = $jq("select[name=empresaSelecionada]").val(); //nullable
	var periodoInicio = $jq.trim($("input[name=periodoInicio]").val());
	var periodoFim = $jq.trim($jq("input[name=periodoFim]").val());
	var tipoComando = $jq.trim($jq("input:radio[name=comando]:checked").val());
	var servico = $jq("select[name=servico]").val(); //nullable

	var grupoServico = $jq("select[name=grupoServico]").val();
	var subGrupoServico = $jq("select[name=subGrupoServico]").val();
	var informouServico = true;
	if (servico == null && grupoServico == null && subGrupoServico == null){
		informouServico = false;
	}
	if(empresa == null || periodoInicio == "" ||  periodoFim == ""	||  tipoComando == "" ||  !informouServico ){
		alert("Existem campos obrigatórios não preenchidos");
	}  
	
	
}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.forms[0];
    
    if (tipoConsulta == 'cobrancaAcaoAtividadeComando') {
      form.idCobrancaAcaoAtividadeComando.value = codigoRegistro;
      form.tituloCobrancaAcaoAtividadeComando.value = descricaoRegistro;
    }
    if (tipoConsulta == 'cobrancaAcaoAtividadeCronograma'){
		form.idCobrancaAcaoAtividadeCronograma.value = codigoRegistro;
		form.descricaoGrupo.value = descricaoRegistro;
    }
}

function recarregaCampos() {
	
	 var form = document.forms[0];
	 
	 if (form.tipoComando[0].checked == true) {
	 	// Ambos
	 	document.getElementById("comandoEventualTR").style.display = "none";
	 	document.getElementById("comandoCronogramaTR").style.display = "none";
	 } else if (form.tipoComando[1].checked == true) {
		// Cronograma
	 	document.getElementById("comandoEventualTR").style.display = "none";
	 	document.getElementById("comandoCronogramaTR").style.display = "";
	 } else if (form.tipoComando[2].checked == true) {
		// Eventual
		document.getElementById("comandoEventualTR").style.display = "";
	 	document.getElementById("comandoCronogramaTR").style.display = "none";
	 }
}

function filtrarSubGrupoServico(){
	var idGrupoServico = $jq("select[name=grupoServico]").val();
	var subGruposCombo = $jq("select[name='subGrupoServico']");

	if(idGrupoServico != "-1" && idGrupoServico.length == 1){
		$jq("select[name='subGrupoServico']").attr("length","0");//Apaga lista atual
		AjaxService.carregaSubGrupoTipoPorTipo(parseInt(idGrupoServico), {callback: 
			function(subGrupos) {
				var opcaoNenhum = new Option(" ", "-1");
				subGruposCombo[0].options[subGruposCombo[0].length] = opcaoNenhum;
				for (key in subGrupos){
					var novaOpcao = new Option(subGrupos[key], key);
					subGruposCombo[0].options[subGruposCombo[0].length] = novaOpcao;
  		   		} 
			}, async: false} );	
	}else if (idGrupoServico.length > 1){
		$jq("select[name='subGrupoServico']").attr("length","0");//Apaga lista atual
	}
}

function filtrarSetor(){
	var idLocalidade = $jq("select[name=localidade]").val();
	var escreveItem = true;
		
	if (idLocalidade != "-1") {
		document.forms[0].setor.disabled = false;
		AjaxService.carregaSetoresPorLocalidade(idLocalidade, {callback: 
				function(setores) {
					//var opcaoNenhum = new Option(" ", "-1");
  					//document.forms[0].setor.options[document.forms[0].setor.length] = opcaoNenhum;
					for (key in setores){
						if (escreveItem) {
							var indice = parseInt(key) + 1;
				  			var novaOpcao = new Option(setores[key], setores[indice]);
				  			document.forms[0].setor.options[document.forms[0].setor.length] = novaOpcao;
				  		}
				  		if (escreveItem) {
				  			escreveItem = false;
				  		} else {
				  			escreveItem = true;
				  		}	
	  		   		} 
				}, async: false} );
				
		document.forms[0].bairro.length = 0;
		document.forms[0].bairro.disabled = false;
		var opcaoNenhum = new Option(" ", "-1");
		document.forms[0].bairro.options[document.forms[0].bairro.length] = opcaoNenhum;
		AjaxService.carregaBairrosPorLocalidade(idLocalidade, {callback: 
				function(bairros) {
					for (key in bairros){
				  		var novaOpcao = new Option(key, bairros[key]);
				  		document.forms[0].bairro.options[document.forms[0].bairro.length] = novaOpcao;
	  		   		} 
				}, async: false} );	
	} else {
		document.forms[0].setor.disabled = true;
		document.forms[0].bairro.disabled = true;
		//document.forms[0].rota.length = 0;
		//document.forms[0].quadra.length = 0;
		//document.forms[0].logradouro.length = 0;
		document.forms[0].localidade.value = "";
		document.forms[0].setor.value = "";
		document.forms[0].bairro.value = "";
		//document.forms[0].rota.value = "";
		//document.forms[0].quadra.value = "";
		//document.forms[0].logradouro.value = "";
	}
}

function exibePadraoPeriodo(){
	var selecao = $jq("input[name=padraoPeriodo]:checked").val();
	if(selecao == "1"){
		document.getElementById("combosPeriodoInicio").style.display = "none";
	 	document.getElementById("combosPeriodoFim").style.display = "none";
	 	document.getElementById("calendarioInicio").style.display = "";
	 	document.getElementById("calendarioFim").style.display = "";
	 	$jq("select[name=periodoMesInicio]").val("-1");
	 	$jq("select[name=periodoAnoInicio]").val("-1");
	 	$jq("select[name=periodoMesFim]").val("-1");
	 	$jq("select[name=periodoAnoFim]").val("-1");
	}else{
		document.getElementById("combosPeriodoInicio").style.display = "";
	 	document.getElementById("combosPeriodoFim").style.display = "";
	 	document.getElementById("calendarioInicio").style.display = "none";
	 	document.getElementById("calendarioFim").style.display = "none";
	 	$jq("input:text[name=periodoInicio]").val("");
	 	$jq("input:text[name=periodoFim]").val("");
	 	
	}
}

function validarBairro(){
	var listaBairros = $jq("select[name=bairro]>option:selected");
	if(listaBairros.length > 1 && listaBairros[0].value=='-1'){
	    $jq("select[name=bairro]>option:selected[value='-1']").attr("selected","");
	}
}

function validarSetor(){
	var listaSetores = $jq("select[name=setor]>option:selected");
	if(listaSetores.length > 1 && listaSetores[0].value=='-1'){
	    $jq("select[name=setor]>option:selected[value='-1']").attr("selected","");
	}
}
function validarGrupo(){
	var listaSetores = $jq("select[name=setor]>option:selected");
	if(listaSetores.length > 1 && listaSetores[0].value=='-1'){
	    $jq("select[name=setor]>option:selected[value='-1']").attr("selected","");
	}
}
function validarMultiSelecao(name){
	var listaSelecionada = $jq("select[name="+name+"]>option:selected");
	if(listaSelecionada.length > 1 && listaSelecionada[0].value=='-1'){
	    $jq("select[name="+name+"]>option:selected[value='-1']").attr("selected","");
	}
}

function validaMultiSelecaoRequired(name){
	var listaSelecionada = $jq("select[name="+name+"]>option:selected");
	if(listaSelecionada.length == 0){
	     $jq("select[name="+name+"]>option")[0].selected = "selected";
	}
}

function inicializarCampos(){
	if ($jq("input:radio[name=padraoPeriodo]").val() == ""){
		$jq("input:radio[name=padraoPeriodo][value=1]").attr("checked","checked");
	}
	$jq("input:radio[name=comando][value=3]").attr("checked","checked");
	$jq("select[multiple='true']>option:visible:first-child").attr("selected","selected")
	//$jq("option:visible[value=-1]").attr("selected","selected");	
	//$jq("option:visible[value!=-1]").attr("selected","");
	$jq("input:radio[value=3]").attr("checked","checked");	
	exibePadraoPeriodo();
}

function limparFormulario(){
	$jq("select[name=bairro]").attr("length","1");
	$jq("select[name=setor]").attr("length","1");
	$jq("select:visible[multiple='true']>option:selected").attr("selected","");
	$jq("input[name*=idCobrancaAcaoAtividade]").attr("value","");
	$jq("input[name=tituloCobrancaAcaoAtividadeComando]").attr("value","");
	$jq("input[name=descricaoGrupo]").attr("value","");
	$jq("select[name=acaoSelecionada]>option:first").attr("selected","selected");
	$jq("input[name=periodoInicio]").val("");
	$jq("input[name=periodoFim]").val("");
	$jq("select[name=localidade]>option:first").attr("selected","selected");
	$jq("select[name=periodoMesInicio]>option:first").attr("selected","selected");
	$jq("select[name=periodoMesFim]>option:first").attr("selected","selected");
	$jq("select[name=periodoAnoInicio]>option:first").attr("selected","selected");
	$jq("select[name=periodoAnoFim]>option:first").attr("selected","selected");
	
	inicializarCampos();
	recarregaCampos();
	exibePadraoPeriodo();
	
}

function limparTitulo(){
	 var form = document.forms[0];
	 form.idCobrancaAcaoAtividadeComando.value = null;
	 form.tituloCobrancaAcaoAtividadeComando.value = "";
}

function limparGrupo(){
	var form = document.forms[0];
	form.idCobrancaAcaoAtividadeCronograma.value = null;
	form.descricaoGrupo.value = "";
}
</script>
</html>