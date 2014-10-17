<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<%--@ page import="gcom.util.ConstantesSistema"--%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>

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
</script>
</head>

<body leftmargin="5" topmargin="5"
	onload="window.focus();resizePageSemLink(700, 530);javascript:setarFoco('${requestScope.nomeCampo}');">

<html:form action="/exibirConsultarDadosOrdemServicoPopupAction" 
	name="ConsultarDadosOrdemServicoPopupActionForm"
	type="gcom.gui.atendimentopublico.ordemservico.ConsultarDadosOrdemServicoPopupActionForm"
	method="post">

	<table width="100%" border="0" cellspacing="5" cellpadding="0">

		<tr>

			<td width="100%" valign="top" class="centercoltext">

				<table height="100%">
					<tr>
						<td></td>
					</tr>
				</table>
	
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<td width="11">
							<img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" />
						</td>
						<td class="parabg">Consultar Ordem de Servi&ccedil;o</td>
						<td width="11">
							<img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
						</td>
					</tr>
				</table>
			
				<p>&nbsp;</p>

				<table width="100%" border="0">
				<tr>
					<td align="right"></td>
				</tr>
				</table>

				<!--Inicio da Tabela Dados Gerais da Ordem de Servi�o -->
            	<table width="100%" border="0">

            	<tr>
                	<td height="31"  colspan="2">
                    <table width="100%" border="0" align="center">

                        <tr bgcolor="#cbe5fe">

                      		<td align="center">
                      		<table width="100%" border="0" bgcolor="#99CCFF">

					    		<tr bgcolor="#99CCFF">
                         			<td height="18" colspan="2">
                         				<div align="center">
                         					<span class="style2"><b>Dados Gerais da Ordem de Servi&ccedil;o </b></span>
                         				</div>
                         			</td>
                        		</tr>

								<tr bgcolor="#cbe5fe">
									<td>
									<table border="0" width="100%">

		                            	<tr>
		                              		<td height="10" width="33%"><strong>N&uacute;mero da OS:</strong></td>
		                              		
		                              		<td> 
												<html:text property="numeroOS" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="9" />
												&nbsp;&nbsp;&nbsp;&nbsp;
												<strong>Situa&ccedil;&atilde;o da OS:</strong>
												&nbsp;&nbsp;
												<html:text property="situacaoOS" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="24"/>
													
		                                  	</td>
		                            	</tr>
										<c:if test="${ConsultarDadosOrdemServicoPopupActionForm.numeroRA != null}">
		                            	<tr>
		                              		<td height="10" width="33%"><strong>N&uacute;mero do RA:</strong></td>
		                              		
		                              		<td> 
												<html:text property="numeroRA" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="9"/>
												&nbsp;&nbsp;&nbsp;&nbsp;	
												<strong>Situa&ccedil;&atilde;o do RA:</strong>
												&nbsp;&nbsp;	
												<html:text property="situacaoRA" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="24"/>
		                                  	</td>
		                            	</tr>
										</c:if>
		                            	
		                            	<c:if test="${ConsultarDadosOrdemServicoPopupActionForm.numeroDocumentoCobranca != null}">
		                            	<tr> 
		                              		<td  width="33%"><strong>N&uacute;mero do Documento de Cobran&ccedil;a:</strong></td>
		                              		<td>
												<html:text property="numeroDocumentoCobranca" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="9"/>
		                                	</td>
		                            	</tr>
										</c:if>
										
		                            	<tr> 
		                              		<td  width="33%"><strong>Data da Gera&ccedil;&atilde;o:</strong></td>
		                              		<td >
												<html:text property="dataGeracao" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="9"
													maxlength="10"/>
		                                	</td>
		                            	</tr>

			                            <tr>
			                              	<td height="10" width="33%"><strong>Tipo do Servi&ccedil;o:</strong></td>
											<td>
												<html:text property="tipoServicoId" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="4"/>
												<html:text property="tipoServicoDescricao" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="50"/>
											</td>
			                            </tr>
		                            	<c:if test="${ConsultarDadosOrdemServicoPopupActionForm.numeroOSReferencia != null}">
		                            	<tr> 
		                              		<td width="33%"><strong>N&uacute;mero da OS de Refer&ecirc;ncia:</strong></td>
		                              		<td>
												<html:text property="numeroOSReferencia" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="9"/>
		                                	</td>
		                            	</tr>
		                            	</c:if>

		                            	<c:if test="${ConsultarDadosOrdemServicoPopupActionForm.tipoServicoReferenciaId != null}">
			                            <tr>
			                              	<td height="10" width="33%"><strong>Tipo do Servi&ccedil;o de Refer&ecirc;ncia:</strong></td>
											<td>
												<html:text property="tipoServicoReferenciaId" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="4"/>
												<html:text property="tipoServicoReferenciaDescricao" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="50"/>
											</td>
			                            </tr>
										</c:if>

		                            	<c:if test="${ConsultarDadosOrdemServicoPopupActionForm.retornoOSReferida != null}">
			                            <tr> 
			                              	<td height="10" width="33%"><strong>Retorno da OS de Refer&ecirc;ncia:</strong></td>
			                              	<td> 
												<html:text property="retornoOSReferida" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="48"/>
											</td>
			                            </tr>
			                            </c:if>

			                            <tr> 
		                              		<td height="10" width="20"><strong>Observa&ccedil;&atilde;o:</strong></td>
			                              	<td> 
											<strong>
												<html:textarea property="observacao" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													cols="40" rows="7"/>
			                              	</strong>
											</td>
			                            </tr>

		                            	<tr>
		                              		<td height="10" width="33%"><strong>Valor do Servi&ccedil;o Original:</strong></td>
		                              		
		                              		<td> 
												<html:text property="valorServicoOriginal" 
													readonly="true"
													style="background-color:#EFEFEF; border:0; text-align:right;" 
													size="9"/>
												&nbsp;&nbsp;&nbsp;&nbsp;	
												
												<strong>Valor do Servi&ccedil;o Atual:</strong>
												&nbsp;&nbsp;
												<html:text property="valorServicoAtual" 
													readonly="true"
													style="background-color:#EFEFEF; border:0; text-align:right;" 
													size="9"/>
		                                  	</td>
		                            	</tr>

			                            <tr>
			                              	<td width="33%"><strong>Prioridade Original:</strong></td>
			                              	<td>
												<html:text property="prioridadeOriginal" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="48"/>
											</td>
			                            </tr>

			                            <tr>
			                              	<td width="33%"><strong>Prioridade Atual:</strong></td>
			                              	<td>
												<html:text property="prioridadeAtual" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="48"/>
											</td>
			                            </tr>


			                            <tr> 
			                              	<td width="33%"><strong>Unidade da Gera&ccedil;&atilde;o:</strong></td>
			                              	<td>
												<html:text property="unidadeGeracaoId" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="4"/>

												<html:text property="unidadeGeracaoDescricao" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="40"/>
											</td>
			                            </tr>
			                            
			                            <tr> 
			                              	<td width="33%"><strong>Usu&aacute;rio da Gera&ccedil;&atilde;o:</strong></td>
			                              	<td>
												<html:text property="usuarioGeracaoId" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="4"/>

												<html:text property="usuarioGeracaoNome" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="40"/>
											</td>
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
			                              	<td width="33%"><strong>Data da &Uacute;ltima Emiss&atilde;o:</strong></td>
			                             	<td>
												<html:text property="dataUltimaEmissao" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="9"/>
			                              
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
           		<!-- Dados da Programa��o -->
				<logic:present name="achouDadosProgramacao">
				<tr>
					<td>
					<div id="layerHideProgramacao" style="display:block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('Programacao',true);" /> <b>Dados
							da Programa��o</b> </a> </span></td>
						</tr>
					</table>
					</div>

					<div id="layerShowProgramacao" style="display:none">

					<table width="100%" border="0" bgcolor="#99CCFF">

						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('Programacao',false);" /> <b>Dados
							da Programa��o</b> </a> </span></td>
						</tr>

						<tr bgcolor="#cbe5fe">

							<td>
							<!-- <table border="0" width="100%">

								<tr>
									<td width="33%" class="style3"><strong>Data da Programa��o:</strong></td>
									<td><html:text property="dataProgramacao" readonly="true"
										style="background-color:#EFEFEF; border:0;" size="10"
										maxlength="10" /></td>
								</tr>
								<tr>
									<td width="33%" class="style3"><strong>Equipe da Programa��o:</strong></td>
									<td><html:text property="equipeProgramacao" readonly="true"
										style="background-color:#EFEFEF; border:0;" size="30"
										maxlength="30" /></td>
								</tr>
							</table> -->

						<logic:present name="ConsultarDadosOrdemServicoPopupActionForm" property="collectionRoteiroOSDadosProgramacaoHelpers" scope="session">							
							<table width="100%" border="0" bgcolor="#90c7fc">
								<tr bgcolor="#90c7fc" height="18">
									<td align="center"><strong>Data Programa��o</strong></td>
									<td align="center"><strong>Equipe</strong></td>
									<td align="center"><strong>Motivo N�o Exec.</strong></td>
									<td align="center"><strong>Unidade</strong></td>
									<td align="center"><strong>Usu�rio</strong></td>
								</tr>
								
									<%int cont = 1;%>
									<logic:iterate id="helperProgramacao" name="ConsultarDadosOrdemServicoPopupActionForm" property="collectionRoteiroOSDadosProgramacaoHelpers" type="gcom.gui.atendimentopublico.ordemservico.RoteiroOSDadosProgramacaoHelper" scope="session">
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
								
								</table>
								</logic:present>
							
							</td>
						</tr>
					</table>
					</div>
					</td>
				</tr>
				</logic:present>
				<!-- Dados do Local Ocorrencia -->
				<logic:present name="achouDadosLocalOcorrencia">
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
										<a href="javascript:abrirPopup('exibirFotoOSAction.do?id=${numeroOS}', 600, 800)">											
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
				</logic:present>
           		<!-- Dados do Encerramento -->
				<c:if test="${ConsultarDadosOrdemServicoPopupActionForm.situacaoOSId == '2'}">
				<tr>
					<td>
           				<div id="layerHideEncerramento" style="display:block">
               				<table width="100%" border="0" bgcolor="#99CCFF">
		    					<tr bgcolor="#99CCFF">
                      				<td align="center">
                     					<span class="style2">
                      					<a href="javascript:extendeTabela('Encerramento',true);"/>
                      						<b>Dados do Encerramento da Ordem de Servi&ccedil;o</b>
                      					</a>
                     					</span>
                      				</td>
                     			</tr>
                    		</table>
           				</div>
                				                        		
                        <div id="layerShowEncerramento" style="display:none">

	                    	<table width="100%" border="0" bgcolor="#99CCFF">

								<tr bgcolor="#99CCFF">
		                    		<td align="center">
                       					<span class="style2">
                         					<a href="javascript:extendeTabela('Encerramento',false);"/>
                         						<b>Dados do Encerramento da Ordem de Servi&ccedil;o</b>
                         					</a>
                       					</span>
                         			</td>
                        		</tr>
                        		
                        		<tr bgcolor="#cbe5fe">
									
									<td>
									<table border="0" width="100%">

										<tr>
                                    		<td class="style3">
                                    			<strong>Data de Execu��o:</strong>
                                    		</td>

                                    		<td>
												<html:text property="dataExecucao" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="19"/>
											</td>
                                  		</tr>
										
										<tr>
                                    		<td class="style3">
                                    			<strong>Data do Encerramento:</strong>
                                    		</td>

                                    		<td>
												<html:text property="dataEncerramento" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="19"/>
											</td>
                                  		</tr>

		                            	<c:if test="${ConsultarDadosOrdemServicoPopupActionForm.parecerEncerramento != null}">
										<tr>
                                    		<td class="style3">
                                    			<strong>Parecer do Encerramento:</strong>
                                    		</td>

                                    		<td>
												<html:textarea property="parecerEncerramento" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													cols="40"/>
											</td>
                                  		</tr>
										</c:if>
										
		                            	<c:if test="${ConsultarDadosOrdemServicoPopupActionForm.areaPavimentacao != null}">
										<tr>
                                    		<td class="style3">
                                    			<strong>&Aacute;rea Pavimenta&ccedil;&atilde;o:</strong>
                                    		</td>

                                    		<td>
												<html:text property="areaPavimentacao" 
													readonly="true"
													style="background-color:#EFEFEF; border:0; text-align:right;" 
													size="9"/> 
												<strong>m&sup2;</strong>
											</td>
                                  		</tr>
                                  		</c:if>


										<tr>
                                    		<td class="style3">
                                    			<strong>Comercial Atualizado:</strong>
                                    		</td>

                                    		<td>
												<html:text property="comercialAtualizado" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="5"/>
											</td>
                                  		</tr>

										<tr>
                                    		<td class="style3">
                                    			<strong>Servi&ccedil;o Cobrado:</strong>
                                    		</td>

                                    		<td>
												<html:text property="servicoCobrado" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="5"/>
											</td>
                                  		</tr>

		                            	<c:if test="${ConsultarDadosOrdemServicoPopupActionForm.motivoNaoCobranca != null}">
										<tr>
                                    		<td class="style3">
                                    			<strong>Motivo da N&atilde;o Cobrado:</strong>
                                    		</td>

                                    		<td>
												<html:text property="motivoNaoCobranca" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="55"/>
											</td>
                                  		</tr>
                                  		</c:if>

		                            	<c:if test="${ConsultarDadosOrdemServicoPopupActionForm.motivoNaoCobranca == null}">
										<tr>
                                    		<td class="style3">
                                    			<strong>Percentual da Cobran&ccedil;a:</strong>
                                    		</td>
                                    		<td>
												<html:text property="percentualCobranca" 
													readonly="true"
													style="background-color:#EFEFEF; border:0; text-align:right;" 
													size="10"/> <strong>%</strong>
											</td>
                                  		</tr>

										<tr>
                                    		<td class="style3">
                                    			<strong>Valor Cobrado:</strong>
                                    		</td>
                                    		<td>
												<html:text property="valorCobrado" 
													readonly="true"
													style="background-color:#EFEFEF; border:0; text-align:right;" 
													size="10"/>
											</td>
                                  		</tr>
                                  		</c:if>


										<tr>
                                    		<td class="style3">
                                    			<strong>Unidade do Encerramento:</strong>
                                    		</td>

                                    		<td>
												<html:text property="unidadeEncerramentoId" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="6"/>

												<html:text property="unidadeEncerramentoDescricao" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="40"/>
											</td>

                                  		</tr>

										<tr>
                                    		<td class="style3">
                                    			<strong>Usu&aacute;rio do Encerramento:</strong>
                                    		</td>
                                    		<td>
												<html:text property="usuarioEncerramentoId" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="6"/>

												<html:text property="usuarioEncerramentoNome" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="40"/>
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
				<!-- Dados da Execu��o do Servi�o -->
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->
				<c:if
					test="${ConsultarDadosOrdemServicoPopupActionForm.possuiExecucaoServico == 'SIM'}">
					<tr>
						<td>
						<div id="layerHideExecucaoServico" style="display:block">
						<table width="100%" border="0" bgcolor="#99CCFF">
							<tr bgcolor="#99CCFF">
								<td align="center"><span class="style2"> <a
									href="javascript:extendeTabela('ExecucaoServico',true);" /> <b>Dados da Execu��o do Servi&ccedil;o</b></a></span></td>
							</tr>
						</table>
						</div>

						<div id="layerShowExecucaoServico" style="display:none">

						<table width="100%" border="0" bgcolor="#99CCFF">

							<tr bgcolor="#99CCFF">
								<td align="center"><span class="style2"> <a
									href="javascript:extendeTabela('ExecucaoServico',false);" /> <b>Dados da Execu��o do Servi&ccedil;o</b> </a> </span></td>
							</tr>

							<tr bgcolor="#cbe5fe">

								<td>
								<table border="0" width="100%">

									<tr>
										<td class="style3"><strong>Usu�rio da Execu��o:</strong></td>

										<td><html:text property="usuarioExecucaoId"
											readonly="true" style="background-color:#EFEFEF; border:0;"
											size="2" maxlength="2" /> <html:text
											property="usuarioExecucaoNome" readonly="true"
											style="background-color:#EFEFEF; border:0;" size="40"
											maxlength="40" /></td>
									</tr>

									<tr>
										<td class="style3"><strong>Leitura do Servi�o:</strong>
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
				<!-- FIM - Dados da Execu��o do Servi�o -->
				<!-- xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -->

           		<!-- Dados das Atividades -->
				<c:choose>
		
					<c:when test="${not empty ConsultarDadosOrdemServicoPopupActionForm.colecaoOSAtividade}">
           		
					<tr>
						<td>
	           				<div id="layerHideAtividade" style="display:block">
	               				<table width="100%" border="0" bgcolor="#99CCFF">
			    					<tr bgcolor="#99CCFF">
	                      				<td align="center">
	                     					<span class="style2">
	                      					<a href="javascript:extendeTabela('Atividade',true);"/>
	                      						<b>Dados das Atividades da Ordem de Servi&ccedil;o</b>
	                      					</a>
	                     					</span>
	                      				</td>
	                     			</tr>
	                    		</table>
	           				</div>
                				                        		
	                        <div id="layerShowAtividade" style="display:none">
	
		                    	<table width="100%" border="0" bgcolor="#99CCFF">
	
									<tr bgcolor="#99CCFF">
			                    		<td align="center">
	                       					<span class="style2">
	                         					<a href="javascript:extendeTabela('Atividade',false);"/>
	                         						<b>Dados das Atividades da Ordem de Servi&ccedil;o</b>
	                         					</a>
	                       					</span>
	                         			</td>
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
													<c:set var="count" value="0"/>
													<logic:iterate id="helper" name="ConsultarDadosOrdemServicoPopupActionForm" property="colecaoOSAtividade" type="gcom.atendimentopublico.ordemservico.bean.ObterDadosAtividadeIdOSHelper" scope="session">
						                     		  		<c:set var="count" value="${count+1}"/>
							                        		<c:choose>
							                        			<c:when test="${count%2 == '1'}">
							                        				<tr bgcolor="#FFFFFF">
							                        			</c:when>
							                        			<c:otherwise>
							                        				<tr bgcolor="#cbe5fe">
							                        			</c:otherwise>
							                        		</c:choose>											
																<td bordercolor="#90c7fc">
										                        	<div align="center">
																		<bean:write name="helper" property="atividade.descricao" />
																	</div>
																</td>
																<td bordercolor="#90c7fc">
										                        	<div align="center">
										                        		<c:if test="${helper.periodo==true}">
										                        			<a href="javascript:window.location.href='<html:rewrite page="/exibirDetalharPeriodoExecucaoEquipePopupAction.do?numeroOS=${helper.idOS}&atividadeId=${helper.atividade.id}"/>';">
										                        				<img title="Consultar Per�odos/Equipes" 
										                        					src="<bean:message key='caminho.imagens'/>relogioTransparente.gif" 
										                        					border="0"></a>
										                        		</c:if>
																	</div>	
																</td>
																<td bordercolor="#90c7fc">
										                        	<div align="center">
										                        		<c:if test="${helper.material==true}">
										                        			<a href="javascript:window.location.href='<html:rewrite page="/exibirDetalharMaterialUtilizadoPopupAction.do?numeroOS=${helper.idOS}&atividadeId=${helper.atividade.id}"/>';">
											                        			<img title="Consultar Materiais" 
											                        				src="<bean:message key='caminho.imagens'/>marteloTransparente3.gif" 
											                        				border="0"></a>
										                        		</c:if>
																	</div>	
																</td>
															</tr>
														</logic:iterate>
											</table>
										</td>       						
	                        		</tr>
	                        		
								</table>
	   						</div>
	   						
	  					</td>
	      			</tr>
      				</c:when>
      				
      			</c:choose>
				
				<tr>
					<td>
						<table width="100%">
			              	<tr>
			            		<td>
		              				<div align="left">
	                  					
	                  					<logic:present name="caminhoTelaPesquisaRetorno" scope="request">
	                  					
	                  						<input name="ButtonVoltar" 
	                  						type="button" 
	                  						class="bottonRightCol" 
	                  						value="Voltar" 
	                  						onclick="history.back();" style="width: 70px;">
	                  						
	                  					</logic:present>
	                  					<logic:present name="voltar">
											<input type="button" onclick="history.back()"
											class ="bottonRightCol" 
	                  						value="Voltar" style="width: 70px;">
										</logic:present>
	                  					
	                  						
	                  					<input name="ButtonFechar" 
	                  						type="button" 
	                  						class="bottonRightCol" 
	                  						value="Fechar" 
	                  						onclick="window.close();" style="width: 70px;">
		              				</div>
			              		</td>
				          	</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<!-- Fim do Corpo - Leonardo Regis -->
</html:form>
</body>
</html:html>