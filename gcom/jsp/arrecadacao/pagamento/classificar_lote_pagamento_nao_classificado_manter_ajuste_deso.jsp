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
<html:javascript staticJavascript="false"  formName="ClassificarLotePagamentosNaoClassificadosAjusteDesoActionForm" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<script language="JavaScript">

	function validarForm(){
		
		var form = document.forms[0];

		if ((form.referenciaPagamentoInicial.value != '' && form.referenciaPagamentoFinal.value != '') && (comparaMesAno(form.referenciaPagamentoInicial.value, '>', form.referenciaPagamentoFinal.value))){
			alert ("Referência Pagamento Final é anterior à Referência Pagamento Inicial.");
			return false;
		}

		if ((form.dataPagamentoInicial.value != '' && form.dataPagamentoFinal.value != '') && (comparaData(form.dataPagamentoInicial.value, '>', form.dataPagamentoFinal.value))){
			alert ("Data Final do período é anterior a Data Inicial do período.");
			return false;
		}
		
		if(confirm('Tem certeza que deseja realizar a baixa forçada dos pagamentos?')){
			document.forms[0].submit();	
		}
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

	function repetirDataPagamento() {
		var form = document.ClassificarLotePagamentosNaoClassificadosActionForm;
		form.dataPagamentoFinal.value = form.dataPagamentoInicial.value;
	}

	function validaReferenciaPagamento(dateField) {
		var from, to, check, form;

		if(dateField.value.length == 7){
		
			from  = new Date(2001, 08, 01);
			to    = new Date(2011, 12, 01);
			check = new Date(dateField.value.substring(3, 7), dateField.value.substring(0, 2), 01);

			form  = document.forms[0]; 
	
			if(!dateCheck(from, to, check)){
				alert('A data de Referência de Pagamento deve estar entre 08/2001 e 12/2011!');
				dateField.value = "";
				dateField.focus();
				return false;
			}

			if(form.referenciaPagamentoInicial.value.length == 7 && form.referenciaPagamentoFinal.value.length == 7) { 
				if(comparaMesAno(form.referenciaPagamentoInicial.value, ">", form.referenciaPagamentoFinal.value)) {
					alert('A Referência de Pagamento Final deve ser maior ou igual a Referência de Pagamento Inicial!');
					dateField.value = "";
					dateField.focus();
					return false;
				}
			}
	
			return true;
		}
	}

	// entre 08/2001 a 12/2011
	function dateCheck(from, to, check) {
		var fDate, lDate, cDate;
	    fDate = Date.parse(from);
	    lDate = Date.parse(to);
	    cDate = Date.parse(check);
	    
	    return (cDate <= lDate && cDate >= fDate);
	}
	
</script>

</head>

<body leftmargin="5" topmargin="5">

	<html:form
	    action="/classificarLotePagamentosNaoClassificadosAjusteDesoAction"
	    method="post"
	    onsubmit="return validatePagamentoAjusteDesoActionForm(this);">

		<%@ include file="/jsp/util/cabecalho.jsp"%>
		<%@ include file="/jsp/util/menu.jsp" %>
		
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
				        	<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
				        	<td class="parabg">Baixa Forçada de Pagamento - Tobias Barreto</td>
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
								<strong>Localidade</strong>
							</td>
							
							<td>
								<html:text  
									property="localidade.descricaoComId" 
									size="30"
									maxlength="30" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000"
									/>
							</td>
						</tr>
						
						<tr>
							<td>
								<strong>Período Refer&ecirc;ncia do Pagamento: </strong>
							</td>					
							<td >						
								<html:text 
									property="referenciaPagamentoInicial"
									maxlength="7" 
									size="7" 
									onkeypress="javascript: return isCampoNumerico(event); "
									onkeyup="javascript: mascaraAnoMes(this, event); validaReferenciaPagamento(this);replicarReferenciaPagamento();"
									/>
									
								<strong>&nbsp;a&nbsp;</strong>
								
								<html:text 
									property="referenciaPagamentoFinal" 
									maxlength="7"
									size="7"
									onkeypress="javascript: return isCampoNumerico(event); "
									onkeyup="javascript: mascaraAnoMes(this, event); validaReferenciaPagamento(this);"
									/>(mm/aaaa)
									<!-- onblur="javascript: validaReferenciaPagamento(this);" -->
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
									<a onfocus="repetirDataPagamento();" href="javascript:abrirCalendario('ClassificarLotePagamentosNaoClassificadosAjusteDesoActionForm', 'dataPagamentoInicial')">
										<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" />
									</a>
									<strong>&nbsp;a&nbsp;</strong>
								<html:text maxlength="10"
									property="dataPagamentoFinal" 
									size="10"
									onkeypress="javascript:return isCampoNumerico(event); "
									onkeyup="javascript:mascaraData(this, event);"
									onblur="javascript:verificaDataMensagemPersonalizada(this,'Data do Pagamento Inválida');"
									/>
									<a href="javascript:abrirCalendario('ClassificarLotePagamentosNaoClassificadosAjusteDesoActionForm', 'dataPagamentoFinal')">
										<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" />
									</a>&nbsp;(dd/mm/aaaa)
							</td>
						</tr>
						
						<tr> 
		                	<td>
		                		<strong>Situa&ccedil;&atilde;o  do Pagamento</strong>
		                    </td>
		                    <td>
		                    	<strong>
									<html:text 
										property="pagamentoSituacao.descricaoComId" 
										size="30"
										maxlength="30" 
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" /> 
								</strong>
							</td>
		              	</tr>
				
						<tr>
							<td>
								<strong> 
									<font color="#FF0000"> 
										<input
											name="btLimpar" class="bottonRightCol" value="Limpar"
											type="button"
											onclick="window.location.href='/gsan/exibirClassificarLotePagamentosNaoClassificadosAjusteDesoAction.do?menu=sim';">
										<input type="button" name="Button" class="bottonRightCol"
											value="Cancelar" tabindex="6"
											onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
											style="width: 80px" /> 
									</font>
								</strong>
							</td>
							<td align="right">
								<input type="button" onclick="javascript:validarForm();" name="botaoListarPagamentos"
								class="bottonRightCol" value="Classificar Pagamentos">
							</td>
						</tr>
						
		        	</table>               
		          
				</td>
			</tr>
			
		</table>

		<%@ include file="/jsp/util/rodape.jsp"%>

	</html:form>
	
</body>
</html:html>
