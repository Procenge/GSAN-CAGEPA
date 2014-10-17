<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page import="gcom.util.ConstantesSistema" %>

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
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="GerarRelatorioImoveisLigacaoCortadaComConsumoDynaForm" />
<script>

function validarForm(){
    var form = document.forms[0];

    if(validarCampos()) {

		if(form.ehRelatorioBatch.value == 2) {
			form.target = "_blank";				
		}
		form.submit();
		form.target = "";
    }
}

function validarCampos(){
	
	var form = document.forms[0];
	var msg = "";

	if(form.P_GP_FATURAMENTO.value == "-1") {
		msg = acrescentarMsg(msg, "Informe o Grupo de Faturamento");
	}
	
	if(form.P_AM_REF.value == "") {
		msg = acrescentarMsg(msg, "Informe o Ano/Mês de Referência");
	}

	if( msg != ""){
		alert(msg);
		return false;
	}
	
	return true;
}

function acrescentarMsg(msgCompleta, novaMsg) {
	var msg = msgCompleta;
	
	if(msg != "") {
		msg = msg + "\n" + novaMsg;
	} else {
		msg = novaMsg;
	}

	return msg;
}

function limpar(){
	var formulario = document.forms[0];
	formulario.P_AM_REF.value = '';
	formulario.P_GP_FATURAMENTO.value = "-1";
}

</script>


</head>

<body leftmargin="5" topmargin="5" onload="">
<html:form action="/gerarRelatorioImoveisLigacaoCortadaComConsumoAction.do"
	name="GerarRelatorioImoveisLigacaoCortadaComConsumoDynaForm"
	type="org.apache.struts.action.DynaActionForm"
	method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
	
	<input type="hidden" name="acao" value="gerarRelatorio" />
	<%--  ehRelatorioBatch indica se o Relatorio será processado como uma Rotina Batch  --%>
	<%--  1 - Indica que SIM, é uma Rotina Batch                                        --%>
	<%--  2 - Indica que NAO, não é uma Rotina Batch e o Relatório será gerado online   --%>
	<input type="hidden" name="ehRelatorioBatch" value="1"/>


	<table width="770" border="0" cellpadding="0" cellspacing="5">
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
			<td valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img
						src="<bean:message key="caminho.imagens"/>parahead_left.gif"
						border="0" /></td>
					<td class="parabg">Relátorio Imóveis com Ligação Cortada com Consumo</td>
					<td width="11" valign="top"><img
						src="<bean:message key="caminho.imagens"/>parahead_right.gif"
						border="0" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para gerar o relatório Imóveis com Ligação Cortada com Consumo, informe os dados abaixo:</td>
				</tr>
				
				<tr>
					<td width="35%"><strong>Grupo de Faturamento <font color="#FF0000">*</font></strong></td>
					<td><strong><strong> 
						<html:select property="P_GP_FATURAMENTO">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<html:options collection="colecaoGrupoFaturamento" labelProperty="descricao" property="id"/>
						</html:select> </strong></strong>
					</td>
				</tr>
				<tr>
         			 <td width="30%"><strong>Ano/Mês Referência:<font color="#FF0000">*</font></strong></td>
          			<td width="70%">
          				<html:text property="P_AM_REF" size="7"  maxlength="7" 
          						   onkeypress="javascript: return isCampoNumerico(event);"
          						   onkeyup="javascript:mascaraAnoMes(this, event);" 
          						   onblur="javascript:return verificaAnoMes(this);"/>
         					&nbsp;mm/aaaa
         					<br/>
          			</td>
        		</tr>   
				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td align="right">
					<div align="left"><br><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>
				
							
				<tr>
					<td>
					<p>&nbsp;</p>
					</td>
					<td colspan="2"></td>
				</tr>
			</table>
			<table width="100%">
				<tr>
					<td height="24" >
			          	<input type="button" 
			          		class="bottonRightCol" 
			          		value="Limpar" 
			          		onclick="javascript:limpar()"/>
					</td>
				
					<td align="right">
						<input type="button" 
							name="Button" 
							class="bottonRightCol" 
							value="Gerar" 
							onClick="javascript:validarForm()" />
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
