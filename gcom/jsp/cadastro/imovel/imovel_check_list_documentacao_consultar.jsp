<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="gcom.util.ConstantesSistema" isELIgnored="false"%>

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="ImovelCheckListDocumentacaoForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">

function limparForm(){
   	var form = document.forms[0];
	form.action = 'exibirConsultarImovelCheckListDocumentacaoAction.do?limparForm=OK';
	form.submit();
}

function validaForm() {
	
	var form = document.ImovelCheckListDocumentacaoForm;
	submeterFormPadrao(form);
}

//Recebe os dados do(s) popup(s)
function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

  var form = document.ImovelCheckListDocumentacaoForm;

   if (tipoConsulta == 'imovel') {
    form.idImovel.value = codigoRegistro;
    form.action = 'exibirConsultarImovelCheckListDocumentacaoAction.do'
    submeterFormPadrao(form);
  }
}

</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');">
<html:form action="/atualizarImovelCheckListDocumentacaoAction.do" method="post"
	name="ImovelCheckListDocumentacaoForm"
	type="gcom.gui.cadastro.imovel.ImovelCheckListDocumentacaoForm">
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
			
			<td valign="top" class="centercoltext"><!--Início Tabela Reference a Páginação da Tela de Processo-->
			
			<p>&nbsp;</p>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Consultar Check List de Documentação do Imóvel</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>						
				</tr>				
			</table>
			
		<p>&nbsp;</p>
			<table width="100%" border="0">
				<%String cor = "#FFFFFF";%>
				<tr>
				<td colspan="1">
					<td align="left">Para Consultar um check list, informe os dados abaixo:</td>
					<table width="100%" align="center" bgcolor="#99CCFF" border="0">
						<tr>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">
							<table width="100%" border="0">
								<tr>
									<td bordercolor="#000000" width="3%"><strong>Im&oacute;vel:<font
										color="#FF0000">*</font></strong></td>
									<td width="75%" colspan="3"><html:text
										property="idImovel" maxlength="9" size="9"
										onkeypress="validaEnterComMensagem(event, 
										'exibirConsultarImovelCheckListDocumentacaoAction.do?indicadorNovo=OK',
										'idImovel','Im&oacute;vel');"/> 
									<a
										href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
									<img width="23" height="21"
										src="<bean:message key="caminho.imagens"/>pesquisa.gif"
										border="0" /></a><logic:present
										name="idImovelDadosCadastraisNaoEncontrado" scope="request">
										<html:text property="matriculaImovelDados" size="40"
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #ff0000" />
										</logic:present> <logic:notPresent
										name="idImovelDadosCadastraisNaoEncontrado" scope="request">
										<logic:present name="valorMatriculaImovelDadosCadastrais"
											scope="request">
											<html:text property="matriculaImovelDados"
												size="40" readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:present>
										<logic:notPresent name="valorMatriculaImovelDadosCadastrais"
											scope="request">
											<html:text property="matriculaImovelDados"
												size="40" readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:notPresent>
									</logic:notPresent> <a href="javascript:limparForm();"><img
										src="<bean:message key="caminho.imagens"/>limparcampo.gif"
										border="0" title="Apagar" /></a></td>
									
			</table>
				<table width="100%" border="0">
					<tr>
									<td height="10">
									<div class="style9"><strong>Localidade:</strong></div>
									</td>
									<td><html:text property="nomeLocalidade"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="40" maxlength="15" /></td>
									</tr>
									<tr>
									<td height="10" width="18%">
									<div class="style9"><strong>Situação de Água:</strong></div>
									</td>
									<td><html:text property="codigoSituacaoAgua"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="1" maxlength="2" />
										<html:text
										property="descricaoSituacaoAgua" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="25" maxlength="35" /></td>
									</tr>
									<tr>
									<td height="10" width="20%">
									<div class="style9"><strong>Situação de Esgoto:</strong></div>
									</td>
									<td><html:text property="codigoSituacaoEsgoto"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="1" maxlength="2" />
										<html:text
										property="descricaoSituacaoEsgoto" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="25" maxlength="35" /></td>
									</tr>
									<tr>
									<td height="10" width="20%">
									<div class="style9"><strong>Rota:</strong></div>
									</td>
									<td><html:text property="numeroRota"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="5" maxlength="5" />
										</td>
									</tr>
									<tr>
									<td height="10" width="20%">
									<div class="style9"><strong>Número Segmento:</strong></div>
									</td>
									<td><html:text property="numeroSegmento"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="5" maxlength="5" />
										</td>
									</tr>
									<tr>
									<td height="10" width="20%">
									<div class="style9"><strong>Cliente:</strong></div>
									</td>
									<td><html:text property="nomeCliente"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="40" maxlength="40" />
										</td>
									</tr>
									<tr> 
			                              	<td class="style3">
			                              		<strong><span class="style2">Endereço:</span></strong>
			                              	</td>
		                                  
		                                  	<td>
												<html:textarea property="endereco" readonly="true"
												style="background-color:#EFEFEF; border:0;" cols="50"/>
		                                  	</td>
		                            	</tr>									
									<tr>
									<td height="20%" width="20%">
									<div class="style9"><strong>Telefones:</strong></div>
									</td>
									<td>
							<table width="40%" align="left" bgcolor="#90c7fc" border="0">
								<tr bordercolor="#79bbfd">
									<td>
									<div align="center"><strong>DDD</strong></div>
									</td>
									<td>
									<div align="center"><strong>Telefone</strong></div>
									</td>
									<td>
									<div align="center"><strong>Ramal</strong></div>
									</td>
									<logic:notEmpty  name="colecaoTelefones">
											<logic:iterate name="colecaoTelefones" id="clienteFone"
											scope="session">
											<pg:item>
												<%if (cor.equalsIgnoreCase("#cbe5fe")) {
													cor = "##FFFFFF";%>
												<tr bgcolor="#cbe5fe">
													<%} else {
													cor = "#cbe5fe";%>
												<tr bgcolor="#FFFFFF">
													<%}%>
													<td bordercolor="#90c7fc">
														<div align="center">
															<logic:present name="clienteFone"  property="ddd">
																<bean:write name="clienteFone" property="ddd" />
															</logic:present>
														</div>
													</td>
													<td bordercolor="#90c7fc">
														<div align="center">
															<logic:present name="clienteFone"  property="telefone">
																<bean:write name="clienteFone" property="dddTelefone" />
															</logic:present>
														</div>
													</td>
													<td bordercolor="#90c7fc">
														<div align="center">
															<logic:present name="clienteFone"  property="ramal">
																<bean:write name="clienteFone" property="ramal" />
															</logic:present>
														</div>
													</td>
													</pg:item>
													</logic:iterate>
								</logic:notEmpty>
								</tr>
							</table>
							</td>
							</td>
							<tr>
							<td height="20%" width="20%">
									<div class="style9"><strong>Categoria e Qtd de Economias:</strong></div>
									</td>
									<td>
										<table width="40%" align="left" bgcolor="#90c7fc" border="0">
								<tr bordercolor="#79bbfd">
									<td>
									<div align="center"><strong>Categoria</strong></div>
									</td>
									<td>
									<div align="center"><strong>Qtd Economia</strong></div>
									</td>
									<logic:notEmpty  name="colecaoImovelSubcategoria">
											<logic:iterate name="colecaoImovelSubcategoria" id="imovelSubcategoria"
											scope="session">
											<pg:item>
												<%if (cor.equalsIgnoreCase("#cbe5fe")) {
													cor = "##FFFFFF";%>
												<tr bgcolor="#cbe5fe">
													<%} else {
													cor = "#cbe5fe";%>
												<tr bgcolor="#FFFFFF">
													<%}%>
													<td bordercolor="#90c7fc">
														<div align="center">
															<logic:present name="imovelSubcategoria"  property="categoria">
																<bean:write name="imovelSubcategoria" property="categoria.descricao" />
															</logic:present>
														</div>
													</td>
													<td bordercolor="#90c7fc">
														<div align="center">
															<logic:present name="imovelSubcategoria"  property="quantidadeEconomias">
																<bean:write name="imovelSubcategoria" property="quantidadeEconomias" />
															</logic:present>
														</div>
													</td>
													</pg:item>
													</logic:iterate>
								</logic:notEmpty>
								</tr>
							</table>
							</td>
							<tr>
									<td height="20%" width="20%">
										<div class="style9"><strong>Contrato:</strong></div>
									</td>
									<td>
									<div align="left"><html:radio property="indicadorContrato"
											value="<%=ConstantesSistema.CONFIRMADA%>" disabled="false" />
										<strong>Sim<html:radio property="indicadorContrato"
											value="<%=ConstantesSistema.NAO_CONFIRMADA%>" disabled="false" />
										Não</strong></div>
									</td>
							</tr>
							<tr>
									<td height="20%" width="20%">
										<div class="style9"><strong>CPF:</strong></div>
									</td>
									<td>
									<div align="left"><html:radio property="indicadorCpf"
											value="<%=ConstantesSistema.CONFIRMADA%>" disabled="false" />
										<strong>Sim<html:radio property="indicadorCpf"
											value="<%=ConstantesSistema.NAO_CONFIRMADA%>" disabled="false" />
										Não</strong></div>
									</td>
							</tr>
							<tr>
									<td height="20%" width="20%">
										<div class="style9"><strong>RG:</strong></div>
									</td>
									<td>
									<div align="left"><html:radio property="indicadorRg"
											value="<%=ConstantesSistema.CONFIRMADA%>" disabled="false" />
										<strong>Sim<html:radio property="indicadorRg"
											value="<%=ConstantesSistema.NAO_CONFIRMADA%>" disabled="false" />
										Não</strong></div>
									</td>
							</tr>
							<tr>
									<td height="20%" width="20%">
										<div class="style9"><strong>Documentos do Imóvel:</strong></div>
									</td>
									<td>
									<div align="left"><html:radio property="indicadorDocImovel"
											value="<%=ConstantesSistema.CONFIRMADA%>" disabled="false" />
										<strong>Sim<html:radio property="indicadorDocImovel"
											value="<%=ConstantesSistema.NAO_CONFIRMADA%>" disabled="false" />
										Não</strong></div>
									</td>
							</tr>
							<tr>
									<td height="20%" width="20%">
										<div class="style9"><strong>Termo de Confissão dívida:</strong></div>
									</td>
									<td>
									<div align="left"><html:radio property="indicadorTemoConfissaoDivida"
											value="<%=ConstantesSistema.CONFIRMADA%>" disabled="false" />
										<strong>Sim<html:radio property="indicadorTemoConfissaoDivida"
											value="<%=ConstantesSistema.NAO_CONFIRMADA%>" disabled="false" />
										Não</strong></div>
									</td>
							</tr>
							<tr>
									<td height="20%" width="20%">
										<div class="style9"><strong>Correspondência:</strong></div>
									</td>
									<td>
									<div align="left"><html:radio property="indicadorCorrespondencia"
											value="<%=ConstantesSistema.CONFIRMADA%>" disabled="false" />
										<strong>Sim<html:radio property="indicadorCorrespondencia"
											value="<%=ConstantesSistema.NAO_CONFIRMADA%>" disabled="false" />
										Não</strong></div>
									</td>
							</tr>
							</table>
							</td>
						</tr>
			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
					<td><input name="Button" type="button" class="bottonRightCol"
						value="Desfazer" align="left"
						onclick="window.location.href='<html:rewrite page="/exibirConsultarImovelCheckListDocumentacaoAction.do?desfazer=S"/>'">
					<input name="Button" type="button" class="bottonRightCol"
						value="Cancelar" align="left"
						onclick="window.location.href='/gsan/telaPrincipal.do'"></td>
					<td align="right"><input name="Button" type="button"
						class="bottonRightCol" value="Atualizar" onclick="javascript:validaForm();" align="right"></td>
				</tr>
			</table>
			<p>&nbsp;</p>
		</tr>
	</table>
	<tr>
		<td colspan="3"><%@ include file="/jsp/util/rodape.jsp"%>
	</tr>
</html:form>
</body>
</html:html>

