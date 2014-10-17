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
<html:javascript staticJavascript="false"  formName="FiltrarSetorAbastecimentoActionForm"
	 />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="Javascript">
	
	function validarForm(form) {
	 	if (validateFiltrarSetorAbastecimentoActionForm(form)){
			submeterFormPadrao(form);
		}
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
<html:form action="/filtrarSetorAbastecimentoAction" method="post"
	onsubmit="return validateFiltrarSetorAbastecimentoActionForm(this);">

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
					<td class="parabg">Filtrar Setor de Abastecimento</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>
			<table bordercolor="#000000" width="100%" cellspacing="0" >
				<tr>
					<td>
					Para manter o(s) Setor(s) de Abastecimento, informe os dados abaixo:
					</td>
					<td width="84">
					<input name="atualizar" type="checkbox"
						checked="checked" onclick="javascript:setarAtualizar();" value="1"> <strong>Atualizar</strong>
					</td>
    			</tr>
   			</table>
   			
   			<table width="100%" border="0">
   			
   				<tr>
					<td><strong>Código do Setor de Abastecimento:</strong>
					</td>
					
					<td align="right">
					<div align="left">
						<html:text maxlength="4"
							property="codigo" 
							size="4" 
							tabindex="1" 
							onblur="javascript:validarCampoInteiro(this.value, 'codigo', 'Código');"/></div>
					</td>
				</tr>
   				
				<tr>
					<td><strong>Descrição do Setor de Abastecimento:</strong></td>
					<td colspan="2"><span class="style2"> <html:text
						property="descricao" size="50" maxlength="30" tabindex="2"/> </span></td>
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
			  		<td  width="40%"class="style3">
			  			<strong>
			  				Sistema de Abastecimento: 
			  			</strong>
			  		</td>
			  		<td  width="60%" colspan="2">
			  			<html:select property="sistemaAbastecimento" tabindex="4" style="width:200px;" onchange="javascript:pesquisaColecaoReload('exibirFiltrarSetorAbastecimentoAction.do?consultaSistema=1','sistemaAbastecimento');">
							<html:option value="-1"> &nbsp; </html:option>
							<html:options collection="colecaoSistemaAbastecimento" property="id" labelProperty="descricaoComCodigo"/>
						</html:select>
					</td>
				</tr>
				
				<tr>
			  		<td  width="40%"class="style3">
			  			<strong>
			  				Distrito Operacional: 
			  			</strong>
			  		</td>
			  		<td  width="60%" colspan="2">
			  			<html:select property="distritoOperacional" tabindex="4" style="width:200px;" onchange="javascript:pesquisaColecaoReload('exibirFiltrarSetorAbastecimentoAction.do?consultaSistema=1','distritoOperacional');">
							<html:option value="-1"> &nbsp; </html:option>
							<html:options collection="colecaoDistritoOperacional" property="id" labelProperty="descricaoComId"/>
						</html:select>
					</td>
				</tr>
				
				<tr>
			  		<td  width="40%"class="style3">
			  			<strong>
			  				Zona de Abastecimento: 
			  			</strong>
			  		</td>
			  		<td  width="60%" colspan="2">
			  			<html:select property="zonaAbastecimento" tabindex="4" style="width:200px;">
							<html:option value="-1"> &nbsp; </html:option>
							<html:options collection="colecaoZonaAbastecimento" property="id" labelProperty="descricaoComCodigo"/>
						</html:select>
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
							onclick="window.location.href='/gsan/exibirFiltrarSetorAbastecimentoAction.do?menu=sim';"> 			
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
