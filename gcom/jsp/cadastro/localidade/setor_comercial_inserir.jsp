<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>
<%@ page import="gcom.util.CalendarioForm"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>


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
function limparLocalidade() {
    var form = document.PesquisarAtualizarSetorComercialActionForm;
    form.localidadeID.value = "";
    form.localidadeNome.value = "";
    form.setorComercialCD.value = "";
    form.municipioID.value = "";
    form.municipioNome.value = "";
}

function limparMunicipio() {
    var form = document.PesquisarAtualizarSetorComercialActionForm;
    form.municipioID.value = "";
    form.municipioNome.value = "";
}

function limpar(){
	var form = document.PesquisarAtualizarSetorComercialActionForm;
	form.localidadeNome.value = "";
    form.setorComercialCD.value = "";
    form.municipioID.value = "";
    form.municipioNome.value = "";
}

function validarForm(formulario)
{
	if (validatePesquisarAtualizarSetorComercialActionForm(formulario)){
		submeterFormPadrao(formulario);
	}
}

function validarLocalidade(){

	var form = document.forms[0];
	
	if(form.localidadeID.value == ""){
		alert("Informe Localidade");
		form.localidadeID.focus();
	}else{
		abrirPopup('exibirPesquisarSetorComercialAction.do?consulta=sim&idLocalidade='+document.forms[0].localidadeID.value);
	}
}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

	var form = document.forms[0];

	if (tipoConsulta == 'municipio') {
	  form.municipioID.value = codigoRegistro;
	  form.municipioNome.value = descricaoRegistro;
	  form.municipioNome.style.color = "#000000";
	}

	if (tipoConsulta == 'localidade') {
      form.localidadeID.value = codigoRegistro;
      form.localidadeNome.value = descricaoRegistro;
	  form.localidadeNome.style.color = "#000000";
	  urlRedirect = "/gsan/exibirInserirSetorComercialAction.do?objetoConsulta=1";
	  redirecionarSubmit(urlRedirect);

	}

}

function redirecionarSubmitAtualizar(idSetorComercial) {
	urlRedirect = "/gsan/exibirAtualizarSetorComercialAction.do?setorComercialID=" + idSetorComercial;
	redirecionarSubmit(urlRedirect);
}
//-->
</SCRIPT>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="PesquisarAtualizarSetorComercialActionForm" />


</head>

<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/inserirSetorComercialAction" method="post">


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

			<td width="625" valign="top" class="centercoltext">

			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Inserir Setor Comercial</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para adicionar o setor comercial, informe os dados
					abaixo:</td>
				<td align="right"></td>
    			</tr>
    		</table>
    
    		<table width="100%" border="0">
				<tr>
					<td width="183"><strong>Localidade:<font color="#FF0000">*</font></strong></td>
					<td width="432"><html:text property="localidadeID" size="3"
						maxlength="3" tabindex="1"
						onkeypress="limpar();validaEnterComMensagem(event, 'exibirInserirSetorComercialAction.do?objetoConsulta=1', 'localidadeID', 'Localidade');" />
					<a
						href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do', 250, 495);"><img
						src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"
						width="23" height="21" title="Pesquisar Localidade"></a> <logic:present
						name="corLocalidade">
						<logic:equal name="corLocalidade" value="exception">
							<html:text property="localidadeNome" size="45" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>

						<logic:notEqual name="corLocalidade" value="exception">
							<html:text property="localidadeNome" size="45" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
						<a href="javascript:limparLocalidade();"> <img
							src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" title="Apagar" /> </a>
					</logic:present> <logic:notPresent name="corLocalidade">

						<logic:empty name="PesquisarAtualizarSetorComercialActionForm"
							property="localidadeID">
							<html:text property="localidadeNome" value="" size="45"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="PesquisarAtualizarSetorComercialActionForm"
							property="localidadeID">
							<html:text property="localidadeNome" size="45" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
						<a href="javascript:limparLocalidade();"> <img
							src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" title="Apagar" /> </a>
					</logic:notPresent></td>
				</tr>
				<tr>
					<td><strong>C&oacute;digo do Setor Comercial:<font color="#FF0000">*</font></strong></td>
					<td><html:text maxlength="3" property="setorComercialCD" size="3"
						tabindex="2" />&nbsp;<a
						href="javascript:validarLocalidade();"><img
						width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Setor Comercial" /></a></td>
				</tr>
				<tr>
					<td><strong>Nome do Setor Comercial:<font color="#FF0000">*</font></strong></td>
					<td align="right">
					<div align="left"><html:text maxlength="30"
						property="setorComercialNome" size="45" tabindex="3" /></div>
					</td>
				</tr>
				
				<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">
				<tr>
					<td><strong>Munic&iacute;pio:<font color="#FF0000">*</font></strong></td>
					<td align="right">
					<div align="left"><html:text property="municipioID" size="5"
						maxlength="4" tabindex="4"
						onkeypress="validaEnterComMensagem(event, 'exibirInserirSetorComercialAction.do?objetoConsulta=2', 'municipioID', 'Localidade');" />
					<a
						href="javascript:abrirPopup('exibirPesquisarMunicipioAction.do', 250, 495);"><img
						src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"
						width="23" height="21" title="Pesquisar Município"></a> <logic:present
						name="corMunicipio">

						<logic:equal name="corMunicipio" value="exception">
							<html:text property="municipioNome" size="45" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>

						<logic:notEqual name="corMunicipio" value="exception">
							<html:text property="municipioNome" size="45" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
						<a href="javascript:limparMunicipio();"> <img
							src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" title="Apagar" /> </a>
					</logic:present> <logic:notPresent name="corMunicipio">

						<logic:empty name="PesquisarAtualizarSetorComercialActionForm"
							property="municipioID">
							<html:text property="municipioNome" size="45" value=""
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="PesquisarAtualizarSetorComercialActionForm"
							property="municipioID">
							<html:text property="municipioNome" size="45" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
						<a href="javascript:limparMunicipio();"> <img
							src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" title="Apagar" /> </a>
					</logic:notPresent></div>
					</td>
				</tr>
				</logic:equal>
				
				<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
				<tr>
					<td><strong>Munic&iacute;pio:<font color="#FF0000">*</font></strong></td>
					<td align="right">
					<div align="left"><html:text property="municipioID" size="5"
						maxlength="4" tabindex="4" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"
						onkeypress="validaEnterComMensagem(event, 'exibirInserirSetorComercialAction.do?objetoConsulta=2', 'municipioID', 'Localidade');" />
					 <logic:present	name="corMunicipio">
						<logic:equal name="corMunicipio" value="exception">
							<html:text property="municipioNome" size="45" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>

						<logic:notEqual name="corMunicipio" value="exception">
							<html:text property="municipioNome" size="45" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> 
					<logic:notPresent name="corMunicipio">
						<logic:empty name="PesquisarAtualizarSetorComercialActionForm"
							property="municipioID">
							<html:text property="municipioNome" size="45" value=""
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="PesquisarAtualizarSetorComercialActionForm"
							property="municipioID">
							<html:text property="municipioNome" size="45" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent></div>
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
									
									<logic:equal name="diaCalendario" property="quebraLinha" value="S">
											<td>
												<bean:write name="diaCalendario" property="dia"/>
												<%if(diaCalendario.getDia() != null && !diaCalendario.getDia().equals("")){%>
													<html:checkbox property="diasVencimento" value="<%=diaCalendario.getDia()%>" />
												<%}%>
											</td>
										</tr>
									</logic:equal>
									<logic:equal name="diaCalendario" property="quebraLinha" value="N">
										<td>
											<bean:write name="diaCalendario" property="dia"/>
											<%if(diaCalendario.getDia() != null && !diaCalendario.getDia().equals("")){%>
												<html:checkbox property="diasVencimento" value="<%=diaCalendario.getDia()%>" />
											<%}%>
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
					<td>&nbsp;</td>
					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>
				<tr>
					<td><input type="button" name="Button" class="bottonRightCol"
						value="Desfazer" tabindex="6"
						onClick="javascript:window.location.href='/gsan/exibirInserirSetorComercialAction.do?menu=sim'"
						style="width: 80px" />&nbsp; <input type="button" name="Button"
						class="bottonRightCol" value="Cancelar" tabindex="6"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
						style="width: 80px" /></td>
					<td align="right"><%-- 
					<input type="button" name="botaoInserir"
						class="bottonRightCol" value="Inserir" tabindex="6"
						style="width: 80px"
						onClick="javascript:validarForm(document.forms[0])" />
					--%> <gcom:controleAcessoBotao name="Button" value="Inserir"
						onclick="javascript:validarForm(document.forms[0]);"
						url="inserirSetorComercialAction.do" /></td>
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
