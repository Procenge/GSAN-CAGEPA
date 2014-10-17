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

<%-- Carrega validações do validator --%>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="TramitarOrdemServicoActionForm"/>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">
	/* Valida Form */
	function validarForm() {
		var form = document.forms[0];
		if(validateTramitarOrdemServicoActionForm(form)) {
			submeterFormPadrao(form);
		}
	}
	
	function textAreaMaxLength(maxlength){
		var form = document.forms[0];
		if(form.parecerTramite.value.length >= maxlength){
			window.event.keyCode = '';
		}
	}
	/* Limpar Unidade Destino */
	function limparUnidadeDestino() {
		var form = document.forms[0];
		
      	form.unidadeDestinoId.value = '';
	    form.unidadeDestinoDescricao.value = '';
	}
	/* Limpar Usuário Responsável */
	function limparUsuarioResponsavel() {
		var form = document.forms[0];
		
      	form.usuarioResponsavelId.value = '';
	    form.usuarioResponsavelNome.value = '';
	}
	/* Limpar Form */
	function limparForm() {
		var form = document.forms[0];
		
		limparUnidadeDestino();
		limparUsuarioResponsavel();
      	form.dataTramite.value = '';
	    form.horaTramite.value = '';
	    form.parecerTramite.value = '';
	}
	/* Chama Popup */ 
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){
		if(objetoRelacionado.disabled != true){
			if (objeto == null || codigoObjeto == null){
				abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
			} else{
				if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
					alert(msg);
				} else{
					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
				}
			}
		}
	}
	/* Recuperar Popup */
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	    var form = document.forms[0];

	    if (tipoConsulta == 'usuario') {
	      form.usuarioResponsavelId.value = codigoRegistro;
	      form.usuarioResponsavelNome.value = descricaoRegistro;
	      form.usuarioResponsavelNome.style.color = '#000000';	 
	    } else if (tipoConsulta == 'unidadeOrganizacional') {
		      form.unidadeDestinoId.value = codigoRegistro;
		      form.action = 'exibirTramitarOrdemServicoAction.do?validaUnidadeDestino=true';
		      form.submit();
		      //form.unidadeDestinoDescricao.value = descricaoRegistro;
		      //form.unidadeDestinoDescricao.style.color = '#000000';
	    }
	}
	/* Consultar Trâmites */
	function consultarTramites() {
		var form = document.forms[0];
		abrirPopup('exibirConsultarRegistroAtendimentoTramiteAction.do?numeroRA='+form.numeroRA.value, 550, 735);
	}
	/* Consultar RA - Registro de Atendimento */	
	function consultarOS() {
		var form = document.forms[0];
		form.action = 'exibirConsultarDadosOrdemServicoAction.do?numeroOS='+form.numeroOS.value;
		form.submit();
	}
</script>
</head>

<body leftmargin="5" topmargin="5">

<html:form action="/tramitarOrdemServicoAction.do"
	name="TramitarOrdemServicoActionForm"
	type="gcom.gui.atendimentopublico.ordemservico.TramitarOrdemServicoActionForm"
	method="post">
	
	<html:hidden property="numeroOS"/>
	
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
						<td class="parabg">Tramitar Ordem de Servi&ccedil;o</td>
						<td width="11">
							<img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
						</td>
					</tr>
				</table>
			
				<p>&nbsp;</p>


				<table width="100%" border="0">

            	<tr> 
                	<td height="10" colspan="2">Para tramitar a Ordem de Servi&ccedil;o, informe os dados abaixo:</td>
                 </tr>
				<!-- Dados do Local Ocorrencia -->
               	<tr bgcolor="#cbe5fe">
           			<td align="center">
                   		<table width="100%" border="0" bgcolor="#99CCFF">
	    					<tr bgcolor="#99CCFF">
                     			<td align="center">
                   					<span class="style2">
                    						<b>Dados da Tramita&ccedil;&atilde;o</b>
                   					</span>
                     			</td>
                    		</tr>

							<tr bgcolor="#cbe5fe">
								<td>
									<table border="0" width="100%">

										<tr> 	
                                    		<td class="style3">
                                    			<strong>Unidade Destino:</strong>
                                    			<font color="#FF0000">*</font>
                                    		</td>
                                    		<td>
                								<span class="style2">
                									<strong>
														<html:text property="unidadeDestinoId" size="6" maxlength="8"
							   									   onkeypress="javascript:validaEnterComMensagem(event, 'exibirTramitarOrdemServicoAction.do?validaUnidadeDestino=true', 'unidadeDestinoId','Unidade Destino');"/>
														<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23"	height="21" 
						 									 style="cursor:hand;" name="imagem"	onclick="chamarPopup('exibirPesquisarUnidadeOrganizacionalAction.do', 'unidadeOrganizacional', null, null, 275, 480, '',document.forms[0].unidadeDestinoId);"
						 									 alt="Pesquisar">
						 									 
														<logic:present name="unidadeDestinoEncontrada" scope="session">
															<html:text property="unidadeDestinoDescricao" readonly="true"
								   									   style="background-color:#EFEFEF; border:0; color: #000000" size="45"/>
														</logic:present> 
														<logic:notPresent name="unidadeDestinoEncontrada" scope="session">
															<html:text property="unidadeDestinoDescricao" readonly="true"
								   									   style="background-color:#EFEFEF; border:0; color: #ff0000" size="45"/>
														</logic:notPresent>
														
														<a href="javascript:limparUnidadeDestino();">
															<img border="0" title="Apagar" src="<bean:message key='caminho.imagens'/>limparcampo.gif" /></a>
                  									</strong>
                  								</span>
											</td>
                                  		</tr>
	                              	
										<tr> 	
                                    		<td class="style3">
                                    			<strong>Usu&aacute;rio Respons&aacute;vel:</strong>
                                    			<font color="#FF0000">*</font>
                                    		</td>
                                    		<td colspan="3">
                								<span class="style2">
                									<strong>
	                									<logic:notEqual name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
														<html:text property="usuarioResponsavelId" size="9" maxlength="9"
							   									   onkeypress="javascript:validaEnterComMensagem(event, 'exibirTramitarOrdemServicoAction.do?validaUsuarioResponsavel=true', 'usuarioResponsavelId','Usuário Responsável');"/>
								   						</logic:notEqual>
								   						<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
															<html:text property="usuarioResponsavelId" size="9" maxlength="9" readonly="true"/>
								   						</logic:equal>
							   						    <logic:notEqual name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
														<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23"	height="21" 
						 									 style="cursor:hand;" name="imagem"	onclick="chamarPopup('exibirUsuarioPesquisar.do', 'usuario', null, null, 275, 480, '',document.forms[0].usuarioResponsavelId);"
						 									 alt="Pesquisar">
							 							</logic:notEqual>

														<logic:present name="usuarioResponsavelEncontrada" scope="session">
															<html:text property="usuarioResponsavelNome" readonly="true"
								   									   style="background-color:#EFEFEF; border:0; color: #000000" size="45"/>
														</logic:present> 
														<logic:notPresent name="usuarioResponsavelEncontrada" scope="session">
															<html:text property="usuarioResponsavelNome" readonly="true"
								   									   style="background-color:#EFEFEF; border:0; color: #ff0000" size="45"/>
														</logic:notPresent>

														<logic:notEqual name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
														<a href="javascript:limparUsuarioResponsavel();">
															<img border="0" title="Apagar" src="<bean:message key='caminho.imagens'/>limparcampo.gif" /></a>
														</logic:notEqual>
                  									</strong>
                  								</span>
											</td>
                                  		</tr>
                                 		
										<tr> 	
                                    		<td class="style3">
                                    			<strong>Data da Tramita&ccedil;&atilde;o:</strong>
                                    			<font color="#FF0000">*</font>
                                    		</td>
                                    		<td colspan="3">
                                    			<span class="style2">
                                    				<strong> 
														<html:text property="dataTramite" size="11" maxlength="10" tabindex="3" onkeyup="mascaraData(this, event);"/>
														<a href="javascript:abrirCalendario('TramitarOrdemServicoActionForm', 'dataTramite');">
														<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário" tabindex="4"/></a>
	                       								<strong>(dd/mm/aaaa)</strong> 
                  									</strong>
                  								</span>            		
											</td>
                                  		</tr>
                                  		
										<tr> 	
                                    		<td class="style3">
                                    			<strong>Hora da Tramita&ccedil;&atilde;o:</strong>
                                    			<font color="#FF0000">*</font>
                                    		</td>
                                    		<td colspan="3">
                                    			<span class="style2">
                                    				<strong> 
														<html:text property="horaTramite" size="6" maxlength="5" tabindex="3" onkeyup="mascaraHora(this, event);"/>
	                       								<strong>(hh:mm)</strong> 
                  									</strong>
                  								</span>            		
											</td>
                                  		</tr>
										<tr> 	
                                    		<td class="style3">
                                    			<strong>Parecer:</strong>
                                    		</td>
                                    		<td colspan="3">
                                    			<span class="style2">
                                    				<strong> 
														<html:textarea property="parecerTramite" cols="50" rows="5" onkeypress="javascript:textAreaMaxLength(200);"/>
													</strong>
												</span>
											</td>
                                  		</tr>

						       			<tr> 
						          			<td>
						          				<strong>
						          					<font color="#FF0000"></font>
						          				</strong>
						          			</td>
						          			<td colspan="3" align="right">
						          				<div align="left">
						          					<strong>
						          						<font color="#FF0000">*</font>
						          					</strong> 
						              				Campos obrigat&oacute;rios
						              			</div>
						              		</td>
						       			</tr>


								</table>
							</td>
                            </tr>
						</table>
       				</td>
      			</tr>



				<tr>
					<td>

					<table width="100%">
						<tr>
                			<td>
                				<input name="ButtonVoltar"   type="button" class="bottonRightCol" value="Voltar" onclick="javascript:consultarOS();">
                				<input name="ButtonDesfazer" type="button" class="bottonRightCol" value="Desfazer" onclick="javascript:window.location.href='/gsan/exibirTramitarOrdemServicoAction.do?desfazer=true'">
								<input name="ButtonCancelar" type="button" class="bottonRightCol" value="Cancelar" onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
                      		</td>
                      		<td width="54%" align="right">
		                  		<!-- 
		                  		<input name="ButtonTramites" type="button" class="bottonRightCol"  value="Consultar Tr&acirc;mites" onClick="javascript:consultarTramites();">
		                  		 -->
                        		<input name="ButtonInserir" type="button" class="bottonRightCol" value="Tramitar" onClick="javascript:validarForm();">
							</td>
						</tr>

					</table>

					</td>

				</tr>

			</table>
		</td>
	</tr>

</table>
<html:hidden name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId"/>
<!-- Fim do Corpo -->

	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>