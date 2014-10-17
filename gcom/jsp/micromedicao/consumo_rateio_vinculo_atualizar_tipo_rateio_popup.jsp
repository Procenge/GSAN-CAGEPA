<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="AtualizarTipoRateioPopupActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript">
<!-- Begin -->
	function atualizarTipoRateio(){
        var form = document.forms[0];
      	form.action = 'atualizarTipoRateioPopupAction.do';
        form.submit();		
	}
</script>

</head>
<html:form action="/atualizarTipoRateioPopupAction.do"
	name="AtualizarTipoRateioPopupActionForm"
	type="gcom.gui.micromedicao.AtualizarTipoRateioPopupActionForm"
	method="post">
	<table width="570" border="0" cellpadding="0" cellspacing="5">
		<tr>
			<td width="560" valign="top" class="centercoltext">
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
					<td class="parabg">Atualizar Tipo de Rateio</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<logic:notEqual name="fechar" value="true" scope="request">
					<logic:present name="AtualizarTipoRateioPopupActionForm"
						property="botao">						
						<tr>
							<td width="80%">Preencha os campos para atualizar o tipo de  rateio:</td>
							<td align="right"></td>								
						</tr>
						<br>						
					</logic:present>
					<logic:notPresent name="AtualizarTipoRateioPopupActionForm"
						property="botao">
			
						<tr>
							<td>Não existe Rateio Tipo para o Imóvel</td>
							<td align="right"></td>								
						</tr>
						<br>
					</logic:notPresent>
					<logic:present name="AtualizarTipoRateioPopupActionForm"
						property="rateioTipoAgua">
						<logic:present scope="request" name="colecaoRateioTipo">
							<tr>
								<td width="40%" height="27">
									<strong>Tipo de Rateio da Liga&ccedil;&atilde;o de &Aacute;gua:<font color="#FF0000">*</font><strong></strong></strong>
								</td>
								<td width="60%" colspan="3">
									<div align="left"><strong> 
										<html:select name="AtualizarTipoRateioPopupActionForm"
											property="rateioTipoAgua">
											<html:options name="request" collection="colecaoRateioTipo"
											labelProperty="descricao" property="id" />
										</html:select>
									</strong></div>
								</td>
							</tr>
						</logic:present>
					</logic:present>
					<logic:present name="AtualizarTipoRateioPopupActionForm"
						property="rateioTipoPoco">
						<logic:present scope="request" name="colecaoRateioTipo">
							<tr>
								<td height="24">
									<strong>Tipo de Rateio do Po&ccedil;o:<font color="#FF0000">*</font><strong></strong></strong>
								</td>
								<td width="60%" colspan="3">
									<div align="left"><strong> 
										<html:select name="AtualizarTipoRateioPopupActionForm"
										property="rateioTipoPoco">
											<html:options name="request" collection="colecaoRateioTipo"
											labelProperty="descricao" property="id" />
										</html:select>
									</strong></div>
								</td>
							</tr>
						</logic:present>
					</logic:present>
					<tr>
						<td height="24" colspan="4">&nbsp;</td>
					</tr>
					<tr>
						<td height="27" colspan="4">
						<div align="right"><logic:present
							name="AtualizarTipoRateioPopupActionForm" property="botao">
							<input type="button" class="bottonRightCol" value="Atualizar"
								onClick="javascript:atualizarTipoRateio();">
						</logic:present> <input type="button" class="bottonRightCol"
							value="Fechar" onClick="javascript:window.close();"></div>
						</td>
					</tr>
				</logic:notEqual>
				<logic:equal name="fechar" value="true" scope="request">
					<tr>
						<td colspan="4">Atualização do Tipo de Rateio efetuada com
						sucesso</td>
					</tr>
					<br>
					<tr>
						<td>
						<div><input type="button" class="bottonRightCol" value="Fechar"
							onClick="javascript:window.close();"></div>
						</td>
					</tr>
				</logic:equal>

			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>

</html:form>
<body>
</html:html>
