<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gsan"%>

<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>

<SCRIPT LANGUAGE="JavaScript">

	function validarForm(formulario) {
		
     	if (validarCampos()){
			submeterFormPadrao(formulario);
		}
	}

	function validarCampos(){
		var retorno = true;
		var form = document.forms[0];
		var msg = '';
		if(form.codigo.value == ''){
			msg = 'Informe o campo C�digo. \n';
		}
		if (form.codigo.value != null && form.codigo.value != ""){
			if (!validaCampoInteiroPositivo(form.codigo.value)){
				msg = 'O campo c�digo aceita apenas n�meros inteiros e positivos. \n';
				retorno = false;
			}
		}

		if (form.gerenciaRegional.value != null && form.gerenciaRegional.value == "-1"){
			if (!validaCampoInteiroPositivo(form.gerenciaRegional.value)){
				msg = msg +  'Informe o campo Ger�ncia Regional.';
				retorno = false;
			}
		}
		
		if(msg != ''){
			alert(msg);
		}
		
		return retorno;
	}

	function validarCampoInteiro(valorCampo, nomeCampo, descricaoCampo){
		
		var form = document.forms[0];

		if (valorCampo != null && !valorCampo == ""){
			if (!validaCampoInteiroPositivo(valorCampo)){
				if (nomeCampo == 'codigo'){
					form.codigo.value = "";
					alert('O campo '+ descricaoCampo + ' aceita apenas n�meros inteiros e positivos.');
				}
			}
		}
	}
  	
  	function limparForm() {
		var form = document.forms[0];
		form.codigo.value = "";
		form.descricao.value = "";
		form.descricaoAbreviada.value = "";
		form.gerenciaRegional.value = "-1";
	}	

</SCRIPT>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="InserirSistemaAbastecimentoActionForm" />


</head>

<body leftmargin="5" topmargin="5">

<html:form action="/inserirSistemaAbastecimentoAction" method="post">


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
					<td class="parabg">Inserir Sistema de Abastecimento</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para adicionar o sistema de abastecimento, informe os dados
					abaixo:</td>				
    			</tr>
    		</table>
    
    		<table width="100%" border="0">
    		
    			<tr>
					<td><strong>C�digo:
						<font color="#FF0000">*</font></strong>
					</td>
					
					<td align="right">
					<div align="left">
						<html:text maxlength="4"
							property="codigo" 
							size="4" 
							tabindex="1" 
							onblur="javascript:validarCampoInteiro(this.value, 'codigo', 'C�digo');"/></div>
					</td>
				</tr>
				
				<tr>
					<td><strong>Descri��o:
						<font color="#FF0000">*</font></strong>
					</td>
					
					<td align="right">
					<div align="left">
						<html:text maxlength="20"
							property="descricao" 
							size="52" 
							tabindex="2" /></div>
					</td>
				</tr>
				
				<tr>
					<td><strong>Descri��o Abreviada:</strong>
					</td>
					
					<td align="right">
					<div align="left">
						<html:text maxlength="6"
							property="descricaoAbreviada" 
							size="6" 
							tabindex="3" /></div>
					</td>
				</tr>
				
				<tr>
					<td><strong>Ger�ncia Regional:</strong>
					<font color="#FF0000">*</font>
					</td>
					
					<td align="right">
					<div align="left">
						<html:select property="gerenciaRegional" tabindex="4">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<html:options collection="gerenciasRegionais" property="id" labelProperty="nomeComId"/> 
	                   	</html:select>
						</div>
					</td>
				</tr>
			
				<tr>
					<td>&nbsp;</td>
					<td align="right">
						<div align="left">
							<strong><font color="#FF0000">*</font></strong>
							Campos obrigat&oacute;rios</div>
					</td>
				</tr>

				<tr>
					<td colspan="2">
						<input name="Button" 
						type="button"
						class="bottonRightCol" 
						value="Limpar" 
						align="left"
						tabindex="4"
						onclick="javascript:limparForm();">&nbsp; 
					&nbsp;
						<input type="button" 
								name="Button"
								class="bottonRightCol" 
								value="Cancelar" 
								tabindex="5"
								onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
								style="width: 80px" />
					</td>
					
					<td align="right">
						<gsan:controleAcessoBotao name="Button" 
							value="Inserir"
							tabindex="6"
							onclick="javascript:validarForm(document.forms[0]);"
							url="inserirSistemaAbastecimentoAction.do" />
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
