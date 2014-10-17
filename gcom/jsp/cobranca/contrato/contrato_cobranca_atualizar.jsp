<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ page import="gcom.cobranca.contrato.CobrancaContratoRemuneracaoPorSucesso"%>
<%@ page import="gcom.cobranca.contrato.CobrancaContratoRemuneracaoPorProdutividade"%>
<%@ page import="gcom.cobranca.contrato.ContratoTipoRemuneracao"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

 <%@ include file="/jsp/util/titulo.jsp"%>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

 <html:javascript staticJavascript="false"  formName="AtualizarContratoCobrancaActionForm" />

 <script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
 <script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
 <script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
 <script>


	function reloadPagina() {
		document.AtualizarContratoCobrancaActionForm.action = 'exibirAtualizarContratoCobrancaAction.do?reloadPage=1';
		document.AtualizarContratoCobrancaActionForm.submit();
		window.focus();
	}

	function validarForm(form) {

		if(validateAtualizarContratoCobrancaActionForm(form)) {
			form.submit();
		}
	}

	function adicionarRemuneracaoProdutividade(){
    	var form = document.forms[0];
    	
    	if (form.remuneracaoTipo[1].checked) {
        	// Se remuneração por produtividade
    		abrirPopup('exibirAdicionarRemuneracaoProdutividadeContratoAction.do', 295, 450);
    	} else {
    		alert("Para adicionar uma linha, o tipo Remuneração por Produtividade deve estar marcado.");
    	}
    }
	
	function habilitarCamposSucesso() {
    	var form = document.forms[0];
    	form.btAdicionar.disabled = false;
    	form.btAdicionarProdutividade.disabled = true;
    }
    
    function habilitarCamposProdutividade() {
    	var form = document.forms[0];
    	form.btAdicionarProdutividade.disabled = false;
    	form.btAdicionar.disabled = true;
    }
	
	function removerLinha(posicaoLinha) {
 		var form = document.forms[0];
 		if (confirm("Confirma remoção?")){
 			form.action = "/gsan/removerRemuneracaoSucessoActionAlterarContrato.do?posicaoLinha=" + posicaoLinha + "&mapeamento=exibirAlterarContrato";
			form.submit();
		}
    }

	function removerRemuneracaoProdutividade(posicaoLinha){
		var form = document.forms[0];
 	 	
 	 	if (confirm("Confirma remoção?")){
 	 		form.action = "/gsan/removerRemuneracaoProdutividadeActionAlterarContrato.do?posicaoLinha=" + posicaoLinha + "&mapeamento=exibirAlterarContrato";
			form.submit();
		}
	}
    
    function adicionarRemuneracaoSucesso() {
    	var form = document.forms[0];
    	
    	if (form.remuneracaoTipo[0].checked) {
        	// Se Remuneração por sucesso
    		abrirPopup('exibirAdicionarRemuneracaoSucessoContratoAction.do', 295, 450);
    	} else {
    		alert("Para adicionar uma linha, o tipo Remuneração por Sucesso deve estar marcado.");
    	}
    }

    function inicializa(){
        var form = document.forms[0];
        // Verifica se tem algum radio marcado
        if (form.remuneracaoTipo[0].checked || form.remuneracaoTipo[1].checked){

            // Caso radio remuneração por sucesso esteja marcado
			if (form.remuneracaoTipo[0].checked){
				form.btAdicionar.disabled = false;
				form.btAdicionarProdutividade.disabled = true;
			}else{
				// Caso radio remuneração por produtividade esteja marcado
				form.btAdicionar.disabled = true;
				form.btAdicionarProdutividade.disabled= false;
			}
        }else{
            form.btAdicionar.disabled = true;
			form.btAdicionarProdutividade.disabled = true;
        }
        
    }

	function alterarCamposTiposRemuneracao(){
		var form = document.forms[0];
		if (form.remuneracaoTipo[0].checked) {
			habilitarCamposSucesso();
		} else {
			habilitarCamposProdutividade();
		}
	}
	
 </script>
</head>

<body leftmargin="5" topmargin="5" onload="inicializa();setarFoco('${requestScope.nomeCampo}');">
<div id="formDiv">

<html:form action="/atualizarContratoCobrancaAction.do" method="post"
	name="AtualizarContratoCobrancaActionForm"
	type="gcom.gui.cobranca.contrato.AtualizarContratoCobrancaActionForm"
	onsubmit="return validateAtualizarContratoCobrancaActionForm(this);">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<input type="hidden" name="tipoPesquisa" />

	<html:hidden property="okContratoCobranca" />

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
			<td width="655" valign="top" class="centercoltext"><!--Início Tabela Reference a Páginação da Tela de Processo-->
			<table>
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="<bean:message key='caminho.imagens'/>parahead_left.gif" /></td>
					<td class="parabg">Atualizar Contrato Cobran&ccedil;a</td>
					<td width="11" valign="top"><img border="0" src="<bean:message key='caminho.imagens'/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			 
			 <table width="100%" border="0">
			 	<tr> 
                	<td colspan="2">Para atualizar o contrato de cobrança, informe os dados abaixo:</td>
              	</tr>
              	
				<tr>
					<td width="32%" class="style3"><strong>Número Contrato:<font color="#FF0000">*</font></strong></td>
					<td width="68%" colspan="2"><strong><b><span class="style2"> 
					  <html:text
						property="numeroContrato" size="10" maxlength="10"
						onkeyup="javascript:verificaNumeroInteiro(this);"/></span></b></strong>
				  </td>
				</tr>
				
				<tr>
					<td><strong>Empresa:<font color="#FF0000">*</font></strong></td>
					<td colspan="2">
						<html:select name="AtualizarContratoCobrancaActionForm" property="idEmpresa">
						  <html:option value="">&nbsp;</html:option>
						  <logic:present scope="session" name="colecaoEmpresa">
							<html:options name="session" collection="colecaoEmpresa" labelProperty="descricao" property="id" />
						  </logic:present>			
						</html:select>
					</td>
				</tr>
				
				<tr>
					<td><strong>Data Início:<font color="#FF0000">*</font></strong></td>
					<td><html:text property="dtInicioContrato" size="10"
						maxlength="10" 
						onkeyup="mascaraData(this, event);"/>
					<a
						href="javascript:abrirCalendario('AtualizarContratoCobrancaActionForm', 'dtInicioContrato');">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
					dd/mm/aaaa</td>
				</tr>

				<tr>
					<td><strong>Data Fim:</strong></td>
					<td><html:text property="dtFimContrato" size="10" maxlength="10"
						onkeyup="mascaraData(this, event);" /> <a
						href="javascript:abrirCalendario('AtualizarContratoCobrancaActionForm', 'dtFimContrato')">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
					dd/mm/aaaa</td>
				</tr>
              	
             	<tr>
					<td><strong>Data Encerramento:</strong></td>
					<td><html:text property="dtEncerramentoContrato" size="10"
						maxlength="10" onkeyup="mascaraData(this, event);" /> <a
						href="javascript:abrirCalendario('AtualizarContratoCobrancaActionForm', 'dtEncerramentoContrato')">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
					dd/mm/aaaa</td>
				</tr>
				
				<tr>
					<td width="32%" class="style3"><strong>Qtd. Mín. Dias Vencidos:</strong></td>
					<td width="68%" colspan="2"><strong><b><span
						class="style2"> <html:text property="qtMinimaDiasVencidos"
						size="1" maxlength="4"
						onkeyup="javascript:verificaNumeroInteiro(this);" /></span></b></strong></td>
				</tr>
				
				<tr>
					<td width="32%" class="style3"><strong>Qtd. Dias Reincidência:</strong></td>
					<td width="68%" colspan="2"><strong><b><span
						class="style2"> <html:text property="qtDiasReincidencia"
						size="1" maxlength="4"
						onkeyup="javascript:verificaNumeroInteiro(this);" /></span></b></strong></td>
				</tr>
				
				<tr>
          			<td><strong>Percentual de Remuneração:</strong></td>
          			<td colspan="3"><html:text property="percRemuneracaoReincidencia" size="2" maxlength="6" 
			            onkeyup="formataValorMonetario(this, 6); verificaCampoVazio(percRemuneracaoReincidencia, valorFixo);" 
			            style="text-align:right;"/>
          		</td>
        		</tr>
             	
              	<tr> 
                	<td><strong> <font color="#FF0000"></font></strong></td>
                	<td align="right"> <div align="left"><strong><font color="#FF0000">*</font></strong>Campos obrigat&oacute;rios</div></td>
              	</tr>
              	
              	
              	<tr >
					<td colspan="2">
					  <table width="100%" >
						<tr bgcolor="#99CCFF">
							<td align="center" width="10%" colspan="2"><strong>Tipo de Remuneração</strong></td>
						</tr>
						<tr>
						  <td><html:radio property="remuneracaoTipo" value="<%= "" + ContratoTipoRemuneracao.TIPO_SUCESSO %>" onclick="javascript:alterarCamposTiposRemuneracao();" /><strong>Remuneração por Sucesso</strong></td>
						  <td><html:radio property="remuneracaoTipo" value="<%= "" + ContratoTipoRemuneracao.TIPO_PRODUTIVIDADE %>" onclick="javascript:alterarCamposTiposRemuneracao();"/><strong>Remuneração por Produtividade</strong></td>
						</tr>
						<!-- <tr>
						<td>
						 <table>
						   <tr>
							<td class="style3"><strong>Qtd. Débito (até):</strong></td>
							<td colspan="2"><strong><b><span class="style2"> 
					  		 <html:text property="qtdDebitoSucesso" size="10" maxlength="8" onkeyup="javascript:verificaNumeroInteiro(this);" disabled="true"/></span></b></strong>
				            </td>						 
						   </tr>
						   <tr>
						 	<td  class="style3"><strong>Valor (até):</strong></td>
							<td  colspan="2"><strong><b><span class="style2"> 
					  		 <html:text property="valorSucesso" size="10" maxlength="13" onkeyup="formataValorMonetario(this, 11);" disabled="true"/></span></b></strong>
				            </td>	
						   </tr>
						 </table>
						</td>
						<td>
						 <table>
						   <tr>
							<td class="style3"><strong>Qtd. Débito (até):</strong></td>
							<td colspan="2"><strong><b><span class="style2">
					  		 <html:text property="qtdDebitoProdutividade" size="10" maxlength="8" onkeyup="javascript:verificaNumeroInteiro(this);" disabled="true"/></span></b></strong>
				            </td>
						   </tr>
						   <tr>
						 	<td class="style3"><strong>Valor (até):</strong></td>
							<td colspan="2"><strong><b><span class="style2"> 
					  		 <html:text property="valorProdutividade" size="10" maxlength="13" onkeyup="formataValorMonetario(this, 11);" disabled="true"/></span></b></strong>
				            </td>
						   </tr>
						  </table>
						</td>
						</tr> -->
					  </table>
					</td>
				</tr>
				
				
				<tr></tr>
				<tr>
				<%
					int cont = 0;
				%>
						<td colspan="4">
							<table width="100%" border="0">
								<tr>
									<td height="17" colspan="3"><strong>Remuneração por sucesso:</strong></td>
									<td align="right">
										<input type="button" class="bottonRightCol" name="btAdicionar"
											   value="Adicionar"  style="width: 80px"
											   onclick="javascript:adicionarRemuneracaoSucesso()" disabled="true">
									</td>
								</tr>
								
								
								
								<tr>
									<td colspan="4">
										<table width="100%" cellpadding="0" cellspacing="0">
											<tr>
												<td>
													<table width="100%" bgcolor="#99CCFF">
														<tr bgcolor="#99CCFF">
															<td align="center" width="10%"><strong>Remover</strong></td>
															<td align="center" width="20%"><strong>Dias Vencidos</strong></td>
															<td align="center" width="20%"><strong>% Remun.</strong></td>
															<td align="center" width="20%"><strong>Valor</strong></td>
															<td align="center" width="20%"><strong>% Parcela</strong></td>
														</tr>
													</table>
												</td>
											</tr>
					
											
											<logic:present name="colecaoRemuneracaoSucesso">
											<tr>
												<td>
													<div style="width: 100%; height: 100; overflow: auto;">
													<table width="100%" align="center" bgcolor="#99CCFF">
													<logic:iterate name="colecaoRemuneracaoSucesso" id="remuneracaoSucesso" type="CobrancaContratoRemuneracaoPorSucesso">
														<%cont = cont + 1;
														if (cont % 2 == 0) {%>
														<tr bgcolor="#cbe5fe">
														<%} else {%>
														<tr bgcolor="#FFFFFF">
														<%}%>
															<td align="center" width="10%" valign="middle">
															    <a href="javascript:removerLinha('<%= (cont-1) %>')">
																   <img src="<bean:message key='caminho.imagens'/>Error.gif" border="0">
																</a>
															</td>
															<td width="20%" align="center">
																<%=remuneracaoSucesso.getDiasVencidos()%>
															</td>
															<td align="center" width="20%">
																<logic:present name="remuneracaoSucesso" property="percentualRemuneracao">
																	<bean:write name="remuneracaoSucesso" property="percentualRemuneracao" formatKey="money.format"/>
																</logic:present>
																<logic:notPresent name="remuneracaoSucesso" property="percentualRemuneracao">
																	
																</logic:notPresent>
															</td>
															<td align="center" width="20%">
																<logic:present name="remuneracaoSucesso" property="valorFixo">
																	<bean:write name="remuneracaoSucesso" property="valorFixo" formatKey="money.format"/>
																</logic:present>
																<logic:notPresent name="remuneracaoSucesso" property="valorFixo">
																	
																</logic:notPresent>
															</td>
															<td align="center" width="20%">
																<logic:present name="remuneracaoSucesso" property="percentualParcelaPaga">
																	<bean:write name="remuneracaoSucesso" property="percentualParcelaPaga" formatKey="money.format"/>
																</logic:present>
																<logic:notPresent name="remuneracaoSucesso" property="percentualParcelaPaga">
																	&nbsp;
																</logic:notPresent>
															</td>

														</tr>
													</logic:iterate>
													</table>
													</div>
												</td>
											</tr>
											</logic:present>
											
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				
					<tr>
						<td colspan="4">
							<table width="100%" border="0">
								<tr>
									<td height="17" colspan="3"><strong>Remuneração por Produtividade:</strong></td>
									<td align="right">
										<input type="button" class="bottonRightCol" name="btAdicionarProdutividade" value="Adicionar" style="width: 80px" onclick="javascript:adicionarRemuneracaoProdutividade()" disabled="true">
									</td>
								</tr>
															
								<tr>
									<td colspan="4">
										<table width="100%" cellpadding="0" cellspacing="0">
											<tr>
												<td>
													<table width="100%" bgcolor="#99CCFF">
														<tr bgcolor="#99CCFF">
															<td align="center" width="10%"><strong>Remover</strong></td>
															<td align="center" width="70%"><strong>Tipo Servico</strong></td>
															<td align="center"><strong>Valor</strong></td>
														</tr>
													</table>
												</td>
											</tr>
											
											<logic:present name="colecaoRemuneracaoProdutividade">
											<tr>
												<td>
													<div style="width: 100%; height: 100; overflow: auto;">
													<table width="100%" align="center" bgcolor="#99CCFF">
													<logic:iterate name="colecaoRemuneracaoProdutividade" id="remuneracaoProdutividade" type="CobrancaContratoRemuneracaoPorProdutividade">
														<%cont = cont + 1;
														if (cont % 2 == 0) {%>
														<tr bgcolor="#cbe5fe">
														<%} else {%>
														<tr bgcolor="#FFFFFF">
														<%}%>
															<td align="center" width="10%" valign="middle">
															    <a href="javascript:removerLinha('<%= (cont-1) %>')">
																   <img src="<bean:message key='caminho.imagens'/>Error.gif" border="0">
																</a>
															</td>
															<td width="20%" align="center">
																<%=remuneracaoProdutividade.getServicoTipo().getDescricao()%>
															</td>															
															<td align="center" width="20%">
																<logic:present name="remuneracaoProdutividade" property="valor">
																	<bean:write name="remuneracaoProdutividade" property="valor" formatKey="money.format"/>
																</logic:present>
																<logic:notPresent name="remuneracaoProdutividade" property="valor">
																	
																</logic:notPresent>
															</td>															

														</tr>
													</logic:iterate>
													</table>
													</div>
												</td>
											</tr>
											</logic:present>
																									
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
              	
              	<table width="100%" border="0">
	              <tr>
					<td>
						<logic:present name="manter" scope="session">
							<input type="button" name="ButtonReset" class="bottonRightCol" value="Voltar"
								onClick="javascript:window.location.href='/gsan/exibirManterContratoCobrancaAction.do'">
						</logic:present>

						<logic:notPresent name="manter" scope="session">
							<input type="button" name="ButtonReset"  class="bottonRightCol" value="Voltar"
								onClick="javascript:window.location.href='/gsan/exibirFiltrarContratoCobrancaAction.do'">
						</logic:notPresent> 
					<input name="Button" type="button" class="bottonRightCol"
						value="Desfazer" align="left"
						onclick="window.location.href='<html:rewrite page="/exibirAtualizarContratoCobrancaAction.do?idContrato=${AtualizarContratoCobrancaActionForm.idContrato}"/>'">
					<input name="Button" type="button" class="bottonRightCol"
							value="Cancelar" align="left"
							onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"></td>
					<td align="right"><input name="Button" type="button"
						class="bottonRightCol" value="Atualizar" align="right"
						onClick="javascript:validarForm(document.forms[0]);"></td>
				</tr>
	              
             	</table>
              	
			 </table>
			
			<p>&nbsp;</p>
		</tr>
		<tr>
			<td colspan="3"></td>
		</tr>
	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</div>
</body>

<%@ include file="/jsp/util/telaespera.jsp"%>

</html:html>