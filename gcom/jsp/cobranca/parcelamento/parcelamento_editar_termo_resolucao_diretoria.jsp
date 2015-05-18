<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.util.Util"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@page isELIgnored="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false" formName="ParcelamentoEditarTermoResolucaoDiretoriaActionForm" dynamicJavascript="false" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>ckeditor/ckeditor.js"></script>
	
<script language="JavaScript">
<!--

function validarForm(){
	var form = document.forms[0];

	form.action = "/gsan/exibirParcelamentoEditarResolucaoDiretoriaAction.do?atualizarTermoParcelamento=sim"
	form.submit();
	
}

//-->
</script>



<logic:equal name="TipoParcelamentoDadosTermo" value="TESTEMUNHA" scope="session">
	<script language="JavaScript">
			function fecharForm(){
				var form = document.forms[0];
			
				form.action = "/gsan/exibirParcelamentoTermoTestemunhasAction.do"
				form.submit();
			
			}
	</script>
</logic:equal>


<logic:equal name="TipoParcelamentoDadosTermo" value="DADOSTERMO" scope="session">
	<script language="JavaScript">
			function fecharForm(){
				var form = document.forms[0];
			
				form.action = "/gsan/exibirParcelamentoDadosTermoAction.do"
				form.submit();
			
			}
	</script>
</logic:equal>

<logic:equal name="TipoParcelamentoDadosTermo" value="DADOSTERMOEXECFISCAL" scope="session">
	<script language="JavaScript">
			function fecharForm(){
				var form = document.forms[0];
			
				form.action = "/gsan/exibirParcelamentoDadosTermoExecFiscalAction.do"
				form.submit();
			
			}
	</script>
</logic:equal>


<logic:notPresent name="TipoParcelamentoDadosTermo"	scope="session">
	<logic:present name="TermoParcelamentoPreview"	scope="session">
		<script language="JavaScript">
				function fecharForm(){
					var form = document.forms[0];
				
					form.action = "/gsan/efetuarParcelamentoDebitosWizardAction.do?action=exibirEfetuarParcelamentoDebitosProcesso4Action"
					form.submit();
				
				}
		</script>
	</logic:present>		
</logic:notPresent>


<logic:notPresent name="TipoParcelamentoDadosTermo"	scope="session">
	<logic:notPresent name="TermoParcelamentoPreview" scope="session">
		<logic:notPresent name="TermoParcelamentoConsultar" scope="session">
			<script language="JavaScript">
					function fecharForm(){
						var form = document.forms[0];
						
						form.action = "/gsan/efetuarParcelamentoDebitosWizardAction.do?retornoTelaSucesso=S&action=concluirProcessoAction"
						form.submit();
					
					}
			</script>
		</logic:notPresent>			
	</logic:notPresent>
</logic:notPresent>	

<logic:notPresent name="TipoParcelamentoDadosTermo"	scope="session">
	<logic:notPresent name="TermoParcelamentoPreview" scope="session">
		<logic:present name="TermoParcelamentoConsultar" scope="session">
			<logic:equal name="TermoParcelamentoConsultar" value="True" scope="session">	
				<script language="JavaScript">
						function fecharForm(){
							var form = document.forms[0];
							
							form.action = "/gsan/exibirConsultarParcelamentoDebitoAction.do?codigoImovel=" + form.idImovel.value + "&codigoParcelamento=" + form.parcelamentoId.value						
							form.submit();
	
						}
				</script>
			</logic:equal>			
		</logic:present>	
	</logic:notPresent>	
</logic:notPresent>	


</head>

<body leftmargin="5" topmargin="5">

<html:form action="/exibirParcelamentoEditarResolucaoDiretoriaAction.do"
	name="ParcelamentoEditarTermoResolucaoDiretoriaActionForm"
	type="gcom.gui.cobranca.parcelamento.ParcelamentoEditarTermoResolucaoDiretoriaActionForm" method="post">
	
	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<html:hidden property="parcelamentoId"/>
	<html:hidden property="idImovel"/>

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

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="11"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
				<td class="parabg">Editar o Termo do Parcelamento</td>
				<td width="11"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
			</tr>
		</table>
		<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="3">

						<html:textarea property="textoTermoModificado"></html:textarea>
						
						<script language="JavaScript">
							CKEDITOR.replace( 'textoTermoModificado', {
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
				<tr>
					<td colspan="3" height="10"></td>
				</tr>
				
				<tr>
					<td align="left">
						<input name="Voltar" type="button" class="bottonRightCol" value="Fechar" style="width: 80px"
							onClick="javascript:fecharForm();" />
					</td>
					
					<td align="right" colspan="2">
					
					<!-- 
						<input type="button" name="ButtonConfirmar" class="bottonRightCol" value="Confirmar" 
							onClick="javascript:confirmarForm()">
					 -->
						<input type="button" name="ButtonAtualizar" class="bottonRightCol" value="Atualizar/Emitir"
							onclick="javascript:validarForm();" />
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
