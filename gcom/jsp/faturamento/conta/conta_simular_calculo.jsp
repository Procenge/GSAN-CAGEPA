<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>

<%@ page import="gcom.util.ConstantesSistema" %>
<%@ page import="gcom.cadastro.imovel.Categoria" %>
<%@ page import="gcom.cadastro.imovel.Subcategoria" %>
<%@ page import="gcom.faturamento.bean.CalcularValoresAguaEsgotoHelper" %>
<%@ page import="gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao" %>
<%@ page import="gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao" %>
<%@ page import="gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoPerfil" %>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>


<%-- Colocado por Raphael Rossiter em 14/03/2007
	 Objetivo: Manipulação dos objetos que serão exibidos no formulário de acordo com a empresa --%>
								
<%-- ================================================================================= --%>
							
<logic:notEqual name="empresaNome" value="<%= "" + SistemaParametro.EMPRESA_CAERN %>">

<script language="JavaScript">
<!--

function habilitacaoCamposAgua(consumoFaturadoAgua){
	var form = document.forms[0];
	
 	if (form.indicadorAguaFaturavel.value == 2){
 		consumoFaturadoAgua.value = "";
 		consumoFaturadoAgua.disabled = true;
 	}
 	else{
 		consumoFaturadoAgua.disabled = false;
 	}
}



function verificarIndicadorFaturavelLigacaoAguaSituacao(idLigacaoAguaSituacao,listBoxAgua, consumoFaturadoAgua){

	var form = document.forms[0];
	
	AjaxService.verificaIndicadorFaturavelLigacaoAguaSituacao(idLigacaoAguaSituacao, function(indicadorFaturavel) {

			form.indicadorAguaFaturavel.value = indicadorFaturavel;
			habilitacaoCamposAgua(document.forms[0].consumoFaturadoAgua);
			
		  });
}

function removerCategoria(idCategoria){
	var form = document.forms[0];
 	
 	form.action = "/gsan/removerSelecaoCategoriaAction.do?idCategoria=" + idCategoria + "&mapeamento=exibirSimularCalculoConta";
 	
 	if (confirm("Confirma remoção?")){
		form.submit();
	}
}


function validarForm(form,listBoxAgua,listBoxEsgoto,consumoFaturadoAgua,consumoFaturadoEsgoto,percentualEsgoto, listBoxPerfilEsgoto, indicadorEsgotoFaturavel,indicadorAguaFaturavel) {
	var valido = true;

	if (validateSimularCalculoContaActionForm(form)) {
	
		var ANO_LIMITE = document.getElementById("ANO_LIMITE").value;
	
		if (!verificaAnoMesMensagemPersonalizada(form.mesAnoReferencia,'Mês e Ano da Conta inválidos.')){
			valido = false;
		}
		else if ((form.mesAnoReferencia.value.substring(3, 7) * 1) < (ANO_LIMITE * 1)){
			valido = false;
			alert("Ano da Conta não deve ser menor que " + ANO_LIMITE+".");
			form.mesAnoConta.focus();
		}
		else if (indicadorAguaFaturavel.value == 1
			&& consumoFaturadoAgua.value.length < 1){
				valido = false;
				alert("Informe Consumo de Água.");
				consumoFaturadoAgua.focus();
		}
		else if (listBoxAgua.options[listBoxAgua.selectedIndex].id == 1 && consumoFaturadoAgua.value.length < 1){
				valido = false;
				alert("Informe Consumo de Água.");
				consumoFaturadoAgua.focus();
		}
		else if (consumoFaturadoAgua.value.length > 0 && consumoFaturadoAgua.value != 0 && 
				consumoFaturadoAgua.value <= listBoxAgua.options[listBoxAgua.selectedIndex].name){
				valido = false;
				alert("Consumo informado menor que consumo mínimo para situação da ligação de água, valor tem que ser maior que " + listBoxAgua.options[listBoxAgua.selectedIndex].name);
				consumoFaturadoAgua.focus();
		}
		else if ((listBoxPerfilEsgoto.value == null || listBoxPerfilEsgoto.value == 0) && indicadorEsgotoFaturavel.value == 1){
				valido = false;
				alert("Informe Perfil da Ligação de Esgoto.");
				listBoxPerfilEsgoto.focus();
		}
		
		else if (consumoFaturadoEsgoto.value.length > 0 && consumoFaturadoEsgoto.value != 0 && 
				consumoFaturadoEsgoto.value <= listBoxEsgoto.options[listBoxEsgoto.selectedIndex].name){
				valido = false;
				alert("Volume informado menor que volume mínimo para situação da ligação de esgoto, valor tem que ser maior que " + listBoxesgoto.name);
				consumoFaturadoEsgoto.focus();
		}
		else if (indicadorEsgotoFaturavel.value == 1 && percentualEsgoto.value.length < 1){
				valido = false;
				alert("Informe Percentual de Esgoto.");
				percentualEsgoto.focus();
		}
		else if (obterQuantidadeInteiro(percentualEsgoto.value) > 3 ||
				 obterQuantidadeFracao(percentualEsgoto.value) > 2){
				valido = false;
				alert("Percentual de Esgoto inválido.");
				percentualEsgoto.focus();
		}
		else if(comparaData(form.dataLeituraAnteriorMedicaoHistoricoAgua.value, ">", form.dataLeituraAtualMedicaoHistoricoAgua.value)){
			valido = false;
			alert("Data de Leitura Atual deve ser maior que a Data de Leitura Anterior. ");
			form.dataLeituraAtualMedicaoHistoricoAgua.focus();  
		}
		else if( form.existeColecao.value == 1){
				valido = false;
				alert("Informe Categorias e Economias.");
		}
		else if(!validarCamposDinamicos(form)){
			valido = false;
		}
		if (valido){
			
    		submeterFormPadrao(form);
    	}
	}
}


function validarCamposDinamicos(form){
 	var camposValidos = true;
 	var quantidadeEconomia = 0;
 	for (i=0; i < form.elements.length; i++) {
    	if (form.elements[i].type == "text" && form.elements[i].id.length > 1){

			switch (form.elements[i].id){
				case "categoria":
					if (form.elements[i].value.length < 1){
						alert("Informe Quantidade de Economias");
						form.elements[i].focus();
						camposValidos = false;
					}
					else if (isNaN(form.elements[i].value) || form.elements[i].value.indexOf('.') != -1){
						alert("Quantidade de Economias deve somente conter números positivos.");
						form.elements[i].focus();
						camposValidos = false;
					}
					else if (!testarCampoValorZero(form.elements[i], "Quantidade de Categorias")){
						form.elements[i].focus();
						camposValidos = false;
					}
					if (form.elements[i].value > 0){
						
					}
					break;
				default:
					break;
			}	
    	}
    	
    	if (!camposValidos){
    		break;
    	}
    }
    
    return camposValidos;
}

//-->
</script>

</logic:notEqual>
							
<%-- ================================================================================= --%>
<%-- ================================================================================= --%>


<script language="JavaScript">
<!--

function habilitacaoCamposEsgoto(){

	var form = document.forms[0];
	
	if (form.indicadorEsgotoFaturavel.value == 1){

		form.consumoFaturadoEsgoto.disabled = false;
		form.percentualEsgoto.disabled = false;
		form.ligacaoEsgotoPerfilId.disabled = false;
 	}else{

		form.consumoFaturadoEsgoto.value = "";
		form.consumoFaturadoEsgoto.disabled = true;
		
		form.percentualEsgoto.disabled = true;
		form.percentualEsgoto.value = "";
		
		form.ligacaoEsgotoPerfilId.disabled = true;
		form.ligacaoEsgotoPerfilId.value = 0;
  	}
}


function verificarIndicadorFaturavel(idLigacaoEsgotoSituacao){

	var form = document.forms[0];
	
	AjaxService.verificaIndicadorFaturavelLigacaoEsgotoSituacao(idLigacaoEsgotoSituacao, function(indicadorFaturavel) {

			form.indicadorEsgotoFaturavel.value = indicadorFaturavel;
			habilitacaoCamposEsgoto();
			
		  });
}



function carregarDatasFaturamento(){
	
	redirecionarSubmit('exibirSimularCalculoContaAction.do?carregarData=OK');	
}

function carregarPercentualPerfil(percentualPerfil){
	
	var form = document.forms[0];

	if (form.ligacaoEsgotoSituacaoID.value != null && form.ligacaoEsgotoSituacaoID.value != -1){

		AjaxService.verificaIndicadorFaturavelLigacaoEsgotoSituacao(form.ligacaoEsgotoSituacaoID.value, 
			       function(indicadorFaturavel) {
	
			  			if (indicadorFaturavel == 1 && percentualPerfil != 0){
			  				form.percentualEsgoto.readonly= "true";
			  				form.percentualEsgoto.value = percentualPerfil;
			  				alert('Será atribuído ao valor da conta ' + percentualPerfil + '% do valor de água como valor de esgoto.');	
				  		}else{
				  			form.percentualEsgoto.readonly= "false";
				  			form.percentualEsgoto.value = "";
					  	}
					  });
	}else{

		form.percentualEsgoto.value = "";
	}
}

//-->
</script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="SimularCalculoContaActionForm"/>

</head>

<script type="text/javascript" src="<c:out value='${pageContext.request.contextPath}'/>/dwr/engine.js"> </script>
<script type="text/javascript" src="<c:out value='${pageContext.request.contextPath}'/>/dwr/util.js"> </script>
<script type="text/javascript" src="<c:out value='${pageContext.request.contextPath}'/>/dwr/interface/AjaxService.js"> </script>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">
<html:form action="/simularCalculoContaAction" method="post" name="SimularCalculoContaActionForm" type="gcom.gui.faturamento.conta.SimularCalculoContaActionForm">


<INPUT TYPE="hidden" ID="ANO_LIMITE" value="${requestScope.anoLimite}"/>
<html:hidden property="indicadorEsgotoFaturavel"/>
<html:hidden property="indicadorAguaFaturavel"/>

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<table width="770" border="0" cellspacing="4" cellpadding="0">
	<tr>
		<td width="149" valign="top" class="leftcoltext">
			<div align="center">

			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>

			<%@ include file="/jsp/util/mensagens.jsp"%>

	      	</div>
		</td>

		<td width="630" valign="top" class="centercoltext">
			<table height="100%">
				<tr><td></td></tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
					<td class="parabg">Simular Cálculo da Conta</td>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
				</tr>
			</table>
			<p>&nbsp;</p>

<!-- ============================================================================================================================== -->

			<table width="100%" border="0" >
		    	<tr>
		        	<td width="100%">
		         		<table width="100%" border="0" >
							<tr> 
								<td colspan="3">
									Para simular o c&aacute;lculo da conta, informe  os dados abaixo:
								</td>
								<td align="right"></td>
							</tr>
						</table>
						
						<table width="100%" border="0" >
							<tr> 
								<td class="style1" height="10" width="165">
									<strong>M&ecirc;s e Ano da Conta:<font color="#FF0000">*</font></strong> 
								</td>
								<td colspan="2" class="style1">
									<html:text property="mesAnoReferencia" size="7" maxlength="7"  
										onkeyup="mascaraAnoMes(this, event)" />mm/aaaa
								</td>
							</tr>
							<tr>
							 	<td colspan="4" height="10"></td>
		        		    </tr>
				            <tr>
					    		<td colspan="4">
									<table width="100%" align="center" bgcolor="#99CCFF">
		   				      			<tr>
		    								<td align="center"><strong>Dados de Água</strong></td>
			    			  			</tr>
						      			<tr bgcolor="#cbe5fe">
							     			<td width="100%" align="center">
					                			<table width="100%" border="0">
													<tr> 
									      				<td height="10" width="148">
									      					<strong>Situação de Água:<font color="#FF0000">*</font></strong>
									      				</td>
						           					  	<td>
															<html:select property="ligacaoAguaSituacaoID" style="width: 230px;"  																
																	onchange="verificarIndicadorFaturavelLigacaoAguaSituacao(this.value, document.forms[0].consumoFaturadoAgua);">
													       		<logic:present name="colecaoLigacaoAguaSituacao">
															   		<option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</option>
															    	
													      			<logic:iterate name="colecaoLigacaoAguaSituacao" id="ligacaoAguaSituacao" type="LigacaoAguaSituacao">
													      				
													      				<logic:equal name="SimularCalculoContaActionForm" property="ligacaoAguaSituacaoID" value="<%="" + ligacaoAguaSituacao.getId() %>">
													      					<option SELECTED value="<%="" + ligacaoAguaSituacao.getId() %>" id="<%="" + ligacaoAguaSituacao.getIndicadorFaturamentoSituacao() %>" name="<%="" + ligacaoAguaSituacao.getConsumoMinimoFaturamento() %>"><%="" + ligacaoAguaSituacao.getDescricao() %></option>
													      				</logic:equal>
													      				
													      				<logic:notEqual name="SimularCalculoContaActionForm" property="ligacaoAguaSituacaoID" value="<%="" + ligacaoAguaSituacao.getId() %>">
													      					<option value="<%="" + ligacaoAguaSituacao.getId() %>" id="<%="" + ligacaoAguaSituacao.getIndicadorFaturamentoSituacao() %>" name="<%="" + ligacaoAguaSituacao.getConsumoMinimoFaturamento() %>"><%="" + ligacaoAguaSituacao.getDescricao() %></option>
													      				</logic:notEqual>
													      			
													      			</logic:iterate>
													      			
													      		</logic:present>
		   												    </html:select>
														</td>
														<td colspan="2"></td>
													</tr>
													<tr> 
		   							      				<td height="10"><strong>Consumo de Água:</strong></td>
														<td>
															<html:text property="consumoFaturadoAgua" size="10" maxlength="6" style="text-align: right;"/>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table> 
								</td>
							</tr>	
							<tr>
						      	<td colspan="4" height="10"></td>
						   	</tr>
						 	<tr>
							    <td colspan="4">
									<table width="100%" align="center" bgcolor="#99CCFF">
		   					    		<tr>
		    								<td align="center"><strong>Dados de Esgoto</strong></td>
			    			  			</tr>
						      			<tr bgcolor="#cbe5fe">
									    	<td width="100%" align="center">
					                			<table width="100%" border="0">
					                				<tr> 
									      				<td height="10" width="150">
									      					<strong>Situação de Esgoto:<font color="#FF0000">*</font></strong>
									      				</td>
					            					  	<td>
										     				<html:select property="ligacaoEsgotoSituacaoID" style="width: 230px;" 
										     					onchange="verificarIndicadorFaturavel(this.value, document.forms[0].consumoFaturadoEsgoto, document.forms[0].percentualEsgoto);">
														       <logic:present name="colecaoLigacaoAguaSituacao">
															   		
														      		<option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</option>
															    	
													      			<logic:iterate name="colecaoLigacaoEsgotoSituacao" id="ligacaoEsgotoSituacao" type="LigacaoEsgotoSituacao">
													      				
													      				<logic:equal name="SimularCalculoContaActionForm" property="ligacaoEsgotoSituacaoID" value="<%="" + ligacaoEsgotoSituacao.getId() %>">
													      					<option SELECTED value="<%="" + ligacaoEsgotoSituacao.getId() %>" id="<%="" + ligacaoEsgotoSituacao.getIndicadorFaturamentoSituacao() %>" name="<%="" + ligacaoEsgotoSituacao.getVolumeMinimoFaturamento() %>"><%="" + ligacaoEsgotoSituacao.getDescricao() %></option>
													      				</logic:equal>
													      			
													      				<logic:notEqual name="SimularCalculoContaActionForm" property="ligacaoEsgotoSituacaoID" value="<%="" + ligacaoEsgotoSituacao.getId() %>">
													      					<option value="<%="" + ligacaoEsgotoSituacao.getId() %>" id="<%="" + ligacaoEsgotoSituacao.getIndicadorFaturamentoSituacao() %>" name="<%="" + ligacaoEsgotoSituacao.getVolumeMinimoFaturamento() %>"><%="" + ligacaoEsgotoSituacao.getDescricao() %></option>
													      				</logic:notEqual>
													      				
													      			</logic:iterate>
													      			
														      	</logic:present>
						   								    </html:select>
														</td>
														<td colspan="2"></td>
													</tr>
													<tr> 
									      				<td height="10" width="150">
									      					<strong>Perfil da Ligação de Esgoto:</strong>
									      				</td>
					            					  	<td>
										     				<html:select property="ligacaoEsgotoPerfilId" style="width: 230px;"
										     					onchange="carregarPercentualPerfil(this.value);">
														       <logic:present name="colecaoLigacaoEsgotoPerfil">
															   		
														      		<html:option value="0">&nbsp;</html:option>
													      			<html:options collection="colecaoLigacaoEsgotoPerfil" labelProperty="descricao" property="percentualEsgotoConsumidaColetadaFormatado"></html:options>
														      	</logic:present>
						   								    </html:select>
														</td>
														<td colspan="2"></td>
													</tr>
													<tr> 
														<td height="10"><strong>Volume de Esgoto:</strong></td>
														<td>
															<html:text property="consumoFaturadoEsgoto" size="10" maxlength="6" style="text-align: right;"/>
														</td>
													</tr>
													<tr> 
														<td height="10"><strong>Percentual de Esgoto:</strong></td>
														<td>
															<html:text property="percentualEsgoto"  
																size="10" 
																maxlength="6"
																style="text-align: right;" 
																readonly="true"
																onkeyup="formataValorMonetario(this, 5);"/>%
														</td>
													</tr>
											    </table>
											    
											    <script type="text/javascript">
											    	habilitacaoCamposEsgoto();
											    </script>
										     </td>
										</tr>
									</table> 
								</td>
							</tr>
							<tr>
								<td colspan="4" height="10"></td>
							</tr>
							<tr> 
								<td class="style1"><strong>Tarifa:<font color="#FF0000">*</font></strong></td>
								<td colspan="2">
									<html:select property="consumoTarifaID" style="width: 330px;">
										<logic:present name="colecaoConsumoTarifa">
											<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
										    <html:options collection="colecaoConsumoTarifa" labelProperty="descricao" property="id"/>
										</logic:present>
									</html:select>
								</td>
							</tr>
							<tr> 
								<td class="style1"><strong>Grupo de Faturamento:</strong></td>
								<td colspan="2">
									<html:select property="faturamentoGrupoID" style="width: 230px;" onchange="javascript:carregarDatasFaturamento();">
										<logic:present name="colecaoFaturamentoGrupo">
											<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
											<html:options collection="colecaoFaturamentoGrupo" labelProperty="descricao" property="id"/>
										</logic:present>
									</html:select>
								</td>
							</tr>
							<tr>
								<td height="10"><strong>Data Leitura Anterior:</strong></td>
			          	      	<td>
									<html:text property="dataLeituraAnteriorMedicaoHistoricoAgua" size="11" maxlength="10" onkeyup="mascaraData(this, event);"/>
									<a href="javascript:abrirCalendario('SimularCalculoContaActionForm', 'dataLeituraAnteriorMedicaoHistoricoAgua')">
					                 <img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário"/></a>				                 
					                dd/mm/aaaa
							  	</td>
						  	</tr>
						  	<tr> 							
								<td height="10"><strong>Data Leitura Fat.:</strong></td>
					          	<td>
									<html:text property="dataLeituraAtualMedicaoHistoricoAgua" size="11" maxlength="10" onkeyup="mascaraData(this, event);"/>
									<a href="javascript:abrirCalendario('SimularCalculoContaActionForm', 'dataLeituraAtualMedicaoHistoricoAgua')">
					                 <img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário"/></a>				                 
					                dd/mm/aaaa
							  	</td>
						  	</tr>
							<tr> 
								<td height="23" colspan="3" class="style1"> </td>
							</tr>
							
							
							
							
							
							<%-- Colocado por Raphael Rossiter em 14/03/2007
								Objetivo: Manipulação dos objetos que serão exibidos no formulário de acordo com a empresa --%>
								
							<%-- ================================================================================= --%>
							
							<logic:notEqual name="empresaNome" value="<%= "" + SistemaParametro.EMPRESA_CAERN %>">
							
							
							<tr> 
								<td class="style1"><strong>Categorias e Economias:</strong><strong><font color="#FF0000">*</font></strong></td>
								<td colspan="2" class="style1"><div align="right"> 
									<input type="button" class="bottonRightCol" value="Adicionar" style="width: 80px" 
										onclick="javascript:abrirPopup('exibirAdicionarCategoriaContaAction.do', 295, 450);"></div>
								</td>
							</tr>
							<tr> 
								<td colspan="4">
									<table width="100%" cellpadding="0" cellspacing="0">
										<tr> 
											<td> 
												<table width="100%" bgcolor="#99CCFF">
													<tr bgcolor="#99CCFF"> 
														<td align="center" width="10%"><strong>Remover</strong></td>
														<td align="center" width="60%"><strong>Categoria</strong></td>
														<td align="center" width="30%"><strong>Quantidade de Economias</strong></td>
													</tr>
												</table>
											</td>
										</tr>
										<tr> 
											<td> 
												<div style="width: 100%; height=150; overflow: auto;">
					   						    <%
					   						    int cont = 0;
					   						    if(session.getAttribute("existeColecao") != null){
					   						    %>
													<input type="hidden" name="existeColecao" value="1">
												<%}
					   						    else{
												%>
													<input type="hidden" name="existeColecao" value="0">
												<%}%>
				                                <logic:present name="colecaoCategoria">
		        		                        <table width="100%" align="center" bgcolor="#99CCFF">
											    <logic:iterate name="colecaoCategoria" id="categoria" type="Categoria">
													<%cont = cont + 1;
													if (cont % 2 == 0) {%>
													<tr bgcolor="#cbe5fe">
													<%} else {%>
													<tr bgcolor="#FFFFFF">
													<%}%>
													    <td align="center" width="10%" valign="middle">
														   <a href="javascript:removerCategoria(<%="" + categoria.getId().intValue()%>)">
															  <img src="<bean:message key='caminho.imagens'/>Error.gif" border="0" >
														   </a>
														</td>
														<td width="60%">
															<bean:write name="categoria" property="descricao"/>
														</td>
														<td width="30%" align="center">
													      <input type="text" name="categoria<%="" + categoria.getId().intValue()%>" size="6" maxlength="4" id="categoria" value="<%="" + categoria.getQuantidadeEconomiasCategoria()%>" style="text-align: right;"/>
														</td>
													 </tr>
												</logic:iterate>
												</table>
												</logic:present>
												</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="4" height="10"></td>
							</tr>
							
							
							</logic:notEqual>
							
							<%-- ================================================================================= --%>
							<%-- ================================================================================= --%>
							
							
							
							
							
							<%-- Colocado por Raphael Rossiter em 14/03/2007
								Objetivo: Manipulação dos objetos que serão exibidos no formulário de acordo com a empresa --%>
								
							<%-- ================================================================================= --%>
							
							<logic:equal name="empresaNome" value="<%= "" + SistemaParametro.EMPRESA_CAERN %>">
							
							<tr> 
								<td class="style1"><strong>Subcategorias e Economias:</strong><strong><font color="#FF0000">*</font></strong></td>
								<td colspan="2" class="style1"><div align="right"> 
									<input type="button" class="bottonRightCol" value="Adicionar" style="width: 80px" 
										onclick="javascript:abrirPopup('exibirAdicionarCategoriaContaAction.do', 295, 450);"></div>
								</td>
							</tr>
							<tr> 
								<td colspan="4">
									<table width="100%" cellpadding="0" cellspacing="0">
										
										<tr> 
											<td> 
												
												<div style="width: 100%; height=150; overflow: auto;">
					   						    <%
					   						    int cont = 0;
					   						    if(session.getAttribute("existeColecao") != null){
					   						    %>
													<input type="hidden" name="existeColecao" value="1">
												<%}
					   						    else{
												%>
													<input type="hidden" name="existeColecao" value="0">
												<%}%>
				                                
				                                <logic:present name="colecaoSubcategoria">
				                                
				                                <% Integer idCategoria = null; %>
		        		                        
		        		                        <table width="100%" align="center" bgcolor="#99CCFF">
											    
											    <logic:iterate name="colecaoSubcategoria" id="subcategoria" type="Subcategoria">
											    
											    	<% if (idCategoria == null || 
											    		   idCategoria.intValue() != subcategoria.getCategoria().getId().intValue()){ %>
											    	
											    		<tr bgcolor="#79bbfd"> 
															<td colspan="3"><strong><bean:write name="subcategoria" property="categoria.descricao"/></strong></td>
														</tr>
											    	
											    		<tr bgcolor="#99CCFF"> 
															<td align="center" width="10%"><strong>Remover</strong></td>
															<td align="center" width="60%"><strong>Subcategoria</strong></td>
															<td align="center" width="30%"><strong>Quantidade de Economias</strong></td>
														</tr>
														
														<% idCategoria = subcategoria.getCategoria().getId(); 
															cont = 0; %>
													
													<%} %>
												
													<%cont = cont + 1;
													if (cont % 2 == 0) {%>
													<tr bgcolor="#cbe5fe">
													<%} else {%>
													<tr bgcolor="#FFFFFF">
													<%}%>
													    <td align="center" width="10%" valign="middle">
														   <a href="javascript:removerSubcategoria(<%="" + subcategoria.getId().intValue()%>)">
															  <img src="<bean:message key='caminho.imagens'/>Error.gif" border="0" >
														   </a>
														</td>
														<td width="60%">
															<bean:write name="subcategoria" property="descricao"/>
														</td>
														<td width="30%" align="center">
													      <input type="text" name="subcategoria<%="" + subcategoria.getId().intValue()%>" size="6" maxlength="4" id="subcategoria" value="<%="" + subcategoria.getQuantidadeEconomias() %>" style="text-align: right;"/>
														</td>
													 </tr>
												
												</logic:iterate>
												
												</table>
												
												</logic:present>
												
												</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="4" height="10"></td>
							</tr>
							
							</logic:equal>
							
							<%-- ================================================================================= --%>
							<%-- ================================================================================= --%>
							
							
							
							
							
							<tr> 
								<td class="style1">&nbsp;</td>
								<td width="221" class="style1"><font color="#FF0000">*</font> 
								Campos Obrigat&oacute;rios</td>
								<td width="226" class="style1" align="right">
									<input type="button" name="adicionar22" class="bottonRightCol" value="Calcular" 
										onclick="validarForm(document.forms[0],document.forms[0].ligacaoAguaSituacaoID,document.forms[0].ligacaoEsgotoSituacaoID,document.forms[0].consumoFaturadoAgua,document.forms[0].consumoFaturadoEsgoto,document.forms[0].percentualEsgoto, document.forms[0].ligacaoEsgotoPerfilId, document.forms[0].indicadorEsgotoFaturavel,document.forms[0].indicadorAguaFaturavel)">
		   				        </td>
				            </tr>
							<tr> 
								<td colspan="3" class="style1"></td>
							</tr>
							
							
							
							
							<%-- Colocado por Raphael Rossiter em 14/03/2007
								Objetivo: Manipulação dos objetos que serão exibidos no formulário de acordo com a empresa --%>
								
							<%-- ================================================================================= --%>
							
							<logic:notEqual name="empresaNome" value="<%= "" + SistemaParametro.EMPRESA_CAERN %>">
							
							<tr> 
								<td class="style1" colspan=3><strong>Valores Calculados de &Aacute;gua e Esgoto por Categoria</strong></td>
							</tr>
							<tr>
								<td colspan="3" class="style1">
									<table width="100%" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF"> 
											<td align="center" width="31%"><strong>Categoria</strong></td>
											<td align="center" width="24%"><strong>Valor de &Aacute;gua</strong></td>
											<td align="center" width="24%"><strong>Valor de Esgoto</strong></td>
											<td align="center" width="21%"><strong>Valor Total</strong></td>
										</tr>
									</table>
									
							</logic:notEqual>
							
							<%-- ================================================================================= --%>
							<%-- ================================================================================= --%>
							
							
							
							<%-- Colocado por Raphael Rossiter em 14/03/2007
								Objetivo: Manipulação dos objetos que serão exibidos no formulário de acordo com a empresa --%>
								
							<%-- ================================================================================= --%>
							
							<logic:equal name="empresaNome" value="<%= "" + SistemaParametro.EMPRESA_CAERN %>">
							
							<tr> 
								<td class="style1" colspan=3><strong>Valores Calculados de &Aacute;gua e Esgoto por Subcategoria</strong></td>
							</tr>
							<tr>
								<td colspan="3" class="style1">
									<table width="100%" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF"> 
											<td align="center" width="31%"><strong>Subcategoria</strong></td>
											<td align="center" width="24%"><strong>Valor de &Aacute;gua</strong></td>
											<td align="center" width="24%"><strong>Valor de Esgoto</strong></td>
											<td align="center" width="21%"><strong>Valor Total</strong></td>
										</tr>
									</table>
									
							</logic:equal>
							
							<%-- ================================================================================= --%>
							<%-- ================================================================================= --%>
									
									
									<logic:present name="colecaoCalcularValoresAguaEsgotoHelper">
									<% int cont = 0;%>
									<table width="100%" bgcolor="#99CCFF">
										<logic:iterate name="colecaoCalcularValoresAguaEsgotoHelper" 
											id="calcularValoresAguaEsgotoHelper" type="CalcularValoresAguaEsgotoHelper">
										<%cont = cont + 1;
										if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe" height="18">
										<%} else {%>
										<tr bgcolor="#FFFFFF" height="18">
										<%}%>
											<td width="31%" align="center">
												<bean:write name="calcularValoresAguaEsgotoHelper" property="descricaoCategoria"/>
											</td>
											<td width="24%" align="right">
												<bean:write name="calcularValoresAguaEsgotoHelper" property="valorFaturadoAguaCategoria" formatKey="money.format"/>
											</td>
											<td width="24%" align="right">
												<bean:write name="calcularValoresAguaEsgotoHelper" property="valorFaturadoEsgotoCategoria" formatKey="money.format"/>
											</td>
											<td width="21%" align="right">
												<bean:write name="calcularValoresAguaEsgotoHelper" property="valorTotalCategoria" formatKey="money.format"/>
											</td>
										</tr>
								   		</logic:iterate>
								   	</table>
								   	<br>
								   	<table width="100%" bgcolor="#99CCFF">
										<tr bgcolor="#cbe5fe" height="18"> 
											<td width="31%" align="right"><strong>Total:&nbsp;</strong></td>
											<td width="24%" bgcolor="#FFFFFF" align="right">
												<bean:write name="valorTotalAgua" formatKey="money.format"/>
											</td>
											<td width="24%" bgcolor="#FFFFFF" align="right">
												<bean:write name="valorTotalEsgoto" formatKey="money.format"/>
											</td>
											<td width="21%" bgcolor="#FFFFFF" align="right">
												<bean:write name="valorTotalCategorias" formatKey="money.format"/>
											</td>
										</tr>
									</table>
									</logic:present>
								</td>
							</tr>
							<tr>
								<td colspan="4" height="10"></td>
							</tr>
							
							
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="button" name="Button" class="bottonRightCol" value="Desfazer"
							onClick="javascript:window.location.href='/gsan/exibirSimularCalculoContaAction.do?menu=sim'">
						<input type="button" name="Button" class="bottonRightCol" value="Cancelar"
							onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"/>
					</td>
				</tr>	
				
<!-- ============================================================================================================================== -->

			</table> 
			<p>&nbsp;</p>
		</td>
	</tr>
</table>

<%@ include file="/jsp/util/rodape.jsp"%>

<script language="JavaScript">
//<!--
//habilitacaoCamposAgua(document.forms[0].ligacaoAguaSituacaoID, document.forms[0].consumoFaturadoAgua);
//-->
</script>
</html:form>
</body>
</html:html>