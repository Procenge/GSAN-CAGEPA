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

<%@ page import="gcom.util.ConstantesSistema"%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript">
	function consultarOS() {
		//abrirPopupDeNome('exibirConsultarRegistroAtendimentoOSAction.do?numeroRA=<bean:write name="ConsultarRegistroAtendimentoPopupActionForm" property="numeroRA" />', 550, 735,'SO','no');
		abrirPopup('exibirConsultarRegistroAtendimentoOSAction.do?botaoVoltar=sim&numeroRA=<bean:write name="ConsultarRegistroAtendimentoPopupActionForm" property="numeroRA" />', 550, 735);
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

	function consultarDebitos() {
		var form = document.forms[0];
		form.action = 'consultarDebitoAction.do?ehPopup=sim&caminhoRetornoTelaConsultaDebito=exibirConsultarRegistroAtendimentoPopupAction&codigoImovel='+form.matriculaImovel.value;
		form.submit();

	}
	
	function voltar(caminho){
		var form = document.forms[0];

		form.action = caminho;
		form.submit();
	}
	
</script>
</head>

<body leftmargin="5" topmargin="5"
	onload="window.focus();resizePageSemLink(630, 500);javascript:setarFoco('${requestScope.nomeCampo}');">

<html:form action="/exibirConsultarRegistroAtendimentoPopupAction" 
	name="ConsultarRegistroAtendimentoPopupActionForm"
	type="gcom.gui.atendimentopublico.registroatendimento.ConsultarRegistroAtendimentoPopupActionForm"
	method="post">

	<table width="600" border="0" cellspacing="5" cellpadding="0">

		<tr>

			<td width="615" valign="top" class="centercoltext">

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
						<td class="parabg">Consultar Dados do RA - Registro de Atendimento</td>
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

				<!--Inicio da Tabela Dados Gerais do RA - Registro de Atendimento -->
            	<table width="100%" border="0">

            	<tr>
                	<td height="31">
                    <table width="100%" border="0" align="center">

                        <tr bgcolor="#cbe5fe">

                      		<td align="center">
                      		<table width="100%" border="0" bgcolor="#99CCFF">

					    		<tr bgcolor="#99CCFF">
                         			<td height="18" colspan="2">
                         				<div align="center">
                         					<span class="style2"><b>Dados Gerais do RA - Registro de Atendimento </b></span>
                         				</div>
                         			</td>
                        		</tr>

								<tr bgcolor="#cbe5fe">
									<td>
									<table border="0" width="100%">

		                            	<tr>
		                              		<td height="10"><strong>N&uacute;mero do RA:</strong></td>
		                              		
		                              		<td width="10%"> 
												<html:text property="numeroRA" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="9"
													maxlength="9" />
		                                  	</td>
			                              	
			                              	<td width="34%"><strong>Situa&ccedil;&atilde;o do RA:</strong></td>
			                              	<td width="25%">
												<html:text property="situacaoRA" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="15"
													maxlength="15" />
			                                </td>
		                            	</tr>



										<logic:present name="existeRaAssociado" scope="request">

			                            	<tr> 
			                              		<td height="10"><strong>N&uacute;mero do RA Associado:</strong></td>
			                              		<td> 
													<html:text property="numeroRaAssociado" readonly="true"
														style="background-color:#EFEFEF; border:0;" size="9"
														maxlength="9" />
			                                	</td>
			                              		
			                              		<td><strong>Situa&ccedil;&atilde;o do RA Associado:</strong></td>
			                              		<td>
													<html:text property="situacaoRaAssociado" readonly="true"
														style="background-color:#EFEFEF; border:0;" size="9"
														maxlength="9" />
	
			                                	</td>
			                            	</tr>

										</logic:present>

										<tr> 
		                              		<td class="style3"><strong>Número Manual:</strong></td>
		                              		<td colspan="3">
												<html:text property="numeroRAManual" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="12"
													maxlength="11" />
		                              		
		                                	</td>
		                            	</tr>
<tr>
		                            	<td class="style3"><strong>RA Reiterado:</strong></td>
		                            	<td><a 
									href="javascript:abrirPopup('exibirConsultarRegistroAtendimentoPopupAction.do?numeroRA='+${ConsultarRegistroAtendimentoPopupActionForm.raReiterado}, 400, 800);">
									${ConsultarRegistroAtendimentoPopupActionForm.raReiterado}</a>
		                            	</td>
		                            	</tr>
		                            	<tr> 
		                              		<td class="style3"><strong>Tipo de Solicita&ccedil;&atilde;o:</strong></td>
		                              		<td colspan="3">
												<html:text property="idTipoSolicitacao" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="4"
													maxlength="4" />

												<html:text property="tipoSolicitacao" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="50"
													maxlength="50" />
		                                	</td>
		                            	</tr>

		                            	<tr> 
		                              		<td height="10"><strong>Especifica&ccedil;&atilde;o:</strong></td>
		                              		<td colspan="3"> 
												<html:text property="idEspecificacao" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="4"
													maxlength="4" />

												<html:text property="especificacao" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="50"
													maxlength="50" />
		                                 	</td>
		                            	</tr>

			                            <tr>
			                              	<td height="10"><strong>Tipo de Atendimento:</strong></td>
											<td>
												<html:radio property="tipoAtendimento" value="<%=""+ConstantesSistema.SIM%>" disabled="true"/>
												<strong>on-line</strong>
											</td>
											<td>
												<html:radio property="tipoAtendimento" value="<%=""+ConstantesSistema.NAO%>" disabled="true"/>
												<strong>manual</strong>
											</td>
			                            </tr>

			                            <tr> 
			                              	<td width="31%" height="10"><strong>Data do Atendimento:</strong></td>
			                              	<td colspan="3"> 
												<html:text property="dataAtendimento" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="10"
													maxlength="10" />
											</td>
			                            </tr>

			                            <tr> 
			                              	<td class="style3"><strong>Hora do Atendimento:</strong></td>
			                              	<td colspan="3">
												<html:text property="horaAtendimento" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="5"
													maxlength="5" />
			                             	</td>
			                            </tr>

			                            <tr> 
		                              		<td height="10"><strong>Tempo de Espera para Atendimento:</strong></td>
			                              	<td colspan="3"> 
											<strong>
												<html:text property="tempoEsperaInicio" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="5"
													maxlength="5" />
												
												&agrave;s 
											
												<html:text property="tempoEsperaTermino" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="5"
													maxlength="5" />
			                              	</strong>
											</td>
			                            </tr>

			                            <tr> 
			                              	<td class="style3"><strong>Data Prevista:</strong></td>
			                              	<td colspan="3">
												<html:text property="dataPrevista" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="16"
													maxlength="16" />
											
											</td>

			                            </tr>

			                            <tr>
			                              	<td class="style3"><strong>Meio de Solicita&ccedil;&atilde;o:</strong></td>
			                              	<td colspan="3">

												<html:text property="idMeioSolicitacao" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="4"
													maxlength="4" />
			                              	
												<html:text property="meioSolicitacao" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="50"
													maxlength="50" />
											</td>
			                            </tr>

			                            <tr> 
			                              	<td class="style3"><strong>Unidade de Atendimento:</strong></td>
			                              	<td colspan="3">
												<html:text property="idUnidadeAtendimento" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="4"
													maxlength="4" />

												<html:text property="unidadeAtendimento" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="40"
													maxlength="40" />
											</td>
			                            </tr>
			                            
			                            <tr> 
			                              	<td class="style3"><strong>Usuário:</strong></td>
			                              	<td colspan="3">
												<html:text property="idUsuario" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="4"
													maxlength="4" />

												<html:text property="usuario" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="40"
													maxlength="40" />
											</td>
			                            </tr>

			                            <tr> 
			                              	<td class="style3"><strong>Unidade Atual:</strong></td>
			                              	<td colspan="3">
												<html:text property="idUnidadeAtual" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="4"
													maxlength="4" />

												<html:text property="unidadeAtual" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="40"
													maxlength="40" />
											</td>
			                            </tr>

			                            <tr> 
			                              	<td class="style3"><strong>Observa&ccedil;&atilde;o:</strong></td>
			                             	<td colspan="3">
												<html:textarea property="observacao" readonly="true" rows="5"
													style="background-color:#EFEFEF; border:0;" cols="50"/>
			                              
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

				<!-- Dados do Local Ocorrencia -->

               	<tr bgcolor="#cbe5fe">
           			<td align="center">
       					<div id="layerHideLocal" style="display:block">

           					<table width="100%" border="0" bgcolor="#99CCFF">
	    						<tr bgcolor="#99CCFF">
                     				<td align="center">
                    					<span class="style2">
                     					<a href="javascript:extendeTabela('Local',true);"/>
                     						<b>Dados do Local da Ocorr&ecirc;ncia</b>
                     					</a>
                    					</span>
                     				</td>
                    			</tr>
                   			</table>
           				</div>
             				                        		
                  		<div id="layerShowLocal" style="display:none">

	                   		<table width="100%" border="0" bgcolor="#99CCFF">
		    					<tr bgcolor="#99CCFF">
	                     			<td align="center">
                    					<span class="style2">
                     					<a href="javascript:extendeTabela('Local',false);"/>
                     						<b>Dados do Local da Ocorr&ecirc;ncia</b>
                     					</a>
                    					</span>
	                     			</td>
	                    		</tr>
		
								<tr bgcolor="#cbe5fe">
									<td>
									<table border="0" width="100%">

	                              		<tr>
	                                		<td>
	                                			<strong>Matr&iacute;cula do Im&oacute;vel:</strong>
	                                		</td>
	                                		
	                                		<td> 
												<html:text property="matriculaImovel" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="8"
													maxlength="8" />
				
												<html:text property="inscricaoImovel" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="21"
													maxlength="21" />

												<c:choose>
	                								<c:when test="${ConsultarRegistroAtendimentoPopupActionForm.matriculaImovel != null}">
								                		<input name="ButtonDebitos" 
								                			type="button" 
								                			class="bottonRightCol"  
								                			value="Consultar D&eacute;bitos" 
								                			onClick="javascript:consultarDebitos();">
								                	</c:when>
								                	<c:otherwise>
								                		<input name="ButtonDebitos" 
								                			type="button" 
								                			class="bottonRightCol"  
								                			value="Consultar D&eacute;bitos" 
								                			disabled="true"
								                			onClick="javascript:consultarDebitos();">
								                	</c:otherwise>
								                </c:choose>
						                		
						                		
											</td>
											
                              			</tr>
                              			<tr>
	                                		<td>
	                                			<strong>Rota:</strong>
	                                		</td>
	                                		
	                                		<td> 
												<html:text property="rota" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="8"
													maxlength="8" />
											&nbsp;<strong>Sequencial Rota:</strong>&nbsp;
												<html:text property="sequencialRota" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="8"
													maxlength="8" />
											</td>
											
                              			</tr>

	                              		<tr> 
	                                		<td height="10" colspan="4"> 
	                                		<div align="right"> 
	                                    	<hr>
	                                  		</div>
	                                  		<div align="right"> </div></td>
	                              		</tr>


		                               	<tr> 
			                              	<td class="style3">
			                              		<strong><span class="style2">Endere&ccedil;o da Ocorr&ecirc;ncia:</span></strong>
			                              	</td>
		                                  
		                                  	<td>
												<html:textarea property="enderecoOcorrencia" readonly="true"
												style="background-color:#EFEFEF; border:0;" cols="50"/>
		                                  	</td>
		                            	</tr>
                            	
		                               	<tr>
		                                 	<td class="style3"><strong>Ponto de Refer&ecirc;ncia:</strong></td>
		                                 	
		                                 	<td>
												<html:text property="pontoReferencia" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="60"
													maxlength="60" />
		                                 	</td>
		                               	</tr>

	                              		<tr> 
	                                		<td class="style3">
	                                			<strong>Munic&iacute;pio:</strong>
	                                		</td>
	                                		<td>
												<html:text property="idMunicipio" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="4"
													maxlength="4" />
				
												<html:text property="municipio" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="40"
													maxlength="40" />

											</td>
                              			</tr>

	                              		<tr> 
	                                		<td class="style3">
	                                			<strong>Bairro:</strong>
	                                		</td>
	                                		<td>
												<html:text property="idBairro" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="4"
													maxlength="4" />
				
												<html:text property="bairro" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="40"
													maxlength="40" />
											</td>
	                              		</tr>
	
	                              		<tr> 
	                                		<td class="style3">
	                                			<strong>&Aacute;rea do Bairro:</strong>
	                                		</td>
	                                		<td>
												<html:text property="idAreaBairro" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="4"
													maxlength="4" />
				
												<html:text property="areaBairro" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="40"
													maxlength="40" />

											</td>
                              			</tr>

	                              		<tr> 
	                                		<td height="10" colspan="4"> 
	                                		<div align="right"> 
	                                    		<hr>
	                                  		</div>
	                                  		<div align="right"> </div>
	                                  		</td>
	                              		</tr>
										
										<tr> 	
	                                		<td class="style3">
	                                			<strong>Localidade:</strong>
	                                		</td>
	                                		<td>
												<html:text property="idLocalidade" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="4"
													maxlength="4" />
				
												<html:text property="localidade" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="40"
													maxlength="40" />
											</td>
                              			</tr>
                            		
										<tr> 	
	                                		<td class="style3">
	                                			<strong>Setor Comercial:</strong>
	                                		</td>
	                                		<td colspan="3">
												<html:text property="idSetorComercial" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="4"
													maxlength="4" />
													
												<html:text property="setorComercial" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="40"
													maxlength="40" />
													
											</td>
	                              		</tr>
                              
										<tr> 	
	                                		<td class="style3">
	                                			<strong>Quadra:</strong>
	                                		</td>
	                                		<td colspan="3">
												<html:text property="idQuadra" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="4"
													maxlength="5" />
											</td>
	                              		</tr>

										<tr> 	
	                                		<td class="style3">
	                                			<strong>Divis&atilde;o de Esgoto:</strong>
	                                		</td>
	                                		<td colspan="3">
												<html:text property="idDivisaoEsgoto" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="4"
													maxlength="4" />
				
												<html:text property="divisaoEsgoto" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="40"
													maxlength="40" />
											</td>
	                              		</tr>
                              
	                              		<tr>
	                                		<td height="10" colspan="4"> 
	                                			<div align="right">
	                                    			<hr>
	                                  			</div>
	                                  			<div align="right"> </div>
	                                  		</td>
	                              		</tr>
	                            		
	                              		<tr> 
		                                 	<td class="style3"><strong>Local da Ocorr&ecirc;ncia:</strong></td>
	    	                             	<td colspan="3">
												<html:text property="localOcorrencia" readonly="true"
												style="background-color:#EFEFEF; border:0;" size="50"
												maxlength="50" />
	                                
		                                 	</td>
	                              		</tr>
                              
		                               	<tr> 
	   	                              		<td class="style3"><strong>Pavimento da Rua:</strong></td>
	        	                          	<td>
												<html:text property="pavimentoRua" readonly="true"
												style="background-color:#EFEFEF; border:0;" size="20"
												maxlength="20" />
	
												<strong>Pavimento da Cal&ccedil;ada:</strong>
				
												<html:text property="pavimentoCalcada" readonly="true"
												style="background-color:#EFEFEF; border:0;" size="20"
												maxlength="20" />
	                                  		</td>
	                               		</tr>
	
		                               	<tr> 
		                               		<td height="10" colspan="4"> <div align="right"> 
		                                     <hr>
		                                   </div>
		                                   <div align="right"> </div></td>
		                               	</tr>

                              		<tr> 
                                		<td class="style3"><strong>Descri&ccedil;&atilde;o 
                                  		do Local da Ocorr&ecirc;ncia:</strong></td>

                                		<td colspan="3">
											<html:textarea property="descricaoLocalOcorrencia" readonly="true"
											style="background-color:#EFEFEF; border:0;" cols="50"/>
                                		</td>
                              		</tr>
									</table>
								</td>
							</tr>
							</table>
        				</div>
					</td>
				</tr>
				
				<!-- Dados do Solicitante -->
				<tr>
					<td>
           				<div id="layerHideSolicitante" style="display:block">
               				<table width="100%" border="0" bgcolor="#99CCFF">
		    					<tr bgcolor="#99CCFF">
                      				<td align="center">
                     					<span class="style2">
                      					<a href="javascript:extendeTabela('Solicitante',true);"/>
                      						<b>Dados do Solicitante</b>
                      					</a>
                     					</span>
                      				</td>
                     			</tr>
                    		</table>
           				</div>
                   				                        		
                        <div id="layerShowSolicitante" style="display:none">

	                    	<table width="100%" border="0" bgcolor="#99CCFF">

								<tr bgcolor="#99CCFF">
		                    		<td align="center">
                       					<span class="style2">
                         					<a href="javascript:extendeTabela('Solicitante',false);"/>
                         						<b>Dados do Solicitante</b>
                         					</a>
                       					</span>
                         			</td>
                        		</tr>

								<tr bgcolor="#cbe5fe">
									<td>
									<table border="0" width="100%">

										<tr> 	
                                    		<td class="style3">
                                    			<strong>Cliente Solicitante:</strong>
                                    		</td>
                                    		<td colspan="3">
												<html:text property="idClienteSolicitante" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="6"
													maxlength="6" />
	
												<html:text property="clienteSolicitante" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="40"
													maxlength="40" />

											</td>
                                  		</tr>
		                                  		
										<tr> 	
                                    		<td class="style3">
                                    			<strong>Unidade Solicitante:</strong>
                                    		</td>
                                    		<td colspan="3">
												<html:text property="idUnidadeSolicitante" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="4"
													maxlength="4" />

												<html:text property="unidadeSolicitante" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="40"
													maxlength="40" />

											</td>
                                  		</tr>

										<tr>
                                    		<td class="style3">
                                    			<strong>Funcion&aacute;rio Respons&aacute;vel:</strong>
                                    		</td>
                                    		<td colspan="3">
												<html:text property="idFuncionarioResponsavel" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="4"
													maxlength="4" />

												<html:text property="funcionarioResponsavel" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="40"
													maxlength="40" />

											</td>
                                  		</tr>
		                                  		
										<tr>
                                    		<td class="style3">
                                    			<strong>Nome do Solicitante:</strong>
                                    		</td>
                                    		<td colspan="3">
												<html:text property="nomeSolicitante" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="40"
													maxlength="40" />
											</td>
                                  		</tr>

	                                  	<tr> 
	                                  		<td height="10" colspan="4"> 
	                                  			<div align="right"> <hr>
	                                      		</div>
	                                      		<div align="right"> </div>
	                                      	</td>
	                                  	</tr>
		                                  		
	                                  	<tr> 
		                                	<td class="style3">
		                                		<strong><span class="style2">Endere&ccedil;o da Ocorr&ecirc;ncia:</span></strong>
		                                	</td>
		                                    
		                                    <td>
												<html:textarea property="enderecoSolicitante" readonly="true"
													style="background-color:#EFEFEF; border:0;" cols="50" />
		                                    </td>
		                              	</tr>
		                              	
	                                  	<tr>
	                                    	<td class="style3"><strong>Ponto de Refer&ecirc;ncia:</strong></td>
	                                    	
	                                    	<td>
												<html:text property="pontoReferenciaSolicitante" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="60"
													maxlength="60" />
	                                    	</td>
	                                  	</tr>

	                                  	<tr> 
	                                  		<td height="10" colspan="4"> 
	                                  			<div align="right"> <hr>
	                                      		</div>
	                                      		<div align="right"> </div>
	                                      	</td>
	                                  	</tr>
                                  		
	                                  	<tr>
	                                    	<td class="style3"><strong>Fone do Solicitante:</strong></td>
	                                    	
	                                    	<td>
												<html:text property="foneDDD" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="60"
													maxlength="2" />
												<html:text property="fone" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="60"
													maxlength="9" />
												<html:text property="foneRamal" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="60"
													maxlength="4" />


	                                    	</td>
	                                  	</tr>
		                                  		
										</table>
									</td>
                            	</tr>
								</table>
       						
       						</div>
       						</td>
      						</tr>

							<!-- Dados da Ultima Tramitação -->
							
							<tr>
								<td>
			           				<div id="layerHideTramitacao" style="display:block">
			               				<table width="100%" border="0" bgcolor="#99CCFF">
					    					<tr bgcolor="#99CCFF">
			                      				<td align="center">
			                     					<span class="style2">
			                      					<a href="javascript:extendeTabela('Tramitacao',true);"/>
			                      						<b>Dados da &Uacute;ltima Tramita&ccedil;&atilde;o</b>
			                      					</a>
			                     					</span>
			                      				</td>
			                     			</tr>
			                    		</table>
			           				</div>
                   				                        		
			                        <div id="layerShowTramitacao" style="display:none">
			
				                    	<table width="100%" border="0" bgcolor="#99CCFF">
			
											<tr bgcolor="#99CCFF">
					                    		<td align="center">
			                       					<span class="style2">
			                         					<a href="javascript:extendeTabela('Tramitacao',false);"/>
			                         						<b>Dados da &Uacute;ltima Tramita&ccedil;&atilde;o</b>
			                         					</a>
			                       					</span>
			                         			</td>
			                        		</tr>

											<tr bgcolor="#cbe5fe">
												<td>
												<table border="0" width="100%">
			
													<tr> 	
			                                    		<td class="style3">
			                                    			<strong>Unidade de Origem:</strong>
			                                    		</td>
			                                    		<td>
															<html:text property="idUnidadeOrigem" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="4"
																maxlength="4" />
				
															<html:text property="unidadeOrigem" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="40"
																maxlength="40" />
														</td>
			                                  		</tr>
			
													<tr> 	
			                                    		<td class="style3">
			                                    			<strong>Unidade Atual:</strong>
			                                    		</td>
			                                    		<td>
															<html:text property="idUnidadeAtualTramitacao" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="4"
																maxlength="4" />
				
															<html:text property="unidadeAtualTramitacao" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="40"
																maxlength="40" />
														</td>
			                                  		</tr>
			
													<tr> 	
			                                    		<td class="style3">
			                                    			<strong>Data do Tr&acirc;mite:</strong>
			                                    		</td>
			                                    		<td colspan="3">
															<html:text property="dataTramite" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="10"
																maxlength="10" />
														</td>
			                                  		</tr>
			
													<tr> 	
			                                    		<td class="style3">
			                                    			<strong>Hora do Tr&acirc;mite:</strong>
			                                    		</td>
			                                    		<td colspan="3">
															<html:text property="horaTramite" 
																readonly="true"
																style="background-color:#EFEFEF; border:0;" 
																size="5"
																maxlength="5" />
														</td>
			                                  		</tr>
			
													<tr> 	
			                                    		<td class="style3">
			                                    			<strong>Usu&aacute;rio Respons&aacute;vel:</strong>
			                                    		</td>
			                                    		<td colspan="3">
															<html:text property="idUsuarioResponsavel" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="6"
																maxlength="6" />
				
															<html:text property="usuarioResponsavel" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="40"
																maxlength="40" />
														</td>
			                                  		</tr>
			
													<tr> 	
			                                    		<td class="style3">
			                                    			<strong>Parecer do Tr&acirc;mite:</strong>
			                                    		</td>
			                                    		<td colspan="3">
															<html:textarea property="parecerTramite" readonly="true"
																style="background-color:#EFEFEF; border:0;" cols="50" />
				
														</td>
			                                  		</tr>
													<tr> 	
			                                    		<td class="style3">
			                                    			<strong>Motivo do Tr&acirc;mite:</strong>
			                                    		</td>
			                                    		<td colspan="3">
															<html:textarea property="motivoTramite" readonly="true"
																style="background-color:#EFEFEF; border:0;" cols="50" />
				
														</td>
			                                  		</tr>

												</table>
			       								</td>       						
			       							<tr>
										</table>
			   						</div>
			  					</td>
			      			</tr>
			      			
							<!-- Dados da Ultima Reiteração -->
							
							<tr>
								<td>
			           				<div id="layerHideReiteracao" style="display:block">
			               				<table width="100%" border="0" bgcolor="#99CCFF">
					    					<tr bgcolor="#99CCFF">
			                      				<td align="center">
			                     					<span class="style2">
			                      					<a href="javascript:extendeTabela('Reiteracao',true);"/>
			                      						<b>Dados de Reitera&ccedil;&atilde;o</b>
			                      					</a>
			                     					</span>
			                      				</td>
			                     			</tr>
			                    		</table>
			           				</div>
                   				                        		
			                        <div id="layerShowReiteracao" style="display:none">
			
				                    	<table width="100%" border="0" bgcolor="#99CCFF">
			
											<tr bgcolor="#99CCFF">
					                    		<td align="center">
			                       					<span class="style2">
			                         					<a href="javascript:extendeTabela('Reiteracao',false);"/>
			                         						<b>Dados de Reitera&ccedil;&atilde;o</b>
			                         					</a>
			                       					</span>
			                         			</td>
			                        		</tr>
			                        		
			                        		<tr bgcolor="#cbe5fe">
												
												<td>
												<table border="0" width="100%">

													<tr>
			                                    		<td width="25%">
			                                    			<strong>Quantidade:</strong>
			                                    		</td>

			                                    		<td>
			                                    				<a href="javascript:abrirPopup('<html:rewrite page="/exibirConsultarRegistroAtendimentoReiteradosAction.do?numeroRA=${ConsultarRegistroAtendimentoPopupActionForm.numeroRA}&botaoTipo=voltar"/>', 400, 600);">
																	<bean:write name="ConsultarRegistroAtendimentoPopupActionForm" property="quantidade" />
																</a>
														</td>
			                                  		</tr>
													<tr>
			                                    		<td>
			                                    			<strong>Data &Uacute;ltima Reitera&ccedil;&atilde;o:</strong>
			                                    		</td>
			                                    		<td>
															<html:text property="dataUltimaReiteracao" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="10"
																maxlength="10" />
														</td>
			                                  		</tr>
													<tr>
			                                    		<td>
			                                    			<strong>Hora &Uacute;ltima Reitera&ccedil;&atilde;o:</strong>
			                                    		</td>

			                                    		<td>
															<html:text property="horaUltimaReiteracao" 
																readonly="true"
																style="background-color:#EFEFEF; border:0;" 
																size="5"
																maxlength="5" />
														</td>
			                                  		</tr>
												
												</table>
			       								</td>       						
			                        		</tr>
			                        		
										</table>
			   						</div>
			   						
			  					</td>
			      			</tr>
			      			
							<!-- Dados da Reativação -->
							
							<tr>
								<td>
			           				<div id="layerHideReativacao" style="display:block">
			               				<table width="100%" border="0" bgcolor="#99CCFF">
					    					<tr bgcolor="#99CCFF">
			                      				<td align="center">
			                     					<span class="style2">
			                      					<a href="javascript:extendeTabela('Reativacao',true);"/>
			                      						<b>Dados da Reativa&ccedil;&atilde;o</b>
			                      					</a>
			                     					</span>
			                      				</td>
			                     			</tr>
			                    		</table>
			           				</div>
                   				                        		
			                        <div id="layerShowReativacao" style="display:none">
			
				                    	<table width="100%" border="0" bgcolor="#99CCFF">
			
											<tr bgcolor="#99CCFF">
					                    		<td align="center">
			                       					<span class="style2">
			                         					<a href="javascript:extendeTabela('Reativacao',false);"/>
			                         						<b>Dados da Reativa&ccedil;&atilde;o</b>
			                         					</a>
			                       					</span>
			                         			</td>
			                        		</tr>
			                        		
			                        		<tr bgcolor="#cbe5fe">
												
												<td>
												<table border="0" width="100%">

													<tr>
			                                    		<td class="style3">
			                                    			<strong>N&uacute;mero do RA Atual:</strong>
			                                    		</td>

			                                    		<td>
															<html:text property="numeroRaAtual" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="9"
																maxlength="9" />
														</td>
			                                  		</tr>

													<tr>
			                                    		<td class="style3">
			                                    			<strong>Situa&ccedil;&atilde;o do RA Atual:</strong>
			                                    		</td>

			                                    		<td>
															<html:text property="situacaoRaAtual" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="9"
																maxlength="9" />
														</td>
			                                  		</tr>

													<tr>
			                                    		<td class="style3">
			                                    			<strong>Motivo da Reativa&ccedil;&atilde;o:</strong>
			                                    		</td>

			                                    		<td>
															<html:text property="idMotivoReativacao" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="2"
																maxlength="2" />

															<html:text property="motivoReativacao" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="40"
																maxlength="40" />

														</td>

			                                  		</tr>

													<tr>
			                                    		<td class="style3">
			                                    			<strong>Data da Reativa&ccedil;&atilde;o:</strong>
			                                    		</td>

			                                    		<td>
															<html:text property="dataReativacao" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="10"
																maxlength="10" />
														</td>
			                                  		</tr>

													<tr>
			                                    		<td class="style3">
			                                    			<strong>Hora da Reativa&ccedil;&atilde;o:</strong>
			                                    		</td>

			                                    		<td>
															<html:text property="horaReativacao" 
																readonly="true"
																style="background-color:#EFEFEF; border:0;" 
																size="5"
																maxlength="5" />
														</td>
			                                  		</tr>

													<tr>
			                                    		<td class="style3">
			                                    			<strong>Data Prevista do RA Atual:</strong>
			                                    		</td>

			                                    		<td>
															<html:text property="dataPrevistaRaAtual" 
																readonly="true"
																style="background-color:#EFEFEF; border:0;" 
																size="16"
																maxlength="16" />
														</td>
			                                  		</tr>

													<tr>
			                                    		<td class="style3">
			                                    			<strong>Unidade da Reativa&ccedil;&atilde;o:</strong>
			                                    		</td>

			                                    		<td>
															<html:text property="idUnidadeReativacao" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="4"
																maxlength="4" />

															<html:text property="unidadeReativacao" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="40"
																maxlength="40" />

														</td>

			                                  		</tr>

													<tr>
			                                    		<td class="style3">
			                                    			<strong>Unidade do RA Atual:</strong>
			                                    		</td>

			                                    		<td>
															<html:text property="idUnidadeRaAtual" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="4"
																maxlength="4" />

															<html:text property="unidadeRaAtual" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="40"
																maxlength="40" />

														</td>

			                                  		</tr>

													<tr>
			                                    		<td class="style3">
			                                    			<strong>Observa&ccedil;&atilde;o:</strong>
			                                    		</td>

			                                    		<td>
															<html:textarea property="observacaoReativacao" readonly="true"
																style="background-color:#EFEFEF; border:0;" cols="40"/>
														</td>
			                                  		</tr>
												
												</table>
			       								</td>       						
			                        		</tr>
			                        		
										</table>
			   						</div>
			   						
			  					</td>
			      			</tr>			      			

							<!-- Dados do Encerramento -->
							
							<tr>
								<td>
			           				<div id="layerHideEncerramento" style="display:block">
			               				<table width="100%" border="0" bgcolor="#99CCFF">
					    					<tr bgcolor="#99CCFF">
			                      				<td align="center">
			                     					<span class="style2">
			                      					<a href="javascript:extendeTabela('Encerramento',true);"/>
			                      						<b>Dados do Encerramento</b>
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
			                         						<b>Dados do Encerramento</b>
			                         					</a>
			                       					</span>
			                         			</td>
			                        		</tr>
			                        		
			                        		<tr bgcolor="#cbe5fe">
												
												<td>
												<table border="0" width="100%">

													<tr>
			                                    		<td class="style3">
			                                    			<strong>Motivo do Encerramento:</strong>
			                                    		</td>

			                                    		<td>
															<html:text property="idMotivoEncerramento" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="5"
																maxlength="5" />

															<html:text property="motivoEncerramento" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="50"
																maxlength="50" />

														</td>

			                                  		</tr>

													<tr>
			                                    		<td class="style3">
			                                    			<strong>N&uacute;mero do RA de Refer&ecirc;ncia:</strong>
			                                    		</td>

			                                    		<td>
															<html:text property="numeroRaReferencia" 
																readonly="true"
																style="background-color:#EFEFEF; border:0;" 
																size="9"
																maxlength="9" />
														</td>
			                                  		</tr>

													<tr>
			                                    		<td class="style3">
			                                    			<strong>Situa&ccedil;&atilde;o do RA Refer&ecirc;ncia:</strong>
			                                    		</td>

			                                    		<td>
															<html:text property="situacaoRaReferencia" 
																readonly="true"
																style="background-color:#EFEFEF; border:0;" 
																size="9"
																maxlength="9" />
														</td>
			                                  		</tr>

													<tr>
			                                    		<td class="style3">
			                                    			<strong>Data da Encerramento:</strong>
			                                    		</td>

			                                    		<td>
															<html:text property="dataEncerramento" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="10"
																maxlength="10" />
														</td>
			                                  		</tr>

													<tr>
			                                    		<td class="style3">
			                                    			<strong>Hora do Encerramento:</strong>
			                                    		</td>

			                                    		<td>
															<html:text property="horaEncerramento" 
																readonly="true"
																style="background-color:#EFEFEF; border:0;" 
																size="5"
																maxlength="5" />
														</td>
			                                  		</tr>

													<tr>
			                                    		<td class="style3">
			                                    			<strong>Data Prevista:</strong>
			                                    		</td>

			                                    		<td>
															<html:text property="dataPrevistaEncerramento" 
																readonly="true"
																style="background-color:#EFEFEF; border:0;" 
																size="16"
																maxlength="16" />
														</td>
			                                  		</tr>

													<tr>
			                                    		<td class="style3">
			                                    			<strong>Unidade do Encerramento:</strong>
			                                    		</td>

			                                    		<td>
															<html:text property="idUnidadeEncerramento" 
																readonly="true"
																style="background-color:#EFEFEF; border:0;" 
																size="4"
																maxlength="4" />

															<html:text property="unidadeEncerramento" 
																readonly="true"
																style="background-color:#EFEFEF; border:0;" 
																size="40"
																maxlength="40" />

														</td>

			                                  		</tr>

													<tr>
			                                    		<td class="style3">
			                                    			<strong>Usu&aacute;rio do Encerramento:</strong>
			                                    		</td>

			                                    		<td>
															<html:text property="idUsuarioEncerramento" 
																readonly="true"
																style="background-color:#EFEFEF; border:0;" 
																size="2"
																maxlength="2" />

															<html:text property="usuarioEncerramento" 
																readonly="true"
																style="background-color:#EFEFEF; border:0;" 
																size="40"
																maxlength="40" />

														</td>
			                                  		</tr>

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
												
												</table>
			       								</td>       						
			                        		</tr>
			                        		
										</table>
			   						</div>
			   						
			  					</td>
			      			</tr>						      			

							<tr>
								<td>
			
								<table width="100%" border="0">
						          	
									<tr>
										<td>
										
										<logic:notEmpty name="caminhoRetornoTelaConsultaRA">
											
	
									          	<input type="button" 
									          		class="bottonRightCol" 
									          		value="Voltar" 
									          		onclick="javascript:voltar('<bean:write name="caminhoRetornoTelaConsultaRA"/>');" style="width: 70px;"/>
									          		
													
											
										</logic:notEmpty>
										
										<!-- Colocado especialmente para trabalhar com o retorno da pesquisa de OS
										Raphael Rossiter em 13/02/2007 -->
										<logic:present name="caminhoTelaPesquisaRetorno" scope="request">
	                  					
	                  						<input name="ButtonVoltar" 
	                  						type="button" 
	                  						class="bottonRightCol" 
	                  						value="Voltar" 
	                  						onclick="history.back();" style="width: 70px;">
	                  						
	                  					</logic:present>
										
										<input name="ButtonFechar" 
			                  				type="button" 
			                  				class="bottonRightCol" 
			                  				value="Fechar" 
			                  				onclick="window.close();" style="width: 70px;">
					            		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					            		<input name="ButtonOrdens" 
					                  			type="button" 
					                  			class="bottonRightCol" 
					                  			value="Consultar O.S" 
					                  			onClick="javascript:consultarOS();">
				              			</td>
										
									</tr>
						          	
									
								</table>
			
								</td>
			
							</tr>


						</table>
  					</td>
				</tr>
				
				
   		
   		
	</table>
	<!-- Fim do Corpo - Rafael Pinto -->

</html:form>
</body>
</html:html>