<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<script src="<bean:message key="caminho.js"/>jquery-1.4.2.min.js"></script>
<script src="<bean:message key="caminho.js"/>jquery.maskedinput-1.2.2.js"></script>

<script src="<bean:message key="caminho.js"/>scriptaculous/prototype.js"></script>
<script src="<bean:message key="caminho.js"/>scriptaculous/scriptaculous.js"></script>
<script src="<bean:message key="caminho.js"/>scriptaculous.js?load=controls,effects"></script>

<title>
	<logic:present scope="application" name="tituloPagina">
		${applicationScope.tituloPagina}
	</logic:present>
	<logic:notPresent scope="application" name="tituloPagina">
		GSAN - Sistema Integrado de Gest&atilde;o de Servi&ccedil;os de Saneamento
	</logic:notPresent>
</title>
