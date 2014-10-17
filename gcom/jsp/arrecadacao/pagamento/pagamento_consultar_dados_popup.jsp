<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript">
<!--
function fechar(){
  window.close();
}
-->
</script>

</head>

<body leftmargin="5" topmargin="5" onload="resizePageSemLink(540, 550);">

<html:form action="/exibirPagamentoPopupAction" method="post">

<table width="500" border="0" cellspacing="5" cellpadding="0">
	<tr>
		<td width="500" valign="top" class="centercoltext">
		<table height="100%">
			<tr>
				<td></td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
				<td class="parabg">Dados do Pagamento</td>
				<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
			</tr>
		</table>

		<p>&nbsp;</p>
		
			<table width="100%" border="0" >
				
				<tr>
			        <td colspan="2"><strong>Aviso Bancário:</strong></td>
			    </tr>
				
				 <tr>
			        <td><strong>Agente:</strong></td>
			        <td>
				        <html:text property="codigoAgenteArrecadador" size="3" maxlength="3" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
			        </td>
		        </tr>
		        
		        <tr>
			        <td><strong>Data do Lançamento:</strong></td>
			        <td>
				        <html:text property="dataLancamento" size="10" maxlength="10" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
			        </td>
		        </tr>
		        
		        <tr>
			        <td><strong>Sequencial:</strong></td>
			        <td>
				        <html:text property="sequencial" size="40" maxlength="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
			        </td>
		        </tr>
		        <tr><td>&nbsp;</td></tr>
		        <tr>
			        <td><strong>Forma de Arrecadação:</strong></td>
			        <td>
			        	<html:text property="formaArrecadacao" size="40" maxlength="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
				    </td>
		        </tr>
		        
		        <tr>
			        <td><strong>Tipo do Documento:</strong></td>
			        <td>
				        <html:text property="tipoDocumento" size="40" maxlength="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
			        </td>
		        </tr>
		        
		        <tr>
			        <td><strong>Localidade:</strong></td>
			        <td>
				        <html:text property="codigoLocalidade" size="3" maxlength="3" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
				        <html:text property="descricaoLocalidade" size="33" maxlength="33" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
			        </td>
		        </tr>
		        <tr>
			        <td><strong>Matrícula do Imóvel:</strong></td>
			        <td>
				        <html:text property="matriculaImovel" size="40" maxlength="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
			        </td>
		        </tr>
				<tr>
			        <td><strong>Cliente:</strong></td>
			        <td>
			        	<html:text property="codigoCliente" size="3" maxlength="3" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
				        <html:text property="nomeCliente" size="33" maxlength="33" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
			        </td>
		        </tr>
		        
		        <tr>
			        <td><strong>Valor do Pagamento:</strong></td>
			        <td>
				        <html:text property="valorPagamento" size="40" maxlength="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
			        </td>
		        </tr>
		        <tr>
			        <td><strong>Número da parcela:</strong></td>
			        <td>
				        <html:text property="quantidadeParcelas" size="10" maxlength="10" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
			        </td>   
				</tr>
		        <tr>
			        <td><strong>Data do Pagamento:</strong></td>
			        <td>
				        <html:text property="dataPagamento" size="10" maxlength="10" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
			        </td>
		        </tr>
		        <tr>
			        <td><strong>Situação do Pagamento:</strong></td>
			        <td>
				        <html:text property="situacaoPagamento" size="40" maxlength="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
			        </td>
		        </tr>
		        <tr><td>&nbsp;</td></tr>
		        <tr><td><strong>Dados do documento pago:</strong></td></tr>
		        <c:choose>
		        	<c:when test="${!empty ConsultarPagamentoPopupActionForm.idConta}">
				        <tr>
					        <td><strong>Referência da conta:</strong></td>
					        <td>
						        <html:text property="referenciaConta" size="10" maxlength="10" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
					        </td>
				        </tr>
		        	</c:when>
		        	<c:when test="${!empty ConsultarPagamentoPopupActionForm.idGuiaPagamento}">
				        <tr>
					        <td><strong>Guia de Pagamento:</strong></td>
					        <td>
						        <html:text property="idGuiaPagamento" size="40" maxlength="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
					        </td>
				        </tr>
				        <tr>
					        <td><strong>Prestação:</strong></td>
					        <td>
						        <html:text property="numeroPrestacao" size="3" maxlength="3" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
					        </td>
				        </tr>
				        <tr>
					        <td><strong>Tipo de Débito:</strong></td>
					        <td>
						        <html:text property="tipoDebito" size="40" maxlength="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
					        </td>
				        </tr>
		        	</c:when>
		        	<c:when test="${!empty ConsultarPagamentoPopupActionForm.idDebito}">
				        <tr>
					        <td><strong>Débito a cobrar:</strong></td>
					        <td>
						        <html:text property="idDebito" size="40" maxlength="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
					        </td>
				        </tr>
				        <tr>
							<td><strong>Prestação:</strong></td>
					        <td>
						        <html:text property="numeroPrestacao" size="3" maxlength="3" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
					        </td>
				        </tr>
				        <tr>
					        <td><strong>Tipo de débito:</strong></td>
					        <td>
						        <html:text property="tipoDebito" size="40" maxlength="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
					        </td>   
				        </tr>
		        	</c:when>
		        
		        </c:choose>
		        
		        <tr><td>&nbsp;</td></tr>
				<tr>
				<td colspan="2" align="right"><input name="Button" type="button"
					class="bottonRightCol" value="Fechar"
					onClick="javascript:fechar();"></td>
				</tr>
				
			</table>	

		</td>
	</tr>

</table>
</html:form>
</body>
</html:html>
