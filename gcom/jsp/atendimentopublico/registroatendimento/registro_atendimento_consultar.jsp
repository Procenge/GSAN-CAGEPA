<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<%@ page import="gcom.util.ConstantesSistema"%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<script language="JavaScript">

	function validaForm(){

		var form = document.forms[0];
		
		if(form.isRAReativado.value == '' || form.isRAReativado.value == '0'){
		
		//Pendente
		if(form.codigoSituacao.value == 1){
		
			form.ButtonReativar.disabled=true;

		//Encerrado
		}else if(form.codigoSituacao.value == 2){

			form.ButtonAtualizar.disabled=true;
			form.ButtonReiterar.disabled=true;
			form.ButtonTramitar.disabled=true;
			form.ButtonEncerrar.disabled=true;
			form.ButtonOrdemSer.disabled=true;

		//Bloqueado
		}else{

			//form.ButtonAtualizar.disabled=true;
			form.ButtonReiterar.disabled=true;
			form.ButtonTramitar.disabled=true;
			form.ButtonOrdemSer.disabled=true;
			form.ButtonReativar.disabled=true;
			form.ButtonOrdens.disabled=true;
			form.ButtonSolicitantes.disabled=true;
			form.ButtonTramites.disabled=true;
		}
		}
		
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

	function consultarTramites() {
		var form = document.forms[0];
		abrirPopup('exibirConsultarRegistroAtendimentoTramiteAction.do?numeroRA='+form.numeroRAPesquisado.value, 550, 735);
	}

	function consultarSolicitantes() {
		var form = document.forms[0];
		abrirPopup('exibirConsultarRegistroAtendimentoSolicitanteAction.do?numeroRA='+form.numeroRAPesquisado.value, 550, 735);
	}

	function consultarDebitos() {
		var form = document.forms[0];

		//form.action = 'consultarDebitoAction.do?codigoImovel='+form.matriculaImovel.value;
		//form.submit();
		
		abrirPopup('consultarDebitoAction.do?ehPopup=true&codigoImovel='+form.matriculaImovel.value, 550, 735);
	}
	
	function consultarOS() {
		var form = document.forms[0];
		abrirPopup('exibirConsultarRegistroAtendimentoOSAction.do?numeroRA='+form.numeroRAPesquisado.value, 550, 735);
	}
	
	function gerarOS() {
		var form = document.forms[0];

		form.action = 'exibirGerarOrdemServicoAction.do?menu=sim&forward=exibirGerarOrdemServico&caminhoRetornoGerarOs=exibirConsultarRegistroAtendimentoAction.do&numeroRA='+form.numeroRAPesquisado.value;
		form.submit();

	}
	
	function reiterar(){
		var form = document.forms[0];
		form.action = 'exibirReiterarRegistroAtendimentoAction.do?numeroRA='+form.numeroRAPesquisado.value;
		form.submit();
	}

	function tramitar(url,id){
		window.location.href=url+id;
	}
	
	function encerrarRA(url,id){
		window.location.href=url+id;
	}
	
	function reativarRA(url,id){
		window.location.href=url+id;
	}
	function atualizarRA(url,id){
		window.location.href=url+id;
	}	
	
	function imprimir(viaCliente){
		var form = document.forms[0];
		if(viaCliente){
			toggleBoxCaminho('demodiv',1,'gerarRelatorioRegistroAtendimentoViaClienteConsultarAction.do');
		}else{
			toggleBoxCaminho('demodiv',1,'gerarRelatorioRegistroAtendimentoConsultarAction.do');
		}
	}
	
	
	function validarPesquisa(){
	
		var form = document.forms[0];
		
		if (validateConsultarRegistroAtendimentoActionForm(form)){
			form.action = "exibirConsultarRegistroAtendimentoAction.do?pesquisaUnitaria=OK&raReativado=" + form.isRAReativado.value;
			submeterFormPadrao(form);
		}
	}
	
	
	function raAnterior(){
 
 		var form = document.forms[0];
 		form.action = "exibirConsultarRegistroAtendimentoAction.do?raAnterior=1&numeroRA=" + form.numeroRAPesquisado.value + "&raReativado=" + form.isRAReativado.value;
 		submeterFormPadrao(form);
	}

	function proximoRA(){
 		var form = document.forms[0];
 		form.action = "exibirConsultarRegistroAtendimentoAction.do?proximoRA=1&numeroRA=" + form.numeroRAPesquisado.value + "&raReativado=" + form.isRAReativado.value;
 		submeterFormPadrao(form);
	}

	function chamarTela(){

		

	}
	
</script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="ConsultarRegistroAtendimentoActionForm"/>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:validaForm();setarFoco('${requestScope.nomeCampo}');">

<html:form action="/exibirConsultarRegistroAtendimentoAction.do"
	name="ConsultarRegistroAtendimentoActionForm"
	type="gcom.gui.atendimentopublico.registroatendimento.ConsultarRegistroAtendimentoActionForm"
	method="post">
	
	<html:hidden property="codigoSituacao"/>
	<html:hidden property="isRAReativado"/>
	
	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

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
	
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<td width="11">
							<img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" />
						</td>
						<td class="parabg">Consultar RA - Registro de Atendimento</td>
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
                         					<span class="style2"><b>Pesquisar outro RA - Registro de Atendimento</b></span>
                         				</div>
                         			</td>
                        		</tr>

								<tr bgcolor="#cbe5fe">
									<td>
									<table border="0" width="100%">

		                            	<tr>
		                              		<td height="10" width="22%"><strong>N&uacute;mero do RA:</strong></td>
		                              		
		                              		<td> 
												<html:text property="numeroRA" size="9" maxlength="9" />&nbsp;
												<input type="button" class="bottonRightCol" value="Pesquisar"
												style="width: 80px" onclick="validarPesquisa();">
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
				<tr>
                	<td height="20"></td>
                </tr>
                
                <logic:notPresent name="naoHabilitarNavegacao" scope="session">
			      			
			    <tr>
					<td>						      			
									
						<table cellspacing="0" cellpadding="0" width="100%" border="0">
										
						<tr>
											
							<logic:notPresent name="desabilitaBotaoAnterior">
											
								<td align="right" width="83%"><img
									src="<bean:message key="caminho.imagens"/>voltar.gif"
									onclick="raAnterior();"></td>
								<td align="left" width="15%"><input type="button"
									name="Button" class="buttonAbaRodaPe" value="RA Anterior"
									onclick="raAnterior();" /></td>
											
							</logic:notPresent>
										
							<logic:notPresent name="desabilitaBotaoProximo">	
											
								<td align="right" width="97%"><input type="button"
									name="Button" class="buttonAbaRodaPe" value="Próximo RA"
									onclick="proximoRA();" /></td>
								<td align="right" width="3%"><img
									src="<bean:message key="caminho.imagens"/>avancar.gif"
									onclick="proximoRA()"></td>
										
							</logic:notPresent>
										
						</tr>
											
						</table>
								
					</td>
			    </tr>
			      			
			    </logic:notPresent>
            	
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
												<html:text property="numeroRAPesquisado" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="9"
													maxlength="9" />
		                                  	</td>
			                              	
			                              	<td>
			                              		<strong>Situa&ccedil;&atilde;o do RA:</strong>
												<html:text property="situacaoRA" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="15"
													maxlength="15" />

			                              	</td>
			                              	<td>
			                                </td>
		                            	</tr>



										<logic:present name="existeRaAssociado" scope="request">

			                            	<tr> 
			                              		<td height="10"><strong><bean:write name="ConsultarRegistroAtendimentoActionForm" property="descricaoRAAssociada"/></strong></td>
			                              		<td> 
													<html:text property="numeroRaAssociado" readonly="true"
														style="background-color:#EFEFEF; border:0;" size="9"
														maxlength="9" />
			                                	</td>
			                              		
			                              		<td>
			                              			<strong><bean:write name="ConsultarRegistroAtendimentoActionForm" property="descricaoSituacaoRAAssociada"/></strong>
			                              			<html:text property="situacaoRaAssociado" readonly="true"
														style="background-color:#EFEFEF; border:0;" size="15"
														maxlength="15" />
			                              		</td>

			                              		<td>
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
									href="javascript:abrirPopup('exibirConsultarRegistroAtendimentoPopupAction.do?numeroRA='+${ConsultarRegistroAtendimentoActionForm.raReiterado}, 400, 800);">
									${ConsultarRegistroAtendimentoActionForm.raReiterado}</a>
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
												<html:text property="dataAtendimento" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="10"
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
			                            <logic:equal name="sistemaParametro" property="parmId" scope="session" value="<%=SistemaParametro.INDICADOR_EMPRESA_ADA.toString()%>">
			                            <tr> 
			                              	<td class="style3"><strong>Senha de Atendimento:</strong></td>
			                              	<td colspan="3">
												<html:text property="senhaAtendimento" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="5" maxlength="5" />
											</td>
			                            </tr>
										</logic:equal>
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
													style="background-color:#EFEFEF; border:0;" size="6"
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
													style="background-color:#EFEFEF; border:0;" size="6"
													maxlength="4" />

												<html:text property="unidadeAtual" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="40"
													maxlength="40" />
											</td>
			                            </tr>

			                            <tr> 
			                              	<td class="style3"><strong>Observação do R.A:</strong></td>
			                             	<td colspan="3">
												<html:textarea property="observacao" readonly="true" rows="5"
													style="background-color:#EFEFEF; border:0;" cols="50"/>
			                              
											</td>
			                            </tr>
			                            
			                            <tr> 
		                              		<td class="style3"><strong>Indicador Processo Adm Jud:</strong></td>
		                              		<td colspan="3">
												<html:text property="indicadorProcessoAdmJud" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="5"
													maxlength="3" />
		                              		
		                                	</td>
		                            	</tr>
		                            	
		                            	<tr> 
		                              		<td class="style3"><strong>Número Processo na Agência:</strong></td>
		                              		<td colspan="3">
												<html:text property="numeroProcessoAgencia" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="25"
													maxlength="20" />
		                              		
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
												<html:text property="matriculaImovel" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="8"
													maxlength="8" />
				
												<html:text property="inscricaoImovel" 
													readonly="true"
													style="background-color:#EFEFEF; border:0;" 
													size="21"
													maxlength="21" />


												<c:choose>
	                								<c:when test="${ConsultarRegistroAtendimentoActionForm.matriculaImovel != null}">
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
												<html:text property="idClienteSolicitante" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="10"/>
	
												<html:text property="clienteSolicitante" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="40"
													maxlength="40" />

											</td>
                                  		</tr>
		                                  		
										<tr> 	
                                    		<td class="style3">
                                    			<strong>Tipo de Pessoa:</strong>
                                    		</td>
                                    		<td colspan="3">
												<logic:equal name="ConsultarRegistroAtendimentoActionForm" property="clienteTipo" value="<%=""+ConstantesSistema.INDICADOR_PESSOA_FISICA%>">
													PESSOA FÍSICA
												</logic:equal>
												<logic:equal name="ConsultarRegistroAtendimentoActionForm" property="clienteTipo" value="<%=""+ConstantesSistema.INDICADOR_PESSOA_JURIDICA%>">
													PESSOA JURÍDICA
												</logic:equal>
												<logic:empty name="ConsultarRegistroAtendimentoActionForm" property="clienteTipo">
													NÃO INFORMADO
												</logic:empty>
											</td>
                                  		</tr>
		                               <logic:equal name="ConsultarRegistroAtendimentoActionForm" property="clienteTipo" value="<%=""+ConstantesSistema.INDICADOR_PESSOA_FISICA%>">
		                               <tr> 	
                                    		<td class="style3">
                                    			<strong>Cpf:</strong>
                                    		</td>
                                    		<td colspan="3">
                                    			<html:text property="numeroCpf" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="11"
													maxlength="11" />
											</td>
										</tr>
                                  		<tr> 
											<td class="style3">
                                    			<strong>Rg:</strong>
                                    		</td>
                                    		<td colspan="3">
                                    			<html:text property="numeroRG" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="13"
													maxlength="13" />
											</td>
                                  		</tr>
                                  		<tr> 	
                                    		<td class="style3">
                                    			<strong>&Oacute;rg&atilde;o Expedidor:</strong>
                                    		</td>
                                    		<td colspan="3">
                                    			<html:text property="orgaoExpedidorRg" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="70"
													maxlength="70" />
											</td>
                                  		</tr>
                                  		<tr> 		
                                  			<td class="style3">
                                    			<strong>Estado:</strong>
                                    		</td>
                                    		<td colspan="3">
                                    			<html:text property="unidadeFederacaoRG" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="20"
													maxlength="20" />
											</td>
										</tr>	
                                  		</logic:equal>
                                  		<logic:equal name="ConsultarRegistroAtendimentoActionForm" property="clienteTipo" value="<%=""+ConstantesSistema.INDICADOR_PESSOA_JURIDICA%>">
	                                  		<tr> 	
	                                    		<td class="style3">
	                                    			<strong>Cnpj:</strong>
	                                    		</td>
	                                    		<td colspan="3">
													<html:text property="numeroCnpj" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="14"
													maxlength="14" />
												</td>
	                                  		</tr>
                                  		</logic:equal>
										<tr> 	
                                    		<td class="style3">
                                    			<strong>Unidade Solicitante:</strong>
                                    		</td>
                                    		<td colspan="3">
												<html:text property="idUnidadeSolicitante" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="6"
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
		                                		<strong><span class="style2">Endere&ccedil;o do Solicitante:</span></strong>
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
																style="background-color:#EFEFEF; border:0;" size="6"
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
																style="background-color:#EFEFEF; border:0;" size="6"
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
															<html:text property="idUsuarioResponsavel" 
																readonly="true"
																style="background-color:#EFEFEF; border:0;" 
																size="4"
																maxlength="4" />
				
															<html:text property="usuarioResponsavel" 
																readonly="true"
																style="background-color:#EFEFEF; border:0;" 
																size="40"
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
															<a href="javascript:abrirPopup('<html:rewrite page="/exibirConsultarRegistroAtendimentoReiteradosAction.do?numeroRA=${ConsultarRegistroAtendimentoActionForm.numeroRAPesquisado}"/>', 400, 600);">
																
																<bean:write name="ConsultarRegistroAtendimentoActionForm" property="quantidade" />
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
															<html:text property="numeroRaAtual" 
																readonly="true"
																style="background-color:#EFEFEF; border:0;" 
																size="9"
																maxlength="9" />
														</td>
			                                  		</tr>

													<tr>
			                                    		<td class="style3">
			                                    			<strong>Situa&ccedil;&atilde;o do RA Atual:</strong>
			                                    		</td>

			                                    		<td>
															<html:text property="situacaoRaAtual" 
																readonly="true"
																style="background-color:#EFEFEF; border:0;" 
																size="15"
																maxlength="15" />
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

															<html:text property="descricaoMotivoReativacao" readonly="true"
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
															<html:text property="dataPrevistaRaAtual" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="10"
																maxlength="10" />
														</td>
			                                  		</tr>

													<tr>
			                                    		<td class="style3">
			                                    			<strong>Unidade da Reativa&ccedil;&atilde;o:</strong>
			                                    		</td>

			                                    		<td>
															<html:text property="idUnidadeReativacao" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="6"
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
																style="background-color:#EFEFEF; border:0;" size="6"
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
															<html:text property="numeroRaReferencia" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="9"
																maxlength="9" />
														</td>
			                                  		</tr>

													<tr>
			                                    		<td class="style3">
			                                    			<strong>Situa&ccedil;&atilde;o do RA Refer&ecirc;ncia:</strong>
			                                    		</td>

			                                    		<td>
															<html:text property="situacaoRaReferencia" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="15"
																maxlength="15" />
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
																size="10"
																maxlength="10" />
														</td>
			                                  		</tr>

													<tr>
			                                    		<td class="style3">
			                                    			<strong>Unidade do Encerramento:</strong>
			                                    		</td>

			                                    		<td>
															<html:text property="idUnidadeEncerramento" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="6"
																maxlength="4" />

															<html:text property="unidadeEncerramento" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="40"
																maxlength="40" />

														</td>

			                                  		</tr>

													<tr>
			                                    		<td class="style3">
			                                    			<strong>Usu&aacute;rio do Encerramento:</strong>
			                                    		</td>

			                                    		<td>
															<html:text property="idUsuarioEncerramento" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="4"
																maxlength="4" />

															<html:text property="usuarioEncerramento" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="40"
																maxlength="40" />

														</td>
			                                  		</tr>

													<tr>
			                                    		<td class="style3">
			                                    			<strong>Parecer do Encerramento:</strong>
			                                    		</td>

			                                    		<td>
															<html:textarea property="parecerEncerramento" readonly="true"
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
							<tr>
								<td>
								<bean:define name="ConsultarRegistroAtendimentoActionForm" property="numeroRAPesquisado" id="ra"/>
								
								<logic:notPresent name="raReativado" scope="session">
								
								<table width="100%">
									<tr>
			                			<td>
			                				<input name="ButtonAtualizar" 
			                					type="button" 
			                					class="bottonRightCol" 
			                					value="Atualizar" 
			                					onclick="javascript:atualizarRA('<html:rewrite page="/exibirAtualizarRegistroAtendimentoAction.do?menu=sim&definirDataPrevista=ok&idRegistroAtualizacao="/>', '<bean:write name="ConsultarRegistroAtendimentoActionForm" property="numeroRAPesquisado"/>')";/> 
			                  				
			                  				<input name="ButtonReiterar" 
			                  					type="button" 
			                  					class="bottonRightCol" 
			                  					value="Reiterar"
			                  					onclick="javascript:reiterar();"> 
			                  				
			                  				<input name="ButtonTramitar" 
				                  				type="button" 
				                  				class="bottonRightCol"  
				                  				value="Tramitar" 
				                  				onClick="javascript:tramitar('<html:rewrite page="/exibirTramitarRegistroAtendimentoAction.do?resetarTramitacao=true&numeroRA="/>', '<bean:write name="ConsultarRegistroAtendimentoActionForm" property="numeroRAPesquisado"/>')";/> 
			                  				
			                  				<input name="ButtonEncerrar" 
			                  					type="button" 
			                  					class="bottonRightCol"  
			                  					value="Encerrar" 
			                  					onClick="javascript:encerrarRA('<html:rewrite page="/exibirEncerrarRegistroAtendimentoAction.do?resetarEncerramento=true&numeroRA="/>', '<bean:write name="ConsultarRegistroAtendimentoActionForm" property="numeroRAPesquisado"/>')";/> 
						                  	
						                  	<input name="ButtonReativar" 
						                  		type="button" 
						                  		class="bottonRightCol"  
						                  		value="Reativar" 
						                  		onClick="javascript:reativarRA('<html:rewrite page="/exibirReativarRegistroAtendimentoAction.do?resetarReativar=true&numeroRA="/>', '<bean:write name="ConsultarRegistroAtendimentoActionForm" property="numeroRAPesquisado"/>')";/>  
						                  	
						                  	<input name="ButtonImprimir" 
						                  		type="button" 
						                  		class="bottonRightCol" 
												value="Imprimir" 
												onClick="javascript:imprimir(false);" > 
						                  	
						                  	<input name="ButtonOrdemSer" 
						                  		type="button" 
						                  		class="bottonRightCol" 
						                  		value="Gerar O.S"
						                  		onClick="javascript:gerarOS();"> 
										</td>
									</tr>
					              	
					              	<tr>
					                	<td> 
					                		<input name="ButtonSolicitantes" 
					                			type="button" 
					                			class="bottonRightCol"  
					                			value="Consultar Solicitantes" 
					                			onClick="javascript:consultarSolicitantes();">
					                  		
					                  		<input name="ButtonTramites" 
					                  			type="button" 
					                  			class="bottonRightCol"  
					                  			value="Consultar Tr&acirc;mites" 
					                  			onClick="javascript:consultarTramites();">
					                  			
					                  		<input name="ButtonOrdens" 
					                  			type="button" 
					                  			class="bottonRightCol" 
					                  			value="Consultar O.S" 
					                  			onClick="javascript:consultarOS();">
					                  			
				                  			<input name="ButtonImprimirViaCliente" 
						                  		type="button" 
						                  		class="bottonRightCol" 
												value="Imprimir Via Cliente" 
												onClick="javascript:imprimir(true);" >
										</td>
					              	</tr>
					              	
					              	<tr>
						            	<td width="233">
			                  					<input type="button" 
			                  					name="ButtonReset" 
			                  					class="bottonRightCol"
												value="Voltar"
												onclick="javascript:history.back();">
						              	</td>
						          	</tr>
									
								</table>
			
								</logic:notPresent>
								
								<logic:present name="raReativado" scope="session">
									<table width="100%">
										<tr>
				                			<td width="233">
						            	
				                  				<logic:notPresent name="naoHabilitarNavegacao" scope="session">
				                  					
				                  					<input type="button" 
				                  					name="ButtonReset" 
				                  					class="bottonRightCol"
													value="Voltar"
													onClick="window.location.href='filtrarRegistroAtendimentoAction.do'">
							              	
							              		</logic:notPresent>
							              		
							              		<logic:present name="naoHabilitarNavegacao" scope="session">
				                  					
				                  					<input type="button" 
				                  					name="ButtonReset" 
				                  					class="bottonRightCol"
													value="Voltar"
													onClick="window.location.href='exibirFiltrarRegistroAtendimentoAction.do'">
							              	
							              		</logic:present>
							              		
							              	</td>
						          		</tr>
									</table>								
								</logic:present>
								
								</td>
			
							</tr>

						</table>
  					</td>
				</tr>
   		
	</table>
	<!-- Fim do Corpo - Rafael Pinto -->
	<jsp:include page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=" />
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>