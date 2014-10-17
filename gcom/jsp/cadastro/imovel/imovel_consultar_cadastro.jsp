<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gcom.cadastro.cliente.ClienteImovel"%>
<%@ page import="gcom.cadastro.imovel.ImovelSubcategoria"%>
<%@ page import="gcom.cadastro.imovel.ImovelConsumoFaixaAreaCategoria"%>

<head>
<html:html>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="ConsultarImovelActionForm" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
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

<!-- Begin
function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

   	var form = document.forms[0];

    if (tipoConsulta == 'imovel') {
      form.idImovelDadosCadastrais.value = codigoRegistro;
      form.matriculaImovelDadosCadastrais.value = descricaoRegistro;
      form.matriculaImovelDadosCadastrais.style.color = "#000000";
	  form.action = 'consultarImovelWizardAction.do?action=exibirConsultarImovelDadosCadastraisAction&indicadorNovo=OK'
	  form.submit();
    }
    
}

function abrirManterImovel() {
	var form = document.forms[0];
	
	if (form.idImovelDadosCadastrais.value == '') {
		alert('Informe a matr�cula do im�vel.');
	} else {
		abrirPopupDeNome('exibirAtualizarImovelAction.do?idRegistroAtualizacao=' + form.idImovelDadosCadastrais.value , 400, 800, 'AtualizarImovel', 'yes');		
	}

}

function abrirInserirCliente() {
	var form = document.forms[0];
	
	if (form.idImovelDadosCadastrais.value == '') {
		alert('Informe a matr�cula do im�vel.');
	} else {
		abrirPopupDeNome('exibirInserirClienteAction.do?menu=nao', 400, 800, 'InserirCliente', 'yes');		
	}

}

function limparForm(){
   	var form = document.forms[0];
	form.action = 'consultarImovelWizardAction.do?action=exibirConsultarImovelDadosCadastraisAction&limparForm=OK'
	form.submit();
}
-->
</script>
</head>

<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('idImovelDadosCadastrais')">
<html:form action="/exibirConsultarImovelAction.do"
	name="ConsultarImovelActionForm"
	type="gcom.gui.cadastro.imovel.ConsultarImovelActionForm" method="post"
	onsubmit="return validateConsultarImovelActionForm(this);">

	<jsp:include
		page="/jsp/util/wizard/navegacao_abas_wizard_consulta.jsp?numeroPagina=1" />

		<logic:present name="montarPopUp">
	 <table width="800" border="0" cellspacing="5" cellpadding="0">
		<tr>
		<td valign="top" class="centercoltext"><!--In�cio Tabela Reference a P�gina��o da Tela de Processo-->
			
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
			<!--Fim Tabela Reference a P�gina��o da Tela de Processo-->		
	
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
			
			<td valign="top" class="centercoltext"><!--In�cio Tabela Reference a P�gina��o da Tela de Processo-->
			
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
			<!--Fim Tabela Reference a P�gina��o da Tela de Processo-->
	
	</logic:notPresent>
			
			<!--Fim Tabela Reference a P�gina��o da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="4">
					<table width="100%" align="center" bgcolor="#99CCFF" border="0">
						<tr>
							<td align="center"><strong>Dados do Im�vel</strong></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">
							<table width="100%" border="0">
								<tr>
									<td bordercolor="#000000" width="25%"><strong>Im&oacute;vel:<font
										color="#FF0000">*</font></strong></td>
									<td width="75%" colspan="3"><html:text
										property="idImovelDadosCadastrais" maxlength="9" size="9"
										onkeypress="validaEnterComMensagem(event, 'consultarImovelWizardAction.do?action=exibirConsultarImovelDadosCadastraisAction&indicadorNovo=OK&limparForm=S','idImovelDadosCadastrais','Im&oacute;vel');"/> 
									<a
										href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
									<img width="23" height="21"
										src="<bean:message key="caminho.imagens"/>pesquisa.gif"
										border="0" /></a> <logic:present
										name="idImovelDadosCadastraisNaoEncontrado" scope="request">
										<html:text property="matriculaImovelDadosCadastrais" size="40"
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #ff0000" />

									</logic:present> <logic:notPresent
										name="idImovelDadosCadastraisNaoEncontrado" scope="request">
										<logic:present name="valorMatriculaImovelDadosCadastrais"
											scope="request">
											<html:text property="matriculaImovelDadosCadastrais"
												size="40" readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:present>
										<logic:notPresent name="valorMatriculaImovelDadosCadastrais"
											scope="request">
											<html:text property="matriculaImovelDadosCadastrais"
												size="40" readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:notPresent>
									</logic:notPresent> <a href="javascript:limparForm();"> <img
										src="<bean:message key="caminho.imagens"/>limparcampo.gif"
										border="0" title="Apagar" /></a></td>

								</tr>
								<tr>
									<td height="10">
									<div class="style9"><strong>Situa��o de �gua:</strong></div>
									</td>
									<td><html:text property="situacaoAguaDadosCadastrais"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="15" maxlength="15" /></td>
									<td width="90"><strong>Situa��o de Esgoto:</strong></td>
									<td width="120"><html:text
										property="situacaoEsgotoDadosCadastrais" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="15" maxlength="15" /></td>
								</tr>
								
								<logic:present name="mensagemAlteracaoInscricao" scope="request">
									<tr>
										<td height="10" colspan="4">
										<div class="style9"><font color="#FF0000"><%=request.getAttribute("mensagemAlteracaoInscricao")%></font></div>
										</td>
									</tr>
								</logic:present>
																
								<tr>									
									<td height="10">								 
										<div class="style9"><strong>Foto do im�vel:</strong></div>									
									</td>									
									<td>										
									<logic:equal name="ConsultarImovelActionForm" property="fotoFachada" value="">																					
											<input name="imageField" type="image" src="imagens/imgfolder.gif" width="18" height="18" border="0" disabled="disabled">																		
									</logic:equal>
									<logic:notEqual name="ConsultarImovelActionForm" property="fotoFachada" value="">
										<a href="javascript:abrirPopup('exibirFotoFachadaImovelAction.do?id=<%=request.getSession().getAttribute("idImovelPrincipalAba")%>', 600, 800)">
											<img name="imageField" src="imagens/imgfolder.gif" width="18" height="18" border="0"/>
										</a>	
									</logic:notEqual>
									</td>
									
								</tr>
								 <logic:notPresent name="montarPopUp">
								<tr>
									<td>
									  <a href="javascript:abrirManterImovel();">
											<strong>Manter Im�vel</strong>
									  </a>
									</td>
								</tr>	
								</logic:notPresent>						
							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>

				<tr>
					<td colspan="4">
					<table width="100%" align="center" bgcolor="#99CCFF" border="0">
						<tr>
							<td align="center">
							<div class="style9"><strong>Endere&ccedil;o </strong></div>

							</td>
						</tr>
						<tr bgcolor="#FFFFFF">
							<td align="center">
							<div class="style9">${enderecoImovelDadosCadastrais} &nbsp;</div>
							</td>
						</tr>
						<tr>
							<td align="center">
							<div class="style9"><strong>Op��o de Envio de Conta </strong></div>

							</td>
						</tr>
						<tr bgcolor="#FFFFFF">
							<td align="center">
							<div class="style9">${ConsultarImovelActionForm.descricaoOpcaoEnvioConta} &nbsp;</div>
							</td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td colspan="5">
					<table width="100%" border="0">
						<tr>
							<td colspan="5">
							<table width="100%" align="center" bgcolor="#90c7fc" border="0">
								<tr bordercolor="#79bbfd">
									<td colspan="5" align="center" bgcolor="#79bbfd"><strong>Clientes</strong></td>
								</tr>
								<tr bordercolor="#000000">
									<td width="28%" bgcolor="#90c7fc" align="center">
									<div class="style9"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Nome do
									Cliente</strong> </font></div>
									</td>
									<td width="17%" bgcolor="#90c7fc" align="center">
									<div class="style9"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Tipo da
									Rela&ccedil;&atilde;o</strong> </font></div>
									</td>
									<td width="19%" bgcolor="#90c7fc" align="center">
									<div class="style9"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Data
									In&iacute;cio Rela&ccedil;&atilde;o</strong></font></div>
									</td>
									<td width="16%" bgcolor="#90c7fc" align="center">
									<div class="style9"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Telefone</strong></font>
									</div>
									</td>
									<td width="20%" bgcolor="#90c7fc" align="center"><font
										color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"><strong>CPF/CNPJ</strong>
									</font></td>
								</tr>
								<tr>
									<td width="100%" colspan="5">
									<div style="width: 100%; height: 100%; overflow: auto;">
									<table width="100%" align="left" bgcolor="#99CCFF">
										<!--corpo da segunda tabela-->
										<%int cont = 0;%>
										<logic:notEmpty name="imovelClientes">
											<logic:iterate name="imovelClientes" id="imovelCliente"
												type="ClienteImovel">
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
										
										  <logic:notPresent name="montarPopUp">
										<tr>
										   <td>
											  <div align="left">
												<a href="javascript:abrirInserirCliente();">
													<strong>Inserir Cliente</strong>
						 						</a>
											  </div> 	
											</td>
										</tr>
										</logic:notPresent>
									</table>
									</div>
									</td>
								</tr>

							</table>
							</td>
						</tr>
						<%int quantidadeTotalEconomias = 0;

			%>

						<tr>
							<td colspan="5">
							<table width="100%" align="center" bgcolor="#90c7fc" border="0">
								<tr bordercolor="#79bbfd">
									<td colspan="5" align="center" bgcolor="#79bbfd"><strong>Categorias,
									Subcategorias e Economias</strong></td>
								</tr>
								<tr bordercolor="#000000">
									<td width="19%" bgcolor="#90c7fc" align="center">
									<div class="style9"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Categoria</strong>
									</font></div>
									</td>
									<td width="56%" bgcolor="#90c7fc" align="center">
									<div class="style9"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Subcategoria</strong></font>
									</div>
									</td>
									<td width="25%" bgcolor="#90c7fc" align="center">
									<div class="style9"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Quantidade
									de Economias</strong> </font></div>
									</td>
								</tr>

								<tr>
									<td width="100%" colspan="3">
									<div style="width: 100%; height: 100%; overflow: auto;">
									<table width="100%" align="left" bgcolor="#99CCFF">
										<!--corpo da segunda tabela-->
										<%cont = 0;%>
										<logic:notEmpty name="imovelSubcategorias">
											<logic:iterate name="imovelSubcategorias"
												id="imovelSubcategoria" type="ImovelSubcategoria">
												<%cont = cont + 1;
			if (cont % 2 == 0) {%>
												<tr bgcolor="#cbe5fe">
													<%} else {%>
												<tr bgcolor="#FFFFFF">
													<%}%>

													<td width="19%" align="left">
													<div align="center"><font color="#000000"
														style="font-size:9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
														name="imovelSubcategoria" property="comp_id">

														<bean:define name="imovelSubcategoria" property="comp_id"
															id="comp_id" />
														<logic:present name="comp_id" property="subcategoria">


															<bean:define name="comp_id" property="subcategoria"
																id="subcategoria" />

															<logic:present name="subcategoria" property="categoria">

																<bean:define name="subcategoria" property="categoria"
																	id="categoria" />
																<bean:write name="categoria" property="descricao" />
															</logic:present>
														</logic:present>
													</logic:present> </font></div>
													</td>
													<td width="56%" align="left">
													<div align="center"><font color="#000000"
														style="font-size:9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
														name="imovelSubcategoria" property="comp_id">

														<bean:define name="imovelSubcategoria" property="comp_id"
															id="comp_id" />
														<logic:present name="comp_id" property="subcategoria">
															<bean:define name="comp_id" property="subcategoria"
																id="subcategoria" />
															<bean:write name="subcategoria" property="descricao" />
														</logic:present>
													</logic:present> </font></div>
													</td>
													<td width="25%" align="right">
													<div align="right"><font color="#000000"
														style="font-size:9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
														name="imovelSubcategoria" property="quantidadeEconomias">
														<bean:write name="imovelSubcategoria"
															property="quantidadeEconomias" />
													</logic:present> </font></div>
													</td>


													<%quantidadeTotalEconomias = ((ImovelSubcategoria) imovelSubcategoria)
					.getQuantidadeEconomias()
					+ quantidadeTotalEconomias;

			%>


												</tr>
											</logic:iterate>
										</logic:notEmpty>
									</table>
									</div>
									</td>
								</tr>

								<tr bgcolor="#FFFFFF">
									<td width="19%" bgcolor="#90c7fc" align="center">
									<div class="style9" align="center"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Total de
									Economias</strong></font></div>
									</td>
									<td width="54%" align="left">&nbsp;</td>
									<td width="27%" align="right">
									<div align="right"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <%=quantidadeTotalEconomias%></font></div>
									</td>
								</tr>
							</table>
							</td>
						</tr>

						<logic:present name="indicadorImovelConsumoFaixaAreaCatg">
						<logic:equal name="indicadorImovelConsumoFaixaAreaCatg" value="1" scope="session">
						<tr>
							<td colspan="8">
							<div id="layerHideDadosConsumoFaixaArea" style="display: block">
							<table width="100%" border="0" bgcolor="#99CCFF">
								<tr bgcolor="#79bbfd">
									<td align="center"><span class="font-size:9px"> <a
										href="javascript:extendeTabela('DadosConsumoFaixaArea',true);" /><b>Consumo por Faixa de �rea e Categoria do Im�vel</b></a></span></td>
								</tr>
							</table>
							</div>

							<div id="layerShowDadosConsumoFaixaArea" style="display: none">
							<table width="100%" border="0" bgcolor="#99CCFF">
								<tr bgcolor="#79bbfd">
									<td align="center"><span class="font-size:9px"> <a
										href="javascript:extendeTabela('DadosConsumoFaixaArea',false);" /><b>Consumo por Faixa de �rea e Categoria do Im�vel</b></a></span></td>
								</tr>
								<tr>
									<td>
									<table width="100%" bgcolor="#99CCFF">
										<tr bordercolor="90c7fc">
											<td width="15%" align="center">
											<font color="#000000" style="font-size:9px"	face="Verdana, Arial, Helvetica, sans-serif">
											<strong>Categoria</strong>
											</font>
											</td>

											<td width="10%" align="center">
											<font color="#000000" style="font-size:9px"	face="Verdana, Arial, Helvetica, sans-serif">
											<strong>Faixa</strong>
											</font>
											</td>

											<td width="13%" align="center">
											<font color="#000000" style="font-size:9px"	face="Verdana, Arial, Helvetica, sans-serif">
											<strong>Comprimento da Frente</strong>
											</font>
											</td>
											
											<td width="13%" align="center">
											<font color="#000000" style="font-size:9px"	face="Verdana, Arial, Helvetica, sans-serif">
											<strong>Comprimento do Lado</strong>
											</font>
											</td>
											
											<td width="13%" align="center">
											<font color="#000000" style="font-size:9px"	face="Verdana, Arial, Helvetica, sans-serif">
											<strong>Comprimento da Testada</strong>
											</font>
											</td>

											<td width="12%" align="center">
											<font color="#000000" style="font-size:9px"	face="Verdana, Arial, Helvetica, sans-serif">
											<strong>N�mero de Andares</strong>
											</font>
											</td>

											<td width="12%" align="center">
											<font color="#000000" style="font-size:9px"	face="Verdana, Arial, Helvetica, sans-serif">
											<strong>Comprimento do Andar</strong>
											</font>
											</td>

											<td width="12%" align="center">
											<font color="#000000" style="font-size:9px"	face="Verdana, Arial, Helvetica, sans-serif">
											<strong>�rea Constru�da</strong>
											</font>
											</td>
										</tr>

										<%String cor = "#cbe5fe";%>	
										<logic:present name="colecaoImovelConsumoFaixaAreaCategoria" scope="session">
											<logic:iterate name="colecaoImovelConsumoFaixaAreaCategoria" id="imovelConsumoFaixaAreaCategoria" type="ImovelConsumoFaixaAreaCategoria">
												<%if (cor.equalsIgnoreCase("#cbe5fe")) {
												cor = "#FFFFFF";%>
												<tr bgcolor="#FFFFFF">
												<%} else {
												cor = "#cbe5fe";%>
												<tr bgcolor="#cbe5fe">
												<%}%>
													<td align="center">
													<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
													<bean:write name="imovelConsumoFaixaAreaCategoria" property="categoria.descricao"/>
													</font>
													</td>
													
													<td align="center">
													<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
													<bean:write name="imovelConsumoFaixaAreaCategoria" property="comp_id.consumoFaixaAreaCategoria.id"/>
													</font>
													</td>
													
													
													<td align="center">
													<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
													<bean:write name="imovelConsumoFaixaAreaCategoria" property="comprimentoFrente"/>
													</font>
													</td>

													<td align="center">
													<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
													<bean:write name="imovelConsumoFaixaAreaCategoria" property="comprimentoLado"/>
													</font>
													</td>
													
													<td align="center">
													<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
													<bean:write name="imovelConsumoFaixaAreaCategoria" property="comprimentoTestada"/>
													</font>
													</td>
													
													
													<td align="center">
													<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
													<bean:write name="imovelConsumoFaixaAreaCategoria" property="numeroAndares"/>
													</font>
													</td>
													
													<td align="center">
													<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
													<bean:write name="imovelConsumoFaixaAreaCategoria" property="comprimentoAndar"/>
													</font>
													</td>
													
													<td align="center">
													<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
													<bean:write name="imovelConsumoFaixaAreaCategoria" property="areaConstruida" />
													</font>
													</td>
												</tr>
											</logic:iterate>
										</logic:present>
									</table>
									</td>
								</tr>										
							</table>
							</div>
							</td>
						</tr>
						</logic:equal>
						</logic:present>

						<tr>
							<td width="28%" height="10" align="left">
							<div class="style9"><strong>Perfil do Im�vel:</strong></div>
							</td>
							<td width="22%" align="left">
							<div class="style9"><html:text
								property="imovelPerfilDadosCadastrais" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></div>
							</td>
							<td width="32%" align="left"><strong>Tipo de Despejo:</strong></td>
							<td width="18%" align="left"><html:text
								property="despejoDadosCadastrais" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></td>
						</tr>
						
						
						<tr>
							<td width="32%" align="left"><strong>Tipo de Esgotamento</strong></td>
							<td width="18%" align="left"><html:text
								property="esgotamento" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></td>
						
							<td align="left"><strong>&Aacute;rea Constru&iacute;da:</strong></td>
							<td align="left"><html:text
								property="areaConstruidaDadosDadosCadastrais" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></td>
							
						</tr>
						
						<tr>
							<td width="32%" align="left"><strong>Padr&atilde;o de Constru&ccedil;&atilde;o:</strong></td>
							<td width="18%" align="left"><html:text
								property="padraoConstrucao" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></td>
						
							<td align="left"><strong>Testada do Lote:</strong></td>
							<td align="left"><html:text property="testadaLoteDadosCadastrais"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></td>
							
						</tr>
						
						
						
						<tr>
							<td height="10">
							<div class="style9"><strong>Vol. Reservat&oacute;rio Inferior: </strong>
							</div>
							</td>
							<td><html:text
								property="volumeReservatorioInferiorDadosCadastrais"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></td>
							<td><strong>Vol. Reservat&oacute;rio Superior:</strong></td>
							<td><html:text
								property="volumeReservatorioSuperiorDadosCadastrais"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></td>
						</tr>
						<tr>
							<td height="10" align="left">
							<div class="style9"><strong>Volume da Piscina:</strong></div>
							</td>
							<td align="left">
							<div class="style9"><html:text
								property="volumePiscinaDadosCadastrais" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></div>
							</td>
							<td align="left"><strong>Fonte de Abastecimento:</strong></td>
							<td align="left"><html:text
								property="fonteAbastecimentoDadosCadastrais" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></td>
						</tr>
						<tr>
							<td height="10" align="left">
							<div class="style9"><strong>Po&ccedil;o:</strong></div>
							</td>
							<td align="left">
							<div class="style9"><html:text
								property="pocoTipoDadosCadastrais" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></div>
							</td>
							<td align="left"><strong>Distrito Operacional:</strong></td>
							<td align="left"><html:text
								property="distritoOperacionalDadosCadastrais" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></td>
						</tr>
						<tr>
							<td height="10" align="left">
							<div class="style9"><strong>Sistema de Abastecimento:</strong></div>
							</td>
							<td align="left">
							<div class="style9"><html:text
								property="sistemaAbastecimento" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></div>
							</td>
							<td align="left"><strong>Setor de Abastecimento:</strong></td>
							<td align="left"><html:text
								property="setorAbastecimento" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></td>
						</tr>
						<tr>
							<td height="10" align="left">
							<div class="style9"><strong>Bacia:</strong></div>
							</td>
							<td align="left">
							<div class="style9"><html:text
								property="bacia" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></div>
							</td>
							<td align="left"><strong>Sub-bacia:</strong></td>
							<td align="left"><html:text
								property="subBacia" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></td>
						</tr>
						<tr>
							<td height="21" align="left">
							<div class="style9"><strong>Pavimento de Rua:</strong></div>
							</td>
							<td align="left">
							<div class="style9"><html:text
								property="pavimentoRuaDadosCadastrais" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></div>
							</td>
							<td align="left"><strong>Pavimento de Cal&ccedil;ada:</strong></td>
							<td align="left"><html:text
								property="pavimentoCalcadaDadosCadastrais" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></td>
						</tr>
						<tr>
							<td height="10" align="left">
							<div class="style9"><strong>Pontos de Utiliza&ccedil;&atilde;o:</strong></div>
							</td>
							<td align="left">
							<div class="style9"><html:text
								property="numeroPontosUtilizacaoDadosCadastrais" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></div>
							</td>
							
							<td align="left"><strong>N&uacute;mero de Quartos:</strong></td>
							<td align="left"><html:text
								property="numeroQuarto" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></td>			
						</tr>
						
						<tr>
							<td height="10" align="left">
							<div class="style9"><strong>N&uacute;mero de Banheiros:</strong></div>
							</td>
							<td align="left">
							<div class="style9"><html:text
								property="numeroBanheiro" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></div>
							</td>
							
								<td align="left"><strong>N&uacute;mero de Moradores:</strong></td>
								<td align="left"><html:text
									property="numeroMoradoresDadosCadastrais" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000"
									size="20" maxlength="20"/>
								</td>	
								<td>
									<img width="25" height="25" border="0" onmouseover="this.T_BGCOLOR='whitesmoke';this.T_LEFT=true;return escape('N�mero de Adultos: <% if(request.getSession().getAttribute("numeroAdulto") != null){request.getSession().getAttribute("numeroAdulto");} %>	 <br>N�mero de Crian�as: <% if(request.getSession().getAttribute("numeroCrianca") != null){request.getSession().getAttribute("numeroCrianca");}  %>	<br>N�mero de Morador Trabalhador: <% if(request.getSession().getAttribute("numeroMoradorTrabalhador")!= null){request.getSession().getAttribute("numeroMoradorTrabalhador");} %>	<br>')" src="/gsan/imagens/informacao.gif"/>
								</td>
						</tr>						
						
						<tr>
							<td height="10" align="left">
							<div class="style9"><strong>Faixa Renda Familiar:</strong></div>
							</td>
							<td align="left">
							<div class="style9"><html:text
								property="rendaFamiliarFaixa" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></div>
							</td>						
						
							<td height="10" align="left">
							<div class="style9"><strong>N&uacute;mero do IPTU:</strong></div>
							</td>
							<td align="left">
							<div class="style9"><html:text
								property="numeroIptuDadosCadastrais" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></div>
							</td>						
							
						</tr>
						<tr>
							<td height="10" align="left">
							<div class="style9"><strong>Coordenada UTM X:</strong></div>
							</td>
							<td align="left">
							<div class="style9"><html:text
								property="coordenadaXDadosCadastrais" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></div>
							</td>
							<td align="left"><strong>Coordenada UTM Y:</strong></td>
							<td align="left"><html:text property="coordenadaYDadosCadastrais"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></td>
						</tr>
						<tr>
							<td height="10" align="left">
							<div class="style9"><strong>Ocorr&ecirc;ncia de Cadastro:</strong></div>
							</td>
							<td align="left">
							<div class="style9"><html:text
								property="cadastroOcorrenciaDadosCadastrais" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></div>
							</td>					
							<td align="left"><strong>Quadra do Im�vel:</strong></td>
							<td align="left"><html:text
								property="numeroQuadra" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="4" maxlength="5" /></td>
									
							
						</tr>
						<tr>
							<td align="left"><strong>Anormalidade de Elo:</strong></td>
							<td height="10" colspan="3" align="left"><html:text
								property="eloAnormalidadeDadosCadastrais" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></td>
						</tr>
						<tr>
							<td align="left"><strong>Im&oacute;vel Condom&iacute;nio:</strong></td>
							<td align="left"><html:text
								property="indicadorImovelCondominioDadosCadastrais"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></td>
							<td height="10" align="left">
							<div class="style9"><strong>Matr&iacute;cula Im&oacute;vel
							Condom&iacute;nio:</strong></div>
							</td>
							<td align="left">
							<div class="style9"><html:text
								property="imovelCondominioDadosCadastrais" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></div>
							</td>
						</tr>
						<tr>
							<td height="10" align="left">
							<div class="style9"><strong>Matr&iacute;cula Im&oacute;vel
							Principal:</strong></div>
							</td>
							<td align="left">
							<div class="style9"><html:text
								property="imovelPrincipalDadosCadastrais" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></div>
							</td>
							<td height="10" align="left">
							<div class="style9"><strong>Jardim:</strong></div>
							</td>
							<td align="left">
							<div class="style9"><html:text
								property="jardimDadosCadastrais" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></div>
							</td>
						</tr>
						
							<tr>
							<td height="10" align="left">
							<div class="style9"><strong>Contrato Comp. Energia:</strong></div>
							</td>
							<td align="left">
							<div class="style9"><html:text
								property="numeroCelpeDadosCadastrais" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></div>
							</td>
							<td align="left"><strong>Contrato Consumo:</strong></td>
							<td align="left"><html:text
								property="indicadorContratoConsumo" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="20" maxlength="20" /></td>
						</tr>
						<hr width="100%">
						<tr>
							
						</tr>
					</table>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">				
				<tr>	
					<td>
					<div align="right"><jsp:include
						page="/jsp/util/wizard/navegacao_botoes_wizard_consulta.jsp?numeroPagina=1" /></div>
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
<%@page import="java.util.Enumeration"%>
<!-- imovel_consultar_cadastro.jsp -->
</html:html>
