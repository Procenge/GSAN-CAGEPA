<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false" formName="GerarRelatorioAnormalidadeConsumoActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">


function limparEloTecla(){
	var form = document.forms[0];
	
	form.nomeElo.value = "";
}

function limparElo(){
	var form = document.forms[0];
	form.idElo.value = "";
	form.nomeElo.value = "";
}

function limparLocalidadeInicial() {
	var form = document.GerarRelatorioAnormalidadeConsumoActionForm;
	form.idLocalidadeInicial.value = "";
	form.nomeLocalidadeInicial.value = "";	
}

function limparLocalidadeFinal() {
	var form = document.GerarRelatorioAnormalidadeConsumoActionForm;
	form.idLocalidadeFinal.value = "";
	form.nomeLocalidadeFinal.value = "";	
}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.forms[0];
   
    if (tipoConsulta == 'elo') {
      form.idElo.value = codigoRegistro;
      form.nomeElo.value = descricaoRegistro;
      form.nomeElo.style.color = "#000000";
      form.idLocalidadeInicial.focus();
    }
   
     if (tipoConsulta == 'localidade') {
     
     if (form.tipoPesquisa.value == 'inicial') {
      form.idLocalidadeInicial.value = codigoRegistro;
      form.nomeLocalidadeInicial.value = descricaoRegistro;
      form.nomeLocalidadeInicial.style.color = "#000000";
      form.idLocalidadeFinal.value = codigoRegistro;
      form.nomeLocalidadeFinal.value = descricaoRegistro;
      form.nomeLocalidadeFinal.style.color = "#000000";
      form.idLocalidadeFinal.focus();
    } else {
 	  form.idLocalidadeFinal.value = codigoRegistro;
      form.nomeLocalidadeFinal.value = descricaoRegistro;
      form.nomeLocalidadeFinal.style.color = "#000000";
      form.referencia.focus();
    }
    
    }
    
}

function verificarAnoMesReferencia(){
		var form = GerarRelatorioAnormalidadeConsumoActionForm;
       	
       	if (form.referencia.value.length > 0){
       		form.action = 'exibirGerarRelatorioAnormalidadeConsumoAction.do';
       		form.submit();
       	}
       	
}


function verificarNumOcorrenciasConsecutivas(){
		var form = GerarRelatorioAnormalidadeConsumoActionForm;
		
		if (form.numOcorrenciasConsecutivas.value.length > 0){
			form.action = 'exibirGerarRelatorioAnormalidadeConsumoAction.do';
       		form.submit();
		}
       	
}

function replicaDados(campoOrigem, campoDestino){
	campoDestino.value = campoOrigem.value;
}
	
function limparLeituraAnormalidade() {
	var form = document.GerarRelatorioAnormalidadeConsumoActionForm;
	form.idLeituraAnormalidade.value = "";
	form.descricaoLeituraAnormalidade.value = "";	
}

function chamarPesquisaLocalidadeInicial() {
	document.forms[0].tipoPesquisa.value = 'inicial';
	abrirPopup('exibirPesquisarLocalidadeAction.do?tipo=imovelLocalidade', 400, 800);
}

function chamarPesquisaLocalidadeFinal() {
	document.forms[0].tipoPesquisa.value = 'final';
	abrirPopup('exibirPesquisarLocalidadeAction.do?tipo=imovelLocalidade', 400, 800);
}

function validarForm(form){
		
	if(validateGerarRelatorioAnormalidadeConsumoActionForm(form)){
		if(form.idLocalidadeInicial.value.length > 0 && form.idLocalidadeFinal.value.length == 0) {
			alert('Informe Localidade Final');
		} else if(form.idLocalidadeFinal.value.length > 0 && form.idLocalidadeInicial.value.length == 0) {
			alert('Informe Localidade Inicial');
		} else if(form.intervaloMediaConsumoInicial.value.length > 0 && form.intervaloMediaConsumoFinal.value.length == 0) {
			alert('Informe a Média de Consumo Final');
		} else if(form.intervaloMediaConsumoFinal.value.length > 0 && form.intervaloMediaConsumoInicial.value.length == 0) {
			alert('Informe a Média de Consumo Inicial');
		} else{
	    	submeterFormPadrao(form);
	    }
	}
}


</script>


</head>

<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('${requestScope.nomeCampo}');">

<html:form  action="/gerarRelatorioAnormalidadeConsumoAction" method="post" 
			name="GerarRelatorioAnormalidadeConsumoActionForm"
			type="gcom.gui.faturamento.GerarRelatorioAnormalidadeConsumoActionForm"
			onsubmit="return validateGerarRelatorioAnormalidadeConsumoActionForm(this);">
	
	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
	
	<input type="hidden" name="tipoPesquisa" />

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
			<td width="625" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>

			<!--Início Tabela Reference a Páginação da Tela de Processo-->
			<table>
				<tr>
					<td></td>

				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Relatório de Anormalidade</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>

			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>

					<td colspan="2">Para gerar Relatório de Anormalidade, informe os dados gerais
					abaixo:</td>
				</tr>
				<tr>
					<td width="200">
						<strong>Gerência Regional:</strong>
					</td>
					<td colspan="3">
						<html:select property="regional" tabindex="1" style="width:200px;">
							<html:option value="-1"> &nbsp; </html:option>
							<html:options collection="colecaoGerenciaRegional" property="id" labelProperty="nome" />
						</html:select>
					</td>
				</tr>
				
				<tr>
					<td width="200">
						<strong>Unidade de Negócio:</strong>
					</td>
					<td colspan="3">
						<html:select property="unidadeNegocio" tabindex="2" style="width:200px;">
							<html:option value="-1"> &nbsp; </html:option>
							<html:options collection="colecaoUnidadeNegocio" property="id" 
							labelProperty="nome" />
						</html:select>
					</td>
				</tr>
				
			  	<tr>
					<td width="26%">
						<strong>Elo:</strong>
					</td>
					<td width="74%">
						<html:text property="idElo" size="5" maxlength="3" tabindex="3" onkeyup="limparEloTecla();"
						onkeypress="validaEnterComMensagem(event,'exibirGerarRelatorioAnormalidadeConsumoAction.do','idElo','Elo');"/>
						
						<a href="javascript:abrirPopup('exibirPesquisarEloAction.do?indicadorUsoTodos=1');">
							<img border="0" src="imagens/pesquisa.gif" height="21" width="23"></a>
						
						<logic:present name="idEloNaoEncontrado" scope="request">
							<html:text property="nomeElo" readonly="true" size="40" maxlength="40"
							style="background-color:#EFEFEF; border:0; color: #ff0000"/>
						</logic:present> 
							
						<logic:notPresent name="idEloNaoEncontrado" scope="request">
							<html:text property="nomeElo" readonly="true" maxlength="40"
							style="background-color:#EFEFEF; border:0" size="40" />
						</logic:notPresent> 
						<a href="javascript:limparElo();"> 
							<img border="0" src="imagens/limparcampo.gif" height="21" width="23"> 
						</a>
					</td>
				</tr>
				<tr>
					<td width="200">
						<strong>Grupo:</strong>
					</td>
					<td >
						<html:select property="grupo" tabindex="4" style="width:200px;">
							<html:option value="-1"> &nbsp; </html:option>
							<html:options collection="colecaoFaturamentoGrupo" property="id" labelProperty="descricao" />
						</html:select>
					</td>
				</tr>
				<tr>
					<td width="200">
						<strong>Rota:</strong>
					</td>

					<td colspan="3">
						<html:text  property="rota" size="7" maxlength="4" tabindex="5" /> 
					</td>
				</tr>
				<tr>
					<td width="200">
						<strong>Localidade Inicial:</strong>
					</td>
					<td colspan="3">
						<strong> 
						<html:text property="idLocalidadeInicial" size="5" maxlength="3" tabindex="6"
						onkeyup="replicaDados(document.forms[0].idLocalidadeInicial, document.forms[0].idLocalidadeFinal);"
						onkeypress="validaEnterComMensagem(event, 'exibirGerarRelatorioAnormalidadeConsumoAction.do', 'idLocalidadeInicial', 'Localidade Inicial');" />
						
						<a href="javascript:chamarPesquisaLocalidadeInicial();"><img
							width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar" />
						</a>
					
					<logic:present name="localidadeInicialInexistente" scope="request">
						<html:text property="nomeLocalidadeInicial" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000"
							size="40" maxlength="40" />
					</logic:present> 
					
					<logic:notPresent name="localidadeInicialInexistente" scope="request">
						<html:text property="nomeLocalidadeInicial" readonly="true"
							style="background-color:#EFEFEF; border:0" size="40"
							maxlength="40" />
					</logic:notPresent> 
					
						<a href="javascript:limparLocalidadeInicial();">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" /> 
						</a> 
						</strong>
					</td>
				</tr>

				<tr>
					<td width="200">
						<strong>Localidade Final:</strong>
					</td>
					<td colspan="3">
						<strong> 
						<html:text property="idLocalidadeFinal" size="5" maxlength="3" tabindex="7"
						onkeypress= "validaEnterComMensagem(event, 'exibirGerarRelatorioAnormalidadeConsumoAction.do', 'idLocalidadeFinal', 'Localidade Final');"/>
						 
						<a href="javascript:chamarPesquisaLocalidadeFinal();">
							<img border="0" src="imagens/pesquisa.gif" height="21" width="23">
						</a>
					
					<logic:present name="localidadeFinalInexistente" scope="request">
						<html:text property="nomeLocalidadeFinal" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000"
							size="40" maxlength="40" />
					</logic:present> 
					
					<logic:notPresent name="localidadeFinalInexistente" scope="request">
						<html:text property="nomeLocalidadeFinal" readonly="true"
							style="background-color:#EFEFEF; border:0" size="40"
							maxlength="40" />
					</logic:notPresent> 
					
						<a href="javascript:limparLocalidadeFinal();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" /> 
						</a> 
					</strong>
					</td>
				</tr>
                 
                 
                 <tr>
					<td width="200">
						<strong>Referência:<font color="#FF0000">*</font></strong>
					</td>

					<td colspan="3">
						<html:text  property="referencia" size="7" maxlength="7" tabindex="8"
									onkeyup="mascaraAnoMes(this, event);" /> (mm/aaaa)
					</td>
				</tr>
				
				<tr>
					<td width="200">
						<strong>Perfil do Imóvel:</strong>
					</td>
					<td >
						<html:select property="idImovelPerfil" tabindex="9" style="width:200px;">
							<html:option value="-1"> &nbsp; </html:option>
							<html:options collection="colecaoImovelPerfil" property="id" labelProperty="descricao" />
						</html:select>
					</td>
				</tr>
				
				<tr>
					<td width="200">
						<strong>Num. Ocorrências Consecutivas:</strong>
					</td>
					<td colspan="3">
						<html:text property="numOcorrenciasConsecutivas" size="3" maxlength="2" tabindex="10" 
						onblur="javascript:verificarNumOcorrenciasConsecutivas();" />
					</td>
				</tr>
				
				<tr>
					<td width="200">
						<strong>Ocorrências Iguais:</strong>
					</td>
					<td colspan="3">
						<strong><html:radio property="indicadorOcorrenciasIguais" value="1" tabindex="11" />Sim
						<html:radio property="indicadorOcorrenciasIguais" value="2" tabindex="12" />Não</strong>
					</td>
				</tr>

				<tr>
					<td width="200">
						<strong>Intervalo de Média de Consumo:</strong>
					</td>
					<td colspan="3">
						<div align="left">
							<strong> 
								<html:text property="intervaloMediaConsumoInicial" size="6" maxlength="6" tabindex="13" 
									onkeyup="replicaDados(document.forms[0].intervaloMediaConsumoInicial,
									document.forms[0].intervaloMediaConsumoFinal);" /> a 
								<html:text property="intervaloMediaConsumoFinal" size="6" maxlength="6" tabindex="14" /> 
							</strong>
						</div>
					</td>
				</tr>

				<tr>
					<td width="200">
						<strong>Anormalidade de Leitura:</strong>
					</td>
					<td>
						<html:select property="idLeituraAnormalidade" tabindex="15" style="width:200px;">
							<html:option value="-1"> &nbsp; </html:option>
							<html:options collection="colecaoLeituraAnormalidade" property="id" labelProperty="descricao"/>
						</html:select>
					</td>
				</tr>

				<tr>
					<td width="200">
						<strong>Anormalidade de Consumo:</strong>
					</td>
					<td>
						<html:select property="idConsumoAnormalidade" tabindex="16" style="width:200px;">
							<html:option value="-1"> &nbsp; </html:option>
							<html:options collection="colecaoConsumoAnormalidade" property="id" labelProperty="descricao"/>
						</html:select>
					</td>
				</tr>
				
				<tr>
					<td height="19">
						<strong> <font color="#FF0000"></font></strong>
					</td>
					<td align="right">
						<div align="left">
							<strong><font color="#FF0000">*</font></strong> Campos obrigat&oacute;rios
						</div>
					</td>
				</tr>
				
				<tr>
					<td><input name="Button" type="button" class="bottonRightCol"
						value="Limpar" align="left"
						onclick="window.location.href='/gsan/exibirGerarRelatorioAnormalidadeConsumoAction.do?menu=sim'" tabindex="17"></td>
					<td align="right" colspan="2">
						<input name="Button" type="button" class="bottonRightCol" value="Gerar" align="left" tabindex="16"
						onclick="validarForm(document.forms[0]);"></td>
					<td align="right"></td>
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

