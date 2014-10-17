<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>


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

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="InserirCepActionForm" />

<script language="JavaScript">

function replicaDados(campoOrigem, campoDestino){
	campoDestino.value = campoOrigem.value;
}

function validaForm(){
	var form = document.forms[0];
	
	if(form.referenciaInicial.value == ""){
		alert("Informe a Referência da Arrecadação Inicial.");
	}else if(form.referenciaFinal.value == ""){
		alert("Informe a Referência da Arrecadação Final.");
	}else{
		form.submit();
	}
}

</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="document.forms[0].removerEndereco.value=''; setarFoco('${requestScope.nomeCampo}');">

<html:form action="/gerarRelatorioRemuneracaoCobrancaAdministrativaAction.do" 
			name="GerarRelatorioRemuneracaoCobrancaAdministrativaActionForm" 
		   type="gcom.gui.relatorio.cobranca.GerarRelatorioRemuneracaoCobrancaAdministrativaActionForm"
			method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
	
	<html:hidden property="modeloRelatorio" value="2"/>
	
	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<tr>
			<td width="149" valign="top" class="leftcoltext">
			<div align="center"><%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%@ include
				file="/jsp/util/informacoes_usuario.jsp"%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%@ include
				file="/jsp/util/mensagens.jsp"%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
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
					<td class="parabg">Gerar Relatório de Remuneração da Cobrança Administrativa</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">
			
				<tr>
					<td>Para gerar o Relatório de Remuneração da Cobrança Administrativa, informe os dados abaixo:</td>
					<td align="right"></td>
					
				</tr>
				
			</table>
			<br>
			<table>
				
				<tr>
					<td><strong>Tipo do Relatório:<font color="#FF0000">*</font></strong></td>				
					<td>
						<html:radio property="tipoRelatorio" value="S">Sintético</html:radio> 
						<html:radio property="tipoRelatorio" value="A">Analítico</html:radio>
					</td>
				</tr>
				<tr>
					<td><strong>Referência da Arrecadação Inicial:<font color="#FF0000">*</font></strong></td>
					<td>
						<html:text name="GerarRelatorioRemuneracaoCobrancaAdministrativaActionForm" 
									property="referenciaInicial" 
									maxlength="7" 
									size="7"
									onkeyup="javascript:replicaDados(referenciaInicial,referenciaFinal);mascaraAnoMes(this,event);"
									onblur="javascript:verificaAnoMes(this)"/>
					</td>				
				</tr>
				<tr>
					<td><strong>Referência da Arrecadação Final:<font color="#FF0000">*</font></strong></td>				
					<td>
					<html:text name="GerarRelatorioRemuneracaoCobrancaAdministrativaActionForm" 
								property="referenciaFinal" 
								maxlength="7" 
								size="7"
								onkeyup="javascript:mascaraAnoMes(this,event);"
								onblur="javascript:verificaAnoMes(this)"/>
					</td>
				</tr>
				<tr>
					<td><strong>Empresa de Cobrança Administrativa:</strong></td>	
					<td>
						<html:select property="idEmpresaCobrancaAdministrativa" style="width: 350px;"
						multiple="true" tabindex="13">
						<logic:present name="colecaoEmpresasCobrancaAdministrativa" scope="session">
						<html:option 
							value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option> 
						
							<html:options collection="colecaoEmpresasCobrancaAdministrativa"
								labelProperty="descricao" property="id" />
						</logic:present>
						</html:select>
					</td>			
				</tr>
				<tr>
					<td><strong>Situação da Remuneraçao:<font color="#FF0000">*</font></strong></td>	
					<td>
						<html:radio property="situacaoRemuneracao" value="1">Paga</html:radio> 
						<html:radio property="situacaoRemuneracao" value="2">Não Paga</html:radio>
						<html:radio property="situacaoRemuneracao" value="3">Ambas</html:radio>
					</td>			
				</tr>
				<tr>
					<td><strong>Confirma Pagamento da Remuneração:<font color="#FF0000">*</font></strong></td>
					<td>
						<html:radio property="indicadorConfirmaPagamento" value="1" >Sim</html:radio> 
						<html:radio property="indicadorConfirmaPagamento" value="2">Não</html:radio>
					</td>				
				</tr>
				<tr> 
                	<td>&nbsp;</td>
                	<td colspan="2"> <div align="right"> </div></td>
             	 </tr>
				<tr>
					<td>
						<input type="button" name="Button"
							class="bottonRightCol" value="Desfazer" tabindex="7"
							onClick="javascript:window.location.href = '/gsan/exibirGerarRelatorioRemuneracaoCobrancaAdministrativaAction.do?menu=sim'"
							style="width: 80px" />&nbsp; 
						<input type="button" name="Button"
							class="bottonRightCol" value="Cancelar" tabindex="8"
							onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
							style="width: 80px" />
					</td>
					<td> <div align="right"> 
                    <input type="button" tabindex="34" name="Submit" class="bottonRightCol" value="Gerar" onclick="validaForm();">
                  </div></td>
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

