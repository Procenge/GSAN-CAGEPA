<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>

<script language="JavaScript" type="text/javascript"
	src="<bean:message key="caminho.js"/>CalendarioEntradaParcelamento.js"></script>

<SCRIPT TYPE="text/javascript" LANGUAGE="JavaScript"><!--
		document.write(CalendarEntradaParcelamento(${requestScope.mes},${requestScope.ano},'${requestScope.situacoes}'));
	//--></SCRIPT>

</head>
<BODY BGCOLOR="#90c7fc" TOPMARGIN=2 LEFTMARGIN=5>

</body>

</html>