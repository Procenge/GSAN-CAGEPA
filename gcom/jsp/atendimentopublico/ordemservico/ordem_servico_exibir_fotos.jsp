<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>

<script type="text/javascript">
	function remover() {
		var form = document.forms[0];
		var ind = 0;
		var idFotosRemovidas = new Array(3);
		form.action = "removerOrdemServicoFotoAction.do";
		for(indice = 0; indice < form.elements.length; indice++){
			campo = form.elements[indice];
			if (campo.type == "checkbox" && campo.name == "check" && campo.checked == true){
				idFotosRemovidas[ind] = campo.value;
				ind = ind + 1;
			}
		}	
		document.forms[0].idFotosRemovidas.value = idFotosRemovidas;
		form.submit();	
	}
	
	function carregaFoto() {
		var form = document.forms[0];
		form.document.getElementById('fotoGG').src = form.document.getElementById('foto1').src;

	}
</script>
</head>

<form action="/exibirFotoOSAction" method="get" >

<body onload="carregaFoto();">

<input type="hidden" name="idFotosRemovidas" id="idFotosRemovidas" >
	<table width="100%">
		<tr align="center" >
			<td align="center" >
				<img id="fotoGG" width="640" height="480"/>
			</td>
		</tr>
		
		<tr>
			<td align="center">	
			<c:forEach items="${idFotos}" var="idFoto">
				<c:set var="ids" value="/pesquisarFotoOSAction.do?idFoto=${idFoto}">
				</c:set>	
				<img id="foto1" width="50" height="50" onclick="fotoGG.src = this.src;"	src="<c:url value='${ids}'/>" />
				
				<logic:present name="remover" scope="request" >
					<input type="checkbox" name="check" value="${idFoto}" >
				</logic:present>
			</c:forEach>
			
			<logic:present name="remover" scope="request" >
				<input type="button" onclick="javascript:remover();" value="Remover Selecionados" class ="bottonRightCol">						
			</logic:present>
			</td>
		</tr>
	</table>
</body>
</form>
</html:html>
