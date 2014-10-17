<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

<%@ page import="gcom.cobranca.*,gcom.util.Util"%>

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

function verificarSubmit(check1, check2) {

	var form = document.forms[0];
	if (verificarMarcados(check1) || verificarMarcados(check2)){
		if(validateInserirProcessoActionForm(form) && validarHoraCompleta(form.horaAgendamento.value)){
			if (confirm('Confirma Início do Processo?')) {
			    form.submit();
			    return true;
		    }
		} 
		return false;
	} else {
		alert('Selecione as atividades de ação de cobrança para execução.');
		return false;		
	}
}

function facilitador(objeto){

	if (objeto.id == "0" || objeto.id == undefined){
		objeto.id = "1";
		marcarTodos(objeto);
	}
	else{

		objeto.id = "0";
		desmarcarTodos(objeto);
	}
}


function marcarTodos(checkbox){

	if (checkbox.length == undefined) {
		checkbox.checked = false;
	} else {
		for (var i=0;i < checkbox.length;i++){
				checkbox[i].checked = false;
		}	
	}
}

function desmarcarTodos(checkbox) {
	if (checkbox.length == undefined) {
		checkbox.checked = true;
	} else {
		for (var i=0;i < checkbox.length;i++){
				checkbox[i].checked = true;
		}	
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

function remover(check1, check2){
	var form = document.forms[0];
	if (verificarMarcados(check1) || verificarMarcados(check2)){
		if (confirm ("Confirma remoção?")) {
			document.forms[0].action = "/gsan/excluirProcessoCobrancaComandadoAction.do"
			document.forms[0].submit();
		 } 
		return false;
	} else {
		alert('Selecione as atividades de ação de cobrança para excluir.');
		return false;		
	}
}



</script>
</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');">

<html:form action="/inserirProcessoCobrancaComandadoAction.do"
	name="InserirProcessoActionForm"
	type="gcom.gui.batch.InserirProcessoActionForm" 
	method="post">
	
	<input type="hidden" name="caminhoReload"
	value="/gsan/exibirInserirProcessoFaturamentoComandadoAction.do" /> 
	
	<input type="hidden" name="dataHoraAgendamento"
	value="/gsan/exibirInserirProcessoFaturamentoComandadoAction.do" /> 
	
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
				<td class="parabg">Iniciar Processo de Cobrança</td>
				<td width="11"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>

			</tr>
		</table>
		<p>&nbsp;</p>
		<table width="100%" border="0" align="center">
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
		<!-- =============================INICIO ATIVIDADES DE COBRANÇA DO CRONOGRAMA====================================================== -->
		<table width="100%" border="0">
			<tr>

				<td colspan="3">
				<table width="98%" border="0">
					<tr>
						<td height="17" colspan="3"><strong>Atividades de ação de cobrança
						do cronograma comandadas para execução :</strong></td>

						<td align="right"></td>
					</tr>

					<tr>
						<td colspan="7">
						<table width="100%" cellpadding="0" cellspacing="0">

							<tr>
								<td>

								<table width="100%" bgcolor="#99CCFF">
									<tr bordercolor="#FFFFFF" bgcolor="#99CCFF">

										<td width="55" rowspan="2" bordercolor="#000000"
											bgcolor="#90c7fc">
										<div align="center"><a onclick="this.focus();" id="0"
											href="javascript:facilitador(document.forms[0].idsCronograma);"><strong>Todas</strong></a></div>

										</td>

										<td width="55" rowspan="2" bordercolor="#000000"
											bgcolor="#90c7fc">
										<div align="center"><strong>Grupo</strong></div>

										</td>

										<td width="74" rowspan="2" bordercolor="#000000"
											bgcolor="#90c7fc">
										<div align="center"><strong>Mês/Ano</strong></div>

										</td>

										<td width="122" rowspan="2" bordercolor="#000000"
											bgcolor="#90c7fc">
										<div align="center"><strong>Ação</strong></div>
										</td>


										<td width="74" rowspan="2" bordercolor="#000000"
											bgcolor="#90c7fc">
										<div align="center"><strong>Atividade</strong></div>
										</td>


										<td width="74" rowspan="2" bordercolor="#000000"
											bgcolor="#90c7fc">
										<div align="center"><strong>Data Prevista</strong></div>
										</td>

										<td height="19" colspan="2" bordercolor="#000000"
											bgcolor="#90c7fc">
										<div align="center"><strong>Comando</strong></div>

										</td>

									</tr>


									<tr bordercolor="#FFFFFF" bgcolor="#cbe5fe">

										<td width="61" bordercolor="#cbe5fe" bgcolor="#cbe5fe">
										<div align="center"><strong>Data</strong></div>
										</td>

										<td width="62" bordercolor="#cbe5fe" bgcolor="#cbe5fe">
										<div align="center"></div>
										<div align="center"><strong>Hora</strong></div>

										</td>

									</tr>

								</table>


								<div style="width: 100%; height: 100; overflow: auto;">

								<table width="100%" bgcolor="#99CCFF">
									<%int cont = 0;%>
									<logic:iterate name="colecaoAcaoAtividadeCronograma"
										id="cobrancaAcaoAtividadeCronograma"
										type="CobrancaAcaoAtividadeCronograma">
										<%cont = cont + 1;
					if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
											<%} else {%>
										<tr bgcolor="#FFFFFF">
											<%}%>

											<td width="55" align="center">
											<div align="center"><input type="checkbox"
												name="idsCronograma"
												value="${cobrancaAcaoAtividadeCronograma.id}"></div>
											</td>


											<td width="55">
											<div align="center">${cobrancaAcaoAtividadeCronograma.cobrancaAcaoCronograma.cobrancaGrupoCronogramaMes.cobrancaGrupo.id}</div>
											</td>

											<td width="74" align="center"><span style="color: #000000;"><%=Util
											.formatarAnoMesParaMesAno(cobrancaAcaoAtividadeCronograma
													.getCobrancaAcaoCronograma()
													.getCobrancaGrupoCronogramaMes()
													.getAnoMesReferencia())%></span></td>


											<td width="122">
											<div align="center">${cobrancaAcaoAtividadeCronograma.cobrancaAcaoCronograma.cobrancaAcao.descricaoCobrancaAcao}</div>
											</td>

											<td width="74">
											<div align="center">${cobrancaAcaoAtividadeCronograma.cobrancaAtividade.descricaoCobrancaAtividade}</div>

											</td>

											<td width="74">
											<div align="center"><bean:write
												name="cobrancaAcaoAtividadeCronograma"
												property="dataPrevista" formatKey="date.format" /></div>
											</td>

											<td width="61">
											<div align="center"><bean:write
												name="cobrancaAcaoAtividadeCronograma" property="comando"
												formatKey="date.format" /></div>
											</td>

											<td width="62">

											<div align="center"><bean:write
												name="cobrancaAcaoAtividadeCronograma" property="comando"
												formatKey="hour.format" /></div>
											</td>

										</tr>
									</logic:iterate>
								</table>

								</div>
								</td>
							</tr>
						</table>
						</td>

					</tr>
				</table>
				</td>
			</tr>
		</table>

		<!-- =============================FIM ATIVIDADES DE COBRANÇA DO CRONOGRAMA========================================================= -->

		<!-- =============================INICIO DAS ATIVIDADES DE COBRANÇA EVENTUAIS====================================================== -->
		<table width="100%" border="0">
			<tr>
				<td colspan="3">
				<table width="98%" border="0">

					<tr>
						<td height="17" colspan="3"><strong>Atividades de ação de cobrança
						eventuais comandadas para execução:</strong></td>
						<td align="right"></td>

					</tr>

					<tr>
						<td colspan="7">
						<table width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td>
								<table width="100%" bgcolor="#99CCFF">
									<tr bordercolor="#FFFFFF" bgcolor="#99CCFF">

										<td width="9%" rowspan="2" bordercolor="#000000"
											bgcolor="#90c7fc">

										<div align="center"><a onclick="this.focus();" id="0"
											href="javascript:facilitador(document.forms[0].idsEventuais);"><strong>Todas</strong></a></div>

										</td>

										<td width="12%" rowspan="2" bordercolor="#000000"
											bgcolor="#90c7fc">
										<div align="center"><strong>Mês/Ano</strong></div>
										</td>

										<td width="9%" rowspan="2" bordercolor="#000000"
											bgcolor="#90c7fc">
										<div align="center"><strong>Ação</strong></div>

										</td>


										<td width="12%" rowspan="2" bordercolor="#000000"
											bgcolor="#90c7fc">
										<div align="center"><strong>Atividade</strong></div>
										</td>

										<td height="19" colspan="2" bordercolor="#000000"
											bgcolor="#90c7fc">
										<div align="center"><strong>Comando</strong></div>
										</td>

										<td width="12%" rowspan="2" bordercolor="#000000"
											bgcolor="#90c7fc">

										<div align="center"><strong>Usuário</strong></div>
										</td>

									</tr>

									<tr bordercolor="#FFFFFF" bgcolor="#cbe5fe">

										<td width="10%" bordercolor="#cbe5fe" bgcolor="#cbe5fe">
										<div align="center"><strong>Data</strong></div>
										</td>

										<td width="10%" bordercolor="#cbe5fe" bgcolor="#cbe5fe">
										<div align="center"></div>

										<div align="center"><strong>Hora</strong></div>
										</td>

									</tr>

								</table>

								<div style="width: 100%; height: 100; overflow: auto;">

								<table width="100%" bgcolor="#99CCFF">
									<%int cont2 = 0;%>
									<logic:iterate name="colecaoAcaoAtividadeComando"
										id="cobrancaAcaoAtividadeComando"
										type="CobrancaAcaoAtividadeComando">
										<%cont2 = cont2 + 1;
					if (cont2 % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
											<%} else {%>
										<tr bgcolor="#FFFFFF">
											<%}%>


											<td width="9%" align="center">

											<div align="center"><input type="checkbox"
												name="idsEventuais" value="${cobrancaAcaoAtividadeComando.id}"></div>
											</td>

											<td width="9%" align="center"><span style="color: #000000;">${requestScope.anoMesFaturamento}</span>
											</td>

											<td width="10%">

											<div align="center">${cobrancaAcaoAtividadeComando.cobrancaAcao.descricaoCobrancaAcao}</div>
											</td>

											<td width="10%">
											<div align="center">${cobrancaAcaoAtividadeComando.cobrancaAtividade.descricaoCobrancaAtividade}</div>
											</td>

											<td width="12%">
											<div align="center"><bean:write
												name="cobrancaAcaoAtividadeComando" property="comando"
												formatKey="date.format" /></div>
											</td>

											<td width="12%">
											<div align="center"><bean:write
												name="cobrancaAcaoAtividadeComando" property="comando"
												formatKey="hour.format" /></div>
											</td>

											<td width="12%">

											<div align="center">${cobrancaAcaoAtividadeComando.usuario.nomeUsuario}</div>
											</td>
										</tr>
									</logic:iterate>
								</table>
								</div>
								</td>

							</tr>

						</table>

						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		<!-- =============================FIM DAS ATIVIDADES DE COBRANÇA EVENTUAIS========================================================= -->
		<div align="right">
		<table>
			<tr>

				<td>
				<input type="button" class="bottonRightCol" tabindex="1" value="Remover"
											onclick="remover(document.forms[0].idsCronograma, document.forms[0].idsEventuais);"/>
				<input type="button"
					onclick="window.location.href='/gsan/telaPrincipal.do'"
					class="bottonRightCol" value="Cancelar" style="width: 70px;">&nbsp;

				<input type="button"
					onclick="verificarSubmit(document.forms[0].idsCronograma, document.forms[0].idsEventuais);"
					class="bottonRightCol" value="Iniciar" style="width: 70px;"></td>
			</tr>
		</table>
		</div>

		</td>
	</tr>
</table>

<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>
