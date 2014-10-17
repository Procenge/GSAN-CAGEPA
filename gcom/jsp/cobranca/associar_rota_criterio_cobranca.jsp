<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
		<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
		<html:javascript staticJavascript="false"  formName="AssociarConjuntoRotasCriterioCobrancaActionForm"/>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">

	function validarForm(form){
		if(camposObrigatorios()){
			submeterFormPadrao(form);
		}
	}
	
	function habilitarTodos(){
		var form = document.forms[0];
	    form.idGrupoCobranca.disabled = false;
    	form.idGerenciaRegional.disabled = false;
    	form.idUnidadeNegocio.disabled = false;
    	form.idLocalidadeInicial.disabled = false;
    	form.idLocalidadeFinal.disabled = false;
    	form.codigoSetorComercialInicial.disabled = false;
    	form.codigoSetorComercialFinal.disabled = false;
    	form.numeroRotaInicial.disabled = false;
    	form.numeroRotaFinal.disabled = false;
	}
	
	function camposObrigatorios(){
		var retorno = false;
		var form = document.forms[0];
		if (campoInformado(form.idAcaoCobranca)){	
			if (campoInformado(form.idCriterioCobranca)){
				retorno = true;
			} else {
				alert('Informe um Critério de Cobrança.');
				form.idCriterioCobranca.focus();
			}
		} else{
			form.idAcaoCobranca.focus();
			alert('Selecione uma Ação de Cobrança.');		
		}			
		
		return retorno;
	}
	
	function selecionar(){
		var form = document.forms[0];
		if(camposObrigatorios()){
			habilitarTodos(); // Habilita todos para o Form reconhecer os valores preenchidos.
			form.action = "selecionarAssociarConjuntoRotasCriterioCobrancaAction.do";
			form.submit();
		}
	}
	
	function associar(){
		var form = document.forms[0];
		if(camposObrigatorios()){
			form.action = "associarConjuntoRotasCriterioCobrancaAction.do";
			form.submit();
		}
	}
	
	function desassociar(){
		var form = document.forms[0];
		if(camposObrigatorios()){
			form.action = "desassociarConjuntoRotasCriterioCobrancaAction.do";
			form.submit();
		}
	}

	function limparLocalidade(form) {
    	form.idLocalidadeInicial.value = "";
    	form.descricaoLocalidadeInicial.value = "";
    	form.codigoSetorComercialInicial.value = "";
    	form.descricaoSetorComercialInicial.value = "";
    	form.codigoSetorComercialFinal.value = "";
    	form.descricaoSetorComercialFinal.value = "";
    	form.numeroRotaInicial.value = "-1";
		form.numeroRotaFinal.value = "-1"
	}
	
	function limparSetorComercial(form) {
    	form.codigoSetorComercialInicial.value = "";
    	form.descricaoSetorComercialInicial.value = "";
    	form.codigoSetorComercialFinal.value = "";
    	form.descricaoSetorComercialFinal.value = "";
    	form.numeroRotaInicial.value = "-1";
		form.numeroRotaFinal.value = "-1"
	}
	
	function limparDescLocalidadeInicial(form){
		form.descricaoLocalidadeInicial.value = "";
		form.codigoSetorComercialInicial.value = "";
    	form.descricaoSetorComercialInicial.value = "";
    	form.codigoSetorComercialFinal.value = "";
    	form.descricaoSetorComercialFinal.value = "";
		form.numeroRotaInicial.value = "-1";
		form.numeroRotaFinal.value = "-1"
	}
	
	function limparLocalidadeFinal(form){
		form.idLocalidadeFinal.value = "";
    	form.descricaoLocalidadeFinal.value = "";
    	form.codigoSetorComercialInicial.value = "";
    	form.descricaoSetorComercialInicial.value = "";
    	form.codigoSetorComercialFinal.value = "";
    	form.descricaoSetorComercialFinal.value = "";
    	form.numeroRotaInicial.value = "-1";
		form.numeroRotaFinal.value = "-1"
	}

	function limparLocalidadeInicial(form) {
		form.idLocalidadeInicial.value = "";
		form.idLocalidadeFinal.value = "";
    	form.descricaoLocalidadeInicial.value = "";
    	form.descricaoLocalidadeFinal.value = "";
    	form.codigoSetorComercialInicial.value = "";
    	form.descricaoSetorComercialInicial.value = "";
    	form.codigoSetorComercialFinal.value = "";
    	form.descricaoSetorComercialFinal.value = "";
    	form.numeroRotaInicial.value = "-1";
		form.numeroRotaFinal.value = "-1"
	}
	
	function limparDescLocalidadeFinal(form) {
    	form.descricaoLocalidadeFinal.value = "";
    	form.codigoSetorComercialFinal.value = "";
    	form.codigoSetorComercialInicial.value = "";
    	form.descricaoSetorComercialInicial.value = "";
    	form.descricaoSetorComercialFinal.value = "";
    	form.numeroRotaInicial.value = "-1";
		form.numeroRotaFinal.value = "-1"
	}
	
	function limparDescSetorComercial(form) {
    	form.descricaoSetorComercialInicial.value = "";
    	form.numeroRotaInicial.value = "-1";
		form.numeroRotaFinal.value = "-1"
	}
	
	function limparDescSetorComercialFinal(form) {
    	form.descricaoSetorComercialFinal.value = "";
    	form.numeroRotaInicial.value = "-1";
		form.numeroRotaFinal.value = "-1"
	}
	
	function limparSetorComercialFinal(form) {
    	form.descricaoSetorComercialFinal.value = "";
    	form.codigoSetorComercialFinal.value = "";
    	form.numeroRotaInicial.value = "-1";
		form.numeroRotaFinal.value = "-1"
	}
	
	function limparDescCriterio(form){
		form.descricaoCriterioCobranca.value = "";
	}
	
	function limparCriterio(form){
		form.idCriterioCobranca.value = "";
		form.descricaoCriterioCobranca.value = "";
	}
	
 	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

	    var form = document.forms[0];
	
	    if (tipoConsulta == 'localidadeOrigem') {
	      	limparLocalidadeInicial(form);
	      	form.idLocalidadeInicial.value = codigoRegistro;
	    	form.descricaoLocalidadeInicial.value = descricaoRegistro;
	    	form.descricaoLocalidadeInicial.style.color = "#000000";
	      	form.idLocalidadeFinal.focus();
	    }
	    
	     if (tipoConsulta == 'localidadeDestino') {
	      	limparLocalidadeFinal(form);
	      	form.idLocalidadeFinal.value = codigoRegistro;
	    	form.descricaoLocalidadeFinal.value = descricaoRegistro;
	    	form.descricaoLocalidadeFinal.style.color = "#000000";
	      	form.idLocalidadeFinal.focus();
	    }
	
	    if (tipoConsulta == 'criterioCobranca') {
	      limparCriterio(form);
	      form.idCriterioCobranca.value = codigoRegistro;
	      form.descricaoCriterioCobranca.value = descricaoRegistro;
	      form.descricaoCriterioCobranca.style.color = "#000000";
	    }
	    
   }
   
   function habilitarPesquisaLocalidade(form, tipo) {
		if (form.idLocalidadeInicial.disabled == false){
		    if(tipo == 'origem'){
				chamarPopup('exibirPesquisarLocalidadeAction.do', 'origem', 'indicadorUsoTodos', '1', 275, 480, '',form.idLocalidadeInicial.value);
			}
			else if(tipo == 'destino'){
				chamarPopup('exibirPesquisarLocalidadeAction.do', 'destino', 'indicadorUsoTodos', '1', 275, 480, '',form.idLocalidadeFinal.value);
			}
		}	
	}

	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){
		if(objetoRelacionado.disabled != true){
			if (objeto == null || codigoObjeto == null){
				abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
			} else {
				if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
					alert(msg);
				} else {
					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
				}
			}
		}
	}
	
	function recuperarDadosQuatroParametros(codigoRegistro, descricaoRegistro1, descricaoRegistro2, tipoConsulta) {
        
        var form = document.forms[0];
        
    	if (tipoConsulta == 'setorComercialOrigem'){
	      limparSetorComercialFinal(form);
	      form.codigoSetorComercialInicial.value = descricaoRegistro2;
	      form.descricaoSetorComercialInicial.value = descricaoRegistro1;
	      form.descricaoSetorComercialInicial.style.color = "#000000"
	    }
	    
	    if (tipoConsulta == 'setorComercialDestino'){
	      limparSetorComercialFinal(form);
	      form.codigoSetorComercialFinal.value = descricaoRegistro2;
	      form.descricaoSetorComercialFinal.value = descricaoRegistro1;
	      form.descricaoSetorComercialFinal.style.color = "#000000"
	    }  
    }
    
    // Função utilizada para Habilitar e Desabilitar os campos 
    //de acordo com a descrição no [UC0543]
    // Saulo Lima 26/06/2008 
    function verificarCampos(){
    	var form = document.forms[0];
    	if (form.idGrupoCobranca.value != "-1"){
    		// Desabilita todos os outros campos
    		form.idGerenciaRegional.disabled = true;
    		form.idUnidadeNegocio.disabled = true;
    		form.idLocalidadeInicial.disabled = true;
    		form.idLocalidadeFinal.disabled = true;
    		form.codigoSetorComercialInicial.disabled = true;
    		form.codigoSetorComercialFinal.disabled = true;
    		form.numeroRotaInicial.disabled = true;
    		form.numeroRotaFinal.disabled = true;
 
    	} else if ((form.idGerenciaRegional.value != "-1") 
    				|| (form.idUnidadeNegocio.value != "-1")
    				|| (form.idLocalidadeInicial.value != "")
    				|| (form.idLocalidadeFinal.value != "")){
    		// Desabilita o Grupo de Cobrança
    		form.idGrupoCobranca.disabled = true;
    		// Habilita os outros campos
    		form.idGerenciaRegional.disabled = false;
    		form.idUnidadeNegocio.disabled = false;
    		form.idLocalidadeInicial.disabled = false;
    		form.idLocalidadeFinal.disabled = false;
    		// Habilita o Setor Comercial caso a "localidade inicial" seja igual à "localidade final"
    		if (((form.idLocalidadeInicial.value != "")
    				|| (form.idLocalidadeFinal.value != ""))
    				&& (form.idLocalidadeInicial.value == form.idLocalidadeFinal.value)){
    			form.codigoSetorComercialInicial.disabled = false;
    			form.codigoSetorComercialFinal.disabled = false;
    			// Habilita a Rota caso o "setor inicial" seja igual ao "setor final"
    			if (((form.codigoSetorComercialInicial.value != "")
    				|| (form.codigoSetorComercialFinal.value != ""))
    				&& (form.codigoSetorComercialInicial.value == form.codigoSetorComercialFinal.value)){
    				form.numeroRotaInicial.disabled = false;
    				form.numeroRotaFinal.disabled = false;
    			} else {
    				form.numeroRotaInicial.disabled = true;
    				form.numeroRotaFinal.disabled = true;
    			}
    		} else {
    			form.codigoSetorComercialInicial.disabled = true;
    			form.codigoSetorComercialFinal.disabled = true;
    			form.numeroRotaInicial.disabled = true;
    			form.numeroRotaFinal.disabled = true;
    		}
    		
    	} else {
    		// Habilita alguns campos
    		form.idGrupoCobranca.disabled = false;
    		form.idGerenciaRegional.disabled = false;
    		form.idUnidadeNegocio.disabled = false;
    		form.idLocalidadeInicial.disabled = false;
    		form.idLocalidadeFinal.disabled = false;
    		// Desabilita alguns campos
    		form.codigoSetorComercialInicial.disabled = true;
    		form.codigoSetorComercialFinal.disabled = true;
    		form.numeroRotaInicial.disabled = true;
    		form.numeroRotaFinal.disabled = true;
    	}

    }
    

</script>
</head>
<body leftmargin="5" topmargin="5"
onload="setarFoco('${requestScope.nomeCampo}');verificarCampos();">
<html:form action="/selecionarAssociarConjuntoRotasCriterioCobrancaAction"
	name="AssociarConjuntoRotasCriterioCobrancaActionForm"
	type="gcom.gui.cobranca.AssociarConjuntoRotasCriterioCobrancaActionForm"
	method="post"
	onsubmit="return validateAssociarConjuntoRotasCriterioCobrancaActionForm(this);">


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
		<td valign="top" class="centercoltext">
            <table>
              <tr><td></td></tr>
              
            </table>
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
                <td class="parabg">Associar Conjunto de Rotas a Critério de Cobrança</td>
                <td width="11" valign="top"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
              </tr>
            </table>


			<p>&nbsp;</p>
			<table width="100%" border="0">


				 <tr>
			          <td width="100%" colspan=2>
				          <table width="100%" border="0">
				          	<tr>
				          		<td>Para associar um conjunto de rotas a critério de cobrança, informe os dados abaixo:</td>
				          	</tr>
				          </table>
			          </td>
			     </tr>
				
				<tr> 
                	<td><strong>Ação de Cobrança:<font color="#FF0000">*</font></strong></td>
                	<td><html:select property="idAcaoCobranca">
                    	<html:option value="-1">&nbsp;</html:option>
                    	<html:options collection="colecaoAcaoCobranca"
							labelProperty="descricaoCobrancaAcao" property="id" />
                    </html:select> <font size="1">&nbsp; </font></td>
              	</tr>
              	
              	<tr>
					<td><strong>Critério de Cobrança:<font color="#FF0000">*</font></strong></td>
					<td><html:text maxlength="3"
						property="idCriterioCobranca" size="3"
						onkeypress="javascript:limparDescCriterio(document.forms[0]);
						validaEnterComMensagem(event, 'exibirAssociarRotasCriterioCobrancaAction.do?objetoConsulta=1', 'idCriterioCobranca','CriterioCobranca');" />
					<a
						href="javascript:abrirPopup('exibirPesquisarCriterioCobrancaAction.do');">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Criterio Cobranca" /></a> 
						
						<logic:present
							name="idCriterioCobrancaNaoEncontrado">
						<logic:equal name="idCriterioCobrancaNaoEncontrado" value="exception">
							<html:text property="descricaoCriterioCobranca" size="35"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						
						<logic:notEqual name="idCriterioCobrancaNaoEncontrado" value="exception">
							<html:text property="descricaoCriterioCobranca" size="35"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
						</logic:present> 
						
						<logic:notPresent name="idCriterioCobrancaNaoEncontrado">
							<logic:empty name="AssociarConjuntoRotasCriterioCobrancaActionForm" property="idCriterioCobranca">
								<html:text property="descricaoCriterioCobranca" value="" size="35"
									maxlength="30" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:empty>
							
							<logic:notEmpty name="AssociarConjuntoRotasCriterioCobrancaActionForm" property="idCriterioCobranca">
								<html:text property="descricaoCriterioCobranca" size="35"
									maxlength="30" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notEmpty>

					</logic:notPresent> 					<a
						href="javascript:limparCriterio(document.forms[0]);">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" />
					</a></td>
				</tr>
              	
              	
              	<tr> 
                	<td><strong>Grupo de Cobrança:</strong></td>
                	<td><html:select property="idGrupoCobranca" onchange="javascript:verificarCampos();">
                    	<html:option value="-1">&nbsp;</html:option>
                    	<html:options collection="colecaoGrupoCobranca"
							labelProperty="descricao" property="id" />
                    </html:select> <font size="1">&nbsp; </font></td>
              	</tr>
              	
              	<tr> 
                	<td><strong>Gerência Regional:</strong></td>
                	<td><html:select property="idGerenciaRegional" onchange="javascript:verificarCampos();" >
                    	<html:option value="-1">&nbsp;</html:option>
                    	<html:options collection="colecaoGerenciaRegional"
							labelProperty="nome" property="id" />
                    </html:select> <font size="1">&nbsp; </font></td>
              	</tr>
              	
              	<tr> 
                	<td><strong>Unidade de Negócio:</strong></td>
                	<td><html:select property="idUnidadeNegocio" onchange="javascript:verificarCampos();">
                    	<html:option value="-1">&nbsp;</html:option>
                    	<html:options collection="colecaoUnidadeNegocio"
							labelProperty="nome" property="id" />
                    </html:select> <font size="1">&nbsp; </font></td>
              	</tr>
              	
             	<tr>
					<td><strong>Localidade Inicial:</strong></td>
					<td><html:text maxlength="3"
						property="idLocalidadeInicial" size="3" 
						onkeypress="javascript:limparDescLocalidadeInicial(document.forms[0]);
						limparSetorComercial(document.forms[0]);
						validaEnterComMensagem(event, 'exibirAssociarRotasCriterioCobrancaAction.do?objetoConsulta=1', 'idLocalidadeInicial','Localidade');"
						onchange="javascript:verificarCampos();" />
						
					<a href="javascript:habilitarPesquisaLocalidade(document.forms[0],'origem');" >
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Localidade" /></a> 
						
						<logic:present
							name="idLocalidadeInicialNaoEncontrado">
						<logic:equal name="idLocalidadeInicialNaoEncontrado" value="exception">
							<html:text property="descricaoLocalidadeInicial" size="35"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						
						<logic:notEqual name="idLocalidadeInicialNaoEncontrado" value="exception">
							<html:text property="descricaoLocalidadeInicial" size="35"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
						</logic:present> 
						
						<logic:notPresent name="idLocalidadeInicialNaoEncontrado">
							<logic:empty name="AssociarConjuntoRotasCriterioCobrancaActionForm" property="idLocalidadeInicial">
								<html:text property="descricaoLocalidadeInicial" value="" size="35"
									maxlength="30" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:empty>
							
							<logic:notEmpty name="AssociarConjuntoRotasCriterioCobrancaActionForm" property="idLocalidadeInicial">
								<html:text property="descricaoLocalidadeInicial" size="35"
									maxlength="30" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notEmpty>

					</logic:notPresent> 					<a
						href="javascript:limparLocalidadeInicial(document.forms[0]);
						limparSetorComercial(document.forms[0]);">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" />
					</a></td>
				</tr>
				
				<tr>
					<td><strong>Localidade Final:</strong></td>
					<td><html:text maxlength="3"
						property="idLocalidadeFinal" size="3"
						onblur="javascript:verificarCampos();"
						onkeypress="javascript:limparDescLocalidadeFinal(document.forms[0]);
						limparSetorComercial(document.forms[0]);
						validaEnterComMensagem(event, 'exibirAssociarRotasCriterioCobrancaAction.do?objetoConsulta=1', 'idLocalidadeFinal','Localidade');" />
					<a href="javascript:habilitarPesquisaLocalidade(document.forms[0],'destino');" >
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Localidade" /></a> 
						
						<logic:present
							name="idLocalidadeFinalNaoEncontrado">
						<logic:equal name="idLocalidadeFinalNaoEncontrado" value="exception">
							<html:text property="descricaoLocalidadeFinal" size="35"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						
						<logic:notEqual name="idLocalidadeFinalNaoEncontrado" value="exception">
							<html:text property="descricaoLocalidadeFinal" size="35"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
						</logic:present> 
						
						<logic:notPresent name="idLocalidadeFinalNaoEncontrado">
							<logic:empty name="AssociarConjuntoRotasCriterioCobrancaActionForm" property="idLocalidadeFinal">
								<html:text property="descricaoLocalidadeFinal" value="" size="35"
									maxlength="30" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:empty>
							
							<logic:notEmpty name="AssociarConjuntoRotasCriterioCobrancaActionForm" property="idLocalidadeFinal">
								<html:text property="descricaoLocalidadeFinal" size="35"
									maxlength="30" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notEmpty>

					</logic:notPresent> 					<a
						href="javascript:limparLocalidadeFinal(document.forms[0]);
						limparSetorComercial(document.forms[0]);">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" />
					</a></td>
				</tr>
				

				<tr>
					<td><strong>Setor Comercial Inicial:</strong></td>
					<td height="24"><html:text maxlength="3"
						property="codigoSetorComercialInicial" size="3"
						onchange="javascript:verificarCampos();"
						onkeypress="javascript:limparDescSetorComercial(document.forms[0]);
						return validaEnterDependenciaComMensagem(event, 'exibirAssociarRotasCriterioCobrancaAction.do?objetoConsulta=1', this, document.forms[0].idLocalidadeInicial.value, 'Localidade','Setor Comercial');" />
						
						<a
						href="javascript:abrirPopupDependencia('exibirPesquisarSetorComercialAction.do?idLocalidade='+document.forms[0].idLocalidadeInicial.value+'&tipo=setorComercialOrigem',document.forms[0].idLocalidadeInicial.value,'Localidade', 400, 800);">
						
						<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Setor Comercial" /></a> <logic:present
						name="codigoSetorComercialInicialNaoEncontrado">
						<logic:equal name="codigoSetorComercialInicialNaoEncontrado"
							value="exception">
							<html:text property="descricaoSetorComercialInicial" size="35"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="codigoSetorComercialInicialNaoEncontrado"
							value="exception">
							<html:text property="descricaoSetorComercialInicial" size="35"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent
						name="codigoSetorComercialInicialNaoEncontrado">
						<logic:empty name="AssociarConjuntoRotasCriterioCobrancaActionForm"
							property="codigoSetorComercialInicial">
							<html:text property="descricaoSetorComercialInicial" value="" size="35"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="AssociarConjuntoRotasCriterioCobrancaActionForm"
							property="codigoSetorComercialInicial">
							<html:text property="descricaoSetorComercialInicial" size="35"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>

					</logic:notPresent> 
						<a
						href="javascript:limparSetorComercial(document.forms[0]);">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" />
					</a>
				</td>
				</tr>
             	
             	<tr>
					<td><strong>Setor Comercial Final:</strong></td>
					<td height="24"><html:text maxlength="3"
						property="codigoSetorComercialFinal" size="3"
						onchange="javascript:verificarCampos();"
						onkeypress="javascript:limparDescSetorComercialFinal(document.forms[0]);
						return validaEnterDependenciaComMensagem(event, 'exibirAssociarRotasCriterioCobrancaAction.do?objetoConsulta=1', this, document.forms[0].idLocalidadeFinal.value, 'Localidade','Setor Comercial');" />
						
						<a
						href="javascript:abrirPopupDependencia('exibirPesquisarSetorComercialAction.do?idLocalidade='+document.forms[0].idLocalidadeFinal.value+'&tipo=setorComercialDestino',document.forms[0].idLocalidadeFinal.value,'Localidade', 400, 800);">
						
						<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Setor Comercial" /></a> <logic:present
						name="codigoSetorComercialFinalNaoEncontrado">
						<logic:equal name="codigoSetorComercialFinalNaoEncontrado"
							value="exception">
							<html:text property="descricaoSetorComercialFinal" size="35"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="codigoSetorComercialFinalNaoEncontrado"
							value="exception">
							<html:text property="descricaoSetorComercialFinal" size="35"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent
						name="codigoSetorComercialFinalNaoEncontrado">
						<logic:empty name="AssociarConjuntoRotasCriterioCobrancaActionForm"
							property="codigoSetorComercialFinal">
							<html:text property="descricaoSetorComercialFinal" value="" size="35"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="AssociarConjuntoRotasCriterioCobrancaActionForm"
							property="codigoSetorComercialFinal">
							<html:text property="descricaoSetorComercialFinal" size="35"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>

					</logic:notPresent> 
						<a
						href="javascript:limparSetorComercialFinal(document.forms[0]);">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" />
					</a>
				</td>
				</tr>
				
	         	<tr> 
                	<td><strong>Rota Inicial:</strong></td>
                	<td><html:select property="numeroRotaInicial" onchange="javascript:verificarCampos();">
                    	<html:option value="-1">&nbsp;</html:option>
                    	<html:options collection="colecaoRotaInicial"
							labelProperty="codigo" property="id" />
                    </html:select> <font size="1">&nbsp; </font></td>
              	</tr>
              	
              	<tr> 
                	<td><strong>Rota Final:</strong></td>
                	<td><html:select property="numeroRotaFinal" onchange="javascript:verificarCampos();">
                    	<html:option value="-1">&nbsp;</html:option>
                    	<html:options collection="colecaoRotaFinal"
							labelProperty="codigo" property="id" />
                    </html:select> <font size="1">&nbsp; </font></td>
              	</tr>
              	<tr>
					<td>&nbsp;</td>
					<td><strong><font color="#FF0000">*</font></strong>Campos obrigat&oacute;rios</td>
				</tr>
              	<tr> 
                	<td colspan="2" align="right">
                	<input type="button" name="Selecionar" value="Selecionar"  class="bottonRightCol" onclick="javascript:selecionar();"/>
                	</td>
              	</tr>
              	
              	<tr> 
                	<td><strong>Quantidade de rotas selecionadas:</strong></td>
                	<td><html:text property="qtdRotasSelecionadas" size="10" 
						 					style="background-color:#EFEFEF; border:0; color: #000000"/></td>
              	</tr>
              	<tr> 
                	<td width="43%"><strong>Quantidade de rotas COM critério informado para ESTA Ação de Cobrança:</strong></td>
                	<td width="57%"><html:text property="qtdRotasComCriterio" size="10" 
						 					style="background-color:#EFEFEF; border:0; color: #000000"/></td>
              	</tr>
              	<tr> 
                	<td width="43%"><strong>Quantidade de rotas SEM critério informado para ESTA Ação de Cobrança:</strong></td>
                	<td width="57%"><html:text property="qtdRotasSemCriterio" size="10" 
						 					style="background-color:#EFEFEF; border:0; color: #000000"/></td>
              	</tr>
             	
				<tr>
					<td>
						<input name="Button" type="button" class="bottonRightCol" value="Limpar" align="left" onclick="window.location.href='<html:rewrite page="/exibirAssociarRotasCriterioCobrancaAction.do?menu=sim"/>'" />
                    	</td>
                    	<td  align="right">
                    	<logic:present name="habilitaAsociar">
							<input type="button" name="Associar" value="Associar" class="bottonRightCol" onclick="javascript:associar();" />
						</logic:present>
                    	<logic:notPresent name="habilitaAsociar">
							<input type="button" name="Associar" value="Associar" class="bottonRightCol" disabled="disabled" enabled="true"/>
						</logic:notPresent>
						<logic:present name="habilitaDessasociar">
							<input type="button" name="Desassociar" value="Desassociar" class="bottonRightCol" onclick="javascript:desassociar();" />
						</logic:present>
                    	<logic:notPresent name="habilitaDessasociar">
                    		<input type="button" name="Desassociar" value="Desassociar" class="bottonRightCol" disabled="disabled" enabled="true"/>
                    	</logic:notPresent>
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
