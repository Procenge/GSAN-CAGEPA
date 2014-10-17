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
<%@ page import="gcom.gui.GcomAction"%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<script language="JavaScript">

function removerInterrupcaoDeslocamento(posicaoInterrupcao) {

	var form = document.forms[0];
	form.action = "/gsan/exibirEncerrarOrdemServicoAction.do?removerInterrupcaoDeslocamento=" + posicaoInterrupcao;
	if (confirm("Confirma remoção?")) {

		form.submit();
	}
}

function adicionarInterrupcaoDeslocamento() {

	var form = document.forms[0];
	
	if (form.kmInicial.value != ""
		&& form.kmFinal.value != ""
		&& form.placaVeiculo.value != ""
		&& form.dataInicioDeslocamento.value != ""
		&& form.dataFimDeslocamento.value != ""
		&& form.horaInicioDeslocamento.value != ""
		&& form.horaFimDeslocamento.value != ""
		&& form.dataInicioInterrupcaoDeslocamento.value != ""
		&& form.horaInicioInterrupcaoDeslocamento.value != ""
		&& form.dataFimInterrupcaoDeslocamento.value != ""
		&& form.horaFimInterrupcaoDeslocamento.value != ""
		&& form.kmInterrupcaoDeslocamento.value != ""
		&& form.idMotivoInterrupcaoDeslocamento.value != "-1") {

		 form.action = "/gsan/exibirEncerrarOrdemServicoAction.do?adicionarInterrupcaoDeslocamento=OK";
		 form.submit();
	} else {

		alert("Para adicionar uma interrupção é preciso informar todos os dados do deslocamento.");
	}
}

function removerVala(posicaoVala) {

	var form = document.forms[0];
	
	form.action = "/gsan/exibirEncerrarOrdemServicoAction.do?removerVala=" + posicaoVala;
	if (confirm("Confirma remoção?")) {

		form.submit();
	}
}

function adicionarVala() {

	var form = document.forms[0];
	form.action = "/gsan/exibirEncerrarOrdemServicoAction.do?adicionarVala=OK";
	form.submit();
}

function consultarPavimentos(obj) {
 	var idLocalOcorrencia = obj.options[obj.selectedIndex].value;
  	var selectPavimentos = document.getElementsByName("idPavimento")[0];
  
  	selectPavimentos.length=0;
  	if (idLocalOcorrencia != "-1") {
		  AjaxService.consultarPavimentos( idLocalOcorrencia, 
		       function(pavimento) {
				  for (key in pavimento){
					  var novaOpcao = new Option(pavimento[key], key);
					  selectPavimentos.options[selectPavimentos.length] = novaOpcao;
		  		   }
				  });
  	} else {
  		var novaOpcao = new Option(" ","-1");
		selectPavimentos.options[selectPavimentos.length] = novaOpcao;
  	}	
}

function consultarCombosAgua(obj) {
 	var idRedeRamal = obj.options[obj.selectedIndex].value;
  	var selectDiametroAgua = document.getElementsByName("idDiametroAgua")[0];
  	var selectMaterialAgua = document.getElementsByName("idMaterialAgua")[0];
  	
  	var idDiametroAguaSelecionado = "${EncerrarOrdemServicoActionForm.idDiametroAgua}";
  	var idMaterialAguaSelecionado = "${EncerrarOrdemServicoActionForm.idMaterialAgua}";
  
  	selectDiametroAgua.length=0;
  	selectMaterialAgua.length=0;
  	if (idRedeRamal != "-1") {
		  AjaxService.consultarDiametroAgua( idRedeRamal, 
		       function(diametro) {
				  for (key in diametro){
					  var novaOpcao = new Option(diametro[key], key);
					  if (key == idDiametroAguaSelecionado) {
					  		novaOpcao.selected = true;
					  }
					  selectDiametroAgua.options[selectDiametroAgua.length] = novaOpcao;
		  		   }
				  });
		  AjaxService.consultarMaterialAgua( idRedeRamal, 
		       function(material) {
				  for (key in material){
					  var novaOpcao = new Option(material[key], key);
					  if (key == idMaterialAguaSelecionado) {
					  		novaOpcao.selected = true;
					  }
					  selectMaterialAgua.options[selectMaterialAgua.length] = novaOpcao;
		  		   }
				  });
  	} else {

  		var novaOpcao = new Option(" ","-1");
		selectDiametroAgua.options[selectDiametroAgua.length] = novaOpcao;
		selectMaterialAgua.options[selectMaterialAgua.length] = novaOpcao;
  	}	
}

function consultarCombosEsgoto(obj) {

 	var idRedeRamal = obj.options[obj.selectedIndex].value;
  	var selectDiametroEsgoto = document.getElementsByName("idDiametroEsgoto")[0];
  	var selectMaterialEsgoto = document.getElementsByName("idMaterialEsgoto")[0];
  	
  	var idDiametroEsgotoSelecionado = "${EncerrarOrdemServicoActionForm.idDiametroEsgoto}";
  	var idMaterialEsgotoSelecionado = "${EncerrarOrdemServicoActionForm.idMaterialEsgoto}";
    
  	selectDiametroEsgoto.length=0;
  	selectMaterialEsgoto.length=0;
  	if (idRedeRamal != "-1") {
		  AjaxService.consultarDiametroEsgoto( idRedeRamal, 
		       function(diametro) {
				  for (key in diametro){
					  var novaOpcao = new Option(diametro[key], key);

					  if (key == idDiametroEsgotoSelecionado) {

						  	novaOpcao.selected = true;
					  }
					  selectDiametroEsgoto.options[selectDiametroEsgoto.length] = novaOpcao;
		  		   }
				  });
		  AjaxService.consultarMaterialEsgoto( idRedeRamal, 
		       function(material) {
				  for (key in material){

					  var novaOpcao = new Option(material[key], key);
					  if (key == idMaterialEsgotoSelecionado) {

						  	novaOpcao.selected = true;
					  }
					  selectMaterialEsgoto.options[selectMaterialEsgoto.length] = novaOpcao;
		  		   }
				  });
  	} else {
  	  	
  		var novaOpcao = new Option(" ","-1");
		selectDiametroEsgoto.options[selectDiametroEsgoto.length] = novaOpcao;
		selectMaterialEsgoto.options[selectMaterialEsgoto.length] = novaOpcao;
  	}	
}
</script>

<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/engine.js'> </script>
<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/util.js'> </script>
<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/interface/AjaxService.js'> </script>

<body>

	
	<table width="100%" border="0" bgcolor="#99CCFF">
		
		<tr bgcolor="#99CCFF">
			
			<td height="18" colspan="2">
	
				<div id="layerShowDadosOperacionais" style="display:block;">
					
					<table width="100%" border="0" bgcolor="#99CCFF">
	
						<c:if test="${sessionScope.ordemServicoEncerrar.servicoTipo.indicadorDeslocamento != 2}">
							<tr bgcolor="#99CCFF">
								<td align="center">
									<span class="style2"> 
									<a href="javascript:extendeTabela('DadosOperacionais',false);" /><b>Dados Operacionais</b> </a> 
									</span>
								</td>
							</tr>
							
							<tr bgcolor="#cbe5fe">
								<td>
									<table border="0" width="100%">
									
										<tr><td colspan="5"><strong>Dados do Deslocamento:</strong></td></tr>
										
										<tr>
								          <td><strong>Km inicial:<span style="color:#FF0000">*</span></strong></td>
								          <td colspan="2">
								          	<html:text property="kmInicial" 
								          		size="7" maxlength="7" 
								          		onkeyup="javascript:formataValorDecimalUmaCasa(this, 7)" />
								          </td>
								          <td><strong>Km final:<span style="color:#FF0000">*</span></strong></td>
								          <td><html:text property="kmFinal" 
								          		size="7" maxlength="7" 
								          		onkeyup="javascript:formataValorDecimalUmaCasa(this, 7)"/>
								          </td>
								        </tr>
								        
								        <tr>
								          <td><strong>Placa Veículo:<span style="color:#FF0000">*</span></strong></td>
								          <td colspan="5">
								          	<html:text property="placaVeiculo" 
								          		size="8" maxlength="8" />
								          	</td>
								         </tr>
								        
								        <tr>
								          <td><strong>Data Inicial:<span style="color:#FF0000">*</span></strong></td>
								          <td colspan="2"><html:text property="dataInicioDeslocamento" size="10" maxlength="10" onkeyup="mascaraData(this, event);"/>
												<a href="javascript:abrirCalendario('EncerrarOrdemServicoActionForm', 'dataInicioDeslocamento');" >
												  <img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif"
													width="20" border="0" align="absmiddle" alt="Exibir Calendário"/>
												</a>
										  </td> 
								          <td><strong>Hora Inicial<span style="color:#FF0000">*</span></strong></td>
								          <td><html:text property="horaInicioDeslocamento" size="5" maxlength="5" onkeyup="mascaraHora(this, event);"/><strong>(hh:mm)</strong></td>
								        </tr>
								        
								        <tr>
								          <td><strong>Data Final:<span style="color:#FF0000">*</span></strong></td>
								          <td colspan="2"><html:text property="dataFimDeslocamento" size="10" maxlength="10" onkeyup="mascaraData(this, event);"/>
												<a href="javascript:abrirCalendario('EncerrarOrdemServicoActionForm', 'dataFimDeslocamento');" >
												  <img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif"
													width="20" border="0" align="absmiddle" alt="Exibir Calendário"/>
												</a>
										  </td> 
								          <td><strong>Hora Final<span style="color:#FF0000">*</span></strong></td>
								          <td><html:text property="horaFimDeslocamento" size="5" maxlength="5" onkeyup="mascaraHora(this, event);"/><strong>(hh:mm)</strong></td>
								        </tr>
								        
								        <tr>
								        	<td>&nbsp;</td>
								        </tr>
								        
								        <tr><td colspan="6"><strong>Dados das Interrupções:</strong></td></tr>
								        
								        <tr>
								          <td><strong>Data Inicial:<span style="color:#FF0000">*</span></strong></td>
								          <td colspan="2"><html:text property="dataInicioInterrupcaoDeslocamento" size="10" maxlength="10" onkeyup="mascaraData(this, event);"/>
												<a href="javascript:abrirCalendario('EncerrarOrdemServicoActionForm', 'dataInicioInterrupcaoDeslocamento');" >
												  <img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif"
													width="20" border="0" align="absmiddle" alt="Exibir Calendário"/>
												</a>
										  </td> 
								          <td><strong>Hora Inicial<span style="color:#FF0000">*</span></strong></td>
								          <td><html:text property="horaInicioInterrupcaoDeslocamento" size="5" maxlength="5" onkeyup="mascaraHora(this, event);"/><strong>(hh:mm)</strong></td>
								        </tr>
								        
								        <tr>
								          <td><strong>Data Final:<span style="color:#FF0000">*</span></strong></td>
								          <td colspan="2"><html:text property="dataFimInterrupcaoDeslocamento" size="10" maxlength="10" onkeyup="mascaraData(this, event);"/>
												<a href="javascript:abrirCalendario('EncerrarOrdemServicoActionForm', 'dataFimInterrupcaoDeslocamento');" >
												  <img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif"
													width="20" border="0" align="absmiddle" alt="Exibir Calendário"/>
												</a>
										  </td> 
								          <td><strong>Hora Final<span style="color:#FF0000">*</span></strong></td>
								          <td><html:text property="horaFimInterrupcaoDeslocamento" size="5" maxlength="5" onkeyup="mascaraHora(this, event);"/><strong>(hh:mm)</strong></td>
								        </tr>
								        
								        <tr>
								          <td><strong>Km Interrupção:<span style="color:#FF0000">*</span></strong></td>
								          <td colspan="2"><html:text property="kmInterrupcaoDeslocamento" size="7" onkeyup="javascript:formataValorDecimalUmaCasa(this, 7)" /></td>
								          <td><strong>Motivo<span style="color:#FF0000">*</span></strong></td>
								          <td> 
								          	<select name="idMotivoInterrupcaoDeslocamento">
						        			    <option value="-1">&nbsp;</option>
						        			    <c:forEach items="${sessionScope.colecaoMotivoInterrupcao}" var="motivo" >
						        			    	<option value="${motivo.id}">${motivo.descricao}</option>
						        			    </c:forEach>
											</select>
								          </td>
								        </tr>
								        
								        <tr>
								          <td colspan="6" height="10"></td>
								        </tr>
								        
								        <tr>
								        	<td colspan="5"><strong>Horários Interrupção:</strong></td>
											<td align="right">
												<input type="button" class="bottonRightCol" id="btnAdicionarInterrupcaoDeslocamento" value="Adicionar" style="width: 80px" onclick="adicionarInterrupcaoDeslocamento();">
											</td>
								        </tr>
								        
								        <tr>
									        <td colspan="6">
									        	<c:set var="i" value="0" />
									        	<display:table class="dataTable" name="sessionScope.colecaoInterrupcaoDeslocamento" sort="list" id="interrupcaoDeslocamento"  pagesize="15" excludedParams="" > 
											        <display:column style="width:10%; text-align: center;" sortable="false" title="Remover">
										       			<a href="javascript:removerInterrupcaoDeslocamento(${i});">
															<img src="<bean:message key='caminho.imagens'/>Error.gif" border="0" >
														</a>
											        </display:column>
											        <display:column style="width:20%;" property="interrupcaoInicio" sortable="false" format="{0,date,dd/MM/yyyy HH:mm}" title="Data/Hora Início"/>
											        <display:column style="width:25%;" property="interrupcaoFim" sortable="false" format="{0,date,dd/MM/yyyy HH:mm}" title="Data/Hora Fim"/>
											        <display:column style="width:15%; text-align: center;" property="numeroKm" sortable="false" title="Km"/>
											        <display:column style="width:40%;" property="motivoInterrupcao.descricao" sortable="false" title="Motivo"/> 
											    	<c:set var="i" value="${i+1}" />
											    </display:table>
											    
					                    	</td>
				                    	</tr>
										
									</table>
								</td>
							</tr>
						</c:if>
						<tr>
							<td>
								<table width="100%">
									<!--  INÍCIO DOS DADOS - SOLICITAÇÃO 315 ADA -->
									<c:if test="${sessionScope.ordemServicoEncerrar.servicoTipo.indicadorRedeRamalAgua != 2}"> 
										
										<tr bgcolor="#cbe5fe">
					            		 	<td colspan="4"><strong>Dados Rede/Ramal de Água:</strong></td>
					            		</tr>
				            			
					            		  <c:if test="${sessionScope.ordemServicoEncerrar.servicoTipo.indicadorCausaOcorrencia != 2}">
					            		    <tr bgcolor="#cbe5fe">
					            			  <td><strong>Causa<span style="color:#FF0000">*</span></strong></td>
									          <td colspan="3"> 
									          	<select name="idCausaAgua">
							        			    <option value="-1">&nbsp;</option>
							        			    <c:forEach items="${sessionScope.colecaoCausa}" var="causa" >
							        			    	<option
							        			    	 <c:if test="${EncerrarOrdemServicoActionForm.idCausaAgua == causa.id}">selected</c:if>
							        			    	 value="${causa.id}">${causa.descricao}</option>
							        			    </c:forEach>
												</select>
									          </td>
									        </tr>
					            		  </c:if>
					            		  
					            		<tr bgcolor="#cbe5fe">
				            			  <td><strong>Rede/Ramal<span style="color:#FF0000">*</span></strong></td>
								          <td colspan="3"> 
								          	<select name="idRedeRamalAgua" onchange="consultarCombosAgua(this);">
						        			    <option value="-1">&nbsp;</option>
						        			    <option 
						        			    <c:if test="${EncerrarOrdemServicoActionForm.idRedeRamalAgua == 1}">selected</c:if>
						        			    value="1">Rede</option>
						        			    <option 
						        			    <c:if test="${EncerrarOrdemServicoActionForm.idRedeRamalAgua == 2}">selected</c:if>
						        			    value="2">Ramal</option>  	
						        			    
						        			     <option 
						        			    <c:if test="${EncerrarOrdemServicoActionForm.idRedeRamalAgua == 3}">selected</c:if>
						        			    value="3">Cavalete</option>  			  
						        			    
											</select>
								          </td>
				            			</tr>
				            			
				            			<tr bgcolor="#cbe5fe">
	            							<td><strong>Diâmetro<span style="color:#FF0000">*</span></strong></td>
								          	<td>
									          	<html:select property="idDiametroAgua">
									          		<html:option value="-1">&nbsp;</html:option>
									          		 <logic:notEmpty name="colecaoDiametroRedeAgua">
									          			<html:options property="id" labelProperty="descricao" collection="colecaoDiametroRedeAgua"/>
									          		</logic:notEmpty>
									          	</html:select>
										  	</td>
										  	<td><strong>Material<span style="color:#FF0000">*</span></strong></td>
								          	<td>
									          	<html:select property="idMaterialAgua">
									          		<html:option value="-1">&nbsp;</html:option>
									          		 <logic:notEmpty name="colecaoMaterialRedeAgua">
									          			<html:options property="id" labelProperty="descricao" collection="colecaoMaterialRedeAgua"/>
									          		</logic:notEmpty>
									          	</html:select>
										  	</td>
				            			</tr>
				            			
				            			<tr bgcolor="#cbe5fe">
				            			  <td><strong>Profundidade:</strong></td>
								          <td><html:text property="profundidadeAgua" size="7" maxlength="7" onkeyup="javaScript:formataValorMonetario(this, 6);"/></td>
								          <td><strong>Pressão:</strong></td>
								          <td><html:text property="pressaoAgua" size="7" maxlength="8" onkeyup="javaScript:formataValorMonetarioQuatroDecimais(this, 8);"/></td>
				            			</tr>
				            			
				            			<tr bgcolor="#cbe5fe">
								          <td><strong>Agente Externo<span style="color:#FF0000">*</span></strong></td>
								          <td colspan="3"> 
								          	<select name="idAgenteExternoAgua">
						        			    <option value="-1">&nbsp;</option>
						        			    <c:forEach items="${sessionScope.colecaoAgenteExterno}" var="agente" >
						        			    	<option 
						        			    	<c:if test="${EncerrarOrdemServicoActionForm.idAgenteExternoAgua == agente.id}">selected</c:if>
						        			    	value="${agente.id}">${agente.descricao}</option>
						        			    </c:forEach>
											</select>
								          </td>
				            			</tr>
				            			
				            			<tr bgcolor="#cbe5fe">
								          <td colspan="4"></td>
								        </tr>
								        <tr bgcolor="#cbe5fe">
					            		 	<td colspan="4"><hr/></td>
					            		</tr>
					            		
									</c:if>
								
									<c:if test="${sessionScope.ordemServicoEncerrar.servicoTipo.indicadorRedeRamalEsgoto != 2}"> 
										
										<tr bgcolor="#cbe5fe">
					            		 	<td colspan="4"><strong>Dados Rede/Ramal de Esgoto:</strong></td>
					            		</tr>
					            		
				            			
					            		  <c:if test="${sessionScope.ordemServicoEncerrar.servicoTipo.indicadorCausaOcorrencia != 2}">
					            			<tr bgcolor="#cbe5fe">
					            			  <td><strong>Causa<span style="color:#FF0000">*</span></strong></td>
									          <td colspan="3"> 
									          	<select name="idCausaEsgoto">
							        			    <option value="-1">&nbsp;</option>
							        			    <c:forEach items="${sessionScope.colecaoCausa}" var="causa" >
							        			    	<option 
							        			    	<c:if test="${EncerrarOrdemServicoActionForm.idCausaEsgoto == causa.id}">selected</c:if>
							        			    	value="${causa.id}">${causa.descricao}</option>
							        			    </c:forEach>
												</select>
									          </td>
									         </tr>
					            		  </c:if>
				            			<tr bgcolor="#cbe5fe">
				            			  <td><strong>Rede/Ramal<span style="color:#FF0000">*</span></strong></td>
								          <td colspan="3"> 
								          	<select name="idRedeRamalEsgoto" onchange="consultarCombosEsgoto(this);">
						        			    <option value="-1">&nbsp;</option>
						        			    <option 
						        			    <c:if test="${EncerrarOrdemServicoActionForm.idRedeRamalEsgoto == 1}">selected</c:if>
						        			    value="1">Rede</option>
						        			    <option 
						        			    <c:if test="${EncerrarOrdemServicoActionForm.idRedeRamalEsgoto == 2}">selected</c:if>
						        			    value="2">Ramal</option>
											</select>
								          </td>
								          
				            			</tr>
				            			
				            			<tr bgcolor="#cbe5fe">
								          <td><strong>Diâmetro<span style="color:#FF0000">*</span></strong></td>
								          	<td>
									          	<html:select property="idDiametroEsgoto">
									          		<html:option value="-1">&nbsp;</html:option>
									          		 <logic:notEmpty name="colecaoDiametroRedeEsgoto">
									          			<html:options property="id" labelProperty="descricao" collection="colecaoDiametroRedeEsgoto"/>
									          		</logic:notEmpty>
									          	</html:select>
										  	</td>
										  	<td><strong>Material<span style="color:#FF0000">*</span></strong></td>
								          	<td>
									          	<html:select property="idMaterialEsgoto">
									          		<html:option value="-1">&nbsp;</html:option>
									          		 <logic:notEmpty name="colecaoMaterialRedeEsgoto">
									          			<html:options property="id" labelProperty="descricao" collection="colecaoMaterialRedeEsgoto"/>
									          		</logic:notEmpty>
									          	</html:select>
										  	</td>
								          
				            			</tr>
				            			
				            			<tr bgcolor="#cbe5fe">
				            			  <td><strong>Profundidade:</strong></td>
								          <td><html:text property="profundidadeEsgoto" size="7" maxlength="7" onkeyup="javaScript:formataValorMonetario(this, 6);"/></td>
								          <td><strong>Pressão:</strong></td>
								          <td><html:text property="pressaoEsgoto" size="7" maxlength="8" onkeyup="javaScript:formataValorMonetarioQuatroDecimais(this, 8);"/></td>
				            			</tr>
				            			
				            			<tr bgcolor="#cbe5fe">
								          <td><strong>Agente Externo<span style="color:#FF0000">*</span></strong></td>
								          <td colspan="3"> 
								          	<select name="idAgenteExternoEsgoto">
						        			    <option value="-1">&nbsp;</option>
						        			    <c:forEach items="${sessionScope.colecaoAgenteExterno}" var="agente" >
						        			    	<option 
						        			    	<c:if test="${EncerrarOrdemServicoActionForm.idAgenteExternoEsgoto == agente.id}">selected</c:if>
						        			    	value="${agente.id}">${agente.descricao}</option>
						        			    </c:forEach>
											</select>
								          </td>
				            			</tr>
				            			
				            			<tr bgcolor="#cbe5fe">
								          <td colspan="4"></td>
								        </tr>
								        <tr bgcolor="#cbe5fe">
					            		 	<td colspan="4"><hr/></td>
					            		</tr>
									</c:if>
								
									<c:if test="${sessionScope.ordemServicoEncerrar.servicoTipo.indicadorVala != 2}"> 
										
										<tr bgcolor="#cbe5fe">
					            		 	<td colspan="4"></td>
					            		</tr>
										
										<tr bgcolor="#cbe5fe">
					            		 	<td colspan="4"><strong>Dados da Vala:</strong></td>
					            		</tr>
					            		
					            		<tr bgcolor="#cbe5fe">
								          <td><strong>Número Vala:<span style="color:#FF0000">*</span></strong></td>
								          <td><html:text property="numeroVala" size="7" maxlength="4"/></td>
								          <td><strong>Local Ocorrência:<span style="color:#FF0000">*</span></strong></td>
								          <td>
								          	<select name="idLocalOcorrencia" onchange="consultarPavimentos(this);">
						        			    <option value="-1">&nbsp;</option>
						        			    <c:forEach items="${sessionScope.colecaoLocalOcorrencia}" var="local" >
						        			    	<option value="${local.id}">${local.descricao}</option>
						        			    </c:forEach>
											</select>
										  </td>
								        </tr>
								        
								        <tr bgcolor="#cbe5fe">
										  <td><strong>Pavimento:<span style="color:#FF0000">*</span></strong></td>
										  <td colspan="3">
									          <html:select property="idPavimento">
									          		<html:option value="-1">&nbsp;</html:option>
									          		 <logic:notEmpty name="colecaoPavimentos">
									          			<html:options property="id" labelProperty="descricao" collection="colecaoPavimentos"/>
									          		</logic:notEmpty>
									          </html:select>
										  	</td>
								        </tr>
								        
								        <tr bgcolor="#cbe5fe">
								        	<td><strong>Comprimento:<span style="color:#FF0000">*</span></strong></td>
								          	<td><html:text property="comprimentoVala" size="7" maxlength="7" onkeyup="javaScript:formataValorMonetario(this, 6);"/></td>
								          	<td><strong>Largura:<span style="color:#FF0000">*</span></strong></td>
								          	<td><html:text property="larguraVala" size="7" maxlength="7" onkeyup="javaScript:formataValorMonetario(this, 6);"/></td>
								        </tr>
								        
								        <tr bgcolor="#cbe5fe">
								          	<td><strong>Profundidade:<span style="color:#FF0000">*</span></strong></td>
								          	<td colspan="3"><html:text property="profundidadeVala" size="7" maxlength="7" onkeyup="javaScript:formataValorMonetario(this, 6);"/></td>
								        </tr>
								        
								        <tr bgcolor="#cbe5fe">
								        	<td colspan="2"><strong>Indicador aterro ?<span style="color:#FF0000">*</span></strong></td>
								        	<td align="left" width="20%">
								        		<label><html:radio property="indicadorValaAterrada" value="1" /> <strong>Sim</strong></label>
											</td>
											<td align="left">
												<label><html:radio property="indicadorValaAterrada"	value="2" /> <strong>Não</strong></label>
											</td>
								        </tr>
								        
								        <tr bgcolor="#cbe5fe">
								        	<td colspan="2"><strong>Indicador entulho?<span style="color:#FF0000">*</span></strong></td>
								        	<td align="left" width="20%">
								        		<label><html:radio property="indicadorEntulho" value="1" /> <strong>Sim</strong></label>
											</td>
											<td align="left">
												<label><html:radio property="indicadorEntulho"	value="2" /> <strong>Não</strong></label>
											</td>
								        </tr>
								        
								        
								        <tr bgcolor="#cbe5fe">
								          <td colspan="4" height="10"></td>
								        </tr>
								        
								        <tr bgcolor="#cbe5fe">
								        	<td><strong>Valas Informadas:</strong></td>
											<td align="right" colspan="3">
												<input type="button" class="bottonRightCol" id="btnAdicionarVala" value="Adicionar" style="width: 80px" onclick="adicionarVala();">
											</td>
								        </tr>
								        
								        <tr bgcolor="#cbe5fe">
									        <td colspan="4">
									        	<c:set var="i" value="0" />
									        	<display:table class="dataTable" name="sessionScope.colecaoVala" sort="list" id="vala"  pagesize="15" excludedParams="" > 
									        		<display:column style="width:10%; text-align: center;" sortable="false" title="Remover">
										       			<a href="javascript:removerVala(${i});">
															<img src="<bean:message key='caminho.imagens'/>Error.gif" border="0" >
														</a>
											        </display:column>
											        <display:column property="numeroVala" sortable="false" title="Número"/>
											        <display:column property="localOcorrencia.descricao" sortable="false" title="Loc. Ocorrência"/>
											        <display:column sortable="false" title="Pavimento">
											        	<c:choose>
											        		<c:when test="${vala.localOcorrencia.indicadorCalcada == 1}">
											        			<c:out value="${vala.pavimentoCalcada.descricao}" />
											        		</c:when>
											        		<c:otherwise>
											        			<c:out value="${vala.pavimentoRua.descricao}" />
											        		</c:otherwise>
											        	</c:choose>
											        </display:column>
											        <display:column property="numeroComprimento" sortable="false" title="Comp."/>
											        <display:column property="numeroLargura" sortable="false" title="Larg."/>
											        <display:column property="numeroProfundidade" sortable="false" title="Prof."/>
											        <display:column sortable="false" title="Aterrada">
											        	<c:choose>
											        		<c:when test="${vala.indicadorAterro eq 1}">
											        			<c:out value="Sim"/>
											        		</c:when>
											        		<c:otherwise>
											        			<c:out value="Não"/>
											        		</c:otherwise>
											        	</c:choose>
											        </display:column>
											        	
											        
											        <display:column sortable="false" title="Entulho">
											        	<c:choose>
											        		<c:when test="${vala.indicadorEntulho eq 1}">
											        			<c:out value="Sim"/>
											        		</c:when>
											        		<c:otherwise>
											        			<c:out value="Não"/>
											        		</c:otherwise>
											        	</c:choose>
											        </display:column>   
											        
											         
											    	<c:set var="i" value="${i+1}" />
									        	</display:table>
									        </td>
							        	</tr>
							        	
								       	<tr>
								          <td colspan="4" height="10"></td>
								        </tr>
								        
								        <tr>
								          <td colspan="4" height="10"></td>
								        </tr>
				
									</c:if>		
								</table>
							</td>
						</tr>
					</table>	
				</div>
				<div id="layerHideDadosOperacionais" style="display:none">
					
					<table width="100%" border="0" bgcolor="#99CCFF">
						
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosOperacionais',true);" /> <b>Dados
							Operacionais</b> </a> </span></td>
						</tr>
						
					</table>
					
				</div>
			</td>
		</tr>
	</table>
