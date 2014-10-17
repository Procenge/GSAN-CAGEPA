<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>




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
	formName="ConsultarArquivoTextoLeituraActionForm"
	dynamicJavascript="true" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">

function validaForm(form){

	if(testarCampoValorZero(document.ConsultarArquivoTextoLeituraActionForm.empresaID, 'Empresa') &&
	testarCampoValorZero(document.ConsultarArquivoTextoLeituraActionForm.grupoFaturamentoID, 'Grupo de Faturamento') && 
	testarCampoValorZero(document.ConsultarArquivoTextoLeituraActionForm.mesAno, 'Mês de Referência')) {


		if(validateConsultarArquivoTextoLeituraActionForm(form)){
			form.action = 'consultarArquivoTextoLeituraAction.do'  ;
    		submeterFormPadrao(form);
		}

     }
  }
  
  function fuahihfa(form) {
  	form.action = 'liberarArquivoTextoLeituraAction.do';
  	form.submit();
  
  }
      function limparForm(form) {
		form.empresaID.value = "";
		form.grupoFaturamentoID.value = "";
		form.mesAno.value = "";
		
	}

	  function facilitador(objeto){
	if (objeto.id == "0" || objeto.id == undefined){
		objeto.id = "1";
		marcarTodos();
	}
	else{
		objeto.id = "0";
		desmarcarTodos();
	}
}




</script>


</head>

<body leftmargin="5" topmargin="5">
<html:form action="/exibirConsultarArquivoTextoLeituraAction"
	name="ConsultarArquivoTextoLeituraActionForm"
	type="gcom.gui.micromedicao.ConsultarArquivoTextoLeituraActionForm"
	method="post"
	onsubmit="return validateConsultarArquivoTextoLeituraActionForm(this);">

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


			<!--Início Tabela Reference a Páginação da Tela de Processo-->
			<table>
				<tr>
					<td></td>

				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Filtrar Arquivos Texto para Leitura</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>

			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td height="10" colspan="3">Para consultar os arquivos textos para
					leitura, informe os dados abaixo:</td>
					<td align="right"></td>
				</tr>
				<tr>
					<td width="150"><strong>Empresa:<font
						color="#FF0000">*</font></strong></td>
					<td colspan="2" align="left"><html:select property="empresaID"
						tabindex="1">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoEmpresa"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>

				<tr>
					<td width="150"><strong>Grupo de Faturamento:<font
						color="#FF0000">*</font></strong></td>
					<td colspan="2" align="left"><html:select
						property="grupoFaturamentoID" tabindex="1">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoFaturamentoGrupo"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>

				<tr>

					<td height="10" width="145"><strong>Mês de Referência :<font
						color="#FF0000">*</font></strong></td>
					<td><html:text property="mesAno" size="8" maxlength="7"
						tabindex="4" onkeyup="mascaraAnoMes(this, event);" /></td>

				</tr>
				
				<tr>
					<td><strong>Situação Texto para Leitura:</strong></td>
					<td><strong> <html:radio property="situaTransmLeitura"
						value="1" /> <strong>Disponível<html:radio
						property="situaTransmLeitura" value="2" />Liberado</strong>
						<strong><html:radio
						property="situaTransmLeitura" value="3" />Em Campo</strong>
						<strong><html:radio
						property="situaTransmLeitura" value="4" />Finalizado</strong>
						<strong><html:radio
						property="situaTransmLeitura" value="" />Todos</strong>
						
					</td>

				</tr>

				<tr>
					<td></td>
				</tr>
				<tr>
					<td><input name="Button" type="button" class="bottonRightCol"
						value="Desfazer" align="left"
						onclick="window.location.href='<html:rewrite page="/exibirConsultarArquivoTextoLeituraAction.do?menu=sim"/>'">
					<input type="button" name="ButtonCancelar" class="bottonRightCol"
						value="Cancelar"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"></td>
					<td align="right"><%--<INPUT type="button" class="bottonRightCol" value="Inserir" tabindex="13" style="width: 70px;" onclick="validarForm(document.forms[0]);">--%>
					<%-- 
		  	A taglib vai substituir o botão, as propriedades requeridas da tag são :
		  	1-name -> O nome do botão.
		  	2-value -> A descrição do botão. 
		  	3-onclick -> a função javascript que vai ser chamada no click do botão.
		  	4-url -> a url doaction da operação para verificar se o usário logado tem acesso a operação.
		  	
		  	As propriedades NÃO requeridas da tag são:
		  	1-tabindex -> mesma função do input
		  	2-align -> mesma função do input
		  --%> <gcom:controleAcessoBotao name="Botao" value="Selecionar"
						onclick="validaForm(document.forms[0]);"
						url="consultarArquivoTextoLeituraAction.do" tabindex="13" /></td>
				</tr>

				<tr>
					<td colspan="5" height="23"><font color="#000000"
						style="font-size:10px"
						face="Verdana, Arial, Helvetica, sans-serif"><strong>Arquivos Textos para Leitura:</strong></font>
				
				
				
				<gcom:controleAcessoBotao name="Botao" value="Não Liberar"
						onclick="fuahihfa(document.forms[0]);"
						url="liberarArquivoTextoLeituraAction.do" tabindex="13" />
				</td>
                </tr>

				<tr>
					<!--<td colspan="4" bgcolor="#3399FF"> -->
					<td colspan="5" bgcolor="#000000" height="2" valign="baseline"></td>
				</tr>

				
				<table width="100%" align="center" bgcolor="#90c7fc" border="0"
					cellpadding="0" cellspacing="0">
					<tr bgcolor="#cbe5fe">
						<td width="100%" align="center">
						<table width="100%" bgcolor="#99CCFF">
							<tr bordercolor="#000000" bgcolor="#90c7fc">
								<td width="7%" bgcolor="#90c7fc">
								<div align="center"><strong><a
									href="javascript:facilitador(this);">Todos</a></strong></div>
								</td>
								<td width="20%" bgcolor="#90c7fc">
								<div align="center"><strong>Arquivo</strong></div>
								</td>

								<td width="16%" bgcolor="#90c7fc">
								<div align="center"><strong>Quantidade</strong></div>
								</td>
								<td width="11%" bgcolor="#90c7fc">
								<div align="center"><strong>Elo</strong></div>
								</td>
								<td width="20%" bgcolor="#90c7fc">
								<div align="center"><strong>Fone Agente Comercial</strong></div>
								</td>
								<td width="20%" bgcolor="#90c7fc">
								<div align="center"><strong>Data de Liberação</strong></div>
								</td>

							</tr>
							<!-- <pg:pager isOffset="true" index="half-full" maxIndexPages="10"
									export="currentPageNumber=pageNumber;pageOffset"
									maxPageItems="10" items="${sessionScope.totalRegistros}">
									<pg:param name="pg" />
									<pg:param name="q" />  -->
							<logic:present name="colecaoArquivoTextoRoteiroEmpresa">
								<%int cont = 0;%>
								<logic:iterate name="colecaoArquivoTextoRoteiroEmpresa"
									id="arquivoTextoRoteiroEmpresa">
									<!-- <pg:item>  -->
									<%cont = cont + 1;
							if (cont % 2 == 0) {%>
									<tr bgcolor="#cbe5fe">
										<%} else {%>
									<tr bgcolor="#FFFFFF">
										<%}%>
										<td width="7%">
										<div align="center"><input type="checkbox"
											name="idRegistrosRemocao"
											value="${arquivoTextoRoteiroEmpresa.id}"></div>
										</td>

										<td width="18%" align="center"><html:link
											page="/retornarArquivoTxtLeituraAction.do"
											paramName="arquivoTextoRoteiroEmpresa" paramProperty="id"
											paramId="idRegistroAtualizacao">${arquivoTextoRoteiroEmpresa.numeroArquivo}
													</html:link></td>
										<td width="15%" align="center">${arquivoTextoRoteiroEmpresa.quantidadeImovel}</td>
										<td width="15%">
										<div align="center">${arquivoTextoRoteiroEmpresa.localidade.localidade.id}</div>
										</td>
										<td width="15%">
										<div align="center">${arquivoTextoRoteiroEmpresa.numeroFoneLeiturista}</div>
										</td>
										<td width="27%">
										<div align="center">${arquivoTextoRoteiroEmpresa.ultimaAlteracao}</div>
										</td>

										<html:link
											href="/gsan/liberarArquivoTextoLeitura.do"
											paramId="codigo" paramProperty="id" paramName="arquivoTextoRoteiroEmpresa">


										</html:link>

									</tr>
									<!-- </pg:item> -->
								</logic:iterate>
							</logic:present>
						</table>
						</td>
					</tr>
				</table>
			</table>
			<p>&nbsp;</p>
		</tr>
		<!-- Rodapé -->
		<%@ include file="/jsp/util/rodape.jsp"%>
	</table>
	<p>&nbsp;</p>

	<tr>

	</tr>

</html:form>
</body>
</html:html>

