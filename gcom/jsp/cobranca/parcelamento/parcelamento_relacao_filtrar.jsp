<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<script language="JavaScript">
<!-- Begin

function limparElo() {
	var form = document.GerarRelatorioRelacaoParcelamentoActionForm;
	form.idElo.value = "";
	form.descricaoElo.value = "";	
}

function limparLocalidade() {
	var form = document.GerarRelatorioRelacaoParcelamentoActionForm;
	form.idLocalidade.value = "";
	form.descricaoLocalidade.value = "";	
	habilitarSetorQuadra();
}

function limparSetorComercial() {
	var form = document.GerarRelatorioRelacaoParcelamentoActionForm;
	form.idSetorComercial.value = "";
	form.descricaoSetorComercial.value = "";	
}

function limparUsuarioResponsavel() {
	var form = document.GerarRelatorioRelacaoParcelamentoActionForm;
	form.idUsuarioResponsavel.value = "";
	form.descricaoUsuarioResponsavel.value = "";	
}

function replicaDados(campoOrigem, campoDestino){
	campoDestino.value = campoOrigem.value;
}

function verificarMotivoDesfazimento(){
  var form = document.GerarRelatorioRelacaoParcelamentoActionForm;
      form.action = 'exibirGerarRelatorioRelacaoParcelamentoAction.do?idSituacaoParcelamento='+form.idSituacaoParcelamento.value;
      form.submit();
}

function replicaDataParcelamento() {
  var form = document.forms[0];
  form.dataParcelamentoFinal.value = form.dataParcelamentoInicial.value
}

function habilitarSetorQuadra(){
 	var form = document.forms[0];
	if(form.idLocalidade.value != ""){
      form.idSetorComercial.disabled = false;
      form.idQuadra.disabled = false;
	} else{
      form.idSetorComercial.disabled = true;
      form.idQuadra.disabled = true;
      limparSetorComercial();
      form.idQuadra.value = "";
	}
}

function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg){
	
	if (objeto == null || codigoObjeto == null){
		abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
	}
	else{
		if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
			alert(msg);
		}
		else{
			abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
		}
	}
}
function limparForm(){

	var form = document.forms[0];
	
	

	form.idGerenciaRegional.value = "-1";
	form.idUnidadeNegocio.value = "-1";
	
	form.idEloPolo.value = '';
	form.descricaoElo.value = '';
	form.idLocalidade.value ='';
	form.descricaoLocalidade.value = '';
	form.idSetorComercial.value = '';
	form.descricaoSetorComercial.value = '';
	form.idQuadra.value = '';
	form.dataParcelamentoInicial.value = '';
	form.dataParcelamentoFinal.value = '';
	form.idsMotivoDesfazimento.value = "-1";
	form.valorDebitoInicial.value = '';
	form.valorDebitoFinal.value = '';
	form.idUsuarioResponsavel.value = '';
	form.descricaoUsuarioResponsavel.value = '';
	
	

	form.action='exibirGerarRelatorioRelacaoParcelamentoAction.do?menu=sim';
form.submit();
}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

	var form = document.GerarRelatorioRelacaoParcelamentoActionForm;

	if (tipoConsulta == 'localidade') {
      form.idLocalidade.value = codigoRegistro;
	  form.descricaoLocalidade.value = descricaoRegistro;
	  form.descricaoLocalidade.style.color = "#000000";
	  habilitarSetorQuadra();
	  form.idSetorComercial.focus()
	 }
	 
	if (tipoConsulta == 'setorComercial') {
      form.idSetorComercial.value = codigoRegistro;
      form.descricaoSetorComercial.value = descricaoRegistro;
      form.descricaoSetorComercial.style.color = "#000000";
      form.idQuadra.focus();
    }
}


-->
</script>

<html:javascript staticJavascript="false"  formName="GerarRelatorioRelacaoParcelamentoActionForm"/>


</head>

<body leftmargin="5" topmargin="5"
		onload="window.focus();javascript:habilitarSetorQuadra('${requestScope.nomeCampo}');" >

<html:form action="/gerarRelatorioRelacaoParcelamentoAction.do"
	name="GerarRelatorioRelacaoParcelamentoActionForm"
	type="gcom.gui.cobranca.parcelamento.GerarRelatorioRelacaoParcelamentoActionForm" 
	method="post"
	onsubmit="return validateGerarRelatorioRelacaoParcelamentoActionForm(this);">

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>


<table width="770" border="0" cellspacing="4" cellpadding="0">
  	
  <tr>
	<td border=0>
	  <table width="770" border="0" cellpadding="0" cellspacing="0">
		<tr>
		  <td width="147" height="381" valign="top" class="leftcoltext">
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
				  		
		  <td width="619" valign="top" class="centercoltext"><!--Início Tabela Reference a Páginação da Tela de Processo-->
		    <table>
			  <tr>
			    <td></td>
			  </tr>
		    </table>
		  
		    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		      <tr>
			    <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
			    <td class="parabg">Filtrar Rela&ccedil;&atilde;o de Parcelamentos</td>
			    <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
			  </tr>
		    </table>
		  
		    <!--Fim Tabela Reference a Páginação da Tela de Processo-->
		    <p>&nbsp;</p>
		  
		    <table width="100%" border="0">
		      <tr>
			    <td colspan="4">Para filtrar o conjunto de parcelamentos, informe os dados abaixo:</td>
			  </tr>
			
			  <tr>
			    <td><strong>Visão do Relatório:</strong></td>
			    <td>
				  <html:select property="idVisaoRelatorio" tabindex="2">
				      <option value="1">MARKETING ATIVO</option>
				      
				  </html:select>
			    </td>
			  </tr>
			  			  		
			  <tr>
			    <td><strong>Gerência Regional:</strong></td>
			    <td>
				  <html:select property="idGerenciaRegional" onchange="controleGerencia(this.value);">
				    <logic:notEmpty name="gerenciasRegionais">
				      <option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</option>
				      <html:options collection="gerenciasRegionais" labelProperty="nome" property="id" />
				    </logic:notEmpty>
				  </html:select>
			    </td>
			  </tr>
			  
			  <tr>
			    <td><strong>Unidade de Negócio:</strong></td>
			    <td>
				  <html:select property="idUnidadeNegocio" tabindex="2" onchange="controleUnidadeNegocio(this.value);">
				    <logic:notEmpty name="colecaoUnidadeNegocio">
				      <option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</option>
				      <html:options collection="colecaoUnidadeNegocio" labelProperty="nome" property="id" />
				    </logic:notEmpty>
				  </html:select>
			    </td>
			  </tr>
			
									
			  <tr>
			    <td><strong>Elo Pólo:</strong></td>
			    <td>
				  <strong>
					<html:text property="idEloPolo" disabled="true" size="5" maxlength="3" onkeyup="return validaEnterComMensagem(event, 'exibirFiltrarRelacaoParcelamentoAction.do', 'idEloPolo', 'Elo Pólo');" onchange="controleElo(this.value);"/>
					<a href="javascript:chamarPopup('exibirPesquisarEloAction.do', 'idEloPolo', null, null, 400, 800, '',document.forms[0].idEloPolo,'idElo',document.forms[0].idElo.value);">
					  <img  border="0" src="imagens/pesquisa.gif" height="21" width="23"></a>
							
					<logic:present name="eloInexistente" scope="request">
					  <html:text property="descricaoElo" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" size="39" maxlength="40" />
					</logic:present>
									
					<logic:notPresent name="eloInexistente" scope="request">
					  <html:text property="descricaoElo" readonly="true" style="background-color:#EFEFEF; border:0" size="39" maxlength="40" />
					</logic:notPresent>		
					
					<a href="javascript:limparElo();"> 
					  <img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
					</a>
				  </strong>
				</td>
			  </tr>
			  
			  <tr>
			    <td><strong>Localidade:</strong></td>
			    <td>
				  <strong>
					<html:text property="idLocalidade" size="5" maxlength="3"
					onfocus="javascript:habilitarSetorQuadra();"
					onkeyup="javascript:habilitarSetorQuadra();"
					onkeypress="validaEnterComMensagem(event, 'exibirGerarRelatorioRelacaoParcelamentoAction.do', 'idLocalidade', 'Localidade');" />
					<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do?indicadorUsoTodos=1', 'localidade', null, null, 275, 480, '');">
					  <img  border="0" src="imagens/pesquisa.gif" height="21" width="23"></a>
							
					<logic:present name="localidadeInexistente" scope="request">
					  <html:text property="descricaoLocalidade" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" size="39" maxlength="40" />
					</logic:present>
									
					<logic:notPresent name="localidadeInexistente" scope="request">
					  <html:text property="descricaoLocalidade" readonly="true" style="background-color:#EFEFEF; border:0" size="39" maxlength="40" />
					</logic:notPresent>		
					
					<a href="javascript:limparLocalidade();"> 
					  <img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
					</a>
				  </strong>
				</td>
			  </tr>
			  
			  <tr>
			    <td><strong>Setor Comercial:</strong></td>
			    <td>
				  <strong>
					<html:text property="idSetorComercial" size="5" maxlength="4" disabled="true" 
					onkeyup="return validaEnterComMensagem(event, 'exibirGerarRelatorioRelacaoParcelamentoAction.do', 'idSetorComercial', 'Setor Comercial');" 
					onchange="controleSetorComercial(this.value);"/>
					<a href="javascript:abrirPopupDependencia('exibirPesquisarSetorComercialAction.do?idLocalidade='+document.forms[0].idLocalidade.value+'&tipo=SetorComercial',document.forms[0].idLocalidade.value,'Localidade', 400, 800);">
					  <img  border="0" src="imagens/pesquisa.gif" height="21" width="23"></a>
							
					<logic:present name="setorComercialInexistente" scope="request">
					  <html:text property="descricaoSetorComercial" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" size="39" maxlength="40" />
					</logic:present>
									
					<logic:notPresent name="setorComercialInexistente" scope="request">
					  <html:text property="descricaoSetorComercial" readonly="true" style="background-color:#EFEFEF; border:0" size="39" maxlength="40" />
					</logic:notPresent>		
					
					<a href="javascript:limparSetorComercial();"> 
					  <img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
					</a>
				  </strong>
				</td>
			  </tr>
			
			  <tr>
			    <td><strong>Quadra:</strong></td>
				<td>
				  <html:text property="idQuadra" style="width: 65px;" size="4" maxlength="5" disabled="true"/>
				</td>
			  </tr>
				
			  <tr>
			    <td><strong>Situação:</strong></td>
			    <td>
				  <html:select property="idSituacaoParcelamento" onchange="javascript:verificarMotivoDesfazimento();">
				    <logic:notEmpty name="colecaoSituacaoParcelamento">				      
				      <html:options collection="colecaoSituacaoParcelamento" labelProperty="descricao" property="id" />
				    </logic:notEmpty>
				  </html:select>
			    </td>
			  </tr>
				
			  <tr>
			    <td><strong>Período de Parcelamento:</strong></td>
			    <td>
			      <strong> 
			        <html:text property="dataParcelamentoInicial" size="10" maxlength="10" onkeyup="mascaraData(this,event);replicaDataParcelamento();"/> 
			        <a href="javascript:abrirCalendario('GerarRelatorioRelacaoParcelamentoActionForm', 'dataParcelamentoInicial')">
				      <img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> a
			      </strong> 
			      
			      <html:text property="dataParcelamentoFinal" size="10" maxlength="10" onkeyup="mascaraData(this,event)"/> 
			      <a href="javascript:abrirCalendario('GerarRelatorioRelacaoParcelamentoActionForm', 'dataParcelamentoFinal')">
					<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> (dd/mm/aaaa)
			    </td>
			  </tr>	
			  <logic:present name="Desfeito" scope="request">	
			  <tr>
			    <td><strong>Motivo de Desfazimento:</strong></td>
			    <td>
				  <html:select property="idsMotivoDesfazimento" style="width: 350px; height:100px;" multiple="true">
				    <logic:notEmpty name="colecaoMotivoDesfazimento">
				      <html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				      <html:options collection="colecaoMotivoDesfazimento" labelProperty="descricaoParcelamentoMotivoDesfazer" property="id" />
				    </logic:notEmpty>  
	              </html:select>
	            </td>
			  </tr>			  
			 </logic:present>		
			 <logic:notPresent name="Desfeito" scope="request">
			 <tr>
			    <td><strong>Motivo de Desfazimento:</strong></td>
			    <td>
				  <html:select property="idsMotivoDesfazimento" disabled="true">
				    <logic:notEmpty name="colecaoMotivoDesfazimento">
				      <html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				      <html:options collection="colecaoMotivoDesfazimento" labelProperty="descricaoParcelamentoMotivoDesfazer" property="id" />
				    </logic:notEmpty>  
	              </html:select>
	            </td>
			  </tr>
			 </logic:notPresent>	  		
			  <tr>
			    <td><strong>Valor do Débito Atualizado:</strong></td>
			    <td>
		          <strong> 
			        <html:text property="valorDebitoInicial" size="10" maxlength="8"/> a 
			        <html:text property="valorDebitoFinal" size="10" maxlength="8"/> 
			      </strong>
			    </td>
			  </tr>
				
			  <tr>
			    <td><strong>Usuário Responsável:</strong></td>
			    <td>
				  <strong>
					
					<html:text property="idUsuarioResponsavel" size="5" maxlength="3" disabled="true" 
					onkeyup="return validaEnterComMensagem(event, 'exibirFiltrarRelacaoParcelamentoAction.do', 'idUsuarioResponsavel', 'Usuário Responsável');" />
					<a href="javascript:chamarPopup('exibirPesquisarUsuarioAction.do', 'idUsuarioResponsavel', null, null, 400, 800, '',document.forms[0].idUsuarioResponsavel,'idElo',document.forms[0].idElo.value);">
					  <img  border="0" src="imagens/pesquisa.gif" height="21" width="23"/></a>
					
					<logic:present name="usuarioResponsavelInexistente" scope="request">
					  <html:text property="descricaoUsuarioResponsavel" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" size="39" maxlength="40" />
					</logic:present>
					
					<logic:notPresent name="usuarioResponsavelInexistente" scope="request">
					  <html:text property="descricaoUsuarioResponsavel" readonly="true" style="background-color:#EFEFEF; border:0" size="39" maxlength="40" />
					</logic:notPresent>		

					<a href="javascript:limparUsuarioResponsavel();"> 
					  <img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
					</a>
				  </strong>
				</td>
			  </tr>
			
			  <tr>
			    <td>&nbsp;</td>
			    <td colspan="3" align="right">&nbsp;</td>
			  </tr>
			
			  <tr>
			    <td>&nbsp;</td>
			    <td colspan="3" align="right">
			      <div align="left">
			        <strong> </strong>
			      </div>
			    </td>
			  </tr>
			
			  <tr> 
                <td> 
                  <div align="left">
                    <input type="button" name="adicionar2" class="bottonRightCol" value="Limpar" onclick="javascript:limparForm();">
                  </div>
                </td>
                <td>&nbsp;</td>
                <td align="right">
						<input type="Button" 
							class="bottonRightCol" 
							value="Gerar" 
							onClick="javascript:toggleBox('demodiv',1);"/>
					</td>
              </tr>
              
		    </table>
		  
		    <p>&nbsp;</p>
		  </td>
	    </tr>
	  
	  </table>
    </td>
  </tr>

</table>
	  <jsp:include page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioRelacaoParcelamentoAction.do&left=500&top=450" />
	    <%@ include file="/jsp/util/rodape.jsp"%>

</html:form>
</body>
</html:html>
