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
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="FiltrarDadosCensitariosActionForm"/>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>

<script language="Javascript">
	
	function validarForm(formulario) {
	
	 	if (validarCampos()){
			submeterFormPadrao(formulario);
		}
	}

	function exibirCamposFormulario() {
		var form = document.forms[0];
		form.action = '/gsan/exibirFiltrarDadosCensitariosAction.do';
		form.submit();
	}

	function validarCampos(){
		var retorno = true;
		var form = document.forms[0];
	
		if (document.all.iLocalidadeMunicipio[0].checked 
				&& (isNaN(form.codigoLocalidade.value) || form.codigoLocalidade.value == "")){
			alert('Informe Localidade.');
			return false;
			
		}else if (document.all.iLocalidadeMunicipio[1].checked 
				&& (isNaN(form.codigoMunicipio.value) || form.codigoMunicipio.value == "")){
			alert('Informe Município.');
			return false;
			
		}
		
		return retorno;
	}
	
	function limparForm() {
	  	
		var form = document.forms[0];
		if (document.all.iLocalidadeMunicipio[0].checked){
			form.codigoLocalidade.value = "";
		}else{
			form.codigoMunicipio.value = "";
		}
		
		form.anoMesReferencia.value = "";
	}

	function limpaNomeMunicipio(){

		var form = document.forms[0];

		form.nomeMunicipio.value = "";
	}

	 function limparPesquisaMunicipio() {

		var form = document.forms[0];
		
	    form.codigoMunicipio.value = "";
	    form.nomeMunicipio.value = "";
	    form.action = '/gsan/exibirFiltrarDadosCensitariosAction.do';
      	form.submit();

	 }

	 function limparPesquisaLocalidade() {

		var form = document.forms[0];
		
	    form.codigoLocalidade.value = "";
	    form.nomeLocalidade.value = "";
	    form.action = '/gsan/exibirFiltrarDadosCensitariosAction.do';
      	form.submit();
	}

	function setarAtualizar(){
		var formulario = document.forms[0];
		
		if (formulario.indicadorAtualizar.value == "1"){
			formulario.indicadorAtualizar.value = "2"; 
		}else{
			formulario.indicadorAtualizar.value = "1";
		}
	}

	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

	    var form = document.forms[0];
	
	    if (tipoConsulta == 'municipio') {

	      	form.codigoMunicipio.value = codigoRegistro;
	      	form.nomeMunicipio.value = descricaoRegistro;
	      	form.nomeMunicipio.style.color = "#000000";
	      	form.action = '/gsan/exibirFiltrarDadosCensitariosAction.do?pesquisarMunicipio=true';
	      	form.submit();
	    }

	    if (tipoConsulta == 'localidade') {

        	form.codigoLocalidade.value = codigoRegistro;
	        form.nomeLocalidade.value = descricaoRegistro;
	  	  	form.nomeLocalidade.style.color = "#000000";
	  	  	form.action = '/gsan/exibirFiltrarDadosCensitariosAction.do?pesquisarLocalidade=true';
	  	  	form.submit();
	  	}
	 }
		
	
</script>

</head>


<body leftmargin="5" topmargin="5"
	onload="document.forms[0].indicadorAtualizar.checked=true">
<html:form action="/filtrarDadosCensitariosAction" method="post">

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
					<td class="parabg">Filtrar Dados Censitários</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>
			<table bordercolor="#000000" width="100%" cellspacing="0" >
				<tr>
					<td>
					Para manter os Dados Censitários, informe os dados abaixo:
					</td>
					<td width="84">
					<input name="atualizar" type="checkbox"
						checked="checked" onclick="javascript:setarAtualizar();" value="1"> <strong>Atualizar</strong>
					</td>
    			</tr>
   			</table>
   			
   			<table width="100%" border="0">
   			
   				<tr>
	        		<td><strong>Localidade ou Munic&iacute;pio do IBGE:</strong></td>
	        		<td>
						<html:radio styleId="iLocalidadeMunicipio" property="indicadorLocalidadeMunicipio" value="<%=""+ConstantesSistema.DADOS_CENSITARIOS_LOCALIDADE%>" 
							onclick="javascript:exibirCamposFormulario();" tabindex="1"/><strong>Localidade
						<html:radio styleId="iLocalidadeMunicipio" property="indicadorLocalidadeMunicipio" value="<%=""+ConstantesSistema.DADOS_CENSITARIOS_MUNICIPIO%>" 
						onclick="javascript:exibirCamposFormulario();" tabindex="2"/>Munic&iacute;pio</strong>
					</td>
      			</tr>
   			
   				<logic:equal name="FiltrarDadosCensitariosActionForm" property="indicadorLocalidadeMunicipio" value="<%=""+ConstantesSistema.DADOS_CENSITARIOS_LOCALIDADE%>">
   				<tr>
			        <td><strong>Localidade:</strong></td>
			        <td width="70%">
						<html:text maxlength="3" 
							tabindex="1"
							property="codigoLocalidade" 
							size="3"
							onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarDadosCensitariosAction.do?objetoConsulta=1','codigoLocalidade','Localidade');"/>
							
						<a href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do?indicadorUsoTodos=1', 250, 495);">
							<img width="23" 
								height="21" 
								border="0" 
								style="cursor:hand;"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" /></a>
								

						<logic:present name="localidadeEncontrada" scope="request">
							<html:text property="nomeLocalidade" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="localidadeEncontrada" scope="request">
							<html:text property="nomeLocalidade" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>

						
						<a	href="javascript:limparPesquisaLocalidade();">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" title="Apagar" /> </a>
					</td>
			    </tr>
			    </logic:equal>
   			
   				<logic:equal name="FiltrarDadosCensitariosActionForm" property="indicadorLocalidadeMunicipio" value="<%=""+ConstantesSistema.DADOS_CENSITARIOS_MUNICIPIO%>">
   				<tr>
					<td><strong>Munic&iacute;pio:</strong></td>
					<td width="70%">
						<html:text maxlength="4" 
						property="codigoMunicipio" 
						size="4"
						tabindex="1" 
						onkeyup="javascript:limpaNomeMunicipio();"
						onkeypress="javascript:return validaEnter(event, 'exibirFiltrarDadosCensitariosAction.do?objetoConsulta=2', 'codigoMunicipio');" />
						
						<a href="javascript:abrirPopup('exibirPesquisarMunicipioAction.do?indicadorUsoTodos=1');">
						<img width="23" height="21" border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar Municipio" /></a> 
						
						<logic:present name="municipioEncontrado" scope="request">
							<html:text maxlength="40" 
								property="nomeMunicipio" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="40" />
						</logic:present> 
						
						<logic:notPresent name="municipioEncontrado" scope="request">
							<html:text 
								maxlength="40" 
								property="nomeMunicipio" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000"
								size="40" />
						</logic:notPresent> <a href="javascript:limparPesquisaMunicipio();">
						
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" title="Apagar" /></a>
					</td>
				</tr>
				</logic:equal>
				
				<logic:present name="colecaoReferencia">
      			<logic:present name="colecaoLocalidadeDadosCensitariosReferencia">
      			<tr>
		      		<td><strong>Mês/Ano Referência:</strong></td>
	                <td width="25%">
	                    <html:select property="anoMesReferencia" tabindex="3">
								<html:option value="">&nbsp;</html:option>
								<html:options collection="colecaoLocalidadeDadosCensitariosReferencia" labelProperty="formatarAnoMesParaMesAno" property="anoMesReferencia"/>
						</html:select>
	                </td>
	      		</tr>
	      		</logic:present>
	      		<logic:present name="colecaoMunicipioDadosCensitariosReferencia">
	      		<tr>
		      		<td><strong>Mês/Ano Referência:</strong></td>
	                <td width="25%">
	                    <html:select property="anoMesReferencia" tabindex="3">
								<html:option value="">&nbsp;</html:option>
								<html:options collection="colecaoMunicipioDadosCensitariosReferencia" labelProperty="formatarAnoMesParaMesAno" property="anoMesReferencia"/>
						</html:select>
	                </td>
	      		</tr>
	      		</logic:present>
	      		</logic:present>
	      		<logic:notPresent name="colecaoReferencia">
	      		<tr>
		      		<td><strong>Mês/Ano Referência:</strong></td>
	                <td width="25%">
	                    <html:select property="anoMesReferencia" tabindex="3">
								<html:option value="">&nbsp;</html:option>
						</html:select>
	                </td>
	      		</tr>	
	      		</logic:notPresent>
				
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
							onclick="window.location.href='/gsan/exibirFiltrarDadosCensitariosAction.do?menu=sim';"> 			
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
					<td align="right">
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
