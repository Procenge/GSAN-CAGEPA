<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="InserirResolucaoDiretoriaActionForm"
	dynamicJavascript="true" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">
<!-- Begin

function validarForm(form){
	if(testarCampoValorZero(document.InserirResolucaoDiretoriaActionForm.numero, 'Número da RD') 
	        && testarCampoValorZero(document.InserirResolucaoDiretoriaActionForm.assunto, 'Assunto da RD')
	        && testarCampoValorZero(document.InserirResolucaoDiretoriaActionForm.dataInicio)) {
		
		if(validateInserirResolucaoDiretoriaActionForm(form)){
  				submeterFormPadrao(form);			
		}
	}	
}

function adicionaGrupo(){
	document.forms[0].target='';
	var indiceSelecionado = document.forms[0].idsGruposAcessoDisponiveis.selectedIndex;
	if(indiceSelecionado>0){				
			var grupoAAdicionar = document.forms[0].idsGruposAcessoDisponiveis.options[indiceSelecionado].value; 
			redirecionarSubmit('exibirInserirResolucaoDiretoriaAction.do?adicionaGrupoAcesso='+grupoAAdicionar);				
	}else{
		alert("É necessário selecionar um grupo de acesso para adicionar.");
	}		
}

function removeGrupo(pGrupo){

	document.forms[0].target='';
	if(confirm('Confirma remoção Grupo código '+pGrupo+' ?')){
		redirecionarSubmit('exibirInserirResolucaoDiretoriaAction.do?removeGrupoAcesso='+pGrupo);
	}		
}

function removeCondicaoValorEntrada(pCondicaoValorEntrada){

	document.forms[0].target='';
	redirecionarSubmit('exibirInserirResolucaoDiretoriaAction.do?removeCondicaoValorEntrada='+pCondicaoValorEntrada);
}

function adicionaRestricaoDebito(){
	document.forms[0].target='';
	var form = document.forms[0];
	if(form.idLocalidade.value != "" && form.anoMesReferenciaDebitoInicio.value != "" && form.anoMesReferenciaDebitoFim.value != ""){				
			redirecionarSubmit('exibirInserirResolucaoDiretoriaAction.do?adicionaRestricaoDebito='+1);				
	}else{
		alert("É necessário informar a localidade e período de referência para adicionar.");
	}		
}

function adicionaCondicaoPagamentoAVista(){
	document.forms[0].target='';
	var form = document.forms[0];
	var msg = "";
	
	if(form.dataPagamentoInicio.value == "") {
		msg = "É necessário informar a Data de Pagamento Inicial";
	} else if(form.dataPagamentoFinal.value == "") {
		msg = "É necessário informar a Data de Pagamento Final";		
	} else if(form.percentualDescontoMulta.value == "") {
		msg = "É necessário informar o Percentual de Desconto na Multa";		
	} else if(form.percentualDescontoJurosMora.value == "") {
		msg = "É necessário informar o Percentual de Desconto nos Juros Mora";		
	} else if(form.percentualDescontoCorrecaoMonetaria.value == "") {
		msg = "É necessário informar o Percentual de Desconto na Correção Monetária";		
	}
	
	if(msg != "") {
		alert(msg);
		return;
	}
	
	redirecionarSubmit('exibirInserirResolucaoDiretoriaAction.do?adicionaCondicaoPagamentoAVista='+1);				
			
}

function adicionaCondicaoValorEntrada(){
	document.forms[0].target='';
	var form = document.forms[0];
	var msg = "";
	
	if(form.dataNegociacaoInicio.value == "") {
		msg = "É necessário informar a Data de Negociação Inicial";
	} else if(form.dataNegociacaoFinal.value == "") {
		msg = "É necessário informar a Data de Negociação Final";		
	} else if(form.percentualMinimoEntrada.value == "") {
		msg = "É necessário informar o Percentual Mínimo do Valor de Entrada";		
	}
	
	if(msg != "") {
		alert(msg);
		return;
	}
	
	redirecionarSubmit('exibirInserirResolucaoDiretoriaAction.do?adicionaCondicaoValorEntrada='+1);				
			
}

function removeCondicaoPagtoAVista(pCondicaoPagtoAVista){

	document.forms[0].target='';
	redirecionarSubmit('exibirInserirResolucaoDiretoriaAction.do?removeCondicaoPagtoAVista='+pCondicaoPagtoAVista);
}


function removeRestricaoDebito(pParcelamentoSituacaoEspecial){

	document.forms[0].target='';
	redirecionarSubmit('exibirInserirResolucaoDiretoriaAction.do?removeRestricaoDebito='+pParcelamentoSituacaoEspecial);
}

function limparFormulario(){
	
	document.forms[0].target='';
	if(confirm('Deseja realmente limpar os dados do formulário ?')){
		var form = document.forms[0];

		form.indicadorUsoRDParcImovel[0].checked = false;
		form.indicadorUsoRDParcImovel[1].checked = true;
		form.indicadorUsoRDUsuarios[0].checked = false;
		form.indicadorUsoRDUsuarios[1].checked = true;
		form.indicadorUsoRDDebitoCobrar[0].checked = false;
		form.indicadorUsoRDDebitoCobrar[1].checked = true;
		form.indicadorEmissaoAssuntoConta[0].checked = false;
		form.indicadorEmissaoAssuntoConta[1].checked = true;
		form.indicadorTrataMediaAtualizacaoMonetaria[0].checked = false;
		form.indicadorTrataMediaAtualizacaoMonetaria[1].checked = true;
		redirecionarSubmit('exibirInserirResolucaoDiretoriaAction.do?desfazer='+1);
	}
}

function inicializarCampos(){
	var form = document.forms[0];
	if (form.indicadorUsoRDUsuarios[0].checked == true) {
		form.idsGruposAcessoDisponiveis.value = '-1';
		form.idsGruposAcessoDisponiveis.disabled = true;
	} 
}	

function bloquearGruposAcesso(){
	document.forms[0].target='';
	limparLocalidade();
	redirecionarSubmit('exibirInserirResolucaoDiretoriaAction.do?limparGrupos='+1);	
}

function desbloquearGruposAcesso(){
	var form = document.forms[0];
	form.idsGruposAcessoDisponiveis.disabled = false;
}

function limparLocalidade() {

    var form = document.forms[0];

    form.idLocalidade.value = "";
    form.nomeLocalidade.value = "";
    form.nomeLocalidade.style.color = "#000000";
}

function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){

	if(objetoRelacionado.readOnly != true){

		if (objeto == null || codigoObjeto == null){

			abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
		} else{

			if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){

				alert(msg);
			} else{

				abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
			}
		}
	}
}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.forms[0];
    
    if (tipoConsulta == 'imovel') {

      form.matriculaImovel.value = codigoRegistro;
      form.inscricaoImovel.value = descricaoRegistro;
      form.inscricaoImovel.style.color = "#000000";
      form.matriculaImovel.disabled = false;
	  form.codigoClienteGuia.value = '';
	  form.nomeClienteGuia.value = '';
	  form.codigoClienteGuia.disabled = true;
	  form.codigoClienteResponsavel.disabled = false;
	  form.idTipoRelacao.disabled = false;
    
    } else if (tipoConsulta == 'cliente' && form.indicadorPesquisaClienteResponsavel.value == 'true') {

	    form.codigoClienteResponsavel.value = codigoRegistro;
	    form.nomeClienteResponsavel.value = descricaoRegistro;
      	form.nomeClienteResponsavel.style.color = "#000000";
      	form.indicadorPesquisaClienteResponsavel.value = 'false';
      	form.idTipoRelacao.selectedIndex = -1;
		form.idTipoRelacao.disabled = true;
     
	    }else if (tipoConsulta == 'cliente' && form.indicadorPesquisaClienteGuia.value == 'true'){

	   	    form.codigoClienteGuia.value = codigoRegistro;
    	form.nomeClienteGuia.value = descricaoRegistro;
  		form.nomeClienteGuia.style.color = "#000000";
  		form.indicadorPesquisaClienteGuia.value = 'false';
  		form.matriculaImovel.value = '';
		form.inscricaoImovel.value = '';
		form.matriculaImovel.disabled = true;
		form.codigoClienteGuia.disabled = false;
		form.codigoClienteResponsavel.value = '';
		form.nomeClienteResponsavel.value = '';	
		form.codigoClienteResponsavel.disabled = true;
		form.idTipoRelacao.selectedIndex = -1;
		form.idTipoRelacao.disabled = true;
	    }else if (tipoConsulta == 'localidade'){

	    	form.idLocalidade.value = codigoRegistro;
	        form.nomeLocalidade.value = descricaoRegistro;
	        form.nomeLocalidade.style.color = "#000000";
	    }
}

-->
</script>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('${requestScope.nomeCampo}');inicializarCampos();">

<html:form action="/inserirResolucaoDiretoriaAction"
	name="InserirResolucaoDiretoriaActionForm"
	type="gcom.gui.cobranca.InserirResolucaoDiretoriaActionForm"
	method="post">

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
			<td width="625" valign="top" bgcolor="#003399" class="centercoltext">
			<table height="100%">

				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Inserir Resolu&ccedil;&atilde;o de Diretoria</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>
			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para adicionar a resolu&ccedil;&atilde;o de
					diretoria, informe os dados abaixo:</td>
				</tr>
				<tr>
					<td width="218"><strong>N&uacute;mero RD:<font color="#FF0000">*</font></strong></td>
					<td width="393"><html:text property="numero" size="15"
						maxlength="15" /></td>
				</tr>
				<tr>
					<td><strong>Assunto RD:<font color="#FF0000">*</font></strong></td>
					<td><html:text property="assunto" size="50" maxlength="50" /></td>
				</tr>
				<tr>
					<td height="25"><strong>Data In&iacute;cio Vig&ecirc;ncia RD:<font
						color="#FF0000">*</font></strong></td>
					<td align="right">
					<div align="left"><html:text property="dataInicio" size="10"
						maxlength="10" onkeyup="mascaraData(this, event);" /> <img
						border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário"
						onclick="javascript:abrirCalendario('InserirResolucaoDiretoriaActionForm', 'dataInicio')" />
					dd/mm/aaaa</div>
					</td>
				</tr>
				<tr>
					<td height="25"><strong>Data T&eacute;rmino Vig&ecirc;ncia RD:</strong></td>
					<td align="right">
					<div align="left"><html:text property="dataFim" size="10"
						maxlength="10" onkeyup="mascaraData(this, event);" /> <img
						border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário"
						onclick="javascript:abrirCalendario('InserirResolucaoDiretoriaActionForm', 'dataFim')" />
					dd/mm/aaaa</div>
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Termo de Confissão de Divida:</strong>
					</td>                 
                 	<td colspan="3">
                 			<html:select property="idsResolucaoDiretoriaLayout"  style="width: 350px;">
                 				<html:option value="-1">&nbsp;</html:option>
              					<html:options name="session" collection="colecaoResolucaoDiretoriaLayout" labelProperty="descricao" property="id" />
             				</html:select>
                 	</td>
                </tr>

				<tr>
					<td width="50%"><strong>Uso da RD é único por parcelamento/imóvel?</strong></td>
					<td><html:radio property="indicadorUsoRDParcImovel" value="1"/>Sim
						<html:radio	property="indicadorUsoRDParcImovel" value="2"/>Não
					</td>
				</tr>
				<tr>
					<td width="50%"><strong>Uso da RD é permitido para todos os usuários?</strong></td>
					<td><html:radio property="indicadorUsoRDUsuarios" value="1" onclick="bloquearGruposAcesso()"/>Sim
						<html:radio	property="indicadorUsoRDUsuarios" value="2" onclick="desbloquearGruposAcesso()"/>Não
					</td>
				</tr>
				<tr>
					<td width="50%"><strong>RD permite desconto nos débitos a cobrar referente a sanções e consumo fraudado? </strong></td>
					<td><html:radio property="indicadorUsoRDDebitoCobrar" value="1"/>Sim
						<html:radio	property="indicadorUsoRDDebitoCobrar" value="2"/>Não
					</td>
				</tr>
                				
				<tr>
					<td width="50%"><strong>RD emite o assunto da resolução de diretoria nas contas? </strong></td>
					<td><html:radio property="indicadorEmissaoAssuntoConta" value="1"/>Sim
						<html:radio	property="indicadorEmissaoAssuntoConta" value="2"/>Não
					</td>
				</tr>

				<tr>
					<td width="50%"><strong>A média da atualização monetária dos últimos 12 (doze) meses deve ser acrescida à taxa de juros do parcelamento? </strong></td>
					<td><html:radio property="indicadorTrataMediaAtualizacaoMonetaria" value="1"/>Sim
						<html:radio	property="indicadorTrataMediaAtualizacaoMonetaria" value="2"/>Não
					</td>
				</tr>
				<tr>
					<td width="50%"><strong>RD permite cobrança de descontos concedidos no parcelamento? </strong></td>
					<td><html:radio property="indicadorCobrarDescontosArrasto" value="1"/>Sim
						<html:radio	property="indicadorCobrarDescontosArrasto" value="2"/>Não
					</td>
				</tr>
				<tr>
					<td width="50%"><strong>Realiza arrasto? </strong></td>
					<td><html:radio property="indicadorArrasto" value="1"/>Sim
						<html:radio	property="indicadorArrasto" value="2"/>Não
					</td>
				</tr>
				<tr>
					<td>
					<p>&nbsp;</p>
					</td>
				</tr>
   				<tr bgcolor="#49A3FC">
					<td colspan="4" align="center"><strong>Grupos de Acesso com Permissão de Uso da RD</strong></td>
				</tr>
				<tr>
					<td>
					<p>&nbsp;</p>
					</td>
				</tr>
				 <tr>
                 	<td>
						<strong>Grupo de Acesso:</strong>
					</td>
					<td>
               			<html:select property="idsGruposAcessoDisponiveis"  style="width: 250px;">
               				<html:option value="-1">&nbsp;</html:option>
            					<html:options name="session" collection="colecaoGruposAcessoDisponiveis" labelProperty="descricao" property="id" />
           				</html:select>
                 	</td>	
					<td align="left" colspan="3">																	
							<input type="button" name="btAdicionar" class="bottonRightCol" value="Adicionar" onClick="javascript:adicionaGrupo();" />						
					</td>	
				</tr>
				<tr>
					<td>
					<p>&nbsp;</p>
					</td>
				</tr>			
			
			<logic:notEmpty name="colecaoGruposAcessoHabilitados">
				<tr>
					<td colspan="4">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td height="0">
							<table width="100%" bgcolor="#90c7fc">
								<!--header da tabela interna -->
								<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
									<td width="10%"><strong>Remover</strong></td>
									<td width="10%"><strong>Código</strong></td>
									<td width="80%"><strong>Descrição</strong></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td height="83">
							<div style="width: 100%; height: 100%; overflow: auto;">
							<table width="100%" align="left" bgcolor="#99CCFF">
								<!--corpo da segunda tabela-->
								<%int cont = 0;%>

									<logic:iterate name="colecaoGruposAcessoHabilitados"
										id="grupoAcessoHabilitado" >
										<%cont = cont + 1;
										  if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
										<%} else {%>
										<tr bgcolor="#FFFFFF">
											<%}%>
											<td width="10%">
    											<div align="center">
	 											   <font color="#333333"> <img width="14" height="14" border="0" src="<bean:message key="caminho.imagens"/>Error.gif"
												onclick="javascript:removeGrupo(<bean:write name="grupoAcessoHabilitado" property="id" />)" />	
												</font></div>
											</td>
											<td width="10%" align="center">
												<bean:write	name="grupoAcessoHabilitado" property="id" />
											</td>
											<td width="80%">
												<bean:write	name="grupoAcessoHabilitado" property="descricao" />
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
			</logic:notEmpty>
				<tr>
					<td>
					<p>&nbsp;</p>
					</td>
				</tr>
				<tr bgcolor="#49A3FC">
					<td colspan="4" align="center"><strong>Restrições da RD por Localidade e Referência do Débito</strong></td>
				</tr>
				<tr>
					<td>
					<p>&nbsp;</p>
					</td>
				</tr>
				<tr>	
					<td width="26%"><strong>Localidade:</strong></td>
					
					<td colspan="2" width="74%">
						
						<html:text maxlength="3" 
							tabindex="6"
							property="idLocalidade" 
							size="3"
							onkeypress="javascript:validaEnterComMensagem(event, 'exibirInserirResolucaoDiretoriaAction.do?objetoConsulta=1','idLocalidade','Localidade');"/>
							
						<a href="javascript: chamarPopup('exibirPesquisarLocalidadeAction.do', 'localidade', null, null, 250, 495, '', document.forms[0].idLocalidade);">
						<img width="23" 
							height="21" 
							border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar Localidade" /></a> 			
	
						<logic:present name="idLocalidadeEncontrado">
							<html:text property="nomeLocalidade" 
								size="45"
								maxlength="45" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 
	
						<logic:notPresent name="idLocalidadeEncontrado">
							<html:text property="nomeLocalidade" 
								size="45"
								maxlength="45" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>
						
						<a href="javascript:limparLocalidade();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" />
						</a>			
					</td>
				</tr>
				<tr>
					<td>
						<strong>Período de Referência do Débito:</strong>
					</td>
					<td>
						<span class="style2">
							<html:text maxlength="7"
								property="anoMesReferenciaDebitoInicio" 
								size="7"
								onkeyup="mascaraAnoMes(this, event);replicarCampo(document.forms[0].anoMesReferenciaDebitoFim, document.forms[0].anoMesReferenciaDebitoInicio);"
								tabindex="7" /> &nbsp; 
							<strong>a</strong> &nbsp; 
							<html:text maxlength="7"
								property="anoMesReferenciaDebitoFim" 
								size="7"
								onkeyup="mascaraAnoMes(this, event);" 
								tabindex="8" />
							</strong>(mm/aaaa)<strong>
						</span>
					</td>
					<td align="left" colspan="3">																	
							<input type="button" name="btAdicionar" class="bottonRightCol" value="Adicionar" onClick="javascript:adicionaRestricaoDebito();" />						
					</td>	
				</tr>
				<tr>
					<td>
					<p>&nbsp;</p>
					</td>
				</tr>			
			
	<logic:notEmpty name="colecaoRestricaoDebitoSelecionados">
				<tr>
					<td colspan="4">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td height="0">
							<table width="100%" bgcolor="#90c7fc">
								<!--header da tabela interna -->
								<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
									<td width="10%"><strong>Remover</strong></td>
									<td width="20%"><strong>Localidade</strong></td>
									<td width="35%"><strong>Referência do Débito Inicial</strong></td>
									<td width="35%"><strong>Referência do Débito Final</strong></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td height="83">
							<div style="width: 100%; height: 100%; overflow: auto;">
							<table width="100%" align="left" bgcolor="#99CCFF">
								<!--corpo da segunda tabela-->
								<%int cont = 0;%>

									<logic:iterate name="colecaoRestricaoDebitoSelecionados"
										id="parcelamentoSituacaoEspecial" >
										<%cont = cont + 1;
										  if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
										<%} else {%>
										<tr bgcolor="#FFFFFF">
											<%}%>
											<td width="10%">
    											<div align="center">
	 											   <font color="#333333"> <img width="14" height="14" border="0" src="<bean:message key="caminho.imagens"/>Error.gif"
												onclick="javascript:removeRestricaoDebito(<bean:write name="parcelamentoSituacaoEspecial" property="localidade.id" />)" />	
												</font></div>
											</td>
											<td width="20%" align="center">
												<bean:write	name="parcelamentoSituacaoEspecial" property="localidade.id" />
											</td>
											<td width="35%">
												<bean:write	name="parcelamentoSituacaoEspecial" property="formatarAnoMesParaMesAnoReferenciaInicio" />
											</td>
											<td width="35%">
												<bean:write	name="parcelamentoSituacaoEspecial" property="formatarAnoMesParaMesAnoReferenciaFim" />
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
	</logic:notEmpty>
	
	<!-- Condições para Pagamento à Vista -->
	
				<tr>
					<td>
					<p>&nbsp;</p>
					</td>
				</tr>
				<tr bgcolor="#49A3FC">
					<td colspan="4" align="center"><strong>Condições para Pagamento à Vista</strong></td>
				</tr>
				<tr>
					<td>
					<p>&nbsp;</p>
					</td>
				</tr>
				<tr>
					<td height="25"><strong>Data de Pagamento Inicial:</strong></td>
					<td align="right">
					<div align="left"><html:text property="dataPagamentoInicio" size="10"
						tabindex="10" maxlength="10" onkeyup="mascaraData(this, event);" /> <img
						border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário"
						onclick="javascript:abrirCalendario('InserirResolucaoDiretoriaActionForm', 'dataPagamentoInicio')" />
					dd/mm/aaaa</div>
					</td>
				</tr>
				<tr>
					<td height="25"><strong>Data de Pagamento Final:</strong></td>
					<td align="right">
					<div align="left"><html:text property="dataPagamentoFinal" size="10"
						tabindex="11" maxlength="10" onkeyup="mascaraData(this, event);" /> <img
						border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário"
						onclick="javascript:abrirCalendario('InserirResolucaoDiretoriaActionForm', 'dataPagamentoFinal')" />
					dd/mm/aaaa</div>
					</td>
				</tr>
				<tr>
					<td height="25"><strong>Percentual de Desconto na Multa:</strong></td>
					<td align="right">
					<div align="left"><html:text property="percentualDescontoMulta" size="6" maxlength="6" tabindex="12" onkeypress="return isCampoNumerico(event);" 
                		onkeyup="formataValorMonetario(this, 5)"
                		style="text-align:right;" />
					</div>
					</td>
				</tr>
				<tr>
					<td height="25"><strong>Percentual de Desconto nos Juros Mora:</strong></td>
					<td align="right">
					<div align="left"><html:text property="percentualDescontoJurosMora" size="6" maxlength="6" tabindex="13" onkeypress="return isCampoNumerico(event);" 
                		onkeyup="formataValorMonetario(this, 5)"
                		style="text-align:right;" />
					</div>
					</td>
				</tr>
				<tr>
					<td height="25"><strong>Percentual de Desconto na Correção Monetária:</strong></td>
					<td align="right">
					<div align="left"><html:text property="percentualDescontoCorrecaoMonetaria" size="6" maxlength="6" tabindex="14"onkeypress="return isCampoNumerico(event);" 
                		onkeyup="formataValorMonetario(this, 5)"
                		style="text-align:right;" />
					</div>
					</td>
				</tr>
				<tr>
					<td height="25"><strong>Mensagem para Exibir no Extrato:</strong></td>
					<td align="right">
					<div align="left"><html:textarea property="descricaoMensagemExtrato" cols="30" rows="4" tabindex="15" onkeyup="validarTamanhoMaximoTextArea(this,250);"/>
					</div>
					</td>
					<td align="left" valign="bottom" colspan="3">																	
							<input type="button" name="btAdicionar" class="bottonRightCol" value="Adicionar" onClick="javascript:adicionaCondicaoPagamentoAVista();" />						
					</td>	
				</tr>
				<tr>
					<td>
					<p>&nbsp;</p>
					</td>
				</tr>			
			
	<logic:notEmpty name="colecaoCondicaoPagamentoAVistaSelecionados">
				<tr>
					<td colspan="4">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td height="0">
							<table width="100%" bgcolor="#90c7fc">
								<!--header da tabela interna -->
								<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
									<td width="10%"><strong>Remover</strong></td>
									<td width="15%"><strong>Data de Pagamento Inicial</strong></td>
									<td width="15%"><strong>Data de Pagamento Final</strong></td>
									<td width="15%"><strong>Perc. Desconto na Multa</strong></td>
									<td width="15%"><strong>Perc. Desconto nos Juros Mora</strong></td>
									<td width="15%"><strong>Perc. Desconto na Correção Monetária</strong></td>
									<td width="15%"><strong>Mensagem para Exibir no Extrato</strong></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td height="83">
							<div style="width: 100%; height: 100%; overflow: auto;">
							<table width="100%" align="left" bgcolor="#99CCFF">
								<!--corpo da segunda tabela-->
								<%int cont = 0;%>

									<logic:iterate name="colecaoCondicaoPagamentoAVistaSelecionados"
										id="condicaoPagamentoAVista" >
										<%cont = cont + 1;
										  if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
										<%} else {%>
										<tr bgcolor="#FFFFFF">
											<%}%>
											<td width="10%">
    											<div align="center">
	 											   <font color="#333333"> <img width="14" height="14" border="0" src="<bean:message key="caminho.imagens"/>Error.gif"
												onclick="javascript:removeCondicaoPagtoAVista(<bean:write name="condicaoPagamentoAVista" property="formatarDataPagamentoInicioSemBarras" />)" />	
												</font></div>
											</td>
											<td width="15%" align="center">
												<bean:write	name="condicaoPagamentoAVista" property="formatarDataPagamentoInicio" />
											</td>
											<td width="15%" align="center">
												<bean:write	name="condicaoPagamentoAVista" property="formatarDataPagamentoFinal" />
											</td>
											<td width="15%" align="center">
												<bean:write	name="condicaoPagamentoAVista" property="formatarPercentualDescontoMulta" />
											</td>
											<td width="15%" align="center">
												<bean:write	name="condicaoPagamentoAVista" property="formatarPercentualDescontoJurosMora" />
											</td>
											<td width="15%" align="center">
												<bean:write	name="condicaoPagamentoAVista" property="formatarPercentualDescontoCorrecaoMonetaria" />
											</td>
											<td width="15%" align="center">
												<bean:write	name="condicaoPagamentoAVista" property="formatarDescricaoMensagemExtrato" />
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
	</logic:notEmpty>
	
	
		<!-- Condições para Valor de Entrada -->
	
				<tr>
					<td>
					<p>&nbsp;</p>
					</td>
				</tr>
				<tr bgcolor="#49A3FC">
					<td colspan="4" align="center"><strong>Condições para Valor de Entrada</strong></td>
				</tr>
				<tr>
					<td>
					<p>&nbsp;</p>
					</td>
				</tr>
				<tr>
					<td height="25"><strong>Data de Negociação Inicial:</strong></td>
					<td align="right">
					<div align="left"><html:text property="dataNegociacaoInicio" size="10"
						tabindex="16" maxlength="10" onkeyup="mascaraData(this, event);" /> <img
						border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário"
						onclick="javascript:abrirCalendario('InserirResolucaoDiretoriaActionForm', 'dataNegociacaoInicio')" />
					dd/mm/aaaa</div>
					</td>
				</tr>
				<tr>
					<td height="25"><strong>Data de Negociação Final:</strong></td>
					<td align="right">
					<div align="left"><html:text property="dataNegociacaoFinal" size="10"
						tabindex="17" maxlength="10" onkeyup="mascaraData(this, event);" /> <img
						border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário"
						onclick="javascript:abrirCalendario('InserirResolucaoDiretoriaActionForm', 'dataNegociacaoFinal')" />
					dd/mm/aaaa</div>
					</td>
				</tr>
				<tr>
					<td height="25"><strong>Percentual Mínimo de Entrada:</strong></td>
					<td align="right">
					<div align="left"><html:text property="percentualMinimoEntrada" size="6" maxlength="6" tabindex="18" onkeypress="return isCampoNumerico(event);" 
                		onkeyup="formataValorMonetario(this, 5)"
                		style="text-align:right;" />
					</div>
					</td>
					<td align="left" valign="bottom" colspan="3">																	
							<input type="button" name="btAdicionar" class="bottonRightCol" value="Adicionar" onClick="javascript:adicionaCondicaoValorEntrada();" />						
					</td>	
				</tr>
			
				<tr>
					<td>
					<p>&nbsp;</p>
					</td>
				</tr>			
			
	<logic:notEmpty name="colecaoCondicaoValorEntradaSelecionados">
				<tr>
					<td colspan="4">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td height="0">
							<table width="100%" bgcolor="#90c7fc">
								<!--header da tabela interna -->
								<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
									<td width="10%"><strong>Remover</strong></td>
									<td width="30%"><strong>Data de Negociação Inicial</strong></td>
									<td width="30%"><strong>Data de Negociação Final</strong></td>
									<td width="30%"><strong>Percentual Mínimo de Entrada</strong></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td height="83">
							<div style="width: 100%; height: 100%; overflow: auto;">
							<table width="100%" align="left" bgcolor="#99CCFF">
								<!--corpo da segunda tabela-->
								<%int cont = 0;%>

									<logic:iterate name="colecaoCondicaoValorEntradaSelecionados"
										id="condicaoValorEntrada" >
										<%cont = cont + 1;
										  if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
										<%} else {%>
										<tr bgcolor="#FFFFFF">
											<%}%>
											<td width="10%">
    											<div align="center">
	 											   <font color="#333333"> <img width="14" height="14" border="0" src="<bean:message key="caminho.imagens"/>Error.gif"
												onclick="javascript:removeCondicaoValorEntrada(<bean:write name="condicaoValorEntrada" property="formatarDataNegociacaoInicioSemBarras" />)" />	
												</font></div>
											</td>
											<td width="30%" align="center">
												<bean:write	name="condicaoValorEntrada" property="formatarDataNegociacaoInicio" />
											</td>
											<td width="30%" align="center">
												<bean:write	name="condicaoValorEntrada" property="formatarDataNegociacaoFinal" />
											</td>
											<td width="30%" align="center">
												<bean:write	name="condicaoValorEntrada" property="formatarPercentualMinimoEntrada" />
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
	</logic:notEmpty>
	
	
				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td align="right">
					<div align="left"><br><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios<br></div>
					</td>
				</tr>
	
	
	

				<tr>
					<td colspan="2"><br>
					<font color="#FF0000"> <input type="button" name="ButtonReset"
						class="bottonRightCol" value="Desfazer"
						onClick="javascript:limparFormulario();"> <input type="button"
						name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do';">
					</font></td>
					<td align="right">
					  <gcom:controleAcessoBotao name="Button" value="Inserir" onclick="javascript:validarForm(document.forms[0]);" url="inserirResolucaoDiretoriaAction.do"/>					  
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
