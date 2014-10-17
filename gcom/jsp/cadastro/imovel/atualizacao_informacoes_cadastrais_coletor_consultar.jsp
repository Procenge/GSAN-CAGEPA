<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.util.Util"%>

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

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

<body leftmargin="5" topmargin="5">

<html:form action="/exibirConsultarAtualizacaoCadastralColetorDadosAction"
	name="ConsultarAtualizacaoCadastralColetorDadosActionForm"
	type="gcom.gui.cadastro.imovel.ConsultarAtualizacaoCadastralColetorDadosActionForm"
	method="post">
	

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="130" valign="top" class="leftcoltext">
			<div align="center"><%@ include
				file="/jsp/util/informacoes_usuario.jsp"%> <%@ include
				file="/jsp/util/mensagens.jsp"%></div>
			</td>

			<td width="625" valign="top" class="centercoltext">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Consulta de Atualização Cadastral via Coletor de Dados</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>

			<table width="100%" border="0">
				
				<!-- 1 Dados do Imóvel -->
				<tr>
					<td colspan="4">
					<div id="layerHideDadosImovel" style="display: none">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosImovel',true);"><b>Dados
							do Imóvel</b></a></span></td>
						</tr>
					</table>
					</div>

					<div id="layerShowDadosImovel" style="display: block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosImovel',false);"><b>Dados
							do Imóvel</b></a></span></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td>
							<table width="100%">
								<tr>
									<td width="25%"><strong>Inscrição:</strong></td>
									<td><html:text property="inscricao"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="20" /></td>
									
								</tr>
								<tr>
									<td><strong>Nome do Cliente:</strong></td>
									<td><html:text property="nomeCliente"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="20" /></td>
									<td><strong>CPF/CNPJ:</strong></td>
									<td><html:text property="cpfCnpj"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="15" /></td>
								</tr>
								<tr>
									<td><strong>Situação de Água:</strong></td>
									<td><html:text property="dsLigacaoAguaSituacao"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="15" /></td>
									<td><strong>Situação de Esgoto:</strong></td>
									<td><html:text property="dsLigacaoEsgotoSituacao"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="15" /></td>
								</tr>
								<!-- 1.4.6 Endereço do Imóvel -->
								<tr>
									<td colspan="4">
									<table width="100%" align="center" bgcolor="#99CCFF" border="0">
										<tr>
											<td align="center">
											<div class="style9"><strong>Endereço do Imóvel</strong></div>
											</td>
										</tr>
										<tr bgcolor="#FFFFFF">
											<td align="center">
											<div class="style9">${ConsultarAtualizacaoCadastralColetorDadosActionForm.endereco}
											&nbsp;</div>
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
				<!-- 2 Dados da Atualizacao -->
				<tr>
					<td colspan="4">
					<div id="layerHideDadosAtualizacao" style="display: none">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosAtualizacao',true);"><b>Dados
							da Atualização</b></a></span></td>
						</tr>
					</table>
					</div>

					<div id="layerShowDadosAtualizacao" style="display: block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosAtualizacao',false);"><b>Dados
							da Atualização</b></a></span></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td>
							<table width="100%">
								<tr>
									<td width="25%"><strong>Leiturista:</strong></td>
									<td colspan="3"><html:text property="funcionario"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="61" /></td>
									
								</tr>
								<tr>
									<td><strong>Data/Hora da Leitura:</strong></td>
									<td><html:text property="dataLeitura"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="20" /></td>
									<td><strong>Referência:</strong></td>
									<td><html:text property="anoMesMovimento"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="15" /></td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</div>
					</td>
				</tr>
				<!-- 3 Dados Cadastrais Atualizados -->
				<tr>
					<td colspan="4">
					<div id="layerHideDadosAtualizados" style="display: none">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosAtualizados',true);"><b>Dados
							Cadastrais Atualizados</b></a></span></td>
						</tr>
					</table>
					</div>

					<div id="layerShowDadosAtualizados" style="display: block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosAtualizados',false);"><b>Dados
							Cadastrais Atualizados</b></a></span></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td>
							<table width="100%">
								<tr bordercolor="#000000">
								
									<td width="35%" bordercolor="#000000" bgcolor="#90c7fc"
										align="center">
										<div align="center"><strong>Informação</strong></div>
									</td>
									<td width="12%" bordercolor="#90c7fc" bgcolor="#90c7fc"
										align="center">
										<div align="center"><strong>Atual</strong></div>
									</td>
									<td width="12%" bordercolor="#90c7fc" bgcolor="#90c7fc"
										align="center">
										<div align="center"><strong>Atualizado</strong></div>
									</td>
								</tr>
								<tr bgcolor="#FFFFFF">
									<td><strong>Número do Imovel</strong></td>
									<td align="center"><html:text property="numeroImovel"
										readonly="true" style="background-color:transparent; border:0;"
										size="15" /></td>
									<td align="center"><html:text property="numeroImovelAlteracao"
										readonly="true" style="background-color:transparent; border:0;"
										size="15" /></td>
								</tr>
								<tr>
									<td><strong>Complemento do Endereço</strong></td>
									<td align="center"><html:text property="dsComplementoEndereco"
										readonly="true" style="background-color:transparent; border:0;"
										size="15" /></td>
									<td align="center"><html:text property="dsComplementoEnderecoAlteracao"
										readonly="true" style="background-color:transparent; border:0;"
										size="15" /></td>
								</tr>
								<tr bgcolor="#FFFFFF">
									<td><strong>Número do Hidrômetro</strong></td>
									<td align="center"><html:text property="numeroHidrometro"
										readonly="true" style="background-color:transparent; border:0;"
										size="15" /></td>
									<td align="center"><html:text property="numeroHidrometroAlteracao"
										readonly="true" style="background-color:transparent; border:0;"
										size="15" /></td>
								</tr>
								<tr>
									<td><strong>Quant. de Economias Residencial </strong></td>
									<td align="center"><html:text property="qntEconomiaResidencial"
										readonly="true" style="background-color:transparent; border:0;"
										size="15" /></td>
									<td align="center"><html:text property="qntEconomiaResidencialAlteracao"
										readonly="true" style="background-color:transparent; border:0;"
										size="15" /></td>
								</tr>
								<tr bgcolor="#FFFFFF">
									<td><strong>Quant. de Economias Comercial </strong></td>
									<td align="center"><html:text property="qntEconomiaComercial"
										readonly="true" style="background-color:transparent; border:0;"
										size="15" /></td>
									<td align="center"><html:text property="qntEconomiaComercialAlteracao"
										readonly="true" style="background-color:transparent; border:0;"
										size="15" /></td>
								</tr>
								<tr>
									<td><strong>Quant. de Economias Industrial </strong></td>
									<td align="center"><html:text property="qntEconomiaIndustrial"
										readonly="true" style="background-color:transparent; border:0;"
										size="15" /></td>
									<td align="center"><html:text property="qntEconomiaIndustrialAlteracao"
										readonly="true" style="background-color:transparent; border:0;"
										size="15" /></td>
								</tr>
								<tr bgcolor="#FFFFFF">
									<td><strong>Quant. de Economias Pública </strong></td>
									<td align="center"><html:text property="qntEconomiaPublica"
										readonly="true" style="background-color:transparent; border:0;"
										size="15" /></td>
									<td align="center"><html:text property="qntEconomiaPublicaAlteracao"
										readonly="true" style="background-color:transparent; border:0;"
										size="15" /></td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</div>
					</td>
				</tr>
			</table>
			<table>
				<tr>
					<td>
						<input name="button" type="button" class="bottonRightCol" value="Cancelar" onclick="window.location.href='<html:rewrite page="/telaPrincipal.do"/>'" align="left" style="width: 80px;">
					</td>
					<td>
						<input name="button" type="button" class="bottonRightCol" value="Voltar" onclick="javascript:history.back(1)" align="left" style="width: 80px;">
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>

</body>
</html:html>
