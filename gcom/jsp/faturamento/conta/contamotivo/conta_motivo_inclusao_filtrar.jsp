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
	formName="FiltrarContaMotivoInclusaoActionForm"
	dynamicJavascript="true" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">

function validaForm(form){
  		submeterFormPadrao(form);
}

function limparForm(form) {
		form.descricao.value = "";
}

function verificarChecado(valor){
	form = document.forms[0];
	if(valor == "1"){
	 	form.indicadorAtualizar.checked = true;
	 }else{
	 	form.indicadorAtualizar.checked = false;
	}
}
</script>


</head>

<body leftmargin="5" topmargin="5"
	onload="verificarChecado('${sessionScope.indicadorAtualizar}');">
<html:form action="/filtrarContaMotivoInclusaoAction"
	name="FiltrarContaMotivoInclusaoActionForm"
	type="gcom.gui.faturamento.conta.contamotivo.FiltrarContaMotivoInclusaoActionForm"
	method="post">

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
					<td class="parabg">Filtrar Motivo de Inclusão de Conta</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>

			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
			
			
			
							<tr>
					<td width="100%" colspan=2>
					<table width="100%" border="0">
						<tr>
							<td>Para filtrar um motivo de inclusão de conta, informe os dados abaixo:</td>
							<td align="right"><input type="checkbox"
								name="indicadorAtualizar" value="1" /><strong>Atualizar</strong>
							
							</td>
							<td align="right"></td>
						</tr>
					</table>
					</td>
				</tr>
	
					<td><strong>Descri&ccedil;&atilde;o:</strong></td>
					<td colspan="2"><span class="style2"> <html:text
						property="descricaoMotivoInclusaoConta" size="50" maxlength="50" /> </span></td>

				<td></td>

                <td>
				<tr>
					<td>&nbsp;</td>
					<td><html:radio property="tipoPesquisa" tabindex="5"
						value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" />
					Iniciando pelo texto <html:radio property="tipoPesquisa"
						tabindex="6"
						value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" />
					Contendo o texto</td>
				</tr>
		


				

								
				<tr>
					<td><strong>Indicador de Uso:</strong></td>
					<td><strong> <html:radio property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>" /> <strong>Ativos
					<html:radio property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>" /> Inativos</strong>
					<strong> <html:radio property="indicadorUso" value="<%=String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO) %>" /> Todos </strong>
					</td>

				</tr>
				



				<tr>
					<td><strong> <font color="#FF0000"> <input type="button"
						name="Submit22" class="bottonRightCol" value="Limpar"
						onClick="javascript:window.location.href='/gsan/exibirFiltrarContaMotivoInclusaoAction.do?menu=sim'"><!-- <input type="button"
								name="Submit23" class="bottonRightCol" value="Cancelar"
								onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"> -->
					</font> </strong></td>
					<td colspan="2" align="right"><input type="button" name="Submit2"
						class="bottonRightCol" value="Filtrar"
						onclick="validaForm(document.forms[0]);"></td>
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

