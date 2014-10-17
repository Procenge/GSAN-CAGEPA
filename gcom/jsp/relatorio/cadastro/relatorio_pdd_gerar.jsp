<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<%@ page import="gcom.util.ConstantesSistema"%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"  formName="GerarRelatorioPddDynaForm"/>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript">

	function validaCamposObrigatorios(){

		var semErro=true;

		if(document.forms[0].mesAnoRelatorioPdd.value==""){

			semErro=false;
			alert('Informe Ano/Mês.');

		}
		if(document.forms[0].mesAnoRelatorioPdd.value!=""){

			semErro=retorno=verificaAnoMes(document.forms[0].mesAnoRelatorioPdd);
		}

		if(semErro==true){

			validarForm();

		}				
	}
	function validarForm(){
	
		var form = document.forms[0];
		var data = new Date();
		var mes = data.getMonth() + 1;
		var ano = data.getFullYear();
		var mesAnoAtual = mes + "/" + ano;
		if (mes < 10) {
			var mesAnoAtual = "0" + mes + "/" + ano;
		}
		var mesAnoCampo = document.forms[0].mesAnoRelatorioPdd.value;

		if (comparaMesAno(mesAnoCampo, ">", mesAnoAtual)) {
			alert("Mês/Ano do Faturamento deve ser inferior ou igual ao Mês/Ano corrente");
		} else {
			if (document.forms[0].ehRelatorioBatch.value == 2) {
				document.forms[0].target = "_blank";
			}
			document.forms[0].submit();
			document.forms[0].target = "";
		}

	}
</script>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('${requestScope.mesAnoRelatorioPdd}');" >

<html:form action="/gerarRelatorioPddAction.do"
	name="GerarRelatorioPddDynaForm"
	type="org.apache.struts.action.DynaActionForm"
	method="post">

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<input type="hidden" name="acao" value="gerarRelatorio"/>
<input type="hidden" name="relatorio" value="${requestScope.relatorio}"/>
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
			<td width="600" valign="top" class="centercoltext">
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
					<td class="parabg"><bean:write name="tituloTela"></bean:write></td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
			
				<tr>
					<td colspan="2"><bean:write name="msg"></bean:write>
					<br/>
					<br/>
					<br/>
					</td>
				</tr>
				<tr> 
                	<td><strong>Mês/Ano:</strong><font color="#FF0000">*</font>
                		<strong>
                  			<html:text property="mesAnoRelatorioPdd" 
                  				size="7" 
                  				maxlength="7" 
                  				onkeyup="mascaraAnoMes(this, event);"/>
                  				&nbsp;mm/aaaa
                  		</strong>
                  	</td>
                </tr>
				<tr>
				
				<td height="24" >
				<br/>
              	<br/>
						<input name="Submit222"
						class="bottonRightCol" value="Voltar" type="button"
						onclick="javascript:history.back();"/>
					
					</td>
				
					<td align="right">
						<input type="button" 
							name="Button" 
							class="bottonRightCol" 
							value="Gerar" 
							onClick="javascript:validaCamposObrigatorios()" />
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
