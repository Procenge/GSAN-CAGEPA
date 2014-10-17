<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ page import="gcom.util.ConstantesSistema"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>	
<script language="JavaScript">
<!-- Begin
function executarFiltrar(){
	var form = document.forms[0];
	var tipoLista = "";
	var idSelecionado = "";
	var caminho = "emitirOSCobrancaAction.do?acao=exibirEmissaoOSCobranca&idComando=";

	if(form.tipoLista.value == 'cronograma'){
		var arr = $.makeArray($("input:radio:checked"));
		if(arr.length == 0){
			alert('Você precisa selecionar um Comando Cronograma');
			return;
		}else{
			idSelecionado = arr[0].value;
			caminho += idSelecionado + "&tipoComando=cronograma";
		}
//		var radioLength = form.idComandoCronograma.length;
//		for(var i = 0; i < radioLength; i++) {
//			if(form.idComandoCronograma[i].checked) {
//				idSelecionado = form.idComandoCronograma[i].value;
//			}
//		}
//		if(idSelecionado == ''){
//			alert('Você precisa selecionar um Comando Cronograma');
//			return;
//		} else {
//			caminho += idSelecionado + "&tipoComando=cronograma";
//		}
	} else if(form.tipoLista.value == 'eventual') {

		var arr = $.makeArray($("input:radio:checked"));
		if(arr.length == 0){
			alert('Você precisa selecionar um Comando Eventual');
			return;
		} else {
			idSelecionado = arr[0].value;
			caminho += idSelecionado + "&tipoComando=eventual";
		}
		
//		var radioLength = form.idComandoEventual.length;
//		for(var i = 0; i < radioLength; i++) {
//			if(form.idComandoEventual[i].checked) {
//				idSelecionado = form.idComandoEventual[i].value;
//			}
//		}
//		if(idSelecionado == ''){
//			alert('Você precisa selecionar um Comando Eventual');
//			return;
//		} else {
//			caminho += idSelecionado + "&tipoComando=eventual";
//		}
	}

	window.location = caminho;

}
-->
</script>
</head>

<body leftmargin="5" topmargin="5" >

<!-- Colocar Hiddens aqui -->
	
<!-- Colocar Hiddens aqui -->

	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td valign="top">
<!-- INICIO ************** COMANDOS CRONOGRAMA ***************************** -->
			<logic:present name="colecaoCobrancaAcaoAtividadeCronograma">
				<form name="comandosCronogramaForm" action="">
					<table width="100%" bgcolor="#90c7fc">
						<tr>
							<td colspan="5" bgcolor="#000000" height="2" valign="baseline"></td>
						</tr>
						<tr bordercolor="#FFFFFF" bgcolor="#79BBFD">
							<td align="center">
								<input type="hidden" name="tipoLista" value="cronograma" />
								&nbsp;
							</td>
							<td align="center">
								<strong>ID</strong>
							</td>
							<td align="center">
								<strong>Descrição</strong>
							</td>
							<td align="center">
								<strong>Ação</strong>
							</td>
							<td align="center">
								<strong>Data</strong>
							</td>
						</tr>			
						<%int cont = 1;%>	
						<logic:iterate id="cobrancaAcaoAtividadeCronograma" name="colecaoCobrancaAcaoAtividadeCronograma">
										<%cont = cont + 1;
											if (cont % 2 == 0) {%>
										<tr bgcolor="#FFFFFF">
											<%} else {%>
										<tr bgcolor="#cbe5fe">
											<%}%>
							<td>
								<input type="radio" name="idComandoCronograma" value="<bean:write name="cobrancaAcaoAtividadeCronograma" property="id"/>" />
							</td>
							<td>
								<bean:write name="cobrancaAcaoAtividadeCronograma" property="id"/>
							</td>
							<td>
								<logic:present name="cobrancaAcaoAtividadeCronograma" property="cobrancaAcaoCronograma">
									<bean:define id="cobrancaAcaoCronograma"
													name="cobrancaAcaoAtividadeCronograma" 
													property="cobrancaAcaoCronograma" />
									<logic:present name="cobrancaAcaoCronograma" property="cobrancaGrupoCronogramaMes">
										<bean:define id="cobrancaGrupoCronogramaMes"
													 name="cobrancaAcaoCronograma" 
													 property="cobrancaGrupoCronogramaMes" />
										<logic:present name="cobrancaGrupoCronogramaMes" property="cobrancaGrupo">
											<bean:define id="cobrancaGrupo" 
														 name="cobrancaGrupoCronogramaMes" 
														 property="cobrancaGrupo" />
											<bean:write name="cobrancaGrupo" property="descricao" />
										</logic:present>
									</logic:present>
								</logic:present>
							</td>
							<td>
								<logic:present name="cobrancaAcaoAtividadeCronograma" property="cobrancaAcaoCronograma">
									<bean:define id="cobrancaAcaoCronograma"
													name="cobrancaAcaoAtividadeCronograma" 
													property="cobrancaAcaoCronograma" />
									<logic:present name="cobrancaAcaoCronograma" property="cobrancaAcao">
										<bean:define id="cobrancaAcao" 	
													 name="cobrancaAcaoCronograma" 
													 property="cobrancaAcao" />
											<bean:write name="cobrancaAcao" property="descricaoCobrancaAcao" />
									</logic:present>
								</logic:present>
							</td>
							<td>
								<bean:write name="cobrancaAcaoAtividadeCronograma" property="realizacao" formatKey="date.format" />
							</td>
						</tr>
						</logic:iterate>				
					</table>
				</form>
			</logic:present>
<!-- FIM ************** COMANDOS CRONOGRAMA ***************************** -->
<!-- INICIO ************** COMANDOS EVENTUAL ***************************** -->
			<logic:present name="colecaoCobrancaAcaoAtividadeComando">
				<form name="comandosEventualForm" action="">
					<table width="100%" bgcolor="#90c7fc">
						<tr>
							<td colspan="5" bgcolor="#000000" height="2" valign="baseline"></td>
						</tr>
						<tr bordercolor="#FFFFFF" bgcolor="#79BBFD">
							<td align="center">
								<input type="hidden" name="tipoLista" value="eventual" />
								&nbsp;
							</td>
							<td align="center">
								<strong>ID</strong>
							</td>
							<td align="center">
								<strong>Descrição</strong>
							</td>
							<td align="center">
								<strong>Ação</strong>
							</td>
							<td align="center">
								<strong>Data</strong>
							</td>
						</tr>
						<%int cont = 1;%>				
						<logic:iterate id="cobrancaAcaoAtividadeComando" name="colecaoCobrancaAcaoAtividadeComando">
						<%cont = cont + 1;
							if (cont % 2 == 0) {%>
						<tr bgcolor="#FFFFFF">
							<%} else {%>
						<tr bgcolor="#cbe5fe">
							<%}%>
							<td>
								<input type="radio" name="idComandoEventual" value="<bean:write name="cobrancaAcaoAtividadeComando" property="id"/>" />
							</td>
							<td>
								<bean:write name="cobrancaAcaoAtividadeComando" property="id"/>
							</td>
							<td>
								<bean:write name="cobrancaAcaoAtividadeComando" property="descricaoTitulo"/>
							</td>
							<td>
								<logic:present name="cobrancaAcaoAtividadeComando" property="cobrancaAcao">
									<bean:define id="cobrancaAcao"
													name="cobrancaAcaoAtividadeComando" 
													property="cobrancaAcao" />
									<bean:write name="cobrancaAcao" property="descricaoCobrancaAcao" />

								</logic:present>
							</td>
							<td>
								<bean:write name="cobrancaAcaoAtividadeComando" property="realizacao" formatKey="date.format" />
							</td>
						</tr>
						</logic:iterate>				
					</table>
				</form>
			</logic:present>
<!-- FIM ************** COMANDOS EVENTUAL ***************************** -->
			<table width="100%" border="0"  cellpadding="0" cellspacing="0">
				<tr>
					<td> 
						<input name="Submit222" class="bottonRightCol" value="Voltar" type="button" 
									onclick="javascript:history.back();">
					</td>
					<td align="right">
						<input type="button" name="filtrar" class="bottonRightCol" value="Filtrar" onclick="javaScript: executarFiltrar();" />
					</td>
				</tr>				
			</table>
			</td>
		</tr>
	</table>

</body>
</html>
