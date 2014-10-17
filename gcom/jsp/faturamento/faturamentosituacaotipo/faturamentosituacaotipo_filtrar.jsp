<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<html:javascript staticJavascript="false"  formName="FiltrarFaturamentoSituacaoTipoActionForm" />

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script>
function validarForm(form){

	submeterFormPadrao(form);
}


</script>
</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">
<div id="formDiv">
<html:form action="/filtrarFaturamentoSituacaoTipoAction"   
	name="FiltrarFaturamentoSituacaoTipoActionForm"
  	type="org.apache.struts.action.DynaActionForm"
  	method="post"
  	focus="idCliente">

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
			<!--Início Tabela Reference a Páginação da Tela de Processo-->
            <table>
              <tr>
                <td></td>
              </tr>
            </table>
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="11"><img border="0" src="<bean:message key='caminho.imagens'/>parahead_left.gif"/></td>
                <td class="parabg">Filtrar Tipo de Situação do Faturamento </td>
                <td width="11" valign="top"><img border="0" src="<bean:message key='caminho.imagens'/>parahead_right.gif"/></td>
              </tr>
            </table>
			<p>&nbsp;</p>
			
            <table width="100%" border="0">
              <tr>
	           <td width="100%" colspan=2>
		          <table width="100%">
		          	<tr>
		          		<td>Para Filtrar o(s) tipo(s) de situação(ões) do faturamento, informe os dados abaixo: </td>
		          		<td width="74" align="right"><html:checkbox property="checkAtualizar" value="1"/><strong>Atualizar</strong></td>
		          	</tr>
		          </table>
	           </td>
	          </tr>
              <tr>
				<td width="162"><strong>Descrição:</strong></td>
				<td><strong> <html:text property="descricao" size="48" maxlength="70"></html:text> </strong></td>
			  </tr>
			  <tr>
				<td>&nbsp;</td>
				<td valign="top">
				  <html:radio property="tipoPesquisaDescricao" value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" /> Iniciando pelo texto
				  <html:radio property="tipoPesquisaDescricao" tabindex="5" value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" /> Contendo o texto
				</td>
			  </tr>
			</table>
			<table width="100%" border="0">				
				<tr>
					<td width="50%"><strong>Paralisação de Faturamento de Água?</strong></td>
					
					<td>
						<html:radio property="indicadorParalisacaoFaturamento" value="<%=ConstantesSistema.SIM.toString()%>" /><strong>Ativo</strong>
						<html:radio property="indicadorParalisacaoFaturamento" value="<%=ConstantesSistema.NAO.toString()%>" /><strong> Inativo</strong>
						<html:radio property="indicadorParalisacaoFaturamento"	value="<%=ConstantesSistema.TODOS.toString()%>" /><strong>Todos</strong>
					</td>
				</tr>	
				<tr>
					<td><strong>Paralisação de Faturamento de Esgoto?</strong></td>
					<td>
						<html:radio property="indicadorFaturamentoParalisacaoEsgoto" value="<%=ConstantesSistema.SIM.toString()%>" /><strong>Ativo</strong>
						<html:radio property="indicadorFaturamentoParalisacaoEsgoto" value="<%=ConstantesSistema.NAO.toString()%>" /><strong> Inativo</strong>
						<html:radio property="indicadorFaturamentoParalisacaoEsgoto"	value="<%=ConstantesSistema.TODOS.toString()%>" /><strong>Todos</strong>
					</td>
				</tr>			
				<tr>
					<td><strong>Paralisação da Leitura?</strong></td>
					<td>
						<html:radio property="indicadorParalisacaoLeitura" value="<%=ConstantesSistema.SIM.toString()%>" /><strong>Ativo</strong>
						<html:radio property="indicadorParalisacaoLeitura" value="<%=ConstantesSistema.NAO.toString()%>" /><strong> Inativo</strong>
						<html:radio property="indicadorParalisacaoLeitura"	value="<%=ConstantesSistema.TODOS.toString()%>" /><strong>Todos</strong>
					</td>	
				</tr>
				<tr>
					<td><strong>Consumo a Cobrar(Sem Leitura):</strong></td>
					<td align="left"><html:select
						property="leituraAnormalidadeConsumoSemLeitura" tabindex="38">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoLeituraAnormalidadeConsumo"
							labelProperty="descricaoConsumoACobrar" property="id" />
					</html:select></td>
				</tr>
				<tr>
					<td><strong>Consumo a Cobrar(Com Leitura):</strong></td>
					<td align="left"><html:select
						property="leituraAnormalidadeConsumoComLeitura" tabindex="38">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoLeituraAnormalidadeConsumo"
							labelProperty="descricaoConsumoACobrar" property="id" />
					</html:select></td>
				</tr>
				<tr>
					<td><strong>Leitura de Faturamento(Sem Leitura):</strong></td>
					<td align="left"><html:select
						property="leituraAnormalidadeLeituraSemLeitura" tabindex="40">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoLeituraAnormalidadeLeitura"
							labelProperty="descricaoFaturamento" property="id" />
					</html:select></td>
				</tr>
				<tr>
					<td><strong>Leitura de Faturamento(Com Leitura):</strong></td>
					<td align="left"><html:select
						property="leituraAnormalidadeLeituraComLeitura" tabindex="40">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoLeituraAnormalidadeLeitura"
							labelProperty="descricaoFaturamento" property="id" />
					</html:select></td>
				</tr>							
				<tr>
					<td><strong>Situação de Faturamento é Válida para Água?</strong></td>
					<td>
						<html:radio property="indicadorValidoAgua" value="<%=ConstantesSistema.SIM.toString()%>" /><strong>Ativo</strong>
						<html:radio property="indicadorValidoAgua" value="<%=ConstantesSistema.NAO.toString()%>" /><strong> Inativo</strong>
						<html:radio property="indicadorValidoAgua"	value="<%=ConstantesSistema.TODOS.toString()%>" /><strong>Todos</strong>
					</td>
				</tr>
				<tr>
					<td><strong>Situação de Faturamento é Válida para Esgoto?</strong></td>
					<td>
						<html:radio property="indicadorValidoEsgoto" value="<%=ConstantesSistema.SIM.toString()%>" /><strong>Ativo</strong>
						<html:radio property="indicadorValidoEsgoto" value="<%=ConstantesSistema.NAO.toString()%>" /><strong> Inativo</strong>
						<html:radio property="indicadorValidoEsgoto"	value="<%=ConstantesSistema.TODOS.toString()%>" /><strong>Todos</strong>
					</td>
				</tr>
				<tr>
					<td><strong>Percentual de Esgoto deve ser Informado?</strong></td>
					<td>
						<html:radio property="indicadorPercentualEsgoto" value="<%=ConstantesSistema.SIM.toString()%>" /><strong>Ativo</strong>
						<html:radio property="indicadorPercentualEsgoto" value="<%=ConstantesSistema.NAO.toString()%>" /><strong> Inativo</strong>
						<html:radio property="indicadorPercentualEsgoto"	value="<%=ConstantesSistema.TODOS.toString()%>" /><strong>Todos</strong>
					</td>
				</tr>
				<tr>
					<td><strong>Indicador de Uso:</strong></td>
					<td>
						<html:radio property="indicadorUso" value="<%=ConstantesSistema.SIM.toString()%>" /><strong>Ativo</strong>
						<html:radio property="indicadorUso" value="<%=ConstantesSistema.NAO.toString()%>" /><strong> Inativo</strong>
						<html:radio property="indicadorUso"	value="<%=ConstantesSistema.TODOS.toString()%>" /><strong>Todos</strong>
					</td>
				</tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>
					<input name="Button" type="button" class="bottonRightCol" value="Limpar" align="left"
					onclick="window.location.href='<html:rewrite page="/exibirFiltrarFaturamentoSituacaoTipoAction.do?limpar=S"/>'">
				</td>
				<td align="right"></td>
				<td align="right">
					<gcom:controleAcessoBotao name="Button" value="Filtrar"
											  onclick="javascript:validarForm(document.forms[0]);" 
											  url="filtrarFaturamentoSituacaoTipoAction.do" />
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