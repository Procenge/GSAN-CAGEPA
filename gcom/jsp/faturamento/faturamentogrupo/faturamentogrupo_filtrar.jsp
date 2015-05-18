<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<%@ page import="gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<html:javascript staticJavascript="false"
	formName="FiltrarFaturamentoGrupoActionForm" />

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script>
	function validarForm(form) {
		if(validateFiltrarFaturamentoGrupoActionForm(form)){
			if(form.diaVencimento.value != ""){
		  		if(form.diaVencimento.value < 1 || form.diaVencimento.value > 31){
		  			alert("O dia do vencimento deve ser entre 1 e 31");
		  			return;
		  		}
		  	}
	 		
	 		if(form.diaVencimentoDebitoAutomatico.value != ""){
		  		if(form.diaVencimentoDebitoAutomatico.value < 1 || form.diaVencimentoDebitoAutomatico.value > 31){
		  			alert("O dia do vencimento de débito automático deve ser entre 1 e 31");
		  			return;
		  		}
		  	}
	 		
	 		if(form.diaVencimentoEntregaAlternativa.value != ""){
		  		if(form.diaVencimentoEntregaAlternativa.value < 1 || form.diaVencimentoEntregaAlternativa.value > 31){
		  			alert("O dia do vencimento da entrega alternativa deve ser entre 1 e 31");
		  			return;
		  		}
		  	}	
			
			submeterFormPadrao(form);
		}
	}
</script>
</head>

<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}');">
	<div id="formDiv">
		<html:form action="/filtrarFaturamentoGrupoAction"
			name="FiltrarFaturamentoGrupoActionForm"
			type="org.apache.struts.action.DynaActionForm" method="post"
			focus="idCliente">

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
					<td width="655" valign="top" class="centercoltext">
						<!--Início Tabela Reference a Páginação da Tela de Processo-->
						<table>
							<tr>
								<td></td>
							</tr>
						</table>
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td width="11"><img border="0"
									src="<bean:message key='caminho.imagens'/>parahead_left.gif" /></td>
								<td class="parabg">Filtrar Grupo de Faturamento</td>
								<td width="11" valign="top"><img border="0"
									src="<bean:message key='caminho.imagens'/>parahead_right.gif" /></td>
							</tr>
						</table>
						<p>&nbsp;</p>

						<table width="100%" border="0">
							<tr>
								<td width="100%" colspan=2>
									<table width="100%">
										<tr>
											<td>Para Filtrar o(s) grupo(s) de faturamento, informe
												os dados abaixo:</td>
											<td width="74" align="right"><html:checkbox
													property="checkAtualizar" value="1" /><strong>Atualizar</strong></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td><strong>Código:</strong></td>
								<td><strong> <html:text
											property="idFaturamentoGrupo" size="5" maxlength="5"></html:text>
								</strong></td>
							</tr>
							<tr>
								<td width="162"><strong>Descrição:</strong></td>
								<td><strong> <html:text property="descricao"
											size="48" maxlength="70"></html:text>
								</strong></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td valign="top"><html:radio
										property="tipoPesquisaDescricao"
										value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL
							.toString()%>" />
									Iniciando pelo texto <html:radio
										property="tipoPesquisaDescricao" tabindex="5"
										value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA
							.toString()%>" />
									Contendo o texto</td>
							</tr>

							<tr>
								<td width="162"><strong>Descrição Abreviada:</strong></td>
								<td><strong> <html:text
											property="descricaoAbreviada" size="3" maxlength="3"></html:text>
								</strong></td>
							</tr>
							<tr>
								<td width="30%"><strong>Mês/Ano Referência:</strong></td>
								<td width="70%"><html:text property="anoMesReferencia"
										size="7" maxlength="7"
										onkeypress="javascript: return isCampoNumerico(event);"
										onkeyup="javascript:mascaraAnoMes(this, event);"
										onblur="javascript:return verificaAnoMes(this);" />
									&nbsp;mm/aaaa <br /></td>
							</tr>
							<tr>
								<td width="162"><strong>Dia do Vencimento:</strong></td>
								<td><strong> <html:text property="diaVencimento"
											size="1" maxlength="2"
											onkeypress="return isCampoNumerico(event);">
										</html:text>
								</strong></td>
							</tr>

							<tr>
								<td width="162"><strong>Dia Vencimento Débito
										Automático:</strong></td>
								<td><strong> <html:text
											property="diaVencimentoDebitoAutomatico" size="1"
											maxlength="2" onkeypress="return isCampoNumerico(event);"></html:text></strong></td>
							</tr>

							<tr>
								<td width="162"><strong>Dia Vencimento Entrega
										Alternativa:</strong></td>
								<td><strong> <html:text
											property="diaVencimentoEntregaAlternativa" size="1"
											maxlength="2" onkeypress="return isCampoNumerico(event);">
										</html:text>
								</strong></td>
							</tr>






							<tr>
								<td><strong>O vencimento do grupo é igual ao
										ano/mês do faturamento?</strong></td>
								<td><html:radio property="indicadorVencimentoMesFatura"
										value="<%=ConstantesSistema.SIM.toString()%>" /><strong>Sim</strong>
									<html:radio property="indicadorVencimentoMesFatura"
										value="<%=ConstantesSistema.NAO.toString()%>" /><strong>
										Não</strong> <html:radio property="indicadorVencimentoMesFatura"
										value="<%=ConstantesSistema.TODOS.toString()%>" /><strong>Todos</strong></td>
							</tr>
							<tr>
								<td><strong>Indicador de Uso:</strong></td>
								<td><html:radio property="indicadorUso"
										value="<%=ConstantesSistema.SIM.toString()%>" /><strong>Ativo</strong>
									<html:radio property="indicadorUso"
										value="<%=ConstantesSistema.NAO.toString()%>" /><strong>
										Inativo</strong> <html:radio property="indicadorUso"
										value="<%=ConstantesSistema.TODOS.toString()%>" /><strong>Todos</strong>
								</td>
							</tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							</tr>
							<tr>
								<td><input name="Button" type="button"
									class="bottonRightCol" value="Limpar" align="left"
									onclick="window.location.href='<html:rewrite page="/exibirFiltrarFaturamentoGrupoAction.do?limpar=S"/>'">
								</td>
								<td align="right"></td>
								<td align="right"><gcom:controleAcessoBotao name="Button"
										value="Filtrar"
										onclick="javascript:validarForm(document.forms[0]);"
										url="filtrarFaturamentoGrupoAction.do" /></td>
							</tr>

						</table>
						<p>&nbsp;</p>
				</tr>
				<tr>
					<td colspan="3"></td>
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