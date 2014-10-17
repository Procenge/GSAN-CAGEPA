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
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="GerarRelatorioContasEmitidasDynaForm" />
<script>

function validarForm(){
    var form = document.forms[0];

    if(validarCampos()) {
	    if(form.tipoRelatorio[0].checked) {
	        form.relatorio.value = "RelatorioContasEmitidas.rpt";
	    } else if(form.tipoRelatorio[1].checked) {
	    	form.relatorio.value = "RelatorioResumoContasEmitida.rpt";
	    } else {
	    	form.relatorio.value = "RelatorioAnaliticoContasEmitidas.rpt";
	    }
		
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
		msg = acrescentarMsg(msg, "Informe o Ano/M�s de Refer�ncia");
	}

	if((form.tipoRelatorio[0].checked == false) && (form.tipoRelatorio[1].checked == false) && (form.tipoRelatorio[2].checked == false)) {
		msg = acrescentarMsg(msg, "Informe o Tipo do Relat�rio");
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
	formulario.P_CL_TP_ESP.value = "-1";
}

function mostrarCampoClienteTipoEspecial(){   
    obj = document.getElementById('campoClienteTipoEspecial');
    obj.style.visibility = "visible";     
 }
 
function esconderCampoClienteTipoEspecial(){  
	var formulario = document.forms[0];
    obj = document.getElementById('campoClienteTipoEspecial');     
    obj.style.visibility = "hidden";
    formulario.P_CL_TP_ESP.value = "-1";
 }

</script>


</head>

<body leftmargin="5" topmargin="5" onload="javascript:document.getElementById('campoClienteTipoEspecial').style.visibility='hidden'">
<html:form action="/gerarRelatorioContasEmitidasAction.do"
	name="GerarRelatorioContasEmitidasDynaForm"
	type="org.apache.struts.action.DynaActionForm"
	method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
	
	<input type="hidden" name="acao" value="gerarRelatorio" />
	<input type="hidden" name="relatorio" value="RelatorioContasEmitidas.rpt" />
	<%--  ehRelatorioBatch indica se o Relatorio ser� processado como uma Rotina Batch  --%>
	<%--  1 - Indica que SIM, � uma Rotina Batch                                        --%>
	<%--  2 - Indica que NAO, n�o � uma Rotina Batch e o Relat�rio ser� gerado online   --%>
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
					<td class="parabg">Rel�torio Contas Emitidas</td>
					<td width="11" valign="top"><img
						src="<bean:message key="caminho.imagens"/>parahead_right.gif"
						border="0" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para gerar o relat&oacute;rio de contas emitidas, informe os dados abaixo:</td>
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
         			 <td width="30%"><strong>Ano/M�s Refer�ncia:<font color="#FF0000">*</font></strong></td>
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
					<td><strong>Tipo do Relat�rio:<font color="#FF0000">*</font></strong></td>
					<td>
						<html:radio property="tipoRelatorio" value="1" tabindex="5" onclick="javascript:mostrarCampoClienteTipoEspecial();">
							<strong> Contas Emitidas por Respons&aacute;vel </strong> 
						</html:radio><br>
						<html:radio property="tipoRelatorio" value="2" tabindex="5" onclick="javascript:esconderCampoClienteTipoEspecial();">
							<strong> Resumo de Contas Emitidas </strong> 
						</html:radio><br>
						<html:radio property="tipoRelatorio" value="3" tabindex="5" onclick="javascript:esconderCampoClienteTipoEspecial();">
							<strong> Relat�rio Anal�tico de Contas Emitidas </strong> 
						</html:radio>
					</td>
				</tr>	
				
				<tr id="campoClienteTipoEspecial">
         			<td width="30%"><strong>Tipo Cliente Especial:</strong></td>
          			<td width="70%">
          				<html:select property="P_CL_TP_ESP">
          					<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
          					<html:options collection="colecaoClienteTipoEspecial" labelProperty="descricao" property="id"/>
          				</html:select>
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
