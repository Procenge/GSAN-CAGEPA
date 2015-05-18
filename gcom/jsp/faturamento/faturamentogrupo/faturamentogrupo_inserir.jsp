<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<%@ page import="gcom.util.Util"%>
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
	formName="InserirFaturamentoGrupoActionForm" />

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script>
	
 function validaForm() {
	  	var form = document.forms[0];	  	
	  	
	 	if(validateInserirFaturamentoGrupoActionForm(form)){
	 		
	 		
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
		<html:form action="/inserirFaturamentoGrupoAction"
			enctype="multipart/form-data" method="post" focus="idCliente">

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
					<td width="655" valign="top" class="centercoltext">
						<!--Início Tabela Reference a Páginação da Tela de Processo-->

						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td width="11"><img border="0"
									src="<bean:message key='caminho.imagens'/>parahead_left.gif" /></td>
								<td class="parabg">Inserir Grupo de Faturamento</td>
								<td width="11" valign="top"><img border="0"
									src="<bean:message key='caminho.imagens'/>parahead_right.gif" /></td>
							</tr>
						</table>
						<p>&nbsp;</p>
						<table width="100%" border="0">
							<tr>
								<td colspan="2">Para adicionar um grupo de faturamento,
									informe os dados abaixo:</td>
							</tr>
							<tr>
								<td><strong>Código:<font color="#FF0000">*</font></strong>
								</td>
								<td><strong> <html:text
											property="idFaturamentoGrupo" size="5" maxlength="5"
											onkeypress="return isCampoNumerico(event);"></html:text>
								</strong></td>
							</tr>
							<tr>
								<td width="162"><strong>Descrição:<font
										color="#FF0000">*</font></strong></td>
								<td><strong> <html:text property="descricao"
											size="25" maxlength="25"></html:text>
								</strong></td>
							</tr>

							<tr>
								<td width="162"><strong>Descrição Abreviada:<font
										color="#FF0000">*</font></strong></td>
								<td><strong> <html:text
											property="descricaoAbreviada" size="3" maxlength="3"></html:text>
								</strong></td>
							</tr>
							<tr>
								<td width="30%"><strong>Mês/Ano Referência:<font
										color="#FF0000">*</font></strong></td>
								<td width="70%"><html:text property="anoMesReferencia"
										size="7" maxlength="7"
										onkeypress="javascript: return isCampoNumerico(event);"
										onkeyup="javascript:mascaraAnoMes(this, event);"
										onblur="javascript:return verificaAnoMes(this);" />
									&nbsp;mm/aaaa <br /></td>
							</tr>
							<tr>
								<td width="162"><strong>Dia do Vencimento:<font
										color="#FF0000">*</font></strong></td>
								<td><strong> <html:text property="diaVencimento"
											size="1" maxlength="2"
											onkeypress="return isCampoNumerico(event);">
										</html:text>
								</strong></td>
							</tr>

							<tr>
								<td width="162"><strong>Dia Vencimento Débito Automático:</strong></td>
								<td><strong> <html:text property="diaVencimentoDebitoAutomatico"
											size="1" maxlength="2"
											onkeypress="return isCampoNumerico(event);">
										</html:text>
								</strong></td>
							</tr>

							<tr>
								<td width="162"><strong>Dia Vencimento Entrega Alternativa:</strong></td>
								<td><strong> <html:text property="diaVencimentoEntregaAlternativa"
											size="1" maxlength="2"
											onkeypress="return isCampoNumerico(event);">
										</html:text>
								</strong></td>
							</tr>

							<tr>
								<td><strong>O vencimento do grupo é igual ao
										ano/mês do faturamento?<font color="#FF0000">*</font>
								</strong></td>
								<td><strong> <html:radio
											property="indicadorVencimentoMesFatura" value="1" /> <strong>Sim
											<html:radio property="indicadorVencimentoMesFatura" value="2" />
											Não
									</strong>
								</strong></td>
							</tr>

							<tr>
								<td><strong> <font color="#FF0000"></font></strong></td>
								<td align="right">
									<div align="left">
										<strong><font color="#FF0000">*</font></strong> Campos
										obrigat&oacute;rios
									</div>
								</td>
							</tr>

							<tr>
								<td><strong> <font color="#FF0000"> <input
											name="btLimpar" class="bottonRightCol" value="Limpar"
											type="button"
											onclick="window.location.href='/gsan/exibirInserirFaturamentoGrupoAction.do?menu=sim';">
											<input type="button" name="Button" class="bottonRightCol"
											value="Cancelar" tabindex="6"
											onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
											style="width: 80px" />
									</font></strong></td>
								<td align="right"><input type="button"
									onclick="javascript:validaForm();" name="botaoInserir"
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