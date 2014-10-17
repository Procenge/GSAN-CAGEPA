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

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="InserirLocalidadeActionForm"/>

<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">
<SCRIPT LANGUAGE="JavaScript">
	function validarForm(formulario){

		if (validateInserirLocalidadeActionForm(formulario)){
		
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
		
			if (!testarCampoValorZero(objLocalidadeID, "Código")){
				objLocalidadeID.focus();
			}
			else if (objMunicipioID.value == -1) {
				alert('Informe Município.');
				objMunicipioID.focus();
				}
			else if (objTelefone.value.length > 0 && !testarCampoValorZero(objTelefone, "Telefone")) {
					objTelefone.focus();
				}
			else if (objTelefone.value.length > 0 && objTelefone.value.length < 7) {
				alert("Telefone deve conter no mínimo 7 dígitos.");
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
				alert("Fax deve conter no mínimo 7 dígitos.");
					objFax.focus();
				}
				else if (objMenorConsumo.value.length > 0 && !testarCampoValorZero(objMenorConsumo, "Menor consumo")){
					objMenorConsumo.focus();
				}
			else if (objUnidadeNegocioID.value == -1) {
				alert('Informe Unidade Negócio.');
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
			} else if (formulario.informatizada[0].checked == false && formulario.informatizada[1].checked == false) {
				alert('Informe Informatizada.');
				formulario.informatizada[0].focus();
			} else if (objIcms.value.length > 0 && !testarCampoValorZero(objIcms, "ICMS")) {
				objIcms.focus();
	        } else if (objCentroCusto.value.length == 0) {
				alert('Informe Centro de Custo.');
				objCentroCusto.focus();
			} else if(validarConcessionaria(formulario)){
				// faz nada!
			} else {
					formulario.action = "/gsan/inserirLocalidadeAction.do";
					submeterFormPadrao(formulario);	
				}
			}
	}
	</SCRIPT>
</logic:equal>

<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
	<SCRIPT LANGUAGE="JavaScript">
	function validarForm(formulario) {
		if (validateInserirLocalidadeActionForm(formulario)) {
			var objLocalidadeID = returnObject(formulario, "localidadeID");
			var objGerenciaRegionalID = returnObject(formulario, "gerenciaRegionalID");
			var objMunicipioID = returnObject(formulario, "municipioID");
			var objTelefone = returnObject(formulario, "telefone");
			var objRamal = returnObject(formulario, "ramal");
			var objFax = returnObject(formulario, "fax");
			var objMenorConsumo = returnObject(formulario, "menorConsumo");
			var objClasseID = returnObject(formulario, "classeID");
			var objPorteID = returnObject(formulario, "porteID");
			//var objLocalidadeContabil = returnObject(formulario, "localidadeContabil");

			if (!testarCampoValorZero(objLocalidadeID, "Código")) {
				objLocalidadeID.focus();
			}
			else if (objGerenciaRegionalID.value == -1) {
				alert('Informe Gerência Regional.');
				objGerenciaRegionalID.focus();
			}
			else if (objMunicipioID.value == -1) {
				alert('Informe Município.');
				objMunicipioID.focus();
			}
			else if (objTelefone.value.length > 0 && !testarCampoValorZero(objTelefone, "Telefone")){
				objTelefone.focus();
			}
			else if (objTelefone.value.length > 0 && objTelefone.value.length < 7){
				alert("Telefone deve conter no mínimo 7 dígitos.");
				objTelefone.focus();
			}
			else if( objRamal.value.length > 0 && !testarCampoValorZero(objRamal, "Ramal")) {
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
				alert("Fax deve conter no mínimo 7 dígitos.");
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
			else {
				formulario.action = "/gsan/inserirLocalidadeAction.do";
				submeterFormPadrao(formulario);
			}
		}
		
	}
	</SCRIPT>
</logic:equal>

<SCRIPT LANGUAGE="JavaScript">
<!--
	function remover(){
		var form = document.forms[0];
	
		document.getElementById('limparCampos').value='1';
		form.removerEndereco.value = "1";
		form.submit();
	}


	function carregarElos(){
		form = document.forms[0];
		document.getElementById("limparCampos").value = "1";
		form.action = "/gsan/exibirInserirLocalidadeAction.do?objetoConsulta=1";
		form.submit();
	}

	function redirecionarSubmitAtualizar(idLocalidade) {
		urlRedirect = "/gsan/exibirAtualizarLocalidadeAction.do?idRegistroInseridoAtualizar=" + idLocalidade;
		redirecionarSubmit(urlRedirect);
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
  	

//-->
</SCRIPT>



<logic:present name="colecaoConcessionaria">
<SCRIPT LANGUAGE="JavaScript">
	function validarConcessionaria(formulario){
		var objConcessionariaID = returnObject(formulario, "concessionariaId");

		if (objConcessionariaID.value == -1) {
			alert('Informe Concessionária.');
			objConcessionariaID.focus();
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


</head>

<body leftmargin="5" topmargin="5" onload="document.forms[0].removerEndereco.value=''; setarFoco('${requestScope.nomeCampo}');">

<html:form action="/exibirInserirLocalidadeAction" method="post">

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
          <td class="parabg">Inserir Localidade</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>

      <table width="100%" border="0">
      <tr>
      	<td>Para adicionar a(s) localidade(s), informe os dados abaixo:</td>
        <td align="right"></td>																	        	
      </tr>
      </table>
      <table width="100%" border="0">
	  <tr> 
          <td><strong>Código:<font color="#FF0000">*</font></strong></td>
          <td colspan="2">
	          <logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
		          <html:text property="localidadeID" size="5" tabindex="1"
								maxlength="3" onkeypress="return isCampoNumerico(event);" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000;" />
				</logic:equal>
				<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">
		          <html:text property="localidadeID" size="5" tabindex="1"
								maxlength="3" onkeypress="return isCampoNumerico(event);" />&nbsp;<a
						href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do?consulta=sim');"><img
						width="23" height="21" border="0"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" /></a>
				</logic:equal>	
			</td>			
      </tr>
	  <tr> 
          <td><strong>Nome:<font color="#FF0000">*</font></strong></td>
          <td colspan="2"><html:text property="localidadeNome" size="40" maxlength="30" tabindex="2"/></td>
      </tr>

	  <logic:present name="colecaoConcessionaria">
		  <tr>
	          <td><strong>Concessionária:<font color="#FF0000">*</font></strong></td>
	          <td>
	              <html:select property="concessionariaId" style="width: 200px;" tabindex="3">
	                  <html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
                      <html:options collection="colecaoConcessionaria" labelProperty="nome" property="id"/>
	              </html:select>
	          </td>
		  </tr>
	  </logic:present>

      <logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
	  <tr>
          <td><strong>Gerência Regional:<font color="#FF0000">*</font></strong></td>
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
          <td><strong>Município:<font color="#FF0000">*</font></strong></td>
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
				<input type="button" class="bottonRightCol" value="Adicionar" tabindex="5" id="botaoEndereco" onclick="javascript:abrirPopup('exibirInserirEnderecoAction.do?tipoPesquisaEndereco=localidade&operacao=1', 570, 700);">
			</logic:empty>
			<logic:notEmpty name="colecaoEnderecos">
				<input type="button" class="bottonRightCol" value="Adicionar" tabindex="5" id="botaoEndereco" onclick="javascript:abrirPopup('exibirInserirEnderecoAction.do?tipoPesquisaEndereco=localidade&operacao=1', 570, 700);" disabled>
			</logic:notEmpty>
		 </logic:present>

		 <logic:notPresent name="colecaoEnderecos">
			<input type="button" class="bottonRightCol" value="Adicionar" tabindex="5"  id="botaoEndereco" onclick="javascript:abrirPopup('exibirInserirEnderecoAction.do?tipoPesquisaEndereco=localidade&operacao=1', 570, 700);">
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
								onclick="javascript:if(confirm('Confirma remoção?')){remover();}"></td>
								
								
								
								<td>
									<a href="javascript:abrirPopup('exibirInserirEnderecoAction.do?exibirEndereco=OK&tipoPesquisaEndereco=localidade&operacao=1', 570, 700)"><bean:write name="endereco" property="enderecoFormatado"/></a>
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
	   <td><strong>Unidade Negócio:</strong>

       <font color="#FF0000">
       <logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">
       *
       </logic:equal>
       </font>

       </td>
	   <td>
			<html:select property="idUnidadeNegocio" tabindex="11" onchange="carregarElos();">
				<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<logic:present name="colecaoUnidadeNegocio">
					<html:options collection="colecaoUnidadeNegocio" labelProperty="nome" property="id"/>
				</logic:present>
			</html:select>
	   </td>
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
				<logic:present name="colecaoClasse">
					<html:options collection="colecaoClasse" labelProperty="descricao" property="id"/>
				</logic:present>
			</html:select>
	   </td>
   </tr>
   <tr>
	   <td><strong>Porte:<font color="#FF0000">*</font></strong></td>
	   <td>
			<html:select property="porteID" style="width: 200px;" tabindex="14">
				<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<logic:present name="colecaoPorte">
					<html:options collection="colecaoPorte" labelProperty="descricao" property="id"/>
				</logic:present>
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
			<html:radio property="informatizada" value="<%=""+ConstantesSistema.SIM%>" /> <strong>Sim</strong>
			<html:radio property="informatizada" value="<%=""+ConstantesSistema.NAO%>" /> <strong>N&atilde;o</strong>
		</td>
	</tr>
	
	<tr>
		<td><strong>Gerente da Localidade:</strong></td>
		
		<td>
			
			<html:text maxlength="9" 
				tabindex="16"
				property="gerenteLocalidade" 
				size="9"
				onkeypress="validaEnterComMensagem(event, 'exibirInserirLocalidadeAction.do?objetoConsulta=2&limparCampos=1','gerenteLocalidade','Gerente da Localidade');"/>
				
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
		    <td><html:text property="localidadeCEF" size="3" maxlength="3" onkeypress="return isCampoNumerico(event);" tabindex="18"/></td>
		</tr>
		<tr>
			<td><strong>Abastecimento Suspenso:</strong></td>
			<td><html:checkbox property="indicadorAbastecimentoSuspenso" tabindex="19"></html:checkbox></td>
		</tr>
		<tr>
			<td><strong>Abastecimento Mínimo:</strong></td>
			<td><html:checkbox property="indicadorAbastecimentoMinimo" tabindex="20"></html:checkbox></td>
		</tr>
		<tr>
		    <td><strong>Última Quadra:</strong></td>
		    <td><html:text property="ultimaQuadra" size="5" maxlength="5" onkeypress="return isCampoNumerico(event);" tabindex="21"/></td>
		</tr>
		<tr>
		    <td><strong>Localidade Contábil:</strong>
		    </td>
		    <td><html:text property="localidadeContabil" size="3" maxlength="3" onkeypress="return isCampoNumerico(event);" tabindex="22"/></td>
		</tr>
	</logic:equal>
	
   <tr>
       <td></td>
       <td><font color="#FF0000">*</font>&nbsp;Campos obrigat&oacute;rios</td>
   </tr>
	
				<tr>
					<td colspan="2"><input type="button" name="Button"
						class="bottonRightCol" value="Desfazer" tabindex="23"
						onClick="javascript:window.location.href='/gsan/exibirInserirLocalidadeAction.do?desfazer=S'"
						style="width: 80px" />&nbsp; <input type="button" name="Button"
						class="bottonRightCol" value="Cancelar" tabindex="24"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
						style="width: 80px" /></td>
					<td align="right"><gcom:controleAcessoBotao name="Button"
						value="Inserir" tabindex="25"
						onclick="javascript:validarForm(document.forms[0]);"
						url="inserirLocalidadeAction.do" /><!-- 
						<input type="button" name="Button" class="bottonRightCol"
						value="Inserir" tabindex="6" style="width: 80px"
						onClick="javascript:validarForm(document.forms[0])" />  --></td>
				</tr>
   
   </table>
      
	<p>&nbsp;</p>
	
	</td>
  </tr>
</table>

<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>


</body>
<!-- localidade_inserir.jsp -->
</html:html>

