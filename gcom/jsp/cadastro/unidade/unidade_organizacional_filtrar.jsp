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
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">

	function validarForm(form){
		if(validateUnidadeOrganizacionalActionForm(form)){
			var atualizar = form.indicadorAtualizar.value;
			form.action = 'filtrarUnidadeOrganizacionalAction.do?atualizar=' + atualizar;
			submeterFormPadrao(form);
		}
	}
	
function limparForm(){
	var form = document.UnidadeOrganizacionalActionForm;

		form.indicadorAtualizar.checked = true;

		form.nivelHierarquico.value = "";
		form.descricao.value = "";
		form.sigla.value = "";
		
		form.idTipoUnidade.selectedIndex = "";
		form.idGerenciaRegional.selectedIndex = "";
		form.idMeioSolicitacao.selectedIndex = "";

		form.unidadeEsgoto[0].checked = false;
		form.unidadeEsgoto[1].checked = false;
		form.unidadeEsgoto[2].checked = true;		
		
		form.unidadeAbreRA[0].checked = false;
		form.unidadeAbreRA[1].checked = false;
		form.unidadeAbreRA[2].checked = true;		

		form.unidadeTramitacao[0].checked = false;
		form.unidadeTramitacao[1].checked = false;
		form.unidadeTramitacao[2].checked = true;	
		
		form.indicadorUso[0].checked = false;
		form.indicadorUso[1].checked = false;
		form.indicadorUso[2].checked = true;
		
		form.tipoPesquisa[0].checked = false;
		form.tipoPesquisa[1].checked = false;		
		
        form.idEmpresa.selectedIndex = "";
        
		limparLocalidade();
		limparUnidadeSuperior();
	 	form.idTipoUnidade.focus();
  }	
  
  function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.UnidadeOrganizacionalActionForm;

    if (tipoConsulta == 'localidade') {
      	limparLocalidade();
      	form.idLocalidade.value = codigoRegistro;
      	form.descricaoLocalidade.value = descricaoRegistro;
      	form.descricaoLocalidade.style.color = "#000000";
    }

    if (tipoConsulta == 'unidadeOrganizacional') {
      limparUnidadeSuperior();
      form.idUnidadeSuperior.value = codigoRegistro;
      form.descricaoUnidadeSuperior.value = descricaoRegistro;
      form.descricaoUnidadeSuperior.style.color = "#000000"
    }

   }
   
	function limparLocalidade() {
	  var form = document.UnidadeOrganizacionalActionForm;
    	form.idLocalidade.value = "";
    	form.descricaoLocalidade.value = "";
	}
	
	function limparUnidadeSuperior() {
	 var form = document.UnidadeOrganizacionalActionForm;
    	form.idUnidadeSuperior.value = "";
    	form.descricaoUnidadeSuperior.value = "";
	}
	
	function verificarValorAtualizar() {
	
		var form = document.forms[0];
	
		if (form.indicadorAtualizar.checked == true) {
			form.indicadorAtualizar.value = '1';
		} else {
			form.indicadorAtualizar.value = '';
		}
		
	}
	
	function checkAtualizar(valor) {
	
		var form = document.forms[0];
		
		if (valor == '1') {
			form.indicadorAtualizar.checked = true;
		} else {
			form.indicadorAtualizar.checked = false;
		}
		
	}	   
</script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="UnidadeOrganizacionalActionForm" />
</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');checkAtualizar('${sessionScope.indicadorAtualizar}');verificarValorAtualizar();">
<html:form action="/filtrarUnidadeOrganizacionalAction.do"
	name="UnidadeOrganizacionalActionForm"
	type="gcom.gui.cadastro.unidade.UnidadeOrganizacionalActionForm"
	method="post" onsubmit="return validateUnidadeOrganizacionalActionForm(this);">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellpadding="0" cellspacing="5">
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
			<td valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img
						src="<bean:message key="caminho.imagens"/>parahead_left.gif"
						border="0" /></td>
					<td class="parabg">Filtrar Unidade Organizacional</td>
					<td width="11" valign="top"><img
						src="<bean:message key="caminho.imagens"/>parahead_right.gif"
						border="0" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td>Para manter a(s) unidade(s) organizacional(is), informe os dados
					abaixo:</td>
					<td width="84"><html:checkbox property="indicadorAtualizar" value="1" onclick="verificarValorAtualizar();"/> 
					<strong>Atualizar</strong></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td><strong>Unidade Organizacional:</strong></td>
					<td width="66%"><html:text property="idUnidade" size="6"
						maxlength="8" onkeypress="return isCampoNumerico(event);"/></td>
				</tr>		
				<tr>
					<td><strong>Tipo Unidade:</strong></td>
					<td width="66%"><html:select property="idTipoUnidade">
						<html:option value="">&nbsp;</html:option>
						<html:options collection="colecaoTipoUnidade"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				<tr>
					<td><strong>Nível Hierárquico:</strong></td>
					<td width="66%"><html:text property="nivelHierarquico" size="10"
						maxlength="10" /></td>
				</tr>
				<tr>
					<td><strong>Localidade:</strong></td>
					<td width="66%"><html:text property="idLocalidade"
						size="4" maxlength="3"
						onkeypress="validaEnterComMensagem(event, 'exibirFiltrarUnidadeOrganizacionalAction.do?consultaLocalidade=1', 'idLocalidade', 'Localidade');" />
					<a
						href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do', 250, 495);">
					<img src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						border="0" width="23" height="21" title="Pesquisar"></a> <logic:present
						name="corLocalidade">
						<logic:equal name="corLocalidade" value="exception">
							<html:text property="descricaoLocalidade" size="45"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="corLocalidade" value="exception">
							<html:text property="descricaoLocalidade" size="45"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent name="corLocalidade">
						<logic:empty name="UnidadeOrganizacionalActionForm"
							property="idLocalidade">
							<html:text property="descricaoLocalidade" size="45" value=""
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="UnidadeOrganizacionalActionForm"
							property="idLocalidade">
							<html:text property="descricaoLocalidade" size="45"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent> <a href="javascript:limparLocalidade();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				<tr>
					<td height="24"><strong>Ger&ecirc;ncia Regional:<strong></strong></strong></td>
					<td width="60%" colspan="3">
					<div align="left"><strong> <html:select
						name="UnidadeOrganizacionalActionForm" property="idGerenciaRegional">
						<html:option value="">&nbsp;</html:option>
						<logic:present scope="session" name="colecaoGerenciaRegional">
							<logic:iterate name="colecaoGerenciaRegional"
								id="colecaoGerenciasRegionais">
								<html:option value="${colecaoGerenciasRegionais.id}">
									<bean:write name="colecaoGerenciasRegionais" property="nome" /> 
               			 				- <bean:write name="colecaoGerenciasRegionais" property="nomeAbreviado" />
								</html:option>
							</logic:iterate>
						</logic:present>
					</html:select></strong></div>
					</td>
				</tr>
				<tr>
					<td><strong>Descri&ccedil;&atilde;o:</strong></td>
					<td width="66%"><html:text property="descricao" size="50"
						maxlength="40" /></td>
				</tr>
				
				<tr>
					<td>&nbsp;</td>
					<td width="66%">
						<html:radio property="tipoPesquisa"
							value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" />
					Iniciando pelo texto<html:radio property="tipoPesquisa"
						value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" />
					Contendo o texto</td>
				</tr>
				
				<tr>
					<td><strong> Sigla:</strong></td>
					<td width="66%"><html:text property="sigla" size="20" maxlength="20" /></td>
				</tr>
				<tr>
					<td><strong>Empresa:</strong></td>
					<td width="66%"><html:select property="idEmpresa">
						<html:option value="">&nbsp;</html:option>
						<html:options collection="colecaoEmpresa"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				<tr>
					<td><strong>Unidade Superior:</strong></td>
					<td width="66%"><html:text property="idUnidadeSuperior"
						size="6" maxlength="8"
						onkeypress="validaEnterComMensagem(event, 'exibirFiltrarUnidadeOrganizacionalAction.do?consultaUnidadeSuperior=1', 'idUnidadeSuperior', 'Unidade Superior');" />
					<a
						href="javascript:abrirPopup('exibirPesquisarUnidadeOrganizacionalAction.do', 250, 495);">
					<img src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						border="0" width="23" height="21" title="Pesquisar"></a> <logic:present
						name="corUnidadeSuperior">
						<logic:equal name="corUnidadeSuperior" value="exception">
							<html:text property="descricaoUnidadeSuperior" size="45"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="corUnidadeSuperior" value="exception">
							<html:text property="descricaoUnidadeSuperior" size="45"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent name="corUnidadeSuperior">
						<logic:empty name="UnidadeOrganizacionalActionForm"
							property="idUnidadeSuperior">
							<html:text property="descricaoUnidadeSuperior" size="45" value=""
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="UnidadeOrganizacionalActionForm"
							property="idUnidadeSuperior">
							<html:text property="descricaoUnidadeSuperior" size="45"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent> <a href="javascript:limparUnidadeSuperior();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				<tr>
					<td><strong>Unidade de Esgoto?</strong></td>
					<td width="66%" align="right">
					<div align="left"><html:radio property="unidadeEsgoto"
						value="<%=ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>" />
					<strong>Sim</strong> <html:radio property="unidadeEsgoto"
						value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>" />
					<strong>Não</strong> <html:radio property="unidadeEsgoto"
						tabindex="5" value="" /> <strong>Todos</strong></div>
					</td>
				</tr>
				<tr>
					<td><strong>Unidade Abre RA - Registro de Atendimento?</strong></td>
					<td width="66%" align="right">
					<div align="left"><html:radio property="unidadeAbreRA"
						value="<%=ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>" />
					<strong>Sim</strong> <html:radio property="unidadeAbreRA"
						value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>" />
					<strong>Não</strong> <html:radio property="unidadeAbreRA"
						value="" /> <strong>Todos</strong></div>
					</td>
				</tr>
				<tr>
					<td><strong>Unidade Aceita Tramitação?</strong></td>
					<td width="66%" align="right">
					<div align="left"><html:radio property="unidadeTramitacao"
						value="<%=ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>" />
					<strong>Sim</strong> <html:radio property="unidadeTramitacao"
						value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>" />
					<strong>Não</strong> <html:radio property="unidadeTramitacao"
						value="" /> <strong>Todos</strong></div>
					</td>
				</tr>
				<tr>
					<td><strong>Meio de Solicitação Padrão:</strong></td>
					<td width="66%"><html:select property="idMeioSolicitacao">
						<html:option value="">&nbsp;</html:option>
						<html:options collection="colecaoMeioSolicitacao"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				<tr>
					<td><strong>Indicador de Uso:</strong></td>
					<td width="66%" align="right">
					<div align="left"><html:radio property="indicadorUso"
						value="<%=ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>" />
					<strong>Ativo</strong> <html:radio property="indicadorUso"
						value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>" />
					<strong>Inativo</strong> <html:radio property="indicadorUso"
						value="" /> <strong>Todos</strong></div>
					</td>
				</tr>
				<tr>
					<td><!--<input name="Button" type="button" class="bottonRightCol" value="Limpar" align="left" onclick="window.location.href='<html:rewrite page="/exibirFiltrarUnidadeOrganizacionalAction.do?desfazer=S"/>'" >
                    	--><input type="button" class="bottonRightCol"
						value="Limpar" onClick="javascript:window.location.href='/gsan/exibirFiltrarUnidadeOrganizacionalAction.do?menu=sim'"></td>
					<td width="66%" align="right"><gcom:controleAcessoBotao
						name="Button" value="Filtrar"
						onclick="javascript:validarForm(document.UnidadeOrganizacionalActionForm);"
						url="filtrarUnidadeOrganizacionalAction.do" /></td>
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
