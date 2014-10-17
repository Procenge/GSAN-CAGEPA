<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="gcom.util.ConstantesSistema" isELIgnored="false"%>
<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false" formName="FiltrarCobrancaRemuneracaoPorAcaoRelatorioActionForm"  dynamicJavascript="true" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">

	function validaForm(form){

		var campo = form.comando;
		var aux;
		for(var i = 0; i < campo.length; i++){
			if(campo[i].checked){
				aux = campo[i].value;
				}
			}
		
		if (aux == undefined){
			
			if (validarDatas()){
				submeterFormPadrao(form);
			}
			
		} else {
			if (aux == "3") {

				if (validarDatas()){
					submeterFormPadrao(form);
				}
				
			} else {
				submeterFormPadrao(form);
			}
		}
	
	}

	function limparForm(form) {
		form.comando[0].checked = false;
		form.comando[1].checked = false;
	    form.acao.value="";
	    form.empresa.value = "";
	    form.periodoInicial.value="";
	    form.periodoFim.value="";
	}

	function validarDatas(){

		var form = document.forms[0];
		
		var periodoInicial = form.periodoInicial.value;
		var periodoFim = form.periodoFim.value;

		if (isBrancoOuNulo(periodoInicial) && isBrancoOuNulo(periodoFim)){
			alert("Informe:\no Período");
			return false;
		} else if (isBrancoOuNulo(periodoInicial)){
			alert("Informe:\nPeríodo Inicial");
			return false;
		} else if (isBrancoOuNulo(periodoFim)){
			alert("Informe:\nPeríodo Final");
			return false;
		}

		return true;

	}

	function limparTitulo(){
		var form = document.forms[0];
		form.idCobrancaAcaoAtividadeComando.value = '';
		form.tituloCobrancaAcaoAtividadeComando.value = '';
	}
	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	    var form = document.forms[0];
	    
	    if (tipoConsulta == 'cobrancaAcaoAtividadeComando') {
	      form.idCobrancaAcaoAtividadeComando.value = codigoRegistro;
	      form.tituloCobrancaAcaoAtividadeComando.value = descricaoRegistro;
	    }
	    if (tipoConsulta == 'cobrancaAcaoAtividadeCronograma'){
			form.idCobrancaAcaoAtividadeCronograma.value = codigoRegistro;
			form.descricaoGrupo.value = descricaoRegistro;
	    }
	    if (tipoConsulta == 'localidade') {
	        limparPesquisaLocalidade()
	        form.localidade.value = codigoRegistro;
	        form.localidadeDescricao.value = descricaoRegistro;
	        form.localidadeDescricao.style.color = "#000000";
	        form.setorComercial.focus();
	    }	    
	}

	function limparPesquisaDescricaoLocalidade() {
	    var form = document.forms[0];
	    form.localidadeDescricao.value = "";
	}

	function limparPesquisaLocalidade() {
	    var form = document.forms[0];

	    form.localidade.value = "";
	    form.localidadeDescricao.value = "";
	}
	
	function recarregaForm(){
		recarregaFormBairro();
		recarregaFormSetorComercial();
		
		redirecionarSubmit('exibirFiltrarRelatorioAcompanhamentoExecucaoServicoCobrancaAction.do?menu=sim');
	}

	function recarregaFormBairro(){
		var setores = document.forms[0].setorComercial;

		if(setores != null){
			for(i = 0; setores.length > i; i++){
				var campo = setores[i];
				if(campo.selected){
					campo.selected = false;
				}
			}
		}
	}
	
	function recarregaFormSetorComercial(){
		var bairros = document.forms[0].bairro;

		if(bairros != null){
			for(i = 0; bairros.length > i; i++){
				var campo = bairros[i];
				if(campo.selected){
					campo.selected = false;
				}

			}
		}
	}

	function verificaTipoComando(){

		var form = document.forms[0];
		var campo = form.comando;
		var aux;
		for(var i = 0; i < campo.length; i++){
			if(campo[i].checked){
				aux = campo[i].value;
				}
			}
		
		if (aux == undefined){
			habilitarCampos();
		} else {
			if (aux == "3") {
				habilitarCampos();
			} else {
				desabilitarCampos();
			}
		}
	}

	function desabilitarCampos(){

		var form = document.forms[0];
		
		form.periodoInicial.disabled = true;
		form.periodoFim.disabled = true;
		document.getElementById('linkPeriodoInicial').href = '#';
		document.getElementById('linkPeriodoFim').href = '#';
		
		//$("input[type=text][name=periodoInicial]").attr('disabled', true);
		//$("input[type=text][name=periodoFim]").attr('disabled', true);
		//$("#linkPeriodoInicial").attr('href', "#");
		//$("#linkPeriodoFim").attr('href', "#");
	}

	function habilitarCampos(){
		
		var form = document.forms[0];

		form.periodoInicial.disabled = false;
		form.periodoFim.disabled = false;
		document.getElementById('linkPeriodoInicial').href = 'javascript:abrirCalendario("GerarRelatorioAcompanhamentoExecucaoServicoCobrancaActionForm", "periodoInicial")';
		document.getElementById('linkPeriodoFim').href = 'javascript:abrirCalendario("GerarRelatorioAcompanhamentoExecucaoServicoCobrancaActionForm", "periodoFim")';
		
		//$("input[type=text][name=periodoInicial]").attr('disabled', false);
		//$("input[type=text][name=periodoFim]").attr('disabled', false);
		//$("#linkPeriodoInicial").attr("href", "javascript:abrirCalendario('GerarRelatorioAcompanhamentoExecucaoServicoCobrancaActionForm', 'periodoInicial')");
		//$("#linkPeriodoFim").attr("href", "javascript:abrirCalendario('GerarRelatorioAcompanhamentoExecucaoServicoCobrancaActionForm', 'periodoFim')");
	}

	
</script>


</head>

<body leftmargin="5" topmargin="5" onload="verificaTipoComando();">
<html:form action="/gerarRelatorioAcompanhamentoExecucaoServicoCobrancaAction"
	name="GerarRelatorioAcompanhamentoExecucaoServicoCobrancaActionForm"
	type="gcom.gui.cobranca.GerarRelatorioAcompanhamentoExecucaoServicoCobrancaActionForm"
	method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

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
			
			<td width="615" valign="top" class="centercoltext">
				
				<table height="100%">
					<tr>
						<td></td>
					</tr>
				</table>

				<!--Início Tabela Reference a Páginação da Tela de Processo-->
				
				<table>
					<tr>
						<td></td>
					</tr>
				</table>
				
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
						<td class="parabg">Gerar Relatório Acompanhamento de Execução de Serviços da Cobrança</td>
						<td width="11" valign="top"><img border="0" src="imagens/parahead_right.gif" /></td>
					</tr>
	
				</table>
				
				<!--Fim Tabela Reference a Páginação da Tela de Processo-->
				<p>&nbsp;</p>
				<table width="100%" border="0">
					<tr>
						<td width="100%" colspan="3">
							<table width="100%" border="0">
								<tr>
									<td>Para filtrar os dados do Relatório de Acompanhamento da Execução de Servi&ccedil;os da Cobran&ccedil;a, informe os dados abaixo:</td>
								</tr>
							</table>						</td>
					</tr>
					<tr>
						<td><strong>Tipo de Comando<font color="red">  </font>:</strong></td>
						<td colspan="2">
							<span class="style2"> 					
								<html:radio property="comando" tabindex="1" value="1" onclick="recarregaForm();"/><strong>Cronograma</strong>
								<html:radio property="comando" tabindex="2" value="2" onclick="recarregaForm();"/><strong>Eventual</strong>
                                    <html:radio property="comando" tabindex="3" value="3" onclick="recarregaForm();"/><strong>Ambos</strong>	 						</span>	 					</td>
					</tr>
					<tr>
           					<logic:equal name="GerarRelatorioAcompanhamentoExecucaoServicoCobrancaActionForm" property="comando" value="2">
						<td HEIGHT="30"><strong>Título do Comando<font color="red">  </font>:</strong></td>
        				<td colspan="2">
           					<html:hidden property="idCobrancaAcaoAtividadeComando"/>
        					<html:text property="tituloCobrancaAcaoAtividadeComando" size="52" maxlength="52" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"/>
							<a href="javascript:abrirPopup('exibirPesquisarComandoAcaoCobrancaAction.do?limparForm=OK', 400, 750);" title="Pesquisar">
								<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23" height="21" border="0"></a>
							<a href="javascript:limparTitulo();">
								<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"	border="0" title="Apagar" /></a>					    </td>
					    </logic:equal>
					</tr>
					<tr><logic:equal name="GerarRelatorioAcompanhamentoExecucaoServicoCobrancaActionForm" property="comando" value="1">
						<td><Strong>Comando Cronograma<font color="red">  </font>:</Strong></td>
						<td colspan="2">
							<html:hidden property="idCobrancaAcaoAtividadeCronograma"/>
							<html:text property="descricaoGrupo" size="20" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>&nbsp;
							<a href="javascript:abrirPopup('exibirPesquisarCronogramaAcaoCobrancaAction.do', 400, 750);" title="Pesquisar">
								<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23" height="21" border="0"></a>
							<a href="javascript:limparGrupo();">
								<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"	border="0" title="Apagar" /></a>						</td>
						</logic:equal>
					</tr>
					<tr>
						<td><strong>Período Inicial:<font color="red">*</font></strong></td>
						<td colspan="2">
							<strong> 
								<html:text maxlength="10" property="periodoInicial" size="10" tabindex="7" onkeypress="return isCampoNumerico(event);" onkeyup="javascript:mascaraData(this, event);replicarCampo(document.forms[0].periodoFim,this);" /> 
								 <a id="linkPeriodoInicial" href="javascript:abrirCalendario('GerarRelatorioAcompanhamentoExecucaoServicoCobrancaActionForm', 'periodoInicial')">
									<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="middle" alt="Exibir Calendário" />
								  </a>
				   			&nbsp;(dd/mm/aaaa)
				   			</strong>
						</td>
					</tr>
					<tr>
						<td><strong>Período Final:<font color="red">*</font></strong></td>
						<td colspan="2">
							<strong> 
								<html:text maxlength="10" property="periodoFim" size="10" tabindex="8" onkeypress="return isCampoNumerico(event);" onkeyup="javascript:mascaraData(this, event);" /> 
								  <a id="linkPeriodoFim" href="javascript:abrirCalendario('GerarRelatorioAcompanhamentoExecucaoServicoCobrancaActionForm', 'periodoFim')">
									<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="middle" alt="Exibir Calendário" />
								  </a>
				   			(dd/mm/aaaa)
				   			</strong>
						</td>
					</tr>
					<tr>
						<td><strong>Ação:</strong></td>
						<td colspan="2" align="left">
                        	<html:select property="acao">
							<html:option value="">&nbsp;</html:option>
							<html:options collection="colecaoCobrancaAcao" labelProperty="descricaoCobrancaAcao" property="id" />
						</html:select></td>
					</tr>
                    <tr>
					  <td><strong>Situa&ccedil;&atilde;o:</strong></td>
					  <td colspan="2" align="left">
                      		<html:radio property="situacao" tabindex="1" value="1" onclick="recarregaForm();"/><strong>Pendente</strong>
							<html:radio property="situacao" tabindex="2" value="2" onclick="recarregaForm();"/><strong>Encerrado</strong>
                                    
                      </td>
				  </tr>
				  <logic:equal name="GerarRelatorioAcompanhamentoExecucaoServicoCobrancaActionForm" property="situacao" value="2">
                  <tr>
					  <td><strong>Motivo do Encerramento:</strong></td>
					  <td colspan="2" align="left">
					        <html:select property="motivoEncerramento">
							<html:option value="">&nbsp;</html:option>
							<html:options collection="colecaoMotivoEncerramento" labelProperty="descricao" property="id" />
							</html:select>
					  </td>
				  </tr></logic:equal>	
                  <tr>
					  <td><strong>Im&oacute;vel Religado:</strong></td>
					  <td colspan="2" align="left">
                      		<html:radio property="religado" tabindex="1" value="1" /><strong>Sim</strong>
							<html:radio property="religado" tabindex="2" value="2" /><strong>Não</strong>
                                </td>
				  </tr>
                  <tr>
					  <td><strong>Tipo Servi&ccedil;o:</strong></td>
					  <td colspan="2" align="left">
					        <html:select property="servico">
							<html:option value="">&nbsp;</html:option>
							<html:options collection="colecaoServicoTipo" labelProperty="descricao" property="id" />
							</html:select>
					  </td>
				  </tr>
                  <tr>
					  <td><strong>Localidade:</strong></td>
					  <td colspan="2" align="left">
						<html:text maxlength="3" property="localidade" size="3"  tabindex="1" 
                   	onkeypress="javascript:limparPesquisaDescricaoLocalidade(); return validaEnter(event, 'exibirFiltrarRelatorioAcompanhamentoExecucaoServicoCobrancaAction.do?pesquisaLocalidade=1', 'localidade');"/>
                      <a href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do?tipo=imovelLocalidade', 400, 800);">
                         <img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/></a>

   		      <logic:present name="codigoLocalidadeNaoEncontrada" scope="request">
			<input type="text" name="localidadeDescricao" size="50" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" value="<bean:message key="atencao.localidade.inexistente"/>"/>
                      </logic:present>

                      <logic:notPresent name="codigoLocalidadeNaoEncontrada" scope="request">
                        <html:text property="localidadeDescricao" size="50" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
                      </logic:notPresent>
						<a href="javascript:limparPesquisaLocalidade();document.forms[0].localidade.focus();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a> 
					  </td>
				  </tr>
                  <tr>
					  <td><strong>Grupo Cobran&ccedil;a:</strong></td>
					  <td colspan="2" align="left">
					  		<html:select property="grupo" onchange="recarregaForm();">
							<html:option value="">&nbsp;</html:option>
							<html:options collection="colecaoCobrancaGrupo" labelProperty="descricao" property="id"  />
							</html:select>		  
					  </td>
				  </tr>	
                  <tr>
					  <td>&nbsp;</td>
					  <td align="left" width="25%"><strong>Setor Comercial:</strong></td>
				      <td align="left" width="25%"><strong>Bairro:</strong></td>
                  </tr>
                  <tr>
					  <td> </td>
					  <td align="left">
					  <logic:notEmpty name="GerarRelatorioAcompanhamentoExecucaoServicoCobrancaActionForm" property="grupo" >
							<html:select property="setorComercial" multiple="true" size="4" onclick="recarregaFormSetorComercial();">
							<html:options collection="colecaoSetorComercial" labelProperty="descricao" property="id" />
							</html:select>
					  </logic:notEmpty>
					  <logic:empty name="GerarRelatorioAcompanhamentoExecucaoServicoCobrancaActionForm" property="grupo" >
					  		<html:select property="setorComercial" multiple="true" size="4" >
							<html:option value="">Selecione um Grupo</html:option>
							</html:select>
					  </logic:empty>
					  </td>
				      <td align="left">
				      	<logic:notEmpty name="GerarRelatorioAcompanhamentoExecucaoServicoCobrancaActionForm" property="grupo" >
							<html:select property="bairro" multiple="true" size="4" onclick="recarregaFormBairro();">
							<html:options collection="colecaoBairro" labelProperty="nome" property="id" />
							</html:select>
						</logic:notEmpty>
					  	<logic:empty name="GerarRelatorioAcompanhamentoExecucaoServicoCobrancaActionForm" property="grupo" >
							<html:select property="bairro" multiple="true" size="4" >
							<html:option value="">Selecione um Grupo</html:option>
							</html:select>
					  	</logic:empty>
					  </td>
                  </tr>
					<tr>
						<td>
							<strong> 
								<font color="#FF0000"> 
									<input type="button" name="Submit22" class="bottonRightCol" value="Limpar" onClick="javascript:limparForm(document.forms[0]);">
								</font>							</strong>						</td>
						<td colspan="2" align="right"><input type="button" name="Submit2"
							class="bottonRightCol" value="Gerar Relatorio"
							onclick="validaForm(document.forms[0]);"></td>
					</tr>
				</table>
			  <p>&nbsp;</p>
			</tr>
			<!-- Rodapé -->
			<%@ include file="/jsp/util/rodape.jsp"%>
		</table>
		<p>&nbsp;</p>
	<tr>
	</tr>

</html:form>
</body>
</html:html>