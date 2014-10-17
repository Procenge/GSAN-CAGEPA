<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.cadastro.cliente.ClienteImovel"%>
<%@ page import="gcom.arrecadacao.debitoautomatico.DebitoAutomaticoMovimentoHelper"%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<html:javascript staticJavascript="false"  formName="CobrancaGrupoActionForm" />

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>

<script>



function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.forms[0];

    if (tipoConsulta == 'imovel') {
      form.matriculaImovel.value = codigoRegistro;
      form.inscricaoImovel.value = descricaoRegistro;
	  form.inscricaoImovel.style.color = '#000000';	    
	  submeterFormPadrao(form);
   }
    
}


function validarForm(){
	var form = document.forms[0];
	if (form.matriculaImovel.value != null && form.matriculaImovel.value != "") {
		submeterFormPadrao(form);
	} else {
		alert('Informe a matrícula do imóvel.');
	}
}

function limparForm(){
	var form = document.forms[0];
	limparFormImovel();
}


function limparFormImovel() {
	var form = document.forms[0];
	
  	form.matriculaImovel.value = "";
    form.inscricaoImovel.value = "";
    form.situacaoAgua.value = "";
    form.situacaoEsgoto.value = "";
    form.matriculaImovel.focus();
    
    submeterFormPadrao(form);
}

</script>
</head>

<body leftmargin="5" topmargin="5">

<div id="formDiv">
<html:form action="/consultarHistoricoMovimentoDebitoAutomaticoFiltrarAction" name="ConsultarHistoricoMovimentoDebitoAutomaticoFiltrarActionForm" 
		type="gcom.gui.arrecadacao.debitoautomatico.ConsultarHistoricoMovimentoDebitoAutomaticoFiltrarActionForm" method="post">

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

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
		
		<td width="655" valign="top" class="centercoltext">
			<!--Início Tabela Reference a Paginação da Tela de Processo-->
            <table>
              <tr>
                <td></td>
              </tr>
            </table>
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="11"><img border="0" src="<bean:message key='caminho.imagens'/>parahead_left.gif"/></td>
                <td class="parabg">Histórico de Movimento de Débito Automático </td>
                <td width="11" valign="top"><img border="0" src="<bean:message key='caminho.imagens'/>parahead_right.gif"/></td>
              </tr>
            </table>
			<p>&nbsp;</p>
			
            <table width="100%" border="0">

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
				                <td>
				                	<strong>
				                		<span class="style2">Matr&iacute;cula do Im&oacute;vel</span>:
				                		<font color="#FF0000">*</font>
				                	</strong>
				                </td>
				                <td colspan="6"><span class="style2"><strong>
									<html:text property="matriculaImovel" size="9" maxlength="9" 
											   onkeypress="javascript:validaEnterComMensagem(event, 'consultarHistoricoMovimentoDebitoAutomaticoFiltrarAction.do?validaImovel=true', 'matriculaImovel','Matrícula do Imovel');"
											   tabindex="3" />
									
									<a href="javascript:chamarPopup('exibirPesquisarImovelAction.do', 'imovel', null, null, 275, 480, '',document.forms[0].matriculaImovel);">
										<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23"	height="21" name="imagem" alt="Pesquisar" border="0"></a>
										 
									<logic:present name="inscricaoImovelEncontrada" scope="session">
										<html:text property="inscricaoImovel" readonly="true" 
												   style="background-color:#EFEFEF; border:0; color: #000000" size="40" maxlength="40" />
									</logic:present> 
				
									<logic:notPresent name="inscricaoImovelEncontrada" scope="session">
										<html:text property="inscricaoImovel" readonly="true" 
												   style="background-color:#EFEFEF; border:0; color: #ff0000" size="40" maxlength="40" />
									</logic:notPresent>  						 
										 
									<a href="javascript:limparFormImovel();">
										<img border="0" title="Apagar" src="<bean:message key='caminho.imagens'/>limparcampo.gif" /></a>
				                  </strong></span></td>
				              </tr>
							
							  <tr>
									<td height="10">
										<div class="style9"><strong>Situação de Água:</strong></div>
									</td>
									<td>
										<html:text property="situacaoAgua" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"
											size="15" maxlength="15" />
									</td>
									<td width="146">
										<strong>Situação de Esgoto:</strong>
									</td>
									<td width="123">
										<html:text property="situacaoEsgoto" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"
											size="15" maxlength="15" />
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
					<table width="100%" border="0">
						<tr>
							<td colspan="4">
							<table width="100%" align="center" bgcolor="#90c7fc" border="0">
								<tr bordercolor="#79bbfd">
									<td colspan="5" align="center" bgcolor="#79bbfd"><strong>Clientes</strong></td>
								</tr>
								<tr bordercolor="#000000">
									<td width="28%" bgcolor="#90c7fc" align="center">
										<div class="style9"><font color="#000000" style="font-size:9px"
											face="Verdana, Arial, Helvetica, sans-serif"> 
											<strong>Nome do Cliente</strong> </font>
										</div>
									</td>
									
									<td width="17%" bgcolor="#90c7fc" align="center">
										<div class="style9"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
											<strong>Tipo da Rela&ccedil;&atilde;o</strong> </font>
										</div>
									</td>
									
									<td width="19%" bgcolor="#90c7fc" align="center">
										<div class="style9"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
											<strong>Data In&iacute;cio Rela&ccedil;&atilde;o</strong></font>
										</div>
									</td>
									
									<td width="16%" bgcolor="#90c7fc" align="center">
										<div class="style9"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
											<strong>Telefone</strong></font>
										</div>
									</td>
									
									<td width="20%" bgcolor="#90c7fc" align="center">
										<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
											<strong>CPF/CNPJ</strong> 
										</font>
									</td>
								</tr>
								
								<tr>
									<td width="100%" colspan="5">
									<div style="width: 100%; height: 100%; overflow: auto;">
								
									<table width="100%" align="left" bgcolor="#99CCFF">
										<!--corpo da segunda tabela-->
										<%int cont = 0;%>
										<logic:notEmpty name="imovelClientes">
											<logic:iterate name="imovelClientes" id="imovelCliente" type="ClienteImovel">
											
												<%cont = cont + 1;
												if (cont % 2 == 0) {%>
												<tr bgcolor="#cbe5fe">
													<%} else {%>
												<tr bgcolor="#FFFFFF">
													<%}%>
													
													<td width="28%" bordercolor="#90c7fc" align="left">
													<div class="style9"><font color="#000000"
														style="font-size:9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
														name="imovelCliente" property="cliente">
														<a
															href="javascript:abrirPopup('exibirConsultarClienteAction.do?desabilitarPesquisaCliente=SIM&codigoCliente='+<bean:write name="imovelCliente" property="cliente.id" />, 500, 800);">
														<bean:write name="imovelCliente" property="cliente.nome" />
														</a>
													</logic:present> </font></div>
													</td>
													<td width="17%" align="left">
													<div class="style9"><font color="#000000"
														style="font-size:9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
														name="imovelCliente" property="clienteRelacaoTipo">
														<bean:write name="imovelCliente"
															property="clienteRelacaoTipo.descricao" />
													</logic:present> </font></div>
													</td>
													<td width="19%" align="center">
													<div class="style9"><font color="#000000"
														style="font-size:9px"
														face="Verdana, Arial, Helvetica, sans-serif"> </font></div>
													<bean:write name="imovelCliente"
														property="dataInicioRelacao" formatKey="date.format" /></td>

													<td width="16%" align="right"><logic:notEmpty
														name="imovelCliente" property="cliente">
														<bean:define name="imovelCliente" property="cliente"
															id="cliente" />
														<logic:notEmpty name="cliente" property="clienteFones">
															<bean:define name="cliente" property="clienteFones"
																id="clienteFones" />
															<logic:iterate name="clienteFones" id="clienteFone">
																<div class="style9">
																<div align="center"><font color="#000000"
																	style="font-size:9px"
																	face="Verdana, Arial, Helvetica, sans-serif"> </font></div>
																<bean:write name="clienteFone" property="dddTelefone" />
																</div>
															</logic:iterate>
														</logic:notEmpty>
													</logic:notEmpty></td>
													<td width="20%" align="right"><font color="#000000"
														style="font-size:9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
														name="imovelCliente" property="cliente.cpf">
														<bean:write name="imovelCliente"
															property="cliente.cpfFormatado" />
													</logic:notEmpty> <logic:notEmpty name="imovelCliente"
														property="cliente.cnpj">
														<bean:write name="imovelCliente"
															property="cliente.cnpjFormatado" />
													</logic:notEmpty> </font></td>
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
				</td>		
			</tr>
			
			
			<!-- Débitos Automáticos -->
			
			
			<tr>
				<td>
					<table width="100%" border="0">
						<tr>
							<td colspan="4">
							<table width="100%" align="center" bgcolor="#90c7fc" border="0">
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
										<div class="style9"><font style="font-size: 9px;" color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
										 <strong>Ident.Cliente no Banco</strong> </font></div>
									</td>
		
									<td align="center" bgcolor="#90c7fc" width="12%">
										<font style="font-size: 9px;" color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
											<strong>Data Op&ccedil;&atilde;o</strong></font>
									</td>
		
									<td align="center" bgcolor="#90c7fc" width="18%">
										<font style="font-size: 9px;" color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
											<strong>Data Implanta&ccedil;&atilde;o</strong></font>
									</td>
		
									<td align="center" bgcolor="#90c7fc">
										<div class="style9">
											<font style="font-size: 9px;" color="#000000" face="Verdana, Arial, Helvetica, sans-serif"> 
												<strong>Data Exclus&atilde;o</strong></font>
										</div>
									</td>
								</tr>
								
								<tr>
							<td colspan="6">
							<div style="width: 100%; height: 100%; overflow: auto;">
							<table width="100%" align="left" bgcolor="#99CCFF">
								<!--corpo da segunda tabela-->
								<%cont = 0;%>
								<logic:notEmpty name="colecaoDebitosAutomaticos" scope="session">
									<logic:iterate name="colecaoDebitosAutomaticos" id="debitosAutomaticos">
										<%cont = cont + 1;
										  if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
											<%} else {%>
										<tr bgcolor="#FFFFFF">
											<%}%>

											<td align="center" width="26%">
												<div class="style9">
												<font style="font-size: 9px;" color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
													<logic:present name="debitosAutomaticos" property="agencia">
														<bean:define name="debitosAutomaticos" property="agencia" id="agencia" />
														<logic:present name="agencia" property="banco">
															<bean:define name="agencia" property="banco" id="banco" />
															<bean:write name="banco" property="descricaoAbreviada" />
														</logic:present>	
													</logic:present>
												</font>
												</div>
											</td>

											<td align="center" width="8%">
												<font style="font-size: 9px;" color="#000000" face="Verdana, Arial, Helvetica, sans-serif">
													<logic:present name="debitosAutomaticos" property="agencia">
														<bean:write name="debitosAutomaticos"
															property="agencia.codigoAgencia" />
													</logic:present>
												</font>
											</td>

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
							</td>
						</tr>
				</table>
				</td>		
			</tr>
				
				
				<!-- Movimentos débitos automático Contas -->
				
				
				
			<tr>
				<td>
					<table width="100%" border="0">
						<tr>
							<td colspan="4">
							<table width="100%" align="center" bgcolor="#90c7fc" border="0">
								<tr bordercolor="#79bbfd">
									<td colspan="7" align="center" bgcolor="#79bbfd"><strong>Movimentos débitos automático Contas</strong></td>
								</tr>
								<tr bordercolor="#000000">
									<td width="10%" bgcolor="#90c7fc" align="center">
										<div class="style9">
											<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
												<strong>Referência</strong> 
											</font>
										</div>
									</td>
									
									<td width="15%" bgcolor="#90c7fc" align="center">
										<div class="style9"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
											<strong>Data de Envio</strong> </font>
										</div>
									</td>

									<td width="15%" bgcolor="#90c7fc" align="center">
										<div class="style9"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
											<strong>Data de Vencimento</strong> </font>
										</div>
									</td>

									<td width="15%" bgcolor="#90c7fc" align="center">
										<div class="style9"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
											<strong>Data de Retorno</strong> </font>
										</div>
									</td>
									
									<td width="17%" bgcolor="#90c7fc" align="center">
										<div class="style9"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
											<strong>Valor de Débito</strong></font>
										</div>
									</td>
									
									<td width="13%" bgcolor="#90c7fc" align="center">
										<div class="style9"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
											<strong>Situação</strong></font>
										</div>
									</td>
									
									<td width="15%" bgcolor="#90c7fc" align="center">
										<div class="style9"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
											<strong>Ocorrência</strong></font>
										</div>
									</td>
									
									
								</tr>
								
								<tr>
									<td width="100%" colspan="8">
									<div style="width: 100%; height: 100%; overflow: auto;">
								
									<table width="100%" align="left" bgcolor="#99CCFF">
										<!--corpo da segunda tabela-->
										<%int contMovCon = 0;%>
										<logic:notEmpty name="colecaoDebitoAutoMovConta">
											<logic:iterate name="colecaoDebitoAutoMovConta" id="helper" type="DebitoAutomaticoMovimentoHelper">
											
												<%contMovCon = contMovCon + 1;
												if (contMovCon % 2 == 0) {%>
												<tr bgcolor="#cbe5fe">
													<%} else {%>
												<tr bgcolor="#FFFFFF">
													<%}%>

													<td width="10%" bordercolor="#90c7fc" align="center">
														<div class="style9">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																<bean:write name="helper" property="referenciaConta" />
															</font>
														</div>
													</td>
													
													<td width="15%" bordercolor="#90c7fc" align="center">
														<div class="style9">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																<bean:write name="helper" property="dataEnvioBanco" />
															</font>
														</div>
													</td>
													
													<td width="15%" bordercolor="#90c7fc" align="center">
														<div class="style9">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																<bean:write name="helper" property="dataVencimento" />
															</font>
														</div>
													</td>
													
													<td width="15%" bordercolor="#90c7fc" align="center">
														<div class="style9">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																<bean:write name="helper" property="dataRetornoBanco" />
															</font>
														</div>
													</td>
													
													<td width="17%" bordercolor="#90c7fc" align="center">
														<div class="style9">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																<bean:write name="helper" property="valorDebito" />
															</font>
														</div>
													</td>
													
													<td width="13%" bordercolor="#90c7fc" align="center">
														<div class="style9" title="${helper.descricaoSituacao}">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																<bean:write name="helper" property="situacao"  />
															</font>
														</div>
													</td>
													
													<td width="15%" bordercolor="#90c7fc" align="center">
														<div class="style9">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																<bean:write name="helper" property="ocorrencia" />
															</font>
														</div>
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
				</td>		
			</tr>
			  
			  
			  		
				<!-- Movimentos débitos automático Guia Pagamento -->
				
				
				
			<tr>
				<td>
					<table width="100%" border="0">
						<tr>
							<td colspan="4">
							<table width="100%" align="center" bgcolor="#90c7fc" border="0">
								<tr bordercolor="#79bbfd">
									<td colspan="7" align="center" bgcolor="#79bbfd"><strong>Movimentos débitos automático Guia Pagamento</strong></td>
								</tr>
								<tr bordercolor="#000000">
									<td width="10%" bgcolor="#90c7fc" align="center">
										<div class="style9">
											<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
												<strong>Guia/Prestação</strong> 
											</font>
										</div>
									</td>
									
									<td width="15%" bgcolor="#90c7fc" align="center">
										<div class="style9"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
											<strong>Data de Envio</strong> </font>
										</div>
									</td>

									<td width="15%" bgcolor="#90c7fc" align="center">
										<div class="style9"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
											<strong>Data de Vencimento</strong> </font>
										</div>
									</td>

									<td width="15%" bgcolor="#90c7fc" align="center">
										<div class="style9"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
											<strong>Data de Retorno</strong> </font>
										</div>
									</td>
									
									<td width="17%" bgcolor="#90c7fc" align="center">
										<div class="style9"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
											<strong>Valor de Débito</strong></font>
										</div>
									</td>
									
									<td width="13%" bgcolor="#90c7fc" align="center">
										<div class="style9"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
											<strong>Situação</strong></font>
										</div>
									</td>
									
									<td width="15%" bgcolor="#90c7fc" align="center">
										<div class="style9"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
											<strong>Ocorrência</strong></font>
										</div>
									</td>
									
									
								</tr>
								
								<tr>
									<td width="100%" colspan="8">
									<div style="width: 100%; height: 100%; overflow: auto;">
								
									<table width="100%" align="left" bgcolor="#99CCFF">
										<!--corpo da segunda tabela-->
										<%int contMovGuia = 0;%>
										<logic:notEmpty name="colecaoDebitoAutoMovGuia">
											<logic:iterate name="colecaoDebitoAutoMovGuia" id="helper" type="DebitoAutomaticoMovimentoHelper">
											
												<%contMovGuia = contMovGuia + 1;
												if (contMovGuia % 2 == 0) {%>
												<tr bgcolor="#cbe5fe">
													<%} else {%>
												<tr bgcolor="#FFFFFF">
													<%}%>

													<td width="10%" bordercolor="#90c7fc" align="center">
														<div class="style9">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																<bean:write name="helper" property="parcelaGuia" />
															</font>
														</div>
													</td>
													
													<td width="15%" bordercolor="#90c7fc" align="center">
														<div class="style9">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																<bean:write name="helper" property="dataEnvioBanco" />
															</font>
														</div>
													</td>
													
													<td width="15%" bordercolor="#90c7fc" align="center">
														<div class="style9">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																<bean:write name="helper" property="dataVencimento" />
															</font>
														</div>
													</td>
													
													<td width="15%" bordercolor="#90c7fc" align="center">
														<div class="style9">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																<bean:write name="helper" property="dataRetornoBanco" />
															</font>
														</div>
													</td>
													
													<td width="17%" bordercolor="#90c7fc" align="center">
														<div class="style9">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																<bean:write name="helper" property="valorDebito" />
															</font>
														</div>
													</td>
													
													<td width="13%" bordercolor="#90c7fc" align="center">
														<div class="style9" title="${helper.descricaoSituacao}">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																<bean:write name="helper" property="situacao"  />
															</font>
														</div>
													</td>
													
													<td width="15%" bordercolor="#90c7fc" align="center">
														<div class="style9">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																<bean:write name="helper" property="ocorrencia" />
															</font>
														</div>
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
				</td>		
			</tr>
			
			  <tr>
				<td>
					<input name="Button" type="button" class="bottonRightCol" value="Limpar" align="left" onclick="javascript:limparFormImovel();">
				</td>
		      </tr>

            </table>
          <p>&nbsp;</p></tr>
		<tr>
		  	<td colspan="3">
			</td>
		</tr>
</table>

<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</div>
</body>

<%@ include file="/jsp/util/telaespera.jsp"%>

<script>

</script>

</html:html>