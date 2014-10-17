<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page import="gcom.util.ConstantesSistema"%>

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<%-- Carrega valida��es do validator --%>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="InserirValorCobrancaServicoActionForm" />
<%-- Carrega javascripts da biblioteca --%>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<%-- Novos Javascripts --%>
<script language="JavaScript">
	/* Valida Form */
	function validaForm() {
		var form = document.forms[0];
		/* Validate */
		if (validateInserirValorCobrancaServicoActionForm(form)) {
			if (validaRadioButton()) {
				if (validaIndicadorMedido()) {
					submeterFormPadrao(form);
				}
			}
		}
	}
	/* Valida Indicador de Medido */
	function validaIndicadorMedido() {
		var form = document.forms[0];
		
		if (form.indicadorMedido[0].checked && 
				form.capacidadeHidrometro.selectedIndex == '-1') {
			alert('Informe a Capacidade do Hidr�metro');
			return false;
		}
		return true;
	}
	/* Limpa Form */	 
	function limparForm() {
		var form = document.forms[0];

		form.indicadorMedido[0].checked = false;
		form.indicadorMedido[1].checked = false;
		form.capacidadeHidrometro.selectedIndex = '-1';
		form.perfilImovel.selectedIndex = '-1';
		form.valorServico.value = "";
		limparTipoServico();
	}
	/* Limpa Tipo Perfil Servico do Form */	 
	function limparTipoServico() {
		var form = document.forms[0];
		form.tipoServico.value = "";
		form.nomeTipoServico.value = "";
	}
	/* Chama Popup */ 
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){
		if(objetoRelacionado.disabled != true){
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
	/* Recupera Dados Popup */	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

      var form = document.InserirValorCobrancaServicoActionForm

      if (tipoConsulta == 'tipoServico') {
        form.tipoServico.value = codigoRegistro;
        form.nomeTipoServico.value = descricaoRegistro;
        form.nomeTipoServico.style.color = "#000000";
      }
      
    }
	/* Recarrega tela de exibi��o */
	function habilitarCapacidadeHidrometro() {
		var form = document.forms[0];
		
		if (form.indicadorMedido[0].checked) {
			form.capacidadeHidrometro.disabled = false;
		} else {
			form.capacidadeHidrometro.selectedIndex = '-1';
			form.capacidadeHidrometro.disabled = true;
		}
	}
	/* Valida Radio */
  	function validaRadioButton() {
		var form = document.forms[0];
		if (!form.indicadorMedido[0].checked
				&& !form.indicadorMedido[1].checked) {
			alert("Informe Indicador de Medido.");		
			return false;
		}		
		return true;
   	}		
</script>
</head>

<body leftmargin="5" topmargin="5"
	onload="habilitarCapacidadeHidrometro();">

<html:form action="/inserirValorCobrancaServicoAction.do"
	name="InserirValorCobrancaServicoActionForm"
	type="gcom.gui.atendimentopublico.ordemservico.InserirValorCobrancaServicoActionForm"
	method="post" focus="tipoServico">

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
			<td width="615" valign="top" class="centercoltext">
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
					<td class="parabg">Inserir Valor de Cobran�a de Servi�o</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<!--Inicio da Tabela Liga��o de Esgoto -->
			<table width="100%" border="0">
				<tr>
					<td height="31">
					<table width="100%" border="0" align="center">
						<tr>
							<td height="10" colspan="2">Para inserir um valor de cobran�a,
							informe os dados abaixo:</td>
						</tr>
						<tr>
							<td height="10" colspan="2">
							<hr>
							</td>
						</tr>
						<tr>
							<td height="10"><strong> Tipo do Servi�o: <span class="style2"> <strong>
							<font color="#FF0000">*</font> </strong> </span> </strong></td>
							<td><span class="style2"> <html:text property="tipoServico"
								size="5" maxlength="4"
								onkeypress="javascript:validaEnterComMensagem(event, 'exibirInserirValorCobrancaServicoAction.do', 'tipoServico','Tipo do Servi�o');" />
							<a
								href="javascript:abrirPopup('exibirPesquisarTipoServicoAction.do', 400, 800);">
							<img src="/gsan/imagens/pesquisa.gif" alt="Pesquisar" border="0"
								height="21" width="23"></a> <logic:present
								name="servicoTipoEncontrada" scope="session">
								<html:text property="nomeTipoServico" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000"
									size="38" />
							</logic:present> <logic:notPresent name="servicoTipoEncontrada"
								scope="session">
								<html:text property="nomeTipoServico" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000"
									size="38" />
							</logic:notPresent> <a href="javascript:limparTipoServico();"> <img
								border="0" title="Apagar"
								src="<bean:message key='caminho.imagens'/>limparcampo.gif" /></a>

							</span></td>
						</tr>
						<tr>
							<td><strong> Perfil do Im&oacute;vel: </strong></td>
							<td colspan="3" align="right">
							<div align="left"><html:select property="perfilImovel"
								style="width: 230px;">
								<html:option
									value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
								<logic:present name="colecaoImovelPerfil" scope="session">
									<html:options collection="colecaoImovelPerfil"
										labelProperty="descricao" property="id" />
								</logic:present>
							</html:select></div>
							</td>
						</tr>
						<tr>
							<td><strong> Indicador de Medido: <font color="#FF0000">*</font>
							</strong></td>
							<td colspan="3" align="right">
							<div align="left"><label> <html:radio property="indicadorMedido"
								value="1" onclick="javascript: habilitarCapacidadeHidrometro();" />
							Sim </label> <label> <html:radio property="indicadorMedido"
								value="2" onclick="javascript: habilitarCapacidadeHidrometro();" />
							N�o </label></div>
							</td>
						</tr>
						<tr>
							<td width="192"><strong> Capacidade do Hidr�metro: </strong></td>
							<td colspan="3" align="right">
							<div align="left"><html:select property="capacidadeHidrometro"
								style="width: 230px;">
								<html:option
									value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
								<logic:present name="colecaoHidrometroCapacidade"
									scope="session">
									<html:options collection="colecaoHidrometroCapacidade"
										labelProperty="descricao" property="id" />
								</logic:present>
							</html:select></div>
							</td>
						</tr>
						<tr>
							<td><strong> Valor do Servi�o: <font color="#FF0000">*</font> </strong>
							</td>
							<td colspan="3" align="right">
							<div align="left"><html:text property="valorServico" size="14"
								maxlength="14"
								onkeyup="javascript:formataValorMonetario(this, 11)"
								style="text-align:right;" /></div>
							</td>
						</tr>
						<tr>
							<td><strong> <font color="#FF0000"></font> </strong></td>
							<td colspan="3" align="right">
							<div align="left"><strong> <font color="#FF0000">*</font> </strong>
							Campos obrigat&oacute;rios</div>
							</td>
						</tr>
						<tr>
							<td><strong> <font color="#FF0000"> <input type="button"
								name="Submit22" class="bottonRightCol" value="Desfazer"
								onClick="javascript:limparForm();"> <input type="button"
								name="Submit23" class="bottonRightCol" value="Cancelar"
								onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
							</font> </strong></td>
							<td colspan="3" align="right"><input type="button" name="Submit2"
								class="bottonRightCol" value="Inserir" onclick="validaForm();">
							</td>
						</tr>
					</table>
					<p>&nbsp;</p>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<!-- Fim do Corpo - Leonardo Regis -->

	<!-- Rodap� -->
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>
