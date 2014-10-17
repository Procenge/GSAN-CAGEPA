<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="gcom.util.ConstantesSistema"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ include file="/jsp/util/titulo.jsp"%>
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script>
  $(document).ready(
	function(){
		limparFormulario();
		$("input:radio[name=tipoComando][value=3]").attr("checked","true");
	}
   );
</script>

</head>
<body>
<html:form action="/acompanhamentoAcoesAction" name="acompanhamentoAcoesForm" type="org.apache.struts.action.DynaActionForm">
<html:hidden property="acao" value="gerarRelatorioAcompanhamentoAcoes"/>
<table width="100%" border="0">
					<tr>
						<td width="100%" colspan=3>
							<table width="100%" border="0">
								<tr>
									<td>Para filtrar os dados do Relatório de Fechamento da Cobran&ccedil;a, informe os dados abaixo:</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td width="90"><strong>Tipo de Comando</td>
						<td colspan="2">
							<span class="style2">
								<html:radio property="tipoComando" value="3" onclick="recarregaCampos();"/><strong>Ambos</strong>
								<html:radio property="tipoComando" value="1" onclick="recarregaCampos();"/><strong>Cronograma</strong>
								<html:radio property="tipoComando" value="2" onclick="recarregaCampos();"/><strong>Eventual</strong>
	 						</span>
	 					</td>
					</tr>
					<tr id="comandoEventualTR" style="display: none;">
						<td><strong>Comando Eventual:</strong></td>
        				<td colspan="2">
           					<html:hidden property="idCobrancaAcaoAtividadeComando"/>
        					<html:text property="tituloCobrancaAcaoAtividadeComando" size="35" maxlength="52" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"/>
							<a href="javascript:abrirPopup('exibirPesquisarComandoAcaoCobrancaAction.do?limparForm=OK', 400, 750);" title="Pesquisar">
								<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23" height="21" border="0"></a>
							<a href="javascript:limparTitulo();">
								<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"	border="0" title="Apagar" /></a>
					    </td>
					</tr>
					<tr id="comandoCronogramaTR" style="display: none;">
						<td><Strong>Comando Cronograma:</Strong></td>
						<td colspan="2">
							<html:hidden property="idCobrancaAcaoAtividadeCronograma"/>
							<html:text property="descricaoGrupo" size="20" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>&nbsp;
							<a href="javascript:abrirPopup('exibirPesquisarCronogramaAcaoCobrancaAction.do', 400, 750);" title="Pesquisar">
								<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23" height="21" border="0"></a>
							<a href="javascript:limparGrupo();">
								<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"	border="0" title="Apagar" /></a>
						</td>
					</tr>
					<tr>
						<td><strong>Ação:</strong></td>
						<td colspan="2" align="left"><html:select property="acaoSelecionada">
							<html:option value="-1">&nbsp;</html:option>
							<html:options collection="colecaoCobrancaAcao" labelProperty="descricaoCobrancaAcao" property="id" />
						</html:select></td>
					</tr>
					<tr>
						<td><strong>Empresa<font color="red">*</font>:</strong>:</strong></td>
						<td colspan="2" align="left">
							<html:select property="empresaSelecionada">
								<html:option value="-1">&nbsp;</html:option>
								<html:options collection="colecaoEmpresa" labelProperty="descricao" property="id" />
							</html:select>
						</td>
					</tr>
					<tr id="calendarioInicio">
						<td><strong>Período Inicio:<font color="red">*</font>:</strong></strong></td>
						<td>
							<html:text maxlength="10" property="periodoInicio" size="10" onkeypress="return isCampoNumerico(event);" onkeyup="javascript:mascaraData(this,event);"/>
                   			<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" onclick="abrirCalendario('acompanhamentoAcoesForm', 'periodoInicio')" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /> dd/mm/aaaa
						</td>
					</tr>
					<tr id="calendarioFim">
						<td><strong>Período Fim:<font color="red">*</font>:</strong></strong></td>
						<td colspan="2">
							<html:text maxlength="10" property="periodoFim" size="10" onkeypress="return isCampoNumerico(event);" onkeyup="javascript:mascaraData(this,event);"/>
                      		<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" onclick="abrirCalendario('acompanhamentoAcoesForm', 'periodoFim')" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /> dd/mm/aaaa
						</td>
					</tr>
				<tr>
				 <td>
					<input type="button" name="Submit2" class="bottonRightCol" value="Desfazer" onClick="limparFormulario();">
					<input type="button" name="Submit3" class="bottonRightCol" value="Cancelar" onclick="window.location.href='/gsan/telaPrincipal.do'">
				 </td>
				 <td align="right">
				     <input type="submit" name="Submit" class="bottonRightCol" value="Gerar Relatório" onclick="return validarCamposRequired();">
				 </td>
				</tr>
</table>
</html:form>
</body>
</html>

<script>
function cancelar(){
	limparFormulario();
	window.location.href='/gsan/telaPrincipal.do';
}

function validarCamposRequired(){

	var empresaSelecionada = $("select[name=empresaSelecionada]").val();
	var periodoInicio = $("input[name=periodoInicio]").val();
	var periodoFim = $("input[name=periodoFim]").val();
	
	if(empresaSelecionada == "-1"){
		alert("Selecione uma empresa");
		return false;
	}else if($.trim(periodoInicio)== ""){
		alert("Informe um perídoo início");
		return false;
	}else if($.trim(periodoFim)== ""){
		alert("Informe um período fim");
		return false;
	}

	return true;
}

function recarregaCampos() {
	
	 var form = document.forms[0];
	 
	 if (form.tipoComando[0].checked == true) {
	 	// Ambos
	 	document.getElementById("comandoEventualTR").style.display = "none";
	 	document.getElementById("comandoCronogramaTR").style.display = "none";
	 } else if (form.tipoComando[1].checked == true) {
		// Cronograma
	 	document.getElementById("comandoEventualTR").style.display = "none";
	 	document.getElementById("comandoCronogramaTR").style.display = "";
	 } else if (form.tipoComando[2].checked == true) {
		// Eventual
		document.getElementById("comandoEventualTR").style.display = "";
	 	document.getElementById("comandoCronogramaTR").style.display = "none";
	 }
}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.forms[0];
    
    if (tipoConsulta == 'cobrancaAcaoAtividadeComando') {
      form.idCobrancaAcaoAtividadeComando.value = codigoRegistro;
      form.tituloCobrancaAcaoAtividadeComando.value = descricaoRegistro;
    }
    if (tipoConsulta == 'cobrancaAcaoAtividadeCronograma'){
		form.idCobrancaAcaoAtividadeCronograma.value = codigoRegistro;
		form.descricaoGrupo.value = descricaoRegistro;
    }
}

function limparFormulario(){
	$("input:radio[value=3]").attr("checked","true");
	$("input[name=periodoInicio]").val("");
	$("input[name=periodoFim]").val("");
	$("select[name=acaoSelecionada]>option[value=-1]").attr("selected","selected");
	$("select[name=empresaSelecionada]>option[value=-1]").attr("selected","selected");
	$("input[name=periodoInicio]").val("");
	$("input[name=idCobrancaAcaoAtividadeCronograma]").val("");
	$("input[name=descricaoGrupo]").val("");
	$("input[name=idCobrancaAcaoAtividadeComando]").val("");
	$("input[name=tituloCobrancaAcaoAtividadeComando]").val("");
	recarregaCampos();
}

</script>