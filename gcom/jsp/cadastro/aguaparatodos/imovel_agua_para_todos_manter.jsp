<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@page isELIgnored="false"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gcom.util.ConstantesSistema"%>

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="ManterImovelAguaParaTodosActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>


<script language="JavaScript">

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.forms[0];

    if (tipoConsulta == 'imovel') {
      form.matricula.value = codigoRegistro;
      pesquisarImovelAguaParaTodos();
    }
 }
function pesquisarImovelAguaParaTodos() {
	var form = document.forms[0];
	redirecionarSubmit("exibirManterImovelAguaParaTodosAction.do?matriculaImovel="+form.matricula.value);	
}

function pesquisarImovelAguaParaTodosEnter(event) {
	var form = document.forms[0];
	validaEnter(event, 'exibirManterImovelAguaParaTodosAction.do?matriculaImovel='+form.matricula.value,'matricula');
}

function validarForm() {
	var form = document.forms[0];
	if (form.nic.value == '') {
		alert("Informe o NIC");
		return false;
	}
	if (form.nomeContribuinte.value == '') {
		alert("Informe o Nome do Contribuinte");
		return false;
	}

	form.submit();
	return true;	
}

function excluir() {
	var form = document.forms[0];

	if(form.flagOperacao.value != '2') {
		if ((form.motivoExclusao.value == '') || (form.motivoExclusao.value == '-1')) {
			alert("Informe o Motivo de Exclusão");
			return false;
		}
	}	

	form.flagOperacao.value = '3';
	form.submit();
	return true;	
}
function renovar() {
	var form = document.forms[0];
	form.flagOperacao.value = '4';
	form.submit();
	return true;	
}
</script>
</head>

<body leftmargin="5" topmargin="5" onload="">
<html:form action="/manterImovelAguaParaTodosAction.do"
	name="ManterImovelAguaParaTodosActionForm"
	type="gcom.gui.cadastro.aguaparatodos.ManterImovelAguaParaTodosActionForm"
	method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
	
	<input type="text" name="tela" id="tela" value="${requestScope.tela}"/>

	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="130" valign="top" class="leftcoltext">
			<div align="center">
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>

			<%@ include file="/jsp/util/mensagens.jsp"%>

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			</div>
			</td>


			<td width="625" valign="top" class="centercoltext">
			
			<table>
				<tr>
					<td></td>
				</tr>
			</table>
			
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg"><bean:write name="tituloTela"></bean:write></td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para Manter o Programa Água para todos, informe os dados abaixo:</td>
				</tr>
				
				<tr>
					<td width="20%">
						<strong>Matrícula do Imóvel:</strong>
					</td>
					<td width="100%">
						<html:hidden property="id"/>
						<logic:equal name="ManterImovelAguaParaTodosActionForm" property="flagOperacao" value="0">
							<html:text property="matricula" maxlength="9" size="9" tabindex="0" onkeypress="javascript:pesquisarImovelAguaParaTodosEnter(event);"/>
						</logic:equal>
						<logic:notEqual name="ManterImovelAguaParaTodosActionForm" property="flagOperacao" value="0">
							<html:text property="matricula" maxlength="9" size="9" tabindex="0" disabled="true" onkeypress="javascript:pesquisarImovelAguaParaTodosEnter();"/>
						</logic:notEqual>
						<a href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
 							<img width="23" height="21" border="0" title="Pesquisar Imóvel."
 								src="<bean:message key="caminho.imagens"/>pesquisa.gif"/></a>
					</td>
				</tr>
				<table width="100%" border="0">
				<tr>
				<td>
				<logic:notEqual name="ManterImovelAguaParaTodosActionForm" property="flagOperacao" value="0">
					<hr>
					<table width="100%" border="0">
						<tr>
						  <td><strong>Localização:</strong></td>						
						  <td colspan="3">
						  	<html:hidden property="localidade"/>
							<bean:write name="ManterImovelAguaParaTodosActionForm" property="localidade"/>
						  </td>
						  <td><strong>Sit. Água:</strong></td>	
						  <td colspan="3">
							<html:hidden property="situacaoAgua"/>
							<bean:write name="ManterImovelAguaParaTodosActionForm" property="situacaoAgua"/>
						  </td>
						</tr>
						<tr>
						  <td><strong>Inscrição:</strong></td>						
						  <td colspan="3">
							<html:hidden property="inscricao"/>
							<bean:write name="ManterImovelAguaParaTodosActionForm" property="inscricao"/>
						  </td>
						  <td><strong>Sit. Esgoto:</strong></td>						
						  <td colspan="3">
							<html:hidden property="situacaoEsgoto"/>
							<bean:write name="ManterImovelAguaParaTodosActionForm" property="situacaoEsgoto"/>
						  </td>
						</tr>
						<tr>
						  <td><strong>Nome:</strong></td>						
						  <td colspan="3">
							<html:hidden property="nomeCliente"/>
							<bean:write name="ManterImovelAguaParaTodosActionForm" property="nomeCliente"/>
						  </td>
						  <td><strong>Telefone:</strong></td>						
						  <td colspan="3">
							<html:hidden property="telefone"/>
							<bean:write name="ManterImovelAguaParaTodosActionForm" property="telefone"/>
						  </td>
						</tr>
						<tr>
						  <td><strong>Endereço:</strong></td>						
						  <td colspan="3" width="350">
							<html:hidden property="endereco"/>
							<bean:write name="ManterImovelAguaParaTodosActionForm" property="endereco"/>
						  </td>
						</tr>
						<tr>
							<td colspan="1"><strong>Economias:</strong></td>						
							<td>
								<html:hidden property="categoriasEconomia"/>
								<bean:write name="ManterImovelAguaParaTodosActionForm" property="categoriasEconomia"/>
							</td>
						</tr>
					</table>
					<hr>
					</logic:notEqual>
							<html:hidden property="flagOperacao"/>
							<logic:equal name="ManterImovelAguaParaTodosActionForm" property="flagOperacao" value="1">
							<table width="100%" border="0">
								<tr>
									<td><strong> NIC:<font color="#FF0000">*</font></strong></td>
									<td>
										<html:text maxlength="15" property="nic" onkeypress="return isCampoNumerico(event);" size="15" tabindex="1" />
									</td>
								</tr>
								<tr>
									<td><strong> Nome do Participante:<font color="#FF0000">*</font></strong></td>
									<td>
										<html:text maxlength="50" property="nomeContribuinte" size="50" tabindex="2" />
									</td>
								</tr>
							</table>
							</logic:equal>
							<logic:equal name="ManterImovelAguaParaTodosActionForm" property="flagOperacao" value="2">
							<table width="100%" border="0">
								<tr>
								  <td><strong>NIC:</strong></td>						
								  <td colspan="2">
									<html:hidden property="nic"/>
									<bean:write name="ManterImovelAguaParaTodosActionForm" property="nic"/>
						  		  </td>
						  		  <td>
						  		   <html:hidden property="situacaoCadastramento"/>
								   <bean:write name="ManterImovelAguaParaTodosActionForm" property="situacaoCadastramento"/>
                                  </td>
						  		</tr>	
						  		<tr>
								  <td><strong>Nome do Participante:</strong></td>						
								  <td colspan="2">
									<html:hidden property="nomeContribuinte"/>
									<bean:write name="ManterImovelAguaParaTodosActionForm" property="nomeContribuinte"/>
						  		  </td>
						  		</tr>	
						  		<tr>
								  <td><strong>Data de Cadastramento:</strong></td>						
								  <td colspan="2">
									<html:hidden property="dataCadastramento"/>
									<bean:write name="ManterImovelAguaParaTodosActionForm" property="dataCadastramento"/>
						  		  </td>
						  		</tr>
							</table>
							</logic:equal>
							<logic:equal name="ManterImovelAguaParaTodosActionForm" property="flagOperacao" value="3">
							<table width="100%" border="0">
								<tr>
								  <td><strong>NIC:</strong></td>						
								  <td colspan="2">
									<html:hidden property="nic"/>
									<bean:write name="ManterImovelAguaParaTodosActionForm" property="nic"/>
						  		  </td>
						  		  <td>
						  		   <html:hidden property="situacaoCadastramento"/>
								   <bean:write name="ManterImovelAguaParaTodosActionForm" property="situacaoCadastramento"/>
                                  </td>
						  		</tr>	
						  		<tr>
								  <td><strong>Nome do Participante:</strong></td>						
								  <td colspan="2">
									<html:hidden property="nomeContribuinte"/>
									<bean:write name="ManterImovelAguaParaTodosActionForm" property="nomeContribuinte"/>
						  		  </td>
						  		</tr>	
						  		<tr>
								  <td><strong>Data de Cadastramento:</strong></td>						
								  <td colspan="2">
									<html:hidden property="dataCadastramento"/>
									<bean:write name="ManterImovelAguaParaTodosActionForm" property="dataCadastramento"/>
						  		  </td>
						  		</tr>
						  		<tr>
								  <td><strong>Data de Habilitação:</strong></td>						
								  <td colspan="2">
									<html:hidden property="dataHabilitacao"/>
									<bean:write name="ManterImovelAguaParaTodosActionForm" property="dataHabilitacao"/>
						  		  </td>
						  		</tr>
						  		<tr>
								  <td><strong>Usuário da Habilitação:</strong></td>						
								  <td colspan="2">
									<html:hidden property="usuarioInclusao"/>
									<bean:write name="ManterImovelAguaParaTodosActionForm" property="usuarioInclusao"/>
						  		  </td>
						  		</tr>
						  		<tr>
								  <td><strong>Referencia Inicial Faturamento:</strong></td>						
								  <td colspan="2">
									<html:hidden property="dataReferenciaFaruramento"/>
									<bean:write name="ManterImovelAguaParaTodosActionForm" property="dataReferenciaFaruramento"/>
						  		  </td>
						  		</tr>
						  		<tr>
								  <td><strong>Tarifa:</strong></td>						
								  <td colspan="2">
									<html:hidden property="tarifa"/>
									<bean:write name="ManterImovelAguaParaTodosActionForm" property="tarifa"/>
						  		  </td>
						  		</tr>
						  		<tr>
								  <td><strong>Data de Validade da Tarifa:</strong></td>						
								  <td colspan="2">
									<html:hidden property="dataValidadeTarifa"/>
									<bean:write name="ManterImovelAguaParaTodosActionForm" property="dataValidadeTarifa"/>
						  		  </td>
						  		</tr>
							    <tr><td><br></td></tr>
								<tr>
								  <td colspan="4" bgcolor="#99CCFF" align="center"><strong>Dados da Exclusão</strong></td>						
						  		</tr>	
							    <tr><td><br></td></tr>						  		
						  		<tr>
								  <td><strong>Motivo da Exclusão:<font color="#FF0000">*</font></strong></td>						
								  <td colspan="2">
						  			<html:select name="ManterImovelAguaParaTodosActionForm" property="motivoExclusao" tabindex="5">							  			
										<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							            </html:option>
										<logic:iterate name="aguaParaTodosMotivosExclusao" id="aguaParaTodosMotivosExclusao">
											<html:option value="${aguaParaTodosMotivosExclusao.id}">
												<bean:write name="aguaParaTodosMotivosExclusao" property="id" /> 
									           - <bean:write name="aguaParaTodosMotivosExclusao" property="descricao" />
											</html:option>
										</logic:iterate>
									</html:select>
								  </td>
						  		</tr>
							</table>
							</logic:equal>
							<logic:equal name="ManterImovelAguaParaTodosActionForm" property="flagOperacao" value="4">
							<table width="100%" border="0">
								<tr>
								  <td><strong>NIC:</strong></td>						
								  <td colspan="2">
									<html:hidden property="nic"/>
									<bean:write name="ManterImovelAguaParaTodosActionForm" property="nic"/>
						  		  </td>
						  		  <td>
						  		   <html:hidden property="situacaoCadastramento"/>
								   <bean:write name="ManterImovelAguaParaTodosActionForm" property="situacaoCadastramento"/>
                                  </td>
						  		</tr>	
						  		<tr>
								  <td><strong>Nome do Participante:</strong></td>						
								  <td colspan="2">
									<html:hidden property="nomeContribuinte"/>
									<bean:write name="ManterImovelAguaParaTodosActionForm" property="nomeContribuinte"/>
						  		  </td>
						  		</tr>	
						  		<tr>
								  <td><strong>Data de Cadastramento:</strong></td>						
								  <td colspan="2">
									<html:hidden property="dataCadastramento"/>
									<bean:write name="ManterImovelAguaParaTodosActionForm" property="dataCadastramento"/>
						  		  </td>
						  		</tr>
						  		<tr>
								  <td><strong>Data de Habilitação:</strong></td>						
								  <td colspan="2">
									<html:hidden property="dataHabilitacao"/>
									<bean:write name="ManterImovelAguaParaTodosActionForm" property="dataHabilitacao"/>
						  		  </td>
						  		</tr>
						  		<tr>
								  <td><strong>Usuário da Habilitação:</strong></td>						
								  <td colspan="2">
									<html:hidden property="usuarioInclusao"/>
									<bean:write name="ManterImovelAguaParaTodosActionForm" property="usuarioInclusao"/>
						  		  </td>
						  		</tr>
							    <tr><td><br></td></tr>
								<tr>
								  <td colspan="4" bgcolor="#99CCFF" align="center"><strong>Dados da Renovação</strong></td>						
						  		</tr>	
							    <tr><td><br></td></tr>
						  		<tr>
								  <td><strong>Data da Renovação:</strong></td>						
								  <td colspan="2">
									<html:hidden property="dataRenovacao"/>
									<bean:write name="ManterImovelAguaParaTodosActionForm" property="dataRenovacao"/>
						  		  </td>
						  		</tr>
						  		<tr>
								  <td><strong>Usuário da Renovação:</strong></td>						
								  <td colspan="2">
									<html:hidden property="usuarioRenovacao"/>
									<bean:write name="ManterImovelAguaParaTodosActionForm" property="usuarioRenovacao"/>
						  		  </td>
						  		</tr>
						  		<tr>
								  <td><strong>Tarifa:</strong></td>						
								  <td colspan="2">
									<html:hidden property="tarifa"/>
									<bean:write name="ManterImovelAguaParaTodosActionForm" property="tarifa"/>
						  		  </td>
						  		</tr>
						  		<tr>
								  <td><strong>Data de Validade da Tarifa:</strong></td>						
								  <td colspan="2">
									<html:hidden property="dataValidadeTarifa"/>
									<bean:write name="ManterImovelAguaParaTodosActionForm" property="dataValidadeTarifa"/>
						  		  </td>
						  		</tr>
						  		<tr>
								  <td><strong>Renovar Validade Para:</strong></td>						
								  <td colspan="2">
									<html:hidden property="dataNovaValidadeTarifa"/>
									<bean:write name="ManterImovelAguaParaTodosActionForm" property="dataNovaValidadeTarifa"/>
						  		  </td>
						  		</tr>
							    <tr><td><br></td></tr>
								<tr>
								  <td colspan="4" bgcolor="#99CCFF" align="center"><strong>Dados da Exclusão</strong></td>						
						  		</tr>	
							    <tr><td><br></td></tr>
						  		<tr>
								  <td><strong>Motivo da Exclusão:<font color="#FF0000">*</font></strong></td>						
								  <td colspan="2">
						  			<html:select name="ManterImovelAguaParaTodosActionForm" property="motivoExclusao" tabindex="5">							  			
										<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							            </html:option>
										<logic:iterate name="aguaParaTodosMotivosExclusao" id="aguaParaTodosMotivosExclusao">
											<html:option value="${aguaParaTodosMotivosExclusao.id}">
												<bean:write name="aguaParaTodosMotivosExclusao" property="id" /> 
									           - <bean:write name="aguaParaTodosMotivosExclusao" property="descricao" />
											</html:option>
										</logic:iterate>
									</html:select>
								  </td>
						  		</tr>						  		
							</table>
							</logic:equal>														
						<tr>
							<td width="100%" colspan="2">
							<div align="center"><font color="#FF0000">*</font> Campos obrigatórios</div>
							<table width="100%" border="0">
								<tr>
									<td width="60%" align="left">
										<input type="button"
											name="ButtonCancelar" class="bottonRightCol" value="Voltar"
											onClick="javascript:history.back();">
	
										<input type="button" name="ButtonReset" class="bottonRightCol" value="Desfazer" 
											onclick="window.location.href='exibirManterImovelAguaParaTodosAction.do?desfazer=S&reloadPage=1&tela=${requestScope.tela}&idImovel=<bean:write 
											name="ManterImovelAguaParaTodosActionForm" property="matricula" />';">
	
										<input type="button" name="ButtonCancelar"
											class="bottonRightCol" value="Cancelar"
											onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
									</td>
									<logic:equal name="ManterImovelAguaParaTodosActionForm" property="flagOperacao" value="1">
										<td align="right"><input type="button" name="Button"
											class="bottonRightCol" value="Inserir"
											onclick="javascript:validarForm();" tabindex="7" />
										</td>
									</logic:equal>
									<logic:equal name="ManterImovelAguaParaTodosActionForm" property="flagOperacao" value="2">
										<td align="right"><input type="button" name="Button"
											class="bottonRightCol" value="Habilitar"
											onclick="javascript:validarForm();" tabindex="7" />
										</td>
										<td align="right"><input type="button" name="Button"
											class="bottonRightCol" value="Excluir"
											onclick="javascript:excluir();" tabindex="7" />
										</td>
									</logic:equal>
									<logic:equal name="ManterImovelAguaParaTodosActionForm" property="flagOperacao" value="3">
										<td align="right"><input type="button" name="Button"
											class="bottonRightCol" value="Excluir"
											onclick="javascript:excluir();" tabindex="7" />
										</td>
									</logic:equal>
									<logic:equal name="ManterImovelAguaParaTodosActionForm" property="flagOperacao" value="4">
										<td align="right"><input type="button" name="Button"
											class="bottonRightCol" value="Renovar"
											onclick="javascript:renovar();" tabindex="7" />
										</td>
										<td align="right"><input type="button" name="Button"
											class="bottonRightCol" value="Excluir"
											onclick="javascript:excluir();" tabindex="7" />
										</td>
									</logic:equal>
								</tr>
							</table>
							</td>
						</tr>

					</td>
				</tr>
				</table>
			</table>
			</td>
		</tr>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>
	
</html:form>



</body>
</html:html>