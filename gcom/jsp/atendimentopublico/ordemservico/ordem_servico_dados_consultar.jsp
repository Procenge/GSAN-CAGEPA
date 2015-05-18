<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="displaytag" %>
<link rel="stylesheet" href="<bean:message key="caminho.css"/>displaytag.css" type="text/css">	

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<%--@ page import="gcom.util.ConstantesSistema"--%>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript">
	
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
	
	function atualizar() {
	
		var form = document.forms[0];
		form.action = "exibirAtualizarOrdemServicoAction.do?primeiraVez=1&numeroOS=" + form.numeroOSPesquisada.value + "&retornoTela=exibirConsultarDadosOrdemServicoAction.do";
		form.submit();
	}
	function encerrar() {
	
		var form = document.forms[0];
		form.action = "exibirEncerrarOrdemServicoAction.do?numeroOS=" + form.numeroOSPesquisada.value + "&retornoTela=exibirConsultarDadosOrdemServicoAction.do?numeroOS=" + form.numeroOSPesquisada.value;
		form.submit();
	}
	
	function gerarOS() {
		var form = document.forms[0];

		form.action = 'exibirGerarOrdemServicoAction.do?menu=sim&forward=exibirGerarOrdemServico&caminhoRetornoGerarOs=filtrarOrdemServicoAction.do&numeroRA='+form.numeroRA.value+"&numeroOS=" + form.numeroOSPesquisada.value;
		form.submit();

	}

	function tramitar(){
	
		var form = document.forms[0];
		form.action = "exibirTramitarOrdemServicoAction.do?numeroOS=" + form.numeroOSPesquisada.value + "&retornoTela=exibirConsultarDadosOrdemServicoAction.do";
		form.submit();
	}	
	
	function voltar(manterOS) {

		var form = document.forms[0];
		if(manterOS == ""){
		 window.location.href='/gsan/filtrarOrdemServicoAction.do?voltar=S'
		}else{
			window.location.href='/gsan/'+manterOS;
		}
	}
	
	function validarPesquisa(){
		
		var form = document.forms[0];		
		form.action = "" ;
		if (validateConsultarDadosOrdemServicoActionForm(form)){			
			submeterFormPadrao(form);
		}
		
	}
	
	function imprimirOS(){	
	
		var form = document.forms[0];		
		form.action = "gerarRelatorioOrdemServicoAction.do?idsOS=" + form.numeroOSPesquisada.value;
		form.submit();	
		
		submeterFormPadrao(form);
		
	}
	
	function imprimirParecerOS(){	
		
		var form = document.forms[0];
		form.action = "gerarRelatorioParecerEncerramentoOSAction.do" ;
		submeterFormPadrao(form);
						
	}
	
	function consultarCombosAgua(obj) {
	 	var idRedeRamal = obj.options[obj.selectedIndex].value;
	  	var selectDiametroAgua = document.getElementById("idDiametroAgua");
	  	var selectMaterialAgua = document.getElementById("idMaterialAgua");
	  	var idDiametroAguaSelecionado = "${ConsultarDadosOrdemServicoActionForm.idDiametroAgua}";
	  	var idMaterialAguaSelecionado = "${ConsultarDadosOrdemServicoActionForm.idMaterialAgua}";
	  
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
	  	var selectDiametroEsgoto = document.getElementById("idDiametroEsgoto");
	  	var selectMaterialEsgoto = document.getElementById("idMaterialEsgoto");
	  	
	  	var idDiametroEsgotoSelecionado = "${ConsultarDadosOrdemServicoActionForm.idDiametroEsgoto}";
	  	var idMaterialEsgotoSelecionado = "${ConsultarDadosOrdemServicoActionForm.idMaterialEsgoto}";
	    
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

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="ConsultarDadosOrdemServicoActionForm" />

</head>

<body leftmargin="5" topmargin="5"
	onload="window.focus();setarFoco('${requestScope.nomeCampo}');">

<html:form action="/exibirConsultarDadosOrdemServicoAction"
	name="ConsultarDadosOrdemServicoActionForm"
	type="gcom.gui.atendimentopublico.ordemservico.ConsultarDadosOrdemServicoActionForm"
	method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<input type="hidden" name="numeroOS" value="${param.numeroOS}" />
	<input type="hidden" name="origem" value="${param.origem}" />

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
					<td class="parabg">Consultar Ordem de Servi&ccedil;o</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
					</td>
				</tr>
			</table>

			<p>&nbsp;</p>


			<table width="100%" border="0">
				<tr>
					<td align="right"></td>
				</tr>
			</table>


			<!--Inicio da Tabela Dados Gerais da Ordem de Serviço -->
			<table width="100%" border="0">

				<tr>
					<td height="31">
					<table width="100%" border="0" align="center">

						<tr bgcolor="#cbe5fe">

							<td align="center">
							<table width="100%" border="0" bgcolor="#99CCFF">

								<tr bgcolor="#99CCFF">
									<td height="18" colspan="2">
									<div align="center"><span class="style2"><b>Pesquisar outra
									Ordem de Serviço</b></span></div>
									</td>
								</tr>

								<tr bgcolor="#cbe5fe">
									<td>
									<table border="0" width="100%">

										<tr>
											<td height="10" width="22%"><strong>Número da OS:</strong></td>

											<td><html:text property="numeroOSParametro" size="9"
												maxlength="9" />&nbsp; <input type="button"
												class="bottonRightCol" value="Pesquisar" style="width: 80px"
												onclick="validarPesquisa();"></td>
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

				<tr>
					<td height="20"></td>
				</tr>

				<tr>
					<td height="31" colspan="2">
					<table width="100%" border="0" align="center">

						<tr bgcolor="#cbe5fe">

							<td align="center">
							<table width="100%" border="0" bgcolor="#99CCFF">

								<tr bgcolor="#99CCFF">
									<td height="18" colspan="2">
									<div align="center"><span class="style2"><b>Dados Gerais da
									Ordem de Servi&ccedil;o </b></span></div>
									</td>
								</tr>

								<tr bgcolor="#cbe5fe">
									<td>
									<table border="0" width="100%">
															<!-- Número da OS -->
										<tr>
											<td height="10" width="33%"><strong>N&uacute;mero da OS:</strong></td>

											<td><html:text property="numeroOSPesquisada" readonly="true"
												style="background-color:#EFEFEF; border:0;" size="9"
												maxlength="9" /> &nbsp;&nbsp;&nbsp;&nbsp; 
															<!-- Situação da OS -->
												<strong>Situa&ccedil;&atilde;o
											da OS:</strong> &nbsp;&nbsp; <html:text property="situacaoOS"
												readonly="true" style="background-color:#EFEFEF; border:0;"
												size="24" maxlength="25" /></td>
										</tr>
										<c:if
											test="${ConsultarDadosOrdemServicoActionForm.numeroRA != null}">
											<tr>
												<td height="10" width="33%"><strong>N&uacute;mero do RA:</strong></td>

												<td><html:text property="numeroRA" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="9"
													maxlength="9" /> &nbsp;&nbsp;&nbsp;&nbsp; <strong>Situa&ccedil;&atilde;o
												do RA:</strong> &nbsp;&nbsp; <html:text
													property="situacaoRA" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="24"
													maxlength="25" /></td>
											</tr>
										</c:if>

										<c:if
											test="${ConsultarDadosOrdemServicoActionForm.numeroDocumentoCobranca != null}">
											<tr>
												<td width="33%"><strong>N&uacute;mero do Documento de
												Cobran&ccedil;a:</strong></td>
												<td><html:text property="numeroDocumentoCobranca"
													readonly="true" style="background-color:#EFEFEF; border:0;"
													size="9" maxlength="9" /></td>
											</tr>
										</c:if>

										<tr>
											<td width="33%"><strong>Data da Gera&ccedil;&atilde;o:</strong></td>
											<td><html:text property="dataGeracao" readonly="true"
												style="background-color:#EFEFEF; border:0;" size="9"
												maxlength="10" /></td>
										</tr>
										<tr>
											<td height="10" width="33%"><strong>Tipo do Servi&ccedil;o:</strong></td>
											<td><html:text property="tipoServicoId" readonly="true"
												style="background-color:#EFEFEF; border:0;" size="4"
												maxlength="4" /> <html:text property="tipoServicoDescricao"
												readonly="true" style="background-color:#EFEFEF; border:0;"
												size="50" maxlength="50" /></td>
										</tr>
										<c:if
											test="${ConsultarDadosOrdemServicoActionForm.numeroOSReferencia != null}">
											<tr>
												<td width="33%"><strong>N&uacute;mero da OS de
												Refer&ecirc;ncia:</strong></td>
												<td><html:text property="numeroOSReferencia" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="9"
													maxlength="9" /></td>
											</tr>
										</c:if>

										<c:if
											test="${ConsultarDadosOrdemServicoActionForm.tipoServicoReferenciaId != null}">
											<tr>
												<td height="10" width="33%"><strong>Tipo do Servi&ccedil;o
												de Refer&ecirc;ncia:</strong></td>
												<td><html:text property="tipoServicoReferenciaId"
													readonly="true" style="background-color:#EFEFEF; border:0;"
													size="4" maxlength="4" /> <html:text
													property="tipoServicoReferenciaDescricao" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="50"
													maxlength="50" /></td>
											</tr>
										</c:if>

										<c:if
											test="${ConsultarDadosOrdemServicoActionForm.retornoOSReferida != null}">
											<tr>
												<td height="10" width="33%"><strong>Retorno da OS de
												Refer&ecirc;ncia:</strong></td>
												<td><html:text property="retornoOSReferida" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="48"
													maxlength="48" /></td>
											</tr>
										</c:if>

										<tr>
											<td height="10" width="20"><strong>Observa&ccedil;&atilde;o da Os:</strong></td>
											<td><strong> <html:textarea property="observacaoOs"
												readonly="true" style="background-color:#EFEFEF; border:0;"
												cols="44" rows="8"/> </strong></td>
										</tr>
										<c:if
											test="${ConsultarDadosOrdemServicoActionForm.numeroRA != null}">
											<tr>
												<td height="10" width="20"><strong>Observa&ccedil;&atilde;o do RA:</strong></td>
											<td><strong> <html:textarea property="observacaoRa"
												readonly="true" style="background-color:#EFEFEF; border:0;"
												cols="44" rows="8"/> </strong></td>
											</tr>
										</c:if>

										<tr>
											<td height="10" width="33%"><strong>Valor do Servi&ccedil;o
											Original:</strong></td>

											<td><html:text property="valorServicoOriginal"
												readonly="true"
												style="background-color:#EFEFEF; border:0; text-align:right;"
												size="9" maxlength="9" /> &nbsp;&nbsp;&nbsp;&nbsp; <strong>Valor
											do Servi&ccedil;o Atual:</strong> &nbsp;&nbsp; <html:text
												property="valorServicoAtual" readonly="true"
												style="background-color:#EFEFEF; border:0; text-align:right;"
												size="9" maxlength="9" /></td>
										</tr>
										
										<tr>
									<logic:equal name="permiteCobrarHora" value="1" scope="request">
											<td height="10" width="33%"><strong>Valor das Horas Trabalhadas:</strong></td>
											<td><html:text property="valorHorasTrabalhadas"
												readonly="true"
												style="background-color:#EFEFEF; border:0; text-align:right;"
												size="9" maxlength="9" /> 
												
												&nbsp;&nbsp;&nbsp;&nbsp; 
									</logic:equal>
									<logic:equal name="permiteCobrarMaterial" value="1" scope="request">
										<strong>Valor
											do Material Utilizado:</strong> &nbsp;&nbsp; <html:text
												property="valorMateriais" readonly="true"
												style="background-color:#EFEFEF; border:0; text-align:right;"
												size="9" maxlength="9" /></td>
												</logic:equal>
										</tr>

										<tr>
											<td width="33%"><strong>Prioridade Original:</strong></td>
											<td><html:text property="prioridadeOriginal" readonly="true"
												style="background-color:#EFEFEF; border:0;" size="48"
												maxlength="48" /></td>
										</tr>

										<tr>
											<td width="33%"><strong>Prioridade Atual:</strong></td>
											<td><html:text property="prioridadeAtual" readonly="true"
												style="background-color:#EFEFEF; border:0;" size="48"
												maxlength="48" /></td>
										</tr>


										<tr>
											<td width="33%"><strong>Unidade da Gera&ccedil;&atilde;o:</strong></td>
											<td><html:text property="unidadeGeracaoId" readonly="true"
												style="background-color:#EFEFEF; border:0;" size="6"
												maxlength="8" /> <html:text
												property="unidadeGeracaoDescricao" readonly="true"
												style="background-color:#EFEFEF; border:0;" size="40"
												maxlength="40" /></td>
										</tr>

										<tr>
											<td width="33%"><strong>Usu&aacute;rio da
											Gera&ccedil;&atilde;o:</strong></td>
											<td><html:text property="usuarioGeracaoId" readonly="true"
												style="background-color:#EFEFEF; border:0;" size="4"
												maxlength="4" /> <html:text property="usuarioGeracaoNome"
												readonly="true" style="background-color:#EFEFEF; border:0;"
												size="40" maxlength="40" /></td>
										</tr>
										
										<tr>
											<td width="33%"><strong>Unidade Atual:</strong></td>
											<td><html:text property="unidadeAtualId" readonly="true"
												style="background-color:#EFEFEF; border:0;" size="6"
												maxlength="8" /> <html:text
												property="unidadeAtualDescricao" readonly="true"
												style="background-color:#EFEFEF; border:0;" size="40"
												maxlength="40" /></td>
										</tr>
										<tr>
											<td width="33%"><strong>Quantidade Dias Unidade:</strong></td>
											<td><html:text property="quantidadeDiasUnidade" readonly="true"
												style="background-color:#EFEFEF; border:0;" size="6"
												maxlength="8" /> </td>
										</tr>
										<tr>
											<td width="33%"><strong>Data Prevista Cliente:</strong></td>
											<td><html:text property="dataPrevisaoCliente" readonly="true"
												style="background-color:#EFEFEF; border:0;" size="10"
												maxlength="10" /> </td>
										</tr>

										<tr>
											<td width="33%"><strong>Data da &Uacute;ltima Emiss&atilde;o:</strong></td>
											<td><html:text property="dataUltimaEmissao" readonly="true"
												style="background-color:#EFEFEF; border:0;" size="9" /></td>
										</tr>
										<c:if test="${ConsultarDadosOrdemServicoActionForm.idOSServicoReparo != null}">
										<tr>
											<td width="33%"><strong>OS do Serviço de Reparo:</strong></td>
											<td><html:text property="idOSServicoReparo" readonly="true"
												style="background-color:#EFEFEF; border:0;" size="9" /></td>
										</tr>
										</c:if>

									</table>
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>

				<!-- Dados da Programação -->
				<logic:present name="achouDadosProgramacao">
				<tr>
					<td>
					<div id="layerHideProgramacao" style="display:block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('Programacao',true);" /> <b>Dados
							da Programação</b> </a> </span></td>
						</tr>
					</table>
					</div>

					<div id="layerShowProgramacao" style="display:none">

					<table width="100%" border="0" bgcolor="#99CCFF">

						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('Programacao',false);" /> <b>Dados
							da Programação</b> </a> </span></td>
						</tr>

						<tr bgcolor="#cbe5fe">

							<td>
							<!-- <table border="0" width="100%">

								<tr>
									<td width="33%" class="style3"><strong>Data da Programação:</strong></td>
									<td><html:text property="dataProgramacao" readonly="true"
										style="background-color:#EFEFEF; border:0;" size="10"
										maxlength="10" /></td>
								</tr>
								<tr>
									<td width="33%" class="style3"><strong>Equipe da Programação:</strong></td>
									<td><html:text property="equipeProgramacao" readonly="true"
										style="background-color:#EFEFEF; border:0;" size="30"
										maxlength="30" /></td>
								</tr>
							</table> -->
							
							<table width="100%" border="0" bgcolor="#90c7fc">
								<tr bgcolor="#90c7fc" height="18">
									<td align="center"><strong>Data Programação</strong></td>
									<td align="center"><strong>Equipe</strong></td>
									<td align="center"><strong>Motivo Não Exec.</strong></td>
									<td align="center"><strong>Unidade</strong></td>
									<td align="center"><strong>Usuário</strong></td>
								</tr>
								
								<logic:present name="ConsultarDadosOrdemServicoActionForm" property="collectionRoteiroOSDadosProgramacaoHelpers" scope="session">
									<%int cont = 1;%>
									<logic:iterate id="helperProgramacao" name="ConsultarDadosOrdemServicoActionForm" property="collectionRoteiroOSDadosProgramacaoHelpers" type="gcom.gui.atendimentopublico.ordemservico.RoteiroOSDadosProgramacaoHelper" scope="session">
											<%cont = cont + 1;
												if (cont % 2 == 0) {%>
											<tr bgcolor="#FFFFFF">
												<%} else {
			
												%>
											<tr bgcolor="#cbe5fe">
												<%}%>
																			       								       
										       <td align="center">
													<div>
														<bean:write	name="helperProgramacao" property="dataRoteiroProgramacao" />
													</div>
											   </td>
											   <td align="center">
													<div>
														<span title="<bean:write name="helperProgramacao" property="idEquipe" />">
															<bean:write	name="helperProgramacao" property="nomeEquipe" />
														</span>
													</div>
											   </td>
											   <td align="center">
													<div>
														<bean:write	name="helperProgramacao" property="descricaoProgramNaoEncerMotivo" />
													</div>
											    </td>
												<td align="center">
													<div>
														<span title="<bean:write name="helperProgramacao" property="descricaoUnidadeOrganizacional" />">
															<bean:write	name="helperProgramacao" property="idUnidadeOrganizacional" />
														</span>
													</div>
											    </td>
												<td align="center">
													<div>
														<span title="<bean:write name="helperProgramacao" property="nomeUsuarioProgramacao" />">
															<bean:write	name="helperProgramacao" property="idUsuarioProgramacao" />
														</span>
													</div>
											    </td>
											</tr>
									</logic:iterate>
								</logic:present>
								
								</table>
								
							</td>
						</tr>
					</table>
					</div>
					</td>
				</tr>
				</logic:present>
				<logic:present name="achouDadosInterrupcaoDeslocamento">
				<tr>
					<td>
					<div id="layerHideInterrupcaoDeslocamento" style="display:block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('InterrupcaoDeslocamento',true);" /> <b>Dados
							de Interrupção do Deslocamento</b> </a> </span></td>
						</tr>
					</table>
					</div>

					<div id="layerShowInterrupcaoDeslocamento" style="display:none">

					<table width="100%" border="0" bgcolor="#99CCFF">

						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('InterrupcaoDeslocamento',false);" /> <b>Dados
							de Interrupcao do Deslocamento</b> </a> </span></td>
						</tr>

						<tr bgcolor="#cbe5fe">

							<td>
							<table width="100%" border="0" bgcolor="#90c7fc">
								<tr bgcolor="#90c7fc" height="18">
									<td align="center"><strong>Motivo Interrupção</strong></td>
									<td align="center"><strong>Km</strong></td>
									<td align="center"><strong>Início da Interrupção</strong></td>
									<td align="center"><strong>Fim da Interrupção</strong></td>
								</tr>
								
								<logic:present name="ConsultarDadosOrdemServicoActionForm" property="collectionOsInterrupcaoDeslocamentoHelpers" scope="session">
									<%int cont = 1;%>
									<logic:iterate id="helperInterrupcaoDeslocamento" name="ConsultarDadosOrdemServicoActionForm" property="collectionOsInterrupcaoDeslocamentoHelpers" type="gcom.gui.atendimentopublico.ordemservico.OSDadosInterrupcaoHelper" scope="session">
											<%cont = cont + 1;
												if (cont % 2 == 0) {%>
											<tr bgcolor="#FFFFFF">
												<%} else {
			
												%>
											<tr bgcolor="#cbe5fe">
												<%}%>
																			       								       
										       <td align="center">
													<div>
														<bean:write	name="helperInterrupcaoDeslocamento" property="motivoInterrupcao" />
													</div>
											   </td>
											   <td align="center">
													<div>
														<bean:write	name="helperInterrupcaoDeslocamento" property="km" />
													</div>
											   </td>
											   <td align="center">
													<div>
														<bean:write	name="helperInterrupcaoDeslocamento" property="inicioInterrupcao" />
													</div>
											   </td>
											   <td align="center">
													<div>
														<bean:write	name="helperInterrupcaoDeslocamento" property="fimInterrupcao" />
													</div>
											   </td>
											</tr>
									</logic:iterate>
								</logic:present>
								
								</table>
								
							</td>
						</tr>
					</table>
					</div>
					</td>
				</tr>
				</logic:present>
				<logic:present name="achouDadosInterrupcaoExecucao">
				<tr>
					<td>
					<div id="layerHideInterrupcaoExecucao" style="display:block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('InterrupcaoExecucao',true);" /> <b>Dados
							de Interrupção de Execução</b> </a> </span></td>
						</tr>
					</table>
					</div>

					<div id="layerShowInterrupcaoExecucao" style="display:none">

					<table width="100%" border="0" bgcolor="#99CCFF">

						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('InterrupcaoExecucao',false);" /> <b>Dados
							de Interrupcao de Execução</b> </a> </span></td>
						</tr>

						<tr bgcolor="#cbe5fe">

							<td>
							<table width="100%" border="0" bgcolor="#90c7fc">
								<tr bgcolor="#90c7fc" height="18">
									<td align="center"><strong>Motivo Interrupção</strong></td>
									<td align="center"><strong>Início da Interrupção</strong></td>
									<td align="center"><strong>Fim da Interrupção</strong></td>
								</tr>
								
								<logic:present name="ConsultarDadosOrdemServicoActionForm" property="collectionOsInterrupcaoExecucaoHelpers" scope="session">
									<%int cont = 1;%>
									<logic:iterate id="helperInterrupcaoDeslocamento" name="ConsultarDadosOrdemServicoActionForm" property="collectionOsInterrupcaoExecucaoHelpers" type="gcom.gui.atendimentopublico.ordemservico.OSDadosInterrupcaoHelper" scope="session">
											<%cont = cont + 1;
												if (cont % 2 == 0) {%>
											<tr bgcolor="#FFFFFF">
												<%} else {
			
												%>
											<tr bgcolor="#cbe5fe">
												<%}%>
																			       								       
										       <td align="center">
													<div>
														<bean:write	name="helperInterrupcaoDeslocamento" property="motivoInterrupcao" />
													</div>
											   </td>
											   <td align="center">
													<div>
														<bean:write	name="helperInterrupcaoDeslocamento" property="inicioInterrupcao" />
													</div>
											   </td>
											   <td align="center">
													<div>
														<bean:write	name="helperInterrupcaoDeslocamento" property="fimInterrupcao" />
													</div>
											   </td>
											</tr>
									</logic:iterate>
								</logic:present>
								
								</table>
								
							</td>
						</tr>
					</table>
					</div>
					</td>
				</tr>
				</logic:present>
				<!-- Dados do Local Ocorrencia -->
				<tr bgcolor="#cbe5fe">
					<td align="center">
					<div id="layerHideLocal" style="display:block">

					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('Local',true);" /> <b>Dados do
							Local da Ocorr&ecirc;ncia</b> </a> </span></td>
						</tr>
					</table>
					</div>

					<div id="layerShowLocal" style="display:none">

					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('Local',false);" /> <b>Dados do
							Local da Ocorr&ecirc;ncia</b> </a> </span></td>
						</tr>

						<tr bgcolor="#cbe5fe">
							<td>
							<table border="0" width="100%">
								<tr>
									<td width="33%"><strong>Matr&iacute;cula do Im&oacute;vel:</strong></td>

									<td><html:text property="matriculaImovel" readonly="true"
										style="background-color:#EFEFEF; border:0;" size="8"
										maxlength="8" /> <html:text property="inscricaoImovel"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="21" maxlength="21" /></td>

								</tr>
								<tr>
									<td width="33%"><strong>Rota:</strong></td>

									<td><html:text property="rota" readonly="true"
										style="background-color:#EFEFEF; border:0;" size="8"
										maxlength="8" /> &nbsp;<strong>Sequencial Rota:</strong>&nbsp;
									<html:text property="sequencialRota" readonly="true"
										style="background-color:#EFEFEF; border:0;" size="8"
										maxlength="8" /></td>

								</tr>
								<tr>
									<td width="33%" class="style3"><strong><span class="style2">Endere&ccedil;o
									da Ocorr&ecirc;ncia:</span></strong></td>

									<td><html:textarea property="enderecoOcorrencia"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										cols="50" /></td>
								</tr>
								<tr>
									<td width="33%" class="style3"><strong><span class="style2">Fotos da Ocorr&ecirc;ncia:</span></strong>
									<td>
										<a href="javascript:abrirPopup('exibirFotoOSAction.do?id=${ConsultarDadosOrdemServicoActionForm.numeroOS}', 600, 1000)">											
												<img name="imageField" src="imagens/imgfolder.gif" width="18" height="18" border="0"/>
										</a>
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</div>
					</td>
				</tr>
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
				<!-- Dados do Encerramento -->
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
				<c:if
					test="${ConsultarDadosOrdemServicoActionForm.situacaoOSId == '2'}">
					<tr>
						<td>
						<div id="layerHideEncerramento" style="display:block">
						<table width="100%" border="0" bgcolor="#99CCFF">
							<tr bgcolor="#99CCFF">
								<td align="center"><span class="style2"> <a
									href="javascript:extendeTabela('Encerramento',true);" /> <b>Dados
								do Encerramento da Ordem de Servi&ccedil;o</b> </a> </span></td>
							</tr>
						</table>
						</div>

						<div id="layerShowEncerramento" style="display:none">

						<table width="100%" border="0" bgcolor="#99CCFF">

							<tr bgcolor="#99CCFF">
								<td align="center"><span class="style2"> <a
									href="javascript:extendeTabela('Encerramento',false);" /> <b>Dados
								do Encerramento da Ordem de Servi&ccedil;o</b> </a> </span></td>
							</tr>

							<tr bgcolor="#cbe5fe">

								<td>
								<table border="0" width="100%">
								
									<tr>
										<td class="style3"><strong>Data de Execução:</strong></td>

										<td><html:text property="dataExecucao" readonly="true"
											style="background-color:#EFEFEF; border:0;" size="19"
											maxlength="19" /></td>
									</tr>

									<tr>
										<td class="style3"><strong>Data do Encerramento:</strong></td>

										<td><html:text property="dataEncerramento" readonly="true"
											style="background-color:#EFEFEF; border:0;" size="19"
											maxlength="19" /></td>
									</tr>

									<c:if
										test="${ConsultarDadosOrdemServicoActionForm.parecerEncerramento != null}">
										<tr>
											<td class="style3"><strong>Parecer do Encerramento:</strong>
											</td>

											<td><html:textarea property="parecerEncerramento"
												readonly="true" style="background-color:#EFEFEF; border:0;"
												cols="40" /></td>
										</tr>
									</c:if>

									<c:if
										test="${ConsultarDadosOrdemServicoActionForm.areaPavimentacao != null}">
										<tr>
											<td class="style3"><strong>&Aacute;rea
											Pavimenta&ccedil;&atilde;o:</strong></td>

											<td><html:text property="areaPavimentacao" readonly="true"
												style="background-color:#EFEFEF; border:0; text-align:right;"
												size="9" maxlength="9" /> <strong>m&sup2;</strong></td>
										</tr>
									</c:if>


									<tr>
										<td class="style3"><strong>Comercial Atualizado:</strong></td>

										<td><html:text property="comercialAtualizado" readonly="true"
											style="background-color:#EFEFEF; border:0;" size="5"
											maxlength="5" /></td>
									</tr>

									<tr>
										<td class="style3"><strong>Servi&ccedil;o Cobrado:</strong></td>

										<td><html:text property="servicoCobrado" readonly="true"
											style="background-color:#EFEFEF; border:0;" size="5"
											maxlength="5" /></td>
									</tr>

									<c:if
										test="${ConsultarDadosOrdemServicoActionForm.motivoNaoCobranca != null}">
										<tr>
											<td class="style3"><strong>Motivo da N&atilde;o Cobrado:</strong>
											</td>

											<td><html:text property="motivoNaoCobranca" readonly="true"
												style="background-color:#EFEFEF; border:0;" size="55"
												maxlength="55" /></td>
										</tr>
									</c:if>

									<c:if
										test="${ConsultarDadosOrdemServicoActionForm.motivoNaoCobranca == null}">
										<tr>
											<td class="style3"><strong>Percentual da Cobran&ccedil;a:</strong>
											</td>
											<td><html:text property="percentualCobranca" readonly="true"
												style="background-color:#EFEFEF; border:0; text-align:right;"
												size="10" maxlength="10" /> <strong>%</strong></td>
										</tr>

										<tr>
											<td class="style3"><strong>Valor Cobrado:</strong></td>
											<td><html:text property="valorCobrado" readonly="true"
												style="background-color:#EFEFEF; border:0; text-align:right;"
												size="10" maxlength="10" /></td>
										</tr>
									</c:if>

									<tr>
										<td class="style3"><strong>Motivo do Encerramento:</strong></td>
										<td><html:text
											property="motivoEncerramento" readonly="true"
											style="background-color:#EFEFEF; border:0;" size="40"
											maxlength="40" /></td>

									</tr>

									<tr>
										<td class="style3"><strong>Unidade do Encerramento:</strong></td>

										<td><html:text property="unidadeEncerramentoId"
											readonly="true" style="background-color:#EFEFEF; border:0;"
											size="4" maxlength="4" /> <html:text
											property="unidadeEncerramentoDescricao" readonly="true"
											style="background-color:#EFEFEF; border:0;" size="40"
											maxlength="40" /></td>

									</tr>

									<tr>
										<td class="style3"><strong>Usu&aacute;rio do Encerramento:</strong>
										</td>
										<td><html:text property="usuarioEncerramentoLogin"
											readonly="true" style="background-color:#EFEFEF; border:0;"
											size="2" maxlength="2" /> <html:text
											property="usuarioEncerramentoNome" readonly="true"
											style="background-color:#EFEFEF; border:0;" size="40"
											maxlength="40" /></td>
									</tr>

								</table>
								</td>
							</tr>

						</table>
						</div>

						</td>
					</tr>
				</c:if>
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
				<!-- FIM - Dados do Encerramento -->
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->

				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
				<!-- Dados da Execução do Serviço -->
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
				<c:if
					test="${ConsultarDadosOrdemServicoActionForm.possuiExecucaoServico == 'SIM'}">
					<tr>
						<td>
						<div id="layerHideExecucaoServico" style="display:block">
						<table width="100%" border="0" bgcolor="#99CCFF">
							<tr bgcolor="#99CCFF">
								<td align="center"><span class="style2"> <a
									href="javascript:extendeTabela('ExecucaoServico',true);" /> <b>Dados da Execução do Servi&ccedil;o</b></a></span></td>
							</tr>
						</table>
						</div>

						<div id="layerShowExecucaoServico" style="display:none">

						<table width="100%" border="0" bgcolor="#99CCFF">

							<tr bgcolor="#99CCFF">
								<td align="center"><span class="style2"> <a
									href="javascript:extendeTabela('ExecucaoServico',false);" /> <b>Dados da Execução do Servi&ccedil;o</b> </a> </span></td>
							</tr>

							<tr bgcolor="#cbe5fe">

								<td>
								<table border="0" width="100%">

									<tr>
										<td class="style3"><strong>Usuário da Execução:</strong></td>

										<td><html:text property="usuarioExecucaoId"
											readonly="true" style="background-color:#EFEFEF; border:0;"
											size="2" maxlength="2" /> <html:text
											property="usuarioExecucaoNome" readonly="true"
											style="background-color:#EFEFEF; border:0;" size="40"
											maxlength="40" /></td>
									</tr>

									<tr>
										<td class="style3"><strong>Leitura do Serviço:</strong>
										</td>

										<td><html:text
											property="leituraServico" readonly="true"
											style="background-color:#EFEFEF; border:0;" size="40"
											maxlength="40" /></td>
									</tr>

									<tr>
										<td class="style3"><strong>Tipo de Corte:</strong>
										</td>
										<td><html:text property="tipoCorteId"
											readonly="true" style="background-color:#EFEFEF; border:0;"
											size="2" maxlength="2" /> <html:text
											property="tipoCorteDescricao" readonly="true"
											style="background-color:#EFEFEF; border:0;" size="40"
											maxlength="40" /></td>
									</tr>
								</table>
								</td>
							</tr>

						</table>
						</div>

						</td>
					</tr>
				</c:if>
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
				<!-- FIM - Dados da Execução do Serviço -->
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->

				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
				<!-- Dados das Atividades -->
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
				<c:choose>

					<c:when
						test="${not empty ConsultarDadosOrdemServicoActionForm.colecaoOSAtividade}">
						<tr>
							<td>
							<div id="layerHideAtividade" style="display:block">
							<table width="100%" border="0" bgcolor="#99CCFF">
								<tr bgcolor="#99CCFF">
									<td align="center"><span class="style2"> <a
										href="javascript:extendeTabela('Atividade',true);" /> <b>Dados
									das Atividades da Ordem de Servi&ccedil;o</b> </a> </span></td>
								</tr>
							</table>
							</div>

							<div id="layerShowAtividade" style="display:none">

							<table width="100%" border="0" bgcolor="#99CCFF">

								<tr bgcolor="#99CCFF">
									<td align="center"><span class="style2"> <a
										href="javascript:extendeTabela('Atividade',false);" /> <b>Dados
									das Atividades da Ordem de Servi&ccedil;o</b> </a> </span></td>
								</tr>

								<tr bgcolor="#cbe5fe">

									<td>
									<table width="100%" bgcolor="#99CCFF">
										<tr bordercolor="#90c7fc" bgcolor="#90c7fc">
											<td width="50%">
											<div align="center"><strong>Atividade</strong></div>
											</td>
											<td width="25%">
											<div align="center"><strong></strong></div>
											</td>
											<td width="25%">
											<div align="center"><strong></strong></div>
											</td>
										</tr>
										<tr>
											<c:set var="count" value="0" />
											<logic:iterate id="helper"
												name="ConsultarDadosOrdemServicoActionForm"
												property="colecaoOSAtividade"
												type="gcom.atendimentopublico.ordemservico.bean.ObterDadosAtividadeIdOSHelper"
												scope="session">

												<c:set var="count" value="${count+1}" />
												<c:choose>
													<c:when test="${count%2 == '1'}">
														<tr bgcolor="#FFFFFF">
													</c:when>
													<c:otherwise>
														<tr bgcolor="#cbe5fe">
													</c:otherwise>
												</c:choose>
												<td bordercolor="#90c7fc">
												<div align="center"><bean:write name="helper"
													property="atividade.descricao" /></div>
												</td>
												<td bordercolor="#90c7fc">
												<div align="center"><c:if test="${helper.periodo==true}">
													<a
														href="javascript:abrirPopup('/gsan/exibirDetalharPeriodoExecucaoEquipePopupAction.do?numeroOS=${helper.idOS}&atividadeId=${helper.atividade.id}', 550, 735);">
													<img title="Consultar Períodos/Equipes"
														src="<bean:message key='caminho.imagens'/>relogioTransparente.gif"
														border="0"></a>
												</c:if></div>
												</td>
												<td bordercolor="#90c7fc">
												<div align="center"><c:if test="${helper.material==true}">
													<a
														href="javascript:abrirPopup('/gsan/exibirDetalharMaterialUtilizadoPopupAction.do?numeroOS=${helper.idOS}&atividadeId=${helper.atividade.id}', 550, 735);">
													<img title="Consultar Materiais"
														src="<bean:message key='caminho.imagens'/>marteloTransparente3.gif"
														border="0"></a>
												</c:if></div>
												</td>
												</logic:iterate>
										</tr>
									</table>
									</td>
								</tr>

							</table>
							
							</div>

							</td>
						</tr>
					</c:when>

				</c:choose>
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
				<!-- FIM - Dados das Atividades -->
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
				<!-- Rede Ramal Agua -->
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
				<logic:equal value="true" name="ConsultarDadosOrdemServicoActionForm" property="exibirDadosRedeRamalAgua">
					<tr>
						<td>
						<div id="layerHideRedeRamalAgua" style="display:block">
						<table width="100%" border="0" bgcolor="#99CCFF">
							<tr bgcolor="#99CCFF">
								<td align="center"><span class="style2"> <a
									href="javascript:extendeTabela('RedeRamalAgua',true);" />
									<b>Rede Ramal Água</b> </a> </span></td>
							</tr>
						</table>
						</div>

						<div id="layerShowRedeRamalAgua" style="display:none">

						<table width="100%" border="0" bgcolor="#99CCFF">

							<tr bgcolor="#99CCFF">
								<td align="center"><span class="style2"> <a
									href="javascript:extendeTabela('RedeRamalAgua',false);" />
									<b>Rede Ramal Água</b> </a> </span></td>
							</tr>

							<tr bgcolor="#cbe5fe">

								<td>
								<table border="0" width="100%">

			            		  <c:if test="${ordemServico.servicoTipo.indicadorCausaOcorrencia != 2}">
			            		    <tr>
			            			  <td width="20%"><strong>Causa:</strong></td>
							          <td> 
			        			    	 <c:if test="${ConsultarDadosOrdemServicoActionForm.descricaoCausaVazamentoAgua != null}">
			        			    	 	<html:text property="descricaoCausaVazamentoAgua" readonly="true"
														style="background-color:#EFEFEF; border:0;" size="20"
														maxlength="48" />
			        			    	 </c:if>
							          </td>
							        </tr>
			            		  </c:if>
									<tr>
										<td><strong>Rede/Ramal:</strong></td>
										<td>
										   <c:if test="${ConsultarDadosOrdemServicoActionForm.descricaoAgua != null}">
												<html:text property="descricaoAgua" readonly="true"
												style="background-color:#EFEFEF; border:0;" size="20"
												maxlength="48" />
											</c:if>
										</td>
									</tr>
									<tr>
										<td><strong>Diâmetro:</strong></td>
										<td>
										<html:text property="descricaoDiametroAgua" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="20"
													maxlength="48" />
										</td>
									</tr>
									<tr>
										  <td><strong>Material:</strong></td>
								          <td>
													<html:text property="descricaoMaterialAgua" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="20"
																maxlength="48" />
										  </td>
									</tr>
									<tr>
										<td><strong>Profundidade:</strong></td>
										<td>
											<html:text property="profundidadeAgua" readonly="true"
														style="background-color:#EFEFEF; border:0;" size="20"
														maxlength="48" />
										</td>
									</tr>
									<tr>
										<td><strong>Pressão:</strong></td>
										<td>
											<html:text property="pressaoAgua" readonly="true"
														style="background-color:#EFEFEF; border:0;" size="20"
														maxlength="48" />
										</td>
									</tr>
									<tr>
										<td><strong>Agente Externo:</strong></td>
										<td> 
											<html:text property="descricaoAgenteExterno" readonly="true"
														style="background-color:#EFEFEF; border:0;" size="20"
														maxlength="48" />
										</td>
									</tr>
								</table>
								</td>
							</tr>

						</table>
						</div>
						</td>
					</tr>
				</logic:equal>
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
				<!-- FIM - Rede Ramal Agua -->
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
				<!-- Rede Ramal Esgoto -->
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
				<logic:equal value="true" name="ConsultarDadosOrdemServicoActionForm" property="exibirDadosRedeRamalEsgoto">
					<logic:notEqual value="true" name="ConsultarDadosOrdemServicoActionForm" property="exibirDadosRedeRamalAgua">
					<tr>
						<td>
						<div id="layerHideRedeRamalEsgoto" style="display:block">
						<table width="100%" border="0" bgcolor="#99CCFF">
							<tr bgcolor="#99CCFF">
								<td align="center"><span class="style2"> <a
									href="javascript:extendeTabela('RedeRamalEsgoto',true);" />
									<b>Rede Ramal Esgoto</b> </a> </span></td>
							</tr>
						</table>
						</div>

						<div id="layerShowRedeRamalEsgoto" style="display:none">

						<table width="100%" border="0" bgcolor="#99CCFF">

							<tr bgcolor="#99CCFF">
								<td align="center"><span class="style2"> <a
									href="javascript:extendeTabela('RedeRamalEsgoto',false);" />
									<b>Rede Ramal Esgoto</b> </a> </span></td>
							</tr>

							<tr bgcolor="#cbe5fe">

								<td>
								<table border="0" width="100%">

		            		  <c:if test="${ordemServico.servicoTipo.indicadorCausaOcorrencia != 2}">
		            		    <tr>
		            			  <td width="20%"><strong>Causa:</strong></td>
						          <td> 
		        			    	 	<html:text property="descricaoCausaVazamentoEsgoto" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="20"
													maxlength="48" />
						          </td>
						        </tr>
		            		  </c:if>
									<tr>
										<td><strong>Rede/Ramal:</strong></td>
										<td>
										   <c:if test="${ConsultarDadosOrdemServicoActionForm.descricaoEsgoto != null}">
												<html:text property="descricaoEsgoto" readonly="true"
												style="background-color:#EFEFEF; border:0;" size="20"
												maxlength="48" />
											</c:if>
										</td>
									</tr>
									<tr>
										<td><strong>Diâmetro:</strong></td>
										<td>
										<html:text property="descricaoDiametroEsgoto" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="20"
													maxlength="48" />
										</td>
									</tr>
									<tr>
										  <td><strong>Material:</strong></td>
								          <td>
													<html:text property="descricaoMaterialEsgoto" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="20"
																maxlength="48" />
										  </td>
									</tr>
									<tr>
										<td><strong>Profundidade:</strong></td>
										<td>
											<html:text property="profundidadeEsgoto" readonly="true"
														style="background-color:#EFEFEF; border:0;" size="20"
														maxlength="48" />
										</td>
									</tr>
									<tr>
										<td><strong>Pressão:</strong></td>
										<td>
											<html:text property="pressaoEsgoto" readonly="true"
														style="background-color:#EFEFEF; border:0;" size="20"
														maxlength="48" />
										</td>
									</tr>
									<tr>
										<td><strong>Agente Externo:</strong></td>
										<td> 
											<html:text property="descricaoAgenteExterno" readonly="true"
														style="background-color:#EFEFEF; border:0;" size="20"
														maxlength="48" />
										</td>
									</tr>
								</table>
								</td>
							</tr>

						</table>
						</div>
						</td>
					</tr>
					</logic:notEqual>
				</logic:equal>
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
				<!-- FIM - Rede Ramal Esgoto -->
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
				<!-- Dados da Vala -->
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
			<c:if test="${ConsultarDadosOrdemServicoActionForm.comprimentoVala != null}">
					<tr>
						<td>
						<div id="layerHideDadosVala" style="display:block">
						<table width="100%" border="0" bgcolor="#99CCFF">
							<tr bgcolor="#99CCFF">
								<td align="center"><span class="style2"> <a
									href="javascript:extendeTabela('DadosVala',true);" />
									<b>Dados da Vala</b> </a> </span></td>
							</tr>
						</table>
						</div>

						<div id="layerShowDadosVala" style="display:none">

						<table width="100%" border="0" bgcolor="#99CCFF">

							<tr bgcolor="#99CCFF">
								<td align="center"><span class="style2"> <a
									href="javascript:extendeTabela('DadosVala',false);" />
									<b>Dados da Vala</b> </a> </span></td>
							</tr>
							<tr bgcolor="#cbe5fe">
								<td>																	
									<display:table class="dataTable" name="sessionScope.colecaoVala" sort="list" id="vala"  pagesize="15" excludedParams="" > 
									        		
											        <display:column property="numeroVala" title="Núm."/>											        											        											        
											        <display:column sortable="false" title="C x L x P">											        	
											        	<c:out value="${vala.numeroComprimento}" />		
											        	<c:out value=" x "/>
											        	<c:out value="${vala.numeroLargura}" />		
											        	<c:out value=" x "/>
											        	<c:out value="${vala.numeroProfundidade}" />								        		
											        </display:column>
											        											        
											        <display:column property="localOcorrencia.descricaoAbreviada" title="Loc. Ocor."/>
											        <display:column sortable="false" title="Pav.">
											        	<c:choose>
											        		<c:when test="${vala.localOcorrencia.indicadorCalcada == 1}">
											        			<c:out value="${vala.pavimentoCalcada.descricao}" />
											        		</c:when>
											        		<c:otherwise>
											        			<c:out value="${vala.pavimentoRua.descricao}" />
											        		</c:otherwise>
											        	</c:choose>
											        </display:column>
											        <display:column sortable="false" title="Ater.">
											        	<c:choose>
											        		<c:when test="${vala.indicadorAterro eq 1}">
											        			<c:out value="Sim"/>
											        		</c:when>
											        		<c:otherwise>
											        			<c:out value="Não"/>
											        		</c:otherwise>
											        	</c:choose>
											        </display:column>
											        	
											        
											        <display:column sortable="false" title="Ent.">
											        	<c:choose>
											        		<c:when test="${vala.indicadorEntulho eq 1}">
											        			<c:out value="Sim"/>
											        		</c:when>
											        		<c:otherwise>
											        			<c:out value="Não"/>
											        		</c:otherwise>
											        	</c:choose>
											        </display:column> 
											        
											        <display:column sortable="false" title="Qtd. Ent.">											        	
											        	<c:out value="${vala.quantidadeEntulho}" />											        		
											        </display:column>
											        
											        <display:column sortable="false" title="Med. Ent.">											        	
											        	<c:out value="${vala.entulhoMedida.descricaoAbreviada}" />											        		
											        </display:column>
											        
											         <display:column sortable="false" title="Tub.Ag. C x D">											        	
											        	<c:out value="${vala.numeroComprimentoTutulacaoAguaPluvial}" />		
											        	<c:out value=" x "/>
											        	<c:out value="${vala.numeroDiametroTutulacaoAguaPluvial}" />									        		
											        </display:column>
											        
											          
											        											         											    	
									        	</display:table>
									        	
								</td>
							</tr>
						</table>
						</div>
						</td>
					</tr>
				</c:if>
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
				<!-- FIM - Dados da Vala -->
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->				
				
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
				<!-- Dados do reparo solicitado -->
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->				
				<c:if test="${ConsultarDadosOrdemServicoActionForm.exibirDadosReparoOSPrincipal != null}">
					<tr>
						<td>
						<div id="layerHideDadosValaOSPrincipal" style="display:block">
						<table width="100%" border="0" bgcolor="#99CCFF">
							<tr bgcolor="#99CCFF">
								<td align="center"><span class="style2"> <a
									href="javascript:extendeTabela('DadosValaOSPrincipal',true);" />
									<b>Dados do Reparo Solicitado</b> </a> </span></td>
							</tr>
						</table>
						</div>

						<div id="layerShowDadosValaOSPrincipal" style="display:none">

						<table width="100%" border="0" bgcolor="#99CCFF">

							<tr bgcolor="#99CCFF">
								<td align="center"><span class="style2"> <a
									href="javascript:extendeTabela('DadosValaOSPrincipal',false);" />
									<b>Dados do Reparo Solicitado</b> </a> </span></td>
							</tr>
							<tr bgcolor="#cbe5fe">

								<td>
								<table border="0" width="100%">
		            		  	<tr>
		            			  <td width="20%"><strong>OS Principal:</strong></td>
						          <td> 
		        			    	 	<html:text property="idOSPrincipal" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="20"
													maxlength="48" />
						          </td>
						        </tr>						        
						        </table>
						        
						        <table border="0" width="100%">
		            		  	<tr>		            			  
						          <td> 
		        			    	 	<strong>Dados da Vala da OS Principal:</strong>														
									<display:table class="dataTable" name="sessionScope.colecaoValaOSPrincipal" sort="list" id="vala"  pagesize="15" excludedParams="" > 
									        		
											        <display:column property="numeroVala" title="Núm."/>											        											        											        
											        <display:column sortable="false" title="C x L x P">											        	
											        	<c:out value="${vala.numeroComprimento}" />		
											        	<c:out value=" x "/>
											        	<c:out value="${vala.numeroLargura}" />		
											        	<c:out value=" x "/>
											        	<c:out value="${vala.numeroProfundidade}" />								        		
											        </display:column>
											        											        
											        <display:column property="localOcorrencia.descricaoAbreviada" title="Loc. Ocor."/>
											        <display:column sortable="false" title="Pav.">
											        	<c:choose>
											        		<c:when test="${vala.localOcorrencia.indicadorCalcada == 1}">
											        			<c:out value="${vala.pavimentoCalcada.descricao}" />
											        		</c:when>
											        		<c:otherwise>
											        			<c:out value="${vala.pavimentoRua.descricao}" />
											        		</c:otherwise>
											        	</c:choose>
											        </display:column>
											        <display:column sortable="false" title="Ater.">
											        	<c:choose>
											        		<c:when test="${vala.indicadorAterro eq 1}">
											        			<c:out value="Sim"/>
											        		</c:when>
											        		<c:otherwise>
											        			<c:out value="Não"/>
											        		</c:otherwise>
											        	</c:choose>
											        </display:column>
											        	
											        
											        <display:column sortable="false" title="Ent.">
											        	<c:choose>
											        		<c:when test="${vala.indicadorEntulho eq 1}">
											        			<c:out value="Sim"/>
											        		</c:when>
											        		<c:otherwise>
											        			<c:out value="Não"/>
											        		</c:otherwise>
											        	</c:choose>
											        </display:column> 
											        
											        <display:column sortable="false" title="Qtd. Ent.">											        	
											        	<c:out value="${vala.quantidadeEntulho}" />											        		
											        </display:column>
											        
											        <display:column sortable="false" title="Med. Ent.">											        	
											        	<c:out value="${vala.entulhoMedida.descricaoAbreviada}" />											        		
											        </display:column>
											        
											         <display:column sortable="false" title="Tub.Ag. C x D">											        	
											        	<c:out value="${vala.numeroComprimentoTutulacaoAguaPluvial}" />		
											        	<c:out value=" x "/>
											        	<c:out value="${vala.numeroDiametroTutulacaoAguaPluvial}" />									        		
											        </display:column>
											        
											          
											        											         											    	
									        	</display:table>
						          </td>
						        </tr>						        
						        </table>
						        
						        </td>
						    </tr>														
						</table>
						</div>
						</td>
					</tr>
				</c:if>
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
				<!-- FIM - Dados do reparo solicitado -->
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
				<!-- -------------asdfasdfsafd---- -->
				<tr>
					<td>
					<table width="100%">
					<tr>
						<div align="left" style="float: left;">

							<input type="button" name="Submit22342"
								class="bottonRightCol" value="Voltar"
								onclick="javascript:voltar('${caminhoRetornoOS}');">
						</div>
						<c:if test="${caminhoRetornoOS != 'filtrarDocumentosCobrancaAction.do'}">
							<div align="right"><strong>
							
<!-- 						<input type="button" name="SubmitImprimirParecerOS" -->
<!-- 											class="bottonRightCol" value="Imprimir Parecer OS" -->
<!-- 											onclick="imprimirParecerOS();"> -->

							<c:if
								test="${ConsultarDadosOrdemServicoActionForm.permiteGerarOSReparo == '1'}">
								<input name="SubmitGerarOS" type="button" class="bottonRightCol"
									value="Gerar OS Reparo" onclick="gerarOS();">

							</c:if> 				
							<c:if
								test="${ConsultarDadosOrdemServicoActionForm.permiteGerarOSReparo != '1'}">
								<input name="SubmitGerarOS" type="button" class="bottonRightCol"
									value="Gerar OS Reparo"
									disabled="disabled">

							</c:if>

							<c:if
								test="${ConsultarDadosOrdemServicoActionForm.situacaoOSId == '2'}">
								<input name="SubmitImprimirParecerOS" type="button" class="bottonRightCol"
									value="Imprimir Parecer" onclick="imprimirParecerOS();">

							</c:if> 				
							<c:if
								test="${ConsultarDadosOrdemServicoActionForm.situacaoOSId != '2'}">
								<input name="SubmitImprimirParecerOS" type="button" class="bottonRightCol"
									value="Imprimir Parecer"
									disabled="disabled">

							</c:if> 
											
							<c:if
								test="${ConsultarDadosOrdemServicoActionForm.permiteTramiteIndependente == '1'}">
								<input name="btnTramitar" type="button" class="bottonRightCol"
									value="Tramitar" onclick="javascript:tramitar();">

							</c:if> 
							<c:if
								test="${ConsultarDadosOrdemServicoActionForm.permiteTramiteIndependente != '1'}">
								<input name="btnTramitar" type="button" class="bottonRightCol"
									value="Tramitar" onclick="javascript:tramitar();"
									disabled="disabled">

							</c:if>
							
							<logic:present name="achouDadosProgramacao"> 
									<input name="btnAtualizar" type="button" class="bottonRightCol"
										value="Atualizar" onclick="javascript:atualizar();"
										disabled="disabled">
							</logic:present>
							<logic:notPresent name="achouDadosProgramacao"> 
								<c:if
									test="${ConsultarDadosOrdemServicoActionForm.situacaoOSId != '2'}">
									<input name="btnAtualizar" type="button" class="bottonRightCol"
										value="Atualizar" onclick="javascript:atualizar();">
								</c:if> 
								<c:if
									test="${ConsultarDadosOrdemServicoActionForm.situacaoOSId == '2'}">
									<input name="btnAtualizar" type="button" class="bottonRightCol"
										value="Atualizar" onclick="javascript:atualizar();"
										disabled="disabled">
								</c:if>
							
							</logic:notPresent> 
							<c:if
								test="${ConsultarDadosOrdemServicoActionForm.situacaoOSId != '2'
					                  							&& ConsultarDadosOrdemServicoActionForm.situacaoOSId != '4'}">
								<input name="btnEncerrar" type="button" class="bottonRightCol"
									value="Encerrar" onclick="javascript:encerrar();">

							</c:if> 
							<c:if
								test="${ConsultarDadosOrdemServicoActionForm.situacaoOSId == '2'
					                  							|| ConsultarDadosOrdemServicoActionForm.situacaoOSId == '4'}">
								<input name="btnEncerrar" type="button" class="bottonRightCol"
									value="Encerrar" onclick="javascript:encerrar();"
									disabled="disabled">

							</c:if> 
							
          					<c:if
								test="${ConsultarDadosOrdemServicoActionForm.emissaoOSHabilitada == '1'}">
								<input name="btnImprimir" type="button"
									class="bottonRightCol" value="Imprimir"
									onclick="imprimirOS();">
							</c:if>
							 
          					<c:if
								test="${ConsultarDadosOrdemServicoActionForm.emissaoOSHabilitada == '2'}">
								<input name="btnImprimir" type="button"
									class="bottonRightCol" value="Imprimir"
									onclick="imprimirOS();" disabled="disabled">
							</c:if> 
							
							

							</strong></div>
							
							</td>
							</c:if>
						</tr>

					</table>

					</td>

				</tr>

			</table>
			</td>
		</tr>
	</table>
	<!-- Fim do Corpo - Leonardo Regis -->
	<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>

</body>
<!-- ordem_servico_dados_consultar.jsp -->
</html:html>
