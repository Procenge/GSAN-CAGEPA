<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
	<script type="text/javascript">
    function imprimir(){
    	window.print();
        window.close();
    }
    
</script>

</head>
<html:html>

<body leftmargin="5" topmargin="5" onload="imprimir();">
	<div id="conteudo">
		<%	
   			String idPagamento = (String) request.getParameter("idPagamento");	
		%>
		<%=idPagamento%>
	</div>
</body>

</html:html>
