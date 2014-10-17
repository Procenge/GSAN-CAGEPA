<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ page import="gcom.util.CalendarioForm"%>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="PesquisarAtualizarSetorComercialActionForm" dynamicJavascript="false" />



<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
<script>

	function countChecked(objeto) {
		var qtdDiasVencimentoSelecionados = $("input[name=diasVencimento]:checked").length;

		if($("input[name=qtdDiasVencimento]").val() < qtdDiasVencimentoSelecionados){
			objeto.attr('checked', false);			
			alert("Quantidade de Dias para Vencimento do Setor Ultrapassou o Limite");
		}
	}

	$(document).ready(function(){
		$("input[name=diasVencimento]:checkbox").click(function () {
			countChecked($(this));
		});
	});

</script>

<style>

.tabela {
    border: medium solid #90C7FC;
    text-align: right;
}

.tabela tr {
	background: #90c7fc;
}

.tabela tbody tr td a {
	font-size: 12px;
	color: blue;
}

.linkDia {}

</style>
</logic:equal>

<SCRIPT LANGUAGE="JavaScript">
<!--

var bCancel = false; 

    function validatePesquisarAtualizarSetorComercialActionForm(form) {                                                                   
        if (bCancel) 
      return true; 
        else 
       return validateCaracterEspecial(form) && validateLong(form); 
   } 

    function caracteresespeciais () { 
		this.ac = new Array("setorComercialNome", "Nome do Setor Comercial possui caracteres especiais.", new Function ("varName", " return this[varName];"));
		this.ab = new Array("municipioID", "Município possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    } 

    function IntegerValidations () { 
     this.ab = new Array("municipioID", "Município deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    } 

function validarForm(formulario){

	var objSetorComercialNome = returnObject(formulario, "setorComercialNome");
	var objMunicipioID = returnObject(formulario, "municipioID");

	if(objSetorComercialNome.value.length < 1){
		alert("Informe Nome do Setor Comercial.");
		objSetorComercialNome.focus();
	}
	else if(objMunicipioID.value.length < 1){
		alert("Informe Município.");
		objMunicipioID.focus();
	}
	else if (validatePesquisarAtualizarSetorComercialActionForm(formulario)){
		submeterFormPadrao(formulario);
	}

}


function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

	var form = document.forms[0];

	if (tipoConsulta == 'municipio') {
	  form.municipioID.value = codigoRegistro;
	  form.municipioNome.value = descricaoRegistro;
	  form.municipioNome.style.color = "#000000";
	}
}
function limpar(){
   var form = document.forms[0];

   form.municipioID.value = "";
   form.municipioNome.value = "";
   //Coloca o foco no objeto selecionado
   form.municipioID.focus();
}

var dias = new Array(32) 

function checarCampos() {
	var form = document.forms[0];
	for (i = 0; i < form.diasVencimento.length; i++) {
		for (j = 0; j < dias.length; j++) {
			if (form.diasVencimento[i].value == dias[j]) {
				form.diasVencimento[i].checked = true;
			}
		}
	}
}

//-->
</SCRIPT>

</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');checarCampos();">

<html:form action="/atualizarSetorComercialAction" method="post">


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

	<td width="625" valign="top" class="centercoltext">

        <table height="100%">
        <tr>
          <td></td>
        </tr>
      	</table>

      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
          <td class="parabg">Atualizar Setor Comercial</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>

      <table width="100%" border="0">
      <tr>
      	<td colspan="2">Para alterar o setor comercial, informe os dados abaixo:</td>
      	<td align="right"></td>
      </tr>
      </table>
    
      <table width="100%" border="0">
      <tr>
      	<td width="30%" height="20"><strong>Localidade:</strong></td>
        <td width="70%">
			<bean:write name="PesquisarAtualizarSetorComercialActionForm" property="localidadeID"/> - 
        	<bean:write name="PesquisarAtualizarSetorComercialActionForm" property="localidadeNome"/>
		</td>
      </tr>
      <tr>
      	<td height="20"><strong>C&oacute;digo do Setor Comercial:</strong></td>
        <td><bean:write name="PesquisarAtualizarSetorComercialActionForm" property="setorComercialCD"/></td>
      </tr>
      <tr>
      	<td height="20"><strong>Nome do Setor Comercial:<font color="#FF0000">*</font></strong></td>
        <td align="right">
		<div align="left">

		<html:text maxlength="30" property="setorComercialNome" size="45" tabindex="1"/>

		</div>
	</td>
      </tr>
      <logic:notEqual name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
      <tr>
        <td height="20"><strong>Munic&iacute;pio:<font color="#FF0000">*</font></strong></td>
        <td align="right">
		<div align="left">

			<html:text property="municipioID" size="5" maxlength="4" tabindex="2" onkeypress="validaEnterComMensagem(event, 'exibirAtualizarSetorComercialAction.do?objetoConsulta=2', 'municipioID', 'Município');"/>
			<a	href="javascript:abrirPopup('exibirPesquisarMunicipioAction.do', 250, 495);"><img
						src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"
						width="23" height="21" title="Pesquisar"></a> 

			<logic:present name="corMunicipio">

				<logic:equal name="corMunicipio" value="exception">
					<html:text property="municipioNome" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
				</logic:equal>

				<logic:notEqual name="corMunicipio" value="exception">
					<html:text property="municipioNome" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
				</logic:notEqual>

			</logic:present>

			<logic:notPresent name="corMunicipio">

				<logic:empty name="PesquisarAtualizarSetorComercialActionForm" property="municipioID">
					<html:text property="municipioNome" size="45" value="" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
				</logic:empty>
				<logic:notEmpty name="PesquisarAtualizarSetorComercialActionForm" property="municipioID">
					<html:text property="municipioNome" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
				</logic:notEmpty>
				

			</logic:notPresent>
			<a href="javascript:limpar();"> 
				<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
			</a>

		</div>
	</td>
      </tr>
      </logic:notEqual>
      <logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
      <tr>
        <td height="20"><strong>Munic&iacute;pio:<font color="#FF0000">*</font></strong></td>
        <td align="right">
		<div align="left">
			<bean:write name="PesquisarAtualizarSetorComercialActionForm" property="municipioID"/> - 
        	<bean:write name="PesquisarAtualizarSetorComercialActionForm" property="municipioNome"/>
        	<html:hidden name="PesquisarAtualizarSetorComercialActionForm" property="municipioID"/>			
		</div>
	</td>
      </tr>
      </logic:equal>
      <logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
		<tr height="127px;">
			<td valign="top"><strong>Dias de Vencimento:</strong></td>
			<td valign="top">
				<logic:present name="calendarioForms">
					<div id="layerHide" style="float: right; margin-right: 175px;">
					<table class="tabela">
						<tr>
							<td colspan="7" align="left" style="font-size:11px">Escolha, no máximo, <b><bean:write name="PesquisarAtualizarSetorComercialActionForm" property="qtdDiasVencimento"/></b> Dias de Vencimento</td>
						</tr>
						<logic:iterate id="diaCalendario" name="calendarioForms" type="CalendarioForm">
							
							<logic:equal name="diaCalendario" property="novaLinha" value="S">
								<tr>
							</logic:equal>
							
							<logic:equal name="diaCalendario" property="ativo" value="S">
							<script language="JavaScript">
							 	dias[<%=diaCalendario.getDia()%>] = <%=diaCalendario.getDia()%>;
							</script>
							</logic:equal>
							<logic:equal name="diaCalendario" property="quebraLinha" value="S">
									<td>
										<bean:write name="diaCalendario" property="dia"/>
										<html:checkbox property="diasVencimento" value="<%=diaCalendario.getDia()%>" />										
									</td>
								</tr>
							</logic:equal>
							<logic:equal name="diaCalendario" property="quebraLinha" value="N">
								<td>
									<bean:write name="diaCalendario" property="dia"/>
									<html:checkbox property="diasVencimento" value="<%=diaCalendario.getDia()%>" />
								</td>
							</logic:equal>
					</logic:iterate>
					</table>
					</div>
				</logic:present>
			</td>
		</tr>
		<html:hidden property="qtdDiasVencimento" name="PesquisarAtualizarSetorComercialActionForm"/>
		</logic:equal>
	  <tr>
        <td><strong>Indicador de uso:</strong></td>
        <td>
			<html:radio property="indicadorUso" value="1"/><strong>Ativo
			<html:radio property="indicadorUso" value="2"/>Inativo</strong>
		</td>
      </tr>
      <tr>
      	<td><strong> <font color="#FF0000"></font></strong></td>
        <td align="right"><div align="left"><strong><font color="#FF0000">*</font></strong>
                    Campos obrigat&oacute;rios</div></td>
      </tr>
      <tr>
      <html:hidden name="PesquisarAtualizarSetorComercialActionForm" property="setorComercialID" />
      	<td colspan="2" widt="100%">
      		<table width="100%">
				<tr>
					<td> 
		      			<logic:present name="voltar">
							<logic:equal name="voltar" value="filtrar">
								<input name="Button" type="button" class="bottonRightCol"
								value="Voltar" align="left"
								onclick="window.location.href='<html:rewrite page="/exibirAtualizarSetorComercialAction.do?objetoConsulta=1"/>'">
							</logic:equal>
							<logic:equal name="voltar" value="filtrarNovo">
								<input name="Button" type="button" class="bottonRightCol"
								value="Voltar" align="left"
								onclick="window.location.href='<html:rewrite page="/exibirFiltrarSetorComercialAction.do"/>'">
							</logic:equal>
							<logic:equal name="voltar" value="manter">
								<input name="Button" type="button" class="bottonRightCol"
								value="Voltar" align="left"
								onclick="window.history.back();">
							</logic:equal>
						</logic:present>
						<logic:notPresent name="voltar">
							<input name="Button" type="button" class="bottonRightCol"
							value="Voltar" align="left"
							onclick="window.location.href='<html:rewrite page="/exibirAtualizarSetorComercialAction.do"/>'">
						</logic:notPresent>
						<input name="Submit22"
							class="bottonRightCol" value="Desfazer" type="button"
							onclick="window.location.href='/gsan/exibirAtualizarSetorComercialAction.do?setorComercialID=<bean:write name="PesquisarAtualizarSetorComercialActionForm" property="setorComercialID" />';">	
						<input name="Submit23" class="bottonRightCol" value="Cancelar"
							type="button"
							onclick="window.location.href='/gsan/telaPrincipal.do'">
	      			</td>
	      			<td align="right">
	      				<html:button  styleClass="bottonRightCol" value="Atualizar Rotas" property="botaoAdicionar" onclick="javascript:abrirPopup('exibirAtualizarRotaSetorComercialAction.do', 340, 800)"/>
	      			</td>
	      			<td align="right">
	      				<gcom:controleAcessoBotao name="Button" value="Atualizar"
								  onclick="javascript:validarForm(document.forms[0]);" url="atualizarSetorComercialAction.do"/>
		      			<%--<INPUT type="button" class="bottonRightCol" value="Atualizar" tabindex="5" onclick="validarForm(document.forms[0]);" style="width: 70px">--%>
	      			</td>
	      		</tr>
      		</table>
      	</td>
      	</tr>
      	</table>
        <p>&nbsp;</p>
	</td>
  </tr>
</table>

<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>


</body>
</html:html>
