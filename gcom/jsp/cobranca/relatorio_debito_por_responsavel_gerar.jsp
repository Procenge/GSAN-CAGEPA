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
	formName="GerarRelatorioDebitoPorResponsavelActionForm"/>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script>

	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

		var form = document.forms[0];
				
		var valorAnterior;
		
		if(form.campoOrigem.value == 'idClienteInicial') {
		
			valorAnterior = form.idClienteInicial.value;
			
			form.idClienteInicial.value = codigoRegistro;
	  		form.nomeClienteInicial.value = descricaoRegistro;
	  		form.nomeClienteInicial.style.color = "#000000";
			
	  		if((form.idClienteFinal.value == '') 
	  		  		|| (form.idClienteFinal.value == valorAnterior)) {
	      		form.idClienteFinal.value = codigoRegistro;
		  		form.nomeClienteFinal.value = descricaoRegistro;	 
		  		form.nomeClienteFinal.style.color = "#000000";
		  	  	
	  		}
	  		
		}  else if(form.campoOrigem.value == 'idClienteFinal') {
			form.idClienteFinal.value = codigoRegistro;
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
		if (form.idClienteInicial.readOnly == false) {
			form.tipoPesquisa.value = 'cliente';
			chamarPopup('exibirPesquisarClienteAction.do', 'cliente', null, null, 275, 480, '',form.idClienteInicial.value);
		}	
	}

	function limparBorracha(tipo){
		var form = document.forms[0];
		
		switch(tipo){
			case 1: // Cliente Inicial
				form.idClienteInicial.value = "";
				form.nomeClienteInicial.value = "";
				form.idTipoClienteInicial.disabled = false;
				form.idClienteFinal.value = "";
				form.nomeClienteFinal.value = "";
				form.idTipoClienteFinal.disabled = false;
				break;
				
			case 2: // Cliente Final
				form.idClienteInicial.value = "";
				form.nomeClienteInicial.value = "";
				form.idTipoClienteInicial.disabled = false;
				form.idClienteFinal.value = "";
				form.nomeClienteFinal.value = "";
				form.idTipoClienteFinal.disabled = false;
				break;
		}
	}

	// Limpa todo o formulário
  	function limpar(){

  		var form = document.forms[0];

	    form.idClienteInicial.value = "";	
   	    form.nomeClienteInicial.value = "";
    	    
	    form.idTipoClienteInicial.value = "-1";
	    form.idTipoClienteInicial.disabled = false;

	    form.idClienteFinal.value = "";	
   	    form.nomeClienteFinal.value = "";

	    form.idTipoClienteFinal.value = "-1";
	    form.idTipoClienteFinal.disabled = false;

	    form.referenciaDebitoInicial.value = "";
	    form.referenciaDebitoFinal.value = "";

	    form.indicadorTipoRelatorio[0].checked = true;
	    form.indicadorTipoRelatorio[1].checked = false;
	    
	    form.indicadorResponsabilidade[0].checked = true;
	    form.indicadorResponsabilidade[1].checked = false;
	    form.indicadorResponsabilidade[2].checked = false;

	    form.indicadorContasEmRevisao[0].checked = false;
	    form.indicadorContasEmRevisao[1].checked = true;

	    form.indicadorValorCorrigido[0].checked = false;
	    form.indicadorValorCorrigido[1].checked = true; 

	    form.idMotivoRevisao.value = "-1";
	    form.idMotivoRevisao.disabled = true;
  	}

  	function replicarTipoCliente() {

		var form = document.forms[0];
		form.idTipoClienteFinal.value = form.idTipoClienteInicial.value;
	}
	
	function desabilitaCombosTipoCliente(valor) {
		var form = document.forms[0];		
		if(valor.value.length > 0){

			form.idTipoClienteInicial.disabled = true;
			form.idTipoClienteFinal.disabled = true;
		}else{

			form.idTipoClienteInicial.disabled = false;
			form.idTipoClienteFinal.disabled = false;
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
				form.idClienteInicial.value = "";
				form.nomeClienteInicial.value = "";
				form.nomeClienteInicial.style.color = "#EFEFEF";
			}

			if(tabelaFinal != '') {

				eval('layerHide'+tabelaFinal).style.display = 'block';
				eval('layerShow'+tabelaFinal).style.display = 'none';

				// limpa o campo de responsável inicial
				form.idClienteFinal.value = "";
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

	function habilitarComboMotivoContaRevisao(valor) {
		var form = document.forms[0];		
		
		if (valor.value == <%=ConstantesSistema.SIM%>) {
			form.idMotivoRevisao.disabled = false;
		} else {
			form.idMotivoRevisao.disabled = true;
		}
	}

	function validarForm(){

		var form = document.forms[0];
		if(comparaMesAno(form.referenciaDebitoInicial.value, '>', form.referenciaDebitoFinal.value)) {

			alert('Ano e mês inicial não pode ser maior que o final.');			
			form.referenciaDebitoInicial.focus();
			return false;
		}
		
		
		/*
		if(form.indicadorContasEmRevisao[0].checked == true && form.idMotivoRevisao.value == "-1") {

			alert('Selecione um motivo da revisão.');			
			form.idMotivoRevisao.focus();
			return false;
		}

		*/
		if (validateGerarRelatorioDebitoPorResponsavelActionForm(form)){
			toggleBox('demodiv',1);
		}
		
	}
  
</script>

</head>
<body leftmargin="5" topmargin="5">


<html:form action="/gerarRelatorioDebitoPorResponsavelAction.do"
	type="gcom.gui.relatorio.cobranca.GerarRelatorioDebitoPorResponsavelActionForm"
	name="GerarRelatorioDebitoPorResponsavelActionForm" method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
	
	<input type="hidden" name="campoOrigem" value=""/>

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
					<td class="parabg">Relatório de débito por responsável</td>
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
										<td><strong>Cliente Resp. Inicial:</strong></td>
											
										<td>										
											<div id="layerShowResponsavelInicial" style="display: block">
												<html:text maxlength="9" 
												property="idClienteInicial" 
												styleId="idResponsavelInicial"
												size="9"				
												onkeypress="javascript:validaEnterComMensagem(event, 'exibirGerarRelatorioDebitoPorResponsavelAction.do?objetoConsulta=1','idClienteInicial','Cliente Inicial'); 												
												return isCampoNumerico(event);"
												onblur="javascript:desabilitaCombosTipoCliente(this);"/>
												
													<img width="23" 
														height="21" 
														border="0" 
														id="idImagemResponsavelInicialAtivo"													
														onclick="javascript:chamarPopup('exibirPesquisarClienteAction.do', 'clienteInicial', null, null, 275, 480, '','idClienteInicial');"
														src="<bean:message key="caminho.imagens"/>pesquisa.gif"
														title="Pesquisar Cliente" alt="Pesquisar Cliente" />
												
					                        <logic:empty name="GerarRelatorioDebitoPorResponsavelActionForm" property="idClienteInicial">
												<html:text property="nomeClienteInicial" 
													size="30"
													maxlength="30" 
													readonly="true"
													style="background-color:#EFEFEF; border:0; color: red" />                        
											</logic:empty>
											<logic:notEmpty name="GerarRelatorioDebitoPorResponsavelActionForm" property="idClienteInicial">
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
												property="idResponsavelInicialHide" 
												styleId="idResponsavelInicialHide"
												size="9"	
												disabled="true"/>
																									
													<img width="23" 
														height="21" 
														border="0" 
														id="idImagemResponsavelInicialDesativo"																	
														src="<bean:message key="caminho.imagens"/>pesquisa.gif"
														title="Pesquisar Cliente" alt="Pesquisar Cliente" />
											
													
											    <html:text property="nomeClienteInicialHide" 
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
											<td width="18%" height="28"><strong>Tipo Cliente Resp. Inicial:</strong></td>
											<td width="82%">
												<html:select property="idTipoClienteInicial" disabled="${requestScope.disabledClienteInicial}"
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
										<td><strong>Cliente Resp. Final:</strong></td>										
										<td>
											<div id="layerShowResponsavelFinal" style="display: block">
												<html:text maxlength="9" 
													property="idClienteFinal" 
													styleId="idResponsavelFinal"													
													size="9"
													onkeypress="validaEnterComMensagem(event, 'exibirGerarRelatorioDebitoPorResponsavelAction.do?objetoConsulta=2','idClienteFinal','Cliente Final'); 
													return isCampoNumerico(event);" 
													onblur="javascript:desabilitaCombosTipoCliente(this);"/>
													
													<img width="23" 
														height="21" 
														border="0" 
														name="idImagemResponsavelFinal"
														onclick="javascript:chamarPopup('exibirPesquisarClienteAction.do', 'destino', null, null, 275, 480, '','idClienteFinal');"
														src="<bean:message key="caminho.imagens"/>pesquisa.gif"
														title="Pesquisar Cliente" alt="Pesquisar Cliente" />
														 
						                        <logic:empty name="GerarRelatorioDebitoPorResponsavelActionForm" property="idClienteFinal">
													<html:text property="nomeClienteFinal" 
														size="30"
														maxlength="30" 
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: red" />                        
												</logic:empty>
												<logic:notEmpty name="GerarRelatorioDebitoPorResponsavelActionForm" property="idClienteFinal">
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
													property="idResponsavelFinalHide" 
													styleId="idResponsavelFinalHide"
													disabled="true"
													size="9"/>
													
													<img width="23" 
														height="21" 
														border="0" 
														name="idImagemResponsavelFinal"														
														src="<bean:message key="caminho.imagens"/>pesquisa.gif"
														title="Pesquisar Cliente" alt="Pesquisar Cliente" />
														 
												    <html:text property="nomeClienteFinalHide" 
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
										<td width="18%" height="28"><strong>Tipo Cliente Resp. Final:</strong></td>
										<td width="82%">
											<html:select property="idTipoClienteFinal" disabled="${requestScope.disabledClienteFinal}"
											
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
					<td><strong>Per&iacute;odo de Refer&ecirc;ncia do D&eacute;bito:</strong></td>					
					<td >						
						<html:text maxlength="7"
							property="referenciaDebitoInicial" 
							size="7" 
							onkeypress="javascript:return isCampoNumerico(event); "
							onkeyup="javascript:mascaraAnoMes(this, event);replicarCampo(document.forms[0].referenciaDebitoFinal,this);"
							onblur="javascript:return verificaAnoMes(this);"
							/>
						<strong>a</strong>
						<html:text maxlength="7"
							property="referenciaDebitoFinal" 
							size="7"
							onkeypress="javascript:return isCampoNumerico(event); "
							onkeyup="javascript:mascaraAnoMes(this, event);"
							onblur="javascript:return verificaAnoMes(this);"/>(mm/aaaa)
					</td>
				</tr>
				
				<tr>
					<td width="30%"><strong>Tipo do relatório:</strong></td>
					<td width="70%">
						<strong> 
							<span class="style1">
								<html:radio property="indicadorTipoRelatorio" value="A"> 
									<strong>Analítico</strong>
								</html:radio>
								<html:radio property="indicadorTipoRelatorio" value="S"> 
									<strong>Débito</strong>
								</html:radio>
							</span>
						</strong>
					</td>
				</tr>
				
				<tr>
					<td width="30%"><strong>Responsabilidade:</strong></td>
					<td width="70%">
						<strong> 
							<span class="style1">							 
								<html:radio property="indicadorResponsabilidade" value="D"> 
									<strong>Direta</strong>
								</html:radio>
								<html:radio property="indicadorResponsabilidade" value="I"> 
									<strong>Indireta</strong>
								</html:radio>
								<html:radio property="indicadorResponsabilidade" value="A"> 
									<strong>Ambas</strong>
								</html:radio>
							</span>
						</strong>
					</td>
				</tr>
				
				<tr>
					<td width="30%"><strong>Contas em Revisão:</strong></td>
					<td width="70%">
						<strong> 
							<span class="style1">							 
								<html:radio property="indicadorContasEmRevisao" value="1" onclick="javascript:habilitarComboMotivoContaRevisao(this);"> 
									<strong>SIM</strong>
								</html:radio>
								<html:radio property="indicadorContasEmRevisao" value="2" onclick="javascript:habilitarComboMotivoContaRevisao(this);"> 
									<strong>NÃO</strong>
								</html:radio>
							</span>
						</strong>
					</td>
				</tr>
				
				
				
				<tr>
		          <td width="150" height="20"><strong>Motivo da Revisão:</strong></td>
		          <td colspan="3">		          
		          	<html:select property="idMotivoRevisao" disabled="${requestScope.disabledMotivoRevisao}">
		          		<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoMotivoRevisaoConta">
							<html:options collection="colecaoMotivoRevisaoConta" labelProperty="descricaoMotivoRevisaoConta" property="id"/>
						</logic:present>
					</html:select>		          
		          </td>
		        </tr>
		        
		        		        
		        
		        <tr>
					<td width="30%"><strong>Valor Corrigido:</strong></td>
					<td width="70%">
						<strong> 
							<span class="style1">							 
								<html:radio property="indicadorValorCorrigido" value="1"> 
									<strong>SIM</strong>
								</html:radio>
								<html:radio property="indicadorValorCorrigido" value="2"> 
									<strong>NÃO</strong>
								</html:radio>
							</span>
						</strong>
					</td>
				</tr>
				 
				<tr>
					<td><strong>Esfera Poder:</font></strong></td>
					<td colspan="2"><html:select property="esferaPoder">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoEsferaPoder"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				
				<tr>
					<td>&nbsp;</td>
				</tr>
								      
		        <tr>
					<td colspan="2" class="style1"><input name="Submit22"
						class="bottonRightCol" value="Limpar" type="button"
						onclick="javascript:limpar();">
						&nbsp;
						<input type="button" name="Button" class="bottonRightCol" value="Cancelar" onClick="javascript:window.location.href='/gsan/telaPrincipal.do'" style="width: 80px"/>
					</td>
					<td colspan="2" align="right">
					<html:button property="gerar" styleClass="bottonRightCol" value="Gerar" onclick="javascript:validarForm();"></html:button>										
					</td>
				</tr>
				
			</table>

			<p>&nbsp;</p>
			</td>
		</tr>


	</table>

	<jsp:include page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioDebitoPorResponsavelAction.do" />
	<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>

</body>
</html:html>
