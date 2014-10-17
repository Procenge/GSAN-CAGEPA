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

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="GerarRelatorioEstatisticaAtendimentoPorAtendenteActionForm"/>

<SCRIPT LANGUAGE="JavaScript">
	function reload() {
		var form = document.forms[0];
		
		if(form.tipoSolicitacao.selectedIndex == 0) {
			limparEspecificacao();
		}
		
		form.action = '/gsan/exibirFiltrarEstatisticaAtendimentoAtendente.do';
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

	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	    var form = document.forms[0];
		
	    if (tipoConsulta == 'unidadeSuperior') {
		      form.unidadeSuperiorId.value = codigoRegistro;
		      form.unidadeSuperiorDescricao.value = descricaoRegistro;
		      form.action = 'exibirFiltrarEstatisticaAtendimentoAtendente.do';
		      form.submit();
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
	
	function limparForm(){
		var form = document.forms[0];
		
		form.situacao[0].checked = true;
		form.tipoSolicitacao.selectedIndex = 0;	
		form.especificacao.selectedIndex = 0;
		form.unidadeAtendimento.selectedIndex = 0;
		form.unidadeAtual.selectedIndex = 0;		
		limparEspecificacao();
		form.periodoAtendimentoInicial.value = "";
		form.periodoAtendimentoFinal.value = "";
		form.periodoEncerramentoInicial.value = "";
		form.periodoEncerramentoFinal.value = "";		
		limparUnidadeAtendimento();
		limparUnidadeAtual();
		limparUnidadeSuperior();
		
		window.location.href = '/gsan/exibirFiltrarEstatisticaAtendimentoAtendente.do?menu=sim';
	}
	
	function limparEspecificacao() {
		var form = document.forms[0];

		for(i=form.especificacao.length-1; i>0; i--) {
			form.especificacao.options[i] = null;
		}
	}
		
	function limparUnidadeAtendimento() {
		var form = document.forms[0];
		
		var form = document.forms[0];

		for(i=form.unidadeAtendimento.length-1; i>0; i--) {
			form.unidadeAtendimento.options[i] = null;
		}
	}
	
	function limparUnidadeAtual() {
		var form = document.forms[0];
		
		var form = document.forms[0];

		for(i=form.unidadeAtual.length-1; i>0; i--) {
			form.unidadeAtual.options[i] = null;
		}
	}
	
	function limparUnidadeSuperior() {
		var form = document.forms[0];
		
      	form.unidadeSuperiorId.value = '';
	    form.unidadeSuperiorDescricao.value = '';
	
		form.unidadeSuperiorId.focus();
	}

	function replicaDataAtendimento() {
		var form = document.forms[0];
		form.periodoAtendimentoFinal.value = form.periodoAtendimentoInicial.value
	}
	
	function replicaDataEncerramento() {
		var form = document.forms[0];
		form.periodoEncerramentoFinal.value = form.periodoEncerramentoInicial.value
	}
	
	function validaForm(){
		var form = document.forms[0];
		if(validateGerarRelatorioEstatisticaAtendimentoPorAtendenteActionForm(form)){

			toggleBox('demodiv',1);
		}
	}
</SCRIPT>


</head>

<body leftmargin="5" topmargin="5">

<html:form action="/gerarRelatorioEstatisticaAtendimentoAtendenteAction.do" 
		   name="GerarRelatorioEstatisticaAtendimentoPorAtendenteActionForm" 
		   type="gcom.gui.atendimentopublico.ordemservico.GerarRelatorioEstatisticaAtendimentoPorAtendenteActionForm"
		   method="post">

<html:hidden property="selectedTipoSolicitacaoSize" />
<html:hidden property="situacaoId"/>

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

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

      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
          <td class="parabg">Gerar Relat&oacute;rio Estat&iacute;stica de Atendimento por Atendente</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>

      <table width="100%" border="0">
			<tr> 
                <td height="24" colspan="7" bordercolor="#000000" class="style1">Para gerar o relat&oacute;rio, 
                	informe os dados abaixo:</td>
              </tr>
              <tr> 
                <td height="24" colspan="7" bordercolor="#000000" class="style1"> 
                  <hr></td>
              </tr>
              
              <tr> 
                <td><strong>Situa&ccedil;&atilde;o:</strong></td>
                <td colspan="6"><span class="style2"><strong> 
                  <label> </label>
				  <html:radio property="situacao" value="0" onclick="javascript: reload();" tabindex="1"/>
 				  Todos
                  <label> &nbsp;&nbsp;
				  <html:radio property="situacao" value="1" onclick="javascript: reload();" tabindex="2"/>
 				  Pendentes</label>
                  <label> &nbsp;&nbsp;
				  <html:radio property="situacao" value="2" onclick="javascript: reload();" tabindex="3"/>
				  Encerrados</label>
				  <label> &nbsp;&nbsp;
				  <html:radio property="situacao" value="501" onclick="javascript: reload();" tabindex="4"/>
				  Reiterados</label></strong></span></td>
              </tr>
              <tr>
	            <td>&nbsp;</td>
	            <td colspan="6"><span class="style2"><strong> 
                  <label> 
				  <html:radio property="situacao" value="3" onclick="javascript: reload();" tabindex="5"/>
				  Sem Local de Ocorr&ecirc;ncia</label>
				</strong></span></td>
			  </tr>
              
              <tr> 
                <td height="24" colspan="7" bordercolor="#000000" class="style1"> 
                  <hr></td>
              </tr>
              
              <tr> 
                <td><strong>Tipo de Solicita&ccedil;&atilde;o:</strong></td>
                <td colspan="6"><span class="style2"><strong>
					<html:select property="tipoSolicitacao" 
						style="width: 350px; height:100px;" 
						multiple="true" 
						onchange="javascript:reload();habilitaEspecificacao();" 
						tabindex="7">
						<html:option value="">&nbsp;</html:option>
						<logic:present name="colecaoTipoSolicitacao" scope="session">
							<html:options collection="colecaoTipoSolicitacao" labelProperty="descricao" property="id" />
						</logic:present>
					</html:select>                
                  </strong></span></td>
              </tr>
              
              <tr> 
                <td><strong>Especifica&ccedil;&atilde;o:</strong></td>
                <td colspan="6"><span class="style2"><strong> 
					<html:select property="especificacao" 
						style="width: 350px;" 
						multiple="true" 
						tabindex="8">
						<html:option value="">&nbsp;</html:option>
						<logic:present name="colecaoEspecificacao" scope="session">
							<html:options collection="colecaoEspecificacao" labelProperty="descricao" property="id" />
						</logic:present>
					</html:select>                
                  </strong></span>
                 </td>
              </tr>
              
              <tr> 
                <td height="24" colspan="7" bordercolor="#000000" class="style1"> 
                  <hr></td>
              </tr>
              
              <tr> 
                <td><strong>Unidade de Atendimento:</strong></td>
               <td colspan="6">
               		<span class="style2"><strong> 
					<html:select property="unidadeAtendimento" 
						style="width: 350px;" 
						multiple="true" 
						tabindex="17">
						<html:option value="">&nbsp;</html:option>
						<logic:present name="colecaoUnidadeAtendimento" scope="session">
							<html:options collection="colecaoUnidadeAtendimento" labelProperty="descricao" property="id" />
						</logic:present>
					</html:select>                
                  	</strong></span>
                 </td>
              </tr>
              
              <tr> 
                <td><strong> Unidade Atual:</strong></td>
                <td colspan="6"><span class="style2"><strong> 
					<html:select property="unidadeAtual" 
						style="width: 350px;" 
						multiple="true" 
						tabindex="18">
						<html:option value="">&nbsp;</html:option>
						<logic:present name="colecaoUnidadeAtual" scope="session">
							<html:options collection="colecaoUnidadeAtual" labelProperty="descricao" property="id" />
						</logic:present>
					</html:select>                
                  </strong></span>
                 </td>
              </tr>
              
              <tr> 
                <td><strong> Unidade Superior:</strong></td>
                <td colspan="6" align="right">
                	<div align="left">
	                	<span class="style2"><strong> 
						<html:text property="unidadeSuperiorId" 
							size="6" 
							maxlength="8"
							tabindex="19"
							onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarEstatisticaAtendimentoAtendente.do', 'unidadeSuperiorId','Unidade Superior');"/>
						
						<a href="javascript:setUnidade(3); chamarPopup('exibirPesquisarUnidadeSuperiorAction.do', 'unidadeOrganizacional', null, null, 275, 480, '',document.forms[0].unidadeSuperiorId);">
						<img src="<bean:message 
									key='caminho.imagens'/>pesquisa.gif" 
									width="23"	
									height="21" 
									name="imagem" 
									alt="Pesquisar" 
									border="0">
						</a>
	
						<logic:present name="unidadeSuperiorEncontrada" scope="session">
							<html:text property="unidadeSuperiorDescricao" 
								readonly="true"
							  	style="background-color:#EFEFEF; border:0; color: #000000" 
							  	size="40" 
							  	maxlength="40" />
						</logic:present> 
	
						<logic:notPresent name="unidadeSuperiorEncontrada" scope="session">
							<html:text property="unidadeSuperiorDescricao" readonly="true"
									   style="background-color:#EFEFEF; border:0; color: #ff0000" size="40" maxlength="40" />
						</logic:notPresent>
	
						<a href="javascript:limparUnidadeSuperior();">
							<img border="0" 
								title="Apagar" 
								src="<bean:message 
								key='caminho.imagens'/>limparcampo.gif" /></a>
	                    </strong></span>
                    </div>
                 </td>
              </tr>
              
              <tr> 
                <td height="24" colspan="7" bordercolor="#000000" class="style1"> 
                  <hr></td>
              </tr>
             
              <tr> 
                <td><strong>Per&iacute;odo de Atendimento:</strong></td>
                <td colspan="6"><span class="style2"><strong> 
					<logic:equal name="GerarRelatorioEstatisticaAtendimentoPorAtendenteActionForm" property="situacao" value="2">
						<html:text property="periodoAtendimentoInicial" 
							size="11" 
							maxlength="10" 
							tabindex="11" 
							onkeyup="mascaraData(this, event)" 
							readonly="true" 
							value=""/>
						<img border="0" 
							src="<bean:message 
									key='caminho.imagens'/>calendario.gif" 
									width="16" 
									height="15" 
									border="0" 
									alt="Exibir Calendário"/>
						a <html:text property="periodoAtendimentoFinal" 
								size="11" 
								maxlength="10" 
								tabindex="12" 
								onkeyup="mascaraData(this, event)"  
								readonly="true" 
								value=""/>
						<img border="0" 
							src="<bean:message 
									key='caminho.imagens'/>calendario.gif" 
									width="16" 
									height="15" 
									border="0" 
									alt="Exibir Calendário"/>
					</logic:equal>
					
					<logic:notEqual name="GerarRelatorioEstatisticaAtendimentoPorAtendenteActionForm" property="situacao" value="2">
						<html:text property="periodoAtendimentoInicial" 
							size="11" 
							maxlength="10" 
							tabindex="13" 
							onkeyup="mascaraData(this, event);replicaDataAtendimento();"/>
						<a href="javascript:abrirCalendario('GerarRelatorioEstatisticaAtendimentoPorAtendenteActionForm', 'periodoAtendimentoInicial');">
						<img border="0" 
							src="<bean:message 
									key='caminho.imagens'/>calendario.gif" 
									width="16" 
									height="15" 
									border="0" 
									alt="Exibir Calendário" />
						</a>
						a <html:text property="periodoAtendimentoFinal" 
							size="11" 
							maxlength="10" 
							tabindex="14" 
							onkeyup="mascaraData(this, event)"/>
						<a href="javascript:abrirCalendario('GerarRelatorioEstatisticaAtendimentoPorAtendenteActionForm', 'periodoAtendimentoFinal');">
						<img border="0" 
							src="<bean:message 
								key='caminho.imagens'/>calendario.gif" 
								width="16" 
								height="15" 
								border="0" 
								alt="Exibir Calendário" />
						</a>
					</logic:notEqual>
					</strong>(dd/mm/aaaa)<strong> 
                  </strong></span></td>
              </tr>
              
              <tr> 
                <td><strong>Per&iacute;odo de Encerramento:</strong></td>
                <td colspan="6"><span class="style2"><strong> 
					<logic:equal name="GerarRelatorioEstatisticaAtendimentoPorAtendenteActionForm" property="situacao" value="1">
						<html:text property="periodoEncerramentoInicial" 
							size="11" 
							maxlength="10" 
							tabindex="15" 
							onkeyup="mascaraData(this, event)"  
							readonly="true" 
							value=""/>
						<img border="0" 
							src="<bean:message 
									key='caminho.imagens'/>calendario.gif" 
									width="16" 
									height="15" 
									border="0" 
									alt="Exibir Calendário" />
						a <html:text property="periodoEncerramentoFinal" 
							size="11" 
							maxlength="10" 
							tabindex="16" 
							onkeyup="mascaraData(this, event)"  
							readonly="true" 
							value=""/>
						<img border="0" 
							src="<bean:message 
								key='caminho.imagens'/>calendario.gif" 
								width="16" 
								height="15" 
								border="0" 
								alt="Exibir Calendário" />
					</logic:equal>
					
					<logic:notEqual name="GerarRelatorioEstatisticaAtendimentoPorAtendenteActionForm" property="situacao" value="1">
						<html:text property="periodoEncerramentoInicial" 
							size="11" 
							maxlength="10" 
							tabindex="15" 
							onkeyup="mascaraData(this, event);replicaDataEncerramento();"/>
						<a href="javascript:abrirCalendario('GerarRelatorioEstatisticaAtendimentoPorAtendenteActionForm', 'periodoEncerramentoInicial');">
						<img border="0" 
							src="<bean:message 
									key='caminho.imagens'/>calendario.gif" 
									width="16" 
									height="15" 
									border="0" 
									alt="Exibir Calendário" />
						</a>
						a <html:text property="periodoEncerramentoFinal" 
							size="11" 
							maxlength="10" 
							tabindex="16" 
							onkeyup="mascaraData(this, event)"/>
						<a href="javascript:abrirCalendario('GerarRelatorioEstatisticaAtendimentoPorAtendenteActionForm', 'periodoEncerramentoFinal');">
						<img border="0" 
							src="<bean:message 
									key='caminho.imagens'/>calendario.gif" 
									width="16" 
									height="15" 
									border="0" 
									alt="Exibir Calendário" />
						</a>
					</logic:notEqual>
					</strong>(dd/mm/aaaa)<strong> 
                  </strong></span></td>
              </tr>
              
               <tr> 
                <td>&nbsp;</td>
                <td colspan="6" align="right">&nbsp;</td>
              </tr>
              
              <tr> 
                <td>&nbsp;</td>
                <td colspan="6"> <div align="right"> </div></td>
              </tr>
    
              <tr>
					<td height="24" >
			          	<input type="button"
			          		tabindex="21" 
			          		class="bottonRightCol" 
			          		value="Limpar" 
			          		onclick="javascript:limparForm();"/>
					</td>
				
					<td align="right">
						<input type="button" 
							name="Button" 
							tabindex="20"
							class="bottonRightCol" 
							value="Gerar" 
							onClick="javascript:validaForm();" />
					</td>
				</tr>      
      </table>
      <p>&nbsp;</p>
	</td>
  </tr>
</table>

<jsp:include page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioEstatisticaAtendimentoAtendenteAction.do&left=480&top=610" />
<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>


</body>
</html:html>