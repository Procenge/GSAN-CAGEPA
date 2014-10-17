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


<script language="JavaScript">

function validaEnterImovel(tecla, caminhoActionReload, nomeCampo) {
	validaEnterComMensagem(tecla, caminhoActionReload, nomeCampo, "Matrícula do Imóvel");
	
}

function validaMudancaCampoMatricula(){
	var form = document.forms[0];	
	
	if(form.idImovel.value != form.idImovelAux.value){
		form.idImovelAux.value = form.idImovel.value;
			
	    if(form.idImovel.value != ""){
			form.action='exibirAtualizarContratoDemandaConsumoAction.do?consultarImovel=1';
		    form.submit();
	    }
	}
}

function habilitarPesquisaImovel(form) {
	if (form.idImovel.readOnly == false) {
		abrirPopup('exibirPesquisarImovelAction.do', 400, 800);
	}
}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
    var form = document.forms[0];

    form.idImovel.value = codigoRegistro;
      
	form.action='exibirAtualizarContratoDemandaConsumoAction.do?consultarImovel=1';
    form.submit();

}

function validarForm(form){
	
	if(form.mesAnoFaturamentoInicial.value == ''){
		alert('Informe Mês/Ano de Faturamento Inicial');
	}else if(form.mesAnoFaturamentoFinal.value == ''){
		alert('Informe Mês/Ano de Faturamento Final');
	}else if(comparaMesAno(form.mesAnoFaturamentoInicial.value, ">", form.mesAnoFaturamentoFinal.value)){
			alert('Mês/Ano de Faturamento Final é anterior ao Mês/Ano de Faturamento Inicial'); 
	}else if(form.idImovel.value == ''){
			alert('Informe a Matrícula do imóvel.'); 
	}else if(form.numeroContrato.value == ''){
			alert('Informe o Número do Contrato.'); 
	}else if(form.idTarifaConsumo.value == '-1' && form.consumoFixo.value == ''){
			alert('Tipo de Contrato Obrigatório. Informe a Tarifa de Consumo ou o Consumo Fixo.'); 
	}else if(form.emailgestorContrato.value == ''){
			alert('Informe o E-mail do Gestor do Contrato.'); 
	}else{
		form.submit();	
	}
}

</script>

</head>

<body leftmargin="5" topmargin="5">

<html:form name="AtualizarContratoDemandaConsumoActionForm" type="gcom.gui.cadastro.imovel.AtualizarContratoDemandaConsumoAction" 
action="/atualizarContratoDemandaConsumoAction.do" method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

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
					<td class="parabg">Atualizar Contrato de Demanda de Consumo</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<html:hidden property="idImovelAux"/>

			<table width="100%" border="0">
				<tr>
					<td>Para Atualizar o Contrato de Demanda de Consumo, informe os dados abaixo:</td>
					<td align="right"></td>
				</tr>
			</table>
			

			<p>&nbsp;</p>
			<table>
				<tr>
					<td ><strong>Matrícula do Imóvel:<font color="#FF0000">*</font></strong>
					
					<html:text property="idImovel" maxlength="9" size="9" onkeypress="return isCampoNumerico(event);"
					onkeyup="validaEnterImovel(event, 'exibirAtualizarContratoDemandaConsumoAction.do?consultarImovel=1', 'idImovel');" 
					onblur="validaMudancaCampoMatricula();"/>
					<a href="javascript:habilitarPesquisaImovel(document.forms[0]);" alt="Pesquisar Imóvel">
					<img width="23" height="21"
						src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" /></a>
					<a href="exibirFiltrarContratoDemandaConsumoAction.do?menu=sim"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
						<html:text name="AtualizarContratoDemandaConsumoActionForm" property="inscricaoImovel" readonly="true" style="background-color:#EFEFEF; border:0"></</html:text>	
					</td>
						
				</tr>
				
			</table>
			<table>
				<tr>
					<td>
						<strong>Número de Contrato:<font color="#FF0000">*</font></strong>
					</td>
					<td>
						<html:text name="AtualizarContratoDemandaConsumoActionForm" property="numeroContrato" maxlength="8" size="8" onkeypress="return isCampoNumerico(event);">
						</</html:text>
					</td>
				</tr>
				<tr>
					<td>
						<strong>Mês/Ano de Faturamento Inicial:<font color="#FF0000">*</font></strong>
					</td>
					<td>
						<html:text name="AtualizarContratoDemandaConsumoActionForm" property="mesAnoFaturamentoInicial" maxlength="7" size="7" 
						onkeyup="mascaraAnoMes(this, event);" onblur="verificaAnoMesMensagemPersonalizada(this,'Referência inválida');">
						</</html:text>
					</td>
				</tr>
				<tr>
					<td>
						<strong>Mês/Ano de Faturamento Final:<font color="#FF0000">*</font></strong>
					</td>
					<td>
						<html:text name="AtualizarContratoDemandaConsumoActionForm" property="mesAnoFaturamentoFinal" maxlength="7" size="7" 
						onkeyup="mascaraAnoMes(this, event);" onblur="verificaAnoMesMensagemPersonalizada(this,'Referência inválida');">
						</</html:text>
					</td>
				</tr>
				<tr>
					<td>
						<strong>Tipo de contrato:<font color="#FF0000">*</font></strong>
					</td>
					<td>
						<table>
							<tr>
								<td>
									<strong>Tarifa de Consumo: </strong>
									<html:select property="idTarifaConsumo"
										onchange="javascript:document.forms[0].consumoFixo.value = '';">
										<html:option
											value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
											</html:option>
				
										<logic:present name="colecaoTarifaConsumo" scope="session">
											<html:options collection="colecaoTarifaConsumo"
												labelProperty="descricao" property="id" />
										</logic:present>
									</html:select>
								</td>
							</tr>
							<tr>
								<td>
									<strong>Consumo Fixo: </strong>
									<html:text property="consumoFixo" size="6" maxlength="6" 
									onkeypress="javascript:document.forms[0].idTarifaConsumo.value = '-1';return isCampoNumerico(event);"></html:text>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<strong>Email do Gestor do Contrato:<font color="#FF0000">*</font></strong> 
					</td>
					<td>
						<html:text property="emailgestorContrato" size="40" maxlength="40"> 
						</html:text>
					</td>
				</tr>
			</table>
			<table width="100%" >
			<tr>
			<td colspan="2">
				<input type="button" name="Button"
					class="bottonRightCol" value="Desfazer" tabindex="7"
					onClick="javascript:window.location.href='/gsan/exibirAtualizarContratoDemandaConsumoAction.do'"
					style="width: 80px" />&nbsp; 
				<input type="button" name="Button"
					class="bottonRightCol" value="Cancelar" tabindex="8"
					onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
					style="width: 80px" />
			</td>
			<td align="right">
				<gcom:controleAcessoBotao name="Button"
					value="Atualizar" tabindex="9"
					onclick="javascript:validarForm(document.forms[0]);"
					url="atualizarContratoDemandaConsumoAction.do" />
			</td>
		</tr>
			</table>
			</td>
		</tr>
		
	</table>
		


	<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>


</body>
</html:html>

