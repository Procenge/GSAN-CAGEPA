<%@page import="gcom.util.ConstantesSistema"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="ClassificarLotePagamentosNaoClassificadosActionForm" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<script language="JavaScript">

	function validarForm(){
		
		var form = document.forms[0];
		if(campoObrigatorio()){
			if ((form.referenciaArrecadacaoInicial.value != '' && form.referenciaArrecadacaoFinal.value != '') && (comparaMesAno(form.referenciaArrecadacaoInicial.value,'>',form.referenciaArrecadacaoFinal.value))){
				alert ("Referência Arrecadação Final é anterior à Referência Arrecadação Inicial.");
				form.referenciaArrecadacaoFinal.focus;
				return false;
			}
			if ((form.referenciaPagamentoInicial.value != '' && form.referenciaPagamentoFinal.value != '') && (comparaMesAno(form.referenciaPagamentoInicial.value,'>',form.referenciaPagamentoFinal.value))){
				alert ("Referência Pagamento Final é anterior à Referência Pagamento Inicial..");
				return false;
			}
			if ((form.dataPagamentoInicial.value != '' && form.dataPagamentoFinal.value != '') && (comparaData(form.dataPagamentoInicial.value,'>',form.dataPagamentoFinal.value))){
				alert ("Data Final do período é anterior a Data Inicial do período.");
				return false;
			}
			
		//	if(form.opcaoGeracao.value == 'C'){
				
				//if(confirm('Tem certeza que deseja realizar a baixa forçada dos pagamentos?')){
	
					document.forms[0].submit();	
					
				//}
		//	}
			
		}
	}
	
	function campoObrigatorio(){  
		
		var form = document.forms[0];
		var msg = "";

		if(form.limiteMaximoDiferenca.value.length < 1 ){
			msg = "Informe o limite máximo da diferença.";
			form.limiteMaximoDiferenca.focus();
			alert(msg);
			return false;
		}
		
		return true;
	}

	function limparOrigem(tipo){
		var form = document.forms[0];
		
		switch(tipo){
		
		case 1: //De localidade pra baixo
			
			form.nomeLocalidadeInicial.value = "";
			form.localidadeFinal.value = "";
			form.nomeLocalidadeFinal.value = "";
		    
		}
	}
	
	function limparBorrachaOrigem(tipo){
		var form = document.forms[0];
		
		switch(tipo){
			case 1: //De localidara pra baixo

				form.localidadeInicial.value = "";
				form.nomeLocalidadeInicial.value = "";
				form.localidadeFinal.value = "";
				form.nomeLocalidadeFinal.value = "";
				break;
		}
	}
	
	function limparBorrachaDestino(tipo){
		var form = document.forms[0];
		
		switch(tipo){
			case 1: //De localidara pra baixo

				form.localidadeFinal.value = "";
				form.nomeLocalidadeFinal.value = "";
				break;
		}
	}
	
	function replicarLocalidade(){
		var formulario = document.forms[0]; 
		
		formulario.localidadeFinal.value = formulario.localidadeInicial.value;
		formulario.referenciaArrecadacaoInicial.focus;
	}
	
	function replicarReferencia(){
		var formulario = document.forms[0]; 
		formulario.referenciaArrecadacaoFinal.value = formulario.referenciaArrecadacaoInicial.value;
		formulario.referenciaArrecadacaoFinal.focus;
	}
	
	function replicarReferenciaPagamento(){
		var formulario = document.forms[0]; 
		formulario.referenciaPagamentoFinal.value = formulario.referenciaPagamentoInicial.value;
		formulario.referenciaPagamentoFinal.focus;
	}
	
	function replicarDataPagamento(){
		var formulario = document.forms[0]; 
		formulario.dataPagamentoFinal.value = formulario.dataPagamentoInicial.value;
		formulario.dataPagamentoFinal.focus;
	}
	
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,campo){
		
		document.forms[0].campoOrigem.value = campo.name;
		
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
	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

		var form = document.forms[0];
		
		if (tipoConsulta == 'localidadeOrigem') {
      		
      		form.localidadeInicial.value = codigoRegistro;
	  		form.nomeLocalidadeInicial.value = descricaoRegistro;
	  		
	  		form.localidadeFinal.value = codigoRegistro;
      		form.nomeLocalidadeFinal.value = descricaoRegistro;
      		
	  		form.nomeLocalidadeInicial.style.color = "#000000";
	  		form.nomeLocalidadeFinal.style.color = "#000000";
	  		
		}

		if (tipoConsulta == 'localidadeDestino') {
		
      		form.localidadeFinal.value = codigoRegistro;
      		form.nomeLocalidadeFinal.value = descricaoRegistro;
	  		form.nomeLocalidadeFinal.style.color = "#000000";
	  	
		}
		
	}
	
	function troca(str,strsai,strentra) 
    { 
            while(str.indexOf(strsai)>-1) 
            { 
                    str = str.replace(strsai,strentra); 
            } 
            return str; 
    }

	function FormataMoeda(campo,tammax,teclapres,caracter) 
    { 
            if(teclapres == null || teclapres == "undefined") 
            { 
                    var tecla = -1; 
            } 
            else 
            { 
                    var tecla = teclapres.keyCode; 
            } 

            if(caracter == null || caracter == "undefined") 
            { 
                    caracter = "."; 
            } 

            vr = campo.value; 
            if(caracter != "") 
            { 
                    vr = troca(vr,caracter,""); 
            } 
            vr = troca(vr,"/",""); 
            vr = troca(vr,",",""); 
            vr = troca(vr,".",""); 

            tam = vr.length; 
            if(tecla > 0) 
            { 
                    if(tam < tammax && tecla != 8) 
                    { 
                            tam = vr.length + 1; 
                    } 

                    if(tecla == 8) 
                    { 
                            tam = tam - 1; 
                    } 
            } 
            if(tecla == -1 || tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <= 105) 
            { 
                    if(tam <= 2) 
                    { 
                            campo.value = vr; 
                    } 
                    if((tam > 2) && (tam <= 5)) 
                    { 
                            campo.value = vr.substr(0, tam - 2) + ',' + vr.substr(tam - 2, tam); 
                    } 
                    if((tam >= 6) && (tam <= 8)) 
                    { 
                            campo.value = vr.substr(0, tam - 5) + caracter + vr.substr(tam - 5, 3) + ',' + vr.substr(tam - 2, tam); 
                    } 
                    if((tam >= 9) && (tam <= 11)) 
                    { 
                            campo.value = vr.substr(0, tam - 8) + caracter + vr.substr(tam - 8, 3) + caracter + vr.substr(tam - 5, 3) + ',' + vr.substr(tam - 2, tam); 
                    } 
                    if((tam >= 12) && (tam <= 14)) 
                    { 
                            campo.value = vr.substr(0, tam - 11) + caracter + vr.substr(tam - 11, 3) + caracter + vr.substr(tam - 8, 3) + caracter + vr.substr(tam - 5, 3) + ',' + vr.substr(tam - 2, tam); 
                    } 
                    if((tam >= 15) && (tam <= 17)) 
                    { 
                            campo.value = vr.substr(0, tam - 14) + caracter + vr.substr(tam - 14, 3) + caracter + vr.substr(tam - 11, 3) + caracter + vr.substr(tam - 8, 3) + caracter + vr.substr(tam - 5, 3) + ',' + vr.substr(tam - 2, tam); 
                    } 
            } 
    }

	function maskKeyPress(objEvent) 
    { 
            var iKeyCode; 
                                     
            if(window.event) // IE 
            { 
                    iKeyCode = objEvent.keyCode; 
                    if(iKeyCode>=48 && iKeyCode<=57) return true; 
                    return false; 
            } 
            else if(e.which) // Netscape/Firefox/Opera 
            { 
                    iKeyCode = objEvent.which; 
                    if(iKeyCode>=48 && iKeyCode<=57) return true; 
                    return false; 
            } 
             
             
    }
	
	function validaPercentual(campo){
		
		var percentual = campo.value;
		
		percentual = replaceAll(percentual,',','');
		
		percentual = replaceAll(percentual,'.','');
		
		if(percentual != '' && !validaCampoInteiroPositivo(percentual)) {
			alert('Este campo deve conter apenas números positivos.');
			campo.value = "";
			campo.focus();
			return false;
		}		
	}
	
	function repetirDataPagamento() {
		var form = document.ClassificarLotePagamentosNaoClassificadosActionForm;
		form.dataPagamentoFinal.value = form.dataPagamentoInicial.value;
	}
	
	
	
	
</script>

</head>

<body leftmargin="5" topmargin="5">

	<html:form
	    action="/classificarLotePagamentosNaoClassificadosAction"
	    method="post"
	    onsubmit="return validatePagamentoActionForm(this);"
	>

		<%@ include file="/jsp/util/cabecalho.jsp"%>
		<%@ include file="/jsp/util/menu.jsp" %>
		
		<input type="hidden" name="campoOrigem" value=""/>
	
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
				        	<td class="parabg">Baixa Forçada dos Pagamentos por Decurso de Prazo</td>
				        	<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
				        </tr>
		      		</table>
		      		<p>&nbsp;</p>
		      		<table width="100%" border="0" dwcopytype="CopyTableRow">
		        		<tr>
				         	<td colspan="2">
				          		Para classificar o(s) pagamento(s), informe os dados abaixo:
				        	</td>
		        		</tr>
		        		
						<tr>
							<td>
								<strong>Localidade Inicial:</strong>
							</td>
							
							<td>
								
								<html:text maxlength="3" 
									
									property="localidadeInicial" 
									size="3"
									onblur="verificaNumero(this); replicarLocalidade();" 
									onkeyup="javascript:limparOrigem(1);"
									onkeypress="javascript:validaEnterComMensagem(event,'exibirClassificarLotePagamentosNaoClassificadosAction.do?objetoConsulta=1','localidadeInicial','Localidade Inicial');return isCampoNumerico(event);"
									/>
									
								<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'origem', null, null, 275, 480, '',document.forms[0].localidadeInicial);limparOrigem(1);">
									<img width="23" 
										height="21" 
										border="0" 
										style="cursor:hand;"
										src="<bean:message key="caminho.imagens"/>pesquisa.gif"
										title="Pesquisar Localidade" /></a>
										
		
								<logic:present name="localidadeInicialEncontrada" scope="request">
									<html:text property="nomeLocalidadeInicial" 
										size="30"
										maxlength="30" 
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:present> 
		
								<logic:notPresent name="localidadeInicialEncontrada" scope="request">
									<html:text property="nomeLocalidadeInicial" 
										size="30"
										maxlength="30" 
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: red" />
								</logic:notPresent>
		
								
								<a href="javascript:limparBorrachaOrigem(1);"> 
									<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
										border="0" 
										title="Apagar" /></a>
							</td>
						</tr>
						
						<tr>
							<td>
								<strong>Localidade Final:</strong>
							</td>
							
							<td>
								
								<html:text maxlength="3" 
									
									property="localidadeFinal" 
									size="3"
									onkeypress="validaEnterComMensagem(event,'exibirClassificarLotePagamentosNaoClassificadosAction.do?objetoConsulta=2','localidadeFinal','Localidade Final'); return isCampoNumerico(event);"
									onblur="verificaNumero(this);"/>
									
								<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'destino', null, null, 275, 480, '',document.forms[0].localidadeFinal);limparDestino(1);">
									<img width="23" 
										height="21" 
										border="0" 
										style="cursor:hand;"
										src="<bean:message key="caminho.imagens"/>pesquisa.gif"
										title="Pesquisar Localidade" />
								</a>
										 
		
								<logic:present name="localidadeFinalEncontrada" scope="request">
									<html:text property="nomeLocalidadeFinal" 
										size="30"
										maxlength="30" 
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:present> 
		
								<logic:notPresent name="localidadeFinalEncontrada" scope="request">
									<html:text property="nomeLocalidadeFinal" 
										size="30"
										maxlength="30" 
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: red" />
								</logic:notPresent>
		
								
								<a href="javascript:limparBorrachaDestino(1);"> 
									<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
										border="0" 
										title="Apagar" />
								</a>
							</td>
						</tr>
						
						<tr>
							<td>
								<strong>Período Refer&ecirc;ncia da Arrecadação: </strong>
							</td>					
							<td >						
								<html:text maxlength="7"
									
									property="referenciaArrecadacaoInicial" 
									size="7" 
									onkeypress="javascript:return isCampoNumerico(event); "
									onkeyup="javascript:mascaraAnoMes(this, event);"
									onblur="javascript:replicarReferencia();verificaAnoMes(this);"
									/>
								<strong>a</strong>
								<html:text maxlength="7"
									
									property="referenciaArrecadacaoFinal" 
									size="7"
									onkeypress="javascript:return isCampoNumerico(event); "
									onkeyup="javascript:mascaraAnoMes(this, event);"
									onblur="javascript:verificaAnoMes(this);"
									/>(mm/aaaa)
							</td>
						</tr>
						
						<tr>
							<td>
								<strong>Período Refer&ecirc;ncia do Pagamento: </strong>
							</td>					
							<td >						
								<html:text maxlength="7"
									
									property="referenciaPagamentoInicial" 
									size="7" 
									onkeypress="javascript:return isCampoNumerico(event); "
									onkeyup="javascript:mascaraAnoMes(this, event);"
									onblur="javascript:replicarReferenciaPagamento();verificaAnoMes(this);"
									/>
								<strong>a</strong>
								<html:text maxlength="7"
									
									property="referenciaPagamentoFinal" 
									size="7"
									onkeypress="javascript:return isCampoNumerico(event); "
									onkeyup="javascript:mascaraAnoMes(this, event);"
									onblur="javascript:verificaAnoMes(this);"
									/>(mm/aaaa)
							</td>
						</tr>
						
						<tr>
							<td>
								<strong>Período de Data do Pagamento: </strong>
							</td>					
							<td >						
								<html:text maxlength="10"
									property="dataPagamentoInicial" 
									size="10" 
									onkeypress="javascript:return isCampoNumerico(event); "
									onkeyup="javascript:mascaraData(this, event);"
									onblur="javascript:replicarDataPagamento();verificaDataMensagemPersonalizada(this,'Data do Pagamento Inválida');"
									/>
									<a onfocus="repetirDataPagamento();" href="javascript:abrirCalendario('ClassificarLotePagamentosNaoClassificadosActionForm', 'dataPagamentoInicial')">
										<img border="0"
											src="<bean:message key="caminho.imagens"/>calendario.gif"
											width="20" border="0" align="absmiddle" alt="Exibir Calendário" />
									</a>
									<strong>&nbsp;a&nbsp;</strong>
								<html:text maxlength="10"
									property="dataPagamentoFinal" 
									size="10"
									onkeypress="javascript:return isCampoNumerico(event); "
									onkeyup="javascript:mascaraData(this, event);"
									onblur="javascript:verificaDataMensagemPersonalizada(this,'Data do Pagamento Inválida');"
									/>
									<a href="javascript:abrirCalendario('ClassificarLotePagamentosNaoClassificadosActionForm', 'dataPagamentoFinal')">
										<img border="0"
											src="<bean:message key="caminho.imagens"/>calendario.gif"
											width="20" border="0" align="absmiddle" alt="Exibir Calendário" />
									</a>&nbsp;(dd/mm/aaaa)
							</td>
						</tr>
						
						<tr> 
		                	<td>
		                		<strong>Situa&ccedil;&atilde;o  do Pagamento</strong>
		                    </td>
		                    <td><strong> 
									<html:select property="situacaoPagamento" style="width:180px">
										<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
										<logic:present name="colecaoSituacaoPagamento" scope="request">
										<html:options collection="colecaoSituacaoPagamento" 
												labelProperty="descricao" 
												property="id"/>
										</logic:present>
									</html:select> 
								</strong>
							</td>
		              	</tr>
		              	
		                   <tr>
							<td><strong>Cobrar Diferença para menor?: <font color="#FF0000">*</font></strong></td>						
							<td>					
								<html:radio property="indicadorCorancaDiferencaPagtoAMenor" value="<%= ""+ConstantesSistema.SIM %>"/><strong>Sim</strong>
								<html:radio property="indicadorCorancaDiferencaPagtoAMenor" value="<%= ""+ConstantesSistema.NAO %>"/><strong>Não</strong>
							</td>
						   </tr>
		            	
		              	<tr>
							<td><strong>Limite Máximo da Diferença: <font color="#FF0000">*</font></strong></td>						
							<td>						
								<html:text maxlength="13" property="limiteMaximoDiferenca" size="13" onkeydown="FormataMoeda(this,10,event)" 
								onkeypress="return maskKeyPress(event); verificaNumero(this);" 
								onblur="validaPercentual(this)"/>
								
							</td>
						</tr>
						<tr>
							<td><strong>Método de Baixa: <font color="#FF0000">*</font></strong></td>						
							<td>						
								<html:radio property="opcaoGeracao" value="C"><strong>Classificar</strong></html:radio>
								<html:radio property="opcaoGeracao" value="S"><strong>Simular</strong></html:radio>
							</td>
						</tr>
						
						
						<tr>
							<td>
								<strong> 
									<font color="#FF0000"> 
										<input
											name="btLimpar" class="bottonRightCol" value="Limpar"
											type="button"
											onclick="window.location.href='/gsan/exibirClassificarLotePagamentosNaoClassificadosAction.do?menu=sim';">
										<input type="button" name="Button" class="bottonRightCol"
											value="Cancelar" tabindex="6"
											onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
											style="width: 80px" /> 
									</font>
								</strong>
							</td>
							<td align="right">
								<input type="button" onclick="javascript:validarForm();" name="botaoListarPagamentos"
								class="bottonRightCol" value="Gerar">
							</td>
						</tr>
						
		        	</table>               
		          
				</td>
			</tr>
			
		</table>

		<%@ include file="/jsp/util/rodape.jsp"%>

	</html:form>
	
</body>

<!-- classificar_lote_pagamentos_nao_classificados.jsp -->
</html:html>
