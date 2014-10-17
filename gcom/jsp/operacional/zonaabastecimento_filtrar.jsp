<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>
<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
	<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
	<html:javascript staticJavascript="false"  formName="FiltrarZonaAbastecimentoActionForm" dynamicJavascript="true" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
	
<script language="JavaScript">
  
    function verificarChecado(valor){
		form = document.forms[0];
		if(valor == "1"){
		 	form.indicadorAtualizar.checked = true;
		 }else{
		 	form.indicadorAtualizar.checked = false;
		}
	}
	
	function setaFocus(){
		var form = document.FiltrarZonaAbastecimentoActionForm;
		form.descricao.focus();
	}
	
	function verificarValorAtualizar(){
		var form = document.FiltrarZonaAbastecimentoActionForm;
       	
       	if (form.indicadorAtualizar.checked == true) {
       		form.indicadorAtualizar.value = '1';
       	} else {
       		form.indicadorAtualizar.value = '';
       	}
       	
	}
	
	function validarForm() {
      var form = document.forms[0];
      
      form.action = 'filtrarZonaAbastecimentoAction.do';
	  if(validateFiltrarZonaAbastecimentoActionForm(form)){	
	  		submeterFormPadrao(form); 
   	  	} 
	 }
	
	
</script>
</head>



<body leftmargin="5" topmargin="5" onload="verificarChecado('${indicadorAtualizar}');setaFocus();">

<html:form action="/filtrarZonaAbastecimentoAction"
	name="FiltrarZonaAbastecimentoActionForm"
	type="gcom.gui.operacional.FiltrarZonaAbastecimentoActionForm"
	method="post"
	onsubmit="return validateFiltrarZonaAbastecimentoActionForm(this);">
	
	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

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
					<td class="parabg">Filtrar Zona de Abastecimento</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">

				<tr>
					<td width="100%" colspan="3">
					<table width="100%">
						<tr>
							<td width="80%">Para filtrar a(s) Zona de Abastecimento(s), informe os dados abaixo:</td>
							<td align="right"><input type="checkbox" name="indicadorAtualizar" value="1"
								onclick="javascript:verificarValorAtualizar();" /><strong>Atualizar</strong>
							</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>

			<table width="100%" border="0">
				<tr>
					<td><strong>Sistema de Abastecimento:</strong></td>
					<td><html:select property="idSistemaAbastecimento"
						style="width: 290px;" tabindex="1">
						<html:option
							value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoSistemaAbastecimento">
							<html:options collection="colecaoSistemaAbastecimento"
								labelProperty="descricaoComCodigo" property="id" />
						</logic:present>
					</html:select></td>
				</tr>
				<tr>
					<td><strong>Distrito Operacional:</strong></td>
					<td>
						<html:select property="idDistritoOperacional" style="width: 290px;" tabindex="2">
						<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoDistritoOperacional" scope="session">
								<logic:notEmpty name="colecaoDistritoOperacional" scope="session">
									<html:options collection="colecaoDistritoOperacional" labelProperty="idComDescricao" property="id" />
								</logic:notEmpty>
							</logic:present>
						</html:select>
					</td>
				</tr>
				<tr>
					<td><strong>Localidade:</strong></td>
					<td>
						<html:select property="idLocalidade" style="width: 290px;" tabindex="3">
							<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoLocalidade" scope="session">
								<logic:notEmpty name="colecaoLocalidade" scope="session">
									<html:options collection="colecaoLocalidade" labelProperty="descricaoComId" property="id" />
								</logic:notEmpty>
							</logic:present>
						</html:select>
					</td>
				</tr>	
				<tr>
					<td><strong>Código:</strong></td>
					<td><html:text property="codigo" size="2" maxlength="2"
						tabindex="4" /></td>
				</tr>
				
				<tr>
					<td><strong>Descrição:</strong></td>
					<td><html:text property="descricao" size="30" maxlength="30"
						tabindex="5" /></td>
				</tr>

				<tr>
					<td>&nbsp;</td>
					<td><html:radio property="tipoPesquisa"	tabindex="6"
						value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" />
						Iniciando pelo texto
						<html:radio	property="tipoPesquisa" tabindex="6"
						value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" />
						Contendo o texto
					</td>
				</tr>				
				<tr>
					<td><strong>Indicador de uso:</strong></td>
					<td><html:radio property="indicadorUso" tabindex="7" value="1"/>Ativo
						<html:radio	property="indicadorUso" tabindex="7" value="2"/>Inativo
						<html:radio	property="indicadorUso" tabindex="7" value=""/>Todos
					</td>
				</tr>																
				
				<tr>
					<td><input name="Button" type="button" class="bottonRightCol" tabindex="8" value="Limpar" align="left"
						onclick="window.location.href='/gsan/exibirFiltrarZonaAbastecimentoAction.do?menu=sim'">
					</td>
					<td align="right"><INPUT type="button" onclick="javascript:validarForm();" 
						class="bottonRightCol" 	value="Filtrar" tabindex="9" style="width: 70px;"></td>
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

