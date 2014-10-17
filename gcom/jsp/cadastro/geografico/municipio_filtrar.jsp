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

<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="FiltrarMunicipioActionForm" dynamicJavascript="true" />

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js">
</script>	
	
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
		var form = document.FiltrarMunicipioActionForm;
		
		form.codigoMunicipio.focus();
	}
		
	function validarForm(formulario){
		var objMunicipio = returnObject(formulario, "codigoMunicipio");
	
		if (objMunicipio.value.length > 0 && !testarCampoValorZero(objMunicipio, "Código do Município")){
			objMunicipio.focus();
		}
		else if (validateFiltrarMunicipioActionForm(formulario)){
			submeterFormPadrao(formulario);
		}
	}
	
	function verificarValorAtualizar(){
		var form = document.FiltrarMunicipioActionForm;
       	
       	if (form.indicadorAtualizar.checked == true) {
       		form.indicadorAtualizar.value = '1';
       	} else {
       		form.indicadorAtualizar.value = '';
       	}
       	
	}
	
	
	function redicionaMicrorregiao(){
		
		urlRedirect = "/gsan/exibirFiltrarMunicipioAction.do?combo=sim";
		redirecionaSubmit(urlRedirect);
	} 

	function bloquearMicrorregio(){
		var form = document.FiltrarMunicipioActionForm;
		
		if(form.regiao.value == '-1'){
			form.microregiao.disabled = true;
		} 
	}
	
	function redirecionaSubmit(caminhoAction) {

	   	var form = document.forms[0];
	   	form.action = caminhoAction;
	   	form.submit();
	
	   	return true;
 	}
		 
</script>
</head>

<body leftmargin="5" topmargin="5" 
		onload="setaFocus('${requestScope.nomeCampo}');verificarChecado('${indicadorAtualizar}');bloquearMicrorregio();">

	<html:form action="/filtrarMunicipioAction"
		name="FiltrarMunicipioActionForm"
		type="gcom.gui.cadastro.geografico.FiltrarMunicipioActionForm"
		method="post"
		onsubmit="return validateFiltrarMunicipioActionForm(this);">
		
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
							<td class="parabg">Filtrar Município</td>
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
									<td width="80%">Para filtrar o(s) município(s), informe os dados abaixo:</td>
									<td align="right"><input type="checkbox"
										name="indicadorAtualizar" value="1"
										onclick="javascript:verificarValorAtualizar();" /><strong>Atualizar</strong>
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
		
					<table width="100%" border="0">
						<tr>
					  		<td  width="40%" class="style3"><strong>Código do Município:</strong></td>
					  		<td  width="60%" colspan="2"><strong><b><span class="style2"> 
					  			<html:text property="codigoMunicipio" size="4" maxlength="4" tabindex="1"/> </span></b></strong></td>
						</tr>
						
						
						<tr>
							<td><strong>Nome do Município:</strong></td>
							<td><html:text property="nomeMunicipio" size="40" maxlength="30"
								tabindex="2" /></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><html:radio property="tipoPesquisa"	tabindex="3"
								value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" />
								Iniciando pelo texto
								<html:radio	property="tipoPesquisa" tabindex="4"
									value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" />
								Contendo o texto
							</td>
						</tr>
						
						<logic:equal name="sistemaParametro" property="parmId" scope="session" value="<%=SistemaParametro.INDICADOR_EMPRESA_ADA.toString()%>">
						<tr>
							 <td  width="40%"class="style3"><strong>Região de Desenvolvimento:</strong></td>
							 <td  width="60%" colspan="2">
							  			<html:select property="regiaoDesenv" tabindex="5" style="width:200px;">
											<html:option value="-1"> &nbsp; </html:option>
											<html:options collection="colecaoRegiaoDesenv" property="id" labelProperty="nome"/>
										</html:select></td>
						</tr>
						</logic:equal>
						
						
						<tr>
							 <td  width="40%"class="style3"><strong>Região:</strong></td>
							 <td  width="60%" colspan="2">
							  			<html:select property="regiao" tabindex="6" style="width:200px;" 
							  					      onchange="javascript:redicionaMicrorregiao();">
											<html:option value="-1"> &nbsp; </html:option>
											<html:options collection="colecaoRegiao" property="id" labelProperty="nome"/>
										</html:select></td>
						</tr>
						
						
						<tr>
							 <td  width="40%"class="style3"><strong>Microrregião:</strong></td>
							 <td  width="60%" colspan="2">
							 
								<logic:present name="colecaoMicrorregiao">
										<html:select property="microregiao" tabindex="7" style="width:200px;">
											<html:option value="-1"> &nbsp; </html:option>
											<html:options collection="colecaoMicrorregiao" property="id" labelProperty="nome"/>
										</html:select>
								</logic:present>
								
								<logic:notPresent name="colecaoMicrorregiao">
									<html:select property="microregiao" tabindex="7" style="width:200px;" disabled="disabled">
											<html:option value="-1"> &nbsp; </html:option> 
									</html:select>
								</logic:notPresent>
								
							 </td> 			
						</tr>
						
						
						<tr>
							 <td  width="40%"class="style3"><strong>Unidade da Federação:</strong></td>
							 <td  width="60%" colspan="2">
							  			<html:select property="unidadeFederacao" tabindex="8" style="width:200px;">
											<html:option value="-1"> &nbsp; </html:option>
											<html:options collection="colecaoUnidadeFederacao" property="id" labelProperty="descricao"/>
										</html:select></td>
						</tr>
						
										
						<tr>
							<td><strong>Indicador de uso:</strong></td>
							<td><html:radio property="indicadorUso" value="1" tabindex="9"/><strong>Ativo 
								<html:radio	property="indicadorUso" value="2" tabindex="10"/>Inativo 
								<html:radio	property="indicadorUso" value="3" tabindex="11"/>Todos</strong></td>
						</tr>
						
						<tr>
							<td>
								<input name="Button" 
									type="button" 
									class="bottonRightCol"
									value="Limpar" 
									align="left"
									onclick="window.location.href='/gsan/exibirFiltrarMunicipioAction.do?menu=sim'">
							</td>
							<td align="right">
								<input type="button"
									onclick="validarForm(document.forms[0]);" 
									class="bottonRightCol"
									value="Filtrar" 
									tabindex="12" 
									style="width: 70px;">
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