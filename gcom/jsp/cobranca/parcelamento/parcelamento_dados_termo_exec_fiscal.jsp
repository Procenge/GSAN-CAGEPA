<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>

<%@ page import="gcom.util.Util"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false" formName="ParcelamentoDadosTermoActionForm" dynamicJavascript="false" />

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
	
<script language="JavaScript">
<!--

function limparClienteParcelamento() {
	var form = document.forms[0];
	form.idCliente.value = "";
	form.nomeCliente.value = "";
}

function habilitarPesquisaClienteParcelamento(form) {
	abrirPopup('exibirPesquisarClienteAction.do?indicadorUsoTodos=1','clienteParcelamento', null, null, 520, 800);
}


function limparCliente() {
	var form = document.forms[0];
	form.idClienteEmpresa.value = "";
	form.nomeClienteEmpresa.value = "";
}

function habilitarPesquisaCliente(form) {
	abrirPopup('exibirPesquisarClienteAction.do?indicadorUsoTodos=1', 520, 800);
}

function modificarAcordoTipoProcurador() {
	var form = document.forms[0];

	form.action = "/gsan/exibirParcelamentoDadosTermoExecFiscalAction.do?modificarAcordoTipoProcurador=sim"
	form.submit();
}

function validarForm(){
	var form = document.forms[0];

	if (validateParcelamentoTermoTestemunhasActionForm(form)) {
		if (validateDadosParcelamento(form)) {
			form.action = "/gsan/exibirParcelamentoDadosTermoExecFiscalAction.do?atualizarDadosTermo=sim"
			form.submit();
		}
	}
}


function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
    var form = document.forms[0];

    if (tipoConsulta == 'clienteParcelamento') {
        form.idCliente.value = codigoRegistro;
        form.nomeCliente.value = descricaoRegistro;
        form.action = '/gsan/exibirParcelamentoDadosTermoExecFiscalAction.do?pesquisaCliente=S'
	    form.submit();        
    } else { 
    	if (tipoConsulta == 'cliente') {
        	form.idClienteEmpresa.value = codigoRegistro;
        	form.nomeClienteEmpresa.value = descricaoRegistro;
        	form.action = '/gsan/exibirParcelamentoDadosTermoExecFiscalAction.do?modificarAcordoTipoProcurador=sim'
	    	form.submit();
    	}
    }
    
}



function validateDadosParcelamento(form){
	var validacao=true;
	var msg='';

	if (form.idParcelamentoAcordoTipo != null && form.idParcelamentoAcordoTipo.value == 5) {
		if (form.numeroProcesso == null || form.numeroProcesso.value == '') {
			msg = msg + 'Informe o Número do Processo.' + '\n';
			validacao=false;
		}
		
		if (form.numeroVara == null || form.numeroVara.value == '') {
			msg = msg + 'Informe o Número da Vara.' + '\n';
			validacao=false;
		}		
	}
	
	if (form.idParcelamentoAcordoTipo != null && (form.idParcelamentoAcordoTipo.value == 5 || form.idParcelamentoAcordoTipo.value == 6 
			|| form.idParcelamentoAcordoTipo.value == 10 || form.idParcelamentoAcordoTipo.value == 11)) {
		if (form.nomeExecutado == null || form.nomeExecutado.value == '') {
			msg = msg + 'Informe o nome do Executado.' + '\n';
			validacao=false;
		}
	}	
	
	if (form.idParcelamentoAcordoTipo != null && form.idParcelamentoAcordoTipo.value == 7) {
		if (form.tituloPosse == null || form.tituloPosse.value == '') {
			msg = msg + 'Informe o Título de Posse.' + '\n';
			validacao=false;
		}		
	}	
	
	if (form.idParcelamentoAcordoTipo != null && form.idParcelamentoAcordoTipo.value == 8) {
		if (form.idClienteEmpresaExistente == null || form.idClienteEmpresaExistente.value == '') {
			msg = msg + 'Informe a Empresa.' + '\n';
			validacao=false;
		}
		
		if (form.idRamoAtividadeClienteEmpresa == null || form.idRamoAtividadeClienteEmpresa.value == -1) {
			msg = msg + 'Informe o Ramo de Atividade da Empresa.' + '\n';
			validacao=false;
		}		
		
		if (form.nomeClienteEmpresa == null || form.nomeClienteEmpresa.value == '') {
			msg = msg + 'Informe o nome da Empresa.' + '\n';
			validacao=false;
		}
		
		if (form.numeroCnpjClienteEmpresa == null || form.numeroCnpjClienteEmpresa.value == '') {
			msg = msg + 'Informe o CPJ da Empresa.' + '\n';
			validacao=false;
		}	
		
		if (form.idEnderecoClienteEmpresa == null || form.idEnderecoClienteEmpresa.value == -1) {
			msg = msg + 'Informe o Endereço da Empresa.' + '\n';
			validacao=false;
		}		
		
		validateCnpj(form)
	}	
	
	if (form.indicadorProcurador != null && form.indicadorProcurador.value == 1) {
		if (form.nomeProcurador == null || form.nomeProcurador.value == '') {
			msg = msg + 'Informe o Nome do Procurador.' + '\n';
			validacao=false;
		}
		
		if (form.numeroRgProcurador == null || form.numeroRgProcurador.value == '') {
			msg = msg + 'Informe o RG do Procurador.' + '\n';
			validacao=false;
		}	
		
		if (form.idOrgaoExpedidorRgProcurador == null || form.idOrgaoExpedidorRgProcurador.value == -1) {
			msg = msg + 'Informe o Orgão Expedidor do RG do Procurador.' + '\n';
			validacao=false;
		}
		
		if (form.idUnidadeFederacaoProcurador == null || form.idUnidadeFederacaoProcurador.value == -1) {
			msg = msg + 'Informe o Estado do RG do Procurador.' + '\n';
			validacao=false;
		}
		
		if (form.numeroCpfProcurador == null || form.numeroCpfProcurador.value == '') {
			msg = msg + 'Informe o CPF do Procurador.' + '\n';
			validacao=false;
		}		
		
		if (form.descricaoEnderecoProcurador == null || form.descricaoEnderecoProcurador.value == '') {
			msg = msg + 'Informe o Endereço do Procurador.' + '\n';
			validacao=false;
		}		
		
		if (form.idEstadoCivilProcurador == null || form.idEstadoCivilProcurador.value == -1) {
			msg = msg + 'Informe o Estado Civil do Procurador.' + '\n';
			validacao=false;
		}		
		
		if (form.idProfissaoProcurador == null || form.idProfissaoProcurador.value == -1) {
			msg = msg + 'Informe a Profissão do Procurador.' + '\n';
			validacao=false;
		}
		
		if (form.idNacionalidadeProcurador == null || form.idNacionalidadeProcurador.value == -1) {
			msg = msg + 'Informe a Nacionalidade do Procurador.' + '\n';
			validacao=false;
		}		
		
	}	

	if (msg != '')
		alert(msg);
	
	return validacao;
}

function cnpj(){
	this.aa = new Array("numeroCnpjClienteEmpresa", "CNPJ da Empresa inválido.", new Function ("varName", " return this[varName];"));
}

function validateParcelamentoTermoTestemunhasActionForm(form) {
	return validateRequired(form) 
		&& validateCaracterEspecial(form)
		&& validateCpf(form);
}

function caracteresespeciais() {
	this.aa = new Array("nomeCliente", "Nome do Cliente possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	this.ab = new Array("numeroCpfCliente", "CPF do Cliente possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	this.ac = new Array("numeroRgCliente", "RG do Cliente possui caracteres especiais.", new Function ("varName", " return this[varName];"));
}

function required() {
	this.aa = new Array("idParcelamentoAcordoTipo", "Informe o Tipo de Acordo.", new Function ("varName", " return this[varName];"));
	this.ab = new Array("indicadorProcurador", "Informe se Possui Procurador.", new Function ("varName", " return this[varName];"));
	this.ac = new Array("idCliente", "Informe o Cliente.", new Function ("varName", " return this[varName];"));
	this.ad = new Array("nomeCliente", "Informe Nome do Cliente.", new Function ("varName", " return this[varName];"));
	this.ae = new Array("numeroRgCliente", "Informe RG do Cliente.", new Function ("varName", " return this[varName];"));	
	this.af = new Array("idOrgaoExpedidorRgCliente", "Informe o Orgão Expedidor do RG do Cliente.", new Function ("varName", " return this[varName];"));
	this.ag = new Array("idUnidadeFederacaoCliente", "Informe o Estado do RG do Cliente.", new Function ("varName", " return this[varName];"));
	this.ah = new Array("numeroCpfCliente", "Informe CPF do Cliente.", new Function ("varName", " return this[varName];"));
	this.ai = new Array("idEnderecoCliente", "Informe o Endereço do Cliente.", new Function ("varName", " return this[varName];"));
	this.aj = new Array("idEstadoCivilCliente", "Informe o Estado Civil do Cliente.", new Function ("varName", " return this[varName];"));
	this.al = new Array("idProfissaoCliente", "Informe a Profissão do Cliente.", new Function ("varName", " return this[varName];"));
	this.ba = new Array("idNacionalidadeCliente", "Informe a Nacionalidade do Cliente.", new Function ("varName", " return this[varName];"));

}

//-->
</script>


<logic:equal name="ParcelamentoDadosTermoActionForm" property="indicadorProcurador" value="2">
	<script language="JavaScript">
		function cpf(){
			this.aa = new Array("numeroCpfCliente", "CPF do Cliente inválido.", new Function ("varName", " return this[varName];"));
		}
	</script>		
</logic:equal>

<logic:equal name="ParcelamentoDadosTermoActionForm" property="indicadorProcurador" value="1">
	<script language="JavaScript">
		function cpf(){
			this.aa = new Array("numeroCpfCliente", "CPF do Cliente inválido.", new Function ("varName", " return this[varName];"));
			this.aa = new Array("numeroCpfProcurador", "CPF do Procurador inválido.", new Function ("varName", " return this[varName];"));
		}
	</script>		
</logic:equal>


<logic:present name="TermoParcelamentoPreview" scope="session">
	<logic:equal name="TermoParcelamentoPreview" value="True" scope="session">
		<script language="JavaScript">
				function fecharForm(){
					var form = document.forms[0];
				
					form.action = "/gsan/efetuarParcelamentoDebitosWizardAction.do?action=exibirEfetuarParcelamentoDebitosProcesso4Action"
					form.submit();
				
				}
		</script>
	</logic:equal>
</logic:present>

<logic:notPresent name="TermoParcelamentoPreview" scope="session">
	<logic:notPresent name="TermoParcelamentoConsultar" scope="session">
		<script language="JavaScript">
				function fecharForm(){
					var form = document.forms[0];
					
					form.action = "/gsan/efetuarParcelamentoDebitosWizardAction.do?retornoTelaSucesso=S&action=concluirProcessoAction"
					form.submit();
				
				}
		</script>
	</logic:notPresent>	
</logic:notPresent>

<logic:notPresent name="TermoParcelamentoPreview" scope="session">
	<logic:present name="TermoParcelamentoConsultar" scope="session">
		<logic:equal name="TermoParcelamentoConsultar" value="True" scope="session">	
			<script language="JavaScript">
					function fecharForm(){
						var form = document.forms[0];
						
						form.action = "/gsan/exibirConsultarParcelamentoDebitoAction.do?codigoImovel=" + form.idImovel.value + "&codigoParcelamento=" + form.idParcelamento.value						
						form.submit();
					
					}
			</script>
		</logic:equal>			
	</logic:present>	
</logic:notPresent>	

</head>

<body leftmargin="5" topmargin="5">

<html:form action="/exibirParcelamentoDadosTermoExecFiscalAction.do"
	name="ParcelamentoDadosTermoActionForm"
	type="gcom.gui.cobranca.parcelamento.ParcelamentoDadosTermoActionForm" method="post">
	
	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<html:hidden property="id"/>
	<html:hidden property="idParcelamento"/>
	<html:hidden property="idImovel"/>
	<html:hidden property="idRamoAtividadeClienteEmpresaInicial"/>
	<html:hidden property="numeroRgClienteInicial"/>
	<html:hidden property="idOrgaoExpedidorRgClienteInicial"/>
	<html:hidden property="idUnidadeFederacaoClienteInicial"/>	
	<html:hidden property="numeroCpfClienteInicial"/>	
	<html:hidden property="idEstadoCivilClienteInicial"/>
	<html:hidden property="idProfissaoClienteInicial"/>
	<html:hidden property="idNacionalidadeClienteInicial"/>
	<html:hidden property="numeroCnpjClienteEmpresaInicial"/>
	<html:hidden property="idClienteEmpresaExistente"/>
	
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

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="11"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
				<td class="parabg">Informar Dados do Parcelamento</td>
				<td width="11"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
			</tr>
		</table>
		<p>&nbsp;</p>
		
			<table width="100%" border="0">
				<tr>
					<td colspan="3">
						<table width="100%" align="center" border="0" bgcolor="#99CCFF">
							<tr>
								<td width="140" bgcolor="#cbe5fe">Tipo de Acordo:<font color="#FF0000">* </font></td>
								<td  bgcolor="#cbe5fe"> <html:select onchange="javascript:modificarAcordoTipoProcurador()"
									property="idParcelamentoAcordoTipo" tabindex="3">
									<html:option
										value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
									<html:options collection="colecaoAcordoTipo"
										labelProperty="descricao" property="id" />
								</html:select></td>
							</tr>
							
							<tr>
								<td width="140" bgcolor="#cbe5fe">Possui Procurador:<font color="#FF0000">*</font>
								</td>
								<td colspan="3" bgcolor="#cbe5fe">
									<html:radio property="indicadorProcurador" onclick="javascript:modificarAcordoTipoProcurador();" onkeypress="javascript:modificarAcordoTipoProcurador();" value="1" />
									Sim
									<html:radio property="indicadorProcurador" onclick="javascript:modificarAcordoTipoProcurador();" onkeypress="javascript:modificarAcordoTipoProcurador();" value="2" />
									Não
								</td>
							</tr>
													
							<tr>
								<td width="140"><strong>Dados do Cliente:</strong></td>
							</tr>
							
							<tr bgcolor="#cbe5fe"> 
								<td> 
									Nome :<strong><font color="#FF0000">*</font></strong>
								</td>
								<td>
									<logic:present name="informarCliente" scope="session">
										<logic:equal name="informarCliente" value="S" scope="session">
											
											<html:text property="idCliente" size="9" maxlength="9"
												onkeyup="validaEnterComMensagem(event, 
												'exibirParcelamentoDadosTermoExecFiscalAction.do?pesquisaCliente=S', 'idCliente','cliente');"
												onkeypress="document.forms[0].nomeCliente.value=''"/>
										
											<a href="javascript:habilitarPesquisaClienteParcelamento(document.forms[0]);" >
												<img width="23" height="21"	src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" />
											</a>
										
											<logic:present name="clienteInexistente" scope="request">
												<html:text property="nomeCliente" size="30" maxlength="35"
													readonly="true"
													style="background-color:#EFEFEF; border:0; color: #ff0000" />
											</logic:present> 
											<logic:notPresent name="clienteInexistente"
												scope="request">
												<html:text property="nomeCliente" size="30" maxlength="35"
													readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
											</logic:notPresent>
											
											<a href="javascript:limparClienteParcelamento();">
												<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" />
											</a>											
										</logic:equal>
										<logic:notEqual name="informarCliente" value="S" scope="session">
											<html:hidden property="idCliente"/>
											<html:text property="nomeCliente" maxlength="50" size="50" disabled="True" />
										</logic:notEqual>										
									</logic:present>
								
									<logic:notPresent name="informarCliente" scope="session">
										<html:hidden property="idCliente"/>
										<html:text property="nomeCliente" maxlength="50" size="50" disabled="True" />
									</logic:notPresent>
								</td>
							</tr>
							
							<tr bgcolor="#cbe5fe" > 
								<td width="140"> 
									RG :<strong><font color="#FF0000">*</font></strong>
								</td>
								<td colspan="2">
									<logic:equal name="ParcelamentoDadosTermoActionForm" property="numeroRgClienteInicial" value="S">								
										<html:text property="numeroRgCliente" maxlength="13" size="13" disabled="true" />
									</logic:equal>								
									<logic:notEqual name="ParcelamentoDadosTermoActionForm" property="numeroRgClienteInicial" value="S">								
										<html:text property="numeroRgCliente" maxlength="13" size="13" disabled="false" />
									</logic:notEqual>
								</td>
							</tr>	
							
							<tr bgcolor="#cbe5fe">
								<td width="140">Órgão Expedidor:<font color="#FF0000">*</font></td>
								<td style="padding-left: 0">
									<table width="100%">
										<tr>
											<td >
												<logic:equal name="ParcelamentoDadosTermoActionForm" property="idOrgaoExpedidorRgClienteInicial" value="S">								
													<html:select property="idOrgaoExpedidorRgCliente" tabindex="1" disabled="true" style="width: 280" >
													<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option>
													<html:options collection="colecaoOrgaoExpedidorRg" labelProperty="descricao" property="id" />
													</html:select>
												</logic:equal>								
												<logic:notEqual name="ParcelamentoDadosTermoActionForm" property="idOrgaoExpedidorRgClienteInicial" value="S">								
													<html:select property="idOrgaoExpedidorRgCliente" tabindex="1" disabled="false" style="width: 280">
													<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option>
													<html:options collection="colecaoOrgaoExpedidorRg" labelProperty="descricao" property="id" />
													</html:select>
												</logic:notEqual>		
											</td>
											<td align="right">Estado:<font color="#FF0000">*</font></td>
											<td >
												<logic:equal name="ParcelamentoDadosTermoActionForm" property="idUnidadeFederacaoClienteInicial" value="S">								
													<html:select property="idUnidadeFederacaoCliente" tabindex="1" disabled="true">
													<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
													<html:options collection="colecaoUnidadeFederacao" labelProperty="sigla" property="id" />
													</html:select>
												</logic:equal>		
												<logic:notEqual name="ParcelamentoDadosTermoActionForm" property="idUnidadeFederacaoClienteInicial" value="S">								
													<html:select property="idUnidadeFederacaoCliente" tabindex="1" disabled="false">
													<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
													<html:options collection="colecaoUnidadeFederacao" labelProperty="sigla" property="id" />
													</html:select>
												</logic:notEqual>																								
											</td>
										</tr>
									</table>
								</td>
							</tr>
							
							<tr bgcolor="#cbe5fe">
								<td width="140"> 
									CPF:<strong><font color="#FF0000">*</font></strong>
								</td>
								<td colspan="2">
									<logic:equal name="ParcelamentoDadosTermoActionForm" property="numeroCpfClienteInicial" value="S">								
										<html:text property="numeroCpfCliente" maxlength="11" size="10" disabled="true" />
									</logic:equal>								
									<logic:notEqual name="ParcelamentoDadosTermoActionForm" property="numeroCpfClienteInicial" value="S">								
										<html:text property="numeroCpfCliente" maxlength="11" size="10" disabled="false" />
									</logic:notEqual>
								</td>
							</tr>	
					
							<tr bgcolor="#cbe5fe">
								<td width="140">Endereço:<font color="#FF0000">*</font></td>
								<td>
									<html:select property="idEnderecoCliente" tabindex="1" style="width: 385" >
									<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option>
									<html:options collection="colecaoClienteEndereco" labelProperty="enderecoFormatado" property="id" />
									</html:select>
								</td>
							</tr>
							
							<tr bgcolor="#cbe5fe">							
								<td width="140">Estado Civil:<font color="#FF0000">*</font></td>							
								<td >
									<logic:equal name="ParcelamentoDadosTermoActionForm" property="idEstadoCivilClienteInicial" value="S">								
										<html:select property="idEstadoCivilCliente" tabindex="1" disabled="true">
										<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option>
										<html:options collection="colecaoEstadoCivil" labelProperty="descricao" property="id" />
										</html:select>
									</logic:equal>								
									<logic:notEqual name="ParcelamentoDadosTermoActionForm" property="idEstadoCivilClienteInicial" value="S">								
										<html:select property="idEstadoCivilCliente" tabindex="1" disabled="false">
										<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option>
										<html:options collection="colecaoEstadoCivil" labelProperty="descricao" property="id" />
										</html:select>
									</logic:notEqual>		
								</td>	
							</tr>
							
							<tr bgcolor="#cbe5fe">							
								<td width="140">Profissão:<font color="#FF0000">*</font></td>							
								<td >
									<logic:equal name="ParcelamentoDadosTermoActionForm" property="idProfissaoClienteInicial" value="S">								
										<html:select property="idProfissaoCliente" tabindex="1" disabled="true">
										<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option>
										<html:options collection="colecaoProfissao" labelProperty="descricao" property="id" />
										</html:select>
									</logic:equal>								
									<logic:notEqual name="ParcelamentoDadosTermoActionForm" property="idProfissaoClienteInicial" value="S">								
										<html:select property="idProfissaoCliente" tabindex="1" disabled="false">
										<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option>
										<html:options collection="colecaoProfissao" labelProperty="descricao" property="id" />
										</html:select>
									</logic:notEqual>		
								</td>	
							</tr>
							
							<logic:equal name="ParcelamentoDadosTermoActionForm" property="visivelNacionalidadeCliente" value="S">
								<tr bgcolor="#cbe5fe">							
									<td width="140">Nacionalidade:<font color="#FF0000">*</font></td>							
									<td >
										<logic:equal name="ParcelamentoDadosTermoActionForm" property="idNacionalidadeClienteInicial" value="S">								
											<html:select property="idNacionalidadeCliente" tabindex="1" disabled="true">
											<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option>
											<html:options collection="colecaoNacionalidade" labelProperty="descricao" property="id" />
											</html:select>
										</logic:equal>								
										<logic:notEqual name="ParcelamentoDadosTermoActionForm" property="idNacionalidadeClienteInicial" value="S">								
											<html:select property="idNacionalidadeCliente" tabindex="1" disabled="false">
											<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option>
											<html:options collection="colecaoNacionalidade" labelProperty="descricao" property="id" />
											</html:select>
										</logic:notEqual>		
									</td>	
								</tr>
							</logic:equal>
								
							<logic:equal name="ParcelamentoDadosTermoActionForm" property="visivelNumeroProcesso" value="S">								
								<tr bgcolor="#cbe5fe"> 
									<td width="140"> 
										Número do Processo :<font color="#FF0000">*</font>
									</td>
									<td>
										<html:text property="numeroProcesso" maxlength="30" size="30"/>
									</td>
								</tr>	
							</logic:equal>
								
							<logic:equal name="ParcelamentoDadosTermoActionForm" property="visivelNumeroVara" value="S" >	
								<tr bgcolor="#cbe5fe"> 
									<td width="140"> 
										Vara :<font color="#FF0000">*</font>
									</td>
									<td>
										<html:text property="numeroVara" maxlength="15" size="38" onkeyup="verificaNumeroInteiro(this);"  />
									</td>
								</tr>
							</logic:equal>

							<logic:equal name="ParcelamentoDadosTermoActionForm" property="visivelNomeExecutado" value="S">
								<tr bgcolor="#cbe5fe"> 
									<td width="140"> 
										Nome do Executado :<font color="#FF0000">*</font>
									</td>
									<td>
										<html:text property="nomeExecutado" maxlength="50" size="50"/>
									</td>
								</tr>	
							</logic:equal>	
							
							<logic:equal name="ParcelamentoDadosTermoActionForm" property="visivelTituloPosse" value="S">
								<tr bgcolor="#cbe5fe"> 
									<td width="140"> 
										Título de Posse :<font color="#FF0000">*</font>
									</td>
									<td>
										<html:text property="tituloPosse" maxlength="50" size="50"/>
									</td>
								</tr>	
							</logic:equal>	
							
							<logic:equal name="ParcelamentoDadosTermoActionForm" property="visivelDadosEmpresa" value="S">								
								<p>&nbsp;</p>							
								<tr>
									<td width="140"><strong>Dados da Empresa:</strong></td>
								</tr>		
								
								<tr bgcolor="#cbe5fe">
									<td  width="140">Nome:<font color="#FF0000">*</strong></td>
									<td>
										<html:text property="idClienteEmpresa" size="9" maxlength="9"
											onkeyup="validaEnterComMensagem(event, 
											'exibirParcelamentoDadosTermoExecFiscalAction.do?modificarAcordoTipoProcurador=sim', 'idClienteEmpresa','Empresa');" />
											
										
										<a href="javascript:habilitarPesquisaCliente(document.forms[0]);" >
											<img width="23" height="21"	src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" />
										</a>
										
										<logic:present name="clienteInexistente" scope="request">
											<html:text property="nomeClienteEmpresa" size="30" maxlength="55"
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #ff0000" />
										</logic:present> 
										<logic:notPresent name="clienteInexistente"
											scope="request">
											<html:text property="nomeClienteEmpresa" size="30" maxlength="55"
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:notPresent>
										
										<a href="javascript:limparCliente();">
											<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" />
										</a>										
											
									</td>
								</tr>
								
								<tr bgcolor="#cbe5fe">							
									<td width="140">Ramo de Atividade:<font color="#FF0000">*</font></td>							
									<td >
										<logic:equal name="ParcelamentoDadosTermoActionForm" property="idRamoAtividadeClienteEmpresaInicial" value="S">								
											<html:select property="idRamoAtividadeClienteEmpresa" tabindex="1" disabled="true">
											<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option>
											<html:options collection="colecaoRamoAtividade" labelProperty="descricao" property="id" />
											</html:select>
										</logic:equal>								
										<logic:notEqual name="ParcelamentoDadosTermoActionForm" property="idRamoAtividadeClienteEmpresaInicial" value="S">								
											<html:select property="idRamoAtividadeClienteEmpresa" tabindex="1" disabled="false">
											<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option>
											<html:options collection="colecaoRamoAtividade" labelProperty="descricao" property="id" />
											</html:select>
										</logic:notEqual>		
									</td>	
								</tr>								
															
									
								<tr bgcolor="#cbe5fe">
									<td width="140"> 
										CNPJ:<strong><font color="#FF0000">*</font></strong>
									</td>
									<td colspan="2">
										<logic:equal name="ParcelamentoDadosTermoActionForm" property="numeroCnpjClienteEmpresaInicial" value="S">								
											<html:text property="numeroCnpjClienteEmpresa" maxlength="14" size="13" disabled="true" />
										</logic:equal>								
										<logic:notEqual name="ParcelamentoDadosTermoActionForm" property="numeroCnpjClienteEmpresaInicial" value="S">								
											<html:text property="numeroCnpjClienteEmpresa" maxlength="14" size="13" disabled="false" />
										</logic:notEqual>
									</td>
								</tr>									
										
								<tr bgcolor="#cbe5fe">
									<td width="140">Endereço:<font color="#FF0000">*</font></td>
									<td>
										<html:select property="idEnderecoClienteEmpresa" tabindex="1" style="width: 385" >
										<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option>
										<html:options collection="colecaoClienteEnderecoEmpresa" labelProperty="enderecoFormatado" property="id" />
										</html:select>
									</td>
								</tr>															
																												
							</logic:equal>	
							
							<p>&nbsp;</p>							
							<tr>
								<td width="100%"><strong>Dados do Procurador:</strong></td>
							</tr>	
							
							<tr bgcolor="#cbe5fe" > 
								<td width="140"> 
									Nome :<strong><font color="#FF0000">*</font></strong>
								</td>
								<td colspan="2">
									<logic:equal name="ParcelamentoDadosTermoActionForm" property="indicadorProcurador" value="2">								
										<html:text property="nomeProcurador" maxlength="50" size="50" disabled="true" />
									</logic:equal>								
									<logic:equal name="ParcelamentoDadosTermoActionForm" property="indicadorProcurador" value="1">								
										<html:text property="nomeProcurador" maxlength="50" size="50" disabled="false" />
									</logic:equal>
								</td>
							</tr>	
							
							<tr bgcolor="#cbe5fe" > 
								<td width="140"> 
									RG :<strong><font color="#FF0000">*</font></strong>
								</td>
								<td colspan="2">
									<logic:equal name="ParcelamentoDadosTermoActionForm" property="indicadorProcurador" value="2">								
										<html:text property="numeroRgProcurador" maxlength="13" size="11" disabled="true" />
									</logic:equal>								
									<logic:equal name="ParcelamentoDadosTermoActionForm" property="indicadorProcurador" value="1">								
										<html:text property="numeroRgProcurador" maxlength="13" size="11" disabled="false" />
									</logic:equal>
								</td>
							</tr>								

							<tr bgcolor="#cbe5fe">
								<td width="140">Órgão Expedidor:<font color="#FF0000">*</font></td>
								<td style="padding-left: 0">
									<table width="100%">
										<tr>
											<td >
												<logic:equal name="ParcelamentoDadosTermoActionForm" property="indicadorProcurador" value="2">								
													<html:select property="idOrgaoExpedidorRgProcurador" tabindex="1" disabled="true" style="width: 280">
													<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option>
													<html:options collection="colecaoOrgaoExpedidorRg" labelProperty="descricao" property="id" />
													</html:select>
												</logic:equal>								
												<logic:equal name="ParcelamentoDadosTermoActionForm" property="indicadorProcurador" value="1">								
													<html:select property="idOrgaoExpedidorRgProcurador" tabindex="1" disabled="false" style="width: 280">
													<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option>
													<html:options collection="colecaoOrgaoExpedidorRg" labelProperty="descricao" property="id" />
													</html:select>
												</logic:equal>		
											</td>
											<td align="right">Estado:<font color="#FF0000">*</font></td>
											<td >
												<logic:equal name="ParcelamentoDadosTermoActionForm" property="indicadorProcurador" value="2">								
													<html:select property="idUnidadeFederacaoProcurador" tabindex="1" disabled="true">
													<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
													<html:options collection="colecaoUnidadeFederacao" labelProperty="sigla" property="id" />
													</html:select>
												</logic:equal>		
												<logic:equal name="ParcelamentoDadosTermoActionForm" property="indicadorProcurador" value="1">								
													<html:select property="idUnidadeFederacaoProcurador" tabindex="1" disabled="false">
													<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
													<html:options collection="colecaoUnidadeFederacao" labelProperty="sigla" property="id" />
													</html:select>
												</logic:equal>																								
											</td>
										</tr>
									</table>
								</td>
							</tr>	
							
							<tr bgcolor="#cbe5fe">
								<td width="140"> 
									CPF:<strong><font color="#FF0000">*</font></strong>
								</td>
								<td colspan="2">
									<logic:equal name="ParcelamentoDadosTermoActionForm" property="indicadorProcurador" value="2">								
										<html:text property="numeroCpfProcurador" maxlength="11" size="10" disabled="true"   />
									</logic:equal>								
									<logic:equal name="ParcelamentoDadosTermoActionForm" property="indicadorProcurador" value="1">								
										<html:text property="numeroCpfProcurador" maxlength="11" size="10" disabled="false"  />
									</logic:equal>
								</td>
							</tr>
							
							<tr bgcolor="#cbe5fe">
								<td width="140"> 
									Endereço:<strong><font color="#FF0000">*</font></strong>
								</td>
								<td colspan="2">
									<logic:equal name="ParcelamentoDadosTermoActionForm" property="indicadorProcurador" value="2">								
										<html:text property="descricaoEnderecoProcurador" maxlength="100" size="50" disabled="true" />
									</logic:equal>								
									<logic:equal name="ParcelamentoDadosTermoActionForm" property="indicadorProcurador" value="1">								
										<html:text property="descricaoEnderecoProcurador" maxlength="100" size="50" disabled="false" />
									</logic:equal>
								</td>
							</tr>	
							
							<tr bgcolor="#cbe5fe">							
								<td width="140">Estado Civil:<font color="#FF0000">*</font></td>							
								<td >
									<logic:equal name="ParcelamentoDadosTermoActionForm" property="indicadorProcurador" value="2">								
										<html:select property="idEstadoCivilProcurador" tabindex="1" disabled="true">
										<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option>
										<html:options collection="colecaoEstadoCivil" labelProperty="descricao" property="id" />
										</html:select>
									</logic:equal>								
									<logic:equal name="ParcelamentoDadosTermoActionForm" property="indicadorProcurador" value="1">								
										<html:select property="idEstadoCivilProcurador" tabindex="1" disabled="false">
										<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option>
										<html:options collection="colecaoEstadoCivil" labelProperty="descricao" property="id" />
										</html:select>
									</logic:equal>		
								</td>	
							</tr>
							
							<tr bgcolor="#cbe5fe">							
								<td width="140">Profissão:<font color="#FF0000">*</font></td>							
								<td >
									<logic:equal name="ParcelamentoDadosTermoActionForm" property="indicadorProcurador" value="2">								
										<html:select property="idProfissaoProcurador" tabindex="1" disabled="true">
										<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option>
										<html:options collection="colecaoProfissao" labelProperty="descricao" property="id" />
										</html:select>
									</logic:equal>								
									<logic:equal name="ParcelamentoDadosTermoActionForm" property="indicadorProcurador" value="1">								
										<html:select property="idProfissaoProcurador" tabindex="1" disabled="false">
										<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option>
										<html:options collection="colecaoProfissao" labelProperty="descricao" property="id" />
										</html:select>
									</logic:equal>		
								</td>	
							</tr>
							
							<tr bgcolor="#cbe5fe">							
								<td width="140">Nacionalidade:<font color="#FF0000">*</font></td>							
								<td >
									<logic:equal name="ParcelamentoDadosTermoActionForm" property="indicadorProcurador" value="2">								
										<html:select property="idNacionalidadeProcurador" tabindex="1" disabled="true">
										<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option>
										<html:options collection="colecaoNacionalidade" labelProperty="descricao" property="id" />
										</html:select>
									</logic:equal>								
									<logic:equal name="ParcelamentoDadosTermoActionForm" property="indicadorProcurador" value="1">								
										<html:select property="idNacionalidadeProcurador" tabindex="1" disabled="false">
										<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option>
										<html:options collection="colecaoNacionalidade" labelProperty="descricao" property="id" />
										</html:select>
									</logic:equal>		
								</td>	
							</tr>
							
																			
						</table>				
					</td>
				</tr>	

				
				<tr>
					<td colspan="3" align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</td>
				</tr>						
				<tr>
					<td align="left">
						<input name="Voltar" type="button" class="bottonRightCol" value="Fechar" style="width: 80px"
							onClick="javascript:fecharForm();" />
					</td>
					
					<td align="right" colspan="2">
					
					<!-- 
						<input type="button" name="ButtonConfirmar" class="bottonRightCol" value="Confirmar" 
							onClick="javascript:confirmarForm()">
					 -->
						<input type="button" name="ButtonAtualizar" class="bottonRightCol" value="Atualizar/Emitir"
							onclick="javascript:validarForm();" />
					</td>
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
