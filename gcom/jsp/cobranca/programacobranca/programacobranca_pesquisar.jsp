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
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="PesquisarProgramaCobrancaActionForm" />

<script >

	var bCancel = false; 
   function validatePesquisa(form) {                                                                   
      if (bCancel) 
    	return true; 
      else 
	    return validateDate(form); 
   } 

	function validarForm(form) {
		if (validatePesquisarProgramaCobrancaActionForm(form)){
	   		submeterFormPadrao(form);
		}else{
			return false;			
		}
	}
	
	function replicaDados(campoOrigem, campoDestino){
		campoDestino.value = campoOrigem.value;
	}
	
	function limparCriterio(form){
		form.idCriterio.value = "";
		form.descricaoCriterio.value = "";
	}
	
	function limparDescricaoCriterio(form){
			form.descricaoCriterio.value = "";
	}
	
</script>

</head>

<!-- onload="resizePageSemLink(765,530);" -->
<body leftmargin="5" topmargin="5" onload="resizePageSemLink(765,530);">
<html:form action="/pesquisarProgramaCobrancaAction" method="post">
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
                <td class="parabg">Pesquisar Programa de Cobrança </td>
                <td width="11" valign="top"><img border="0" src="<bean:message key='caminho.imagens'/>parahead_right.gif"/></td>
              </tr>
            </table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="3">Preencha o campo para pesquisar um programa de cobran&ccedil;a:</td>
				</tr>
			</table>
				
			<table width="100%" border="0">
			  <tr>
				<td width="20%"><strong>Nome:</strong></td>
				<td width="80%"><html:text maxlength="40" property="nomeProgramaCobranca" size="40" tabindex="6" /></td>
			  </tr>
			  <tr>
				<td>&nbsp;</td>
				<td valign="top">
				  <html:radio property="tipoPesquisa" value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" /> Iniciando pelo texto
				  <html:radio property="tipoPesquisa" tabindex="5" value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" /> Contendo o texto
				</td>
			  </tr>
			  
			  <tr>
				<td width="20%"><strong>Descrição:</strong></td>
				<td width="80%"><html:text maxlength="40" property="descricaoProgramaCobranca" size="40" tabindex="6" /></td>
			  </tr>
			  <tr>
				<td>&nbsp;</td>
				<td valign="top">
				  <html:radio property="tipoPesquisaDescricao" value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" /> Iniciando pelo texto
				  <html:radio property="tipoPesquisaDescricao" tabindex="5" value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" /> Contendo o texto
				</td>
			  </tr>
			  <tr>
					<td><strong>Critério de Cobrança:</strong></td>
					<td colspan="2"><html:text maxlength="4" property="idCriterio"
						onkeyup="limparDescricaoCriterio(document.forms[0]);" size="4" tabindex="5"
						onkeypress="javascript:limparDescricaoCriterio(document.forms[0]);
						validaEnterComMensagem(event, 'exibirPesquisarProgramaCobrancaAction.do?objetoConsulta=1', 'idCriterio', 'CriterioCobranca');" />
						
						<a href="javascript:redirecionarSubmit('exibirPesquisarCriterioCobrancaAction.do?caminhoRetornoTelaPesquisaCriterioCobranca=exibirPesquisarProgramaCobrancaAction', 275, 480);">
						   <img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Critério Cobrança" />
						</a> 
						
						<logic:present name="idCriterioNaoEncontrado">
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
						<logic:empty name="PesquisarProgramaCobrancaActionForm" property="idCriterio">
							<html:text property="descricaoCriterio" value="" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="PesquisarProgramaCobrancaActionForm" property="idCriterio">
							<html:text property="descricaoCriterio" size="40" maxlength="30"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>

					</logic:notPresent> <a href="javascript:limparCriterio(document.forms[0]);">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				
				<tr>
					<td width="30%"><strong>Per&iacute;odo de Criação:</strong></td>
					<td colspan="3" align="right">
					<div align="left">
					<html:text property="dataCriacaoInicial" size="10"
						maxlength="10" onkeyup="mascaraData(this, event);replicarCampo(document.forms[0].dataCriacaoFinal,this);" /> 
						<a href="javascript:abrirCalendario('PesquisarProgramaCobrancaActionForm', 'dataCriacaoInicial')">
					<img border="0"
						src="<bean:message key='caminho.imagens'/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> <strong> a</strong> 
					<html:text property="dataCriacaoFinal"
						size="10" maxlength="10"  onkeyup="mascaraData(this, event);" /> 
						<a href="javascript:abrirCalendario('PesquisarProgramaCobrancaActionForm', 'dataCriacaoFinal')">
					<img border="0"
						src="<bean:message key='caminho.imagens'/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>&nbsp;dd/mm/aaaa
					</div>
					</td>
				</tr>
				 
				 <tr>
					<td width="30%">
						<strong>Per&iacute;odo de Início:</strong>
					</td>
					<td colspan="3" align="right">
					<div align="left">
					<html:text property="dataInicioInicial" size="10"
						maxlength="10" onkeyup="mascaraData(this, event);replicarCampo(document.forms[0].dataInicioFinal,this);" /> 
						<a href="javascript:abrirCalendario('PesquisarProgramaCobrancaActionForm', 'dataInicioInicial')">
					<img border="0"
						src="<bean:message key='caminho.imagens'/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> <strong> a</strong> 
					<html:text property="dataInicioFinal"
						size="10" maxlength="10"  onkeyup="mascaraData(this, event);" /> 
						<a href="javascript:abrirCalendario('PesquisarProgramaCobrancaActionForm', 'dataInicioFinal')">
					<img border="0"
						src="<bean:message key='caminho.imagens'/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>&nbsp;dd/mm/aaaa
					</div>
					</td>
				</tr>
				
				<tr>
					<td width="30%"><strong>Per&iacute;odo da Última Movimentação:</strong></td>
					<td colspan="3" align="right">
					<div align="left">
					<html:text property="dataUltimoMovimentoInicial" size="10"
						maxlength="10" onkeyup="mascaraData(this, event);replicarCampo(document.forms[0].dataUltimoMovimentoFinal,this);" />
						<a href="javascript:abrirCalendario('PesquisarProgramaCobrancaActionForm', 'dataUltimoMovimentoInicial')">
					<img border="0"
						src="<bean:message key='caminho.imagens'/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> <strong> a</strong> 
					<html:text property="dataUltimoMovimentoFinal"
						size="10" maxlength="10"  onkeyup="mascaraData(this, event);" /> 
						<a href="javascript:abrirCalendario('PesquisarProgramaCobrancaActionForm', 'dataUltimoMovimentoFinal')">
					<img border="0"
						src="<bean:message key='caminho.imagens'/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>&nbsp;dd/mm/aaaa
					</div>
					</td>
				</tr>

			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
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
						value="Limpar" onclick="window.location.href='/gsan/exibirPesquisarProgramaCobrancaAction.do?limpaForm=sim&menu=sim';"></td>
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
