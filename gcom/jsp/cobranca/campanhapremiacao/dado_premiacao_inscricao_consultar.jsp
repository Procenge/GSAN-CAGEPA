<%@page import="gcom.cobranca.campanhapremiacao.CampanhaCadastro"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="PesquisarActionForm"
	dynamicJavascript="false" />

<script>

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

function exibirOcultarDIV(camada, display){
	var form = document.forms[0];
	
	if (camada == 'camadaDadosCliente'){
		if(eval('camadaDadosCliente').style.display == 'block'){
			eval('camadaDadosCliente').style.display = 'none'
		} else {
			eval('camadaDadosCliente').style.display = 'block'
		}
	} else if (camada == 'camadaEnderecoImovel'){
		if(eval('camadaEnderecoImovel').style.display == 'block'){
			eval('camadaEnderecoImovel').style.display = 'none'
		} else {
			eval('camadaEnderecoImovel').style.display = 'block'
		}
	}else if (camada == 'camadaFonesCliente'){
		if(eval('camadaFonesCliente').style.display == 'block'){
			eval('camadaFonesCliente').style.display = 'none'
		} else {
			eval('camadaFonesCliente').style.display = 'block'
		}
	}else if (camada == 'camadaDadosPremiacao'){
		if(eval('camadaDadosPremiacao').style.display == 'block'){
			eval('camadaDadosPremiacao').style.display = 'none'
		} else {
			eval('camadaDadosPremiacao').style.display = 'block'
		}
	}else if (camada == 'camadaDadosRetiradaPremiacao'){
		if(eval('camadaDadosRetiradaPremiacao').style.display == 'block'){
			eval('camadaDadosRetiradaPremiacao').style.display = 'none'
		} else {
			eval('camadaDadosRetiradaPremiacao').style.display = 'block'
		}
	}else if (camada == 'camadaDadosCancelamentoPremiacao'){
		if(eval('camadaDadosCancelamentoPremiacao').style.display == 'block'){
			eval('camadaDadosCancelamentoPremiacao').style.display = 'none'
		} else {
			eval('camadaDadosCancelamentoPremiacao').style.display = 'block'
		}
	}else if (camada == 'camadaDadosInscricao'){
		if(eval('camadaDadosInscricao').style.display == 'block'){
			eval('camadaDadosInscricao').style.display = 'none'
		} else {
			eval('camadaDadosInscricao').style.display = 'block'
		}
	}
}

//  var bCancel = false;

//     function validatePesquisarActionForm(form) {
//         if (bCancel)
//       return true;
//         else
//        return validateCaracterEspecial(form);
//    }

//     function caracteresespeciais () {
//      this.ab = new Array("nomeMunicipio", "Nome possui caracteres especiais.", new Function ("varName", " return this[varName];"));
//     }

// function validarForm(form){
// 	form.submit();
// }

</script>
</head>

<body leftmargin="5" topmargin="5"
	onload="window.focus();resizePageSemLink(485, 1000);javascript:setarFoco('${requestScope.nomeCampo}');">

<html:form action="/ExibirFiltrarCampanhaPremiacaoAction.do" method="post">
	<table width="452" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="452" valign="top" class="centercoltext">
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
					<td class="parabg">Consultar Dados da Premiação da Inscrição</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
<!-- 			<table width="100%" border="0"> -->
<!-- 				<tr> -->
<!-- 					<td colspan="2">Preencha os campos para pesquisar um -->
<!-- 					munic&iacute;pio:</td> -->
<!-- 					<td align="right"></td> -->
<!--     			</tr> -->
<!--       		</table> -->
    
      		<table width="100%" border="0">
      			<tr bgcolor="#99CCFF">
					<td colspan="3" align="center" bgcolor="#99CCFF"><span class=""> <a
						href="javascript:exibirOcultarDIV('camadaDadosInscricao',true);" />
						<b>Dados da inscrição</b></a></span></td>
				</tr>
				<tr>
				<td>
				<div id="camadaDadosInscricao" style="display: none">
				<table>
				
				
				<tr>
					<td width="142"><strong>Número Inscrição:</strong></td>
					<td><bean:write name="campanhaPremiacao" property="campanhaCadastro.numeroInscricao"/>
				</tr>
				<tr>
					<td width="142"><strong>Imóvel:</strong></td>
					<td><bean:write name="campanhaPremiacao" property="campanhaCadastro.imovel.id"/>
				</tr>
				<tr>
					<td width="142"><strong>Inscrição:</strong></td>
					<td><bean:write name="campanhaPremiacao" property="campanhaCadastro.imovel.inscricaoFormatada"/>
				</tr>
				<tr>
					<logic:present name="campanhaPremiacao" property="campanhaPremio.gerenciaRegional">
						<td width="142"><strong>Gerência Regional:</strong></td>
						<td><bean:write name="campanhaPremiacao" property="campanhaPremio.gerenciaRegional.nome"/>
					</logic:present>
					<logic:present name="campanhaPremiacao" property="campanhaPremio.unidadeNegocio">
						<td width="142"><strong>Unidade de Negócio:</strong></td>
						<td><bean:write name="campanhaPremiacao" property="campanhaPremio.unidadeNegocio.nome"/>
					</logic:present>
					<logic:present name="campanhaPremiacao" property="campanhaPremio.eloPremio">
						<td width="142"><strong>Elo:</strong></td>
						<td><bean:write name="campanhaPremiacao" property="campanhaPremio.eloPremio.descricao"/>
					</logic:present>
					<logic:present name="campanhaPremiacao" property="campanhaPremio.localidade">
						<td width="142"><strong>Localidade:</strong></td>
						<td><bean:write name="campanhaPremiacao" property="campanhaPremio.localidade.descricao"/>
					</logic:present>
				
				</tr>
				<tr>
					<td colspan="3" align="center" bgcolor="#99CCFF"><strong>Endereço do Imóvel</strong></td>
				</tr>
				<tr>
					<td colspan="3"  bgcolor="#FFFFFF" align="center">
						<bean:write name="endereco"/>
					</td>
				</tr>
				<tr bgcolor="#99CCFF">
					<td colspan="3" align="center" bgcolor="#99CCFF"><span class=""> <a
						href="javascript:exibirOcultarDIV('camadaDadosCliente',true);" />
						<b>Dados do Cliente</b></a></span></td>
				</tr>
				<logic:present name="indicadorResidencial">
				<tr bgcolor="#99CCFF">
					<td colspan="3">
					<div id="camadaDadosCliente" style="display: block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						
						<tr bgcolor="#cbe5fe">
							<td>
							
								<table width="100%">
								
									<tr>
									
										<td><strong>Nome do Cliente:<font color="#FF0000">*</font></strong></td>
										
										<td><strong> <html:text readonly="true" name="campanhaPremiacao" property="campanhaCadastro.nomeCliente" size="50" maxlength= "50" /> </strong></td>
									</tr>
									<tr>
						                <td><strong>Tipo de Relação:<font color="#FF0000">*</font></strong></td>
						                <td align="right">
						       			    <div align="left">
						                        <html:radio disabled="true" name="campanhaPremiacao" property="campanhaCadastro.codigoTipoRelacaoClienteImovel" value="<%=(CampanhaCadastro.TIPO_RELACAO_USUARIO).toString()%>"/><strong>Usuário
						                        <html:radio disabled="true" name="campanhaPremiacao" property="campanhaCadastro.codigoTipoRelacaoClienteImovel" value="<%=(CampanhaCadastro.TIPO_RELACAO_RESPONSAVEL).toString()%>"/>Responsável
						                        <html:radio disabled="true" name="campanhaPremiacao" property="campanhaCadastro.codigoTipoRelacaoClienteImovel" value="<%=(CampanhaCadastro.TIPO_RELACAO_USUARIO_RESPONSAVEL).toString()%>"/>Usuário e Responsável</strong>
											</div>
										</td>
						            </tr>
						          <logic:equal name="indicadorResidencial" value="<%=ConstantesSistema.ATIVO%>">
						            <tr>
										<td width="162"><strong>CPF:<font color="#FF0000">*</font></strong></td>
										<td><strong> <html:text readonly="true" name="campanhaPremiacao" property="campanhaCadastro.numeroCPF" size="11" maxlength= "11" /> </strong></td>
									</tr>
									<tr>
										<td width="162"><strong>RG:<font color="#FF0000">*</font></strong></td>
										<td style="padding-left: 0">
											<table width="100%">
												<tr>
													<td style="padding-left: 0"><strong> <html:text readonly="true" name="campanhaPremiacao" property="campanhaCadastro.numeroRG" size="11" maxlength= "11" style="margin-left:0px;" /> </strong></td>
													<td align="right"><strong>Data de Emissão:<font color="#FF0000">*</font></strong></td>
													<td width="30%" align="left"><strong> </strong> <html:text 
													readonly="true"
													onkeyup="javascript:mascara_data(this);"
													onblur="javascript:verifica_tamanho_data(this);"
													name="campanhaPremiacao" property="campanhaCadastro.dataRGEmissao" size="10" maxlength="10" />
													</td>
												</tr>
											</table>
										</td>
										
										
									</tr>
									<tr>
										<td width="162"><strong>Órgão Expedidor:<font color="#FF0000">*</font></strong></td>
										<td style="padding-left: 0">
											<table width="100%">
												<tr>

													<td >
														<html:select disabled="true" name="campanhaPremiacao" property="campanhaCadastro.orgaoExpedidorRG.id" tabindex="1">
														<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option>
														<html:options collection="colOrgaoExpedidorRg" labelProperty="descricao" property="id" />
														</html:select>
													</td>
													<td align="right"><strong>Estado:<font color="#FF0000">*</font></strong></td>
													<td >
														<html:select disabled="true" name="campanhaPremiacao" property="campanhaCadastro.unidadeFederacao.id" tabindex="1">
														<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
														<html:options collection="colEstados" labelProperty="sigla" property="id" />
														</html:select>
													</td>
												</tr>
											</table>
										</td>
									
									</tr>
									<tr>
										<td align="left"><strong>Data de Nascimento:<font color="#FF0000">*</font></strong></td>
													<td width="30%" align="left"><strong> </strong> <html:text 
													readonly="true"
													onkeyup="javascript:mascara_data(this);"
													onblur="javascript:verifica_tamanho_data(this);"
													name="campanhaPremiacao" property="campanhaCadastro.dataNascimento" size="10" maxlength="10" />
													</td>
									
									</tr>
									<tr>
										<td><strong>Nome da Mãe:<font color="#FF0000">*</font></strong></td>
										<td><strong> <html:text readonly="true" name="campanhaPremiacao" property="campanhaCadastro.nomeMae" size="50" maxlength= "50" /> </strong></td>
									</tr>
								</logic:equal>
								<logic:equal name="indicadorResidencial" value="<%=ConstantesSistema.INATIVO %>">
									<tr>
										<td width="162"><strong>CNPJ:<font color="#FF0000">*</font></strong></td>
										<td><strong> <html:text readonly="true" name="campanhaPremiacao" property="campanhaCadastro.numeroCNPJ" size="14" maxlength= "14" /> </strong></td>
									</tr>
								</logic:equal>
									<tr>
										<td width="162"><strong>E-Mail:</strong></td>
										<td><strong> <html:text readonly="true" name="campanhaPremiacao" property="campanhaCadastro.dsEmail" size="40" maxlength= "40" /> </strong></td>
									</tr>
								
								</table>
								
							</td>
						</tr>
						
					</table>
					</div>								
					</td>
				</tr>	
				</logic:present>
				<tr bgcolor="#99CCFF" style="padding-top: 300px;">
					<td colspan="3" align="center"><span class=""> <a
						href="javascript:exibirOcultarDIV('camadaFonesCliente',true);" />
						<b>Fones do Cliente</b></a></span></td>
				</tr>
				
				<logic:present name="indicadorResidencial">
				<tr bgcolor="#99CCFF">
					<td colspan="3">
					<div id="camadaFonesCliente" style="display: block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						
						<tr bgcolor="#cbe5fe">
							<td>
								<table width="100%" border="0">
					
				
					
					
					
					<tr>
						<td colspan="3">
						<table width="100%" border="0">
							<tr>
								<td colspan="2">
									<table width="100%" cellpadding="0" cellspacing="0">
										<tr>
											<td height="0">
												<table width="100%" bgcolor="#90c7fc">
													<!--header da tabela interna -->
													<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
														<td width="50%" align="center">
															<strong>Telefone</strong>
														</td>
														<td width="30%" align="center">
															<strong>Tipo</strong>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td height="100">
												
												<div style="width: 100%; height: 100%; overflow: auto;">
												<input type="hidden" name="idRegistrosRemocao" value="" />
												<table width="100%" align="center" bgcolor="#99CCFF">
												  <logic:present name="colecaoCadastroFone">
													<%int cont = 0;%>
													<%--Campo que vai guardar o valor do telefone a ser removido--%>
													<logic:iterate name="colecaoCadastroFone" id="cadastroFone" scope="session">
														<%cont = cont + 1;
														if (cont % 2 == 0) {%>
														<tr bgcolor="#cbe5fe">
														<%} else { %>
														<tr bgcolor="#FFFFFF">
														<%}%>															

															<td width="50%" align="center">
																<strong>
																<bean:write name="cadastroFone" property="codigoDDD"/> 
																<logic:notEmpty name="cadastroFone" property="numeroFoneRamal">
																	&nbsp;Ramal&nbsp;<bean:write name="cadastroFone" property="numeroFoneRamal" />
																</logic:notEmpty> 
																</strong>
															</td>
															<td width="30%" align="center">
																<strong><bean:write name="cadastroFone" property="foneTipo.descricao" /></strong>
															</td>
														</tr>
													</logic:iterate>
													</logic:present>
												</table>
												</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<p>&nbsp;</p>
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
				<tr bgcolor="#99CCFF" style="padding-top: 300px;">
					<td colspan="3" align="center"><span class=""> <a
						href="javascript:exibirOcultarDIV('camadaDadosPremiacao',true);" />
						<b>Dados da Premiação</b></a></span></td>
				</tr>
				<logic:present name="indicadorResidencial">
				<tr bgcolor="#99CCFF">
					<td colspan="3">
					<div id="camadaDadosPremiacao" style="display: block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						
						<tr bgcolor="#cbe5fe">
							<td>
								<table width="100%" border="0">
					
					<tr>
						<td colspan="3">
						<table width="100%" border="0">
							<tr>
								<td colspan="2">
									<table width="100%" cellpadding="0" cellspacing="0">
										<tr>
											<td>
												<strong>Prêmio:</strong>
											</td>
											<td>
												<bean:write name="campanhaPremiacao" property="campanhaPremio.descricao"/>
											</td>
									    </tr>
									    <tr>
											<td>
												<strong>Ordem Premiação:</strong>
											</td>
											<td>
												<bean:write name="campanhaPremiacao" property="campanhaPremio.numeroOrdemPremiacao"/>
											</td>
									    </tr>
									    <tr>
											<td>
												<strong>Data Sorteio:</strong>
											</td>
											<td>
												<bean:write name="campanhaPremiacao" property="campanhaSorteio.dataSorteioFormatada"/>
											</td>
									    </tr>
									    <tr bgcolor="#99CCFF" style="padding-top: 300px;">
											<td colspan="3" align="center"><span class=""> <a
												href="javascript:exibirOcultarDIV('camadaDadosRetiradaPremio',true);" />
												<b>Dados da Retirada do Premio</b></a></span></td>
										</tr>
										<logic:present name="campanhaPremiacao" property="campanhaPremiacaoMotCancel">
										<tr bgcolor="#99CCFF">
											<td colspan="3">
											<div id="camadaDadosRetiradaPremio" style="display: block">
											<table width="100%" border="0" bgcolor="#99CCFF">
												
												<tr bgcolor="#cbe5fe">
													<td>
														<table width="100%" border="0">
											
															<tr>
																<td colspan="3">
																<table width="100%" border="0">
																	<tr>
																		<td colspan="2">
																			<table width="100%" cellpadding="0" cellspacing="0">
																				<tr>
																					<td>
																						<strong>Data Retirada Prêmio:</strong>
																					</td>
																					<td>
																						<bean:write name="campanhaPremiacao" property="dataRetiradaPremio"/>
																					</td>
																			    </tr>
																			    <tr>
																					<td>
																						<strong>Usuário Responsável Entrega Prêmio:</strong>
																					</td>
																					<td>
																						<bean:write name="campanhaPremiacao" property="usuarioEntregaPremio.nomeUsuario"/>
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
											</table>
											</div>
										</td>
										</tr>
										</logic:present>
									  	<tr bgcolor="#99CCFF" style="padding-top: 300px;">
											<td colspan="3" align="center"><span class=""> <a
												href="javascript:exibirOcultarDIV('camadaDadosCancelamentoPremiacao',true);" />
												<b>Dados do Cancelamento da Premiação</b></a></span></td>
										</tr>
										<logic:present name="campanhaPremiacao" property="campanhaPremiacaoMotCancel">
										<tr bgcolor="#99CCFF">
											<td colspan="3">
											<div id="camadaDadosCancelamentoPremiacao" style="display: block">
											<table width="100%" border="0" bgcolor="#99CCFF">
												
												<tr bgcolor="#cbe5fe">
													<td>
														<table width="100%" border="0">
											
															<tr>
																<td colspan="3">
																<table width="100%" border="0">
																	<tr>
																		<td colspan="2">
																			<table width="100%" cellpadding="0" cellspacing="0">
																				<tr>
																					<td>
																						<strong>Data Cancelamento Premiação:</strong>
																					</td>
																					<td>
																						<bean:write name="campanhaPremiacao" property="dataCancelamentoPremiacao"/>
																					</td>
																			    </tr>
																			    <tr>
																					<td>
																						<strong>Usuário Responsável Cancelamento Premiação:</strong>
																					</td>
																					<td>
																						<bean:write name="campanhaPremiacao" property="usuarioCancelamentoPremiacao.nomeUsuario"/>
																					</td>
																			    </tr>
																			    <tr>
																					<td>
																						<strong>Motivo Cancelamento Premiação:</strong>
																					</td>
																					<td>
																						<bean:write name="campanhaPremiacao" property="campanhaPremiacaoMotCancel.descricao"/>
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
											</table>
											</div>
										</td>
										</tr>
										</logic:present>
									</table>
								</td>
							</tr>
						</table>
						<p>&nbsp;</p>
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
<!-- 				<tr> -->
<!-- 					<td>&nbsp;</td> -->
<%-- 					<td colspan="3"><html:radio property="tipoPesquisa" --%>
<%-- 						value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" /> --%>
<%-- 						Iniciando pelo texto&nbsp;<html:radio property="tipoPesquisa" --%>
<%-- 							tabindex="5" --%>
<%-- 							value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" /> --%>
<!-- 						Contendo o texto</td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td width="142"><strong>Regi&atilde;o Desenvolvimento:</strong></td> -->
<%-- 					<td><html:select property="idRegiaoDesenvolvimento" tabindex="2"> --%>
<%-- 						<html:option value="-1">&nbsp;</html:option> --%>
<%-- 						<html:options collection="regiaoDesenvolvimentos" --%>
<%-- 							labelProperty="nome" property="id" /> --%>
<%-- 					</html:select></td> --%>
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td><strong>Regi&atilde;o:</strong></td> -->
<%-- 					<td><html:select property="idRegiao" tabindex="3" --%>
<%-- 						onchange="pesquisaColecaoReload('exibirPesquisarMunicipioAction.do?objetoConsulta=1','idRegiao');"> --%>
<%-- 						<html:option value="-1">&nbsp;</html:option> --%>
<%-- 						<html:options collection="regioes" labelProperty="nome" --%>
<%-- 							property="id" /> --%>
<%-- 					</html:select></td> --%>
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td><strong>Microrregi&atilde;o:</strong></td> -->
<%-- 					<td><html:select property="idMicrorregiao" tabindex="4"> --%>
<%-- 						<html:option value="-1">&nbsp;</html:option> --%>
<%-- 						<html:options collection="microrregioes" labelProperty="nome" --%>
<%-- 							property="id" /> --%>
<%-- 					</html:select></td> --%>
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td><strong>Unidade Federa&ccedil;&atilde;o:</strong></td> -->
<%-- 					<td><html:select property="idUnidadeFederacao" tabindex="5"> --%>
<%-- 						<html:option value="-1">&nbsp;</html:option> --%>
<%-- 						<html:options collection="unidadesFederacao" --%>
<%-- 							labelProperty="descricao" property="id" /> --%>
<%-- 					</html:select></td> --%>
<!-- 				</tr> -->
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
<!-- 				<tr> -->
<!-- 					<td height="24" colspan="3" width="80%"> -->
<!-- 		          	<INPUT TYPE="button" class="bottonRightCol" value="Limpar" onclick="redirecionarSubmit('exibirPesquisarMunicipioAction.do?indicadorUsoTodos=1');"/> -->
<!--         			  	&nbsp;&nbsp; -->
<%--        			  	<logic:present name="caminhoRetornoTelaPesquisaMunicipio"> --%>
<%-- 		          		<INPUT TYPE="button" class="bottonRightCol" value="Voltar" onclick="redirecionarSubmit('${caminhoRetornoTelaPesquisaMunicipio}.do?objetoConsulta=1&caminhoRetornoTelaPesquisaBairro=${caminho}');"/> --%>
<%--         		  	</logic:present> --%>
<!--         		  	</td> -->
<!-- 					<td align="right"> -->
<%-- 					<html:submit styleClass="bottonRightCol" tabindex="6" --%>
<%-- 						value="Pesquisar" /></td> --%>
<!-- 					<td>&nbsp;</td> -->
<!-- 				</tr> -->

			</table>
			</div>
				</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
		
	</table>
</html:form>
</body>
</html:html>
