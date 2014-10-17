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

		if (isNaN(form.idFonteDadosCensitarios.value) || form.idFonteDadosCensitarios.value == ""){
			alert('Informe Fonte de Dados.');
			return false;
		}
		
		return retorno;
	}

	function validarCampoInteiro(valorCampo, nomeCampo, descricaoCampo){
		
		var form = document.forms[0];

		if (valorCampo != null && !valorCampo == ""){
			if (!validaCampoInteiroPositivo(valorCampo)){
				if (nomeCampo == 'populacaoUrbana'){
					form.populacaoUrbana.value = "";
					alert('O campo '+ descricaoCampo + ' aceita apenas números inteiros e positivos.');
				}else if (nomeCampo == 'populacaoRural'){
					form.populacaoRural.value = "";
					alert('O campo '+ descricaoCampo + ' aceita apenas números inteiros e positivos.');
				}
			}
		}
	}

</SCRIPT>

</head>

<body leftmargin="5" topmargin="5">

<html:form action="/atualizarDadosCensitariosAction" method="post">

	<html:hidden name="AtualizarDadosCensitariosActionForm" property="id" />
	
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
					<td class="parabg">Atualizar Dados Censit&aacute;rios</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para alterar os dados censit&aacute;rios, informe os dados
					abaixo:</td>				
    			</tr>
    		</table>
    
    		<table width="100%" border="0">
    		
      			<logic:equal name="AtualizarDadosCensitariosActionForm" property="indicadorLocalidadeMunicipio" value="<%=""+ConstantesSistema.DADOS_CENSITARIOS_LOCALIDADE%>">
	      		<tr>
		      		<td><strong>Localidade:<font color="#FF0000">*</font></strong></td>
	                <td width="25%">
	                   <html:text 
						property="localidade" 
						size="45" 
						tabindex="1"
						readonly="true"/>
	                </td>
	      		</tr>
	      		</logic:equal>
				<logic:equal name="AtualizarDadosCensitariosActionForm" property="indicadorLocalidadeMunicipio" value="<%=""+ConstantesSistema.DADOS_CENSITARIOS_MUNICIPIO%>">
	      		<tr>
		      		<td><strong>Munic&iacute;pio:<font color="#FF0000">*</font></strong></td>
	                <td width="25%">
	                   <html:text
						property="municipio" 
						size="45" 
						tabindex="2"
						readonly="true"/>
	                </td>
	      		</tr>
	      		</logic:equal>
	      		
	      		<tr>
                  <td><strong>Mês/Ano:<font color="#FF0000">*</font></strong></td>
                  <td><html:text maxlength="7" property="anoMesReferencia" size="7" 
                  		onblur="verificaAnoMesNovo(this);" 
                  		onkeyup="mascaraAnoMes(this, event);"
                  		readonly="true"
                  		tabindex="3"/>
                    &nbsp; mm/aaaa</td>
                </tr>
    		
    			<tr>
		      		<td><strong>Fonte de Informação:<font color="#FF0000">*</font></strong></td>
	                <td width="25%">
	                    <html:select property="idFonteDadosCensitarios" tabindex="4">
								<html:option value="">&nbsp;</html:option>
								<html:options collection="colecaoFonteDadosCensitarios" labelProperty="descricao" property="id" />
						</html:select>
	                </td>
	      		</tr>
	      		
	      		<tr>
					<td><strong>População Urbana:</strong>
					</td>
					
					<td width="25%">
					<div align="left">
						<html:text maxlength="7"
							property="populacaoUrbana" 
							size="7" 
							tabindex="5"
							onblur="javascript:validarCampoInteiro(this.value, 'populacaoUrbana', 'Pópulação Urbana');"/></div>
					</td>
		  	  	</tr>
		  	  	
		  	  	<tr>
					<td><strong>Taxa Anual de Crescimento da População Urbana:</strong></td>

					<td width="25%"> <strong> <html:text property="taxaCrescimentoAnualPopulacaoUrbana" 
						style="text-align:right;"
						onkeyup="formataValorMonetario(this, 5);" 
						size="6"
						tabindex="6" 
						maxlength="6" /></strong>
					</td>
				</tr>
				
				<tr>
					<td><strong>Taxa de ocupação da população urbana:</strong></td>

					<td width="25%"> <strong> <html:text property="taxaOcupacionalPopulacaoUrbana" 
						style="text-align:right;"
						onkeyup="formataValorMonetario(this, 5);" 
						size="6"
						tabindex="7" 
						maxlength="6" /></strong>
					</td>
				</tr>
				
				<tr>
					<td><strong>População Rural:</strong>
					</td>
					
					<td width="25%">
					<div align="left">
						<html:text maxlength="7"
							property="populacaoRural" 
							size="7" 
							tabindex="8"
							onblur="javascript:validarCampoInteiro(this.value, 'populacaoRural', 'Pópulação Rural');"/></div>
					</td>
		  	  	</tr>
		  	  	
		  	  	<tr>
					<td><strong>Taxa Anual de Crescimento da População Rural:</strong></td>

					<td width="25%"> <strong> <html:text property="taxaCrescimentoAnualPopulacaoRural" 
						style="text-align:right;"
						onkeyup="formataValorMonetario(this, 5);" 
						size="6"
						tabindex="9" 
						maxlength="6" /></strong>
					</td>
				</tr>
				
				<tr>
					<td><strong>Taxa de Ocupação da População Rural:</strong></td>

					<td width="25%"> <strong> <html:text property="taxaOcupacionalPopulacaoRural" 
						style="text-align:right;"
						onkeyup="formataValorMonetario(this, 5);" 
						size="6" 
						tabindex="10"
						maxlength="6" /></strong>
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
	      			<td colspan="2" widt="100%">
	      			<table width="100%">
						<tr>
							<tr>
								<td width="40%" align="left">
									<input type="button"
										name="ButtonCancelar" 
										class="bottonRightCol" 
										value="Voltar"
										onClick="window.history.go(-1)"> 
									<input type="button"
										name="ButtonReset" 
										class="bottonRightCol" 
										value="Desfazer"
										onClick="window.location.href='/gsan/exibirAtualizarDadosCensitariosAction.do?idRegistroAtualizacao=<bean:write name="AtualizarDadosCensitariosActionForm" property="id" />&desfazerLocalidadeOuMunicipio=<bean:write name="AtualizarDadosCensitariosActionForm" property="indicadorLocalidadeMunicipio" />'"> 
									<input type="button"
										name="ButtonCancelar" 
										class="bottonRightCol" 
										value="Cancelar"
										onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
								</td>
								<td align="right">
									<input type="button"
										onClick="javascript:validarForm(document.forms[0]);"
										name="botaoAtualizar" 
										class="bottonRightCol" 
										value="Atualizar">
								</td>
							</tr>
		      			
	      			</table>
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
