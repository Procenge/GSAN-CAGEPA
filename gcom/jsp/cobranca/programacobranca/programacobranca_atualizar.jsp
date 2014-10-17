<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

 <%@ include file="/jsp/util/titulo.jsp"%>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

 <html:javascript staticJavascript="false"  formName="AtualizarProgramaCobrancaActionForm" />

 <script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
 <script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
 <script>

function validarForm(form){

	if(validateAtualizarProgramaCobrancaActionForm(form)){
		form.submit();
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

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.forms[0];

    if (tipoConsulta == 'criterioCobranca') {
      limparCriterio(form);
      form.idCriterio.value = codigoRegistro;
      form.descricaoCriterio.value = descricaoRegistro;
      form.descricaoCriterio.style.color = "#000000";
    }
}
	
 </script>
</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">
<div id="formDiv">
<html:form action="/atualizarProgramaCobrancaAction.do" method="post"
	name="AtualizarProgramaCobrancaActionForm"
	enctype="multipart/form-data"
	type="gcom.gui.cadastro.empresa.AtualizarProgramaCobrancaActionForm"
	onsubmit="return validateAtualizarProgramaCobrancaActionForm(this);">

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
					<td width="11"><img border="0" src="<bean:message key='caminho.imagens'/>parahead_left.gif" /></td>
					<td class="parabg">Atualizar Programa de Cobranca</td>
					<td width="11" valign="top"><img border="0" src="<bean:message key='caminho.imagens'/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			 
			 <table width="100%" border="0">
			 	<tr> 
                	<td colspan="2">Para atualizar o programa de cobrança, informe os dados abaixo:</td>
              	</tr>
              	
              	<tr>
                	<td><strong>C&oacute;digo: </strong></td>
                	<td><html:hidden property="codigoProgramaCobranca" /> 
						<bean:write name="AtualizarProgramaCobrancaActionForm" property="codigoProgramaCobranca" /></td>
              	</tr>
              	
             	
              	<tr>
					<td><strong>Nome: <font color="#FF0000">*</font></strong></td>
					<td align="right">
					<div align="left"><strong><html:text
						property="nomeProgramaCobranca" maxlength="30" size="50" /></strong></div>
					</td>
				</tr>
				
				<tr>
					<td><strong>Descrição: <font color="#FF0000">*</font></strong></td>
					<td align="right">
					<div align="left"><strong><html:text
						property="descricaoProgramaCobranca" maxlength="50" size="50" /></strong></div>
					</td>
				</tr>
				
				<tr>
					<td><strong>Critério de Cobrança:<font color="#FF0000">*</font></strong></td>
					<td colspan="2"><html:text maxlength="4" property="idCriterio"
						onkeyup="limparDescricaoCriterio();" size="4" tabindex="5"
						onkeypress="javascript:limparDescricaoCriterio(document.forms[0]);
						validaEnterComMensagem(event, 'exibirAtualizarProgramaCobrancaAction.do?enter=sim', 'idCriterio','CriterioCobranca');" />
					<a
						href="javascript:abrirPopup('exibirPesquisarCriterioCobrancaAction.do');">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Critério Cobrança" /></a> <logic:present
						name="idCriterioNaoEncontrado">
						<logic:equal name="idCriterioNaoEncontrado" value="exception">
							<html:text property="descricaoCriterio" size="40" maxlength="30"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="idCriterioNaoEncontrado" value="exception">
							<html:text property="descricaoCriterio" size="40" maxlength="30"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent name="idCriterioNaoEncontrado">
						<logic:empty name="AtualizarProgramaCobrancaActionForm" property="idCriterio">
							<html:text property="descricaoCriterio" value="" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="AtualizarProgramaCobrancaActionForm" property="idCriterio">
							<html:text property="descricaoCriterio" size="40" maxlength="30"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>

					</logic:notPresent> <a href="javascript:limparCriterio();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				
              	<tr> 
                	<td><strong> <font color="#FF0000"></font></strong></td>
                	<td align="right"> <div align="left"><strong><font color="#FF0000">*</font></strong>Campos obrigat&oacute;rios</div></td>
              	</tr>
              	<tr><td>&nbsp;</td></tr>
              	
              	<table width="100%" border="0">
	              <tr>
					<td>
						<logic:present name="manter" scope="session">
							<input type="button" name="ButtonReset" class="bottonRightCol" value="Voltar"
								onClick="javascript:window.location.href='/gsan/exibirManterProgramaCobrancaAction.do'">
						</logic:present>

						<logic:notPresent name="manter" scope="session">
							<input type="button" name="ButtonReset"  class="bottonRightCol" value="Voltar"
								onClick="javascript:window.location.href='/gsan/exibirFiltrarProgramaCobrancaAction.do'">
						</logic:notPresent> 
					<input name="Button" type="button" class="bottonRightCol"
						value="Desfazer" align="left"
						onclick="window.location.href='<html:rewrite page="/exibirAtualizarProgramaCobrancaAction.do?codigoPrograma=${AtualizarProgramaCobrancaActionForm.codigoProgramaCobranca}"/>'">
					<input name="Button" type="button" class="bottonRightCol"
							value="Cancelar" align="left"
							onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"></td>
					<td align="right"><input name="Button" type="button"
						class="bottonRightCol" value="Atualizar" align="right"
						onClick="javascript:validarForm(document.forms[0]);"></td>
				</tr>
	              
             	</table>
              	
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