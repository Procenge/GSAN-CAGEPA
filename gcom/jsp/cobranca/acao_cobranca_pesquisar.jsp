<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="gcom.util.Util" %>
<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js">
</script><html:javascript staticJavascript="false"  formName="PesquisarAcaoCobrancaActionForm" />

<script >

	function validarForm(form) {
		if (validatePesquisarAcaoCobrancaActionForm(form)){
	   		submeterFormPadrao(form);
		}
	}
	
	function replicaDados(campoOrigem, campoDestino){
		campoDestino.value = campoOrigem.value;
	}
	
	function limparServicoTipo(form){
		form.idServicoTipo.value = "";
		form.descricaoServicoTipo.value = "";
	}
	
	function limparDescricaoServicoTipo(form){
			form.descricaoServicoTipo.value = "";
	}
	

</script>

</head>

<!-- onload="resizePageSemLink(765,530);" -->
<body leftmargin="5" topmargin="5" onload="resizePageSemLink(765,530);">
<html:form action="/pesquisarAcaoCobrancaAction" method="post">
	<table width="717" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="707" valign="top" class="centercoltext">
			<table height="100%">

				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="11"><img border="0" src="<bean:message key='caminho.imagens'/>parahead_left.gif"/></td>
                <td class="parabg">Pesquisar Ação de Cobrança</td>
                <td width="11" valign="top"><img border="0" src="<bean:message key='caminho.imagens'/>parahead_right.gif"/></td>
              </tr>
            </table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="3">Preencha o campo para pesquisar uma ação de cobran&ccedil;a:</td>
				</tr>
			</table>
				
			<table width="100%" border="0">
			<tr>
					<td width="50%"><strong>Descrição da Ação de Cobrança:</strong></td>
					<td width="50%"><html:text property="descricaoAcao"	size="40" maxlength="30" tabindex="1"/></td>
				</tr>				
              <tr> 
                <td width="50%"><strong>Estágio da Cobrança</strong></td>
                <td width="50%">
                    <html:select property="idCobrancaEstagio" tabindex="3">
							<html:option value="">&nbsp;</html:option>
							<html:options collection="colecaoCobrancaEstagio" labelProperty="descricao" property="id" />
					</html:select>
                </td>
              </tr>            
             <tr> 
                <td width="50%"><strong>Tipo de Documento a ser Gerado:</strong></td>
                <td width="50%">
                    <html:select property="idTipoDocumentoGerado" tabindex="6">
							<html:option value="">&nbsp;</html:option>
							<html:options collection="colecaoDocumentoTipo" labelProperty="descricaoDocumentoTipo" property="id" />
					</html:select>
                </td>
              </tr>     
              
              <tr>
					<td><strong>Tipo de Serviço da Ordem de Serviço a ser Gerada:</strong></td>
					<td colspan="2"><html:text maxlength="4" property="idServicoTipo"
						onkeyup="limparDescricaoServicoTipo(document.forms[0]);" size="4" tabindex="5"
						onkeypress="javascript:limparDescricaoServicoTipo(document.forms[0]);
						validaEnterComMensagem(event, 'exibirPesquisarAcaoCobrancaAction.do?objetoConsulta=1', 'idServicoTipo', 'Tipo de Serviço');" />
						
						<a href="javascript:redirecionarSubmit('exibirPesquisarTipoServicoAction.do?caminhoRetornoTelaPesquisaTipoServico=exibirPesquisarAcaoCobrancaAction', 400, 600);">
						   <img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Tipo de Serviço" />
						</a> 
						
						<logic:present name="idServicoTipoNaoEncontrado">
						<logic:equal name="idServicoTipoNaoEncontrado" value="exception">
							<html:text property="descricaoServicoTipo" size="35" maxlength="30"
								readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="idServicoTipoNaoEncontrado" value="exception">
							<html:text property="descricaoServicoTipo" size="35" maxlength="30"
								readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent name="idServicoTipoNaoEncontrado">
						<logic:empty name="PesquisarAcaoCobrancaActionForm" property="idServicoTipo">
							<html:text property="descricaoServicoTipo" value="" size="35"
								maxlength="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="PesquisarAcaoCobrancaActionForm" property="idServicoTipo">
							<html:text property="descricaoServicoTipo" size="35" maxlength="30"
								readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>

					</logic:notPresent> <a href="javascript:limparServicoTipo(document.forms[0]);">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" /></a></td>
				</tr>
              
				<tr>
					<td height="0" colspan="3">&nbsp;</td>
				</tr>
				<tr>
					<td height="24">
					<logic:present name="caminhoRetornoTelaPesquisaCriterioCobranca">
					<input type="button" class="bottonRightCol" value="Voltar"
						onclick="redirecionarSubmit('${caminhoRetornoTelaPesquisaCriterioCobranca}.do')">
					 </logic:present>
					 
					 <logic:notPresent name="popup">
					<input type="button" name="Submit223"
						class="bottonRightCol" value="Fechar"
						onClick="javascript:window.close();">
					 </logic:notPresent>
					
					<input type="button" name="Submit2222" class="bottonRightCol"
						value="Limpar" onclick="window.location.href='/gsan/exibirPesquisarAcaoCobrancaAction.do?limpaForm=sim&menu=sim';"></td>
					<td colspan="2">
					
					<div align="right">
						<input type="button" onclick="javascript:validarForm(document.forms[0]);" name="botaoPesquisar" class="bottonRightCol" value="Pesquisar">
					</div>
					</td>
				</tr>
				<tr>
					<td height="24">&nbsp;</td>
					<td colspan="2">&nbsp;</td>

				</tr>
			</table>
			<p>&nbsp;</p>
			</td>

		</tr>
	</table>
</html:form>
</body>
</html:html>
