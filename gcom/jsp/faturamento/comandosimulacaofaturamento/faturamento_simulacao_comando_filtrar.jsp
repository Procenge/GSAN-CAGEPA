<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="gcom.util.ConstantesSistema"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="FiltrarComandoSimulacaoFaturamentoActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">

function validarForm(form){

	if(validateFiltrarComandoSimulacaoFaturamentoActionForm(form) && validarIndicadorExecutado()){
   		
		
		submeterFormPadrao(form);
	}
}

function replicaDataComando() {
	
	var form = document.forms[0];
	form.dataFinalComando.value = form.dataInicialComando.value;
}

function validarIndicadorExecutado(){
	
	
	var form = document.forms[0];
	var msg = '';
	
	if (form.indicadorExecutado[0].checked == false && form.indicadorExecutado[1].checked == false 
			&& form.indicadorExecutado[2].checked == false) {
		
		msg = "É necessário informar Executado";
	}
	
	if( msg != ""){
		
		alert(msg);
		return false;
	}else{
		
		return true;
	}
}


</script>


</head>

<body leftmargin="5" topmargin="5"
	onload="verificarChecado('${sessionScope.indicadorAtualizar}'); setarFoco('${requestScope.nomeCampo}');">
<html:form action="/filtrarComandoSimulacaoFaturamentoAction.do" method="post"
	name="FiltrarComandoSimulacaoFaturamentoActionForm"
	type="gcom.gui.faturamento.comandosimulacaofaturamento.FiltrarComandoSimulacaoFaturamentoActionForm">
	
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
			<td width="625" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>

			<table>
				<tr>
					<td></td>

				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Filtrar Comando de Simulação de Faturamento</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>

			</table>
		
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2" >Para filtrar o comando de simulação de faturamento, informe os dados abaixo:</td>
					<td align="right"><html:checkbox property="atualizar" value="1" /><strong>Atualizar</strong></td>
				</tr>
				
				<tr>
	                <td>
	                	<strong>Per&iacute;odo de Comando:</strong>
	                </td>
                
	                <td>
	                	<span>
	                	
	                	<strong> 
							
							<html:text property="dataInicialComando" 
								size="11" 
								maxlength="10" 
								onkeyup="mascaraData(this, event);replicaDataComando();"/>
							
							<a href="javascript:abrirCalendarioReplicando('FiltrarComandoSimulacaoFaturamentoActionForm', 'dataInicialComando','dataFinalComando');">
							<img border="0" 
								src="<bean:message key='caminho.imagens'/>calendario.gif" 
								width="16" 
								height="15" 
								border="0" alt="Exibir Calendário"/>
							</a>
							
							a 
							
							<html:text property="dataFinalComando" 
								size="11" 
								maxlength="10"  
								onkeyup="mascaraData(this, event)"/>
								
							<a href="javascript:abrirCalendario('FiltrarComandoSimulacaoFaturamentoForm', 'dataFinalComando');">
							<img border="0" 
								src="<bean:message key='caminho.imagens'/>calendario.gif" 
								width="16" 
								height="15" 
								border="0" 
								alt="Exibir Calendário" />
							</a>
							
							</strong>(dd/mm/aaaa)<strong> 
	                  	</strong>
	                  	</span>
	              	</td>
              	</tr>				
			
				<tr>
					<td><strong>Executado:</strong></td>
					<td><strong>
					<html:radio property="indicadorExecutado" value="1" />Sim
					<html:radio property="indicadorExecutado" value="2" />N&atilde;o
					<html:radio property="indicadorExecutado" value="3" />Ambos</strong></td>
				</tr>

				<tr>
					<td colspan="2">
								
						<input name="Button" 
							type="button" 
							class="bottonRightCol"
							value="Limpar" 
							align="left"
							onclick="window.location.href='<html:rewrite page="/exibirFiltrarComandoSimulacaoFaturamentoAction.do?menu=sim"/>'">
					</td>
					<td align="right">
						
						<input type="button" 
								name="Button"
								class="bottonRightCol" 
								value="Cancelar" 
								onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
								style="width: 80px" />
								
						<input name="Button" 
							type="button"
							class="bottonRightCol" 
							value="Filtrar" 
							align="right"
							onClick="javascript:validarForm(document.forms[0]);"></td>
				</tr>
			</table>
			<p>&nbsp;</p>
		</tr>
	</table>
	<tr>
		<td colspan="3"><%@ include file="/jsp/util/rodape.jsp"%>
	</tr>
</html:form>
</body>
</html:html>

