<%@page import="gcom.gui.cadastro.localidade.AtualizarLocalidadeActionForm"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.util.ConstantesSistema" %>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"  formName="AtualizarLocalidadeActionForm"/>

<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">
<SCRIPT LANGUAGE="JavaScript">
function validarForm(formulario){

	
	if (validateAtualizarLocalidadeActionForm(formulario)){
	
		var objLocalidadeID = returnObject(formulario, "localidadeID");
		var objMunicipioID = returnObject(formulario, "municipioID");
		var objTelefone = returnObject(formulario, "telefone");
		var objRamal = returnObject(formulario, "ramal");
		var objFax = returnObject(formulario, "fax");
		var objMenorConsumo = returnObject(formulario, "menorConsumo");
		var objUnidadeNegocioID = returnObject(formulario, "idUnidadeNegocio");		
		var objEloID = returnObject(formulario, "eloID");
		var objClasseID = returnObject(formulario, "classeID");
		var objPorteID = returnObject(formulario, "porteID");
		var objIcms = returnObject(formulario, "icms");
		var objCentroCusto = returnObject(formulario, "centroCusto");
	
		if (!testarCampoValorZero(objLocalidadeID, "C�digo")){
			objLocalidadeID.focus();
		}
			else if (objMunicipioID.value == -1) {
				alert('Informe Munic�pio.');
				objMunicipioID.focus();
			}
			else if (objTelefone.value.length > 0 && !testarCampoValorZero(objTelefone, "Telefone")) {
				objTelefone.focus();
			}
			else if (objTelefone.value.length > 0 && objTelefone.value.length < 7) {
				alert("Telefone deve conter no m�nimo 7 d�gitos.");
				objTelefone.focus();
			}
			else if (objRamal.value.length > 0 && !testarCampoValorZero(objRamal, "Ramal")) {
				objRamal.focus();
			}
			else if (objRamal.value.length > 0 && objTelefone.value.length < 1) {
				alert("Informe Telefone.");
				objTelefone.focus();
			}
			else if (objFax.value.length > 0 && !testarCampoValorZero(objFax, "Fax")){
				objFax.focus();
			}
			else if (objFax.value.length > 0 && objFax.value.length < 7){
				alert("Fax deve conter no m�nimo 7 d�gitos.");
				objFax.focus();
			}
			else if (objMenorConsumo.value.length > 0 && !testarCampoValorZero(objMenorConsumo, "Menor consumo")){
				objMenorConsumo.focus();
			}
			else if (objUnidadeNegocioID.value == -1) {
				alert('Informe Unidade Neg�cio.');
				objUnidadeNegocioID.focus();
			}
			else if (objEloID.value == -1) {
				alert('Informe Elo.');
				objEloID.focus();
			}
			else if (objClasseID.value == -1) {
				alert('Informe Classe.');
				objClasseID.focus();
			}
			else if (objPorteID.value == -1) {
				alert('Informe Porte.');
				objPorteID.focus();
			}
			else if (formulario.informatizada[0].checked == false && formulario.informatizada[1].checked == false) {
				alert('Informe Informatizada.');
				formulario.informatizada[0].focus();
			}		
			else if (objIcms.value.length > 0 && !testarCampoValorZero(objIcms, "ICMS")) {
				objIcms.focus();
	        }
			else if (objCentroCusto.value.length == 0) {
				alert('Informe Centro de Custo.');
				objCentroCusto.focus();
			} else if(validarConcessionaria(formulario)){
				// faz nada!
			}
			else {
				formulario.action = "/gsan/atualizarLocalidadeAction.do";
				submeterFormPadrao(formulario);	
			}
		}
	}
	</SCRIPT>
</logic:equal>

<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
	<SCRIPT LANGUAGE="JavaScript">
	function validarForm(formulario) {
		
		if (validateAtualizarLocalidadeActionForm(formulario)) {
			var objLocalidadeID = returnObject(formulario, "localidadeID");
			var objGerenciaRegionalID = returnObject(formulario, "gerenciaRegionalID");
			var objMunicipioID = returnObject(formulario, "municipioID");
			var objTelefone = returnObject(formulario, "telefone");
			var objRamal = returnObject(formulario, "ramal");
			var objFax = returnObject(formulario, "fax");
			var objMenorConsumo = returnObject(formulario, "menorConsumo");
			var objClasseID = returnObject(formulario, "classeID");
			var objPorteID = returnObject(formulario, "porteID");
			var objLocalidadeContabil = returnObject(formulario, "localidadeContabil");

		if (!testarCampoValorZero(objLocalidadeID, "C�digo")) {
			objLocalidadeID.focus();
		} else if (objGerenciaRegionalID.value == -1) {
			alert('Informe Ger�ncia Regional.');
			objGerenciaRegionalID.focus();
		}  else if (objMunicipioID.value == -1) {
			alert('Informe Munic�pio.');
			objMunicipioID.focus();
		} else if (objTelefone.value.length > 0 && !testarCampoValorZero(objTelefone, "Telefone")){
			objTelefone.focus();
		} else if (objTelefone.value.length > 0 && objTelefone.value.length < 7){
			alert("Telefone deve conter no m�nimo 7 d�gitos.");
			objTelefone.focus();
		} else if( objRamal.value.length > 0 && !testarCampoValorZero(objRamal, "Ramal")) {
			objRamal.focus();
		} else if (objRamal.value.length > 0 && objTelefone.value.length < 1) {
			alert("Informe Telefone.");
			objTelefone.focus();
		} else if (objFax.value.length > 0 && !testarCampoValorZero(objFax, "Fax")){
			objFax.focus();
		} else if (objFax.value.length > 0 && objFax.value.length < 7){
			alert("Fax deve conter no m�nimo 7 d�gitos.");
			objFax.focus();
		} else if (objMenorConsumo.value.length > 0 && !testarCampoValorZero(objMenorConsumo, "Menor consumo")){
			objMenorConsumo.focus();
		} else if(objClasseID.value == -1){
			alert('Informe Classe.');
			objClasseID.focus();
		} else if(objPorteID.value == -1){
			alert('Informe Porte.');
			objPorteID.focus();
		} else if (formulario.informatizada[0].checked == false && formulario.informatizada[1].checked == false) {
			alert('Informe Informatizada.');
			formulario.informatizada[0].focus();
		} else if(validarConcessionaria(formulario)){
			// faz nada!
		}			
		/*else if (objLocalidadeContabil.value.length == 0) {
				alert('Informe Localidade Cont�bil.');
				objLocalidadeContabil.focus();
			}*/
		else {
			formulario.action = "/gsan/atualizarLocalidadeAction.do";
			submeterFormPadrao(formulario);
		}
	}
}
	</SCRIPT>
</logic:equal>

<logic:present name="colecaoConcessionaria">
<SCRIPT LANGUAGE="JavaScript">
	function validarConcessionaria(formulario){
		var objConcessionariaID = returnObject(formulario, "concessionariaId");
		var objDataVigenciaInicioConcessionaria = returnObject(formulario, "dataVigenciaInicioConcessionaria");

		if (objConcessionariaID.value == -1) {
			alert('Informe Concession�ria.');
			objConcessionariaID.focus();
			return true;
		} else if (objDataVigenciaInicioConcessionaria.value.length < 10) {
			alert('Informe Data de In�cio da Vig�ncia.');
			objDataVigenciaInicioConcessionaria.focus();
			return true;
		}else {
			return false;
		}
	}
</SCRIPT>
</logic:present>
<logic:notPresent name="colecaoConcessionaria">
<SCRIPT LANGUAGE="JavaScript">
	function validarConcessionaria(formulario){
		// Faz nada!
		return false;
	}
</SCRIPT>
</logic:notPresent>


<SCRIPT LANGUAGE="JavaScript">
<!--
function remover(){
	var form = document.forms[0];
	form.action = "/gsan/exibirAtualizarLocalidadeAction.do?removerEndereco=1";
	form.submit();
}



function carregarElos(){
	form = document.forms[0];
	document.getElementById("limparCampos").value = "1";
	form.action = "/gsan/exibirAtualizarLocalidadeAction.do?objetoConsulta=1";
	form.submit();
}

	function limparGerente() {

    	var form = document.forms[0];

    	form.gerenteLocalidade.value = "";
    	form.nomeGerente.value = "";
  	}
	
	/* Recuperar Popup */
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	    var form = document.forms[0];
	    
	    if (tipoConsulta == 'funcionario') {
	    //if (tipoConsulta == 'cliente') {

		    form.gerenteLocalidade.value = codigoRegistro;
		    form.nomeGerente.value = descricaoRegistro;
	      	form.nomeGerente.style.color = "#000000";
	    
	    }
	}
	    
	function abrirCalendarioDtLoca(){
		var form = document.forms[0];
		
		if(form.dataVigenciaInicioConcessionaria.disabled == false){
			abrirCalendario('AtualizarLocalidadeActionForm', 'dataVigenciaInicioConcessionaria')
		} else {
			// FAZ NADA
		}
	}
	
	function habilitarDesabilitarDataInicioVigencia(){
		var form = document.forms[0];
		
		if(form.concessionariaId.value == form.concessionariaIdOriginal.value){
			form.dataVigenciaInicioConcessionaria.value = form.dataVigenciaInicioConcessionariaOriginal.value; 
			form.dataVigenciaInicioConcessionaria.disabled = true;
		} else{
			form.dataVigenciaInicioConcessionaria.disabled = false;
		}
			
	}

//-->
</SCRIPT>

</head>

<body leftmargin="5" topmargin="5" onload="document.forms[0].removerEndereco.value=''; setarFoco('${requestScope.nomeCampo}');">

<html:form action="/exibirAtualizarLocalidadeAction" method="post">

<INPUT TYPE="hidden" name="removerEndereco">
<INPUT TYPE="hidden" name="limparCampos" id="limparCampos">

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

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

      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
          <td class="parabg">Atualizar Localidade</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>

      <table width="100%" border="0">
      <tr>
      	<td colspan="2">Para alterar a localidade, informe os dados abaixo:</td>
      	<td align="right"></td>
      </tr>
      </table>
      <table width="100%" border="0">
	  <tr> 
          <td><strong>C�digo:</strong></td>
          <td colspan="2">
				<html:hidden property="localidadeID"/>
				<bean:write name="AtualizarLocalidadeActionForm" property="localidadeID"/>
		  </td>
      </tr>
	  <tr> 
          <td><strong>Nome:<font color="#FF0000">*</font></strong></td>
          <td colspan="2"><html:text property="localidadeNome" size="40" maxlength="30" tabindex="2"/></td>
      </tr>

	  <logic:present name="colecaoConcessionaria">
		  <html:hidden property="concessionariaIdOriginal"/>		
		  <html:hidden property="dataVigenciaInicioConcessionariaOriginal"/>		
		  <tr>
	          <td><strong>Concession�ria:<font color="#FF0000">*</font></strong></td>
	          <td>
	              <html:select property="concessionariaId" style="width: 200px;" tabindex="3" onchange="javascript:habilitarDesabilitarDataInicioVigencia()">
	                  <html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
                      <html:options collection="colecaoConcessionaria" labelProperty="nome" property="id"/>
	              </html:select>
	          </td>
		  </tr>
	  </logic:present>

	  <logic:present name="colecaoConcessionaria">
		  <tr> 
	          <td>
	          	<strong>Data de In�cio da Vig�ncia:<font color="#FF0000">*</font></strong>
	          </td>
	          <td><html:text property="dataVigenciaInicioConcessionaria" size="10" maxlength="10" tabindex="5" onkeyup="mascaraData(this,event)" disabled="true"/>
          		<a id="calendarioDt" href="javascript:abrirCalendarioDtLoca()">
          			<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calend�rio"/>
          		</a>dd/mm/aaaa
	          </td>
		  </tr>
	  </logic:present>

      <logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
	  <tr>
          <td><strong>Ger�ncia Regional:<font color="#FF0000">*</font></strong></td>
          <td>
              <html:select property="gerenciaRegionalID" style="width: 200px;" tabindex="3">
                  <html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
                  <logic:present name="colecaoGerenciaRegional">
                      <html:options collection="colecaoGerenciaRegional" labelProperty="nome" property="id"/>
                  </logic:present>
              </html:select>
          </td>
	  </tr>
      </logic:equal>

	   <tr>
		   <td><strong>Unidade Neg�cio:</strong>
	
	       <logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">
	           <font color="#FF0000">*</font>
		   </logic:equal>
	
	       </td>
		   <td>
				<html:select property="idUnidadeNegocio" tabindex="11" onchange="carregarElos();">
				<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
					<html:options collection="colecaoUnidadeNegocio" labelProperty="nome" property="id"/>
				</html:select>
		   </td>
	   </tr>

      <tr>
          <td><strong>Munic�pio:<font color="#FF0000">*</font></strong></td>
          <td>
              <html:select property="municipioID" style="width: 200px;" tabindex="4">
                  <html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
                  <logic:present name="colecaoMunicipio">
                      <html:options collection="colecaoMunicipio" labelProperty="nomeComId" property="id"/>
                  </logic:present>
              </html:select>
          </td>
	  </tr>

      <tr>
         <td><strong>Endere&ccedil;o Localidade:</strong></td>
         <td align="right">

		 <logic:present name="colecaoEnderecos">
			<logic:empty name="colecaoEnderecos">
				<input type="button" class="bottonRightCol" value="Adicionar" tabindex="5" id="botaoEndereco" onclick="javascript:abrirPopup('exibirInserirEnderecoAction.do?tipoPesquisaEndereco=localidade&operacao=2', 560, 450);">
			</logic:empty>
			<logic:notEmpty name="colecaoEnderecos">
				<input type="button" class="bottonRightCol" value="Adicionar" tabindex="5" id="botaoEndereco" onclick="javascript:abrirPopup('exibirInserirEnderecoAction.do?tipoPesquisaEndereco=localidade&operacao=2', 560, 450);" disabled>
			</logic:notEmpty>
		 </logic:present>

		 <logic:notPresent name="colecaoEnderecos">
			<input type="button" class="bottonRightCol" value="Adicionar" tabindex="5"  id="botaoEndereco" onclick="javascript:abrirPopup('exibirInserirEnderecoAction.do?tipoPesquisaEndereco=localidade&operacao=2', 560, 450);">
		 </logic:notPresent>
		
		</td>
     </tr>
     <tr>
         <td colspan="2" height="70" valign="top">
			<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<table width="100%" border="0" bgcolor="#90c7fc">
					<tr bgcolor="#90c7fc" height="18">
						<td width="10%" align="center"><strong>Remover</strong></td>
						<td align="center"><strong>Endere&ccedil;o</strong></td>
					</tr>
					</table>
				</td>
			</tr>

			<logic:present name="colecaoEnderecos">

			<tr>
				<td>
					<table width="100%" align="center" bgcolor="#99CCFF">
						<!--corpo da segunda tabela-->

						<% String cor = "#cbe5fe";%>

						<logic:iterate name="colecaoEnderecos" id="endereco">
						
							<%	if (cor.equalsIgnoreCase("#cbe5fe")){	
								cor = "#FFFFFF";%>
								<tr bgcolor="#FFFFFF" height="18">	
							<%} else{	
								cor = "#cbe5fe";%>
								<tr bgcolor="#cbe5fe" height="18">		
							<%}%>
																            
								<td width="10%" align="center"><img src="<bean:message key='caminho.imagens'/>Error.gif" width="14" height="14" style="cursor:hand;" alt="Remover" 
								onclick="javascript:if(confirm('Confirma remo��o?')){remover();}"></td>
								<td>
									<a href="javascript:abrirPopup('exibirInserirEnderecoAction.do?exibirEndereco=OK&tipoPesquisaEndereco=localidade&operacao=2', 570, 700)"><bean:write name="endereco" property="enderecoFormatado"/></a>
								</td>
							</tr>
						</logic:iterate>

					</table>
			  	</td>
			</tr>

			</logic:present>

			</table>
	   </td>
   </tr>
   <tr>
       <td><strong>Telefone:</strong></td>
       <td><html:text property="telefone" size="10" maxlength="9" tabindex="6"/></td>
   </tr>
   <tr> 
      <td><strong>Ramal:</strong></td>
	  <td><html:text property="ramal" size="5" maxlength="4" tabindex="7"/></td>
   </tr>
   <tr>
	  <td><strong>Fax:</strong></td>
	  <td><html:text property="fax" size="10" maxlength="9" tabindex="8"/></td>
   </tr>    
   <tr>
	  <td><strong>E-mail:</strong></td>
	  <td><html:text property="email" size="50" maxlength="40" tabindex="9"/></td>
   </tr>
   <tr>
	  <td><strong>Menor Consumo:</strong></td>
	  <td><html:text property="menorConsumo" size="10" maxlength="6" tabindex="10"/></td>
   </tr>

   <tr>
	  <td><strong>Elo:</strong></td>
	  <td>
			<html:select property="eloID" style="width: 200px;" tabindex="12">
				<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<logic:present name="colecaoElo">
					<html:options collection="colecaoElo" labelProperty="descricao" property="id"/>
				</logic:present>
			</html:select>
			
	  </td>
   </tr>

   <tr>
	   <td><strong>Classe:<font color="#FF0000">*</font></strong></td>
	   <td>
			<html:select property="classeID" style="width: 200px;" tabindex="13">
			<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<html:options collection="colecaoClasse" labelProperty="descricao" property="id"/>
			</html:select>
	   </td>
   </tr>
   <tr>
	   <td><strong>Porte:<font color="#FF0000">*</font></strong></td>
	   <td>
			<html:select property="porteID" style="width: 200px;" tabindex="14">
			<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<html:options collection="colecaoPorte" labelProperty="descricao" property="id"/>
			</html:select>
	   </td>
   </tr>

   <logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">
   <tr>
      <td><strong>ICMS:</strong></td>
	  <td><html:text property="icms" size="10" maxlength="10" onkeypress="return isCampoNumerico(event);" tabindex="15"/></td>
   </tr>
   </logic:equal>
   
   <tr>
      <td><strong>Centro de Custo:</strong>

	   <logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">
	       <font color="#FF0000">*</font>
	   </logic:equal>
   
      </td>
	  <td><html:text property="centroCusto" size="10" maxlength="10" tabindex="16"/></td>
   </tr>

	<tr>
		<td><strong>Informatizada?</strong><font color="#FF0000">*</font></td>
		<td>
			<html:radio property="informatizada"
				value="<%=""+ConstantesSistema.SIM%>" /> <strong>Sim</strong>
			<html:radio property="informatizada"
				value="<%=""+ConstantesSistema.NAO%>" /> <strong>N&atilde;o</strong>
		</td>
	</tr>
	
	<tr>
		<td><strong>Gerente da Localidade:</strong></td>
		
		<td>
			
			<html:text maxlength="9" 
				tabindex="17"
				property="gerenteLocalidade" 
				size="9"
				onkeypress="validaEnterComMensagem(event, 'exibirAtualizarLocalidadeAction.do?objetoConsulta=2&limparCampos=1','gerenteLocalidade','Gerente da Localidade');"/>
				
				<!-- <a href="javascript:abrirPopup('exibirPesquisarFuncionarioAction.do?limpaForm=sim', 570, 700)"> -->
				<a href="javascript:abrirPopup('exibirPesquisarFuncionarioAction.do?limpaForm=sim', 570, 700)">
				
					<img width="23" 
						height="21" 
						border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Gerente" /></a> 

				<logic:present name="gerenteLocalidadeEncontrado" scope="request">
					<html:text property="nomeGerente" 
						size="35"
						maxlength="35" 
						readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" />
				</logic:present> 

				<logic:notPresent name="gerenteLocalidadeEncontrado" scope="request">
					<html:text property="nomeGerente" 
						size="35"
						maxlength="35" 
						readonly="true"
						style="background-color:#EFEFEF; border:0; color: red" />
				</logic:notPresent>

				
				<a href="javascript:limparGerente();"> 
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" 
						title="Apagar" />
				</a>					
			</td>
	</tr>

	<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
		<tr>
		    <td><strong>Localidade para C.E.F.:</strong></td>
		    <td><html:text property="localidadeCEF" size="3" maxlength="3" onkeypress="return isCampoNumerico(event);" tabindex="19"/></td>
		</tr>
		<tr>
			<td><strong>Abastecimento Suspenso:</strong></td>
			<td><html:checkbox property="indicadorAbastecimentoSuspenso" value="true" tabindex="20"></html:checkbox></td>
		</tr>
		<input type="hidden" name="indicadorAbastecimentoSuspenso" value="false">

		<tr>
			<td><strong>Abastecimento M�nimo:</strong></td>
			<td><html:checkbox property="indicadorAbastecimentoMinimo" value="true" tabindex="21"></html:checkbox></td>
		</tr>
		<input type="hidden" name="indicadorAbastecimentoMinimo" value="false">

		<tr>
		    <td><strong>�ltima Quadra:</strong></td>
		    <td><html:text property="ultimaQuadra" size="5" maxlength="5" onkeypress="return isCampoNumerico(event);" tabindex="22"/></td>
		</tr>
		<tr>
		    <td><strong>Localidade Cont�bil: </strong>
		    </td>
		    <td><html:text property="localidadeContabil" size="3" maxlength="3" onkeypress="return isCampoNumerico(event);" tabindex="23"/></td>
		</tr>
	</logic:equal>

   <tr>
        <td><strong>Indicador de uso:</strong></td>
        <td>
			<html:radio property="indicadorUso" value="1"/><strong>Ativo
			<html:radio property="indicadorUso" value="2"/>Inativo</strong>
		</td>
      </tr>
   
   <tr>



       <td></td>
       <td><font color="#FF0000">*</font>&nbsp;Campos obrigat&oacute;rios</td>
   </tr>
   <tr>
   		<td>
		<logic:present name="voltar">
			<logic:equal name="voltar" value="filtrar">
				<input name="Button" type="button" class="bottonRightCol"
				value="Voltar" align="left"
				onclick="window.location.href='<html:rewrite page="/exibirFiltrarLocalidadeAction.do?desfazer=N"/>'">
			</logic:equal>
			<logic:equal name="voltar" value="manter">
				<input name="Button" type="button" class="bottonRightCol"
				value="Voltar" align="left"
				onclick="window.location.href='<html:rewrite page="/exibirManterLocalidadeAction.do"/>'">
			</logic:equal>
		</logic:present>
		<logic:notPresent name="voltar">
			<input name="Button" type="button" class="bottonRightCol"
			value="Voltar" align="left"
			onclick="window.location.href='<html:rewrite page="/exibirManterLocalidadeAction.do"/>'">
		</logic:notPresent>
   		
   		<input name="Button" type="button" class="bottonRightCol"
						value="Desfazer" align="left"
						onclick="window.location.href='<html:rewrite page="/exibirAtualizarLocalidadeAction.do?desfazer=S"/>'">
		
		<input name="Button" type="button" class="bottonRightCol"
						value="Cancelar" align="left"
						onClick="window.location.href='/gsan/telaPrincipal.do'"></td>						
   
       <td align="right">
       <%-- <INPUT type="button" class="bottonRightCol" value="Atualizar" tabindex="13" style="width: 70px;" onclick="validarForm(document.forms[0]);"> --%>
        <gcom:controleAcessoBotao name="Botao" value="Atualizar" onclick="validarForm(document.forms[0]);" url="atualizarLocalidadeAction.do" tabindex="24"/>
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


