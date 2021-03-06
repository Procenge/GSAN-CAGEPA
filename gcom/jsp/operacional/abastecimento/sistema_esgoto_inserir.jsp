<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@page isELIgnored="false"%>

<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
	<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
	<html:javascript staticJavascript="false"  formName="InserirSistemaEsgotoActionForm"
	dynamicJavascript="true" />

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js">
</script>	

<!-- Valida��es de JavaScript para o Cliente DESO -->
<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
<script>
	function validarForm() {
		var form = document.forms[0];
		if(validateInserirSistemaEsgotoActionForm(form)){	     
			submeterFormPadrao(form); 
		}
	}

	function validateInserirSistemaEsgotoActionForm(form){
		return validateRequired(form);
	}

	function required () {
		this.aa1 = new Array("codigoSistemaEsgoto", "Informe o C�digo.", new Function ("varName", " return this[varName];"));
		this.aa = new Array("descricaoSistemaEsgoto", "Informe a Descri��o.", new Function ("varName", " return this[varName];"));
		this.ab = new Array("gerenciaRegionalID", "Informe a Ger�ncia Regional.", new Function ("varName", " return this[varName];"));
	}

	function limparForm() {
		var form = document.forms[0];
		form.codigoSistemaEsgoto.value = "";
		form.descricaoSistemaEsgoto.value = "";
		form.descricaoAbreviada.value = "";
		form.gerenciaRegionalID.value = "-1";
	}
</script>
</logic:equal>

<!-- Valida��es de JavaScript para o Cliente ADA -->
<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">	
<script language="JavaScript">
	function validarForm() {
		var form = document.forms[0];
		if(validateInserirSistemaEsgotoActionForm(form)){	     
		   	submeterFormPadrao(form); 
		  }
   	  }
 
  
       
  	function limparForm() {
		var form = document.forms[0];
		form.descricaoSistemaEsgoto.value = "";
	    form.descricaoAbreviada.value = "";
	    form.divisaoEsgoto.value = "-1";
        form.tipoTratamento.value = "-1";	    
		form.gerenciaRegionalID.value = "-1";
	 }
	 
	 
</script>
</logic:equal>
</head>



<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/inserirSistemaEsgotoAction.do"
	name="InserirSistemaEsgotoActionForm"
	type="gcom.gui.operacional.abastecimento.InserirSistemaEsgotoActionForm"
	method="post"
	onsubmit="return InserirSistemaEsgotoActionForm(this);">
	
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


		<td valign="top" class="centercoltext">
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
				<td class="parabg">Inserir Sistema de Esgoto</td>
				<td width="11" valign="top"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
			</tr>
		</table>

		<p>&nbsp;</p>
		<table width="100%" border="0">
			<tr>
				<td colspan="2">Para adicionar o sistema de esgoto, informe os dados abaixo:</td>
			</tr>
			<tr>
			  <td  width="30%" class="style3"><strong>C�digo:<font color="#FF0000">*</font></strong></td>
			  <td  width="70%" colspan="2"><strong><b><span class="style2"> 
			  		<html:text  property="codigoSistemaEsgoto" size="3" maxlength="3" tabindex="1" onkeypress="return isCampoNumerico(event);" /> </span></b></strong></td>
			</tr>	
			<tr>
			  <td  width="30%" class="style3"><strong>Descri��o:<font color="#FF0000">*</font></strong></td>
			  <td  width="70%" colspan="2"><strong><b><span class="style2"> 
			  		<html:text  property="descricaoSistemaEsgoto" size="20" maxlength="20" tabindex="1"/> </span></b></strong></td>
			</tr>	
			
			<tr>
			  <td  width="30%" class="style3"><strong>Descri��o Abreviada:</strong></td>
			  <td  width="70%" colspan="2"><strong><b><span class="style2"> 
			  		<html:text  property="descricaoAbreviada" size="6" maxlength="6" tabindex="2"/> </span></b></strong></td>
			</tr>
			
			<!-- C�digo restrito ao cliente ADA -->
			<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">
			<tr>
			  <td  width="30%"class="style3"><strong>Divis�o de Esgoto:<font color="#FF0000">*</font></strong></td>
			  <td  width="70%" colspan="2">
			  			<html:select property="divisaoEsgoto" tabindex="3" style="width:200px;">
							<html:option value="-1"> &nbsp; </html:option>
							<html:options collection="colecaoDivisaoEsgoto" property="id" labelProperty="descricao"/>
						</html:select></td>
			</tr>	
			
			
			<tr>
			  <td  width="30%"class="style3"><strong>Tipo de Tratamento:<font color="#FF0000">*</font></strong></td>
			  <td  width="70%" colspan="2">
			  			<html:select property="tipoTratamento" tabindex="4" style="width:200px;">
							<html:option value="-1"> &nbsp; </html:option>
							<html:options collection="colecaoSistemaTratamentoTipo" property="id" labelProperty="descricao"/>
						</html:select></td>
			</tr>
			</logic:equal>
			
			<tr>
				<td  width="30%"class="style3"><strong>Ger�ncia Regional:<font color="#FF0000">*</font></strong></td>
				<td  width="70%" colspan="2">
					<html:select property="gerenciaRegionalID" tabindex="5" style="width:200px;">
						<html:option value="-1"> &nbsp; </html:option>
						<html:options collection="colecaoGerenciaRegional" property="id" labelProperty="nomeComId"/>
					</html:select>
				</td>
			</tr>
			
			
				<table width="100%">
						<tr>
								<td height="19"><strong> <font color="#FF0000"></font></strong></td>
								
								<td align="right"><div align="left"><strong><font color="#FF0000">*</font></strong>
								Campos obrigat&oacute;rios</div>
								</td>
						</tr>
						<tr>
							  <td width="40%" align="left">				
								<input type="button" name="ButtonReset" class="bottonRightCol"
								value="Desfazer" onClick="limparForm();"> 
								
								<input type="button"
								name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
								onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
							  </td>
							  
							  <td align="right"><input type="button" name="Button" class="bottonRightCol"
									value="Inserir" onclick="validarForm();" tabindex="6"/>
							  </td>
						</tr>
				</table>
			
          </table>
          
</table>
<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</html:html>
