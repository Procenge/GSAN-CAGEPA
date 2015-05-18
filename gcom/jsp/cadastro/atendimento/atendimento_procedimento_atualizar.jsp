<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@page import="gcom.cadastro.atendimento.AtendimentoProcedimento"%>
<%@page import="gcom.gui.GcomAction"%>
<%@ page import="gcom.util.ConstantesSistema" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css"  type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="AtualizarAtendimentoProcedimentoActionForm" />

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">

	function validarForm(form){
		
		if(validateAtualizarAtendimentoProcedimentoActionForm(form)){
			if (form.indicadorImovel.checked || form.indicadorCliente.checked) {
	    		form.submit();
			} else {
				alert("Informe se será solicitado a informação do Imóvel ou Cliente.")
			}	
		}			
	
	}
	
	function carregarSolicitacaoTipoEspecificacao() {
		var form = document.forms[0];
		
		form.idSolicitacaoTipoEspecificacao.value = "";
		form.action = '/gsan/exibirAtualizarAtendimentoProcedimentoAction.do?carregarSolicitacaoTipoEspecificacao=1';
		form.submit();
	}
	
	 function adicionarDocumentoTipoPessoa(){
		 	var form = document.forms[0];
		 	var msg="";
		 	
		 	if (form.idTipoDocumento == null || 
		 			form.idTipoDocumento.value == null || 
		 			form.idTipoDocumento.value == "" || 
		 			form.idTipoDocumento.value == "-1" ) {		 		
		 		msg="Informe o Tipo de Documento.\n";
		 	}
		 	
		 	if (form.idTipoPessoa == null || 
		 			form.idTipoPessoa.value == null || 
		 			form.idTipoPessoa.value == "" || 
		 			form.idTipoPessoa.value == "-1" ) {		 		
		 		msg=msg+"Informe o Tipo de Pessoa.\n";
		 	}

	 		if (msg == "") {
			 	form.action = "removerAtualizarAdicionarAtendProcDocumentosPessoaTipoAction.do?operacao=inserir"
			 	form.submit();
			} else {
				alert(msg);
			}
	 }	 
	 
	 function removerDocumentoTipoPessoa(time){
		 	var form = document.forms[0];
		 	
		 	
		 	if(confirm('Confirma remoção?')){
			 	form.action = "removerAtualizarAdicionarAtendProcDocumentosPessoaTipoAction.do?operacao=remover&idDocumentoTipoPessoRemovao=" + time;
			 	form.submit();
		 	}
	 }		 
	 
	 function adicionarNormaProcedimental(){
		 	var form = document.forms[0];
		 	var msg="";
		 	
		 	if (form.idNormaProcedimental == null || 
		 			form.idNormaProcedimental.value == null || 
		 			form.idNormaProcedimental.value == "" || 
		 			form.idNormaProcedimental.value == "-1" ) {
		 		msg="Informe a Norma Procedimental.\n";
		 	}

	 		if (msg == "") {		 	
			 	form.action = "removerAtualizarAdicionarAtendProcNormaProcedimentalAction.do?operacao=inserir"
			 	form.submit();
			} else {
				alert(msg);
			}		 	
	 }	 
	 
	 function removerNormaProcedimental(time){
		 	var form = document.forms[0];
		 	
		 	if(confirm('Confirma remoção?')){
			 	form.action = "removerAtualizarAdicionarAtendProcNormaProcedimentalAction.do?operacao=remover&idNormaProcedimentalRemovao=" + time;
			 	form.submit();
		 	}
	 }	 	

</script>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('${requestScope.nomeCampo}');">

<html:form action="/atualizarAtendimentoProcedimentoAction" method="post"
	name="AtualizarAtendimentoProcedimentoActionForm"
	type="gcom.gui.seguranca.acesso.AtualizarAtendimentoProcedimentoActionForm"
	onsubmit="return validateAtualizarAtendimentoProcedimentoActionForm(this);">

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
			
			<td width="600" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Atualizar AtendimentoProcedimento</td>
					<td width="11" valign="top">
						<img border="0" src="imagens/parahead_right.gif" />
					</td>
				</tr>

			</table>

			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para atualizar a Procedimento de Atendimento, informe os dados
					abaixo:</td>
				</tr>

				<tr>
					<td><strong>Código:</strong></td>
					<td>
						<html:hidden property="id" /> 
						<bean:write name="AtualizarAtendimentoProcedimentoActionForm" property="id" />
					</td>
				</tr>

				<tr>
					<td><strong>Funcionalidade:<font color="#FF0000">*</font></strong></td>
					<td colspan="3"><html:text property="idFuncionalidade" size="9"
						maxlength="9" tabindex="2" disabled="true"  />

							<logic:empty name="AtualizarAtendimentoProcedimentoActionForm"
								property="idFuncionalidade">
								<html:text property="descricaoFuncionalidade" value="" size="30"
									maxlength="45" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:empty>
							<logic:notEmpty name="AtualizarAtendimentoProcedimentoActionForm"
								property="idFuncionalidade">
								<html:text property="descricaoFuncionalidade" size="30"
									maxlength="45" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notEmpty>
				</tr>				
				<tr>
					<td width="162"><strong>Descri&ccedil;&atilde;o:<font
						color="#FF0000">*</font></strong></td>
					<td>
					<strong> 
					<html:text property="descricao" size="40" maxlength="60" style="text-transform: none;"/> 
					</strong>
					</td>
				</tr>
				
	              <tr> 
	                <td><strong>Tipo de Solicita&ccedil;&atilde;o:</strong></td>
	                <td colspan="6"><span class="style2"><strong>
						<html:select property="idSolicitacaoTipo" 
							style="width: 350px; height:100px;" disabled="true"
							multiple="false" 
							onchange="javascript:carregarSolicitacaoTipoEspecificacao();" 
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
						<html:select property="idSolicitacaoTipoEspecificacao" 
							style="width: 350px;" disabled="true"
							multiple="false" 
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
					<td><strong>Informar Imóvel:<font color="#FF0000">*</font></strong></td>
					<td>
						<table width="100%" border="0">
							<tr>
								<td align="left">
									<html:checkbox property="indicadorImovel" value="1" />
								</td>
								
								<td align="right" >
								</td>
							</tr>
						</table>
					</td>
					
				</tr>	
				
				<tr>
					<td><strong>Informar Cliente:<font color="#FF0000">*</font></strong></td>
					<td>
						<table width="100%" border="0">
							<tr>
								<td align="left">
									<html:checkbox property="indicadorCliente" value="1" />
								</td>
								
								<td align="right" >
								</td>
							</tr>
						</table>
					</td>
					
				</tr>
				
				<tr>
					<td><strong>Permite prosseguir o atendimento sem a documentação obrigatória:</strong></td>
					<td>
						<table width="100%" border="0">
							<tr>
								<td align="left">
									<html:checkbox property="indicadorDispensadoDocumentacaoObrigatoria" value="1" />
								</td>
								
								<td align="right" >
								</td>
							</tr>
						</table>
					</td>
					
				</tr>					
				
			  <tr>
				<td><strong>Indicador de Uso:</strong></td>
				<td align="right">
				  <div align="left">
					<html:radio property="indicadorUso" tabindex="9" value="<%=ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>" /><strong>Ativo</strong>
					<html:radio property="indicadorUso"	tabindex="9" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>" /><strong>Inativo</strong>
				  </div>
				</td>
			  </tr>										              		
				
				<tr>
					<td colspan="2">
					<hr>
					</td>
				</tr>

				<tr>
					<td><strong>Documentos/Tipo de Pessoas:</strong></td>
					<td align="right">
					</td>
				</tr>

				<tr>
					<html:hidden property="descricaoTipoDocumento"/>
					<td><strong>Tipo de Documento:<font color="#FF0000">*</font></strong></td>
					<td><html:select property="idTipoDocumento" onchange="document.forms[0].descricaoTipoDocumento.value = this[this.selectedIndex].innerText;" >
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoAtendimentoDocumentoTipo"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				
				<tr>
					<td><strong>Tipo Pessoa:<font color="#FF0000">*</font></strong></td>
					<html:hidden property="descricaoTipoPessoa"/>
										
					<td>
						<table width="100%" border="0" cellpadding="0" cellspacing="0" >
							<tr>
								<td align="left"><html:select property="idTipoPessoa" onchange="document.forms[0].descricaoTipoPessoa.value = this[this.selectedIndex].innerText;" >>
										<html:option value="-1">&nbsp;</html:option>
										<html:options collection="colecaoAtendimentoPessoaTipo"
											labelProperty="descricao" property="id" /></html:select>
								</td>
								
								<td align="right" >
								</td>
							</tr>
						</table>
					</td>
					
				</tr>	
				
				<tr>
					<td><strong>Obrigatório:<font color="#FF0000">*</font></strong></td>
					<td>
						<table width="100%" border="0">
							<tr>
								<td align="left">
									<html:checkbox	property="indicadorDocumentoObrigatorio" value="1" />
								</td>
								
								<td align="right" ><input name="Button" type="button"
										class="bottonRightCol" value="Adicionar" align="right"
										onclick="javascript:adicionarDocumentoTipoPessoa()" />
								</td>
							</tr>
						</table>
					</td>
					
				</tr>					
				

				<tr>
					<td colspan="2">
					<table width="100%" bgcolor="#99CCFF">


						<tr bordercolor="#000000">
							<td align="center" width="10%" bgcolor="#90c7fc"><strong>Remover</strong></td>
							<td width="35%" bgcolor="#90c7fc"><strong>Tipo Documento</strong></td>
							<td width="35%" bgcolor="#90c7fc"><strong>Tipo Pessoa</strong></td>
							<td width="10%" bgcolor="#90c7fc"><strong>Obrigatório</strong></td>
						</tr>
						<%--Esquema de paginação--%>

						<logic:present name="colecaoAtendProcDocumentoPessoaTipo" scope="session" >
							<%int cont = 0;%>
							<logic:iterate id="atendProcDocumentoPessoaTipo"
								name="colecaoAtendProcDocumentoPessoaTipo">
								<%cont = cont + 1;
							if (cont % 2 == 0) {%>
								<tr bgcolor="#FFFFFF">
									<%} else {

							%>
								<tr bgcolor="#cbe5fe">
									<%}%>

									<td width="10%">
									<div align="center"><font color="#333333"> <img width="14"
										height="14" border="0" onclick="javascript:removerDocumentoTipoPessoa('<bean:write name="atendProcDocumentoPessoaTipo" property="ultimaAlteracao.time" />');"
										src="<bean:message key="caminho.imagens"/>Error.gif"
										 />
									</font></div>
									</td>
									
									<td width="35%">
										<div align="center">
											<bean:write name="atendProcDocumentoPessoaTipo"	property="atendimentoDocumentoTipo.descricao" />
										</div>
									</td>
									<td width="35%">
									<div align="center"><bean:write name="atendProcDocumentoPessoaTipo"
										property="atendimentoPessoaTipo.descricao" /></div>
									</td>									
									<td width="10%">
									<div align="center">
									
									  <logic:equal name="atendProcDocumentoPessoaTipo" property="indicadorDocumentoObrigatorio" value="1">     
									     Sim
									  </logic:equal>     
 
									  <logic:notEqual name="atendProcDocumentoPessoaTipo" property="indicadorDocumentoObrigatorio" value="1">     
									     Não
									  </logic:notEqual>     
									
							    	</div>
							    	</td>
								</tr>
							</logic:iterate>

						</logic:present>
					</table>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
					<hr>
					</td>
				</tr>

				<tr>
					<td><strong>Normas Procedimentais:</strong></td>
					<td align="right"></td>
				</tr>

				<tr>
					<td><strong>Norma Procedimental:<font color="#FF0000">*</font></strong></td>
					<td>
						<table width="100%" border="0">
							<tr>
								<html:hidden property="descricaoNormaProcedimental"/>							
								<td align="left">
									<html:select property="idNormaProcedimental" onchange="document.forms[0].descricaoNormaProcedimental.value = this[this.selectedIndex].innerText;">
											<html:option value="-1">&nbsp;</html:option>
											<html:options collection="colecaoNormaProcedimental"
												labelProperty="descricao" property="id" /></html:select>
								</td>
								
								<td align="right" ><input name="Button" type="button"
										class="bottonRightCol" value="Adicionar" align="right"
										onclick="javascript:adicionarNormaProcedimental()" />
								</td>
							</tr>
						</table>					
					
					
					</td>
				</tr>

				<tr>
					<td colspan="2">
					<table width="100%" bgcolor="#99CCFF">


						<tr bordercolor="#000000">
							<td align="center" width="10%" bgcolor="#90c7fc"><strong>Remover</strong></td>
							<td width="40%" bgcolor="#90c7fc"><strong>Nota Procedimental</strong></td>
						</tr>
						<%--Esquema de paginação--%>

						<logic:present name="colecaoAtendProcNormaProcedimental" scope="session">
							<%int cont = 0;%>
							<logic:iterate id="atendProcNormaProcedimental"
								name="colecaoAtendProcNormaProcedimental">
								<%cont = cont + 1;
							if (cont % 2 == 0) {%>
								<tr bgcolor="#FFFFFF">
									<%} else {

							%>
								<tr bgcolor="#cbe5fe">
									<%}%>



									<td width="10%">
									<div align="center"><font color="#333333"> <img width="14"
										height="14" border="0"
										src="<bean:message key="caminho.imagens"/>Error.gif"
										onclick="javascript:removerNormaProcedimental('<bean:write name="atendProcNormaProcedimental" property="ultimaAlteracao.time" />');"
										 />
									</font></div>
									</td>
									<td>
									<div align="center"><bean:write name="atendProcNormaProcedimental"
										property="normaProcedimental.descricao" /></div>
									</td>
								</tr>
							</logic:iterate>

						</logic:present>
					</table>
					</td>
				</tr>	

				<tr>
					<td colspan="2">
					<hr>
					</td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">
					<hr>
					</td>
				</tr>
				<tr>
					<td height="19">
					<div align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>

						</td>
				</tr>
				<tr>
					<td colspan="1">
					<font color="#FF0000">
						<logic:present name="manter" scope="session">
						
							<input type="button" 
								name="ButtonReset" 
								class="bottonRightCol"
								value="Voltar"
								onClick="javascript:window.location.href='/gsan/exibirManterAtendimentoProcedimentoAction.do'">
							
						</logic:present>
						
						<logic:notPresent name="manter" scope="session">
							<input type="button" 
								name="ButtonReset" 
								class="bottonRightCol"
								value="Voltar"
								onClick="javascript:window.location.href='/gsan/exibirFiltrarAtendimentoProcedimentoAction.do'">
						</logic:notPresent> 
						
						<input type="button" 
							name="ButtonReset"
							class="bottonRightCol" 
							value="Desfazer"
							onClick="window.location.href='<html:rewrite page="/exibirAtualizarAtendimentoProcedimentoAction.do?desfazer=S&idAtendimentoProcedimento=${requestScope.idAtendimentoProcedimento}"/>'">
						
						<input type="button" 
							name="ButtonCancelar" 
							class="bottonRightCol"
							value="Cancelar"
							onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
					</font>
					</td>
					<td align="right">
						<input type="button" 
							name="Button"
							class="bottonRightCol" 
							value="Atualizar"
							onClick="javascript:validarForm(document.forms[0]);" />
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
		</tr>
	</table>
<%@ include file="/jsp/util/rodape.jsp"%>	
</html:form>
</body>
</html:html>

