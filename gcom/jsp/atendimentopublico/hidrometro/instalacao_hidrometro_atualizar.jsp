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
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="AtualizarInstalacaoHidrometroActionForm"/>	
	
<script language="JavaScript">

	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
  		var form=document.forms[0];
    	if (tipoConsulta == 'hidrometro') {
      		form.numeroHidrometro.value = codigoRegistro;
    	} else if (tipoConsulta == 'ordemServico') {
	    	form.idOrdemServico.value = codigoRegistro;
	    	form.nomeOrdemServico.value = descricaoRegistro;
	    	form.action='exibirAtualizarInstalacaoHidrometroAction.do?reset=hidrometro';
	    	form.submit();
    	}    
  	}	

	function limparFuncionario(){

		var form = document.forms[0];
		
		form.idFuncionario.value = "";
		form.descricaoFuncionario.value = "";
				   
	}

	function limparDescricao(){
		
		var form = document.forms[0];
		form.descricaoFuncionario.value = "";
	}
  
	function validaForm() {
	  	var form = document.forms[0];
	  	form.action = "/gsan/atualizarInstalacaoHidrometroAction.do";
		if(validateAtualizarInstalacaoHidrometroActionForm(form)) {
		  	if(validaTodosRadioButton()) {		     		  		
				submeterFormPadrao(form);   		  
   	      	}
   	    }
	}
	 
  	function validaTodosRadioButton() {
		var form = document.forms[0];		
		if (!form.medicaoTipo[0].checked
				&& !form.medicaoTipo[1].checked) {
			alert("Informe Tipo de Medição.");		
			return false;
		}		
		if (!form.indicadorExistenciaCavalete[0].checked
				&& !form.indicadorExistenciaCavalete[1].checked) {
			alert("Informe Cavalete.");		
			return false;
		}		
		return true;
   	}

  	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
   		var form = document.forms[0];
   
    	
    	if (tipoConsulta=='funcionario'){
  			form.idFuncionario.value = codigoRegistro;
  			form.descricaoFuncionario.value = descricaoRegistro;
  			form.descricaoFuncionario.style.color = "#000000";
      	}else{
      		form.idCliente.value = codigoRegistro;  
      		form.descricaoCliente.value = descricaoRegistro;
      		form.descricaoCliente.style.color = "#000000";
      	}	  
		
  	}
	 
	function limparForm() {
		var form = document.AtualizarInstalacaoHidrometroActionForm;
		form.nomeOrdemServico.value = "";
		form.matriculaImovel.value = "";
		form.inscricaoImovel.value = "";
		form.clienteUsuario.value = "";
		form.cpfCnpjCliente.value = "";
		form.situacaoLigacaoAgua.value = "";
		form.situacaoLigacaoEsgoto.value = "";
		form.dataInstalacao.value = "";
		limparFuncionario();
		limparHidrometro();
	}  
	
	function limparHidrometro(){
		var form = document.AtualizarInstalacaoHidrometroActionForm;
		
		form.medicaoTipo[0].checked = false;
		form.medicaoTipo[1].checked = false;		
		limparHidrometroSemMedicao();
		
	}
	
	function limparHidrometroSemMedicao(){

		var form = document.AtualizarInstalacaoHidrometroActionForm;
		
		form.hidrometro.value = "";
		form.localInstalacao.selectedIndex = 0;
		form.protecao.selectedIndex = 0;
		form.indicadorExistenciaCavalete[0].checked = false;
		form.indicadorExistenciaCavalete[1].checked = false;
		form.leituraInstalacao.value = "";
		form.numeroSelo.value = "";
	}
	
	function limpar(tipo) {
		var form = document.AtualizarInstalacaoHidrometroActionForm;
		if (tipo == 'ordemServico') {
			form.idOrdemServico.value = "";
			form.nomeOrdemServico.value = "";
			limparForm();
		}
	}  
	
	function reload() {

		var form = document.AtualizarInstalacaoHidrometroActionForm;
		
		limparHidrometroSemMedicao();
		
		form.action = "/gsan/exibirAtualizarInstalacaoHidrometroAction.do";
		form.submit();
	}  
	
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado) {
		if(objetoRelacionado.disabled != true) {
			if (objeto == null || codigoObjeto == null) {
				abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
			} else {
				if (codigoObjeto.length < 1 || isNaN(codigoObjeto)) {
					alert(msg);
				} else {
					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
				}
			}
		}
	}	 

	function resetar() {
		var form = document.forms[0];
		
		limparForm();
		
		form.action = "exibirAtualizarInstalacaoHidrometroAction.do?menu=sim";
		form.submit();
	}  
	
	//verifica se existe alguma restrição 
	//para exibição alguma campo no formulario
    function verificaForm() {

       	var form = document.forms[0];
		if(form.veioEncerrarOS.value == 'true'){
			document.getElementById('limparOS').disabled=true;
			form.idOrdemServico.disabled=true;
		}else{
			document.getElementById('limparOS').disabled=false;
			form.idOrdemServico.disabled=false;
		}       	
	}	
</script>
  
</head>

<body leftmargin="5" topmargin="5" onload="verificaForm();setarFoco('${requestScope.nomeCampo}');">

<html:form 	action="/atualizarInstalacaoHidrometroAction.do"
			name="AtualizarInstalacaoHidrometroActionForm"
			type="gcom.gui.atendimentopublico.hidrometro.AtualizarInstalacaoHidrometroActionForm"
			method="post"
			onsubmit="return validateAtualizarInstalacaoHidrometroActionForm(this);">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<html:hidden property="veioEncerrarOS" />

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

			<td width="625" valign="top" class="centercoltext">
            	<table>
              		<tr>
                		<td></td>
              		</tr>
            	</table>
            	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              		<tr>
                		<td width="11"><img border="0" src="imagens/parahead_left.gif"/></td>
                		<td class="parabg"> Atualizar Instala&ccedil;&atilde;o de Hidr&ocirc;metro </td>
                		<td width="11" valign="top"><img border="0" src="imagens/parahead_right.gif"/></td>
              		</tr>
            	</table>
            	
            	<table width="100%" border="0">
              		<tr>
                		<td height="31"><table width="100%" border="0" align="center">
                   			<tr>
                          		<td height="10" colspan="2">Para efetuar a atualiza&ccedil;&atilde;o da instala&ccedil;&atilde;o do hidr&ocirc;metro, informe os dados abaixo:. </td>
                   			</tr>
                   			<tr>
                          		<td height="10" colspan="2"><hr></td>
                   			</tr>

                   			<!-- Ordem de Serviço -->
                   			
							<tr>
								<td height="10" width="33%">
									<strong>Ordem de Servi&ccedil;o:<span class="style2">
									<strong><font color="#FF0000">*</font></strong></span>
									</strong>
								</td>
								<td>
									<span class="style2">
		
										<html:text
											property="idOrdemServico" size="7" maxlength="9" 
											onkeypress="javascript:validaEnterComMensagem(event, 'exibirAtualizarInstalacaoHidrometroAction.do?reset=hidrometro', 'idOrdemServico','Ordem de Serviço');"
											onkeyup="limparForm();"/>
		
										<a href="javascript:chamarPopup('exibirPesquisarOrdemServicoAction.do', 'ordemServico', null, null, 600, 500, '',document.forms[0].idOrdemServico);">
			
											<img width="23" 
												height="21" 
												border="0"
												src="<bean:message key="caminho.imagens"/>pesquisa.gif"
												title="Pesquisar OS" /></a>
			
											<logic:present name="ordemServicoEncontrada" scope="session">
												<html:text property="nomeOrdemServico" readonly="true"
															style="background-color:#EFEFEF; border:0; color: #000000" size="42"/> 
											</logic:present> 

											<logic:notPresent name="ordemServicoEncontrada" scope="session">
												<html:text property="nomeOrdemServico" readonly="true"
															style="background-color:#EFEFEF; border:0; color: #ff0000" size="42"/> 
											</logic:notPresent>
											
										<a href="javascript:limpar('ordemServico');" id="limparOS"> 
											<img border="0" title="Apagar"
												src="<bean:message key='caminho.imagens'/>limparcampo.gif" />
										</a>
									
									</span>
								</td>
							</tr>
		                  	
		                  	<!-- Dados do Imóvel  -->
		                  	
							<tr bgcolor="#cbe5fe">
								<td align="left" colspan="2">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td height="18" colspan="2">
												<div align="center"><span class="style2"> Dados do Imóvel </span></div>
											</td>
										</tr>
										<tr bgcolor="#cbe5fe">
											<td>
												<table border="0" width="100%">
													<tr>
														<td width="28%" height="10">
															<strong>Matr&iacute;cula do Im&oacute;vel:</strong>
														</td>
														<td width="58%" align="left">
															<html:text property="matriculaImovel"
																readonly="true" 
																style="background-color:#EFEFEF; border:0;"
																size="15" maxlength="20" /> 
															
															<html:text
																property="inscricaoImovel" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="21"
																maxlength="20" />
														</td>
													</tr>
													<tr>
														<td><strong> Cliente Usu&aacute;rio:</strong></td>
														<td align="left"><html:text property="clienteUsuario" readonly="true"
															style="background-color:#EFEFEF; border:0; color: #000000"
															size="40" maxlength="40" />
														</td>
													</tr>
													<tr>
														<td><strong>CPF ou CNPJ:</strong></td>
														<td align="left">
															<html:text property="cpfCnpjCliente" readonly="true"
																style="background-color:#EFEFEF; border:0; color: #000000"
																size="40" maxlength="40" />
														</td>						
													</tr>
													<tr>
														<td><strong>Situa&ccedil;&atilde;o da Liga&ccedil;&atilde;o
														de &Aacute;gua:</strong></td>
														<td align="left"><html:text property="situacaoLigacaoAgua" readonly="true"
															style="background-color:#EFEFEF; border:0; color: #000000"
															size="40" maxlength="40" />
														</td>
													</tr>
													<tr>
														<td><strong>Situa&ccedil;&atilde;o da Liga&ccedil;&atilde;o de Esgoto:</strong></td>
														<td><html:text property="situacaoLigacaoEsgoto"
															readonly="true"
															style="background-color:#EFEFEF; border:0; color: #000000"
															size="40" maxlength="40" /></td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</tr>

			  		<tr>
				    	<td height="31"><table width="100%" border="0" align="center">
	                    	<tr bgcolor="#cbe5fe">
	                      		<td align="center" colspan="2">
	                        		<table width="100%" border="0" bgcolor="#99CCFF">
	                          			<tr bgcolor="#99CCFF">
	                            			<td height="18" colspan="2"><div align="center"><span class="style2"> Dados da Instala&ccedil;&atilde;o do Hidr&ocirc;metro </span></div></td>
	                          			</tr>
			                          	<tr bgcolor="#cbe5fe">
			                            	<td>
			                              		<table border="0" width="100%">
			                              		
			                              			<!-- Tipo de Medição -->
			                              		
			                                		<tr>
			                                  			<td><strong><span class="style2">Tipo de Medi&ccedil;&atilde;o:<font color="#FF0000">*</font></span></strong></td>
			                                  			<td>
			                                      			<p>
			                                        		<label>
	                                        				<html:radio property="medicaoTipo" value="1" onclick="javascript:reload();" />
			                                        		<strong>LIGAÇÃO DE ÁGUA</strong></label>
			                                        		<label>
		                                        			<html:radio property="medicaoTipo" value="2" onclick="javascript:reload();" />
			                                        		<strong>POÇO</strong></label>
			                                        		</p>
			                                  			</td>
			                                		</tr>
			                                		
			                                		<!-- Número do Hidrômetro -->
			                                		
			                                		<tr>
					                                  	<td>
					                                  		<strong>N&uacute;mero do Hidr&ocirc;metro:<font color="#FF0000">*</font></strong>
					                                  	</td>
					                                  	
														<td><html:text property="hidrometro" readonly="true"
															style="background-color:#EFEFEF; border:0; color: #000000"
															size="15" maxlength="15" />
														</td>						
					                                </tr>
					                                
													<tr>
														<td>
															<strong> Data da Instala&ccedil;&atilde;o:
															<strong><font color="#FF0000">*</font></strong>
															</strong>
														</td>
			
														<td colspan="2">
															<strong> 
															<html:text property="dataInstalacao" 
																size="11" 
																maxlength="10" 
																tabindex="3" 
																onkeyup="mascaraData(this, event)"/>
																
															<a href="javascript:abrirCalendario('AtualizarInstalacaoHidrometroActionForm', 'dataInstalacao');">
																<img border="0" 
																	src="<bean:message key='caminho.imagens'/>calendario.gif" 
																	width="16" 
																	height="15" 
																	border="0" 
																	alt="Exibir Calendário" 
																	tabindex="4"/></a> (dd/mm/aaaa)
			
															</strong>
														</td>
													</tr>
					                                
					                                
					                                <!-- Local de Instalação -->

													<tr>
								                      	<td><strong>Local de Instalação:<font color="#FF0000">*</font></strong></td>
													  	<td>
													  		<html:select property="localInstalacao">
																<html:option value="-1">&nbsp;</html:option>
																<html:options collection="colecaoLocalInstalacao" labelProperty="descricao" property="id" />
															</html:select> 
															<font size="1">&nbsp; </font>
														</td>
													</tr>

					                                <!-- Proteção -->
													
					                                <tr>
					                                  	<td class="style3"><strong>Prote&ccedil;&atilde;o:<strong><span class="style2"><strong><font color="#FF0000">*</font></strong></span></strong><strong><strong><font color="#FF0000"> </font></strong></strong></strong></td>
													  	<td>
													  		<html:select property="protecao">
																<html:option value="-1">&nbsp;</html:option>
																<html:options collection="colecaoProtecao" labelProperty="descricao" property="id" />
															</html:select> 
															<font size="1">&nbsp; </font>
														</td>
			                                		</tr>
			                                		<tr>
			                                  			<td class="style3"><strong>Cavalete:<strong><strong><strong><strong><strong><strong><strong><span class="style2"><strong><font color="#FF0000">*</font></strong></span></strong></strong></strong></strong></strong></strong></strong></strong></td>
			                                  			<td>
			                                  				<strong><b><span class="style2">
															</span></b></strong>
						                                      <p><strong>
						                                        <label>
						                                        <html:radio property="indicadorExistenciaCavalete" value="1" />COM
						                                        </label>
						                                        <label>
						                                        <html:radio property="indicadorExistenciaCavalete" value="2" />SEM
						                                        </label>
															</strong></p>
							                            </td>
							                        </tr>
							                        <tr>
															<td WIDTH="20%"><strong>Matrícula Funcionário:</strong></td>
															<td width="80%" colspan="3"><html:text property="idFuncionario"
																size="8" maxlength="8" tabindex="1"
																onkeypress="limparDescricao();validaEnterComMensagem(event, 'exibirAtualizarInstalacaoHidrometroAction.do', 'idFuncionario', 'Funcionário');" />
															<a 
																href="javascript:abrirPopup('exibirAtualizarInstalacaoHidrometroAction.do?pesquisarFuncionario=OK',400,400);">
															<img src="/gsan/imagens/pesquisa.gif" alt="Pesquisar Funcionario"
																border="0" height="21" width="23"></a> <logic:present
																name="corFuncionario">
										
																<logic:equal name="corFuncionario" value="exception">
																	<html:text property="descricaoFuncionario" size="30"
																		readonly="true"
																		style="background-color:#EFEFEF; border:0; color: #ff0000" />
																</logic:equal>
										
																<logic:notEqual name="corFuncionario" value="exception">
																	<html:text property="descricaoFuncionario" size="30"
																		readonly="true"
																		style="background-color:#EFEFEF; border:0; color: #000000" />
																</logic:notEqual>
										
															</logic:present> <logic:notPresent name="corFuncionario">
										
																<logic:empty name="AtualizarInstalacaoHidrometroActionForm"
																	property="idFuncionario">
																	<html:text property="descricaoFuncionario" value="" size="30"
																		readonly="true"
																		style="background-color:#EFEFEF; border:0; color: #ff0000" />
																</logic:empty>
																<logic:notEmpty name="AtualizarInstalacaoHidrometroActionForm"
																	property="idFuncionario">
																	<html:text property="descricaoFuncionario" size="30"
																		readonly="true"
																		style="background-color:#EFEFEF; border:0; color: #000000" />
																</logic:notEmpty>
										
										
															</logic:notPresent> <a href="javascript:limparFuncionario();"> <img
																src="<bean:message key='caminho.imagens'/>limparcampo.gif"
																alt="Apagar" border="0"></a></td>
														</tr>
							                        
							                        <!-- Leitura Instalação -->
							                        
					                                <tr>
					                                  	<td class="style3"><strong>Leitura Instala&ccedil;&atilde;o<strong>:<strong><strong><span class="style2"><strong><font color="#FF0000">*</font></strong></span></strong></strong></strong></strong></td>
					                                  	<td>
					                                  		<strong><b><span class="style2">
					                                    	<html:text property="leituraInstalacao" size="10" maxlength="8" />
					                                  		</span></b></strong>
					                                  	</td>
					                                </tr>
					                                
					                                <!-- Número do Selo -->
					                                
			                                		<tr>
					                                  	<td class="style3"><strong>N&uacute;mero do Selo:</strong></td>
					                                  	<td>
					                                  		<strong><b><span class="style2">
						                                    <html:text property="numeroSelo" size="10" maxlength="8" />
						                                  	</span></b></strong>
						                                </td>
			                                		</tr>
					                            </table>
					                        </td>
					                    </tr>
					                </table>
					            </td>
	                    	</tr>
							<tr>
								<td colspan="2">
									<table width="100%">	
										<tr>
											<td align="left">
												<input type="button" 
													name="ButtonReset" 
													class="bottonRightCol" 
													value="Desfazer" 
													onClick="javascript:resetar();"> 
												
												<input 	type="button"
														name="ButtonCancelar" 
														class="bottonRightCol" 
														value="Cancelar"
														onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
											</td>
											<td align="right">
												<input 	name="Button" 
														type="button"
														class="bottonRightCol" 
														value="Atualizar" 
														onclick="validaForm();">
											</td>
										</tr>
									</table>
								</td>
							</tr>
				</table>
			</table>
		</table>

	<%@ include file="/jsp/util/rodape.jsp"%>
	
</html:form>
</html:html>
