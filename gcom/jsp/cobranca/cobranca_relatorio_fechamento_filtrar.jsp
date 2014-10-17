<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="gcom.util.ConstantesSistema" isELIgnored="false"%>
<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false" formName="FiltrarRelatorioFechamentoCobrancaActionForm"  dynamicJavascript="true" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">

	function validaForm(form){
        var form = document.forms[0];
		var msg = '';
		var dataValida = true;	
		
		if(form.periodoInicio.value == null || form.periodoInicio.value == '') {
			alert('Período inicial deve ser informada.');			
			form.periodoInicio.focus();
			return false;
		}

		if(form.periodoFim.value == null || form.periodoFim.value == '') {
			alert('Período final deve ser informada.');			
			form.periodoFim.focus();
			return false;
		}
		
		if(comparaData(form.periodoInicio.value, '>', form.periodoFim.value)) {
			alert('Período inicial não pode ser maior que o período final.');			
			form.periodoInicio.focus();
			return false;
		}
				
		
		form.target = "_blank";
		form.submit();
		form.target = "";
		
	}
		
		//submeterFormPadrao(form);
	


	function limparForm(form) {
		form.comando[0].checked = false;
		form.comando[1].checked = false;
	    form.acao.value="";
	    form.empresa.value = "";
	    form.periodoInicio.value="";
	    form.periodoFim.value="";
	}

    function limparPeriodo() {
        var form = document.forms[0];
        
        if (form.periodoInicio.value != ''){
			form.periodoInicio.value="";
		}
		
		if (form.periodoFim.value != ''){
			form.periodoFim.value="";
		}			
  	}	

	/* Replica Periodo de Fechamento de Cobrança */
	function replicaPeriodo() {
		var form = document.forms[0];
		form.periodoFim.value = form.periodoInicio.value
	}	

	function limparTitulo(){
		var form = document.forms[0];
		form.idCobrancaAcaoAtividadeComando.value = '';
		form.tituloCobrancaAcaoAtividadeComando.value = '';
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

	function recarregaCampos() {
	
		 var form = document.forms[0];
		 
		 if (form.comando[0].checked == true) {
		 	// Ambos
		 	document.getElementById("comandoEventualTR").style.display = "none";
		 	document.getElementById("comandoCronogramaTR").style.display = "none";
		 } else if (form.comando[1].checked == true) {
			// Cronograma
		 	document.getElementById("comandoEventualTR").style.display = "none";
		 	document.getElementById("comandoCronogramaTR").style.display = "";
		 } else if (form.comando[2].checked == true) {
			// Eventual
			document.getElementById("comandoEventualTR").style.display = "";
		 	document.getElementById("comandoCronogramaTR").style.display = "none";
		 }
	}

</script>

</head>

<body leftmargin="5" topmargin="5">
<html:form action="/gerarRelatorioFechamentoCobrancaAction"
	name="FiltrarRelatorioFechamentoCobrancaActionForm"
	type="gcom.gui.cobranca.FiltrarRelatorioFechamentoCobrancaActionForm"
	method="post"
	onsubmit="return validateFiltrarClienteTipoActionForm(this);">

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
			
			<td width="615" valign="top" class="centercoltext">
				
				<table height="100%">
					<tr>
						<td></td>
					</tr>
				</table>

				<!--Início Tabela Reference a Páginação da Tela de Processo-->
				
				<table>
					<tr>
						<td></td>
					</tr>
				</table>
				
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
						<td class="parabg">Filtrar Relatorio Fechamento da Cobran&ccedil;a</td>
						<td width="11" valign="top"><img border="0" src="imagens/parahead_right.gif" /></td>
					</tr>
	
				</table>
				
				<!--Fim Tabela Reference a Páginação da Tela de Processo-->
				<p>&nbsp;</p>
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
						<td width="90"><strong>Tipo de Comando<font color="red">*</font>:</strong></td>
						<td colspan="2">
							<span class="style2">
								<html:radio property="comando" value="3" onclick="recarregaCampos();"/><strong>Ambos</strong>
								<html:radio property="comando" value="1" onclick="recarregaCampos();"/><strong>Cronograma</strong>
								<html:radio property="comando" value="2" onclick="recarregaCampos();"/><strong>Eventual</strong>
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
						<td colspan="2" align="left"><html:select property="acao">
							<html:option value="-1">&nbsp;</html:option>
							<html:options collection="colecaoCobrancaAcao" labelProperty="descricaoCobrancaAcao" property="id" />
						</html:select></td>
					</tr>
					<tr>
						<td><strong>Empresa:</strong></td>
						<td colspan="2" align="left">
							<html:select property="empresa">
								<html:option value="-1">&nbsp;</html:option>
								<html:options collection="colecaoEmpresa" labelProperty="descricao" property="id" />
							</html:select>
						</td>
					</tr>
					<tr>
						<td><strong>Período Inicio:</strong></td>
						<td colspan="2">
							<html:text maxlength="10" property="periodoInicio" size="10" 
							onkeypress="javascript:return isCampoNumerico(event); " 
							onkeyup="mascaraData(this, event);replicaPeriodo();" />
                   			<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" onclick="abrirCalendarioReplicando('FiltrarRelatorioFechamentoCobrancaActionForm', 'periodoInicio','periodoFim')" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /> dd/mm/aaaa
						</td>
					</tr>
					<tr>
						<td><strong>Período Fim:</strong></td>
						<td colspan="2">
							<html:text maxlength="10" property="periodoFim" size="10" 
							onkeypress="javascript:return isCampoNumerico(event); "
							onkeyup="mascaraData(this, event)" />
                      		<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" onclick="abrirCalendario('FiltrarRelatorioFechamentoCobrancaActionForm', 'periodoFim')" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /> dd/mm/aaaa
						</td>
					</tr>			
					<tr>
						<td>
							<strong> 
								<font color="#FF0000"> 
									<input type="button" name="Submit22" class="bottonRightCol" value="Limpar" onClick="javascript:limparForm(document.forms[0]);">
								</font> 
							</strong>
						</td>
						<td colspan="2" align="right"><input type="button" name="Submit2"
							class="bottonRightCol" value="Gerar Relatorio"
							onclick="validaForm();"></td>
					</tr>
				</table>
				<p>&nbsp;</p>
			</tr>
			<!-- Rodapé -->
			<%@ include file="/jsp/util/rodape.jsp"%>
		</table>
		<p>&nbsp;</p>
	<tr>
	</tr>

</html:form>
</body>
</html:html>