<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.util.Util" %>
<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
 <%@ include file="/jsp/util/titulo.jsp"%>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

 <html:javascript staticJavascript="false"  formName="InserirFaturamentoSituacaoTipoActionForm" />

 <script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
 <script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
 <script>
	
 function validaForm() {
	  	var form = document.forms[0];
	  	var formValidacao = document.forms[0];
	 	if(validateInserirFaturamentoSituacaoTipoActionForm(form)){
			submeterFormPadrao(form);
	 	}
	}
 

 </script>
</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">
<div id="formDiv">
<html:form 	action="/inserirFaturamentoSituacaoTipoAction" enctype="multipart/form-data"
			method="post" focus="idCliente">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<input type="hidden" name="tipoPesquisa" />

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
			<td width="655" valign="top" class="centercoltext"><!--Início Tabela Reference a Páginação da Tela de Processo-->

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key='caminho.imagens'/>parahead_left.gif" /></td>
					<td class="parabg">Inserir Tipo Situacao do Faturamento</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key='caminho.imagens'/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
			<tr>
				<td colspan="2">Para adicionar um faturamento situacao tipo, informe os dados
				abaixo:</td>
			</tr>					
			<tr>
					<td width="162"><strong>Descrição:<font color="#FF0000">*</font></strong></td>
					<td><strong> <html:text property="descricao" size="48" maxlength="70"></html:text> </strong></td>
				</tr>
			</table>
			<table width="100%" border="0">				
				<tr>
					<td width="50%"><strong>Paralisação de Faturamento de Água?<font color="#FF0000">*</font></strong></td>
					
					<td><strong> <html:radio property="indicadorParalisacaoFaturamento" value="1" />
					<strong>Ativo <html:radio property="indicadorParalisacaoFaturamento" value="2" />
					Inativo</strong> </strong></td>
				</tr>	
				<tr>
					<td><strong>Paralisação de Faturamento de Esgoto?<font color="#FF0000">*</font></strong></td>
					<td><strong> <html:radio property="indicadorFaturamentoParalisacaoEsgoto" value="1" />
					<strong>Ativo <html:radio property="indicadorFaturamentoParalisacaoEsgoto" value="2" />
					Inativo</strong> </strong></td>
				</tr>			
				<tr>
					<td><strong>Paralisação da Leitura?<font color="#FF0000">*</font></strong></td>
					<td><strong> <html:radio property="indicadorParalisacaoLeitura" value="1" />
					<strong>Ativo <html:radio property="indicadorParalisacaoLeitura" value="2" />
					Inativo</strong> </strong></td>
				</tr>
				<tr>
					<td><strong>Consumo a Cobrar(Sem Leitura):<font color="#FF0000">*</font></strong></td>
					<td align="left"><html:select
						property="leituraAnormalidadeConsumoSemLeitura" tabindex="38">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoLeituraAnormalidadeConsumo"
							labelProperty="descricaoConsumoACobrar" property="id" />
					</html:select></td>
				</tr>
				<tr>
					<td><strong>Consumo a Cobrar(Com Leitura):<font color="#FF0000">*</font></strong></td>
					<td align="left"><html:select
						property="leituraAnormalidadeConsumoComLeitura" tabindex="38">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoLeituraAnormalidadeConsumo"
							labelProperty="descricaoConsumoACobrar" property="id" />
					</html:select></td>
				</tr>
				<tr>
					<td><strong>Leitura de Faturamento(Sem Leitura):<font color="#FF0000">*</font></strong></td>
					<td align="left"><html:select
						property="leituraAnormalidadeLeituraSemLeitura" tabindex="40">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoLeituraAnormalidadeLeitura"
							labelProperty="descricaoFaturamento" property="id" />
					</html:select></td>
				</tr>
				<tr>
					<td><strong>Leitura de Faturamento(Com Leitura):<font color="#FF0000">*</font></strong></td>
					<td align="left"><html:select
						property="leituraAnormalidadeLeituraComLeitura" tabindex="40">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoLeituraAnormalidadeLeitura"
							labelProperty="descricaoFaturamento" property="id" />
					</html:select></td>
				</tr>							
				<tr>
					<td><strong>Situação de Faturamento é Válida para Água?<font color="#FF0000">*</font></strong></td>
					<td><strong> <html:radio property="indicadorValidoAgua" value="1" />
					<strong>Ativo <html:radio property="indicadorValidoAgua" value="2" />
					Inativo</strong> </strong></td>
				</tr>
				<tr>
					<td><strong>Situação de Faturamento é Válida para Esgoto?<font color="#FF0000">*</font></strong></td>
					<td><strong> <html:radio property="indicadorValidoEsgoto" value="1" />
					<strong>Ativo <html:radio property="indicadorValidoEsgoto" value="2" />
					Inativo</strong> </strong></td>
				</tr>
				<tr>
					<td><strong>Percentual de Esgoto deve ser Informado?<font color="#FF0000">*</font></strong></td>
					<td><strong> <html:radio property="indicadorPercentualEsgoto" value="1" />
					<strong>Ativo <html:radio property="indicadorPercentualEsgoto" value="2" />
					Inativo</strong> </strong></td>
				</tr>
				<tr>
					<td><strong> <font color="#FF0000"> <input
						name="btLimpar" class="bottonRightCol" value="Limpar"
						type="button"
						onclick="window.location.href='/gsan/exibirInserirFaturamentoSituacaoTipoAction.do?menu=sim';">
					<input type="button" name="Button" class="bottonRightCol"
						value="Cancelar" tabindex="6"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
						style="width: 80px" /> </font></strong></td>
					<td align="right"><input type="button" onclick="javascript:validaForm();" name="botaoInserir"
						class="bottonRightCol" value="Inserir"></td>
				</tr>
				
			</table>
			<p>&nbsp;</p>
		</tr>
	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</div>
</body>

<%@ include file="/jsp/util/telaespera.jsp"%>

</html:html>