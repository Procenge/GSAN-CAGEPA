<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ page import="gcom.util.ConstantesSistema" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

</head>

<script language="JavaScript">

	function confirmar(){

		var formulario = document.forms[0];
		
		if(formulario.indicadorSubstituirRota != null) {
			if (formulario.indicadorSubstituirRota[0].checked == false && formulario.indicadorSubstituirRota[1].checked == false){
				alert('Informe qual será o tratamento da alteração da Rota.');
				return;
			}
		}
		if(formulario.indicadorSubstituirDistritoOperacional != null) {
			if (formulario.indicadorSubstituirDistritoOperacional[0].checked == false && formulario.indicadorSubstituirDistritoOperacional[1].checked == false){
				alert('Informe qual será o tratamento da alteração do Distrito Operacional.');
				return;
			}
		}
		
		formulario.action = "/gsan/processarAtualizarQuadraRotaImoveisAction.do";
		formulario.submit();
		
	}

</script>

<body leftmargin="5" topmargin="5">

<html:form action="/exibirProcessarAtualizarQuadraRotaImoveisAction" method="post">

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

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

		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
			  <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
			  <td class="parabg">Atualizar Rotas e Distritos Operacionais dos Imóveis da Quadra</td>
			  <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
			</tr>
		</table>
      	
      	<p>&nbsp;</p>

        <logic:present name="rotaAlterada">

    	<table width="100%" border="0">
		    <tr>
		      	<td colspan="2">Para atualizar as rotas dos imóveis da quadra selecione uma opção:</td>
		    	<td align="right"></td>
			</tr>
		</table>
				
		<table width="100%" border="0">
		
			<tr>
			   <td><strong>Total de imóveis com rota igual à rota anterior da quadra:</strong></td>
		       <td><html:text property="quantidadeImoveisRotaIgualAnterior" readonly="true"></html:text></td>
		   	</tr>
		   	
		   	<tr>
			   <td><strong>Total de imóveis com rota diferente da rota anterior da quadra:</strong></td>
		       <td><html:text property="quantidadeImoveisRotaDiferenteAnterior" readonly="true"></html:text></td>
		   	</tr>
		   
		    <tr>
		        <td height="30"><strong>Confirmar a Alteração da Quadra:</strong></td>
		        <td>
					<html:radio property="indicadorSubstituirRota" value="1"/>
					<strong>Com a substituição das rotas de todos os imóveis da quadra</strong>
					
				</td>
				<td>
					<html:radio property="indicadorSubstituirRota" value="2"/>
					<strong>Com a substituição das rotas dos imóveis com rota igual à rota anterior</strong>
				</td>
		   	</tr>
       
   		</table>
   		</logic:present>

        <logic:present name="distritoOperacionalAlterado">
	        <table height="100%">
		        <tr>
		          <td></td>
		        </tr>
	      	</table>
	
	      	<p>&nbsp;</p>	
	      	
	    	<table width="100%" border="0">
			    <tr>
			      	<td colspan="2">Para atualizar o distrito operacional dos imóveis da quadra selecione uma opção:</td>
			    	<td align="right"></td>
				</tr>
			</table>
					
			<table width="100%" border="0">
			
				<tr>
				   <td><strong>Total de imóveis com distrito operacional igual ao distrito operacional anterior da quadra:</strong></td>
			       <td><html:text property="quantidadeImoveisDistritoOperacionalIgualAnterior" readonly="true"></html:text></td>
			   	</tr>
			   	
			   	<tr>
				   <td><strong>Total de imóveis com distrito operacional diferente do distrito operacional anterior da quadra:</strong></td>
			       <td><html:text property="quantidadeImoveisDistritoOperacionalDiferenteAnterior" readonly="true"></html:text></td>
			   	</tr>
			   
			    <tr>
			        <td height="30"><strong>Confirmar a Alteração da Quadra:</strong></td>
			        <td>
						<html:radio property="indicadorSubstituirDistritoOperacional" value="1"/>
						<strong>Com a substituição dos distritos operacionais de todos os imóveis da quadra</strong>
						
					</td>
					<td>
						<html:radio property="indicadorSubstituirDistritoOperacional" value="2"/>
						<strong>Com a substituição dos distritos operacionais dos imóveis com distrito operacional igual ao anterior</strong>
					</td>
			   	</tr>
	       
	   		</table>
   		</logic:present>
		<table height="100%">
	      	<tr>
	   			<td width="100%" colspan=2>

					<input name="Button" type="button" class="bottonRightCol"
					value="Voltar" align="left"
					onclick="window.location.href='javascript:history.back();'">
					
					<input name="Button" type="button" class="bottonRightCol"
					value="Confirmar" align="left"
					onclick="javascript:confirmar();">
					
					<input name="Button" type="button" class="bottonRightCol"
							value="Cancelar" align="left"
							onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
						
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


