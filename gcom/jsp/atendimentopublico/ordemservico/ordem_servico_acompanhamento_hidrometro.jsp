<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<%@ page import="gcom.util.ConstantesSistema"%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false" 
	formName="GerarRelatorioAcompanhamentoOrdemServicoHidrometroActionForm" staticJavascript="false" dynamicJavascript="true"/>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">

	function validarForm(){
   		var form = document.forms[0];

    	if (form.firma.selectedIndex == -1 || form.firma.selectedIndex == 0) {
    		form.firma.focus();
    		alert('Infome a Firma');
    		return;
    	}
    	
    	if (!validateInteger(form)) { return false; }
    	if (!verificaDataMensagemPersonalizada(form.dataEncerramentoInicial, 'Data de Encerramento Inicial é inválida')) { return false; }
    	if (!verificaDataMensagemPersonalizada(form.dataEncerramentoFinal, 'Data de Encerramento Final é inválida')) { return false; }
    	if (!validaTodosPeriodos()) { return false; }
		
		form.nomeFirma.value = form.firma.options[form.firma.selectedIndex].text;
		
		toggleBox('demodiv', 1);
    }
    
    function IntegerValidations () { 
	     this.aa = new Array("localidade", "Localidade deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    }
    
    function validaTodosPeriodos() {
		var form = document.forms[0];
		
		if (validaPeriodoEncerramento(form)) {
    		if (comparaData(form.dataEncerramentoInicial.value, '>', form.dataEncerramentoFinal.value)){
				alert('Data Final do Período de Encerramento é anterior à Data Inicial');
				return false;
			}
		} else {
			return false;
		} 

		return true;
    }
    
    function validaPeriodoEncerramento(form) {
    	var form = document.forms[0];
    	
    	var periodoEncerramentoInicial = trim(form.dataEncerramentoInicial.value);
 	   	var periodoEncerramentoFinal = trim(form.dataEncerramentoFinal.value);
    	
    	if ((periodoEncerramentoInicial != null && periodoEncerramentoInicial != '') &&
    	((periodoEncerramentoFinal == null || periodoEncerramentoFinal == ''))) {
    		alert('Informe Data Final Período de Encerramento');
       		return false;
    	} else if ((periodoEncerramentoFinal != null && periodoEncerramentoFinal != '') &&
    	((periodoEncerramentoInicial == null || periodoEncerramentoInicial == ''))) {
    		alert('Informe Data Inicial Período de Encerramento');
    		return false;
    	}
    	
    	return true;
    }

	//So chama a função abrirCalendario caso o campo esteja habilitado
	function chamarCalendario(fieldNameOrigem,objetoRelacionado,fieldNameDestino){
		
		if(objetoRelacionado.disabled != true){
			if(fieldNameDestino != ''){
				abrirCalendarioReplicando('GerarRelatorioAcompanhamentoOrdemServicoHidrometroActionForm', fieldNameOrigem,fieldNameDestino);
			}else{
				abrirCalendario('GerarRelatorioAcompanhamentoOrdemServicoHidrometroActionForm', fieldNameOrigem);
			}
		}
	}
	
	
	function MoveSelectedDataFromMenu1TO2(form, object, selectedObject){
		var form = document.forms[0];
		
		if (form.tipoServico.value != '-1') {
			MoverDadosSelectMenu1PARAMenu2(form, object, selectedObject);
		}
	}
	
	function MoveSelectedDataFromMenu2TO1(form, object, selectedObject){
		var form = document.forms[0];
		
		if (form.tipoServico.value != '-1') {
			MoverDadosSelectMenu2PARAMenu1(form, object, selectedObject);
		}
	}
	// ===============================================================================================
	function chamarPopup1(url, tipo, objeto, codigoObjeto, altura, largura, msg){
		if (objeto == null || codigoObjeto == null){
			abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
		}
		else{
			if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
				alert(msg);
			}
			else{
				abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
			}
		}
	}
	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
		var form = GerarRelatorioAcompanhamentoOrdemServicoHidrometroActionForm;
		
		if (tipoConsulta == 'localidadeOrigem') {
	      form.localidade.value = codigoRegistro;
		  form.nomeLocalidade.value = descricaoRegistro;
		  form.nomeLocalidade.style.color = "#000000";
		}
	}
	
	function limparBorrachaOrigem(tipo){
		var form = document.forms[0];
		
		switch(tipo){
			case 1: //De localidara pra baixo
			    if(!form.localidade.disabled){
					form.localidade.value = "";
					form.nomeLocalidade.value = "";
				}else {
					break;
				}
		}
	}
	
	function controleDataEncerramento() {
		var form = document.forms[0];
		
		if (form.situacaoOrdemServico[0].checked) {
			form.dataEncerramentoInicial.disabled = false;
			form.dataEncerramentoFinal.disabled = false;
			document.getElementById("calendario1").disabled = false;
			document.getElementById("calendario2").disabled = false;
		}else if (form.situacaoOrdemServico[1].checked) {
			form.dataEncerramentoInicial.disabled = true;
			form.dataEncerramentoInicial.value = "";
			form.dataEncerramentoFinal.disabled = true;
			form.dataEncerramentoFinal.value = "";
			form.calendario1.disabled = true;
			form.calendario2.disabled = true;
		}
	}
	
	function replicaDataEncerramento() {
		var form = document.forms[0];
		form.dataEncerramentoFinal.value = form.dataEncerramentoInicial.value;
	}
	
</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="window.focus();javascript:setarFoco('${requestScope.nomeCampo}');" >

<html:form action="/gerarRelatorioAcompanhamentoOrdemServicoHidrometroAction" method="post"
	name="GerarRelatorioAcompanhamentoOrdemServicoHidrometroActionForm"
	type="gcom.gui.atendimentopublico.ordemservico.GerarRelatorioAcompanhamentoOrdemServicoHidrometroActionForm">


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
			<td width="600" valign="top" class="centercoltext">
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
					<td class="parabg">Filtrar Relatório de Acompanhamento Ordem de Serviço de Hidr&ocirc;metro</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">

				<tr>
					<td colspan="2">Para selecionar ordens de serviço para geração do
					relatório de acompanhamento, informe os dados abaixo:</td>
				</tr>
				<tr>
					<td><strong>Tipo de Ordem:</strong></td>
					<td align="left">
						<table width="100%" border="0">
							<tr>
								<logic:present name="tipoOrdem">
									<td width="180" nowrap><html:radio value="INSTALACAO" property="tipoOrdem"></html:radio>&nbsp;Instala&ccedil;&atilde;o de Hidr&ocirc;metro</td>
									<td align="left" nowrap><html:radio value="SUBSTITUICAO" property="tipoOrdem"></html:radio>&nbsp;Substitui&ccedil;&atilde;o Hidr&ocirc;metro</td>
								</logic:present>
								<logic:notPresent name="tipoOrdem">
									<td width="180" nowrap><input type="radio" name="tipoOrdem" value="INSTALACAO" checked="checked">Instala&ccedil;&atilde;o de Hidr&ocirc;metro</td>
									<td align="left" nowrap><input type="radio" name="tipoOrdem" value="SUBSTITUICAO">Substitui&ccedil;&atilde;o Hidr&ocirc;metro</td>
								</logic:notPresent>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td height="10" colspan="3"><hr></td>
				</tr>

				<tr>
					<td><strong>Situa&ccedil;&atilde;o da Ordem:</strong></td>
					<td align="left">
						<table width="100%" border="0">
							<tr>
								<td width="180" nowrap><html:radio value="ENCERRADAS" property="situacaoOrdemServico" onclick="controleDataEncerramento();"></html:radio>&nbsp;Encerradas</td>
								<td align="left" nowrap><html:radio value="PENDENTES" property="situacaoOrdemServico" onclick="controleDataEncerramento();"></html:radio>&nbsp;Pendentes</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<td><strong>Firma:&nbsp;<font color="#FF0000">*</font></strong></td>
					<td align="right">
						<div align="left">
							<strong>
								<html:select property="firma">
									<html:option value="-1">&nbsp;</html:option>
									<html:options collection="colecaoFirma"
										property="id"
										labelProperty="descricao" />
								</html:select>
								<html:hidden property="nomeFirma"/>
							</strong>
						</div>
					</td>
				</tr>
				
				<tr>
					<td><strong>Localidade:</strong></td>
					<td>
						<html:text tabindex="2" maxlength="3" property="localidade" size="5"
							onkeypress="form.target=''; validaEnter(event, 'exibirFiltrarAcompanhamentoOrdemServicoHidrometro.do?objetoConsulta=1', 'localidade');"/>
						<a href="javascript:chamarPopup1('exibirPesquisarLocalidadeAction.do', 'origem', null, null, 275, 480, '');" id="btPesqLocalidadeInicial">
							<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar" /></a>
						<logic:present name="corLocalidadeOrigem">
							<logic:equal name="corLocalidadeOrigem" value="exception">
								<html:text property="nomeLocalidade" size="35" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:equal>
	
							<logic:notEqual name="corLocalidadeOrigem" value="exception">
								<html:text property="nomeLocalidade" size="35" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notEqual>
						</logic:present>
						
						<logic:notPresent name="corLocalidadeOrigem">
							<logic:empty name="GerarRelatorioAcompanhamentoOrdemServicoHidrometroActionForm"
								property="localidade">
								<html:text property="nomeLocalidade" value="" size="35" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:empty>

							<logic:notEmpty name="GerarRelatorioAcompanhamentoOrdemServicoHidrometroActionForm"
								property="localidade">
								<html:text property="nomeLocalidade" size="35" readonly="true"
									style="background-color:#EFEFEF; border:0; color: 	#000000" />
							</logic:notEmpty>
						</logic:notPresent>
						
						<a href="javascript:limparBorrachaOrigem(1);">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
						</a>
					</td>
				</tr>
				
				<tr>
					<td><strong>Per&iacute;odo de Encerramento:</strong></td>
					<td colspan="6"><span class="style2"> <strong>
						<html:text property="dataEncerramentoInicial" size="9" maxlength="10" tabindex="3"
							onkeyup="mascaraData(this, event);replicaDataEncerramento();" />
						<a id="calendario1" href="javascript:abrirCalendarioReplicando('GerarRelatorioAcompanhamentoOrdemServicoHidrometroActionForm', 'dataEncerramentoInicial','dataEncerramentoFinal');">
							<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif"
								width="16" height="15" border="0" alt="Exibir Calendário" tabindex="4" />
						</a> a <html:text property="dataEncerramentoFinal" size="9" maxlength="10" tabindex="3"
									onkeyup="mascaraData(this, event)" />
						<a id="calendario2" href="javascript:abrirCalendario('GerarRelatorioAcompanhamentoOrdemServicoHidrometroActionForm', 'dataEncerramentoFinal');">
							<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16"
								height="15" border="0" alt="Exibir Calendário" tabindex="4" />
						</a> </strong>(dd/mm/aaaa)<strong> </strong> </span>
					</td>
				</tr>
				
				<tr>
					<td height="10" colspan="3"><hr></td>
				</tr>
				
				<tr>
					<td><strong>Tipo do Relat&oacute;rio:</strong></td>
					<td align="left">
						<table width="100%" border="0">
							<tr>
								<td width="180" nowrap><html:radio value="1" property="tipoRelatorioAcompanhamento"></html:radio>&nbsp;Anal&iacute;tico</td>
								<td align="left" nowrap><html:radio value="2" property="tipoRelatorioAcompanhamento"></html:radio>&nbsp;Sint&eacute;tico</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<td height="10" colspan="3"><hr></td>
				</tr>
				
				<tr>
					<td align="left" colspan="2">
						<strong><font color="#FF0000">*</font></strong>Campos obrigat&oacute;rios
					</td>
				</tr>
				<tr>
					<td height="24"><input type="button" class="bottonRightCol"
						value="Limpar"
						onclick="window.location.href='/gsan/exibirFiltrarAcompanhamentoOrdemServicoHidrometro.do?menu=sim';" />
					</td>

					<td align="right"><input type="button" name="Button"
						class="bottonRightCol" value="Filtrar"
						onClick="javascript:validarForm()" /></td>

				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
	<jsp:include
		page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioAcompanhamentoOrdemServicoHidrometroAction.do" />
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>

<script>
	controleDataEncerramento();
</script>

</body>
</html:html>
