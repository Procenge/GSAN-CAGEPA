<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gcom.cobranca.CobrancaSituacaoHistorico"%>
<%@ page import="gcom.faturamento.FaturamentoSituacaoHistorico"%>
<%@ page import="gcom.util.Util"%>
<%@ page import="gcom.arrecadacao.ContratoDemandaConsumo" %>

<head>
<html:html>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="BairroActionForm" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<script language="JavaScript">
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	
	   	var form = document.forms[0];
	
	    if (tipoConsulta == 'imovel') {
	      form.idImovelDadosComplementares.value = codigoRegistro;
	      form.matriculaImovelDadosComplementares.value = descricaoRegistro;
	      form.matriculaImovelDadosComplementares.style.color = "#000000";
		  form.action = 'consultarImovelWizardAction.do?action=exibirConsultarImovelDadosComplementaresAction&indicadorNovo=OK'
		  form.submit();
	    }
	}

	function limparForm(){
	   	var form = document.forms[0];
		form.action = 'consultarImovelWizardAction.do?action=exibirConsultarImovelDadosComplementaresAction&limparForm=OK'
		form.submit();
	}
	
</script>
</head>

<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('idImovelDadosComplementares')">

<html:form action="/exibirConsultarImovelAction.do"
	name="ConsultarImovelActionForm"
	type="gcom.gui.cadastro.imovel.ConsultarImovelActionForm" method="post"
	onsubmit="return validateConsultarImovelActionForm(this);">

	<jsp:include page="/jsp/util/wizard/navegacao_abas_wizard_consulta.jsp?numeroPagina=2" />

	
	<logic:present name="montarPopUp">
	 <table width="800" border="0" cellspacing="5" cellpadding="0">
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

			<!--Fim Tabela Reference a Páginação da Tela de Processo-->

			<p>&nbsp;</p>
			<table border="0" width="100%">
				<tr>
					<td colspan="4">
					<table align="center" bgcolor="#99ccff" border="0" width="100%">
						<tr>
							<td align="center"><strong>Dados do Im&oacute;vel</strong></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td align="center" width="100%">
							<table border="0" width="100%">
								<tr>
									<td bordercolor="#000000" width="25%"><strong>Im&oacute;vel:<font
										color="#FF0000">*</font></strong></td>
									<td width="75%" colspan="3"><html:text
										property="idImovelDadosComplementares" maxlength="9" size="9"
										onkeypress="validaEnterComMensagem(event, 'consultarImovelWizardAction.do?action=exibirConsultarImovelDadosComplementaresAction&indicadorNovo=OK','idImovelDadosComplementares','Im&oacute;vel');"/> 
									<a
										href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
									<img width="23" height="21"
										src="<bean:message key="caminho.imagens"/>pesquisa.gif"
										border="0" /></a> <logic:present
										name="idImovelDadosComplementaresNaoEncontrado"
										scope="request">
										<html:text property="matriculaImovelDadosComplementares"
											size="40" readonly="true"
											style="background-color:#EFEFEF; border:0; color: #ff0000" />
									</logic:present> <logic:notPresent
										name="idImovelDadosComplementaresNaoEncontrado"
										scope="request">
										<logic:present name="valorMatriculaImovelDadosComplementares"
											scope="request">
											<html:text property="matriculaImovelDadosComplementares"
												size="40" readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:present>
										<logic:notPresent
											name="valorMatriculaImovelDadosComplementares"
											scope="request">
											<html:text property="matriculaImovelDadosComplementares"
												size="40" readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:notPresent>
									</logic:notPresent> <a href="javascript:limparForm();"> <img
										src="<bean:message key="caminho.imagens"/>limparcampo.gif"
										border="0" title="Apagar" /></a></td>
								</tr>
								<tr>
									<td height="10">
									<div class="style9"><strong>Situa&ccedil;&atilde;o de
									&Aacute;gua:</strong></div>
									</td>

									<td><html:text property="situacaoAguaDadosComplementares"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="15" maxlength="15" /></td>
									<td width="96"><strong>Situação de Esgoto:</strong></td>
									<td width="120"><html:text
										property="situacaoEsgotoDadosComplementares" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="15" maxlength="15" /></td>

								</tr>
							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>
				<logic:present name="exibirDataValidadeTarifaTemporaria" scope="request">
					<tr>
						<td align="left" height="10" width="28%">
						<div class="style9"><strong>Tarifa de Consumo:</strong></div>
						</td>
	
						<td align="left" width="22%">
	
						<div class="style9"><html:text
							property="tarifaConsumoDadosComplementares" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000"
							size="20" maxlength="20" /></div>
						</td>
						
						<td align="left" width="32%"><strong>Data de Validade:</strong></td>
						<td align="left" width="18%"><html:text
							property="dataValidadeTarifaConsumo"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000"
							size="20" maxlength="20" /></td>
					</tr>
	
					<tr>
						<td align="left" width="32%"><strong>Qtd. de Retifica&ccedil;&otilde;es:</strong></td>
						<td align="left" width="18%"><html:text
							property="quantidadeRetificacoesDadosComplementares"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000"
							size="20" maxlength="20" /></td>
	
					</tr>
				</logic:present>
				
				<logic:notPresent name="exibirDataValidadeTarifaTemporaria" scope="request">
					<tr>
						<td align="left" height="10" width="28%">
						<div class="style9"><strong>Tarifa de Consumo:</strong></div>
						</td>
	
						<td align="left" width="22%">
	
						<div class="style9"><html:text
							property="tarifaConsumoDadosComplementares" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000"
							size="20" maxlength="20" /></div>
						</td>
						
						<td align="left" width="32%"><strong>Qtd. de Retifica&ccedil;&otilde;es:</strong></td>
						<td align="left" width="18%"><html:text
							property="quantidadeRetificacoesDadosComplementares"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000"
							size="20" maxlength="20" /></td>
					</tr>
				</logic:notPresent>

				<tr>
					<td align="left"><strong>Qtd. de Cortes:</strong></td>
					<td align="left"><html:text
						property="quantidadeCortes"
						readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000"
						size="20" maxlength="20" /></td>

					<td align="left"><strong>Qtd. de Cortes Administrativos:</strong></td>
					<td align="left"><html:text
						property="quantidadeCortesAdministrativos"
						readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000"
						size="20" maxlength="20" /></td>
				</tr>

				<tr>
					<td align="left"><strong>Qtd. de Supressões:</strong></td>
					<td align="left"><html:text
						property="quantidadeSupressoes"
						readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000"
						size="20" maxlength="20" /></td>

					<td align="left"><strong>Qtd. de Parcelamentos:</strong></td>
					<td align="left"><html:text
						property="quantidadeParcelamentosDadosComplementares"
						readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000"
						size="20" maxlength="20" /></td>

				</tr>

				<tr>
					<td align="left"><strong>Qtd. de Reparcelamentos:</strong></td>
					<td align="left"><html:text
						property="quantidadeReparcelamentoDadosComplementares"
						readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000"
						size="20" maxlength="20" /></td>

					<td align="left"><strong>Qtd. de Reparcelamentos Consecutivos:</strong></td>
					<td align="left"><html:text
						property="quantidadeReparcelamentoConsecutivosDadosComplementares"
						readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000"
						size="20" maxlength="20" /></td>
				</tr>

				<tr>

					<td align="left"><strong>Situa&ccedil;&atilde;o de Cobran&ccedil;a:</strong></td>

					<td colspan="3" align="left"><html:text
						property="situacaoCobrancaDadosComplementares" readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000"
						size="50" maxlength="50" /></td>

				</tr>
				
                <tr> 
                  	<td align="left"><strong>Funcionário Resp.:</strong></td>
                  	<td colspan="3">
						<html:text property="idFuncionario" readonly="true"
							style="background-color:#EFEFEF; border:0;" size="4"
							maxlength="4" />
						
						<html:text property="nomeFuncionario" readonly="true"
							style="background-color:#EFEFEF; border:0;" size="60"
							maxlength="40" />
					</td>
             	</tr>
				

<!--  
				<tr>

					<td align="left" height="10">
					<div class="style9"><strong>Ocorr&ecirc;ncia de Cadastro:</strong></div>
					</td>

					<td colspan="3" align="left">
					<div class="style9"><html:text
						property="descricaoOcorrenciaDadosComplementares" readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000"
						size="50" maxlength="50" /></div>
					</td>

				</tr>

				<tr>

					<td align="left"><strong>Anormalidade de Elo:</strong></td>

					<td colspan="3" align="left" height="10"><html:text
						property="descricaoAnormalidadeDadosComplementares"
						readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000"
						size="50" maxlength="50" /></td>

				</tr>
-->


								
				<tr>
					<td colspan="7" align="left">
				    <table bgcolor="#90c7fc" width="100%" border="0">
					    	<tr>	
								<td colspan="7" align="center" bgcolor="#79bbfd"><strong>Situa&ccedil;&otilde;es de Cobran&ccedil;a</strong></td>
							</tr>
							<tr bgcolor="#90c7fc" bordercolor="#000000">
	
								<td align="center" bgcolor="#90c7fc" width="27%">
								<div class="style9"><font style="font-size: 9px;" color="#000000"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Situa&ccedil;&atilde;o</strong></font></div>
								</td>
	
								<td align="center" bgcolor="#90c7fc" width="15%">
								<div class="style9"><font style="font-size: 9px;" color="#000000"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Per&iacute;odo Refr&ecirc;ncia</strong> </font></div>
								</td>
	
								<td align="center" bgcolor="#90c7fc" width="15%">
								<div class="style9"><font style="font-size: 9px;" color="#000000"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Data Implantação</strong></font></div>
								</td>
								
								<td align="center" bgcolor="#90c7fc" width="15%">
								<div class="style9"><font style="font-size: 9px;" color="#000000"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Data Retirada</strong></font></div>
								</td>
								
								<td align="center" bgcolor="#90c7fc" width="10%">
								<div class="style9"><font style="font-size: 9px;" color="#000000"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Cliente Alvo</strong></font></div>
								</td>
								
								<td align="center" bgcolor="#90c7fc" width="10%">
								<div class="style9"><font style="font-size: 9px;" color="#000000"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Escritório Cobrança</strong></font></div>
								</td>
								
								<td align="center" bgcolor="#90c7fc" width="10%">
								<div class="style9"><font style="font-size: 9px;" color="#000000"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Advogado Responsável</strong></font></div>
								</td>
	
							</tr>							
							
							
							
							<tr>						
								<td colspan="7">
								<div style="height: 100%; overflow: auto; width : 100%;"><!--corpo da segunda tabela-->
								<%int cont = 0;%>
								<logic:notEmpty name="colecaoSituacoesCobranca" scope="session">
									<table width="100%" align="left" border="0">

									<logic:iterate name="colecaoSituacoesCobranca"
										id="situacoesCobranca">
										<%cont = cont + 1;
		                            	if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
											<%} else {%>
										<tr bgcolor="#FFFFFF">
											<%}%>
											<td align="center" width="25%">
											<div class="style9"><font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
											<bean:write name="situacoesCobranca"
												property="cobrancaSituacao.descricao" /></font></div>
											</td>

											<td align="center" width="15%">
											<div class="style9">
												<font style="font-size: 9px;" color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
													<bean:write name="situacoesCobranca" property="anoMesReferenciaInicio"/>
													a
													<bean:write name="situacoesCobranca" property="anoMesReferenciaFinal"/>
												</font>
											</div>
											</td>

											<td align="center" width="15%">
											<div class="style9"><font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
											<bean:write name="situacoesCobranca"
												property="dataImplantacaoCobranca" formatKey="date.format" /></font></div>
											</td>
											
											
											<td align="center" width="15%">
											<div class="style9"><font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
											<bean:write name="situacoesCobranca"
												property="dataRetiradaCobranca" formatKey="date.format" /></font></div>
											</td>
											
											
											<td align="center" width="10%">
											<div class="style9">
												<font style="font-size: 9px;" color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
													<a href="javascript:abrirPopup('exibirConsultarClienteAction.do?desabilitarPesquisaCliente=SIM&codigoCliente='+<bean:write name="situacoesCobranca" property="cliente.id" />, 500, 800);">
														<bean:write name="situacoesCobranca" property="cliente.id" />
													</a>												
												</font>
											</div>
											</td>											
											
											<td align="center" width="10%">
											<div class="style9">
												<font style="font-size: 9px;" color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
												    <logic:notEmpty name="situacoesCobranca" property="escritorio">												
														<bean:write name="situacoesCobranca" property="escritorio.nome" />
													</logic:notEmpty>
												
												</font>
											</div>	
																						
											</td>											
											<td align="center" width="10%">
											<div class="style9">
												<font style="font-size: 9px;" color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
												    <logic:notEmpty name="situacoesCobranca" property="escritorio">												
														<bean:write name="situacoesCobranca" property="advogado.nome" />
													</logic:notEmpty>												
												</font>
											</div>
										</tr>
									</logic:iterate>
								</table>								
								</logic:notEmpty>
								</div>								
								</td>				
							</tr>						
						</table>				
				
				<br>	
								
				<tr>				

					<td colspan="4" align="left">
					<table bgcolor="#90c7fc" width="100%" border="0">
						<tr>

							<td colspan="6" align="center" bgcolor="#79bbfd"><strong>Vencimentos
							Alternativos </strong></td>

						</tr>

						<tr bgcolor="#90c7fc">

							<td colspan="2" align="center" bgcolor="#90c7fc">
							<div class="style9"><font style="font-size: 9px;" color="#000000"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Dia do
							Vencimento</strong></font></div>
							</td>

							<td colspan="3" align="center" bgcolor="#90c7fc">
							<div class="style9"><font style="font-size: 9px;" color="#000000"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Data
							Implanta&ccedil;&atilde;o</strong> </font></div>
							</td>

							<td align="center" bgcolor="#90c7fc">
							<div class="style9"><font style="font-size: 9px;" color="#000000"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Data
							Exclus&atilde;o</strong></font></div>
							</td>

						</tr>
						
						
						<tr>
							<td colspan="6">
							<div style="width: 100%; height: 100%; overflow: auto;"><!--corpo da segunda tabela-->
							<logic:notEmpty
								name="colecaoVencimentosAlternativos" scope="session">
								<table width="100%" align="left" border="0">

									<logic:iterate name="colecaoVencimentosAlternativos"
										id="vencimentosAlternativos">
										<%cont = cont + 1;
			if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
											<%} else {%>
										<tr bgcolor="#FFFFFF">
											<%}%>
											<td align="center" width="36%">
											<div class="style9"><font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
											<bean:write name="vencimentosAlternativos"
												property="dateVencimento" /></font></div>
											</td>

											<td align="center" width="37%">
											<div class="style9"><font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
											<bean:write name="vencimentosAlternativos"
												property="dataImplantacao" formatKey="date.format" /></font></div>
											</td>

											<td align="center" width="29%">
											<div class="style9"><font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
											<bean:write name="vencimentosAlternativos"
												property="dateExclusao" formatKey="date.format" /></font></div>
											</td>
										</tr>
									</logic:iterate>

								</table>
							</logic:notEmpty></div>
							</td>
						</tr>


					</table>
					<br>
					<table bgcolor="#90c7fc" width="100%"
						border="0">
						<tr>

							<td colspan="6" bordercolor="#79bbfd" align="center"
								bgcolor="#79bbfd"><strong>D&eacute;bito Autom&aacute;tico</strong></td>

						</tr>

						<tr bgcolor="#90c7fc">

							<td align="center" bgcolor="#90c7fc" width="26%">
							<div class="style9"><font style="font-size: 9px;" color="#000000"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Banco</strong></font></div>
							</td>

							<td align="center" bgcolor="#90c7fc" width="8%"><font
								style="font-size: 9px;" color="#000000"
								face="Verdana, Arial, Helvetica, sans-serif"><strong>Ag&ecirc;ncia</strong></font></td>

							<td align="center" bgcolor="#90c7fc" width="22%">
							<div class="style9"><font style="font-size: 9px;" color="#000000"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Ident.Cliente
							no Banco</strong> </font></div>
							</td>

							<td align="center" bgcolor="#90c7fc" width="12%"><font
								style="font-size: 9px;" color="#000000"
								face="Verdana, Arial, Helvetica, sans-serif"><strong>Data
							Op&ccedil;&atilde;o</strong></font></td>

							<td align="center" bgcolor="#90c7fc" width="18%"><font
								style="font-size: 9px;" color="#000000"
								face="Verdana, Arial, Helvetica, sans-serif"><strong>Data
							Implanta&ccedil;&atilde;o</strong></font></td>

							<td align="center" bgcolor="#90c7fc">
							<div class="style9"><font style="font-size: 9px;" color="#000000"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Data
							Exclus&atilde;o</strong></font></div>
							</td>

						</tr>


						<tr>
							<td colspan="6">
							<div style="width: 100%; height: 100%; overflow: auto;">
							<table width="100%" align="left" bgcolor="#99CCFF">
								<!--corpo da segunda tabela-->
								<%cont = 0;%>
								<logic:notEmpty name="colecaoDebitosAutomaticos" scope="session">
									<logic:iterate name="colecaoDebitosAutomaticos"
										id="debitosAutomaticos">
										<%cont = cont + 1;
										  if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
											<%} else {%>
										<tr bgcolor="#FFFFFF">
											<%}%>


											<td align="center" width="26%">
											<div class="style9"><font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif"><logic:present name="debitosAutomaticos"
												property="agencia">
												<bean:define name="debitosAutomaticos" property="agencia" id="agencia" />

												<logic:present name="agencia" property="banco">

													<bean:define name="agencia" property="banco" id="banco" />
													
													<bean:write name="banco" property="descricaoAbreviada" />
												</logic:present>	
													

											</logic:present></font></div>
											</td>

											<td align="center" width="8%"><font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
											<logic:present name="debitosAutomaticos" property="agencia">
												<bean:write name="debitosAutomaticos"
													property="agencia.codigoAgencia" />
											</logic:present></font></td>

											<td align="center" width="22%">
											<div class="style9"><font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
											<bean:write name="debitosAutomaticos"
												property="identificacaoClienteBanco" /> </font></div>
											</td>

											<td align="center" width="12%"><font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
											<bean:write name="debitosAutomaticos"
												property="dataOpcaoDebitoContaCorrente"
												formatKey="date.format" /></font></td>

											<td align="center" width="18%"><font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
											<bean:write name="debitosAutomaticos"
												property="dataInclusaoNovoDebitoAutomatico"
												formatKey="date.format" /></font></td>

											<td align="center">
											<div class="style9"><font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
											<bean:write name="debitosAutomaticos" property="dataExclusao"
												formatKey="date.format" /></font></div>
											</td>
										</tr>
									</logic:iterate>
								</logic:notEmpty>
							</table>
							</div>
							</td>
						</tr>

					</table>
					<br>
					
					<table width="100%" align="center" bgcolor="#90c7fc" border="0">
							<tr bordercolor="#79bbfd">
								<td colspan="4" align="center" bgcolor="#79bbfd"><strong>Ocorr&ecirc;ncias
								de Cadastro</strong></td>
							</tr>
							<tr bordercolor="#000000">
								<td width="65%" bgcolor="#90c7fc" align="center">
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Ocorr&ecirc;ncia</strong></font>
								</div>
								</td>

								<td width="17%" align="center" bgcolor="#90c7fc">
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Data </strong></font></div>
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> </font></div>
								</td>
								<td width="18%" align="center" bgcolor="#90c7fc"><span
									class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"><strong>Funcionário</strong></font></span></td>
								<td width="18%" align="center" bgcolor="#90c7fc"><span
									class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"><strong>Foto</strong></font></span></td>
								
							</tr>
							<%String cor = "#FFFFFF";%>
							
							<logic:present name="colecaoImovelCadastroOcorrencia" scope="session">
								<logic:iterate name="colecaoImovelCadastroOcorrencia" id="imovelCadastroOcorrencia">
										<%if (cor.equalsIgnoreCase("#FFFFFF")) {
				cor = "#cbe5fe";%>
										<tr bgcolor="#FFFFFF">
										
											<%} else {
				cor = "#FFFFFF";%>
										<tr bgcolor="#cbe5fe">
											<%}%>
								<td bordercolor="#90c7fc" align="left">
								<font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="imovelCadastroOcorrencia" property="cadastroOcorrencia.descricao"/></font>
								</td>
								<td align="center">
								<font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="imovelCadastroOcorrencia" property="dataOcorrencia" formatKey="date.format"/></font>
								</td>
								
								<td bordercolor="#90c7fc" align="left">
									<font style="font-size: 9px;" color="#000000" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="imovelCadastroOcorrencia" property="funcionario.nome"/></font>
								</td>
								
								<td align="center">
								<logic:notEmpty name="imovelCadastroOcorrencia" property="fotoOcorrencia">
								<a href="javascript:abrirPopup('exibirFotoOcorrenciaCadastroAction.do?id=<bean:write name="imovelCadastroOcorrencia" property="id"/>',  600, 800)">
								<input name="imageField" type="image" src="imagens/imgfolder.gif" width="18" height="18" border="0" disabled="disabled"> </a>
								</logic:notEmpty>
								</td>								
								
							</tr>
							</logic:iterate>
							</logic:present>
						</table>
					
					<br>
					<table width="100%" align="center" bgcolor="#90c7fc" border="0">
					<tr bordercolor="#79bbfd">
						<td colspan="4" align="center" bgcolor="#79bbfd"><strong>Anormalidades de Elo </strong></td>
					</tr>
					
							<tr bordercolor="#000000">
								<td width="65%" bgcolor="#90c7fc" align="center">
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong>Anormalidade</strong></font></div>
								</td>

								<td width="17%" bgcolor="#90c7fc" align="center">
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong> Data </strong>
								</font></div>
								</td>
								<td width="17%" bgcolor="#90c7fc" align="center">
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong> Funcionário </strong>
								</font></div>
								</td>
								<td width="18%" bgcolor="#90c7fc" align="center"><span
									class="style9"> <font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong> Foto </strong>
								</font> </span></td>
							</tr>
							<%String cor2 = "#FFFFFF";%>
							<logic:present name="colecaoImovelEloAnormalidade" scope="session">
								<logic:iterate name="colecaoImovelEloAnormalidade" id="imovelEloAnormalidade">
										<%if (cor2.equalsIgnoreCase("#FFFFFF")) {
				cor2 = "#cbe5fe";%>
										<tr bgcolor="#FFFFFF">
										
											<%} else {
				cor2 = "#FFFFFF";%>
										<tr bgcolor="#cbe5fe">
											<%}%>
								<td align="left"><font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="imovelEloAnormalidade" property="eloAnormalidade.descricao"/></font></td>
								<td align="center">
								<font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="imovelEloAnormalidade" property="dataAnormalidade" formatKey="date.format"/></font>
								</td>
								<td align="left"><font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="imovelEloAnormalidade" property="funcionario.nome"/></font></td>
								<td align="center">
								<logic:notEmpty name="imovelEloAnormalidade" property="fotoAnormalidade">
								<a href="javascript:abrirPopup('exibirFotoAnormalidadeEloAction.do?id=<bean:write name="imovelEloAnormalidade" property="id"/>', 600, 800)">
								<input name="imageField" type="image"
									src="imagens/imgfolder.gif" width="18" height="18" border="0" disabled="disabled"></a>
								</logic:notEmpty>
								</td>
							</tr>
							</logic:iterate>
							</logic:present>
						</table>
					
					
					
					<br>
					<table bgcolor="#90c7fc" width="100%"

						border="0">
						<tr>
							<td colspan="6" bordercolor="#79bbfd" align="center"
								bgcolor="#79bbfd"><strong>Situa&ccedil;&otilde;es Especiais de
							Faturamento </strong></td>
						</tr>
						<tr bgcolor="#90c7fc">
							<td align="center" bgcolor="#90c7fc" width="26%">
							<div class="style9"><font style="font-size: 9px;" color="#000000"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Tipo</strong></font></div>
							</td>
							<td align="center" bgcolor="#90c7fc" width="8%"><font
								style="font-size: 9px;" color="#000000"
								face="Verdana, Arial, Helvetica, sans-serif"><strong>Motivo</strong></font></td>
							<td align="center" bgcolor="#90c7fc" width="22%">
							<div class="style9"><font style="font-size: 9px;" color="#000000"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>M&ecirc;s/Ano
							In&iacute;cio&nbsp;</strong> </font></div>
							</td>
							<td align="center" bgcolor="#90c7fc" width="12%"><font
								style="font-size: 9px;" color="#000000"
								face="Verdana, Arial, Helvetica, sans-serif"><strong>M&ecirc;s/Ano
							Fim</strong></font></td>
							<td align="center" bgcolor="#90c7fc" width="18%"><font
								style="font-size: 9px;" color="#000000"
								face="Verdana, Arial, Helvetica, sans-serif"><strong>M&ecirc;s/Ano
							Retirada</strong></font></td>
							<td align="center" bgcolor="#90c7fc">
							<div class="style9"><font style="font-size: 9px;" color="#000000"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Usu&aacute;rio</strong></font></div>
							</td>
						</tr>

						<tr>
							<td colspan="6">
							<div style="width: 100%; height: 100%; overflow: auto;">
							<table width="100%" align="left" bgcolor="#99CCFF">
								<!--corpo da segunda tabela-->
								<%cont = 0;%>
								<logic:notEmpty name="colecaoFaturamentosSituacaoHistorico" scope="session">
								
									<logic:iterate name="colecaoFaturamentosSituacaoHistorico"
										id="faturamentosSituacaoHistorico"
										type="FaturamentoSituacaoHistorico">
										<%cont = cont + 1;
			if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
											<%} else {%>
										<tr bgcolor="#FFFFFF">
											<%}%>

											<td align="center" width="26%">
											<div class="style9"><font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif">


											<logic:present name="faturamentosSituacaoHistorico"
												property="faturamentoSituacaoTipo">

												<%if (cont % 2 == 0) {%>
												<input type="text" name="dados" readonly="true"
													value="<bean:write
															name="faturamentosSituacaoHistorico"
															property="faturamentoSituacaoTipo.id"/>"
													style="background-color:#cbe5fe; font-size: 9px;border:0; color: #000000;text-align: center; font-family:Verdana, Arial, Helvetica, sans-serif;"
													onmouseover="this.T_BGCOLOR='whitesmoke';this.T_LEFT=true;return escape('<bean:write
															name="faturamentosSituacaoHistorico"
															property="faturamentoSituacaoTipo.descricao"/>')"
													size="15" maxlength="15" />
												<%} else {%>
												<input type="text" name="dados" readonly="true"
													value="<bean:write
															name="faturamentosSituacaoHistorico"
															property="faturamentoSituacaoTipo.id"/>"
													style="background-color:#FFFFFF; font-size: 9px;border:0; color: #000000;text-align: center; font-family:Verdana, Arial, Helvetica, sans-serif;"
													onmouseover="this.T_BGCOLOR='whitesmoke';this.T_LEFT=true;return escape('<bean:write
															name="faturamentosSituacaoHistorico"
															property="faturamentoSituacaoTipo.descricao"/>')"
													size="15" maxlength="15" />
												<%}%>

											</logic:present> </font></div>
											</td>
											<td align="center" width="8%">
											<font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
											<logic:present name="faturamentosSituacaoHistorico"
												property="faturamentoSituacaoMotivo">

												<%if (cont % 2 == 0) {%>
												<input type="text" name="dados" readonly="true"
													value="<bean:write
															name="faturamentosSituacaoHistorico"
															property="faturamentoSituacaoMotivo.id"/>"
													style="background-color:#cbe5fe; font-size: 9px;border:0; color: #000000;text-align: center; font-family:Verdana, Arial, Helvetica, sans-serif;"
													onmouseover="this.T_BGCOLOR='whitesmoke';this.T_LEFT=true;return escape('<bean:write
															name="faturamentosSituacaoHistorico"
															property="faturamentoSituacaoMotivo.descricao"/> ')"
													size="15" maxlength="15" />
												<%} else {%>
												<input type="text" name="dados" readonly="true"
													value="<bean:write
															name="faturamentosSituacaoHistorico"
															property="faturamentoSituacaoMotivo.id"/>"
													style="background-color:#FFFFFF; font-size: 9px;border:0; color: #000000;text-align: center; font-family:Verdana, Arial, Helvetica, sans-serif;"
													onmouseover="this.T_BGCOLOR='whitesmoke';this.T_LEFT=true;return escape('<bean:write
															name="faturamentosSituacaoHistorico"
															property="faturamentoSituacaoMotivo.descricao"/> ')"
													size="15" maxlength="15" />
												<%}%>

											</logic:present> </font></td>


											<td align="center" width="22%">
											<div class="style9"><font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
											<%=""
							+ Util
									.formatarMesAnoReferencia(faturamentosSituacaoHistorico
											.getAnoMesFaturamentoSituacaoInicio())%>
											</font></div>
											</td>
											<td align="center" width="12%">
											<font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
											<%=""
							+ Util
									.formatarMesAnoReferencia(faturamentosSituacaoHistorico
											.getAnoMesFaturamentoSituacaoFim())%>
											</font></td>
											
											<td align="center" width="18%">
											<font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
											<% if(faturamentosSituacaoHistorico.getAnoMesFaturamentoRetirada() != null){ %>
											<%=""
							+ Util
									.formatarMesAnoReferencia(faturamentosSituacaoHistorico
											.getAnoMesFaturamentoRetirada())%>
											<%} %>
											
											</font></td>
											<td align="center">
											<div class="style9"><font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
											<logic:present name="faturamentosSituacaoHistorico"
												property="usuario">
												<bean:write name="faturamentosSituacaoHistorico"
													property="usuario.nomeUsuario" />
											</logic:present></font></div>
											</td>


										</tr>
									</logic:iterate>
								</logic:notEmpty>
							</table>
							</div>
							</td>
						</tr>

					</table>
					<br>
					<table bgcolor="#90c7fc" width="100%"
						border="0">
						<tr>
							<td colspan="10" align="center" bgcolor="#79bbfd"><strong>Situa&ccedil;&otilde;es
							Especiais de Cobran&ccedil;a </strong></td>
						</tr>
						<tr bordercolor="#000000" bgcolor="#90c7fc">
							<td align="center" bgcolor="#90c7fc" width="10%">
							<div class="style9"><font style="font-size: 9px;" color="#000000"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Tipo</strong></font></div>
							</td>
							<td align="center" bgcolor="#90c7fc" width="13%"><font
								style="font-size: 9px;" color="#000000"
								face="Verdana, Arial, Helvetica, sans-serif"><strong>Motivo</strong></font></td>
							<td align="center" bgcolor="#90c7fc" width="10%">
							<div class="style9"><font style="font-size: 9px;" color="#000000"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>M&ecirc;s/Ano
							In&iacute;cio&nbsp;</strong> </font></div>
							</td>
							<td align="center" bgcolor="#90c7fc" width="10%"><font
								style="font-size: 9px;" color="#000000"
								face="Verdana, Arial, Helvetica, sans-serif"><strong>M&ecirc;s/Ano
							Fim</strong></font></td>
							<td align="center" bgcolor="#90c7fc" width="10%"><font
								style="font-size: 9px;" color="#000000"
								face="Verdana, Arial, Helvetica, sans-serif"><strong>M&ecirc;s/Ano
							Retirada</strong></font></td>
							<td align="center" bgcolor="#90c7fc" width="15%">
							<div class="style9"><font style="font-size: 9px;" color="#000000"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Usu&aacute;rio Inclusão</strong></font></div>
							</td>
							<td align="center" bgcolor="#90c7fc" width="15%">
							<div class="style9"><font style="font-size: 9px;" color="#000000"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Data/Hora Inclusão</strong></font></div>
							</td>
							<td align="center" bgcolor="#90c7fc" width="15%">
							<div class="style9"><font style="font-size: 9px;" color="#000000"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Usu&aacute;rio Exclusão</strong></font></div>
							</td>
							<td align="center" bgcolor="#90c7fc" width="15%">
							<div class="style9"><font style="font-size: 9px;" color="#000000"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Data/Hora Exclusão</strong></font></div>
							</td>
						</tr>



						<tr>
							<td colspan="10">
							<!-- <div style="width: 100%; height: 100%; overflow: auto;">  -->
							<table width="100%" align="left" bgcolor="#99CCFF">
								<!--corpo da segunda tabela-->
								<%cont = 0;%>
								<logic:notEmpty name="colecaoCobrancasSituacaoHistorico" scope="session">
									<logic:iterate name="colecaoCobrancasSituacaoHistorico"
										id="cobrancasSituacaoHistorico"
										type="CobrancaSituacaoHistorico">
										<%cont = cont + 1;
			if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
											<%} else {%>
										<tr bgcolor="#FFFFFF">
											<%}%>
											<td align="center" width="8%" style="height:50px;">
											
											<%if (cont % 2 == 0) {%>
												<input type="text" name="dados" readonly="true"
													value="<bean:write
															name="cobrancasSituacaoHistorico"
															property="cobrancaSituacaoTipo.id"/>"
													style="background-color:#cbe5fe; font-size: 9px;border:0; color: #000000;text-align: center; font-family:Verdana, Arial, Helvetica, sans-serif;"
													onmouseover="this.T_BGCOLOR='whitesmoke';this.T_LEFT=true;return escape('<bean:write
															name="cobrancasSituacaoHistorico"
															property="cobrancaSituacaoTipo.descricao"/>')"
													size="9" maxlength="9" />
												<%} else {%>
												<input type="text" name="dados" readonly="true"
													value="<bean:write
															name="cobrancasSituacaoHistorico"
															property="cobrancaSituacaoTipo.id"/>"
													style="background-color:#FFFFFF; font-size: 9px;border:0; color: #000000;text-align: center; font-family:Verdana, Arial, Helvetica, sans-serif;"
													onmouseover="this.T_BGCOLOR='whitesmoke';this.T_LEFT=true;return escape('<bean:write
															name="cobrancasSituacaoHistorico"
															property="cobrancaSituacaoTipo.descricao"/>')"
													size="9" maxlength="9" />
												<%}%>
											
											</td>
											<td align="center" width="8%" style="height:50px;">
											<%if (cont % 2 == 0) {%>
												<input type="text" name="dados" readonly="true"
													value="<bean:write
															name="cobrancasSituacaoHistorico"
															property="cobrancaSituacaoMotivo.id"/>"
													style="background-color:#cbe5fe; font-size: 9px;border:0; color: #000000;text-align: center; font-family:Verdana, Arial, Helvetica, sans-serif;"
													onmouseover="this.T_BGCOLOR='whitesmoke';this.T_LEFT=true;return escape('<bean:write
															name="cobrancasSituacaoHistorico"
															property="cobrancaSituacaoMotivo.descricao"/> ')"
													size="9" maxlength="9" />
												<%} else {%>
												<input type="text" name="dados" readonly="true"
													value="<bean:write
															name="cobrancasSituacaoHistorico"
															property="cobrancaSituacaoMotivo.id"/>"
													style="background-color:#FFFFFF; font-size: 9px;border:0; color: #000000;text-align: center; font-family:Verdana, Arial, Helvetica, sans-serif;"
													onmouseover="this.T_BGCOLOR='whitesmoke';this.T_LEFT=true;return escape('<bean:write
															name="cobrancasSituacaoHistorico"
															property="cobrancaSituacaoMotivo.descricao"/> ')"
													size="9" maxlength="9" />
												<%}%>
											
											</td>


											<td align="center" width="10%" style="height:50px;">
											<div class="style9"><font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
											<%=""
					+ Util.formatarMesAnoReferencia(cobrancasSituacaoHistorico
							.getAnoMesCobrancaSituacaoInicio())%>
											</font></div>
											</td>
											<td align="center" width="10%" style="height:50px;"><font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif">

											<%=""
					+ Util.formatarMesAnoReferencia(cobrancasSituacaoHistorico
							.getAnoMesCobrancaSituacaoFim())%>
											</font></td>
											<td align="center" width="10%" style="height:50px;"><font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
											<%if (cobrancasSituacaoHistorico.getAnoMesCobrancaRetirada() != null) {%>
											<%=""
								+ Util
										.formatarMesAnoReferencia(cobrancasSituacaoHistorico
												.getAnoMesCobrancaRetirada())%>
											<%}%> </font></td>


											<td align="center" width="15%" style="height:50px;">
											<div class="style9"><font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
											<logic:present name="cobrancasSituacaoHistorico"
												property="usuarioLogadoInclusao">
												<bean:write name="cobrancasSituacaoHistorico"
													property="usuarioLogadoInclusao.nomeUsuario" />
											</logic:present></font></div>
											</td>

											<td align="center" width="15%" style="height:50px;"><font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
											<%if (cobrancasSituacaoHistorico.getDataHoraInclusao() != null) {%>
											<%="" + Util.formatarDataComHora(cobrancasSituacaoHistorico
												.getDataHoraInclusao())%>
											<%}%> </font></td>
											
											<td align="center" width="15%" style="height:50px;">
											<div class="style9"><font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
											<logic:present name="cobrancasSituacaoHistorico"
												property="usuarioLogadoExclusao">
												<bean:write name="cobrancasSituacaoHistorico"
													property="usuarioLogadoExclusao.nomeUsuario" />
											</logic:present></font></div>
											</td>
											
											<td align="center" width="15%" style="height:50px;"><font style="font-size: 9px;"
												color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
											<%if (cobrancasSituacaoHistorico.getDataHoraExclusao() != null) {%>
											<%="" + Util.formatarDataComHora(cobrancasSituacaoHistorico
												.getDataHoraExclusao())%>
											<%}%> </font></td>
										</tr>
									</logic:iterate>
								</logic:notEmpty>
							</table>
							<!-- </div>  -->
							</td>
						</tr>

					</table>
					<br>
					<table  bgcolor="#90c7fc" width="100%"
						border="0">
						<tr>
							<td colspan="5">
								<table width="100%" align="center" bgcolor="#90c7fc" border="0">
									<tr bordercolor="#79bbfd">
										<td colspan="10" align="center" bgcolor="#79bbfd"><strong>Contrato Demanda Consumo</strong></td>
									</tr>
									<tr bordercolor="#000000">
										<td align="center" bgcolor="#90c7fc" width="15%">
											<div class="style9"><font style="font-size: 9px;" color="#000000"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Número Contrato</strong></font></div>
										</td>
										<td align="center" bgcolor="#90c7fc" width="12%">
											<div class="style9"><font style="font-size: 9px;" color="#000000"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Início</strong></font></div>
										</td>
										<td align="center" bgcolor="#90c7fc" width="10%">
											<div class="style9"><font style="font-size: 9px;" color="#000000"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Fim</strong></font></div>
										</td>
										<td align="center" bgcolor="#90c7fc" width="10%">
											<div class="style9"><font style="font-size: 9px;" color="#000000"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Tarifa de Consumo</strong></font></div>
										</td>
										<td align="center" bgcolor="#90c7fc" width="15%">
											<div class="style9"><font style="font-size: 9px;" color="#000000"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Consumo Fixo</strong></font></div>
										</td>
										<td align="center" bgcolor="#90c7fc" width="15%">
											<div class="style9"><font style="font-size: 9px;" color="#000000"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Encer.</strong></font></div>
										</td>
									</tr>
									<tr>
										<td width="100%" colspan="6">
										<div style="width: 100%; height: 100%; overflow: auto;">
											<table width="100%" align="center" bgcolor="#99CCFF">
												<!--corpo da segunda tabela-->
												<%int contt = 0;%>
										<logic:notEmpty name="colecaoContratoDemandaConsumo">
											<logic:iterate name="colecaoContratoDemandaConsumo" id="contratoDemandaConsumo"
												type="ContratoDemandaConsumo">
												<%contt = contt + 1;
			if (cont % 2 == 0) {%>					
												<tr bgcolor="#cbe5fe">
													<%} else {%>
												<tr bgcolor="#FFFFFF">
													<%}%>
													<td width="17%" align="center">
														<div class="style9"><font color="#000000"
														style="font-size:9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
														name="contratoDemandaConsumo" property="numeroContrato">
														<bean:write name="contratoDemandaConsumo"
															property="numeroContrato" />
													</logic:present> </font></div>
													</td>
													<td width="15%" align="center">
														<div class="style9"><font color="#000000"
														style="font-size:9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
														name="contratoDemandaConsumo" property="dataInicioFormatada">
														<bean:write name="contratoDemandaConsumo"
															property="dataInicioFormatada" />
													</logic:present> </font></div>
													</td>
													<td width="15%" align="center">
														<div class="style9"><font color="#000000"
														style="font-size:9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
														name="contratoDemandaConsumo" property="dataFimFormatada">
														<bean:write name="contratoDemandaConsumo"
															property="dataFimFormatada" />
													</logic:present> </font></div>
													</td>
													<td width="15%" align="center">
														<div class="style9"><font color="#000000"
														style="font-size:9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
														name="contratoDemandaConsumo" property="consumoTarifa">
														<bean:write name="contratoDemandaConsumo"
															property="consumoTarifa.id" />
													</logic:present> </font></div>
													</td>
													<td width="18%" align="center">
														<div class="style9"><font color="#000000"
														style="font-size:9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
														name="contratoDemandaConsumo" property="numeroConsumoFixo">
														<bean:write name="contratoDemandaConsumo"
															property="numeroConsumoFixo" />
													</logic:present> </font></div>
													</td>
													<td width="17%" align="center">
														<div class="style9"><font color="#000000"
														style="font-size:9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
														name="contratoDemandaConsumo" property="contratoEncerrado">
														<bean:write name="contratoDemandaConsumo"
															property="contratoEncerrado" />
													</logic:present> </font></div>
													</td>
												</tr>
											</logic:iterate>
										</logic:notEmpty>
											</table>
										</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<br>
					<table bgcolor="#90c7fc" width="100%"
						border="0">
							
					</table>
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">
					<div align="right"><jsp:include
						page="/jsp/util/wizard/navegacao_botoes_wizard_consulta.jsp?numeroPagina=2" /></div>
					</td>
				</tr>
			</table>

			</td>

		</tr>

	</table>


	<p>&nbsp;</p>


	<%@ include file="/jsp/util/tooltip.jsp"%>
	<logic:notPresent name="montarPopUp">	
		<%@ include file="/jsp/util/rodape.jsp"%>
	</logic:notPresent>
</html:form>
</body>
<!-- imovel_consultar_dados_complementares.jsp -->
</html:html>
