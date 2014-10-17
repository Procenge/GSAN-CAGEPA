<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@page import="gcom.cobranca.bean.CobrancaDocumentoHelper"%>
<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="java.util.Collection"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
<html:html>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">
<!-- Begin
function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

   	var form = document.forms[0];

    if (tipoConsulta == 'imovel') {
      form.idImovelDadosCadastrais.value = codigoRegistro;
      form.matriculaImovelDadosCadastrais.value = descricaoRegistro;
      form.matriculaImovelDadosCadastrais.style.color = "#000000";
	  form.action = 'consultarImovelWizardAction.do?action=exibirConsultarImovelDocumentosCobrancaAction&indicadorNovo=OK'
	  form.submit();
    }
    
}

function limparForm(){
   	var form = document.forms[0];
	form.action = 'consultarImovelWizardAction.do?action=exibirConsultarImovelDocumentosCobrancaAction&limparForm=OK'
	form.submit();
}
-->

</script>
</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('idImovelDocumentosCobranca')">
<html:form action="/exibirConsultarImovelAction.do"
	name="ConsultarImovelActionForm"
	type="gcom.gui.cadastro.imovel.ConsultarImovelActionForm" method="post"
	onsubmit="return validateConsultarImovelActionForm(this);">


	<jsp:include
		page="/jsp/util/wizard/navegacao_abas_wizard_consulta.jsp?numeroPagina=8" />

	<logic:present name="montarPopUp">
	 <table width="800" border="0" cellspacing="5" cellpadding="0">
		<tr>
		<td valign="top" class="centercoltext"><!--In�cio Tabela Reference a P�gina��o da Tela de Processo-->
			
			<p>&nbsp;</p>			
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">&nbsp;</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>						
				</tr>				
			</table>
			<!--Fim Tabela Reference a P�gina��o da Tela de Processo-->		
	
	</logic:present>
	
	<logic:notPresent name="montarPopUp">	
	
		<%@ include file="/jsp/util/cabecalho.jsp"%>
		<%@ include file="/jsp/util/menu.jsp"%>	
		
		<table width="800" border="0" cellspacing="5" cellpadding="0">
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
			
			<td valign="top" class="centercoltext"><!--In�cio Tabela Reference a P�gina��o da Tela de Processo-->
			
			<p>&nbsp;</p>			
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">&nbsp;</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>						
				</tr>				
			</table>
			<!--Fim Tabela Reference a P�gina��o da Tela de Processo-->
	
	</logic:notPresent>
			<p>&nbsp;</p>
			<table width="590" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td colspan="4">
					<table width="100%" align="center" bgcolor="#99CCFF" border="0">
						<tr>
							<td align="center"><strong>Dados do Im�vel</strong></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">
							<table width="100%" border="0">
								<tr>
									<td bordercolor="#000000" width="25%"><strong>Im&oacute;vel:<font
										color="#FF0000">*</font></strong></td>
									<td width="75%" colspan="3"><html:text
										property="idImovelDocumentosCobranca" maxlength="9" size="9"
										onkeypress="validaEnterComMensagem(event, 'consultarImovelWizardAction.do?action=exibirConsultarImovelDocumentosCobrancaAction&indicadorNovo=OK&limparForm=S','idImovelDocumentosCobranca','Im&oacute;vel');" />
									<a
										href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
									<img width="23" height="21"
										src="<bean:message key="caminho.imagens"/>pesquisa.gif"
										border="0" /></a> <logic:present
										name="idImovelDocumentosCobrancaNaoEncontrado" scope="request">
										<html:text property="matriculaImovelDocumentosCobranca"
											size="40" readonly="true"
											style="background-color:#EFEFEF; border:0; color: #ff0000" />

									</logic:present> <logic:notPresent
										name="idImovelDocumentosCobrancaNaoEncontrado" scope="request">
										<logic:present name="valorMatriculaImovelDocumentosCobranca"
											scope="request">
											<html:text property="matriculaImovelDocumentosCobranca"
												size="40" readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:present>
										<logic:notPresent
											name="valorMatriculaImovelDocumentosCobranca" scope="request">
											<html:text property="matriculaImovelDocumentosCobranca"
												size="40" readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:notPresent>
									</logic:notPresent> <a href="javascript:limparForm();"> <img
										src="<bean:message key="caminho.imagens"/>limparcampo.gif"
										border="0" title="Apagar" /></a></td>

								</tr>
								<tr>
									<td height="10">
									<div class="style9"><strong>Situa��o de �gua:</strong></div>
									</td>
									<td><html:text property="situacaoAguaDocumentosCobranca"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="15" maxlength="15" /></td>
									<td width="90"><strong>Situa��o de Esgoto:</strong></td>
									<td width="120"><html:text
										property="situacaoEsgotoDocumentosCobranca" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="15" maxlength="15" /></td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>


				<tr>
					<td><br>
					</td>
				</tr>
				<tr>
					<td>
					<table width="590" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center" width="65"><FONT COLOR="#000000"><strong>DOC</strong></FONT></td>
							<td align="center" width="40"><FONT COLOR="#000000"><strong>Sit. Doc.</strong></FONT></td>
						<td align="center" width="90"><FONT COLOR="#000000"><strong>A��o
						de Cobran�a</strong></FONT></td>
						<td align="center" width="65"><FONT COLOR="#000000"><strong>Emiss�o</strong></FONT></td>
						<td align="center" width="95"><FONT COLOR="#000000"><strong>Forma
						Emiss�o</strong></FONT></td>
						<td align="center" width="90"><FONT COLOR="#000000"><strong>Tipo
						Doc.</strong></FONT></td>
						<td align="right" width="70"><FONT COLOR="#000000"><strong>Vl.
						Doc.</strong></FONT></td>
						<td align="center" width="60"><FONT COLOR="#000000"><strong>Qtde.
						Itens</strong></FONT></td>
						<td align="left" width="80"><FONT COLOR="#000000"><strong>Sit. OS</strong></FONT></td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td>

   				    <div style="width: 100%; height: 102; overflow: auto;">
					<table width="100%" align="center" bgcolor="#99CCFF">

						<logic:present name="colecaoDocumentoCobranca">
							<%int cont = 1;%>
							<logic:iterate name="colecaoDocumentoCobranca"
								id="cobrancaDocumentoHelper" type="CobrancaDocumentoHelper">

								<%cont = cont + 1;
							if (cont % 2 == 0) {%>
								<tr bgcolor="#FFFFFF">
									<%} else {

							%>
								<tr bgcolor="#cbe5fe">
									<%}%>
									<td width="70" align="center">
									<logic:empty name="cobrancaDocumentoHelper" property="idOrdemServico">
									<a
										href="javascript:abrirPopup('exibirDocumentoCobrancaItemAction.do?cobrancaDocumentosID=<%="" + cobrancaDocumentoHelper.getCobrancaDocumento().getId()%>', 400, 800);"
										title="<%="" + cobrancaDocumentoHelper.getCobrancaDocumento().getId()%>">${cobrancaDocumentoHelper.cobrancaDocumento.numeroSequenciaDocumento}</a>
									</logic:empty>
									<logic:notEmpty name="cobrancaDocumentoHelper" property="idOrdemServico">
<a
										href="javascript:abrirPopup('exibirDocumentoCobrancaItemAction.do?cobrancaDocumentosID=<%="" + cobrancaDocumentoHelper.getCobrancaDocumento().getId()%>&numeroOS=${cobrancaDocumentoHelper.idOrdemServico}', 400, 800);"
										title="<%="" + cobrancaDocumentoHelper.getCobrancaDocumento().getId()%>">${cobrancaDocumentoHelper.cobrancaDocumento.numeroSequenciaDocumento}</a>
									</logic:notEmpty>
									</td>
									<logic:notEmpty name="cobrancaDocumentoHelper" property="cobrancaDocumento.cobrancaAcaoSituacao">										
										<td width="40" align="center" title="${cobrancaDocumentoHelper.cobrancaDocumento.cobrancaAcaoSituacao.descricao}">
											${cobrancaDocumentoHelper.cobrancaDocumento.cobrancaAcaoSituacao.id}
										</td>
									</logic:notEmpty>
									<logic:empty name="cobrancaDocumentoHelper" property="cobrancaDocumento.cobrancaAcaoSituacao">
										<td width="40" align="center">
											&nbsp;
										</td>
									</logic:empty>
									<td width="95" align="left"><logic:equal
										name="cobrancaDocumentoHelper"
										property="cobrancaDocumento.documentoEmissaoForma.id"
										value="<%= "" + gcom.cobranca.DocumentoEmissaoForma.CRONOGRAMA %>">
											${cobrancaDocumentoHelper.cobrancaDocumento.cobrancaAcaoAtividadeCronograma.cobrancaAcaoCronograma.cobrancaAcao.descricaoCobrancaAcao}&nbsp;
										</logic:equal> <logic:equal name="cobrancaDocumentoHelper"
										property="cobrancaDocumento.documentoEmissaoForma.id"
										value="<%= "" + gcom.cobranca.DocumentoEmissaoForma.EVENTUAL %>">
											${cobrancaDocumentoHelper.cobrancaDocumento.cobrancaAcaoAtividadeComando.cobrancaAcao.descricaoCobrancaAcao}
										</logic:equal> <logic:equal name="cobrancaDocumentoHelper"
										property="cobrancaDocumento.documentoEmissaoForma.id"
										value="<%= "" + gcom.cobranca.DocumentoEmissaoForma.INDIVIDUAL %>">
									</logic:equal> &nbsp;</td>									
									<td width="75" align="center"><bean:write
										name="cobrancaDocumentoHelper"
										property="cobrancaDocumento.emissao"
										formatKey="datehour.format" /></td>
									<td width="95" align="left">${cobrancaDocumentoHelper.cobrancaDocumento.documentoEmissaoForma.descricaoDocumentoEmissaoForma}</td>
									<td width="90" align="center">${cobrancaDocumentoHelper.cobrancaDocumento.documentoTipo.descricaoDocumentoTipo}</td>
									<td width="70" align="right"><bean:write
										name="cobrancaDocumentoHelper"
										property="cobrancaDocumento.valorDocumento"
										formatKey="money.format" /></td>
									<td width="60" align="center">${cobrancaDocumentoHelper.quantidadeItensCobrancaDocumento}</td>
									<td width="30" align="center">
										 <logic:notEmpty name="cobrancaDocumentoHelper" property="idOrdemServico">
										   ${cobrancaDocumentoHelper.situacaoOrdemServico}
										 </logic:notEmpty>
										 <logic:empty name="cobrancaDocumentoHelper" property="idOrdemServico">
										   &nbsp;
										 </logic:empty>
									</td>
								</tr>

							</logic:iterate>
						</logic:present>
					</table>
					</div>
					<table width="590" border="0">
						<tr>
							<td>
							<div align="right"><jsp:include
								page="/jsp/util/wizard/navegacao_botoes_wizard_consulta.jsp?numeroPagina=8" /></div>
							</td>
						</tr>
					</table>
			</table>
			</td>
		</tr>


	</table>

<logic:notPresent name="montarPopUp">	
		<%@ include file="/jsp/util/rodape.jsp"%>
</logic:notPresent>
</html:form>
</body>
<!-- imovel_consultar_documentos_cobranca.jsp -->
</html:html>
