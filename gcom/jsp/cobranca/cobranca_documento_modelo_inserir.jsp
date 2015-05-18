<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@page import="gcom.cobranca.DocumentoLayoutAssinatura"%>
<%@page import="gcom.gui.GcomAction"%>
<%@page import="gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<script language="JavaScript" src="<bean:message key="caminho.js"/>ckeditor/ckeditor.js"></script>

<SCRIPT LANGUAGE="JavaScript">
	function validarForm(form){
		
		if(validateInserirModeloDocumentoCobrancaActionForm(form)){
			if (CKEDITOR.instances.conteudoLayout.getData() != '') { 
				form.indicadorModificacao = "S";
	   			form.submit();
			} else {
				alert("Informa o Conteúdo do Modelo do Documento de cobrança");
			}
		}

	}
	 
	 function adicionarDocumentoLayoutAssinatura(){
		 	var form = document.forms[0];
		 	var msg="";
		 	
		 	if (form.descricaoCargoDocumentoLayoutAssinatura == null || 
		 			form.descricaoCargoDocumentoLayoutAssinatura.value == null || 
		 			form.descricaoCargoDocumentoLayoutAssinatura.value == "") {		 		
		 		msg="Informe o Cargo.\n";
		 	}
		 	
	 		if (msg == "") {
			 	form.action = "inserirRemoverAdicionarCargoDocumentoLayoutAssinaturaAction.do?operacao=inserir&operacaoModeloDocumento=inserir"
			 	form.submit();
 			} else {
 				alert(msg);
 			}
	 }	 
	 
	 function removerDocumentoLayoutAssinatura(time){
		 	var form = document.forms[0];
		 	
		 	
		 	if(confirm('Confirma remoção?')){
			 	form.action = "inserirRemoverAdicionarCargoDocumentoLayoutAssinaturaAction.do?operacao=remover&operacaoModeloDocumento=inserir&idDocumentoLayoutAssinatura=" + time;
			 	form.submit();
		 	}
	 }		 
	 
</SCRIPT>		

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="InserirModeloDocumentoCobrancaActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>



</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');">
<html:form action="/inserirModeloDocumentoCobrancaAction" method="post"
	name="InserirModeloDocumentoCobrancaActionForm"
	type="gcom.gui.cobranca.InserirModeloDocumentoCobrancaActionForm">
	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<html:hidden property="indicadorModificacao"/>

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

			<!--Início Tabela Reference a Páginação da Tela de Processo-->
			<table>
				<tr>
					<td></td>

				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Inserir Modelo do Documento de Cobrança</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>

			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="3">Para inseir Modelo do Documento de Cobrança, informe os dados abaixo:</td>
				</tr>
				<tr>
					<td><strong>Documento:</strong></td>
					<td colspan="3">
					
							<html:hidden property="documentoTipoId"/>
							<html:text property="documentoTipoDescricao" size="60"
								maxlength="45" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
					</td>
				</tr>				
				<tr>
					<td width="162"><strong>Descrição:<font
						color="#FF0000">*</font></strong></td>
					<td>
					<strong> 
					<html:text property="descricaoLayout" size="60" maxlength="60" /> 
					</strong>
					</td>
				</tr>
				
				<tr>
					<td width="162"><strong>CI:</strong></td>
					<td>
					<strong> 
					<html:text property="descricaoCI" size="60" maxlength="60" /> 
					</strong>
					</td>
				</tr>				
				
				<tr>
					<td><strong>Padrão:</strong></td>
					<td>
						<table width="100%" border="0">
							<tr>
								<td align="left">
									<html:checkbox property="indicadorLayoutPadrao" value="1" />
								</td>
								
								<td align="right" >
								</td>
							</tr>
						</table>
					</td>
					
				</tr>	
				
				<tr>
					<td colspan="3">
					<hr>
					</td>
				</tr>

				<tr>
					<td><strong>Assinaturas:</strong></td>
					<td align="right">
					</td>
				</tr>
				
				<tr>
					<td><strong>Cargo:<font
						color="#FF0000">*</font></strong></td>
					<td colspan="3">
						<html:select property="descricaoCargoDocumentoLayoutAssinatura"  style="width: 457px;">
							<logic:present name="colecaoCargoFuncionario" scope="session" > 
								<logic:iterate name="colecaoCargoFuncionario" id="cargoFuncionario" type="java.lang.String">
									<html:option value="<%= cargoFuncionario %>">
										<bean:write name="cargoFuncionario" />
									</html:option>
								</logic:iterate>
							</logic:present>						  
						</html:select>  
					</td>
				</tr>	
				
				<tr>
					<td></td>
					<td colspan="3" align="right">
						<input name="Button" type="button"
							class="bottonRightCol" value="Adicionar" align="right"
							onclick="javascript:adicionarDocumentoLayoutAssinatura()" />
					</td>
					
				</tr>
				
				<tr>
					<td colspan="3">
					<table width="100%" bgcolor="#99CCFF">


						<tr bordercolor="#000000">
							<td align="center" width="10%" bgcolor="#90c7fc"><strong>Remover</strong></td>
							<td width="90%" bgcolor="#90c7fc"><strong>Cargo</strong></td>
						</tr>
						<%--Esquema de paginação--%>

						<logic:present name="colecaoDocumentoLayoutAssinatura" scope="session" >
							<%int cont = 0;%>
							<logic:iterate id="documentoLayoutAssinatura"
								name="colecaoDocumentoLayoutAssinatura">
								<%cont = cont + 1;
							if (cont % 2 == 0) {%>
								<tr bgcolor="#FFFFFF">
									<%} else {

							%>
								<tr bgcolor="#cbe5fe">
									<%}%>

									<td width="10%">
									<div align="center"><font color="#333333"> <img width="14"
										height="14" border="0" onclick="javascript:removerDocumentoLayoutAssinatura('<bean:write name="documentoLayoutAssinatura" property="ultimaAlteracao" />');"
										src="<bean:message key="caminho.imagens"/>Error.gif"
										 />
									</font></div>
									</td>
									
									<td width="90%">
										<div align="center">
											<bean:write name="documentoLayoutAssinatura"	property="descricaoCargo" />
										</div>
									</td>
								</tr>
							</logic:iterate>

						</logic:present>
					</table>
					</td>
				</tr>	
				
				<tr>
					<td colspan="3">
					<hr>
					</td>
				</tr>
						
				<tr>
					<td colspan="3"><strong>Conteúdo:<font
						color="#FF0000">*</font></strong></td>
				</tr>	
				
				<tr>
					<td colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">	
							<tr>
								<td>

									<html:textarea property="conteudoLayout"></html:textarea>
									
									<script language="JavaScript">
										CKEDITOR.replace( 'conteudoLayout', {
											toolbar: [
												{ name: 'clipboard',   items : [ 'Cut','Copy','Paste','-','Undo','Redo' ] },
												{ name: 'editing',     items : [ 'Find','Replace','-','SelectAll' ] },
												{ name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript' ] },	
												{ name: 'insert',      items : [ 'Table','HorizontalRule', 'SpecialChar' ] },								    
												'/',
												{ name: 'paragraph',   items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock' ] },
												{ name: 'styles',      items : [ 'FontSize' ] },
												{ name: 'colors',      items : [ 'TextColor','BGColor' ] },
												{ name: 'tools',       items : [ 'Maximize' ] }
											]
										});
									</script>
								</td>
							</tr>
						</table>	
					</td>	
				</tr>
				
				<tr>
					<td colspan="3">
					<hr>
					</td>
				</tr>

				<tr>
					<td height="19"><strong> <font color="#FF0000"></font></strong></td>
					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>
						
				<tr>
					<td colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><input name="Button" type="button" class="bottonRightCol"
									value="Desfazer" align="left" onclick="window.location.href='<html:rewrite page="/exibirInserirModeloDocumentoCobrancaAction.do?desfazer=S"/>'" > 
									<input name="Button" type="button"
									class="bottonRightCol" value="Cancelar" align="left"
									onclick="window.location.href='/gsan/telaPrincipal.do'">
									
									<input name="Button" type="button"
									class="bottonRightCol" value="Voltar" align="left"
									onclick="window.location.href='/gsan/exibirInformarModeloDocumentoCobrancaAction.do'">									
									
								</td>
								
								<td align="right">
									<input name="Button" type="button"
									class="bottonRightCol" value="Inserir" align="right"
									onClick="javascript:validarForm(document.forms[0]);">
								</td>
							</tr>
						</table>
					</td>
				</tr>																
			</table>
			<p>&nbsp;</p>
		</tr>
	</table>
	<tr>
		<td colspan="3"><%@ include file="/jsp/util/rodape.jsp"%>
	</tr>
</html:form>
</body>
</html:html>

