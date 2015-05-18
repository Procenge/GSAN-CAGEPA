<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>
<%@ page import="gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="FiltrarEquipeActionForm"
	dynamicJavascript="true" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">
<!-- Begin
	
	//Recebe os dados do(s) popup(s)
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

		var form = document.FiltrarEquipeActionForm;

		if (tipoConsulta == 'unidadeOrganizacional') {
       
			form.idUnidade.value = codigoRegistro;
			form.nomeUnidade.value = descricaoRegistro;
			form.nomeUnidade.style.color = "#000000";
        
		}
	
		if (tipoConsulta == 'funcionario') {
       	
			form.idFuncionario.value = codigoRegistro;
			form.nomeFuncionario.value = descricaoRegistro;
			form.nomeFuncionario.style.color = "#000000";
        
		}

		if (tipoConsulta == 'servicoPerfilTipo') {
       	
			form.idPerfilServico.value = codigoRegistro;
			form.descricaoPerfilServico.value = descricaoRegistro;
			form.descricaoPerfilServico.style.color = "#000000";
        
		}

	}

	function limparUnidade() {
	
		var form = document.forms[0];
	
		form.idUnidade.value = "";
		form.nomeUnidade.value = "";
	
	}
	
	function limparUnidadeTecla() {
	
		var form = document.forms[0];
	
		form.nomeUnidade.value = "";
	
	}
	
	function limparFuncionario() {
	
		var form = document.forms[0];
	
		form.idFuncionario.value = "";
		form.nomeFuncionario.value = "";
	
	}
	
	function limparFuncionarioTecla() {
	
		var form = document.forms[0];
	
		form.nomeFuncionario.value = "";
	
	}
	
	function limparServicoPerfil() {
	
		var form = document.forms[0];
	
		form.idPerfilServico.value = "";
		form.descricaoPerfilServico.value = "";
	
	}
	
	
	function limparServicoPerfilTecla() {
	
		var form = document.forms[0];
	
		form.descricaoPerfilServico.value = "";
	
	}
	
	function verificarValorAtualizar() {
	
		var form = document.forms[0];
	
		if (form.atualizar.checked == true) {
			form.atualizar.value = '1';
		} else {
			form.atualizar.value = '';
		}
		
	}
	
	function checkAtualizar(valor) {
	
		var form = document.forms[0];
		
		if (valor == '1') {
			form.atualizar.checked = true;
		} else {
			form.atualizar.checked = false;
		}
		
	}

	function validarForm(form){
				
		if(testarCampoValorZero(form.codigo, 'Código da Equipe')
		&& testarCampoValorZero(form.nome, 'Nome da Equipe')
		&& testarCampoValorZero(form.placa, 'Número da Placa')
		&& testarCampoValorZero(form.cargaTrabalho, 'Carga de Trabalho Diária')
		&& testarCampoValorZero(form.idUnidade, 'Unidade Organizacional')
		&& testarCampoValorZero(form.idFuncionario, 'Funcionário')
		&& testarCampoValorZero(form.idPerfilServico, 'Serviço Perfil Tipo')) { 		
			if(validateFiltrarEquipeActionForm(form)){
			
				var cargaTrabalho = trim(form.cargaTrabalho.value);
				
				if (cargaTrabalho != null 
				&& cargaTrabalho != '' 
				&& form.cargaTrabalho.value > 24) {
					alert('Carga de Trabalho Diária(hora) não deve exceder 24');
				} else {
					var atualizar = form.atualizar.value;
					form.action = 'filtrarEquipeAction.do?atualizar=' + atualizar;
   					submeterFormPadrao(form);
   				}
			}
		}	
	}

-->
</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');checkAtualizar('${sessionScope.atualizar}');verificarValorAtualizar();">

<html:form action="/filtrarEquipeAction" name="FiltrarEquipeActionForm"
	type="gcom.gui.atendimentopublico.ordemservico.FiltrarEquipeActionForm"
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
					<td class="parabg">Filtrar Equipe</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>
			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para manter a(s) equipe(s), informe os dados
					abaixo:</td>
					<td align="right"><html:checkbox property="atualizar" value="1"
						onclick="javascript:verificarValorAtualizar()" /><strong>Atualizar</strong></td>
				</tr>
				<tr>
					<td width="100"><strong>Código da Equipe:</strong></td>
					<td colspan="2"><html:text property="codigo" size="5" maxlength="5" /></td>
				</tr>
				<tr>
					<td width="100"><strong>Nome da Equipe:</strong></td>
					<td><html:text property="nome" size="30" maxlength="20" /></td>
				</tr>
				<tr>
					<td width="100"><strong>Número da Placa:</strong></td>
					<td colspan="2"><html:text property="placa" size="8" maxlength="7" /></td>
				</tr>
				<tr>
					<td width="100"><strong>Carga de Trabalho Diária(hora):</strong></td>
					<td colspan="2"><html:text property="cargaTrabalho" size="2"
						maxlength="2" /></td>
				</tr>
				<tr>
					<td width="130"><strong>Unidade Organizacional:</strong></td>
					<td colspan="2"><html:text maxlength="8" property="idUnidade"
						tabindex="1" size="6" onkeyup="javascript:limparUnidadeTecla();"
						onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarEquipeAction.do?atualizar=' + document.forms[0].atualizar.value, 'idUnidade', 'Unidade Organizacional');" />
					<a
						href="javascript:abrirPopup('exibirPesquisarUnidadeOrganizacionalAction.do');">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Unidade Organizacional" /></a> <logic:present
						name="idUnidadeNaoEncontrado" scope="request">
						<html:text maxlength="30" property="nomeUnidade" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000"
							size="40" />
					</logic:present> <logic:notPresent name="idUnidadeNaoEncontrado"
						scope="request">
						<html:text maxlength="30" property="nomeUnidade" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000"
							size="40" />
					</logic:notPresent> <a href="javascript:limparUnidade();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				<tr>
					<td width="130"><strong>Funcionário:</strong></td>
					<td colspan="2"><html:text maxlength="9" property="idFuncionario"
						tabindex="1" size="9"
						onkeyup="javascript:limparFuncionarioTecla();"
						onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarEquipeAction.do?atualizar=' + document.forms[0].atualizar.value, 'idFuncionario', 'Funcionário');" />
					<a
						href="javascript:abrirPopup('exibirPesquisarFuncionarioAction.do');">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Funcionário" /></a> <logic:present
						name="idFuncionarioNaoEncontrado" scope="request">
						<html:text maxlength="30" property="nomeFuncionario"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000"
							size="40" />
					</logic:present> <logic:notPresent
						name="idFuncionarioNaoEncontrado" scope="request">
						<html:text maxlength="30" property="nomeFuncionario"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000"
							size="40" />
					</logic:notPresent> <a href="javascript:limparFuncionario();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				<tr>
					<td width="130"><strong>Serviço Perfil Tipo:</strong></td>
					<td colspan="2"><html:text maxlength="3" property="idPerfilServico"
						tabindex="1" size="4"
						onkeyup="javascript:limparServicoPerfilTecla();"
						onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarEquipeAction.do?atualizar=' + document.forms[0].atualizar.value, 'idPerfilServico', 'Serviço Perfil Tipo');" />
					<a
						href="javascript:abrirPopup('exibirPesquisarTipoPerfilServicoAction.do');">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Serviço Perfil Tipo" /></a> <logic:present
						name="idServicoPerfilNaoEncontrado" scope="request">
						<html:text maxlength="40" property="descricaoPerfilServico"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000"
							size="40" />
					</logic:present> <logic:notPresent
						name="idServicoPerfilNaoEncontrado" scope="request">
						<html:text maxlength="40" property="descricaoPerfilServico"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000"
							size="40" />
					</logic:notPresent> <a href="javascript:limparServicoPerfil();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				
				<tr> 
 					<td>
 						<strong>
 							Tipo de Equipe :
 						</strong>
 					</td>
 					<td colspan="3" align="right">
 						<div align="left"> 
     						<html:select property="idEquipeTipo" tabindex="3">
								<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
								<html:options collection="colecaoEquipeTipo" labelProperty="descricao" property="id" />
							</html:select>
     					</div>
     				</td>
				</tr>
				
				<tr>
					<td><strong>Indicador de Uso:</strong></td>
					<td align="right" colspan="2">
					<div align="left"><html:radio property="indicadorUso" tabindex="4"
						value="<%=ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>" />
					<strong>Ativo</strong> <html:radio property="indicadorUso"
						tabindex="5"
						value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>" />
					<strong>Inativo</strong> <html:radio property="indicadorUso"
						tabindex="5" value="" /> <strong>Todos</strong></div>
					</td>
				</tr>
				<tr>
					<td>
					<p>&nbsp;</p>
					</td>
				</tr>
				<tr>
					<td><input type="button" name="ButtonReset" class="bottonRightCol"
						value="Limpar"
						onclick="window.location.href='/gsan/exibirFiltrarEquipeAction.do?menu=sim';">
					<td colspan="2" align="right"><gcom:controleAcessoBotao
						name="Button" value="Filtrar"
						onclick="javascript:validarForm(document.forms[0]);"
						url="filtrarEquipeAction.do" /> <%-- <input type="button" name="Button" class="bottonRightCol" value="Filtrar" onClick="javascript:validarForm(document.forms[0]);" /> --%>
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
