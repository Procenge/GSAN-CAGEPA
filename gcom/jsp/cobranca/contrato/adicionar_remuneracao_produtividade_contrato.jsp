<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="gcom.util.ConstantesSistema" %>

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="AdicionarRemuneracaoProdutividadeContratoActionForm"/>
<SCRIPT LANGUAGE="JavaScript">
<!--

function validarForm(form){
	submeterFormPadrao(form);
}

function verificaCampoVazio(objeto1, objeto2) {
	var form = document.forms[0];
	if (objeto1.value != "") {
		objeto2.disabled = true;
	} else {
		objeto2.disabled = false;
	}
}

function limparTipoServico(){
	var form = document.forms[0];
	form.servicoTipo.value = "";
	form.descricaoServicoTipo.value = "";
}

/* Recuperar Popup */
function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
    var form = document.forms[0];
    
    if (tipoConsulta == 'TipoServico') {

	      if(!form.servicoTipo.disabled){
		      form.servicoTipo.value = codigoRegistro;
		      form.descricaoServicoTipo.value = descricaoRegistro;
		      form.descricaoServicoTipo.style.color = "#000000";
		      reload();
	      }
    }
}

//-->
</SCRIPT>

</head>

<logic:present name="reloadPage">

	<logic:equal name="reloadPageURL" value="INSERIRCONTRATO">
		<body leftmargin="5" topmargin="5" onload="window.focus(); resizePageSemLink(480, 280); chamarSubmitComUrl('exibirInserirContratoCobrancaAction.do?reloadPage=1'); self.close();">
	</logic:equal>
	
	<logic:equal name="reloadPageURL" value="MANTERCONTRATO">
		<body leftmargin="5" topmargin="5" onload="window.focus(); resizePageSemLink(480, 280); chamarSubmitComUrl('exibirAtualizarContratoCobrancaAction.do?reloadPage=OK'); opener.reloadPagina();limparTipoServico();">
	</logic:equal>
	
</logic:present>

<logic:notPresent name="reloadPage">
	<body leftmargin="5" topmargin="5" onload="window.focus(); resizePageSemLink(480, 280);">
</logic:notPresent>


<html:form action="/adicionarRemuneracaoProdutividadeContratoAction.do" method="post">

<table width="452" border="0" cellpadding="0" cellspacing="5">
  <tr>
    <td width="442" valign="top" class="centercoltext"> 
      <table height="100%">
        <tr>
          <td></td>
        </tr>
      </table>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img src="<bean:message key="caminho.imagens"/>parahead_left.gif" editor="Webstyle4" moduleid="Album Photos (Project)\toptab_page2_parahead_left.xws" border="0" /></td>
          <td class="parabg">Adicionar Remuneração por Produtividade</td>
          <td width="11"><img src="<bean:message key="caminho.imagens"/>parahead_right.gif" editor="Webstyle4" moduleid="Album Photos (Project)\toptab_page2_parahead_right.xws" border="0" /></td>
        </tr>
      </table>
      <p>&nbsp;</p>

      <table width="100%" border="0">
        <tr>
          <td colspan="4">Preencha os campos abaixo para inserir uma remuneração por produtividade:</td>
        </tr>
        </table>
			<table width="100%" border="0">

				<tr>
					<td><strong>Tipo Serviço:<font color="#FF0000">*</font></strong></strong></td>
					<td colspan="3"><html:text property="servicoTipo" size="5" maxlength="10" onkeyup="javascript:verificaNumeroInteiro(this);validaEnterComMensagem(event, 'exibirAdicionarRemuneracaoProdutividadeContratoAction.do', 'servicoTipo', 'Serviço Tipo');" />

					<a href="javascript:abrirPopup('exibirPesquisarTipoServicoAction.do?caminhoRetornoTelaPesquisaTipoServico=exibirAdicionarRemuneracaoProdutividadeContratoAction',500,800);">
					<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Servico Tipo" />
					</a> 
						<logic:present name="servicoTipoEncontrado" scope="request">
							<html:text property="descricaoServicoTipo" size="45" maxlength="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 
						<logic:notPresent name="servicoTipoEncontrado" scope="request">
							<html:text property="descricaoServicoTipo" size="45" maxlength="45" readonly="true" style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent> 
						<a href="javascript:limparTipoServico();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
						</a></td>
				</tr>
				<tr>
					<td><strong>Valor Fixo:</td>
					<td colspan="3"><html:text property="valorFixo" size="15"
						style="text-align: right;"
						onkeyup="formataValorMonetario(this, 11);" />
					</td>
				</tr>
				<tr>
					<td height="30" colspan="4">
					<div align="right">
						<input type="button" onclick="validarForm(document.forms[0]);" class="bottonRightCol" value="Inserir" style="width: 70px;">&nbsp; 
						<input type="button" onclick="window.close();" class="bottonRightCol" value="Fechar" style="width: 70px;"></div>
					</td>
				</tr>
			</table>

			<p>&nbsp;</p>
      </td>
  </tr>
</table>

</html:form>

</body>
</html:html>



