<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gcom.util.ConstantesSistema"%>
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

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="GerarRelatorioResumoAcoesCobrancaForm" />

<script language="JavaScript">

function validarForm(form){
	
	if(form.P_MES_ANO_REFERENCIA.value == '') {
		alert('Informe o Mês e Ano');
		form.P_MES_ANO_REFERENCIA.focus();
		return false;
	} else {
		if(validaMesAno(form.P_MES_ANO_REFERENCIA) == true) {
			if(form.ehRelatorioBatch.value == 2) {
				form.target = "_blank";				
			}
			form.submit();
			form.target = "";
		}		
	}
}

function validaMesAno(mesAno){
	if(mesAno.value != ""){
		return verificaAnoMes(mesAno);
	}else{
		return false;
	}
}

/*function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.forms[0];

    if (tipoConsulta == 'localidade') {
      form.P_LC_IM.value = codigoRegistro;
      form.nomeLocalidade.value = descricaoRegistro;
      form.nomeLocalidade.style.color = "#000000";
    }
    
    
}*/

/*function limparLocalidade(){
	var form = document.forms[0];
	form.P_LC_IM.value = "";
	form.nomeLocalidade.value = "";
}*/


</script>

</head>

<body leftmargin="5" topmargin="5">

<html:form action="/gerarRelatorioResumoAcoesCobranca"
	name="GerarRelatorioResumoAcoesCobrancaForm"
	type="org.apache.struts.action.DynaActionForm"
	method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
	
	<input type="hidden" name="acao" value="gerarRelatorio"/>
	<input type="hidden" name="relatorio" value="RelatorioResumoAcoesCobranca.rpt"/>
	<%--  ehRelatorioBatch indica se o Relatorio será processado como uma Rotina Batch  --%>
	<%--  1 - Indica que SIM, é uma Rotina Batch                                        --%>
	<%--  2 - Indica que NAO, não é uma Rotina Batch e o Relatório será gerado online   --%>
	<input type="hidden" name="ehRelatorioBatch" value="1"/>
	
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
			<td width="600" valign="top" bgcolor="#003399" class="centercoltext">
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
					<td class="parabg">Gerar Relatório Resumo das Ações de Cobrança</td>

					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
				<tr>
					<td height="5" colspan="3"></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td colspan="3">Para gerar o relatório,
					informe os dados abaixo:</td>
				</tr>
				<tr>
					<td></td>
				</tr>
				
				<tr>
		          <td width="30%"><strong>Mês/Ano do Faturamento:<font color="#FF0000">*</font></strong></td>
		          <td width="70%">
		          	<html:text property="P_MES_ANO_REFERENCIA" size="7"  maxlength="7" 
		          		onkeyup="javascript:mascaraAnoMes(this, event);"
		          		onblur="javascript:verificaAnoMes(this);"
		          		/>
		          	&nbsp;mm/aaaa
		          </td>
		        </tr>
		        				
				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td align="right">
					<div align="left" colspan="2"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td valign="top"><input name="button" type="button"
						class="bottonRightCol" value="Limpar" tabindex="4"
						onclick="window.location.href='/gsan/exibirGerarRelatorioResumoAcoesCobrancaAction.do?menu=sim'">&nbsp;</td>
					<td valign="top">
					<div align="right"><input name="button" type="button"
						class="bottonRightCol" value="Gerar"
						onclick="validarForm(document.forms[0]);" tabindex="5"></div>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>
</body>
</html:form>
</html:html>
