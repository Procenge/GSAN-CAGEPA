<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.util.Util" %>
<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<html:javascript staticJavascript="false"  formName="FiltrarProgramaCobrancaActionForm" />

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<script>

function validarForm(form){
	var form = document.forms[0];
	if (validateFiltrarProgramaCobrancaActionForm(form)) {
		submeterFormPadrao(form);
	}
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

function limparCriterio(form){
		var form = document.forms[0];
		form.idCriterio.value = "";
		form.descricaoCriterio.value = "";
	}
	
function limparDescricaoCriterio(form){
		form.descricaoCriterio.value = "";
}
</script>
</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">
<div id="formDiv">
<html:form action="/filtrarProgramaCobrancaAction"   
	name="FiltrarProgramaCobrancaActionForm"
  	type="gcom.gui.cobranca.programacobranca.FiltrarProgramaCobrancaActionForm"
  	method="post"
  	focus="codigoProgramaCobranca">

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
                <td class="parabg">Filtrar Programa de Cobrança </td>
                <td width="11" valign="top"><img border="0" src="<bean:message key='caminho.imagens'/>parahead_right.gif"/></td>
              </tr>
            </table>
			<p>&nbsp;</p>
			
            <table width="100%" border="0">
              <tr>
	           <td width="100%" colspan=2>
		          <table width="100%">
		          	<tr>
		          		<td>Para Filtrar o(s) programa(s) de cobrança(s), informe os dados abaixo: </td>
		          		<td width="74" align="right"><html:checkbox property="atualizar" value="1"/><strong>Atualizar</strong></td>
		          	</tr>
		          </table>
	           </td>
	          </tr>
              <tr>
                <td><strong>C&oacute;digo: </strong></td>
                <td><strong>
                  	<html:text property="codigoProgramaCobranca" maxlength="4" size="9" />
                </strong></td>
              </tr>
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
						validaEnterComMensagem(event, 'exibirFiltrarProgramaCobrancaAction.do?objetoConsulta=1', 'idCriterio', 'CriterioCobranca');" />
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
						<logic:empty name="FiltrarProgramaCobrancaActionForm" property="idCriterio">
							<html:text property="descricaoCriterio" value="" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="FiltrarProgramaCobrancaActionForm" property="idCriterio">
							<html:text property="descricaoCriterio" size="40" maxlength="30"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>

					</logic:notPresent> <a href="javascript:limparCriterio();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				
				<tr>
					<td width="30%"><strong>Per&iacute;odo de Criação:</strong></td>
					<td colspan="3" align="right">
					<div align="left">
					<html:text property="dataCriacaoInicial" size="10"
						maxlength="10" onkeyup="mascaraData(this, event);replicarCampo(document.forms[0].dataCriacaoFinal,this);" /> 
						<a href="javascript:abrirCalendario('FiltrarProgramaCobrancaActionForm', 'dataCriacaoInicial')">
					<img border="0"
						src="<bean:message key='caminho.imagens'/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> <strong> a</strong> 
					<html:text property="dataCriacaoFinal"
						size="10" maxlength="10"  onkeyup="mascaraData(this, event);" /> 
						<a href="javascript:abrirCalendario('FiltrarProgramaCobrancaActionForm', 'dataCriacaoFinal')">
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
						<a href="javascript:abrirCalendario('FiltrarProgramaCobrancaActionForm', 'dataInicioInicial')">
					<img border="0"
						src="<bean:message key='caminho.imagens'/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> <strong> a</strong> 
					<html:text property="dataInicioFinal"
						size="10" maxlength="10"  onkeyup="mascaraData(this, event);" /> 
						<a href="javascript:abrirCalendario('FiltrarProgramaCobrancaActionForm', 'dataInicioFinal')">
					<img border="0"
						src="<bean:message key='caminho.imagens'/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>&nbsp;dd/mm/aaaa
					</div>
					</td>
				</tr>
				
				<tr>
					<td width="30%"><strong>Per&iacute;odo de Encerramento:</strong></td>
					<td colspan="3" align="right">
					<div align="left">
					<html:text property="dataEncerramentoInicial" size="10"
						maxlength="10" onkeyup="mascaraData(this, event);replicarCampo(document.forms[0].dataEncerramentoFinal,this);" /> 
						<a href="javascript:abrirCalendario('FiltrarProgramaCobrancaActionForm', 'dataEncerramentoInicial')">
					<img border="0"
						src="<bean:message key='caminho.imagens'/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> <strong> a</strong> 
					<html:text property="dataEncerramentoFinal"
						size="10" maxlength="10"  onkeyup="mascaraData(this, event);" /> 
						<a href="javascript:abrirCalendario('FiltrarProgramaCobrancaActionForm', 'dataEncerramentoFinal')">
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
						<a href="javascript:abrirCalendario('FiltrarProgramaCobrancaActionForm', 'dataUltimoMovimentoInicial')">
					<img border="0"
						src="<bean:message key='caminho.imagens'/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> <strong> a</strong> 
					<html:text property="dataUltimoMovimentoFinal"
						size="10" maxlength="10"  onkeyup="mascaraData(this, event);" /> 
						<a href="javascript:abrirCalendario('FiltrarProgramaCobrancaActionForm', 'dataUltimoMovimentoFinal')">
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
				<td>
					<input name="Button" type="button" class="bottonRightCol" value="Limpar" align="left"
					onclick="window.location.href='<html:rewrite page="/exibirFiltrarProgramaCobrancaAction.do?desfazer=S"/>'">
				</td>
				<td align="right"></td>
				<td align="right">
					<gcom:controleAcessoBotao name="Button" value="Filtrar"
											  onclick="javascript:validarForm();" 
											  url="filtrarProgramaCobrancaAction.do" />
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