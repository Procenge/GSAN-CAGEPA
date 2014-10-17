<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<%@ page import="gcom.util.ConstantesSistema"%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"  formName="GerarRelatorioConsumidoresOcorrenciaLeiturasForm"/>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<script language="JavaScript"><!--
	
	function submeterForm(){
		
		var form = document.forms[0];
		
		if(validarCampos()) {
			
			if(form.ehRelatorioBatch.value == 2) {
				form.target = "_blank";				
			}
			
			form.submit();
			form.target = "";
		}
	}
	
function validarCampos(){

	var form = document.forms[0];
	var msg = "";

	if(form.p_nAmRef.value == "") {
		msg = "Informe o Ano/Mês de Referência";
	}

	if( form.p_nQuadra.value != "" && !validaCampoInteiroPositivo(form.p_nQuadra.value)){
		msg = "Infome Números válidos para a Quadra";	
	}

	
	if( msg != ""){
	alert(msg);
	return false;
	}

	return true;
	}


	
	
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,campo){

		document.forms[0].campoOrigem.value = campo;

		if(!campo.disabled){
	  		if (objeto == null || codigoObjeto == null){
	     		if(tipo == "" ){
	      			abrirPopup(url,altura, largura);
	     		}else{
		  			abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
		 		}
	 		}else{
				if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
					alert(msg);
				}else{
					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto, altura, largura);
				}
			}
  		}
	}
	
  	function reloadForm(){
  		var form = document.forms[0];
  	
  		form.action='exibirRelatorioConsumidoresOcorrenciaLeiturasAction.do';
	    form.submit();
  	}

	// Função que centraliza toda a operação de limpeza do formulário
	// O parêmetro "nivel" segue a seguinte regra:
	// 0 - Limpa todo o formulário 
	// 1 - Limpa da Regional em diante
	// 2 - Limpa do Grupo de Faturamento em diante
	// 3 - Limpa de Localidade em diante
	// 4 - Limpa do Setor de Faturamento em diante
	// 5 - Limpa da Quadra em diante 
  	function limpar(nivel){

  		var form = document.forms[0];

  		if(nivel <= 1) {
  		    form.p_nAmRef.value = "";
  		}  
  		
  		if(nivel <= 1) {
  		    form.p_nRegiao.value = "-1";
  		}
  		
  		if(nivel <= 1) {
  		    form.p_nUnidNegocio.value = "-1";
  		}

  		if(nivel <= 1) {
  		    form.p_nUF.value = "-1";
  		}
  		
  		if(nivel <= 2) {
  		    form.p_nGrupo.value = "-1";
  		}

  		if(nivel <= 3) {
  		    form.p_nLocVinculada.value = "";
		    form.nomeLocalidadeVinculada.value = "";
  		}  

  		if(nivel <= 4) {
  		    form.p_nLocalidade.value = "";
		    form.nomeLocalidade.value = "";
  		}  

  		if(nivel <= 5) {
			form.p_nCodSetor.value = "";
			form.nomeSetorComercial.value = "";
  		}

  		if(nivel <= 6) {
			form.p_nQuadra.value = "";
  		}
  	}
  	
	function limparBorracha(tipo){
		var form = document.forms[0];
		
		switch(tipo){
			case 1: // Localidade Vinculada(Elo Pólo)
				limpar(3);
				break;

			case 2: // Localidade
				limpar(4);
				break;
				
			case 3: // Setor		     	
		     	limpar(5);
		     	break;
		}
	}
	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

		var form = document.forms[0];

		if(form.campoOrigem.value == 'p_nLocVinculada') {
      		form.p_nLocVinculada.value = codigoRegistro;
	  		form.nomeLocalidadeVinculada.value = descricaoRegistro;
	  		form.nomeLocalidadeVinculada.style.color = "#000000";
		} else if(form.campoOrigem.value == 'p_nLocalidade') {		
      		form.p_nLocalidade.value = codigoRegistro;
	  		form.nomeLocalidade.value = descricaoRegistro;	 
	  		form.nomeLocalidade.style.color = "#000000";
		} else if(form.campoOrigem.value == 'p_nCodSetor') {		
      		form.p_nCodSetor.value = codigoRegistro;
	  		form.nomeSetorComercial.value = descricaoRegistro;	  		
	  		form.nomeSetorComercial.style.color = "#000000";
		} else if(form.campoOrigem.value == 'p_nQuadra') {		
      		form.p_nQuadra.value = codigoRegistro;
		}

		form.campoOrigem.value = "";
	}	
	
	function recuperarDadosQuatroParametros(idRegistro, descricaoRegistro, codigoRegistro, tipoConsulta) {
		recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta);
	}

	 function limparPesquisaQuadra(inicial) {
	    var form = document.forms[0];
	
	     	form.p_nQuadra.value = "";
	
		var msgQuadraInicio = document.getElementById("msgQuadraInicio");
	
		if (msgQuadraInicio != null){
	
			msgQuadraInicio.innerHTML = "";
		}
	  }
	
	 function pesquisaLupaComDependencia(url, idDependencia, nomeMSG, altura, largura, campo){
	
		 document.forms[0].campoOrigem.value = campo;
	
		 abrirPopupDependencia(url, idDependencia, nomeMSG, altura, largura);
	 }
	 	 
--></script>

</head>

<body leftmargin="5" topmargin="5">

<html:form action="/gerarRelatorioConsumidoresOcorrenciaLeiturasAction"
	name="GerarRelatorioConsumidoresOcorrenciaLeiturasForm"
	type="org.apache.struts.action.DynaActionForm"
	method="post">
	
<input type="hidden" name="acao" value="gerarRelatorio"/>
<input type="hidden" name="relatorio" value="RelatorioConsumidores.rpt"/>	
<input type="hidden" name="campoOrigem" value=""/>

<%--  ehRelatorioBatch indica se o Relatorio será processado como uma Rotina Batch  --%>
<%--  1 - Indica que SIM, é uma Rotina Batch                                       --%>
<%--  2 - Indica que NAO, não é uma Rotina Batch e o Relatório será gerado online  --%>
<input type="hidden" name="ehRelatorioBatch" value="1"/>

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
			<td width="600" valign="top" class="centercoltext">
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
					<td class="parabg">Gerar Relat&oacute;rio de Consumidores com Ocorr&ecirc;ncia de Leituras</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				
				<tr>
					<td colspan="2">Para gerar o relat&oacute;rio, informe os dados abaixo:<br><br></td>
				</tr>

				  <tr>
			      	<td HEIGHT="30"><strong>Mês/Ano de Referência:<font color="#FF0000">*</font></strong></td>
			        <td>
						<html:text property="p_nAmRef" size="8" maxlength="7" tabindex="1" 
						onkeypress="javascript:return isCampoNumerico(event); "
						onkeyup="mascaraAnoMes(this, event);"
						onblur="javascript:return verificaAnoMes(this);"
						/>&nbsp;MM/AAAA
			        </td>
                </tr>
				<tr>
					<td>
						<strong>Ger&ecirc;ncia Regional:<font color="#FF0000"></font></strong>
					</td>

					<td>
						<strong> 
						<html:select property="p_nRegiao" 
							style="width: 230px;">
							
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>
					
							<logic:present name="colecaoGerenciaRegional" scope="request">
								<html:options collection="colecaoGerenciaRegional"
									labelProperty="nomeComId" 
									property="id" />
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>
				<tr>
					<td>
						<strong>Unidade Neg&oacute;cio:<font color="#FF0000"></font></strong>
					</td>

					<td>
						<strong> 
						<html:select property="p_nUnidNegocio" 
							style="width: 230px;">
							
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>
					
							<logic:present name="colecaoUnidadeNegocio" scope="request">
								<html:options collection="colecaoUnidadeNegocio"
									labelProperty="nomeComId" 
									property="id" />
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>
				<tr>
					<td>
						<strong>Unidade Federa&ccedil;&atilde;o:<font color="#FF0000"></font></strong>
					</td>

					<td>
						<strong> 
						<html:select property="p_nUF" 
							style="width: 230px;">
							
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>
					
							<logic:present name="colecaoUnidadeFederacao" scope="request">
								<html:options collection="colecaoUnidadeFederacao"
									labelProperty="descricao" 
									property="id" />
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>
				<tr>
					<td>
						<strong>Grupo de Faturamento:<font color="#FF0000"></font></strong>
					</td>

					<td>
						<strong> 
						<html:select property="p_nGrupo" 
							style="width: 230px;">
							
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>
					
							<logic:present name="colecaoFaturamentoGrupo" scope="request">
								<html:options collection="colecaoFaturamentoGrupo"
									labelProperty="descricao" 
									property="id" />
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>
				<tr>
					<td><strong>Elo Pólo:<font color="#FF0000"></font></strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							tabindex="1"
							property="p_nLocVinculada" 
							size="3"		
							onkeypress="javascript:validaEnterComMensagem(event, 'exibirRelatorioConsumidoresOcorrenciaLeiturasAction.do?objetoConsulta=1','p_nLocVinculada','Elo Pólo');return isCampoNumerico(event);"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarEloAction.do', 'EloPolo', null, null, 320, 810, '','p_nLocVinculada');">
							<img width="23" 
								height="21" 
								border="0" 
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Elo Polo" /></a>
								
                        <logic:empty name="GerarRelatorioConsumidoresOcorrenciaLeiturasForm" property="p_nLocVinculada">
							<html:text property="nomeLocalidadeVinculada" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />                        
						</logic:empty>
						<logic:notEmpty name="GerarRelatorioConsumidoresOcorrenciaLeiturasForm" property="p_nLocVinculada">
						    <html:text property="nomeLocalidadeVinculada" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
						<a href="javascript:limparBorracha(1);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				<tr>
					<td><strong>Localidade:<font color="#FF0000"></font></strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							tabindex="1"
							property="p_nLocalidade" 
							size="3"		
							onkeypress="javascript:validaEnterComMensagem(event, 'exibirRelatorioConsumidoresOcorrenciaLeiturasAction.do?objetoConsulta=2','p_nLocalidade','Localidade');return isCampoNumerico(event);"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'localidade', null, null, 275, 480, '','p_nLocalidade');">
							<img width="23" 
								height="21" 
								border="0" 
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" /></a>
								
                        <logic:empty name="GerarRelatorioConsumidoresOcorrenciaLeiturasForm" property="p_nLocalidade">
							<html:text property="nomeLocalidade" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />                        
						</logic:empty>
						<logic:notEmpty name="GerarRelatorioConsumidoresOcorrenciaLeiturasForm" property="p_nLocalidade">
						    <html:text property="nomeLocalidade" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
						<a href="javascript:limparBorracha(2);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				<tr>
					<td><strong>Setor Comercial:<font color="#FF0000"></font></strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							tabindex="1"
							property="p_nCodSetor" 
							size="3"
							onkeypress="javascript:validaEnterDependencia(event, 'exibirRelatorioConsumidoresOcorrenciaLeiturasAction.do?objetoConsulta=3', document.forms[0].p_nCodSetor, document.forms[0].p_nLocalidade.value, 'Setor Comercial');return isCampoNumerico(event);"/>
							
						<a href="javascript:pesquisaLupaComDependencia('exibirPesquisarSetorComercialAction.do?idLocalidade='+document.forms[0].p_nLocalidade.value+'&tipo=SetorComercial',document.forms[0].p_nLocalidade.value,'Localidade', 400, 800,'p_nCodSetor');limparPesquisaQuadra('true');">
							<img width="23" 
								height="21" 
								border="0" 
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Setor Comercial" /></a>
								
                        <logic:empty name="GerarRelatorioConsumidoresOcorrenciaLeiturasForm" property="p_nCodSetor">
							<html:text property="nomeSetorComercial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />                        
						</logic:empty>
						<logic:notEmpty name="GerarRelatorioConsumidoresOcorrenciaLeiturasForm" property="p_nCodSetor">
						    <html:text property="nomeSetorComercial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>						
						
						<a href="javascript:limparBorracha(3);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				<tr>
					<td><strong>Quadra:<font color="#FF0000"></font></strong></td>
					
					<td>
						
						<html:text maxlength="5" 
							tabindex="1"
							property="p_nQuadra"							
							size="5"
							onkeypress="return isCampoNumerico(event);"/>
							<a
								href="javascript:pesquisaLupaComDependencia('exibirPesquisarQuadraAction.do?idLocalidade='+document.forms[0].p_nLocalidade.value+'&codigoSetorComercial='+document.forms[0].p_nCodSetor.value+'&tipo=Quadra&retornarSeteParametros=S',document.forms[0].p_nCodSetor.value,'Setor Comercial', 400, 800,'p_nQuadra');"
								title="Pesquisar Quadra">
								<img border="0"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0" />
							</a> 
							<logic:present name="codigoQuadraInicioNaoEncontrada" scope="request">
								<span style="color: #ff0000" id="msgQuadraInicio"><bean:write
									scope="request" name="msgQuadraInicio" /></span>
							</logic:present> 
							
							<a href="javascript:limparPesquisaQuadra('true');document.forms[0].p_nQuadra.focus();">
								<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" title="Apagar" /> 
							</a>
					</td>
				</tr>
				<tr>
			       <td></td>
			       <td><font color="#FF0000">*</font>&nbsp;Campos obrigat&oacute;rios</td>
			   </tr>
				<tr><td><br></td></tr>
				<tr>
					<td height="24" >
			          	<input type="button" 
			          		class="bottonRightCol" 
			          		value="Limpar" 
			          		onclick="javascript:limpar(0);"/>
					</td>
				
					<td align="right">
						<input type="button" 
							name="Button" 
							class="bottonRightCol" 
							value="Gerar" 
							onClick="javascript:submeterForm()" />
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
<%@ include file="/jsp/util/rodape.jsp"%>	
</body>
</html:form>
</html:html>