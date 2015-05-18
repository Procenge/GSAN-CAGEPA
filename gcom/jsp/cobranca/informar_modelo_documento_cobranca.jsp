<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.cobranca.DocumentoTipoLayout"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gcom.util.Util"%>
<%@page import="java.util.Date"%><html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="InformarModeloDocumentoCobrancaActionForm"
	dynamicJavascript="true" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<SCRIPT LANGUAGE="JavaScript">

function validarForm(form){
	var form = document.forms[0];
	var documentoInformado = true;

	if (form.documentoTipoId == null || form.documentoTipoId.value == '' || form.documentoTipoId.value == <%=ConstantesSistema.NUMERO_NAO_INFORMADO %>){
		documentoInformado = false;
		alert("Informe o Tipo de Documento.");
		return false;
	}
	
	if(!documentoInformado){
		alert("Nenhum documento de cobrança válido foi informado.");
		return false;
	}else{
		form.indicadorModificacao.value = "S";
		form.action = "exibirInserirModeloDocumentoCobrancaAction.do";
		form.submit();
	}
}

function modificarTipoDocumento() {
	var form = document.forms[0];
	
	form.documentoTipoDescricao.value = form.documentoTipoId.options[form.documentoTipoId.selectedIndex].text;
	form.indicadorModificacao.value = "S";
	
  	form.action = "exibirInformarModeloDocumentoCobrancaAction.do";           
	form.submit();
}

function atualizarModeloCobrancaDocumento(idDocumentoLayout) {
	var form = document.forms[0];
	
	form.id = idDocumentoLayout;
	form.indicadorModificacao.value = "S";
  	form.action = "exibirAtualizarModeloDocumentoCobrancaAction.do?idDocumentoLayout=" + idDocumentoLayout + "&desfazer=S";           
	form.submit();
}

function desfazer() {
	var form = document.forms[0];

	form.documentoTipoId.value = <%=ConstantesSistema.NUMERO_NAO_INFORMADO %>;
	form.indicadorModificacao.value = "S";
  	form.action = "exibirInformarModeloDocumentoCobrancaAction.do?menu=sim";           
	form.submit();
  	
}

</SCRIPT>


</head>

<body leftmargin="5" topmargin="5"
	onload="">
<html:form action="/exibirInserirModeloDocumentoCobrancaAction.do"
	name="InformarModeloDocumentoCobrancaActionForm"
	type="gcom.gui.cobranca.ExibirInserirModeloDocumentoCobrancaAction"
	method="post">
	
	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<tr>
			<td width="149" valign="top" class="leftcoltext">
			<div align="center"><%@ include
				file="/jsp/util/informacoes_usuario.jsp"%> <%@ include
				file="/jsp/util/mensagens.jsp"%></div>
			</td>
			<td width="625" valign="top" bgcolor="#003399" class="centercoltext">
			
			<html:hidden property="id"/>
			<html:hidden property="indicadorModificacao"/>
				
			<table height="100%">

				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Informar Modelo do Documento de Cobrança</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>
			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="3">Para informar Modelo do Documento de Cobrança, informe os dados abaixo:</td>
				</tr>

				<tr>
					<td><strong>Tipo de Documento:<font
						color="#FF0000">*</font></strong></td>
					<td colspan="3">
						<html:hidden property="documentoTipoDescricao"/>
						<html:select property="documentoTipoId" onchange="javascript:modificarTipoDocumento();"
							tabindex="1"
							style="width: 450px;" >
							<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>
							<html:options collection="colecaoDocumentoTipo"
								labelProperty="descricaoDocumentoTipo" property="id" />
						</html:select>
					</td>
				</tr>
				
				<tr>

					<td><strong></strong></td>


					<td colspan="3" align="left"><label><span
						class="style2"> </span> </label></td>
				</tr>

				<tr>

					<td><strong></strong></td>


					<td colspan="3" align="left"><label><span
						class="style2"> </span> </label></td>
				</tr>

				<tr>
					<td colspan="4">

					<table bgcolor="#99ccff" width="100%">

						<tbody>
							<tr bordercolor="#FFFFFF" bgcolor="#79bbfd">
								<td width="40%">
								<div align="center">Descrição</div>
								</td>
								<td width="40%">
								<div align="center">CI</div>
								</td>
								<td width="10%">
								<div align="center">Modelo Padrão</div>
								</td>
								<td width="10%">
								<div align="center">Modelo Ativo</div>
								</td>
							</tr>

							<logic:notEmpty name="colecaoDocumentoTipoLayout">

											<%int cont = 0;%>
											<logic:iterate name="colecaoDocumentoTipoLayout"
												id="documentoTipoLayout" type="DocumentoTipoLayout">
												<%cont = cont + 1;
												if (cont % 2 == 0) {%>
												<tr bgcolor="#cbe5fe">
												<%} else {%>
												<tr bgcolor="#FFFFFF">
													<%}%>
													
													<td style="width: 40%">
														<div align="left">
															<script type="text/javascript">
																var valor = "<c:out value="${documentoTipoLayout.descricaoLayout}"/>".toUpperCase();
																var valorCortado = valor.substring(0,20).trim().toUpperCase();
																var id = "<c:out value="${documentoTipoLayout.id}"/>".trim();
																
																var href = "<a href=" + "javascript:atualizarModeloCobrancaDocumento('" + id + "') " + 
																				"	title='" + valor + "' >" + valorCortado + "</a>";
																document.write(href);
																
															</script>
														</div>
													</td>
													
													<td style="width: 40%">
														<div align="left" >
															<script type="text/javascript">
																var valor = "<c:out value="${documentoTipoLayout.descricaoCIControleDocumento}"/>".toUpperCase();
																var valorCortado = valor.substring(0,20).trim().toUpperCase();
																
																var href = "<a href=" + "javascript:atualizarModeloCobrancaDocumento('" + id + "') " + 
																				"	title='" + valor + "' >" + valorCortado + "</a>";
																document.write(href);
															</script>											
														</div>
													</td>
																										
													<td style="width: 10%">
														<div align="center" >
															<script type="text/javascript">
																var valor = "<c:out value="${documentoTipoLayout.indicadorPadrao}"/>";
																
																if (valor.trim() == '1') {
																	document.write('Sim');
																} else {
																	document.write('Não');
																}
															</script>														
														</div>
													</td>
													
													<td style="width: 10%">
														<div align="center" >
															<script type="text/javascript">
																var valor = "<c:out value="${documentoTipoLayout.indicadorUso}"/>";
																
																if (valor.trim() == '1') {
																	document.write('Sim');
																} else {
																	document.write('Não');
																}
															</script>															
														</div>
													</td>																								
												</tr>
											</logic:iterate>
							</logic:notEmpty>
						</tbody>
					</table>

					</td>
				</tr>

				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>
				<tr>
					<td align="left"><input name="Button" type="button"
						class="bottonRightCol" value="Desfazer" align="left"
						onclick="javascript:desfazer();">
					<input type="button" name="ButtonCancelar" class="bottonRightCol"
						value="Cancelar"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
					</td>
					<td align="right"><input name="Button" type="button"
						class="bottonRightCol" value="Inserir Novo Modelo"
						onclick="javascript:validarForm(document.forms[0]);" /></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>
