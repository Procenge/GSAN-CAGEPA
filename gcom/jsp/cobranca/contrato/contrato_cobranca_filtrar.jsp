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

<html:javascript staticJavascript="false"  formName="FiltrarContratoCobrancaActionForm" />

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script>

	function validarForm(form) {
	
		if (testarCampoValorZero(document.FiltrarContratoCobrancaActionForm.numeroContrato, 'Número do Contrato')) {
	
			if(validateFiltrarContratoCobrancaActionForm(form)){
	    		submeterFormPadrao(form);
			}
		}
	}

</script>
</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">
<div id="formDiv">
<html:form action="/filtrarContratoCobrancaAction"   
	name="FiltrarContratoCobrancaActionForm"
  	type="gcom.gui.cobranca.contrato.FiltrarContratoCobrancaActionForm"
  	method="post"
  	focus="numeroContrato">

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
                <td class="parabg">Filtrar Contrato Cobrança</td>
                <td width="11" valign="top"><img border="0" src="<bean:message key='caminho.imagens'/>parahead_right.gif"/></td>
              </tr>
            </table>
			<p>&nbsp;</p>
			
            <table width="100%" border="0">
              <tr>
	           <td width="100%" colspan=2>
		          <table width="100%">
		          	<tr>
		          		<td>Para Filtrar o(s) contrato(s) de cobrança, informe os dados abaixo: </td>
		          		<td width="74" align="right"><html:checkbox property="checkAtualizar" value="1"/><strong>Atualizar</strong></td>
		          	</tr>
		          </table>
	           </td>
	          </tr>
              <tr>
                <td><strong>Número do Contrato: </strong></td>
                <td><strong>
                  	<html:text property="numeroContrato" maxlength="10" size="10" />
                </strong></td>
              </tr>

				<tr>
					<td width="162"><strong>Empresa:</strong></td>
					<td><html:select property="empresa">
						<option value="-1"></option>
						<html:options name="request" collection="colecaoEmpresa"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>

				<tr>
					<td width="30%"><strong>Per&iacute;odo:</strong></td>

					<td width="70%"><strong>
						<input type="hidden" id="DATA_ATUAL" value="${requestScope.dataAtual}" />
						<html:text property="dataInicio" size="10" maxlength="10" 
						onkeyup="mascaraData(this, event); replicarCampo(document.forms[0].dataFim,this);"
						onfocus="replicarCampo(document.forms[0].dataFim,this);"/>
						
						<a href="javascript:abrirCalendarioReplicando('FiltrarContratoCobrancaActionForm', 'dataInicio', 'dataFim');" >
							<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário"/>
						</a> a </strong> 
						<html:text property="dataFim" size="10" maxlength="10" 
								   onkeyup="mascaraData(this, event);" /> 
						<a href="javascript:abrirCalendario('FiltrarContratoCobrancaActionForm', 'dataFim')">
							<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" 
								 align="absmiddle" alt="Exibir Calendário" />
						</a>dd/mm/aaaa</td>
				</tr>
			  
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>
					<input name="Button" type="button" class="bottonRightCol" value="Limpar" align="left"
					onclick="window.location.href='<html:rewrite page="/exibirFiltrarContratoCobrancaAction.do?desfazer=S"/>'">
				</td>
				<td align="right"></td>
				<td align="right">
				
				<input name="Button2" type="button"
						class="bottonRightCol" value="Filtrar" align="right"
						onClick="javascript:validarForm(document.forms[0]);">

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