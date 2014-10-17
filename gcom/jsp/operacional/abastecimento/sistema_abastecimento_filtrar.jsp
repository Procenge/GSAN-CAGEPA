<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="gcom.util.Pagina, gcom.util.ConstantesSistema"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gsan"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="FiltrarSistemaAbastecimentoActionForm"
	 />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="Javascript">
	
	function validarForm(formulario){
		
		if(validarCampos()){    		 
  			submeterFormPadrao(formulario);
		}
	}

	function validarCampos(){
		var retorno = true;
		var form = document.forms[0];

		if (form.codigo.value != null && !form.codigo.value == ""){
			if (!validaCampoInteiroPositivo(form.codigo.value)){
				alert('O campo código aceita apenas números inteiros e positivos.');
				return false;
			}
		}
		
		return retorno;
	}

	function validarCampoInteiro(valorCampo, nomeCampo, descricaoCampo){
		
		var form = document.forms[0];

		if (valorCampo != null && !valorCampo == ""){
			if (!validaCampoInteiroPositivo(valorCampo)){
				if (nomeCampo == 'codigo'){
					form.codigo.value = "";
					alert('O campo '+ descricaoCampo + ' aceita apenas números inteiros e positivos.');
				}
			}
		}
	}

	function setarAtualizar(){
		var formulario = document.forms[0];
		
		if (formulario.indicadorAtualizar.value == "1"){
			formulario.indicadorAtualizar.value = "2"; 
		}else{
			formulario.indicadorAtualizar.value = "1";
		}
	}
	
</script>

</head>


<body leftmargin="5" topmargin="5"
	onload="document.forms[0].indicadorAtualizar.checked=true">
<html:form action="/filtrarSistemaAbastecimentoAction" method="post"
	onsubmit="return validateFiltrarSistemaAbastecimentoActionForm(this);">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
	<html:hidden property="indicadorAtualizar" value="1"/>
	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="115" valign="top" class="leftcoltext">

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
					<td class="parabg">Filtrar Sistema de Abastecimento</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>
			<table bordercolor="#000000" width="100%" cellspacing="0" >
				<tr>
					<td>
					Para manter o(s) Sistema(s) de Abastecimento, informe os dados abaixo:
					</td>
					<td width="84">
					<input name="atualizar" type="checkbox"
						checked="checked" onclick="javascript:setarAtualizar();" value="1"> <strong>Atualizar</strong>
					</td>
    			</tr>
   			</table>
   			
   			<table width="100%" border="0">
   			
   				<tr>
					<td><strong>Código:</strong></td>
					<td><html:text property="codigo" size="3" tabindex="1"
						maxlength="4" onkeypress="return isCampoNumerico(event);" onblur="javascript:validarCampoInteiro(this.value, 'codigo', 'Código');"/></td>
				</tr>
   				
				<tr>
					<td><strong>Descri&ccedil;&atilde;o:</strong></td>
					<td colspan="2"><span class="style2"> <html:text
						property="descricao" size="50" maxlength="20" tabindex="2"/> </span></td>
					<td width="15%">&nbsp;</td>
				</tr>
				
				<tr>
					<td>&nbsp;</td>
					<td><html:radio property="tipoPesquisa" tabindex="4"
						value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" />
					Iniciando pelo texto <html:radio property="tipoPesquisa"
						tabindex="4"
						value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" />
					Contendo o texto</td>
					<td>&nbsp;</td>
				</tr>

				<tr>
					<td><strong>Descrição Abreviada:</strong></td>
					<td><html:text property="descricaoAbreviada" size="6" tabindex="5"
						maxlength="6" /></td>
				</tr>
				
				<tr>
			        <td height="30"><strong>Indicador de uso:</strong></td>
			        <td>
						<html:radio property="indicadorUso" value="1"/><strong>Ativo
						<html:radio property="indicadorUso" value="2"/>Inativo</strong>
						<html:radio property="indicadorUso"	tabindex="5" value="" /><strong>Todos</strong>
					</td>
     		   </tr>
				
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						<input name="Submit22"
							class="bottonRightCol" 
							value="Limpar" 
							type="button"
							onclick="window.location.href='/gsan/exibirFiltrarSistemaAbastecimentoAction.do?menu=sim';"> 			
					</td>
					<td>
						<input type="button" 
								name="Button"
								class="bottonRightCol" 
								value="Cancelar" 
								tabindex="6"
								onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
								style="width: 80px" />
					</td>
					
					<td align="right">
					<td align="right">
					<td width="65" align="right">
						<input name="Button2" 
							   type="button"
							class="bottonRightCol" 
							value="Filtrar" 
							align="right"
							onClick="javascript:validarForm(document.forms[0]);" 
							tabindex="7" />
						
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
