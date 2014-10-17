<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="java.util.Locale"%>
<% session.setAttribute("currentLocale", new Locale("pt", "BR")); %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

	<head>
		<title>Cálculo de Consumo Estimado</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
		<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
		<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
		<SCRIPT LANGUAGE="JavaScript">
		<!--
			function fechar() {
				window.close();
			}
		
		   function validarForm(formulario){
			   var status = true;
			   if(formulario.dataLeituraAnteriorString.value.length == 0){
				    status = false;
				    formulario.dataLeituraAnteriorString.focus();
				    alert('O preenchimento do campo Data Leitura Anterior é obrigatório.');
				   
			   } else if(formulario.dataLeituraAtualString.value.length == 0){
				    status = false;
				    formulario.dataLeituraAtualString.focus();
				    alert('O preenchimento do campo Data Leitura Atual é obrigatório.');
                   
               }  else if(formulario.leituraAnteriorString.value.length == 0){
            	    status = false;
				    formulario.leituraAnteriorString.focus();
				    alert('O preenchimento do campo Leitura Anterior é obrigatório.');
                   
               }else if(formulario.leituraAtualString.value.length == 0){
                   status = false;
                   formulario.leituraAtualString.focus();
                   alert('O preenchimento do campo Leitura Atual é obrigatório.');
                  
              } else if (comparaData(formulario.dataLeituraAnteriorString.value, ">", formulario.dataLeituraAtualString.value )){
            	    status = false;
				    alert('Data Leitura Atual é anterior a Data Leitura Anterior.');
				    
              }else if (parseInt(formulario.leituraAtualString.value) < parseInt(formulario.leituraAnteriorString.value)){
	            	status = false;
	            	alert("Leitura Atual é menor que a Leitura Anterior.");
              }
			   return status;
		   }
		-->
		</SCRIPT>
	</head>
	
    <body leftmargin="5" topmargin="5">
        <html:form styleId="CalculoConsumoEstimadoForm" action="/exibirObterCalculoConsumoEstimadoAction.do" type="gcom.gui.micromedicao.CalculoConsumoEstimadoForm" name="CalculoConsumoEstimadoForm" method="post" onsubmit="return validarForm(this)">
            <table width="570" border="0" cellpadding="0" cellspacing="5">
                <tr>
                    <td width="560" valign="top" class="centercoltext">
                        <table height="100%">
                            <tr>
                                <td></td>
                            </tr>
                        </table>
                        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                            <tr>
                                <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
                                <td width="100%" class="parabg">C&aacute;lculo de Consumo Estimado</td>
                                <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
                            </tr>
                            <tr>
                                <td colspan=3>
                                    <table width="100%" border="0" align="left" cellpadding="3" cellspacing="3">
                                        <tr>
                                            <td align="center">
                                                <table width="90%" border="0" align="left" cellpadding="3" cellspacing="3">
	                                                <tr>
			                                            <td align="right">Data Leitura Anterior<font color="#FF0000">*</font>:</td>
			                                            <td>
			                                                 <html:text styleId="dataLeituraAnteriorString" property="dataLeituraAnteriorString" size="10"  maxlength="10" onkeypress="javascript:mascaraData(this,event);"/>
			                                                 <a href="javascript:abrirCalendario('CalculoConsumoEstimadoForm', 'dataLeituraAnteriorString')"> <img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
			                                            </td>
			                                            <td align="right">Leitura Anterior<font color="#FF0000">*</font>:</td>
			                                            <td><html:text styleId="leituraAnteriorString" property="leituraAnteriorString" size="10"  maxlength="10"  onkeyup="javascript:verificaNumeroInteiro(this);"/></td>
			                                        </tr>
			                                        <tr>
			                                            <td align="right">Data Leitura Atual<font color="#FF0000">*</font>:</td>
			                                            <td>
			                                                 <html:text styleId="dataLeituraAtualString" property="dataLeituraAtualString" size="10"  maxlength="10" onkeypress="javascript:mascaraData(this,event);"/>
			                                                 <a href="javascript:abrirCalendario('CalculoConsumoEstimadoForm', 'dataLeituraAtualString')"> <img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
			                                            </td>
			                                            <td align="right">Leitura Atual<font color="#FF0000">*</font>:</td>
			                                            <td><html:text styleId="leituraAtualString" property="leituraAtualString" size="10"  maxlength="10"  onkeyup="javascript:verificaNumeroInteiro(this);"/></td>
			                                        </tr>
			                                        <tr>
			                                            <td colspan="4" align="right"><font color="#FF0000">*</font> Campos obrigatórios</td>
			                                        </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="right"><input class="bottonRightCol" type="submit" value="Calcular" /></td>
                                        </tr>
                                        <!-- Se Botão Calcular acionado -->
                                        <logic:present name="isPosted">
                                        <tr>
                                            <td align="center" class="parabg"><strong>Resultado calculado</strong></td>
                                        </tr>
                                        <tr>
                                            <td align="center">
                                                <table width="90%" border="0" align="left" cellpadding="3" cellspacing="3">
                                                    <tr>
	                                                    <td><strong>Dias de consumo: <bean:write name="qtdDiasConsumo" formatKey="number.format" locale="currentLocale"/></strong></td>
	                                                    <td><strong>Total consumido no per&iacute;odo: 
                                                            <bean:write name="totalConsumidoPeriodo" formatKey="number.format" locale="currentLocale"/></strong></td>
                                                    </tr>
                                                    <tr>
                                                        <td colspan="2"><strong>Consumo estimado (para 30 dias): <bean:write name="consumoEstimado" formatKey="number.format" locale="currentLocale"/></strong></td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        </logic:present>
                                        
                                    </table>        
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" align="right">
                                    <logic:present name="isPosted">
                                        <input name="Button" type="button" class="bottonRightCol" value="Limpar" onClick="javascript:window.location.href='exibirObterCalculoConsumoEstimadoAction.do';">
                                    </logic:present>
                                    <input name="Button" type="button" class="bottonRightCol" value="Fechar" onClick="javascript:fechar();">
                                </td>
                            </tr>
                        </table> 
                        <br> 
                    </td>
                </tr>
            </table>
        </html:form>
    <body>
</html:html>