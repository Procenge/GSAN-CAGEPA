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

</head>

<body leftmargin="5" topmargin="5">
<html:form action="/filtrarContaMotivoRetificacaoAction"
	name="FiltrarContaMotivoRetificacaoActionForm"
	type="gcom.gui.faturamento.conta.contamotivo.FiltrarContaMotivoRetificacaoActionForm"
	method="post" >

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
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Filtrar Motivo de Retificação da Conta</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>
			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>

		   	<table width="100%" border="0">
		      	<tr>
		      		<td>Para filtrar um Motivo de Retificação da Conta, informe os dados abaixo:</td>
			        <td width="84">
						<input type="checkbox" name="indicadorAtualizar" value="1" /><strong>Atualizar</strong>
					</td>
					<td align="right"></td>
		    	</tr>
		   	</table>

			<table width="100%" border="0">
			   	<tr>
					<td><strong>Descri&ccedil;&atilde;o:</strong></td>
					<td><html:text property="descricaoMotivoRetificacaoConta" size="50" maxlength="50"/></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><html:radio property="tipoPesquisa" tabindex="5" 
						value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" />
					Iniciando pelo texto <html:radio property="tipoPesquisa"
						tabindex="6"
						value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" />
					Contendo o texto</td>
				</tr>

				<!-- Tipo de Serviço -->

				<tr>
					<td><strong>Indicador de Uso:</strong></td>
					<td> <html:radio property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>" /> Ativos
					<html:radio property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>" /> Inativos
					 <html:radio property="indicadorUso" value="<%=String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO) %>" /> Todos 
					</td>
				</tr>

				<tr>
					<td><strong> <font color="#FF0000"> 
					<input type="button" onClick="javascript:window.location.href='/gsan/exibirFiltrarContaMotivoRetificacaoAction.do?menu=sim'"
					 name="Submit22" class="bottonRightCol" value="Limpar" >
					</font> </strong></td>
					<td colspan="2" align="right">
					<input type="submit" name="Submit2" class="bottonRightCol" value="Filtrar"></td>
				</tr>
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

