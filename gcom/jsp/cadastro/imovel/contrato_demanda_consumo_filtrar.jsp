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

function validaEnterImovel(tecla, caminhoActionReload, nomeCampo) {
// 	var form = document.forms[0];
// 	form.action = 'exibirInserirContratoDemandaConsumoAction.do?consultarImovel=1';
// 	form.submit();
	validaEnterComMensagem(tecla, caminhoActionReload, nomeCampo, "Matr�cula do Im�vel");
	
}

function validaMudancaCampoMatricula(){
	var form = document.forms[0];	
	if(form.idImovel.value != form.idImovelAux.value){
		form.idImovelAux.value = form.idImovel.value;
			
	    if(form.idImovel.value != ""){
			form.action='exibirFiltrarContratoDemandaConsumoAction.do?consultarImovel=1';
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
      
	form.action='exibirFiltrarContratoDemandaConsumoAction.do?consultarImovel=1';
    form.submit();

}

function validarForm(form){
	
	if(form.mesAnoFaturamento.value == ''){
			if(form.idImovel.value == ''){
				if(form.numeroContrato.value == ''){
					if(form.consumoFixo.value == ''){
						alert('Informe pelo menos um campo.');
						return;
					}
				}
		}
	} 
	form.submit();	
}

</script>

</head>

<body leftmargin="5" topmargin="5">

<html:form name="ManterContratoDemandaConsumoActionForm" type="gcom.gui.cadastro.imovel.ManterContratoDemandaConsumoActionForm" 
action="/filtrarContratoDemandaConsumoAction.do" method="post">

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
					<td class="parabg">Filtrar Contrato de Demanda de Consumo</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<html:hidden property="idImovelAux"/>
			<table width="100%" border="0">
				<tr>
					<td>Para Manter um Contrato de Demanda de Consumo, informe os dados abaixo:</td>
					<td align="right"></td>
				</tr>
			</table>
			

			<p>&nbsp;</p>
			<table>
				<tr>
					<td ><strong>Matr�cula do Im�vel:</strong>
					
					<html:text property="idImovel" maxlength="9" size="9" onkeypress="return isCampoNumerico(event);"
					onkeyup="validaEnterImovel(event, 'exibirFiltrarContratoDemandaConsumoAction.do?consultarImovel=1', 'idImovel');" 
					onblur="validaMudancaCampoMatricula();"/>
					<a href="javascript:habilitarPesquisaImovel(document.forms[0]);" alt="Pesquisar Im�vel">
					<img width="23" height="21"
						src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" /></a>
					<a href="exibirFiltrarContratoDemandaConsumoAction.do?menu=sim"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
						<html:text name="ManterContratoDemandaConsumoActionForm" property="inscricaoImovel" readonly="true" style="background-color:#EFEFEF; border:0"></</html:text>	
					</td>
						
				</tr>
			</table>
			<table>
				<tr>
					<td>
						<strong>N�mero de Contrato:</strong>
					</td>
					<td>
						<html:text name="ManterContratoDemandaConsumoActionForm" property="numeroContrato" maxlength="8" size="8" onkeypress="return isCampoNumerico(event);">
						</</html:text>
					</td>
				</tr>
				<tr>
					<td>
						<strong>M�s/Ano de Faturamento:</strong>
					</td>
					<td>
						<html:text name="ManterContratoDemandaConsumoActionForm" property="mesAnoFaturamento" maxlength="7" size="7" 
						onkeyup="mascaraAnoMes(this, event);" onblur="verificaAnoMesMensagemPersonalizada(this,'Refer�ncia inv�lida');">
						</</html:text>
					</td>
				</tr>
				<tr>
					<td>
						<strong>Tipo de contrato:</strong>
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
			</table>
			<table width="100%" >
			<tr>
			<td colspan="2">
				<input type="button" name="Button"
					class="bottonRightCol" value="Desfazer" tabindex="7"
					onClick="javascript:window.location.href='/gsan/exibirFiltrarContratoDemandaConsumoAction.do?menu=sim'"
					style="width: 80px" />&nbsp; 
				<input type="button" name="Button"
					class="bottonRightCol" value="Cancelar" tabindex="8"
					onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
					style="width: 80px" />
			</td>
			<td align="right">
				<gcom:controleAcessoBotao name="Button"
					value="Filtrar" tabindex="9"
					onclick="javascript:validarForm(document.forms[0]);"
					url="filtrarContratoDemandaConsumoAction.do" />
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

