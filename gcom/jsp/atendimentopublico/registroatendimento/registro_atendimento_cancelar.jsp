<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page import="gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<%-- Carrega validações do validator --%>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="EncerrarRegistroAtendimentoActionForm"/>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">
	/* Valida Form */
	function validarForm() {
		var form = document.forms[0];
		
		if (validateEncerrarRegistroAtendimentoActionForm(form)) {
			if (form.especificacaoIndicadorParecer.value != '1' 
									|| (form.especificacaoIndicadorParecer.value == '1' 
										&& form.parecerEncerramento.value != '')) {
				submeterFormPadrao(form);
			} else {
					alert('Informe o Parecer de Cancelamento.');
			}
		} 
	}	
	
	/* Limpar Form */
	function limparForm() {
		var form = document.forms[0];
		
      	form.motivoEncerramentoId.selectedIndex = 0;
      	if (form.usuarioRegistroUnidadeIndicadorCentralAtendimento.value == '1') {
	      	form.dataEncerramento.value = '';
		    form.horaEncerramento.value = '';
      	}
	    form.parecerEncerramento.value = '';
	}
	
	function verificarRetorno(retorno) {
		if (retorno != null && retorno != '' && retorno != 'telaAnterior') {
			redirecionarSubmit(retorno);
		} else {
			history.back();
		}
	}	
</script>
</head>

<body leftmargin="5" topmargin="5" onload="">

<html:form action="/cancelarRegistroAtendimentoAction.do"
	name="EncerrarRegistroAtendimentoActionForm"
	type="gcom.gui.atendimentopublico.registroatendimento.EncerrarRegistroAtendimentoActionForm"
	method="post">
	
	<html:hidden property="especificacaoIndicadorParecer"/>
	<html:hidden property="usuarioRegistroUnidadeIndicadorCentralAtendimento"/>
	<html:hidden property="indicadorDuplicidade"/>
	<html:hidden property="numeroRAReferencia" />
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
						<td class="parabg">Cancelar RA - Registro de Atendimento</td>
						<td width="11">
							<img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
						</td>
					</tr>
				</table>
			
				<p>&nbsp;</p>


				<!--Inicio da Tabela Dados Gerais do RA - Registro de Atendimento -->
            	<table width="100%" border="0">
				<tr> 
                	<td height="10" colspan="2">Para cancelar o(s) RA(s) - Registro(s) de Atendimento(s), informe os dados abaixo:</td>
                 </tr>
				<!-- Dados do Local Ocorrencia -->
               	<tr bgcolor="#cbe5fe">
           			<td align="center">
                   		<table width="100%" border="0" bgcolor="#99CCFF">
	    					<tr bgcolor="#99CCFF">
                     			<td align="center">
                   					<span class="style2">
                    						<b>Dados do Cancelamento do(s) RA(s) - Registro(s) de Atendimento(s)</b>
                   					</span>
                     			</td>
                    		</tr>

							<tr bgcolor="#cbe5fe">
								<td>
									<table border="0" width="100%">
						                <tr> 
						                	<td class="style3">
						                		<strong>Motivo do Encerramento:</strong>
						                		<font color="#FF0000">*</font>
						                	</td>
						                	<td colspan="6">
						                		<span class="style2">
						                			<strong> 
														<html:select property="motivoEncerramentoId" style="width: 230px;">
															<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
															<logic:present name="colecaoMotivoEncerramento" scope="session">
																<html:options collection="colecaoMotivoEncerramento" labelProperty="descricao" property="id" />
															</logic:present>
														</html:select>
						                  			</strong>
						                  		</span>
						                  	</td>
						                </tr>

										<tr> 	
                                    		<td class="style3">
                                    			<strong>Data do Cancelamento:</strong>
                                    			<font color="#FF0000">*</font>
                                    		</td>
                                    		<td colspan="3">
                                    			<span class="style2">
                                    				<strong>
														<html:text property="dataEncerramento" readonly="true" style="background-color:#EFEFEF; border:0;" 
																   size="10" maxlength="10" />
                  									</strong>
                  								</span>            		
											</td>
                                  		</tr>
                                  		
										<tr> 	
                                    		<td class="style3">
                                    			<strong>Hora do Cancelamento:</strong>
                                    			<font color="#FF0000">*</font>
                                    		</td>
                                    		<td colspan="3">
                                    			<span class="style2">
                                    				<strong> 
														<html:text property="horaEncerramento" readonly="true" style="background-color:#EFEFEF; border:0;" 
																   size="6" maxlength="6" />
                  									</strong>
                  								</span>            		
											</td>
                                  		</tr>

										<tr> 	
                                    		<td class="style3">
                                    			<strong>Parecer do Cancelamento:<font color="#FF0000">*</font></strong>
                                    		</td>
                                    		<td colspan="3">
                                    			<span class="style2">
                                    				<strong>
														<html:textarea property="parecerEncerramento" cols="50" rows="5" onkeyup="javascript:limitTextArea(this, 400, document.getElementById('utilizadoParecerEncerramento'), document.getElementById('limiteParecerEncerramento'));"/>	
														<strong><span id="utilizadoParecerEncerramento">0</span>/<span id="limiteParecerEncerramento">400</span></strong>		                                   						
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
								<input name="ButtonVoltar" 
									type="button" 
									class="bottonRightCol" 
									value="Voltar" 
									onclick="javascript:verificarRetorno('${sessionScope.caminhoRetorno}')">
                      		</td>
                      		<td width="54%" align="right">
                        		<input name="ButtonCancelar" 
                        			type="button" 
                        			class="bottonRightCol" 
                        			value="Cancelar" 
                        			onClick="javascript:validarForm();">
							</td>
						</tr>

					</table>

					</td>

				</tr>

			</table>
		</td>
	</tr>

</table>
<!-- Fim do Corpo -->
<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>

</body>
</html:html>