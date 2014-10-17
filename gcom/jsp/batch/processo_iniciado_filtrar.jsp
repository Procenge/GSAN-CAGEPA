<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
<html:html>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="FiltrarProcessoActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript">

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	var form = document.FiltrarProcessoActionForm;
	
	if ('usuario' == tipoConsulta) {
		document.forms[0].idUsuario.value = codigoRegistro;
		document.forms[0].nomeUsuario.value = descricaoRegistro;
 	}
	if (tipoConsulta == 'processo') {
	      form.idProcesso.value = codigoRegistro;
	      form.descricaoProcesso.value = descricaoRegistro;
	      form.descricaoProcesso.style.color = "#000000";
	}
}

function validarDatas(dataInicial, dataFinal, nomeCampo) {
	//Verificando as datas iniciais e finais.
	if(dataInicial != '' && dataFinal != '') {
		if(comparaDatas(dataInicial, '<', dataFinal) == false) {
			alert('Data inicial não pode ser maior que a data final.('+ nomeCampo +')');
			dataInicial.focus();
			return false;	
		}		
	}
}

function validarHoras(dataInicial, dataFinal, horaInicial, horaFinal, nomeCampo){
	
	if(((dataInicial != '' && dataFinal != '') && (dataInicial == dataFinal)) || (dataInicial == '' && dataFinal == '')) {			
		var hmsInicial = horaInicial.split(':');		
		var hmsFinal = horaFinal.split(':');
		
		if ((hmsInicial[0] > hmsFinal[0]) || 
				(hmsInicial[0] == hmsFinal[0] && hmsInicial[1] > hmsFinal[1]) || 
				(hmsInicial[1] == hmsFinal[1] && hmsInicial[2] > hmsFinal[2])){
			
				alert('Hora inicial não pode ser maior que a hora final.('+ nomeCampo +')');				
				horaInicial.focus();
				return false;
		}
	}
}

function validarForm(){

	var form = document.forms[0];
	
	// Criando variáveis para as datas.
	var dataAgendamentoInicial = form.dataAgendamentoInicial.value;
	var dataAgendamentoFinal = form.dataAgendamentoFinal.value;
	var dataInicioInicial = form.dataPeriodoInicioInicial.value;
	var dataInicioFinal = form.dataPeriodoInicioFinal.value;
	var dataConclusaoInicial = form.dataConclusaoInicial.value;
	var dataConclusaoFinal = form.dataConclusaoFinal.value;
	var dataComandoInicial = form.dataComandoInicial.value;
	var dataComandoFinal = form.dataComandoFinal.value;

	// Criando variáveis para horas.
	var horaAgendamentoInicial = form.horaAgendamentoInicial.value;
	var horaAgendamentoFinal = form.horaAgendamentoFinal.value;
	var horaPeriodoInicioInicial = form.horaPeriodoInicioInicial.value;
	var horaPeriodoInicioFinal = form.horaPeriodoInicioFinal.value;
	var horaConclusaoInicial = form.horaConclusaoInicial.value;
	var horaConclusaoFinal = form.horaConclusaoFinal.value;
	var horaComandoInicial= form.horaComandoInicial.value;
	var horaComandoFinal= form.horaComandoFinal.value;

	// Valida as datas.
	validarDatas(dataAgendamentoInicial, dataAgendamentoFinal, "Período de agendamento");
	validarDatas(dataInicioInicial, dataInicioFinal, "Período de inicio");
	validarDatas(dataConclusaoInicial, dataConclusaoFinal, "Período de conclusão");
	validarDatas(dataComandoInicial, dataComandoFinal, "Período de comando");

	// Valida as horas.
	validarHoras(dataAgendamentoInicial, dataAgendamentoFinal, horaAgendamentoInicial, horaAgendamentoFinal, 'Período de agendamento.')
	validarHoras(dataInicioInicial, dataInicioFinal, horaPeriodoInicioInicial, horaPeriodoInicioFinal, 'Período de início.')
	validarHoras(dataConclusaoInicial, dataConclusaoFinal, horaConclusaoInicial, horaConclusaoFinal, 'Período de conclusão.')
	validarHoras(dataComandoInicial, dataComandoFinal, horaComandoInicial, horaComandoFinal, 'Período de comando.')
	
	form.submit();
}

function limparUsuario() {
	document.forms[0].nomeUsuario.value = '';
	document.forms[0].idUsuario.value = '';
	document.forms[0].usuarioLimpo.value='1';                  
}

function limparProcesso() {
	
	document.forms[0].idProcesso.value = "";
	document.forms[0].descricaoProcesso.value = "";

}

</script>
</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');">
<html:form action="/filtrarProcessoAction.do"
	name="FiltrarProcessoActionForm"
	type="gcom.gui.batch.FiltrarProcessoActionForm" method="post"
	onsubmit="return validateFiltrarProcessoActionForm(this);">

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

			<td valign="top" class="centercoltext">

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
					<td class="parabg">Filtrar Processo Iniciado</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>

				</tr>
			</table>

			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="3">Para consultar processos iniciados, informe os
					dados abaixo:</td>
					<td width="227">&nbsp;</td>
				</tr>
				<tr>
					<td height="31"><strong>Processo:</strong></td>
					<td colspan="3"><html:text property="idProcesso" size="5"
						maxlength="5"
						onkeypress="javascript:return validaEnter(event, 'exibirFiltrarProcessoAction.do', 'idProcesso');" 
						onblur="javascript:verificaNumero(this);"/>
					
					<img src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						width="23" height="21"
						onclick="javascript:abrirPopup('exibirPesquisarProcessoAction.do?limparForm=OK&tipoConsulta=processo');"> 
					
					<span class="style1">
					<logic:present name="idProcessoNaoEncontrado">
						<html:text property="descricaoProcesso" size="30" maxlength="30"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: red" />
					</logic:present> <logic:notPresent name="idProcessoNaoEncontrado">

						<html:text property="descricaoProcesso" size="30" maxlength="30"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000" />

					</logic:notPresent> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						onclick="javascript:limparProcesso();"> </span></td>
				</tr>
				<tr>
					<td><strong>Tipo do Processo:</strong></td>
					<td>
						<html:select property="idTipoProcesso">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<html:options collection="collectionTipoProcesso" labelProperty="descricao" property="id" />
						</html:select>
					</td>
				</tr>
				<tr>
					<td><strong>Codigo do Grupo:</strong></td>
					<td>
						<html:select property="codigoGrupoProcesso">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						  <!--optgroup label="Grupo Cobrança"-->
							<!--html:options collection="collectionGrupoCobranca" labelProperty="descricao" property="id" /-->
						  <!--/optgroup-->
						  <!--optgroup label="Grupo Faturamento"-->
							<html:options collection="collectionGrupoFaturamento" labelProperty="descricao" property="id" />
						  <!--/optgroup-->
						</html:select>
					</td>
				</tr>
				<tr>
					<td width="135"><strong>Situa&ccedil;&atilde;o do Processo:</strong></td>
					<td colspan="3"><html:select property="idSituacaoProcesso">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoProcessoSituacao"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				<tr> 
                  <td height="31"><strong>Usuário:</strong></td>
                  <td colspan="3">
				    <input type="hidden" name="usuarioLimpo" value="0">
					<html:text maxlength="11" property="idUsuario" style="text-transform: none;" size="11" 
					onkeypress="javascript:return validaEnter(event, 'exibirFiltrarProcessoAction.do', 'idUsuario');"
					onblur="javascript:verificaNumero(this);"/>
                    <img onclick="abrirPopup('exibirUsuarioPesquisar.do', 250, 495);" width="23" height="21" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/>
   		      		<logic:equal property="usuarioNaoEncontrada" name="FiltrarProcessoActionForm" value="true">
<%--   <!--						<input type="text" name="nomeUsuario" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" value="<bean:message key=""/>"/>--> --%>
						<html:text property="nomeUsuario" size="40" maxlength="50" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
                    </logic:equal>                    
   		      		<logic:equal property="usuarioNaoEncontrada" name="FiltrarProcessoActionForm" value="false">
   		      			<html:text property="nomeUsuario" size="40" maxlength="50" readonly="true" style="border: 0pt none ; background-color: rgb(239, 239, 239);" />						
                    </logic:equal>
                    
                    <a href="javascript:limparUsuario();">
	                  <img  border="0" 
	                    	src="<bean:message key="caminho.imagens"/>limparcampo.gif" height="21" width="23"                    	 
	                    />
                    </a>
                    </td>
                </tr>
                </table>
                <table>
				<tr>
					<td rowspan="2"><strong>Per&iacute;odo de Agendamento:</strong></td>
					<td><strong>Inicial:</strong></td>
					<td width="189"><strong> </strong> <html:text 
						onkeyup="javascript:mascara_data(this);"
						onblur="javascript:verifica_tamanho_data(this);"
						property="dataAgendamentoInicial" size="10" maxlength="10" /> <a
						href="javascript:abrirCalendario('FiltrarProcessoActionForm', 'dataAgendamentoInicial')">
					<img border="0" width="16" height="15"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
					(dd/mm/aaaa)</td>
					<td><html:text property="horaAgendamentoInicial" size="8" 
						value="00:00:00" maxlength="8"
						onkeyup="mascaraHoraMinutoSegundo(this, event);" 
						onblur="javascript:verifica_tamanho_hora(this);"/> (hh:mm:ss)</td>
				</tr>
				<tr>
					<td><strong>Final:</strong></td>
					<td><strong> </strong> <html:text property="dataAgendamentoFinal" 
						onkeyup="javascript:mascara_data(this);"
						onblur="javascript:verifica_tamanho_data(this);"
						size="10" maxlength="10" /> <a
						href="javascript:abrirCalendario('FiltrarProcessoActionForm', 'dataAgendamentoFinal')">
					<img border="0" width="16" height="15"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
					(dd/mm/aaaa)</td>
					<td><html:text property="horaAgendamentoFinal" size="8" 
						value="23:59:59" maxlength="8"
						onkeyup="mascaraHoraMinutoSegundo(this, event);" 
						onblur="javascript:verifica_tamanho_hora(this);"/> (hh:mm:ss)</td>
				</tr>
				<tr>
					<td rowspan="2"><strong>Per&iacute;odo de In&iacute;cio:</strong></td>
					<td><strong>Inicial:</strong></td>
					<td><strong> </strong> <html:text onkeyup="javascript:mascara_data(this);" 
						onblur="javascript:verifica_tamanho_data(this);"
						property="dataPeriodoInicioInicial" size="10" maxlength="10" /> <a
						href="javascript:abrirCalendario('FiltrarProcessoActionForm', 'dataPeriodoInicioInicial')">
					<img border="0" width="16" height="15"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
					(dd/mm/aaaa)</td>
					<td><html:text property="horaPeriodoInicioInicial" size="8"
						value="00:00:00" maxlength="8"
						onkeyup="mascaraHoraMinutoSegundo(this, event);" 
						onblur="javascript:verifica_tamanho_hora(this);"/> (hh:mm:ss)</td>
				</tr>
				<tr>
					<td><strong>Final:</strong></td>
					<td><strong> </strong> <html:text property="dataPeriodoInicioFinal" onkeyup="javascript:mascara_data(this);"
						onblur="javascript:verifica_tamanho_data(this);"
						size="10" maxlength="10" /> <a
						href="javascript:abrirCalendario('FiltrarProcessoActionForm', 'dataPeriodoInicioFinal')">
					<img border="0" width="16" height="15"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
					(dd/mm/aaaa)</td>
					<td><html:text property="horaPeriodoInicioFinal" size="8"
						value="23:59:59" maxlength="8"
						onkeyup="mascaraHoraMinutoSegundo(this, event);" 
						onblur="javascript:verifica_tamanho_hora(this);"/> (hh:mm:ss)</td>
				</tr>
				<tr>
					<td rowspan="2"><strong>Per&iacute;odo de Conclus&atilde;o:</strong></td>
					<td><strong>Inicial:</strong></td>
					<td><strong> </strong> <html:text property="dataConclusaoInicial" onkeyup="javascript:mascara_data(this);"
						onblur="javascript:verifica_tamanho_data(this);"
						size="10" maxlength="10" /> <a
						href="javascript:abrirCalendario('FiltrarProcessoActionForm', 'dataConclusaoInicial')">
					<img border="0" width="16" height="15"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
					(dd/mm/aaaa)</td>
					<td><html:text property="horaConclusaoInicial" size="8"
						value="00:00:00" maxlength="8"
						onkeyup="mascaraHoraMinutoSegundo(this, event);" 
						onblur="javascript:verifica_tamanho_hora(this);"/> (hh:mm:ss)</td>
				</tr>
				<tr>
					<td><strong>Final:</strong></td>
					<td><strong> </strong> <html:text property="dataConclusaoFinal" onkeyup="javascript:mascara_data(this);"
						onblur="javascript:verifica_tamanho_data(this);"
						size="10" maxlength="10" /> <a
						href="javascript:abrirCalendario('FiltrarProcessoActionForm', 'dataConclusaoFinal')">
					<img border="0" width="16" height="15"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
					(dd/mm/aaaa)</td>
					<td><html:text property="horaConclusaoFinal" size="8" maxlength="8"
						value="23:59:59" 
						onkeyup="mascaraHoraMinutoSegundo(this, event);" 
						onblur="javascript:verifica_tamanho_hora(this);"/>
					(hh:mm:ss)</td>
				</tr>
				<tr>
					<td rowspan="2"><strong>Per&iacute;odo de Comando:</strong></td>
					<td><strong>Inicial: </strong></td>
					<td><strong> </strong> <html:text property="dataComandoInicial" onkeyup="javascript:mascara_data(this);"
						onblur="javascript:verifica_tamanho_data(this);"
						size="10" maxlength="10" /> <a
						href="javascript:abrirCalendario('FiltrarProcessoActionForm', 'dataComandoInicial')">
					<img border="0" width="16" height="15"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
					(dd/mm/aaaa)</td>
					<td><html:text property="horaComandoInicial" size="8" maxlength="8"
						value="00:00:00" onkeyup="mascaraHoraMinutoSegundo(this, event);" 
						onblur="javascript:verifica_tamanho_hora(this);"/>
					(hh:mm:ss)</td>
				</tr>
				<tr>
					<td><strong>Final:</strong></td>
					<td><strong> </strong> <html:text property="dataComandoFinal" onkeyup="javascript:mascara_data(this);"
						onblur="javascript:verifica_tamanho_data(this);"
						size="10" maxlength="10" /> <a
						href="javascript:abrirCalendario('FiltrarProcessoActionForm', 'dataComandoFinal')">
					<img border="0" width="16" height="15"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
					(dd/mm/aaaa)</td>
					<td><html:text property="horaComandoFinal" size="8" maxlength="8"
						value="23:59:59" onkeyup="mascaraHoraMinutoSegundo(this, event);" 
						onblur="javascript:verifica_tamanho_hora(this);"/>
					(hh:mm:ss)</td>
				</tr>
				<% if(request.getAttribute("exibirIpProcesso").equals("1")) { %>
				    <tr>
                        <td colspan="3"><strong>Filtrar processos que foram iniciados pelo IP <%=request.getAttribute("ipLocal")%>:</strong></td>
                        <td colspan="1">
                            <html:radio property="filtrarPorIP" value="1"/>Sim 
                            <html:radio property="filtrarPorIP" value="2"/>Não
                        </td>
				    </tr>
				<% } %>
				<tr>
					<td colspan="4">
					<p>&nbsp;</p>
					</td>
				</tr>
				<tr>
					<td>
				<%--	<div align="left"><input type="button" name="limpar"
						class="bottonRightCol" value="Limpar"
						onclick="limparCampos();"></div> --%>

					<div align="left"><input type="reset" name="limpar"
						class="bottonRightCol" value="Limpar"
						onclick="window.location.href='/gsan/exibirFiltrarProcessoAction.do?limpar=true'"></div>

					</td>
					<td width="52">&nbsp;</td>
					<td colspan="2">
					<div align="right"><input type="button" name="filtrar"
						class="bottonRightCol" value="Filtrar"
						onclick="javascript:validarForm();"></div>
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
