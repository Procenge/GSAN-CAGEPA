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

	function exibirCamposFormulario() {
		var form = document.forms[0];
		form.action = '/gsan/exibirInserirDadosCensitariosAction.do';
		form.submit();
	}

	function validarCampos(){
		var retorno = true;
		var form = document.forms[0];

		var DATA_ATUAL = document.getElementById("DATA_ATUAL").value;
		var REFERENCIA_I = form.anoMesReferencia.value.substring(3,7) + form.anoMesReferencia.value.substring(0,2);
		var REFERENCIA_A = DATA_ATUAL.substring(6,10) + DATA_ATUAL.substring(3,5);
		var MES_ANO_C = DATA_ATUAL.substring(3,5) + DATA_ATUAL.substring(5,6) + DATA_ATUAL.substring(6,10);

		if (document.all.iLocalidadeMunicipio[0].checked 
				&& (isNaN(form.codigoLocalidade.value) || form.codigoLocalidade.value == "")){
			alert('Informe Localidade.');
			return false;
			
		}else if (document.all.iLocalidadeMunicipio[1].checked 
				&& (isNaN(form.codigoMunicipio.value) || form.codigoMunicipio.value == "")){
			alert('Informe Município.');
			return false;
		}else if (isNaN(form.idFonteDadosCensitarios.value) || form.idFonteDadosCensitarios.value == ""){
			alert('Informe Fonte de Informação.');
			return false;
		}else if (form.anoMesReferencia.value == ""){
			alert('Informe Mês/Ano.');
			return false;
		}else if (REFERENCIA_A < REFERENCIA_I){
		    alert("Mês/Ano informado posterior ao Mês e Ano corrente " + MES_ANO_C + ".");
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
  	
  	function limparForm() {
		var form = document.forms[0];

		form.codigoLocalidade.value = "";
		form.codigoMunicipio.value = "";

		form.anoMesReferencia.value = "";
		form.idFonteDadosCensitarios.value = "";
		form.populacaoUrbana.value = "";
		form.taxaCrescimentoAnualPopulacaoUrbana.value = "";
		form.taxaOcupacionalPopulacaoUrbana.value = "";
		form.populacaoRural.value = "";
		form.taxaCrescimentoAnualPopulacaoRural.value = "";
		form.taxaOcupacionalPopulacaoRural.value = "";
	}	

</SCRIPT>

</head>

<body leftmargin="5" topmargin="5">

<html:form action="/inserirDadosCensitariosAction" method="post">


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
					<td class="parabg">Inserir Dados Censit&aacute;rios</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para adicionar os dados censit&aacute;rios, informe os dados
					abaixo:</td>				
    			</tr>
    		</table>
    
    		<table width="100%" border="0">
    		
    			<tr>
	        		<td><strong>Localidade ou Munic&iacute;pio do IBGE:<font color="#FF0000">*</font></strong></td>
	        		<td width="25%">
						<html:radio styleId="iLocalidadeMunicipio" property="indicadorLocalidadeMunicipio" value="<%=""+ConstantesSistema.DADOS_CENSITARIOS_LOCALIDADE%>" 
							onclick="javascript:exibirCamposFormulario();" tabindex="1"/><strong>Localidade
						<html:radio styleId="iLocalidadeMunicipio" property="indicadorLocalidadeMunicipio" value="<%=""+ConstantesSistema.DADOS_CENSITARIOS_MUNICIPIO%>" 
						onclick="javascript:exibirCamposFormulario();" tabindex="2"/>Munic&iacute;pio</strong>
					</td>
      			</tr>
      			
      			<logic:present name="colecaoLocalidade">
      			<tr>
		      		<td><strong>Localidade:<font color="#FF0000">*</font></strong></td>
	                <td width="25%">
	                    <html:select property="codigoLocalidade" tabindex="3">
								<html:option value="">&nbsp;</html:option>
								<html:options collection="colecaoLocalidade" labelProperty="descricaoComId" property="id" />
						</html:select>
	                </td>
	      		</tr>
	      		</logic:present>

      			<logic:notPresent name="colecaoLocalidade">
					<html:hidden property="codigoLocalidade"/>
	      		</logic:notPresent>

				<logic:present name="colecaoMunicipio">
	      		<tr>
		      		<td><strong>Munic&iacute;pio:<font color="#FF0000">*</font></strong></td>
	                <td width="25%">
	                    <html:select property="codigoMunicipio" tabindex="3">
								<html:option value="">&nbsp;</html:option>
								<html:options collection="colecaoMunicipio" labelProperty="nomeComId" property="id" />
						</html:select>
	                </td>
	      		</tr>
	      		</logic:present>

      			<logic:notPresent name="colecaoMunicipio">
					<html:hidden property="codigoMunicipio"/>
	      		</logic:notPresent>
	      		
	      		<tr>
                  <td><strong>Mês/Ano:<font color="#FF0000">*</font></strong></td>
                  <td><INPUT TYPE="hidden" ID="DATA_ATUAL"	value="${requestScope.dataAtual}" />
                  	  <html:text maxlength="7" property="anoMesReferencia" size="7" 
                  		onblur="verificaAnoMesNovo(this);" 
                  		onkeyup="mascaraAnoMes(this, event);"
                  		tabindex="4"/>
                    &nbsp; mm/aaaa</td>
                </tr>
    		
    			<tr>
		      		<td><strong>Fonte de Informação:<font color="#FF0000">*</font></strong></td>
	                <td width="25%">
	                    <html:select property="idFonteDadosCensitarios" tabindex="5">
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
							tabindex="6"
							onblur="javascript:validarCampoInteiro(this.value, 'populacaoUrbana', 'Pópulação Urbana');"/></div>
					</td>
		  	  	</tr>
		  	  	
		  	  	<tr>
					<td><strong>Taxa Anual de Crescimento da População Urbana:</strong></td>

					<td width="25%"> <strong> <html:text property="taxaCrescimentoAnualPopulacaoUrbana" 
						style="text-align:right;"
						onkeyup="formataValorMonetario(this, 5);" 
						size="6"
						tabindex="7" 
						maxlength="6" /></strong>
					</td>
				</tr>
				
				<tr>
					<td><strong>Taxa de ocupação da população urbana:</strong></td>

					<td width="25%"> <strong> <html:text property="taxaOcupacionalPopulacaoUrbana" 
						style="text-align:right;"
						onkeyup="formataValorMonetario(this, 5);" 
						size="6"
						tabindex="8" 
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
							tabindex="9"
							onblur="javascript:validarCampoInteiro(this.value, 'populacaoRural', 'Pópulação Rural');"/></div>
					</td>
		  	  	</tr>
		  	  	
		  	  	<tr>
					<td><strong>Taxa Anual de Crescimento da População Rural:</strong></td>

					<td width="25%"> <strong> <html:text property="taxaCrescimentoAnualPopulacaoRural" 
						style="text-align:right;"
						onkeyup="formataValorMonetario(this, 5);" 
						size="6"
						tabindex="10" 
						maxlength="6" /></strong>
					</td>
				</tr>
				
				<tr>
					<td><strong>Taxa de Ocupação da População Rural:</strong></td>

					<td width="25%"> <strong> <html:text property="taxaOcupacionalPopulacaoRural" 
						style="text-align:right;"
						onkeyup="formataValorMonetario(this, 5);" 
						size="6" 
						tabindex="11"
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
					<td colspan="2">
						<input name="Button" 
						type="button"
						class="bottonRightCol" 
						value="Limpar" 
						align="left"
						tabindex="12"
						onclick="javascript:limparForm();">&nbsp; 
					&nbsp;
						<input type="button" 
								name="Button"
								class="bottonRightCol" 
								value="Cancelar" 
								tabindex="13"
								onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
								style="width: 80px" />
					</td>
					
					<td align="right">
						<gsan:controleAcessoBotao name="Button" 
							value="Inserir"
							tabindex="14"
							onclick="javascript:validarForm(document.forms[0]);"
							url="inserirDadosCensitariosAction.do" />
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
