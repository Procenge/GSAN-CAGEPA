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
<html:javascript staticJavascript="false"  formName="GerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm"/>

<SCRIPT LANGUAGE="JavaScript">
	function reload() {
		var form = document.forms[0];
		
		//if(form.tipoSolicitacao.selectedIndex == 0) {
		//	limparEspecificacao();
		//}
		
		form.action = '/gsan/exibirGerarRelatorioEstatisticoAtendimentoPorRacaCorAction.do';
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
		
	    if (tipoConsulta == 'unidadeOrganizacional') {
		      form.unidadeAtendimento.value = codigoRegistro;
		      form.dsUnidadeAtendimento.value = descricaoRegistro;
		      //form.action = 'exibirFiltrarEstatisticaAtendimentoAtendente.do';
		      //form.submit();
	    }
	    
	    if (tipoConsulta == 'localidade') {

			form.localidade.value = codigoRegistro;
		    form.dsLocalidade.value = descricaoRegistro;
		    form.dsLocalidade.style.color = "#000000";
	    
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
		
		form.tipoSolicitacao.selectedIndex = 0;	
		form.especificacao.selectedIndex = 0;
		limparEspecificacao();
		form.periodoAtendimentoInicial.value = "";
		form.periodoAtendimentoFinal.value = "";
		form.unidadeAtendimento.value = "";
		form.dsUnidadeAtendimento.value = "";
		
		window.location.href = '/gsan/exibirGerarRelatorioEstatisticoAtendimentoPorRacaCorAction.do?menu=sim';
	}
	
	function limparEspecificacao() {
		var form = document.forms[0];

		for(i=form.especificacao.length-1; i>0; i--) {
			form.especificacao.options[i] = null;
		}
	}

	function replicaDataAtendimento() {
		var form = document.forms[0];
		form.periodoAtendimentoFinal.value = form.periodoAtendimentoInicial.value
	}
	
	function validaForm(){
		var form = document.forms[0];
		if(validateGerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm(form)){
			enviarSelectMultiplo('GerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm','racaCorSelecionadas');
			toggleBox('demodiv',1);
		}
	}
	
	function limparUnidadeAtendimento(){
		var form = document.forms[0];
		
		form.dsUnidadeAtendimento.value = "";
		form.unidadeAtendimento.value = "";
	}
	
	function limparLocalidade(){
		var form = document.forms[0];
		
		form.dsLocalidade.value = "";
		form.localidade.value = "";
	}
</SCRIPT>


</head>

<body leftmargin="5" topmargin="5">

<html:form action="/gerarRelatorioEstatisticoAtendimentoPorRacaCorAction.do" 
		   name="GerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm" 
		   type="gcom.gui.atendimentopublico.ordemservico.GerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm"
		   method="post">

<html:hidden property="selectedTipoSolicitacaoSize" />

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
          <td class="parabg">Gerar Relatório Estatístico de Atendimento por Raça/Cor</td>
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
                <td><strong>Período de Atendimento:</strong></td>
                <td colspan="6"><span class="style2"><strong> 				
					<html:text property="periodoAtendimentoInicial" 
							size="11" 
							maxlength="10" 
							tabindex="13" 
							onkeyup="mascaraData(this, event);replicaDataAtendimento();"/>
						<a href="javascript:abrirCalendario('GerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm', 'periodoAtendimentoInicial');">
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
						<a href="javascript:abrirCalendario('GerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm', 'periodoAtendimentoFinal');">
						<img border="0" 
							src="<bean:message 
								key='caminho.imagens'/>calendario.gif" 
								width="16" 
								height="15" 
								border="0" 
								alt="Exibir Calendário" />
						</a>					
					</strong>(dd/mm/aaaa)<strong> 
                  </strong></span></td>
              </tr>
              
              <tr> 
                <td><strong>Unidade de Atendimento:</strong></td>
               	<td>
					<html:text maxlength="8" 
						tabindex="1"
						property="unidadeAtendimento" 
						size="6"
						onkeypress="validaEnterComMensagem(event, 'exibirGerarRelatorioEstatisticoAtendimentoPorRacaCorAction.do','unidadeAtendimento','Unidade Atendimento');"/> 
		
						
						<a href="javascript:setUnidade(1); chamarPopup('exibirPesquisarUnidadeOrganizacionalAction.do?tipoUnidade=unidadeAtendimento', 'unidadeAtendimento', null, null, 275, 480, '', document.forms[0].unidadeAtendimento);">
						<img width="23" 
							height="21" 
							border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar Unidade Atendimento" /></a> 
						
						<html:text property="dsUnidadeAtendimento" 
							size="40"
							maxlength="40" 
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000" />
	
					<a href="javascript:limparUnidadeAtendimento();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" 
						title="Apagar" />
					</a>
				</td>
              </tr>
              
              <tr>
				<td width="110">
					<strong>Raça/Cor:</strong>					</td>
				<td colspan="2">
				<table width="100%" border=0 cellpadding=0 cellspacing=0 align="left">
					<tr>
						<td width="175">
						
							<div align="left"><strong>Disponíveis</strong></div>

							<html:select property="racaCor" 
								size="6" 
								multiple="true" 
								style="width:190px">
							<logic:notEmpty name="colecaoRaca">	
								<html:options collection="colecaoRaca" 
									labelProperty="descricao" 
									property="id" />
							</logic:notEmpty>
							</html:select>
						</td>

						<td width="5" valign="center"><br>
							<table width="50" align="center">
								<tr>
									<td align="center">
										<input type="button" 
											class="bottonRightCol"
											onclick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('GerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm', 'racaCor', 'racaCorSelecionadas');"
											value=" &gt;&gt; ">
									</td>
								</tr>

								<tr>
									<td align="center">
										<input type="button" 
											class="bottonRightCol"
											onclick="javascript:MoverDadosSelectMenu1PARAMenu2('GerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm', 'racaCor', 'racaCorSelecionadas');"
											value=" &nbsp;&gt;  ">
									</td>
								</tr>

								<tr>
									<td align="center">
										<input type="button" 
											class="bottonRightCol"
											onclick="javascript:MoverDadosSelectMenu2PARAMenu1('GerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm', 'racaCor', 'racaCorSelecionadas');"
											value=" &nbsp;&lt;  ">
									</td>
								</tr>

								<tr>
									<td align="center">
										<input type="button" 
											class="bottonRightCol"
											onclick="javascript:MoverTodosDadosSelectMenu2PARAMenu1('GerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm', 'racaCor', 'racaCorSelecionadas');"
											value=" &lt;&lt; ">
									</td>
								</tr>
							</table>
						</td>

						<td>
							<div align="left">
								<strong>Selecionadas</strong>
							</div>
							
							<html:select property="racaCorSelecionadas" 
								size="6" 
								multiple="true" 
								style="width:190px">
							
							</html:select>
						</td>
					</tr>
				</table>
				</td>
			</tr>
              
              <tr> 
                <td><strong>Tipo de Solicitação:</strong></td>
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
                <td><strong>Especificação:</strong></td>
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
				<td><strong>Regional:</strong></td>
				<td><html:select property="gerenciaRegional" tabindex="1">
					<html:option
						value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
					<html:options collection="colecaoGerenciaRegional"
						labelProperty="nome" property="id" />
				</html:select></td>
			  </tr>
			  <tr>
					<td><strong>Localidade onde ocorreu o atendimento:</strong></td>
					<td colspan="2"><html:text maxlength="3"
						property="localidade" size="4" tabindex="3"
						onkeyup=""
						onkeypress="validaEnterComMensagem(event, 'exibirGerarRelatorioEstatisticoAtendimentoPorRacaCorAction.do','localidade','Localidade');" />
					<a
						href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do?indicadorUsoTodos=1');">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Localidade" /></a> 
						<html:text maxlength="40" property="dsLocalidade"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000"
							size="40" />
						<a href="javascript:limparLocalidade();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
              
              <tr> 
                <td height="24" colspan="7" bordercolor="#000000" class="style1"> 
                  <hr></td>
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
						<input type="Button" 
							class="bottonRightCol" 
							value="Gerar" 
							onClick="javascript:validaForm();"/>
					</td>
				</tr>      
      </table>
      <p>&nbsp;</p>
	</td>
  </tr>
</table>

<jsp:include page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioEstatisticoAtendimentoPorRacaCorAction.do&left=480&top=610" />
<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>


</body>
</html:html>