<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>
<%@ page import="gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gcom.util.ConstantesSistema"%>
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="GerarRelatorioTabulacaoPagamentosPorResponsavelForm" dynamicJavascript="false" />

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script>

	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

		var form = document.forms[0];
				
		var valorAnterior;
		
		if(form.campoOrigem.value == 'P_CLI_INI') {
		
			valorAnterior = form.P_CLI_INI.value;
			
			form.P_CLI_INI.value = codigoRegistro;
	  		form.nomeClienteInicial.value = descricaoRegistro;
	  		form.nomeClienteInicial.style.color = "#000000";
			
	  		if((form.P_CLI_FIN.value == '') 
	  		  		|| (form.P_CLI_FIN.value == valorAnterior)) {
	      		form.P_CLI_FIN.value = codigoRegistro;
		  		form.nomeClienteFinal.value = descricaoRegistro;	 
		  		form.nomeClienteFinal.style.color = "#000000";
		  	  	
	  		}
	  		
		}  else if(form.campoOrigem.value == 'P_CLI_FIN') {
			form.P_CLI_FIN.value = codigoRegistro;
	  		form.nomeClienteFinal.value = descricaoRegistro;
	  		form.nomeClienteFinal.style.color = "#000000";	  		 		
		}

		form.campoOrigem.value = "";
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

	function habilitarPesquisaCliente(form) {
		if (form.P_CLI_INI.readOnly == false) {
			form.tipoPesquisa.value = 'cliente';
			chamarPopup('exibirPesquisarClienteAction.do', 'cliente', null, null, 275, 480, '',form.P_CLI_INI.value);
		}	
	}

	function limparBorracha(tipo){
		var form = document.forms[0];
		
		switch(tipo){
			case 1: // Cliente Inicial
				form.P_CLI_INI.value = "";
				form.nomeClienteInicial.value = "";
				form.P_TP_CLI_INI.disabled = false;
				form.P_CLI_FIN.value = "";
				form.nomeClienteFinal.value = "";
				form.P_TP_CLI_FIN.disabled = false;
				break;
				
			case 2: // Cliente Final
				form.P_CLI_INI.value = "";
				form.nomeClienteInicial.value = "";
				form.P_TP_CLI_INI.disabled = false;
				form.P_CLI_FIN.value = "";
				form.nomeClienteFinal.value = "";
				form.P_TP_CLI_FIN.disabled = false;
				break;
		}
	}

	// Limpa todo o formulário
  	function limpar(){

  		var form = document.forms[0];

	    form.P_CLI_INI.value = "";	
	   	form.nomeClienteInicial.value = "";
    	    
	    form.P_TP_CLI_INI.value = "-1";
	    form.P_TP_CLI_INI.disabled = false;

	    form.P_CLI_FIN.value = "";	
   	    form.nomeClienteFinal.value = "";

	    form.P_TP_CLI_FIN.value = "-1";
	    form.P_TP_CLI_FIN.disabled = false;

	    form.P_DT_REALIZACAO_INI.value = "";
	    form.P_DT_REALIZACAO_FIN.value = "";

	  }

  	function replicarTipoCliente() {
		var form = document.forms[0];
		form.P_TP_CLI_FIN.value = form.P_TP_CLI_INI.value;
	}
	
	function desabilitaCombosTipoCliente(valor) {
		var form = document.forms[0];		
		if(valor.value.length > 0){
			form.P_TP_CLI_INI.disabled = true;
			form.P_TP_CLI_FIN.disabled = true;
		}else{
			form.P_TP_CLI_INI.disabled = false;
			form.P_TP_CLI_FIN.disabled = false;
		}
	}

	function habilitaImagem(tabelaInicial,tabelaFinal,display,valor){
		var form = document.forms[0];
		
		if(display){

			if(tabelaInicial != '') {
				eval('layerHide'+tabelaInicial).style.display = 'none';
				eval('layerShow'+tabelaInicial).style.display = 'block';
			}
			
			if(tabelaFinal != '') {
			    eval('layerHide'+tabelaFinal).style.display = 'none';
			    eval('layerShow'+tabelaFinal).style.display = 'block';
			}
			
		}else if(display == false && valor.value != 't'){

			if(tabelaInicial != '') {
				eval('layerHide'+tabelaInicial).style.display = 'block';
				eval('layerShow'+tabelaInicial).style.display = 'none';
	
				// limpa o campo de responsável inicial
				form.P_CLI_INI.value = "";
				form.nomeClienteInicial.value = "";
				form.nomeClienteInicial.style.color = "#EFEFEF";
			}

			if(tabelaFinal != '') {
				eval('layerHide'+tabelaFinal).style.display = 'block';
				eval('layerShow'+tabelaFinal).style.display = 'none';

				// limpa o campo de responsável inicial
				form.P_CLI_FIN.value = "";
				form.nomeClienteFinal.value = "";
				form.nomeClienteFinal.style.color = "#EFEFEF";
			}
			
		}else if(valor.value == 't'){
			
			if(tabelaInicial != '') {
				eval('layerHide'+tabelaInicial).style.display = 'none';
				eval('layerShow'+tabelaInicial).style.display = 'block';
			}

			if(tabelaFinal != '') {
				eval('layerHide'+tabelaFinal).style.display = 'none';
				eval('layerShow'+tabelaFinal).style.display = 'block';
			}
		}
	}

	function validarForm(){
		var form = document.forms[0];

		if(form.P_DT_REALIZACAO_INI.value == null || form.P_DT_REALIZACAO_INI.value == '') {
			alert('Data inicial de realização deve ser informada.');			
			form.P_DT_REALIZACAO_INI.focus();
			return false;
		}

		if(form.P_DT_REALIZACAO_FIN.value == null || form.P_DT_REALIZACAO_FIN.value == '') {
			alert('Data final de realização deve ser informada.');			
			form.P_DT_REALIZACAO_FIN.focus();
			return false;
		}
		
		if(comparaData(form.P_DT_REALIZACAO_INI.value, '>', form.P_DT_REALIZACAO_FIN.value)) {
			alert('Data inicial de realização não pode ser maior que a data final de realização.');			
			form.P_DT_REALIZACAO_INI.focus();
			return false;
		}
		form.target = "_blank";
		form.submit();
		form.target = "";
		
	}
  
</script>

</head>
<body leftmargin="5" topmargin="5">


<html:form action="/gerarRelatorioTabulacaoPagamentosPorResponsavelAction.do"
	type="org.apache.struts.action.DynaActionForm"
	name="GerarRelatorioTabulacaoPagamentosPorResponsavelForm" method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
	
	<input type="hidden" name="campoOrigem" value=""/>
	<input type="hidden" name="acao" value="gerarRelatorio"/>
	<input type="hidden" name="relatorio" value="RelatorioTabPagamento.rpt"/>

	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<tr>
			<td width="149" valign="top" class="leftcoltext">
			<div align="center">

			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>
			
			<%@ include file="/jsp/util/mensagens.jsp"%>

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
					<td width="11">
						<img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" />
					</td>
					<td class="parabg">Relatório de Tabulação de Pagamentos por Responsável</td>
					<td width="11">
						<img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="4">Para gerar o relatório informe pelo menos um dos valores abaixo:</td>
				</tr>

				<tr height="1">
					<td colspan="4">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="4">
						<table 
							width="100%" 
							align="center" 
							bgcolor="#99CCFF" 
							border="0">

							<tr>
								<td height="0"><strong>Per&iacute;odo de Realiza&ccedil;&atilde;o do Aviso:</strong></td>					
								<td >						
									<html:text maxlength="10"
										tabindex="1"
										property="P_DT_REALIZACAO_INI" 
										size="10" 
										onkeypress="javascript:return isCampoNumerico(event); "
										onkeyup="javascript:mascaraData(this, event);replicarCampo(document.forms[0].P_DT_REALIZACAO_FIN,this);"
										/>
										<a	href="javascript:abrirCalendarioReplicando('GerarRelatorioTabulacaoPagamentosPorResponsavelForm', 'P_DT_REALIZACAO_INI', 'P_DT_REALIZACAO_FIN');">
											<img border="0"	src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" />
										</a>
										&nbsp;&nbsp;
										<strong>a</strong>
										&nbsp;&nbsp;
									<html:text maxlength="10"
										tabindex="1"
										property="P_DT_REALIZACAO_FIN" 
										size="10"
										onkeypress="javascript:return isCampoNumerico(event); "
										onkeyup="javascript:mascaraData(this, event);"
										/>
									<a href="javascript:abrirCalendario('GerarRelatorioTabulacaoPagamentosPorResponsavelForm', 'P_DT_REALIZACAO_FIN')">
										<img border="0"	src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" />
									</a>		
									(dd/mm/aaaa)<font color="#FF0000">*</font>
								</td>
							</tr>
               			</table>
                  	</td>
				</tr>
				
				<tr>
					<td colspan="4">
						<table 
							width="100%" 
							align="center" 
							bgcolor="#99CCFF" 
							border="0">

		                	<tr> 
		                      <td colspan="2">
		                      	<strong>Informe os Dados de Referências Iniciais:</strong>
		                      </td>
		                	</tr>
		                	
		                	<tr bgcolor="#cbe5fe"> 
		                  	  	<td width="100%" 
		                  	  		align="center" 
		                  	  		colspan="2"> 

											                  	    
			                  	    <table width="100%" border="0">										
										<tr> 		
										<td><strong>Cliente Respons&aacute;vel Inicial:</strong></td>
											
										<td>										
											<div id="layerShowResponsavelInicial" style="display: block">
												<html:text maxlength="9" 
												tabindex="1"
												property="P_CLI_INI" 
												styleId="idResponsavelInicial"
												size="9"				
												onkeypress="javascript:validaEnterComMensagem(event, 'exibirGerarRelatorioTabulacaoPagamentosPorResponsavelAction.do?objetoConsulta=1','P_CLI_INI','Cliente Inicial'); 												
												return isCampoNumerico(event);"
												onblur="javascript:desabilitaCombosTipoCliente(this);"/>
												
													<img width="23" 
														height="21" 
														border="0" 
														id="idImagemResponsavelInicialAtivo"													
														onclick="javascript:chamarPopup('exibirPesquisarClienteAction.do', 'clienteInicial', null, null, 275, 480, '','P_CLI_INI');"
														src="<bean:message key="caminho.imagens"/>pesquisa.gif"
														title="Pesquisar Cliente" alt="Pesquisar Cliente" />
												
					                        <logic:empty name="GerarRelatorioTabulacaoPagamentosPorResponsavelForm" property="P_CLI_INI">
												<html:text property="nomeClienteInicial" 
													size="30"
													maxlength="30" 
													readonly="true"
													style="background-color:#EFEFEF; border:0; color: red" />                        
											</logic:empty>
											<logic:notEmpty name="GerarRelatorioTabulacaoPagamentosPorResponsavelForm" property="P_CLI_INI">
											    <html:text property="nomeClienteInicial" 
													size="30"
													maxlength="30" 
													readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
											</logic:notEmpty>
												<img 
												src="<bean:message key="caminho.imagens"/>limparcampo.gif"
												border="0" 
												title="Apagar" 
												onclick="javascript:limparBorracha(1);"													
												/>
											</div>

											<div id="layerHideResponsavelInicial" style="display: none">											

											<html:text maxlength="9" 
												tabindex="1"
												property="p_resp_inicial_hide" 
												styleId="idResponsavelInicialHide"
												size="9"	
												disabled="true"/>
																									
													<img width="23" 
														height="21" 
														border="0" 
														id="idImagemResponsavelInicialDesativo"																	
														src="<bean:message key="caminho.imagens"/>pesquisa.gif"
														title="Pesquisar Cliente" alt="Pesquisar Cliente" />
											
													
											    <html:text property="nomeClienteInicial_hide" 
													size="30"
													maxlength="30" 
													readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />

												<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
													border="0" 
													title="Apagar" />
											</div>											
										</td>
										</tr>										
		                				<tr>
											<td width="18%" height="28"><strong>Tipo Cliente Respons&aacute;vel Inicial:</strong></td>
											<td width="82%">
												<html:select property="P_TP_CLI_INI" tabindex="1" disabled="${requestScope.disabledClienteInicial}"
												onchange="javascript:replicarTipoCliente();habilitaImagem('ResponsavelInicial', 'ResponsavelFinal', false, this);" 
												
												styleId="idTipoResponsavelInicial">
													<html:option value="t">&nbsp;</html:option>
													<html:options collection="colecaoTiposClientes" labelProperty="descricaoComId" property="id" />
												</html:select>	
											</td>
										</tr>
										
				   					</table>
						 		</td>
							</tr>
               			</table>
                  	</td>
				</tr>

				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>

				<tr>
					<td colspan="4">
					<table 
						width="100%" 
						align="center" 
						bgcolor="#99CCFF" 
						border="0">
	                	
	                	<tr> 
	                      <td colspan="2"><strong>Informe os Dados de Referências Finais:</strong></td>
	                	</tr>
	                	
	                	<tr bgcolor="#cbe5fe"> 
	                  	  	<td width="100%" 
	                  	  		align="center" 
	                  	  		colspan="2"> 
	                  	    
		                  	   <table width="100%" border="0">
									<tr>
										<td><strong>Cliente Respons&aacute;vel Final:</strong></td>										
										<td>
											<div id="layerShowResponsavelFinal" style="display: block">
												<html:text maxlength="9" 
													tabindex="1"
													property="P_CLI_FIN" 
													styleId="idResponsavelFinal"													
													size="9"
													onkeypress="validaEnterComMensagem(event, 'exibirGerarRelatorioTabulacaoPagamentosPorResponsavelAction.do?objetoConsulta=2','P_CLI_FIN','Cliente Final'); 
													return isCampoNumerico(event);" 
													onblur="javascript:desabilitaCombosTipoCliente(this);"/>
													
													<img width="23" 
														height="21" 
														border="0" 
														name="idImagemResponsavelFinal"
														onclick="javascript:chamarPopup('exibirPesquisarClienteAction.do', 'destino', null, null, 275, 480, '','P_CLI_FIN');"
														src="<bean:message key="caminho.imagens"/>pesquisa.gif"
														title="Pesquisar Cliente" alt="Pesquisar Cliente" />
														 
						                        <logic:empty name="GerarRelatorioTabulacaoPagamentosPorResponsavelForm" property="P_CLI_FIN">
													<html:text property="nomeClienteFinal" 
														size="30"
														maxlength="30" 
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: red" />                        
												</logic:empty>
												<logic:notEmpty name="GerarRelatorioTabulacaoPagamentosPorResponsavelForm" property="P_CLI_FIN">
												    <html:text property="nomeClienteFinal" 
														size="30"
														maxlength="30" 
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: #000000" />
												</logic:notEmpty>						
												<a href="javascript:limparBorracha(2);"> 
													<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
														border="0" 
														title="Apagar" />
												</a>
											</div>
											
											<div id="layerHideResponsavelFinal" style="display: none">	
												<html:text maxlength="9" 
													tabindex="1"
													property="p_resp_final_hide" 
													styleId="idResponsavelFinalHide"
													disabled="true"
													size="9"/>
													
													<img width="23" 
														height="21" 
														border="0" 
														name="idImagemResponsavelFinal"														
														src="<bean:message key="caminho.imagens"/>pesquisa.gif"
														title="Pesquisar Cliente" alt="Pesquisar Cliente" />
														 
												    <html:text property="nomeClienteFinal_hide" 
														size="30"
														maxlength="30" 
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: #000000" />

 
													<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
														border="0" 
														title="Apagar" />
											</div>
										</td>
									</tr>
									
									<tr>
										<td width="18%" height="28"><strong>Tipo Cliente Respons&aacute;vel Final:</strong></td>
										<td width="82%">
											<html:select property="P_TP_CLI_FIN" tabindex="1" disabled="${requestScope.disabledClienteFinal}"
											
											onchange="javascript:habilitaImagem('ResponsavelInicial', 'ResponsavelFinal', false, this);" 
											styleId="idTipoResponsavelFinal">
												<html:option value="t">&nbsp;</html:option>
												<html:options collection="colecaoTiposClientes" labelProperty="descricaoComId" property="id" />
											</html:select>	
										</td>
									</tr>
									
							   </table>
					      </td>
						 </tr>
					   </table>
					</td>
				</tr>
				
				<tr>
					<td>&nbsp;</td>
				</tr>
								      
		        <tr>
					<td class="style1">
						<input name="Submit22" class="bottonRightCol" value="Limpar" type="button" onclick="javascript:limpar();">
						&nbsp;
						<input type="button" name="Button" class="bottonRightCol" value="Cancelar" tabindex="6" onClick="javascript:window.location.href='/gsan/telaPrincipal.do'" style="width: 80px"/>
					</td>
					<td align="right">
					<html:button property="gerar" styleClass="bottonRightCol" value="Gerar" onclick="javascript:validarForm();"></html:button>										
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
