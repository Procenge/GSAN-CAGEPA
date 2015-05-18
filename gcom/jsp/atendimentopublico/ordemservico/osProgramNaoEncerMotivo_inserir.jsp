<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page
	isELIgnored="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false" formName="InserirOsProgramNaoEncerMotivoActionForm" />

</head>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript">

function limpar(){
	var form = document.forms[0];
	
	form.descricao.value = '';
	form.descricaoAbreviada.value = '';
	form.indicadorVisitaImprodutiva[1].checked = true;
	form.indicadorCobraVisitaImprodutiva[1].checked = true;
	
}

function validarForm() {	
	var form = document.forms[0];
		if (validateInserirOsProgramNaoEncerMotivoActionForm(form)) {
			submeterFormPadrao(form);
	}	
}	

</script>


<body leftmargin="5" topmargin="5">

<html:form action="/inserirOsProgramNaoEncerMotivoAction" 
	method="post" 
	name="InserirOsProgramNaoEncerMotivoActionForm" 
	type="gcom.gui.atendimentopublico.ordemservico.InserirOsProgramNaoEncerMotivoActionForm">
	
	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<tr>
			<td width="149" valign="top" class="leftcoltext"><%@ include
				file="/jsp/util/informacoes_usuario.jsp"%></td>
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
					<td class="parabg">Inserir Motivo do não encerramento da OS</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="3">Para inserir um(a) Motivo do não encerramento da OS,
					informe os dados abaixo:</td>
				</tr>
				<tr>
					<td width="30%" height="0"><strong>Descri&ccedil;&atilde;o:<font color="#FF0000">*</font></strong></td>
					<td colspan="2">
						<html:text property="descricao" size="50" maxlength="30"/>
					</td>
				</tr>
				<tr>
					<td width="30%" height="0"><strong>Descri&ccedil;&atilde;o Abreviada:<font color="#FF0000">*</font></strong></td>
					<td colspan="2">
						<html:text property="descricaoAbreviada" size="8" maxlength="5"/>
					</td>
				</tr>
				<logic:equal name="permiteCobrarHora" value="1" scope="session">
				<tr>
					<td width="30%" height="0"><strong>Valor Hora</strong></td>
					<td colspan="2">
						<html:text property="valorHora" size="8"maxlength="11" onkeyup="javascript:formataValorMonetario(this, 11)"/>
					</td>
				</tr>
				</logic:equal>
				
				<tr>
					<td><strong>Indicador Vísita Improdutiva:<font color="#FF0000">*</font></strong></td>
					<td><strong>
					<html:radio property="indicadorVisitaImprodutiva" value="1"/> Sim
					<html:radio property="indicadorVisitaImprodutiva" value="2"/> N&atilde;o
					</strong></td>
				</tr>

				
				<tr>
					<td><strong>Indicador Cobrar Vísita Improdutiva:<font color="#FF0000">*</font></strong></td>
					<td><strong>
					<html:radio property="indicadorCobraVisitaImprodutiva" value="1"/> Sim
					<html:radio property="indicadorCobraVisitaImprodutiva" value="2"/> N&atilde;o
					</strong></td>
				</tr>


				<tr>
					<td height="0">&nbsp;</td>
					<td colspan="2"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</td>
				</tr>
				<tr>
					<td height="0">&nbsp;</td>
					<td colspan="2">&nbsp;</td>
				</tr>




				<tr>
					<td height="0" align="left"><input name="Button" type="button"
						class="bottonRightCol" value="Limpar" align="left"
						onclick="javascript:limpar();"></td>
					<td height="0" align="right" colspan="2"><input type="button"
						name="Button" class="bottonRightCol" value="Inserir"
						onclick="javascript:validarForm()" /></td>

					<td>
					<div align="right"></div>
					</td>
				</tr>

				<tr>
					<td height="0">&nbsp;</td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td height="0">&nbsp;</td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td height="0">&nbsp;</td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td height="0">&nbsp;</td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td height="0">&nbsp;</td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td height="0">&nbsp;</td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td height="0">&nbsp;</td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td height="0">&nbsp;</td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td height="0">&nbsp;</td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td height="0">&nbsp;</td>
					<td colspan="2">&nbsp;</td>
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
