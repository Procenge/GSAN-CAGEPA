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

function habilitarPesquisaCliente(form) {
	abrirPopup('exibirPesquisarClienteAction.do?indicadorUsoTodos=1', 520, 800);
}

function limparClienteParcelamento() {
	var form = document.forms[0];
	form.idCliente.value = "";
	form.nomeCliente.value = "";
}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.forms[0];

     if (tipoConsulta == 'cliente') {
      form.idCliente.value = codigoRegistro;
      form.nomeCliente.value = descricaoRegistro;
      form.nomeCliente.style.color = "#000000";
      form.action='exibirParcelamentoDadosTermoAction.do?pesquisaCliente=S';
      submeterFormPadrao(form);
    }
}

function validarForm(){
	var form = document.forms[0];

	if (validateParcelamentoTermoTestemunhasActionForm(form)) {
		form.action = "/gsan/exibirParcelamentoDadosTermoAction.do?atualizarDadosTermo=sim"
		form.submit();
	}
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
	this.af = new Array("idOrgaoExpedidorRgCliente", "Informe o Orgão Expedidor dp RG do Cliente.", new Function ("varName", " return this[varName];"));
	this.ag = new Array("idUnidadeFederacaoCliente", "Informe o Estado do RG do Cliente.", new Function ("varName", " return this[varName];"));
	this.ah = new Array("numeroCpfCliente", "Informe CPF do Cliente.", new Function ("varName", " return this[varName];"));
	this.ai = new Array("idEnderecoCliente", "Informe o Endereço do Cliente.", new Function ("varName", " return this[varName];"));
	this.ba = new Array("idNacionalidadeCliente", "Informe a Nacionalidade do Cliente.", new Function ("varName", " return this[varName];"));
}

function cpf(){
	this.aa = new Array("numeroCpfCliente", "CPF do Cliente inválido.", new Function ("varName", " return this[varName];"));
}

//-->
</script>

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

<html:form action="/exibirParcelamentoDadosTermoAction.do"
	name="ParcelamentoDadosTermoActionForm"
	type="gcom.gui.cobranca.parcelamento.ParcelamentoDadosTermoActionForm" method="post">
	
	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<html:hidden property="id"/>
	<html:hidden property="idParcelamento"/>
	<html:hidden property="idImovel"/>	
	<html:hidden property="indicadorProcurador"/>
	<html:hidden property="numeroRgClienteInicial"/>
	<html:hidden property="idOrgaoExpedidorRgClienteInicial"/>
	<html:hidden property="idUnidadeFederacaoClienteInicial"/>
	<html:hidden property="idNacionalidadeClienteInicial"/>
	<html:hidden property="numeroCpfClienteInicial"/>	
	

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
								<td  bgcolor="#cbe5fe">Tipo de Acordo:<font color="#FF0000">* </font></td>
								<td  bgcolor="#cbe5fe"> <html:select
									property="idParcelamentoAcordoTipo" tabindex="3">
									<html:option
										value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
									<html:options collection="colecaoAcordoTipo"
										labelProperty="descricao" property="id" />
								</html:select></td>
							</tr>
													
							<tr>
								<td width="100%"><strong>Dados do Cliente:</strong></td>
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
												'exibirParcelamentoDadosTermoAction.do?pesquisaCliente=S', 'idCliente','Cliente');"
												onkeypress="document.forms[0].nomeCliente.value=''"/>
										
											<a href="javascript:habilitarPesquisaCliente(document.forms[0]);" >
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
								<td> 
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
								<td width="162">Órgão Expedidor:<font color="#FF0000">*</font></td>
								<td style="padding-left: 0">
									<table width="100%">
										<tr>
											<td >
												<logic:equal name="ParcelamentoDadosTermoActionForm" property="idOrgaoExpedidorRgClienteInicial" value="S">								
													<html:select property="idOrgaoExpedidorRgCliente" tabindex="1" disabled="true" style="width: 280">
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
								<td> 
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
								<td width="162">Endereço:<font color="#FF0000">*</font></td>
								<td width="100%">
									<html:select property="idEnderecoCliente" tabindex="1" style="width: 100%">
									<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option>
									<html:options collection="colecaoClienteEndereco" labelProperty="enderecoFormatado" property="id" />
									</html:select>
								</td>
							</tr>	
							
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
