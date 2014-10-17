<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="gcom.util.Util" %>
<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
 <%@ include file="/jsp/util/titulo.jsp"%>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

 <html:javascript staticJavascript="false"  formName="InserirProgramaCobrancaActionForm" />

 <script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
 <script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
 <script>
 
	 function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	
	    var form = document.forms[0];
	
	    if (tipoConsulta == 'criterioCobranca') {
	      limparCriterio(form);
	      form.idCriterio.value = codigoRegistro;
	      form.descricaoCriterio.value = descricaoRegistro;
	      form.descricaoCriterio.style.color = "#000000";
	    }
	}
	
	function limparCriterio(){
		var form = document.forms[0];
		form.idCriterio.value = "";
		form.descricaoCriterio.value = "";
	}
	
	function limparDescricaoCriterio(){
		var form = document.forms[0];
		form.descricaoCriterio.value = "";
	}

	
	function validaForm() {
	  	var form = document.forms[0];
	 	if(validateInserirProgramaCobrancaActionForm(form)){
   			submeterFormPadrao(form);
	 	}
	}

 </script>
</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">
<div id="formDiv">
<html:form 	action="/inserirProgramaCobrancaAction" method="post"
			name="InserirProgramaCobrancaActionForm"
			type="gcom.gui.cobranca.programacobranca.InserirProgramaCobrancaActionForm"
			onsubmit="return validateInserirProgramaCobrancaActionForm(this);">

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
					<td class="parabg">Inserir Programa de Cobranca</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key='caminho.imagens'/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">

				<tr>
					<td colspan="2">Para adicionar um programa de cobranca, informe os dados abaixo:</td>
				</tr>
				<tr>
					<td><strong>Nome do programa de cobrança: <font color="#FF0000">*</font></strong></td>
					<td align="right">
						<div align="left">
							<html:text property="nomeProgramaCobranca" maxlength="30" size="50" tabindex="1" />
						</div>
					</td>
				</tr>

				<tr>
					<td><strong>Descrição do Programa de Cobranca: <font color="#FF0000">*</font></strong></td>
					<td align="right">
					  <div align="left">
					  	<html:text property="descricaoProgramaCobranca" maxlength="50" size="50" tabindex="2" />
					  </div>
					</td>
				</tr>

				<tr>
					<td><strong>Critério de Cobrança:<font color="#FF0000">*</font></strong></td>
					<td colspan="2">
						<html:text maxlength="4" property="idCriterio" onkeyup="limparDescricaoCriterio();" 
								   size="4" tabindex="3" onkeypress="javascript:limparDescricaoCriterio();
								   validaEnterComMensagem(event, 'exibirInserirProgramaCobrancaAction.do?objetoConsulta=1', 'idCriterio','CriterioCobranca');" />
						<a href="javascript:abrirPopup('exibirPesquisarCriterioCobrancaAction.do');" tabindex="4">
							<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								 title="Pesquisar Critério Cobrança" />
						</a>
						<logic:present name="idCriterioNaoEncontrado">
						  <logic:equal name="idCriterioNaoEncontrado" value="exception">
							<html:text property="descricaoCriterio" size="40" maxlength="30" readonly="true"
									   style="background-color:#EFEFEF; border:0; color: #ff0000" />
						  </logic:equal>
						  <logic:notEqual name="idCriterioNaoEncontrado" value="exception">
							<html:text property="descricaoCriterio" size="40" maxlength="30" readonly="true"
									   style="background-color:#EFEFEF; border:0; color: #000000" />
						  </logic:notEqual>
					   	</logic:present>
					   	<logic:notPresent name="idCriterioNaoEncontrado">
						  <logic:empty name="InserirProgramaCobrancaActionForm" property="idCriterio">
							<html:text property="descricaoCriterio" value="" size="40" maxlength="30" readonly="true"
									   style="background-color:#EFEFEF; border:0; color: #ff0000" />
						  </logic:empty>
						  <logic:notEmpty name="InserirProgramaCobrancaActionForm" property="idCriterio">
							<html:text property="descricaoCriterio" size="40" maxlength="30" readonly="true"
									   style="background-color:#EFEFEF; border:0; color: #000000" />
						  </logic:notEmpty>
					    </logic:notPresent> 
					    <a href="javascript:limparCriterio();" tabindex="5">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
						</a>
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td><strong> <font color="#FF0000"> 
					  <input name="btLimpar" class="bottonRightCol" value="Limpar" type="button" tabindex="7"
						onclick="window.location.href='/gsan/exibirInserirProgramaCobrancaAction.do?menu=sim';">
					  <input type="button" name="Button" class="bottonRightCol" value="Cancelar" tabindex="8"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
						style="width: 80px" /> </font></strong>
					</td>
					<td align="right">
						<input type="button" onclick="javascript:validaForm();" name="botaoInserir" class="bottonRightCol" value="Inserir" tabindex="6">
					</td>
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

</html:html>