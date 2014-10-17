<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ page import="gcom.cobranca.bean.ContaValoresHelper" isELIgnored="false"%>
<%@ page import="gcom.cobranca.bean.GuiaPagamentoValoresHelper" isELIgnored="false"%>
<%@ page import="gcom.util.ConstantesSistema" isELIgnored="false"%>
<%@ page import="java.util.Collection" isELIgnored="false"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<%@page import="gcom.cobranca.CobrancaDocumento"%>
<%@page import="gcom.cobranca.bean.ContaHelper"%><html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">
<!--
	// Para cada item marcado ou desmarcado, atualizar 'chave' no form
	function montarChaveCobrancaDocumento(objeto) {
		
		var form = document.forms[0];
		var retorno = form.chavesDocumentoCobranca.value;
		var valorObjeto = objeto.value;
		var selecionados = form.elements['cobrancaDocumentoSelecionadas'];

		if (objeto.checked) {

			if (!chaveJaSelecionada(valorObjeto,retorno)) {
				if (retorno != null && retorno != '' && retorno != 'undefined') {
					retorno = retorno+"$"+valorObjeto;
				} else {
					retorno = valorObjeto;
				}
			}
		    
			for (i=0; i< selecionados.length; i++) {
				if(selecionados[i].value == valorObjeto) {
					selecionados[i].checked = true;
				}
			}
		} else {
			myString = new String(form.chavesDocumentoCobranca.value);
			splitString = myString.split("$");
			
			for (i=0; i< splitString.length; i++) {

				if (splitString[i] == valorObjeto) {
					splitString.splice( i, 1 );
					break;
				}
			}
			
			retorno = "";
			for (i=0; i< splitString.length; i++) {
				if(retorno == ""){
					retorno = splitString[i];
				} else {
					retorno = retorno+"$"+splitString[i];
				}
			}

			for (i=0; i< selecionados.length; i++) {
				if (selecionados[i].value == valorObjeto) {
					selecionados[i].checked = false;
				}
			}
		}
		form.chavesDocumentoCobranca.value = retorno;
	}
	
	function chaveJaSelecionada(valorObjeto, chavePesquisa) {
		var retorno = false;
		var form = document.forms[0];

		if (chavePesquisa != null && chavePesquisa != "") {

			myString = new String(chavePesquisa);
			splitString = myString.split("$");

			for (i=0; i< splitString.length; i++) {
				if(splitString[i] == valorObjeto) {
					retorno = true;
					break;
				}
			}
		}
		return retorno;
	}
	
	
	// Marcar ou desmarcar todos os itens
	function selecionarCobrancaDocumento() {
		var chaves = "";
		var form = document.forms[0];
		var selecionados = form.elements['cobrancaDocumentoSelecionadas'];

		// Se o 1º estiver marcado, desmarcar todos
		if (selecionados[1] != null && selecionados[1].checked) {
			for (i=0; i < selecionados.length; i++) {
				selecionados[i].checked = false;
			}
		
		} else {
			// Se o 1º não estiver marcado, marcar todos
			for (i=0; i < selecionados.length; i++) {
				selecionados[i].checked = true;
				if(chaves == "") {
					chaves = selecionados[i].value;
				} else {
					chaves = chaves + "$" + selecionados[i].value;
				}
			}
		}
		form.chavesDocumentoCobranca.value = chaves;
	}

	
	function validarForm(){
    	var form = document.forms[0];
	   		submeterFormPadrao(form);
	}

function validaDataCompleta(data, event){
	if(mascaraData(data, event)){
		return false;
	}
	
	return verificaData(data);
}

function verificarDataSituacaoAcaoGeral(){

	var form = document.forms[0];

	if(form.dataSituacaoAcaoGeral.value != ''){
		for (i=0; i < form.elements.length; i++) {
		if(form.elements[i].type == "text" && form.elements[i].id.length > 1){
				if(form.elements[i].id == "data"){
					form.elements[i].value = form.dataSituacaoAcaoGeral.value;
				}
		}
	  }
	}
}

function verificarDataEntrega(data){

	if(data.value != null && data.value != ''){
		return verificaData(data);
	}else{
		return true;
	}
}
	
-->
</script>
</head>

<body leftmargin="5" topmargin="5">
<html:form action="/informarEntregaDocumentoCobrancaAction"
	name="InformarEntregaDocumentoCobrancaActionForm"
	type="gcom.gui.cobranca.InformarEntregaDocumentoCobrancaActionForm"
	method="post">
	
	<html:hidden property="chavesDocumentoCobranca"/>

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp" %>


<table width="770" border="0" cellspacing="4" cellpadding="0">


			<td width="149" valign="top" class="leftcoltext">
			<input type="hidden" name="numeroPagina" value="1"/>
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
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Informar Entrega do Documento Cobrança</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<!-- Início do Corpo - Roberta Costa -->
			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="4">Para inserir a data de entrega dos documentos de cobrança preencha os campos abaixo:</td>
				</tr>
				
				<tr> 
					<td>
						<strong>Quantidade de Documentos de Cobrança:</strong>
					</td>
			        <td>
						<html:text property="quantidadeDocumentos" 
							size="40"
							maxlength="40" 
							readonly="true"
							disabled="true"
						/>
					</td>
				</tr>
				<tr> 
					<td>
						<strong>Ação de Cobrança:</strong>
					</td>
			        <td>
						<html:text property="cobrancaAcaoDescricao" 
							size="40"
							maxlength="40" 
							readonly="true"
							disabled="true"
						/>
					</td>
				</tr>
				<tr> 
					<td>
						<strong>Data Entrega Geral:</strong>
					</td>
			        <td>
						<strong> 
				       
				       <input    type="text"   		onkeyup="mascaraData(this,event);verificarDataSituacaoAcaoGeral();"
					       							onblur="verificarDataEntrega(this);"
													size="10" maxlength="10"
													name="dataSituacaoAcaoGeral"	
													formatKey="date.format"
													tabindex="1" 
													/>
				       
				        <a href="javascript:abrirCalendarioReplicandoVarios('InformarEntregaDocumentoCobrancaActionForm', 'dataSituacaoAcaoGeral', 'nDataEntrada');">
					     <img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>&nbsp;(dd/mm/aaaa) 
			      		</strong> 
			      		
					</td>
				</tr>
				
				<tr>
					<td colspan="4" height="23"><strong>Documentos de Cobrança:</strong></td>
				</tr>
				<%int contAux = 1;%>
				<% int cont = 0;%>
				
				<tr>
					<td colspan="4">
					<table width="100%" bgcolor="#99CCFF" id="tabela">
						<logic:empty name="colecaoDocumentosCobranca" scope="session">
							<tr bgcolor="#90c7fc">
								<td align="center" width="10%"><strong>Todos</strong></td>
								<td align="center" width="9%"><strong>Linha</strong></td>
								<td align="center" width="13%"><strong>Empresa</strong></td>
								
								<logic:present name="indicadorOrdenacaoDocumentos">
									<logic:equal name="indicadorOrdenacaoDocumentos" value="inscricao">
										<td align="center" width="15%"><strong>Local/Setor/Quadra</strong></td>
									</logic:equal> 
									
									<logic:equal name="indicadorOrdenacaoDocumentos" value="comando">
										<td align="center" width="15%"><strong>Sequencial</strong></td>
									</logic:equal>
								</logic:present>
								
								<td align="center" width="15%"><strong>Imóvel</strong></td>
								<td align="center" width="10%"><strong>Data de Emissão</strong></td>
								<td align="center" width="30%"><strong>Data de Entrega</strong></td>
							</tr>
						</logic:empty>
						<logic:notEmpty name="colecaoDocumentosCobranca" scope="session">
							<tr bgcolor="#90c7fc">
								<td align="center" width="10%"><a href="javascript:selecionarCobrancaDocumento();"><strong>Todos</strong></a></td>
								<td align="center" width="9%"><strong>Linha</strong></td>
								<td align="center" width="13%"><strong>Empresa</strong></td>
								
								<logic:present name="indicadorOrdenacaoDocumentos">
									<logic:equal name="indicadorOrdenacaoDocumentos" value="inscricao">
										<td align="center" width="15%"><strong>Local/</br>Setor/</br>Quadra</strong></td>
									</logic:equal> 
									
									<logic:equal name="indicadorOrdenacaoDocumentos" value="comando">
										<td align="center" width="15%"><strong>Sequencial</strong></td>
									</logic:equal>
								</logic:present>
								
								<td align="center" width="15%"><strong>Imóvel</strong></td>
								<td align="center" width="10%"><strong>Data de Emissão</strong></td>
								<td align="center" width="30%"><strong>Data de Entrega</strong></td>
								</td>
							</tr>
							<tr>
								<td height="100" colspan="7">
								<div style="width: 100%; height: 100%; overflow: auto;">
								<table width="100%">
									<logic:iterate name="colecaoDocumentosCobranca"
										type="CobrancaDocumento" id="cobrancaDocumento">
											<%cont = cont + 1;
											if (cont % 2 == 0) {%>
											<tr bgcolor="#cbe5fe">
											<%} else {%>
											<tr bgcolor="#FFFFFF">
											<%}%>
											<td width="10%">
												<input type="checkbox" name="cobrancaDocumentoSelecionadas" onchange="javascript:montarChaveCobrancaDocumento(this);"
													value="<bean:write name="cobrancaDocumento" property="id"/>" />
					                		</td>
											<td width="10%" align="center" height="20">
											<%=contAux%>
											<%contAux = contAux + 1;%>
									</td>
									
									<logic:notEmpty property="empresa" name="cobrancaDocumento">
									<td width="15%" align="center"><bean:write name="cobrancaDocumento" property="empresa.descricaoAbreviada" /></td>
									</logic:notEmpty><logic:empty property="empresa" name="cobrancaDocumento">
									<td width="15%" align="center"></td>
									</logic:empty>
									
									
									<logic:present name="indicadorOrdenacaoDocumentos">
										<logic:equal name="indicadorOrdenacaoDocumentos" value="inscricao">
												<td width="15%" align="center"><bean:write name="cobrancaDocumento" property="localidade.id"  />/
												<bean:write name="cobrancaDocumento" property="codigoSetorComercial"  />/
												<bean:write name="cobrancaDocumento" property="numeroQuadra"  /></td>
										</logic:equal> 
										
										<logic:equal name="indicadorOrdenacaoDocumentos" value="comando">
											<td width="15%" align="center"><bean:write name="cobrancaDocumento" property="numeroSequenciaDocumento" /></td>
										</logic:equal>
									</logic:present>
									
									<logic:notEmpty property="imovel" name="cobrancaDocumento">
									<td width="15%" align="center"><bean:write name="cobrancaDocumento" property="imovel.id" /></td>
									</logic:notEmpty><logic:empty property="imovel" name="cobrancaDocumento">
									<td width="15%" align="center"></td>
									</logic:empty>
											
									
									<logic:notEmpty property="emissao" name="cobrancaDocumento">
									<td width="10%" align="center"><bean:write name="cobrancaDocumento" property="emissao" formatKey="date.format"/></td>
									</logic:notEmpty><logic:empty property="emissao" name="cobrancaDocumento">
									<td width="10%" align="center"></td>
									</logic:empty>
									
									
									<td width="30%" align="center">
									
									<input type="text"  onkeyup="mascaraData(this,event);" 
													name="nDataEntrada<bean:write name="cobrancaDocumento" property="id"/>" 
													onblur="verificarDataEntrega(this);"
													size="10" maxlength="10"													
													id="data"
													/>
													
				       				<a  href="javascript:abrirCalendario('InformarEntregaDocumentoCobrancaActionForm', 'nDataEntrada<bean:write name="cobrancaDocumento" property="id"/>');">
					     			<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> 
					    			</td>
					     			
										</tr>
									</logic:iterate>
								</table>
								</div>
								</td>
							</tr>
						</logic:notEmpty>
					</table>
					</td>
				</tr>
				
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				
				<tr>
					<td colspan="3" width="100%" height="1px" bgcolor="#000000"></td>
				</tr>				
				
				<tr>
					<td align="left">
					
						<input type="button" name="ButtonReset"
						class="bottonRightCol" value="Voltar Filtro"
						onclick="window.location.href='<html:rewrite page="/exibirFiltrarEntregaDocumentoCobrancaAction.do"/>'">&nbsp;
						
						<input type="button"
							name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
							onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"></td>
					<td colspan="2" height="24" align="right">
					
					<input type="button" name="Button" class="bottonRightCol"
							 value="Informar data de entrega"
							onClick="javascript:validarForm();" /></td>
					<td></td>
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