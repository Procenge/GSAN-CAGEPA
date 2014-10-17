<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<!--

function fechar(){
		window.close();
-->

<%@ page import="gcom.atendimentopublico.ligacaoagua.bean.HistoricoManutencaoLigacaoHelper"%>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>


<script language="JavaScript">

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

   	var form = document.forms[0];

    if (tipoConsulta == 'imovel') {
      form.idImovelHistoricoAlteracao.value = codigoRegistro;
      form.matriculaImovelHistoricoAlteracao.value = descricaoRegistro;
      form.matriculaImovelHistoricoAlteracao.style.color = "#000000";
	  form.action = 'consultarImovelWizardAction.do?action=exibirConsultarImovelHistoricoAlteracaoAction&indicadorNovo=OK'
	  form.submit();
    }
    
}

function limparForm(){
   	var form = document.forms[0];
	form.action = 'consultarImovelWizardAction.do?action=exibirConsultarImovelHistoricoAlteracaoAction&limparForm=OK'
	form.submit();	
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

function consultarOs(idOs){
	var url = 'exibirConsultarDadosOrdemServicoPopupAction.do?numeroOS='+idOs;
	abrirPopup(url);
}

function consultarDocCobranca(idDocCobranca){
	var url = 'exibirDocumentoCobrancaItemAction.do?cobrancaDocumentosID='+idDocCobranca;
	abrirPopup(url, 400, 800);
}

	
</script>


</head>
<body leftmargin="5" topmargin="5">


<html:form action="/exibirConsultarImovelAction.do"
	name="ConsultarImovelActionForm"
	type="gcom.gui.cadastro.imovel.ConsultarImovelActionForm" method="post"
	onsubmit="return validateConsultarImovelActionForm(this);">	


	<jsp:include
		page="/jsp/util/wizard/navegacao_abas_wizard_consulta.jsp?numeroPagina=11"/>

	<logic:present name="montarPopUp">
	 <table width=800" border="0" cellspacing="5" cellpadding="0">
		<tr>
		<td valign="top" class="centercoltext"><!--Início Tabela Reference a Páginação da Tela de Processo-->
			
			<p>&nbsp;</p>			
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">&nbsp;</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>						
				</tr>				
			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->		
	
	</logic:present>
	
	<logic:notPresent name="montarPopUp">	
	
		<%@ include file="/jsp/util/cabecalho.jsp"%>
		<%@ include file="/jsp/util/menu.jsp"%>	
		
		<table width="800" border="0" cellspacing="5" cellpadding="0">
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
			
			<td valign="top" class="centercoltext"><!--Início Tabela Reference a Páginação da Tela de Processo-->
			
			<p>&nbsp;</p>			
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">&nbsp;</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>						
				</tr>				
			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
	
	</logic:notPresent>
		<p>&nbsp;</p>

		<!-- Início do Corpo - Vitor Hora  -->
		<table width="100%" border="0" >
				<tr>
					<td colspan="4">
					<table width="100%" align="center" bgcolor="#99CCFF" border="0">
						<tr>
							<td align="center"><strong>Dados do Imóvel</strong></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">
							<table width="100%" border="0">
								<tr>
									<td bordercolor="#000000" width="25%"><strong>Im&oacute;vel:<font
										color="#FF0000">*</font></strong></td>
									<td width="75%" colspan="3"><html:text
										property="idImovelHistoricoAlteracao" maxlength="9" size="9"
										onkeypress="validaEnterComMensagem(event, 'consultarImovelWizardAction.do?action=exibirConsultarImovelHistoricoAlteracaoAction&indicadorNovo=OK&limparForm=S','idImovelHistoricoAlteracao','Im&oacute;vel');"/> 
									<a
										href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
									<img width="23" height="21"
										src="<bean:message key="caminho.imagens"/>pesquisa.gif"
										border="0" /></a> <logic:present
										name="idImovelHistoricoAlteracaoNaoEncontrado" scope="request">
										<html:text property="matriculaImovelHistoricoAlteracao" size="40"
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #ff0000" />

									</logic:present> <logic:notPresent
										name="idImovelHistoricoAlteracaoNaoEncontrado" scope="request">
										<logic:present name="valorMatriculaImovelHistoricoAlteracao"
											scope="request">
											<html:text property="matriculaImovelHistoricoAlteracao"
												size="40" readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:present>
										<logic:notPresent name="valorMatriculaImovelHistoricoAlteracao"
											scope="request">
											<html:text property="matriculaImovelHistoricoAlteracao"
												size="40" readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:notPresent>
									</logic:notPresent> <a href="javascript:limparForm();"> <img
										src="<bean:message key="caminho.imagens"/>limparcampo.gif"
										border="0" title="Apagar" /></a></td>

								</tr>
								<tr>
									<td height="10">
									<div class="style9"><strong>Situação de Água:</strong></div>
									</td>
									<td><html:text property="situacaoAguaHistoricoAlteracao"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="15" maxlength="15" /></td>
									<td width="90"><strong>Situação de Esgoto:</strong></td>
									<td width="120"><html:text
										property="situacaoEsgotoHistoricoAlteracao" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="15" maxlength="15" /></td>
								</tr>								
							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>
			<tr>
				<td colspan="3" height="10"></td>
			</tr>

				<tr bgcolor="#cbe5fe">
					<td>
						<div id="layerHideDadosAlteracoes" style="display:block">
							<table width="600" bgcolor="#90c7fc" border="0" style="">
								<tr bordercolor="#79bbfd">
									<td align="center" bgcolor="#79bbfd" colspan="20">
										<a href="javascript:extendeTabela('DadosAlteracoes',true);" />
										   <b>Dados das Altera&ccedil;&otilde;es</b>
										</a>
									</td>
								</tr>
							</table>
						</div>
						
						<div id="layerShowDadosAlteracoes" style="display:none">						
							<table width="600" bgcolor="#90c7fc" border="0" style="">
								<tr bordercolor="#79bbfd">
									<td align="center" bgcolor="#79bbfd" colspan="20">
										<a href="javascript:extendeTabela('DadosAlteracoes',false);" />
										   <b>Dados das Altera&ccedil;&otilde;es</b>
										</a>									
									</td>
								</tr>						
														
								<tr bordercolor="#000000">							
								    <td width="15%" bgcolor="#90c7fc">
										<div align="center"><font
													color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Data Realiza&ccedil;&atilde;o</strong></font></div>
									</td>
									<td width="20%" bgcolor="#90c7fc">
										<div align="center"><font
													color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Descri&ccedil;&atilde;o da Opera&ccedil;&atilde;o</strong></font></div>
									</td>
									<td width="13%" bgcolor="#90c7fc">
										<div align="center"><font
													color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Usu&aacute;rio</strong></font></div>
									</td>							
									<td width="13%" bgcolor="#90c7fc">
										<div align="center"><font
													color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Tipo Altera&ccedil;&atilde;o</strong></font></div>
									</td>
									<td width="13%" bgcolor="#90c7fc">
										<div align="center"><font
													color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Campo da Tabela</strong></font></div>
									</td>
									<td width="13%" bgcolor="#90c7fc">
										<div align="center"><font
													color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Conte&uacute;do Anterior</strong></font></div>
									</td>
									<td width="13%" bgcolor="#90c7fc">
										<div align="center"><font
													color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Conte&uacute;do Atual</strong></font></div>
									</td>
									
								</tr>
								<logic:present name="colecaoExibirHistoricoAlteracao">
									<%int cont = 0;%>
									<logic:iterate name="colecaoExibirHistoricoAlteracao"
										id="colecaoExibirHistoricoAlteracao">
										<%cont = cont + 1;
											if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
											<%} else {	%>
										<tr bgcolor="#FFFFFF">
											<%}%>									
											<td width="14%" align="center">${colecaoExibirHistoricoAlteracao.dataRealizacaoAlteracao}</td>	
											<td width="16%" align="center">${colecaoExibirHistoricoAlteracao.descricaoOperacaoAlteracao}</td>	
											<td width="16%" align="center">${colecaoExibirHistoricoAlteracao.usuarioOperacaoAlteracao}</td>									
											<td width="16%" align="center">${colecaoExibirHistoricoAlteracao.tipoAlteracao}</td>	
											<td width="16%" align="center">${colecaoExibirHistoricoAlteracao.campoTabelaAlteracao}</td>	
											<td width="25%" align="center">${colecaoExibirHistoricoAlteracao.conteudoAnteriorAlteracao}</td>	
											<td width="25%" align="center">${colecaoExibirHistoricoAlteracao.conteudoAtualAlteracao}</td>																
											
										</tr>
									</logic:iterate>
								</logic:present>
							</table>
						</div>
					</td>
				</tr>
				
				<tr>
					<td colspan="3" height="20"></td>
				</tr>
	
				<tr bgcolor="#cbe5fe">
					<td>
						<div id="layerHideHistoricoManutencao" style="display:block">
							<table width="600" border="0">
								<tr>
									<td colspan="4">
										<table width="100%" align="center" bgcolor="#90c7fc" border="0">
											<tr bordercolor="#79bbfd">											
												<td colspan="9" align="center" bgcolor="#79bbfd">
													<a href="javascript:extendeTabela('HistoricoManutencao',true);" />
													   <b>Manutenções da Ligação de Água</b>
													</a>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</div>

						<div id="layerShowHistoricoManutencao" style="display:none">
							<table width="600" border="0">
								<tr>
									<td colspan="4">
										<table width="100%" align="center" bgcolor="#90c7fc" border="0">
											<tr bordercolor="#79bbfd">
												<td colspan="9" align="center" bgcolor="#79bbfd">
													<a href="javascript:extendeTabela('HistoricoManutencao',false);" />
													   <b>Manutenções da Ligação de Água</b>
													</a>
												</td>
											</tr>
											
											<tr bordercolor="#000000">
												<td width="11%" bgcolor="#90c7fc" align="center">
												<div class="style9"><font color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <strong>Imóvel</strong> </font></div>
												</td>
												<td width="11%" bgcolor="#90c7fc" align="center">
												<div class="style9"><font color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <strong>Dt.Emissão</strong> </font></div>
												</td>
												<td width="11%" bgcolor="#90c7fc" align="center">
												<div class="style9"><font color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <strong>Doc/OS</strong> </font></div>
												</td>
												<td width="11%" bgcolor="#90c7fc" align="center">
												<div class="style9"><font color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <strong>Doc.Cobr.</strong></font></div>
												</td>
												<td width="11%" bgcolor="#90c7fc" align="center">
												<div class="style9"><font color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <strong>Sit. Doc.</strong></font>
												</div>
												</td>
												<td width="11%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Dt.Sit.Doc.</strong>
												</font></td>
												<td width="11%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Serv.Associado</strong>
												</font></td>
												<td width="11%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>OS</strong>
												</font></td>
												<td width="11%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Sit. OS</strong>
												</font></td>
											</tr>
											
											<!--conteúdo da tabela-->
											<%int cont = 0;%>
											<tr>
												<td colspan="9">
													<logic:notEmpty name="colecaoHistoricoManutencaoLigacao">
														<logic:iterate name="colecaoHistoricoManutencaoLigacao" id="historicoManutencaoLigacao"
															type="HistoricoManutencaoLigacaoHelper">
														  
															<%cont = cont + 1;
									                                 if (cont % 2 == 0) {%>
															<tr bgcolor="#cbe5fe">
																<%} else {%>
															<tr bgcolor="#FFFFFF">
																<%}%>
																<td width="11%" align="left">
																	<div class="style9"><font color="#000000"
																		style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																		<bean:write name="historicoManutencaoLigacao" property="imovelId" />
																	    </font>
																	</div>
																</td>
																<td width="11%" align="left">
																	<div class="style9"><font color="#000000"
																		style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																		<bean:write name="historicoManutencaoLigacao" property="dataEmissao" />
																	    </font>
																	</div>
																</td>
																<td width="11%" align="left">
																	<div class="style9"><font color="#000000"
																		style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																		<bean:write name="historicoManutencaoLigacao" property="docOS" />
																	    </font>
																	</div>
																</td>
																
																<logic:notEmpty name="historicoManutencaoLigacao" property="descricaoDocumentoTipo">												
																	<td width="11%" align="left" title="${historicoManutencaoLigacao.descricaoDocumentoTipo}">
																		<div class="style9"><font color="#000000"
																			style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																			<logic:notEmpty name="historicoManutencaoLigacao" property="documentoCobranca">
																				<a href="javascript:consultarDocCobranca('<bean:write name="historicoManutencaoLigacao" property="documentoCobranca"/>');">
																				    <bean:write name="historicoManutencaoLigacao" property="documentoCobranca" />
																				</a>																														
																			</logic:notEmpty>
																		    </font>
																		</div>
																	</td>
																</logic:notEmpty>
																<logic:empty name="historicoManutencaoLigacao" property="descricaoDocumentoTipo">
																	<td width="11%" align="left">
																		<div class="style9"><font color="#000000"
																			style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																			<logic:notEmpty name="historicoManutencaoLigacao" property="documentoCobranca">
																				<a href="javascript:consultarDocCobranca('<bean:write name="historicoManutencaoLigacao" property="documentoCobranca"/>');">
																				    <bean:write name="historicoManutencaoLigacao" property="documentoCobranca" />
																				</a>																														
																			</logic:notEmpty>
																		    </font>
																		</div>
																	</td>
																</logic:empty>
																
																
																<logic:notEmpty name="historicoManutencaoLigacao" property="descricaoSituacaoAcao">												
																	<td width="11%" align="left" title="${historicoManutencaoLigacao.descricaoSituacaoAcao}">
																		<div class="style9"><font color="#000000"
																			style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																			<bean:write name="historicoManutencaoLigacao" property="situacaoDocumento" />
																		    </font>
																		</div>
																	</td>												
																</logic:notEmpty>
																<logic:empty name="historicoManutencaoLigacao" property="descricaoSituacaoAcao">
																	<td width="11%" align="left">
																		<div class="style9"><font color="#000000"
																			style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																			<bean:write name="historicoManutencaoLigacao" property="situacaoDocumento" />
																		    </font>
																		</div>
																	</td>
																</logic:empty>
																<td width="11%" align="left">
																	<div class="style9"><font color="#000000"
																		style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																		<bean:write name="historicoManutencaoLigacao" property="dataSituacaoDocumento" />
																	    </font>
																	</div>
																</td>
																<logic:notEmpty name="historicoManutencaoLigacao" property="hintServicoAssociado">												
																	<td width="11%" align="left" title="${historicoManutencaoLigacao.hintServicoAssociado}">
																		<div class="style9"><font color="#000000"
																			style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																			<logic:notEmpty name="historicoManutencaoLigacao" property="servicoAssociado">
																				<a href="javascript:consultarOs('<bean:write name="historicoManutencaoLigacao" property="servicoAssociado"/>');">
																				    <bean:write name="historicoManutencaoLigacao" property="servicoAssociado" />
																				</a>																														
																			</logic:notEmpty>															
																		    </font>
																		</div>
																	</td>
																</logic:notEmpty>
																<logic:empty name="historicoManutencaoLigacao" property="hintServicoAssociado">
																	<td width="11%" align="left">
																		<div class="style9"><font color="#000000"
																			style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																			<logic:notEmpty name="historicoManutencaoLigacao" property="servicoAssociado">
																				<a href="javascript:consultarOs('<bean:write name="historicoManutencaoLigacao" property="servicoAssociado"/>');">
																				    <bean:write name="historicoManutencaoLigacao" property="servicoAssociado" />
																				</a>																														
																			</logic:notEmpty>															
																		    </font>
																		</div>
																	</td>
																</logic:empty>																<logic:notEmpty name="historicoManutencaoLigacao" property="descricaoServicoTipo">												
																	<td width="11%" align="left" title="${historicoManutencaoLigacao.descricaoServicoTipo}">
																		<div class="style9"><font color="#000000"
																			style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																			<logic:notEmpty name="historicoManutencaoLigacao" property="ordemServico">
																				<a href="javascript:consultarOs('<bean:write name="historicoManutencaoLigacao" property="ordemServico"/>');">
																				    <bean:write name="historicoManutencaoLigacao" property="ordemServico" />
																				</a>																														
																			</logic:notEmpty>															
																		    </font>
																		</div>
																	</td>
																</logic:notEmpty>
																<logic:empty name="historicoManutencaoLigacao" property="descricaoServicoTipo">
																	<td width="11%" align="left">
																		<div class="style9"><font color="#000000"
																			style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																			<logic:notEmpty name="historicoManutencaoLigacao" property="ordemServico">
																				<a href="javascript:consultarOs('<bean:write name="historicoManutencaoLigacao" property="ordemServico"/>');">
																				    <bean:write name="historicoManutencaoLigacao" property="ordemServico" />
																				</a>																														
																			</logic:notEmpty>															
																		    </font>
																		</div>
																	</td>
																</logic:empty>
																<logic:notEmpty name="historicoManutencaoLigacao" property="hintSituacaoOS">												
																	<td width="11%" align="left" title="${historicoManutencaoLigacao.hintSituacaoOS}">
																		<div class="style9"><font color="#000000"
																			style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																			<bean:write name="historicoManutencaoLigacao" property="situacaoOS" />
																		    </font>
																		</div>
																	</td>												</logic:notEmpty>
																<logic:empty name="historicoManutencaoLigacao" property="hintSituacaoOS">
																	<td width="11%" align="left">
																		<div class="style9"><font color="#000000"
																			style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																			<bean:write name="historicoManutencaoLigacao" property="situacaoOS" />
																		    </font>
																		</div>
																	</td>
																</logic:empty>
															</tr>									
														</logic:iterate>
													</logic:notEmpty>
												</td>
											</tr>
										</table>
										</td>
									</tr>													
							</table>
						</div>												
					</td>
				</tr>
			</table>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
				
		<table width="100%" border="0">
			<tr>
				<td colspan="2">
				<div align="right"><jsp:include
					page="/jsp/util/wizard/navegacao_botoes_wizard_consulta.jsp?numeroPagina=11" /></div>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
</table>
<logic:notPresent name="montarPopUp">	
		<%@ include file="/jsp/util/rodape.jsp"%>
</logic:notPresent>
</html:form>
</body>
<!-- imovel_consultar_historico_alteracao.jsp -->
</html:html>
