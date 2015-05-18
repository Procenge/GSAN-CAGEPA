<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

<%@ page import="gcom.faturamento.*,gcom.util.Util"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
<html:html>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>	
<html:javascript staticJavascript="false"  formName="InserirProcessoActionForm" />	
	
<script language="JavaScript">
function facilitadorPrimeiro(nome){
	
	var form = document.forms[0];
	
	if (form.elements.checadoPrimeiro.value == "0" || form.elements.checadoPrimeiro == undefined){

		
		form.elements.checadoPrimeiro.value = "1";
		marcarTodos(nome);
		
	} else{
		
		
		form.elements.checadoPrimeiro.value = "0";
		desmarcarTodos(nome);
	}
}

function facilitadorSegundo(nome){
	
	var form = document.forms[0];
	
	if (form.elements.checadoSegundo.value == "0" || form.elements.checadoSegundo == undefined){

		
		form.elements.checadoSegundo.value = "1";
		marcarTodos(nome);
		
	} else{
		
		
		form.elements.checadoSegundo.value = "0";
		desmarcarTodos(nome);
	}
}

function marcarTodos(nome){

	
	for (var i=0;i < document.forms[0].elements.length;i++){
		
		var elemento = document.forms[0].elements[i];
		if (elemento.type == "checkbox" && elemento.name == nome && elemento.value != '-1'){
			elemento.checked = true;
		}
	}
}

function desmarcarTodos(nome) {
	
	
	for (var i=0;i < document.forms[0].elements.length;i++){
		var elemento = document.forms[0].elements[i];
		if (elemento.type == "checkbox" && elemento.name == nome){
			elemento.checked = false;
		}
	}
}


function verificarSubmit(check1, check2) {

	var form = document.forms[0];
	if(verificarMarcados(check1) || verificarMarcados(check2)){
		
		if(validateInserirProcessoActionForm(form) && validarHoraCompleta(form.horaAgendamento.value)){
			
			if(confirm('Confirma Início do Processo?')) {
			    
				form.submit();
			    return true;
		    }
		}
		
		return false;
	}else{
		alert('Selecione as atividades do comando de faturamento para execução.');
		return false;		
	}
}

function verificarMarcados(checkbox) {
	
	if (checkbox != undefined) {
		
		if (checkbox.length == undefined) {
			if (checkbox.checked) {
				return true;
			}
			
		} else {
			for (var i=0;i < checkbox.length;i++){
					if (checkbox[i].checked) {
						return true;
					}
			}	
		}
	
		return false;
	}
}


</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');">
	
<html:form action="/inserirProcessoFaturamentoComandadoAction.do" 
	name="InserirProcessoActionForm"
	type="gcom.gui.batch.InserirProcessoActionForm" 
	method="post">
	
<input type="hidden" name="caminhoReload" value="/gsan/exibirInserirProcessoFaturamentoComandadoAction.do" /> 
<input type="hidden" name="checadoPrimeiro" value="0" />
<input type="hidden" name="checadoSegundo" value="0" />

<%@ include file="/jsp/util/cabecalho.jsp"%> 
<%@ include file="/jsp/util/menu.jsp"%>

<table width="770" border="0" cellspacing="4" cellpadding="0">
	<tr>
		<td width="149" valign="top" class="leftcoltext">

		<div align="center">

		<%@ include file="/jsp/util/informacoes_usuario.jsp"%>


		<%@ include file="/jsp/util/mensagens.jsp"%>

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
				<td class="parabg">Iniciar Processo de Faturamento</td>
				<td width="11"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>

			</tr>
		</table>
		<p>&nbsp;</p>
		<table width="100%" border="0" align="center">
			<tr>
				<td height="0"><strong>Data e Hora Corrente:</strong></td>
				<td><input type="text" name="dataAtualServidor" size="15" maxlength="15"
					tabindex="8" value="${requestScope.dataAtualServidor}" disabled="disabled" />
				(hh:mm:ss)</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td height="0"><strong>Data do Agendamento:</strong></td>
				<td><input type="text" name="dataAgendamento" size="10" maxlength="10"
					tabindex="3" onkeyup="javascript:mascaraData(this, event);" /> <a
					href="javascript:abrirCalendario('InserirProcessoActionForm', 'dataAgendamento')">
				<img border="0"
					src="<bean:message key="caminho.imagens"/>calendario.gif"
					width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
				<font size="2">(dd/mm/aaaa)</font></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td height="0"><strong>Hora do Agendamento:</strong></td>
				<td><input type="text" name="horaAgendamento" size="8" maxlength="8"
					tabindex="4" onkeyup="mascaraHoraMinutoSegundo(this, event);" />
				(hh:mm:ss)</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<p>&nbsp;</p>
		<table width="100%" border="0">
			<tr>
				<td><strong> Comandos de cronograma de faturamento:</strong></td>
			</tr>
		</table>
		<table width="100%" border="0" bgcolor="#99CCFF">
			<tr bgcolor="79BBFD">

				<td align="center" width="5%" rowspan="2">
					<strong><a href="javascript:facilitadorPrimeiro('idFaturamentoAtividadeCronograma');" id="0">Todos</a></strong>
				</td>

				<td align="center" width="10%" rowspan="2"><strong>Grupo</strong></td>
				<td align="center" width="10%" rowspan="2"><strong>Mês/Ano</strong></td>
				<td align="center" width="40%" rowspan="2"><strong>Atividade</strong></td>
				<td align="center" width="15%" rowspan="2"><strong>Data Prevista</strong></td>
				<td align="center" width="20%" colspan="2"><strong>Comando</strong></td>

			</tr>

			<tr>
				<td align="center" width="50%" bgcolor="#cbe5fe"><FONT
					COLOR="#000000"><strong>Data</strong></FONT></td>
				<td align="center" width="50%" bgcolor="#cbe5fe"><FONT
					COLOR="#000000"><strong>Hora</strong></FONT></td>
			</tr>
			<%--Esquema de paginação--%>
			<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
				export="currentPageNumber=pageNumber;pageOffset" maxPageItems="10"
				items="${sessionScope.totalRegistros}">
				<pg:param name="pg" />
				<pg:param name="q" />

				<%int cont = 0;%>
				<logic:iterate name="colecaoFaturamentoAtividadeCronograma"
					id="faturamentoAtividadeCronograma"
					type="gcom.faturamento.FaturamentoAtividadeCronograma">
					<pg:item>

						<%cont = cont + 1;
							if (cont % 2 == 0) {%>
						<tr bgcolor="#cbe5fe">
							<%} else {%>
						<tr bgcolor="#FFFFFF">
							<%}%>


							<td align="center" width="5%">
								<div align="center"><input type="checkbox" name="idFaturamentoAtividadeCronograma" value="${faturamentoAtividadeCronograma.id}">
								</div>
							</td>

							<td align="center" width="10%">${faturamentoAtividadeCronograma.faturamentoGrupoCronogramaMensal.faturamentoGrupo.id}</td>
							<td align="center" width="10%"><%=Util
													.formatarAnoMesParaMesAno(faturamentoAtividadeCronograma
															.getFaturamentoGrupoCronogramaMensal()
															.getAnoMesReferencia())%></td>
							<td align="center" width="40%">${faturamentoAtividadeCronograma.faturamentoAtividade.descricao}</td>
							<td align="center" width="15%"><bean:write
								name="faturamentoAtividadeCronograma" property="dataPrevista"
								formatKey="date.format" /></td>
							<td align="center" width="20%"><bean:write
								name="faturamentoAtividadeCronograma" property="comando"
								formatKey="date.format" /></td>
							<td align="center" width="20%"><bean:write
								name="faturamentoAtividadeCronograma" property="comando"
								formatKey="hour.format" /></td>
						</tr>
					</pg:item>
				</logic:iterate>
		</table>
		<table width="100%" border="0">
			<tr>
				<td align="center"><strong><%@ include
					file="/jsp/util/indice_pager_novo_parametro_url.jsp"%></strong></td>
			</tr>
		</table>
		</pg:pager>
		<table width="100%" border="0">
			<tr>
				<td><strong>Comandos de simulação de faturamento:</strong></td>
			</tr>
		</table>
		<table width="100%" border="0" bgcolor="#99CCFF">
			<tr bgcolor="79BBFD">
		
				<td align="center" width="5%" rowspan="2">
					<strong><a href="javascript:facilitadorSegundo('idFaturamentoSimulacaoComando');" id="0">Todos</a></strong>
				</td>
		
				<td align="center" width="75%" rowspan="2"><strong>Título</strong></td>
				<td align="center" width="20%" rowspan="2"><strong>Data Prevista</strong></td>
		
			</tr>
		</table>
		
		<div style="width: 100%; height: 100; overflow: auto;">

			<table width="100%" bgcolor="#99CCFF">
				<%int cont2 = 0;%>
				<logic:iterate name="colecaoFaturamentoSimulacaoComando"
					id="faturamentoSimulacaoComando"
					type="gcom.faturamento.faturamentosimulacaocomando.FaturamentoSimulacaoComando">
					
					<%cont2 = cont2 + 1;
						if (cont2 % 2 == 0) {%>
					<tr bgcolor="#cbe5fe">
						<%} else {%>
					<tr bgcolor="#FFFFFF">
						<%}%>

						<td align="center" width="5%">
							<div align="center"><input type="checkbox" name="idFaturamentoSimulacaoComando" value="${faturamentoSimulacaoComando.id}">
							</div>
						</td>
	
						<td align="center" width="75%"><bean:write
							name="faturamentoSimulacaoComando" property="descricaoTitulo"/></td>
						<td align="center" width="20%"><bean:write
							name="faturamentoSimulacaoComando" property="dataComando"
							formatKey="date.format" /></td>
					</tr>

			</logic:iterate>
			</table>
			
		</div>
		
		<table width="100%">
			<tr>
				<td width="50%"><input type="button"
					onclick="window.location.href='/gsan/telaPrincipal.do'"
					class="bottonRightCol" value="Cancelar" style="width: 70px;"></td>
				<td align="right"><input type="button"
					onclick="return verificarSubmit(document.forms[0].idFaturamentoAtividadeCronograma, document.forms[0].idFaturamentoSimulacaoComando);"
					class="bottonRightCol" value="Iniciar" style="width: 70px;"></td>

			</tr>
		</table>
		</td>
	</tr>
</table>


<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>	

</body>
</html:html>
