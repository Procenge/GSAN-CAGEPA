<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="FiltrarRegistroAtendimentoActionForm"/>

<SCRIPT LANGUAGE="JavaScript">
	function reload() {
		var form = document.forms[0];
		
		if(form.tipoSolicitacao.selectedIndex == 0) {
			limparEspecificacao();
		}
		
		form.action = '/gsan/exibirGerarRelatorioEstatisticoRegistroAtendimentoAction.do';
		form.submit();
	}

	function habilitaEspecificacao() {
		var form = document.forms[0];
		if (form.selectedTipoSolicitacaoSize.value == '1') {
			form.especificacao.disabled = false;	
		} else {
			form.especificacao.disabled = true;	
		}
	}
	/* Chama Popup */ 
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){
		if(objetoRelacionado.disabled != true){
			if (objeto == null || codigoObjeto == null){
				abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
			} else{
				if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
					alert(msg);
				} else{
					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
				}
			}
		}
	}
	var unidade = 0;
	
	function setUnidade(tipo) {
		unidade = tipo;
	}
	/* Recuperar Popup */
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	    var form = document.forms[0];
		
		//form.action='exibirFiltrarRegistroAtendimentoAction.do';
	    if (tipoConsulta == 'usuario') {
  	      	form.loginUsuario.value = codigoRegistro;
	      	form.nomeUsuario.value = descricaoRegistro;
	      	form.nomeUsuario.style.color = '#000000';
	    } else if (tipoConsulta == 'unidadeSuperior') {
            form.unidadeSuperiorId.value = codigoRegistro;
            form.unidadeSuperiorDescricao.value = descricaoRegistro;
            form.unidadeSuperiorDescricao.style.color = '#000000';
            limparUnidadeAtendimento();
 	    } else if (tipoConsulta == 'unidadeOrganizacional') {
 	    	if(unidade == 1) {
			    form.unidadeAtendimentoId.value = codigoRegistro;
			    form.unidadeAtendimentoDescricao.value = descricaoRegistro;
				form.unidadeAtendimentoDescricao.style.color = '#000000';	
				limparUnidadeSuperior();	      
			} else if(unidade == 2) {
			    form.unidadeAtualId.value = codigoRegistro;
			    form.unidadeAtualDescricao.value = descricaoRegistro;
				form.unidadeAtualDescricao.style.color = '#000000';		      				
			}
 	    }
	     
	}
	
    function limparPeriodoAtendimento() {
        var form = document.forms[0];
        
        if (form.periodoAtendimentoInicial.value != ''){
			form.periodoAtendimentoInicial.value="";
		}
		
		if (form.periodoAtendimentoFinal.value != ''){
			form.periodoAtendimentoFinal.value="";
		}			
  	}
	
	/* Limpar Form */
	function limparForm(){
		var form = document.forms[0];
		
		limparUnidadeSuperior();
		limparUnidadeAtendimento();
		limparUsuario();
		form.tipoSolicitacao.selectedIndex = 0;	
		form.especificacao.selectedIndex = 0;		
		limparEspecificacao();
		limparUnidadeAtual();
		form.periodoAtendimentoInicial.value = "";
		form.periodoAtendimentoFinal.value = "";
		
		window.location.href = '/gsan/exibirGerarRelatorioEstatisticoRegistroAtendimentoAction.do?menu=sim';
	}
	/* Clear Especificação */
	function limparEspecificacao() {
		var form = document.forms[0];

		for(i=form.especificacao.length-1; i>0; i--) {
			form.especificacao.options[i] = null;
		}
	}
	
	/* Limpar Unidade Atendimento */
	function limparUnidadeAtendimento() {
		var form = document.forms[0];
		
      	form.unidadeAtendimentoId.value = '';
	    form.unidadeAtendimentoDescricao.value = '';
	}

	/* Limpar Usuário */
	function limparUsuario() {
		var form = document.forms[0];
		
      	form.loginUsuario.value = "";
	    form.nomeUsuario.value = "";
	
		form.loginUsuario.focus();
	}	

	/* Limpar Unidade Atual */
	function limparUnidadeAtual() {
		var form = document.forms[0];
		
      	form.unidadeAtualId.value = '';
	    form.unidadeAtualDescricao.value = '';
	
		form.unidadeAtualId.focus();
	}

	/* Limpar Unidade Superior */
	function limparUnidadeSuperior() {
		var form = document.forms[0];
		
      	form.unidadeSuperiorId.value = '';
	    form.unidadeSuperiorDescricao.value = '';
	}

	/* Replica Data de Atendimento */
	function replicaDataAtendimento() {
		var form = document.forms[0];
		form.periodoAtendimentoFinal.value = form.periodoAtendimentoInicial.value
	}

	function validaPeriodoAtendimento() {
		var form = document.forms[0];

	}
	
	function existeItensSelecionados(campo) {
		for(i = 1; i < campo.options.length; i++) {
			if(campo.options[i].selected) {
				return true;
			}
		}
		
		return false;
	}

	function existeOutroCampoPreenchido() {

		var form = document.forms[0];
		
		return (form.unidadeSuperiorId.value != "") ||
		       (form.unidadeAtendimentoId.value != "") ||
		       (form.loginUsuario.value != "") ||		       
		       ((form.tipoSolicitacao.selectedIndex > 0) || (existeItensSelecionados(form.tipoSolicitacao))) ||
		       (form.especificacao.selectedIndex > 0) ||
		       (form.unidadeAtualId.value != "");
	}

	function validaForm(){

		var form = document.forms[0];
		var msg = '';
		var dataValida = true;
		
        if((form.unidadeAtendimentoId.value != "") && (form.unidadeSuperiorId.value != "")) {
			msg = 'Informe a Unidade Superior ou a Unidade de Atendimento. Você não deve informar as duas Unidades.';
		}

		if(((form.periodoAtendimentoInicial.value == "") || (form.periodoAtendimentoFinal.value == "")) 
			|| (!existeOutroCampoPreenchido())) {
			
			msg = "Informe o período de atendimento,a situação e outra opção de seleção";
		} else {

		if(verificaData(form.periodoAtendimentoInicial)) {

			if(verificaData(form.periodoAtendimentoFinal)) {

				if(!comparaDatas(form.periodoAtendimentoInicial.value, '<', form.periodoAtendimentoFinal.value)) {
					msg = "Data Final do Período é anterior à Data Inicial do Período";
				}
				
				if(subtrairDatas(form.periodoAtendimentoFinal.value, form.periodoAtendimentoInicial.value) > 30) {
					msg = "O período de atendimento não pode ser superior a 30 dias."
				}

			} else {
				dataValida = false;
			}
		} else {
			dataValida = false;
		}
	}
		
		if(msg != '') {
			alert(msg);
			return;
		}

		if(dataValida) {
			toggleBox('demodiv',1);
		}
	}

</SCRIPT>


</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/gerarRelatorioEstatisticoRegistroAtendimentoAction.do" 
		   name="GerarRelatorioEstatisticoRegistroAtendimentoActionForm" 
		   type="gcom.gui.relatorio.atendimentopublico.registroatendimento.GerarRelatorioEstatisticoRegistroAtendimentoActionForm"
		   method="post">

<html:hidden property="selectedTipoSolicitacaoSize" />

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
          <td class="parabg">Gerar Relatório Estatístico de RA - Registro de Atendimento</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>

      <table width="100%" border="0">
			<tr> 
                <td height="24" colspan="7" bordercolor="#000000" class="style1">Para 
                  gerar o Relatório Estatístico de RA - Registro de Atendimento, informe os dados abaixo:</td>
              </tr>
              <tr> 
                <td height="24" colspan="7" bordercolor="#000000" class="style1"> 
                  <hr></td>
              </tr>

              <!-- Unidade Superior -->

              <tr> 
                <td><strong> Unidade Superior:</strong></td>
                <td colspan="6" align="right"><div align="left"><span class="style2"><strong> 
					<html:text property="unidadeSuperiorId" size="6" maxlength="8"
							   onkeypress="javascript:limparUnidadeAtendimento();validaEnterComMensagem(event, 'exibirGerarRelatorioEstatisticoRegistroAtendimentoAction.do?validaUnidadeSuperior=true', 'unidadeSuperiorId','Unidade Superior');"/>
					
					<a href="javascript:chamarPopup('exibirPesquisarUnidadeSuperiorAction.do', 'unidadeSuperior', null, null, 275, 480, '',document.forms[0].unidadeSuperiorId);">
					<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23"	height="21" name="imagem" alt="Pesquisar" border="0"></a>

					<logic:present name="unidadeSuperiorEncontrada" scope="session">
						<html:text property="unidadeSuperiorDescricao" readonly="true"
								   style="background-color:#EFEFEF; border:0; color: #000000" size="40" maxlength="40" />
					</logic:present> 

					<logic:notPresent name="unidadeSuperiorEncontrada" scope="session">
						<html:text property="unidadeSuperiorDescricao" readonly="true"
								   style="background-color:#EFEFEF; border:0; color: #ff0000" size="40" maxlength="40" />
					</logic:notPresent>

					<a href="javascript:limparUnidadeSuperior();form.unidadeSuperiorId.focus();">
						<img border="0" title="Apagar" src="<bean:message key='caminho.imagens'/>limparcampo.gif" /></a>
                    </strong></span></div></td>
              </tr>

              <!-- Unidade de Atendimento -->

              <tr> 
                <td><strong>Unidade de Atendimento:</strong></td>
                <td colspan="6"><span class="style2"><strong>
					<html:text property="unidadeAtendimentoId" size="6" maxlength="8"
							   onkeypress="javascript:limparUnidadeSuperior();validaEnterComMensagem(event, 'exibirGerarRelatorioEstatisticoRegistroAtendimentoAction.do?validaUnidadeAtendimento=true', 'unidadeAtendimentoId','Unidade Atendimento');" tabindex="28"/>
					
					<a href="javascript:chamarPopup('exibirPesquisarUnidadeOrganizacionalAction.do', 'unidadeOrganizacional', null, null, 275, 480, '',document.forms[0].unidadeAtendimentoId);setUnidade(1);">
					<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23"	height="21" name="imagem" alt="Pesquisar" border="0"></a>
						 
					<logic:present name="unidadeAtendimentoEncontrada" scope="session">
						<html:text property="unidadeAtendimentoDescricao" readonly="true"
								   style="background-color:#EFEFEF; border:0; color: #000000" size="40" maxlength="40" />
					</logic:present> 

					<logic:notPresent name="unidadeAtendimentoEncontrada" scope="session">
						<html:text property="unidadeAtendimentoDescricao" readonly="true"
								   style="background-color:#EFEFEF; border:0; color: #ff0000" size="40" maxlength="40" />
					</logic:notPresent>
					
					<a href="javascript:limparUnidadeAtendimento();form.unidadeAtendimentoId.focus();">
						<img border="0" title="Apagar" src="<bean:message key='caminho.imagens'/>limparcampo.gif" /></a>
                  </strong></span></td>
              </tr>

              <!-- Login do Usuário -->

              <tr> 
                <td><strong>Login do usuário:</strong></td>
                <td colspan="6"><span class="style2"><strong>
					<html:text property="loginUsuario" 
						size="12" 
						maxlength="11"
						style="text-transform: none;"
						onkeypress="javascript:pesquisaEnterSemUpperCase(event, 'exibirGerarRelatorioEstatisticoRegistroAtendimentoAction.do', 'loginUsuario');" tabindex="7"/>
					
					<a href="javascript:chamarPopup('exibirUsuarioPesquisar.do?mostrarLogin=S&limpaForm=sim', 'usuario', null, null, 370, 600, '',document.forms[0].loginUsuario);">
						<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23"	height="21" name="imagem" alt="Pesquisar" border="0"></a>
						 
					<logic:present name="usuarioEncontrado" scope="session">
						<html:text property="nomeUsuario" 
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000" 
							size="40" 
							maxlength="40" />
							
					</logic:present> 

					<logic:notPresent name="usuarioEncontrado" scope="session">
						<html:text property="nomeUsuario" 
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000" 
							size="40" 
							maxlength="40" />
					</logic:notPresent>
					
					<a href="javascript:limparUsuario();">
						<img border="0" title="Apagar" src="<bean:message key='caminho.imagens'/>limparcampo.gif" />
					</a>
                  </strong></span></td>
              </tr>
             
             <!-- Situação -->
 			<tr> 
                <td><strong>Situa&ccedil;&atilde;o:</strong></td>
                <td colspan="6"><span class="style2"><strong> 
                  <label> </label>
				  <html:radio   property="situacao" value="0" onclick="javascript: "  tabindex="1"/>
 				  Todos
                  <label> &nbsp;&nbsp;
				  <html:radio property="situacao" value="1" onclick="javascript: " tabindex="2"/>
 				  Pendentes</label>
                  <label> &nbsp;&nbsp;
				  <html:radio property="situacao" value="2" onclick="javascript: " tabindex="3"/>
				  Encerrados</label>
				  <label> &nbsp;&nbsp;
				  <html:radio property="situacao" value="3" onclick="javascript: " tabindex="4"/>
				  Reiterados</label></strong></span></td>
              </tr>
			<!-- Situação -->
 			 <tr> 
                <td height="24" colspan="7" bordercolor="#000000" class="style1"> 
                  <hr></td>
              </tr>


              <!-- Tipo de Solicitação -->
				
              <tr> 
                <td><strong>Tipo de Solicita&ccedil;&atilde;o:</strong></td>
                <td colspan="6"><span class="style2"><strong>
					<html:select property="tipoSolicitacao" style="width: 350px; height:100px;" multiple="true" onchange="javascript:reload();habilitaEspecificacao();" tabindex="12">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoTipoSolicitacao" scope="session">
							<html:options collection="colecaoTipoSolicitacao" labelProperty="descricao" property="id" />
						</logic:present>
					</html:select>                
                  </strong></span></td>
              </tr>

              <!-- Especificação -->

              <tr> 
                <td><strong>Especifica&ccedil;&atilde;o:</strong></td>
                <td colspan="6"><span class="style2"><strong> 
					<html:select property="especificacao" style="width: 350px;" multiple="true" tabindex="13">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoEspecificacao" scope="session">
							<html:options collection="colecaoEspecificacao" labelProperty="descricao" property="id" />
						</logic:present>
					</html:select>                
                  </strong></span></td>
              </tr>
              <tr> 
                <td height="24" colspan="7" bordercolor="#000000" class="style1"> 
                  <hr></td>
              </tr>
              
              
              <!-- Unidade Atual -->

              <tr> 
                <td><strong> Unidade Atual:</strong></td>
                <td colspan="6" align="right"><div align="left"><span class="style2"><strong> 
					<html:text property="unidadeAtualId" size="6" maxlength="8"
							   onkeypress="javascript:validaEnterComMensagem(event, 'exibirGerarRelatorioEstatisticoRegistroAtendimentoAction.do?validaUnidadeAtual=true', 'unidadeAtualId','Unidade Atual');" tabindex="29"/>
					
					<a href="javascript:chamarPopup('exibirPesquisarUnidadeOrganizacionalAction.do', 'unidadeOrganizacional', null, null, 275, 480, '',document.forms[0].unidadeAtualId);setUnidade(2);">
						<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23"	height="21" name="imagem" alt="Pesquisar" border="0"></a>

					<logic:present name="unidadeAtualEncontrada" scope="session">
						<html:text property="unidadeAtualDescricao" readonly="true"
								   style="background-color:#EFEFEF; border:0; color: #000000" size="40" maxlength="40" />
					</logic:present> 

					<logic:notPresent name="unidadeAtualEncontrada" scope="session">
						<html:text property="unidadeAtualDescricao" readonly="true"
								   style="background-color:#EFEFEF; border:0; color: #ff0000" size="40" maxlength="40" />
					</logic:notPresent>

					<a href="javascript:limparUnidadeAtual();">
						<img border="0" title="Apagar" src="<bean:message key='caminho.imagens'/>limparcampo.gif" /></a>
                    </strong></span></div></td>
              </tr>
                         

              <!-- Período de Atendimento -->

              <tr> 
                <td><strong>Per&iacute;odo de Atendimento:</strong></td>
                <td colspan="6"><span class="style2"><strong> 
						<html:text property="periodoAtendimentoInicial" size="11" maxlength="10" tabindex="22" onkeyup="mascaraData(this, event);replicaDataAtendimento();"/>
						<a href="javascript:abrirCalendario('GerarRelatorioEstatisticoRegistroAtendimentoActionForm', 'periodoAtendimentoInicial');">
						<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário" /></a>
						a <html:text property="periodoAtendimentoFinal" size="11" maxlength="10" tabindex="23" onkeyup="mascaraData(this, event)"/>
						<a href="javascript:abrirCalendario('GerarRelatorioEstatisticoRegistroAtendimentoActionForm', 'periodoAtendimentoFinal');">
						<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário" /></a>
					</strong>(dd/mm/aaaa)<strong> 
                  </strong></span></td>
              </tr>              

              <!-- Botões -->

              <tr> 
                <td>&nbsp;</td>
                <td colspan="6" align="right">&nbsp;</td>
              </tr>
              <tr> 
                <td>&nbsp;</td>
                <td colspan="6"> <div align="right"> </div></td>
              </tr>
              <tr> 
                <td><input type="button" name="adicionar2" class="bottonRightCol" value="Limpar" tabindex="35" onclick="javascript:limparForm();"></td>
                <td colspan="6"> <div align="right"> 
                    <input type="button" tabindex="34" name="Submit" class="bottonRightCol" value="Gerar" onclick="validaForm();">
                  </div></td>
              </tr>      
      </table>
      <p>&nbsp;</p>
	</td>
  </tr>
</table>

<jsp:include page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioEstatisticoRegistroAtendimentoAction.do&left=500&top=400"/>

<%@ include file="/jsp/util/rodape.jsp"%>

<!-- relatorio_estatistico_registro_atendimento.jsp -->

</html:form>


</body>
</html:html>