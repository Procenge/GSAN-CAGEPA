<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gcom.util.ConstantesSistema"%>
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
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="GerarRelatorioAnaliticoContasActionForm" />

<script language="JavaScript">

function validarForm(form){
	var form = document.forms[0];
		if(validateGerarRelatorioAnaliticoContasActionForm(form)){
			
			submeterFormPadrao(form);
		}
	}





function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.forms[0];

    if (tipoConsulta == 'localidade') {
      form.idLocalidade.value = codigoRegistro;
      form.nomeLocalidade.value = descricaoRegistro;
      form.nomeLocalidade.style.color = "#000000";
    }if(tipoConsulta == 'cliente'){
    	 form.idCliente.value = codigoRegistro;
         form.nomeCliente.value = descricaoRegistro;
         form.nomeCliente.style.color = "#000000";
    	
    }if (tipoConsulta == 'imovel'){
    	 form.idImovel.value = codigoRegistro;
         form.inscricao.value = descricaoRegistro;
         form.inscricao.style.color = "#000000";
    }if(tipoConsulta == 'setorComercial') {
        form.setorComercial.value = codigoRegistro;
        form.setorComercialNome.value = descricaoRegistro;
        form.setorComercialNome.style.color = "#000000";
    }if(tipoConsulta == 'quadra') {
        form.idQuadra.value = codigoRegistro;
        form.descricaoQuadra.value = codigoRegistro;
        form.descricaoQuadra.style.color = "#000000";
    }
    
   
    
}


function limparLocalidadeTecla(){
	var form = document.forms[0];

	form.nomeLocalidade.value = "";
}


function limparLocalidade(){
	var form = document.forms[0];

	form.idLocalidade.value = "";
	form.nomeLocalidade.value = "";
}

function limparCliente(){
	
	var form = document.forms[0];

	form.idCliente.value = "";
	form.nomeCliente.value = "";
	
	
}

function limparClienteTecla(){
	var form = document.forms[0];

	form.nomeCliente.value = "";
}


function limparImovel(){
	var form = document.forms[0];

	form.idImovel.value = "";
	form.inscricao.value = "";
}

function limparImovelTecla(){
	var form = document.forms[0];

	form.inscricao.value = "";
}


function limparSetorComercial(){
	var form = document.forms[0];

	form.setorComercial.value = "";
	form.setorComercialNome.value = "";
}

function limparidQuadra(){
	var form = document.forms[0];

	form.idQuadra.value = "";
	form.descricaoQuadra.value = "";
}


</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/gerarRelatorioAnaliticoContasAction"
	name="GerarRelatorioAnaliticoContasActionForm"
	type="gcom.gui.relatorio.faturamento.GerarRelatorioAnaliticoContasActionForm"
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
			<td width="600" valign="top" bgcolor="#003399" class="centercoltext">
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
					<td class="parabg">Gerar Relatórios Analítico de Contas </td>

					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
				<tr>
					<td height="5" colspan="3"></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td colspan="3">Para gerar o relatório analitico de contas ,
					informe os dados abaixo:</td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
		          <td><strong>Grupo de Faturamento:</strong> </td>
		          <td><html:select property="grupoFaturamento" tabindex="1">
		          	  <html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
		          	  <html:options collection="colecaoFaturamentoGrupo" labelProperty="descricao" property="id"/>
		             </html:select>
		          </td>
        </tr>
				<tr>
					<td><strong>Ger&ecirc;ncia Regional:</strong></td>
					<td><html:select property="idGerenciaRegional" tabindex="1">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoGerenciaRegional" labelProperty="nome" property="id" />
					</html:select>
					</td>
				</tr>
				<tr>
					<td><strong>Localidade:</strong></td>
					<td colspan="2"><html:text maxlength="3"
						property="idLocalidade" size="4" tabindex="3"
						onkeyup="javascript:limparLocalidadeTecla();document.forms[0].idLocalidade)"
						onkeypress="javascript:return validaEnter(event, 'exibirGerarRelatorioAnaliticoContasAction.do', 'idLocalidade');" />
					<a href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do?indicadorUsoTodos=1');">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Localidade" /></a> <logic:present
						name="idLocalidadeNaoEncontrada" scope="request">
						<html:text maxlength="40" property="nomeLocalidade"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000"
							size="40" />
					</logic:present> <logic:notPresent
						name="idLocalidadeNaoEncontrada" scope="request">
						<html:text maxlength="40" property="nomeLocalidade"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000"
							size="40" />
					</logic:notPresent> <a href="javascript:limparLocalidade();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				<tr>
					<td><strong>Setor Comercial:</strong></td>
					<td colspan="2"><html:text maxlength="10"
						property="setorComercial" size="4" tabindex="3"
						onkeyup="javascript:limparsetorComercial();document.forms[0].setorComercial)"
						onkeypress="javascript:return validaEnter(event, 'exibirGerarRelatorioAnaliticoContasAction.do', 'setorComercial');" />
						
					<a href="javascript:abrirPopup('exibirPesquisarSetorComercialAction.do?indicadorUsoTodos=1');">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Setor Comércial" /></a>
				    <logic:present
						name="idSetorComercialNaoEncontrado" scope="request">
						<html:text maxlength="40" property="setorComercialNome"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000"
							size="40" />
					</logic:present> <logic:notPresent
						name="idSetorComercialNaoEncontrado" scope="request">
						<html:text maxlength="40" property="setorComercialNome"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000"
							size="40" />
					</logic:notPresent> <a href="javascript:limparSetorComercial();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				
				
				<tr>
					<td><strong>Quadra:</strong></td>
					<td colspan="2"><html:text maxlength="10"
						property="idQuadra" size="4" tabindex="3"
						onkeypress="javascript:return validaEnter(event, 'exibirGerarRelatorioAnaliticoContasAction.do', 'idQuadra');" />
					<a href="javascript:abrirPopup('exibirPesquisarQuadraAction.do?indicadorUsoTodos=1');">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Quadra" /></a>
				    <logic:present
						name="idQuadraNaoEncontrado" scope="request">
						<html:text maxlength="40" property="descricaoQuadra"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000"
							size="40" />
					</logic:present> <logic:notPresent
						name="idQuadraNaoEncontrado" scope="request">
						<html:text maxlength="40" property="descricaoQuadra"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000"
							size="40" />
					</logic:notPresent> <a href="javascript:limparidQuadra();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				
				
				<tr>
					<td><strong>Categoria:</strong></td>
					<td><html:select property="idCategoria" tabindex="1">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoCategoria"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				<tr>
					<td><strong>Cliente:</strong></td>
					<td colspan="2"><html:text maxlength="10"
						property="idCliente" size="4" tabindex="3"
						onkeypress="javascript:return validaEnter(event, 'exibirGerarRelatorioAnaliticoContasAction.do', 'idCliente');" />
					<a				
						href="javascript:abrirPopup('exibirPesquisarClienteAction.do?indicadorUsoTodos=1');">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Cliente" /></a>
				    <logic:present
						name="idClienteNaoEncontrada" scope="request">
						<html:text maxlength="40" property="nomeCliente"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000"
							size="40" />
					</logic:present> <logic:notPresent
						name="idClienteNaoEncontrada" scope="request">
						<html:text maxlength="40" property="nomeCliente"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000"
							size="40" />
					</logic:notPresent> <a href="javascript:limparCliente();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				<tr>
					<td><strong>Referência:<font color="#FF0000">*</font></strong></td>
					<td colspan="2"><html:text maxlength="7"
						property="referencia" size="7"
						onkeyup="mascaraAnoMes(this, event);replicarCampo(document.forms[0].referenciaFinal, document.forms[0].referencia)"
						tabindex="7" />
					</td>
				</tr>
				
				<tr>
					<td><strong>Imóvel:</strong></td>
					<td colspan="2"><html:text maxlength="10"
						property="idImovel" size="4" tabindex="3"
						onkeyup="javascript:limparImovelTecla(); document.forms[0].idImovel)"
						onkeypress="javascript:return validaEnter(event, 'exibirGerarRelatorioAnaliticoContasAction.do', 'idImovel');" />
					<a
						href="javascript:abrirPopup('exibirPesquisarImovelAction.do?indicadorUsoTodos=1');">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Imóvel" /></a> <logic:present
						name="idImovelNaoEncontrada" scope="request">
						<html:text maxlength="40" property="inscricao"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000"
							size="40" />
					</logic:present> <logic:notPresent
						name="idImovelNaoEncontrada" scope="request">
						<html:text maxlength="40" property="inscricao"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000"
							size="40" />
					</logic:notPresent> <a href="javascript:limparImovel();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				
				<tr>
					<td><strong>Situação da Conta:</strong></td>
					<td><html:select property="idSituacao" tabindex="1">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoSituacao"
							labelProperty="descricaoDebitoCreditoSituacao" property="id" />
					</html:select></td>
				</tr>
				
				
				
				<tr>
					<td><strong>Motivo de Retificação:</strong></td>
					<td><html:select property="motivoRetificacao" >
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoMotivoRetificacao"
							labelProperty="descricaoMotivoRetificacaoConta" property="id" />
					</html:select></td>
				</tr>
				
				<tr>
					<td><strong> <font color="#FF0000">* Campo obrigatório</font></strong></td>
					<td align="right">
					<div align="left" colspan="2"><strong></strong>
					</div>
					</td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td valign="top"><input name="button" type="button"
						class="bottonRightCol" value="Limpar" tabindex="4"
						onclick="window.location.href='/gsan/exibirGerarRelatorioAnaliticoContasAction.do?menu=sim'">&nbsp;</td>
					<td valign="top">
					<div align="right"><input name="button" type="button"
						class="bottonRightCol" value="Gerar"
						onclick="validarForm(document.forms[0]);" tabindex="5"></div>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>
</body>
</html:form>
</html:html>
