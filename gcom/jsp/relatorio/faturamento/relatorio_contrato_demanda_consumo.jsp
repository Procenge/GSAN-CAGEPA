<%@page
	import="gcom.gui.relatorio.arrecadacao.GerarRelatorioSituacaoDosAvisosBancariosActionForm"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

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
	formName="GerarRelatorioSituacaoDosAvisosBancariosActionForm"
	dynamicJavascript="true" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">
<!-- Begin
	
	function reload() {
	
		var form = document.GerarRelatorioContratoDemandaConsumoActionForm;
		form.action = "/gsan/exibirGerarRelatorioContratoDemandaConsumoAction.do";
		form.submit();
	}
	
	function desabilitarLocalidade(){
		
		var form=document.GerarRelatorioContratoDemandaConsumoActionForm;
		
		if(form.faturamentoGrupo.value==-1){
			
			form.localidade.disabled=false;
			
		}else{
			 
			form.localidade.disabled=true;
		}
		
	}
	function replicaDados(campoOrigem, campoDestino){
		campoDestino.value = campoOrigem.value;
	}
	
	function validarForm(){
		
		var form = document.GerarRelatorioContratoDemandaConsumoActionForm;
			
		form.submit();			
	}
	-->
</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');">

	<html:form action="/gerarRelatorioContratoDemandaConsumoAction"
		name="GerarRelatorioContratoDemandaConsumoActionForm"
		type="gcom.gui.relatorio.faturamento.GerarRelatorioContratoDemandaConsumoActionForm"
		method="post">

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
				<td width="625" valign="top" bgcolor="#003399" class="centercoltext">
					<table height="100%">

						<tr>
							<td></td>
						</tr>
					</table>
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td width="11"><img border="0"
								src="imagens/parahead_left.gif" /></td>
							<td class="parabg">Relatório Contrato Demanda Consumo</td>
							<td width="11" valign="top"><img border="0"
								src="imagens/parahead_right.gif" /></td>
						</tr>
					</table>
					<p>&nbsp;</p>
					<table width="100%" border="0">
						<tr>
							<td colspan="2">Para gerar o relatório, informe os dados
								abaixo:</td>
						</tr>

						<tr>
							<td>
								<p>&nbsp;</p>
							</td>
						</tr>

						<tr>
						<tr>
							<td width="40%"><strong>Grupo de Faturamento:</strong></td>
							<td><strong><strong>
								<html:select property="faturamentoGrupo" onchange="javascript:desabilitarLocalidade();">
									<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
									<html:options collection="colecaoGrupoFaturamento" labelProperty="descricao" property="id"/>
								</html:select> </strong></strong>
							</td>
						</tr>	
						<tr>
						<td><strong>Localidade:</strong></td>
								<td align="left">
									<html:select property="localidade" multiple="true"
											style="width: 450px; height: 100px" >
										<logic:present name="colecaoLocalidade">
												<html:options collection="colecaoLocalidade" property="id"
												labelProperty="descricao" />
										</logic:present>
									</html:select>
						    	</td>
						</tr>
						<tr>
					
						<td><strong>Período Faturamento:</strong></td>
							<td colspan="2"><html:text maxlength="7" property="mesAnoFaturamentoInicial" size="7" 
							onkeyup="mascaraAnoMes(this, event);replicaDados(document.forms[0].mesAnoFaturamentoInicial,
									document.forms[0].mesAnoFaturamentoFinal);"
									onblur="javascript:verificaAnoMes(this);" tabindex="2"/>
									
							<html:text maxlength="7" property="mesAnoFaturamentoFinal" size="7"
							onkeyup="mascaraAnoMes(this, event);" onblur="javascript:verificaAnoMes(this);"
							tabindex="2" />&nbsp;mm/aaaa</td>
						
						<tr>
						<td><strong>Tipo Contrato:</strong></td>
							<td>
								<html:select property="tipoContrato"  onchange="javascript:reload();">
									<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
									<html:option value="<%=""+ConstantesSistema.CONFIRMADA%>">Tarifa de Consumo</html:option>
									<html:option value="<%=""+ConstantesSistema.NAO_CONFIRMADA%>">Consumo Fixo</html:option>
								</html:select> 
							</td>
						 </tr>
						 	
						 	
						 <logic:present name="colecaoTarifaConsumo">
						 	<tr>	
								<td><strong>Tarifa Consumo:</strong></td>
									<td>
									<html:select property="tarifaConsumo">
										<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
										<html:options collection="colecaoTarifaConsumo" labelProperty="descricao" property="id"/>
									</html:select> 
							</td>
						 </tr>
						 </logic:present>
						 <tr>
							<tr>
								<td height="20%" width="20%">
										<div class="style9"><strong>Encerrado:<font color="#FF0000">*</font></strong></div>
									</td>
									<td>
									<div align="left"><html:radio property="encerrado" value="<%=ConstantesSistema.CONFIRMADA%>" disabled="false"/>
										<strong>Sim<html:radio property="encerrado"    value="<%=ConstantesSistema.NAO_CONFIRMADA%>" disabled="false"/>
										Não<html:radio property="encerrado" value="<%=ConstantesSistema.AMBOS_CONTRATO%>" disabled="false"/>Ambos</strong>
										</div>
									</td>
							</tr>
						</tr>
					</tr>

						<tr>
							<td><strong> <font color="#FF0000"></font>
							</strong></td>
							<td colspan="3" align="right">
								<div align="left">
									<strong> <font color="#FF0000">*</font>
									</strong> Campos obrigat&oacute;rios
								</div>
							</td>
						</tr>
						
						<tr>
							<td><input name="Submit22" class="bottonRightCol"
								value="Limpar" type="button"
								onclick="window.location.href='/gsan/exibirGerarRelatorioContratoDemandaConsumoAction.do?&menu=sim';">
							
							<td colspan="2" align="right"><gcom:controleAcessoBotao
									name="Button" value="Gerar"
									onclick="javascript:validarForm();"
									url="gerarRelatorioContratoDemandaConsumoAction.do" /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

		<%@ include file="/jsp/util/rodape.jsp"%>

	</html:form>
</body>
<!-- movimento_arrecadadores_acompanhar.jsp -->
</html:html>
