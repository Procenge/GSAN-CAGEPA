<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<logic:present name="urlHelpOnLine">

	<logic:notEmpty name="urlHelpOnLine">
		<logic:present name="chaveUrlHelpOnLine">
			<span style="font-weight: bold; color: #3165CE;"> <a
				href="javascript:abrirPopupHelp('<bean:message key="caminho.helpgsan.html"/><bean:write name="urlHelpOnLine" />', 500, 700);"
				title="<bean:write name="chaveUrlHelpOnLine" />"><img
				src="<bean:message key="caminho.imagens"/>help.png" border="0"></a>
			</span>
		</logic:present>
		<logic:notPresent name="chaveUrlHelpOnLine">
			<span style="font-weight: bold; color: #3165CE;"> <a
				href="javascript:abrirPopupHelp('<bean:message key="caminho.helpgsan.html"/><bean:write name="urlHelpOnLine" />', 500, 700);"><img
				title="Ajuda<logic:present name="nomeFuncionalidadeHelpOnLine"><bean:write name="nomeFuncionalidadeHelpOnLine" /></logic:present>"
				src="<bean:message key="caminho.imagens"/>help.png" border="0"></a>
			</span>
		</logic:notPresent>
	</logic:notEmpty>

	<logic:empty name="urlHelpOnLine">
		<logic:present name="chaveUrlHelpOnLine">
			<span style="color: #696969;"
				title="<bean:write name="chaveUrlHelpOnLine" />"><img
				src="<bean:message key="caminho.imagens"/>help.png" border="0"></span>
		</logic:present>
		<logic:notPresent name="chaveUrlHelpOnLine">
			<span style="color: #696969;"><a
				href="javascript:abrirPopupHelp('<bean:message key="caminho.helpgsan.html"/>default.htm', 500, 700);"><img
				title="Ajuda<logic:present name="nomeFuncionalidadeHelpOnLine"><bean:write name="nomeFuncionalidadeHelpOnLine" /></logic:present>"
				src="<bean:message key="caminho.imagens"/>help.png" border="0"></a></span>
		</logic:notPresent>
	</logic:empty>

</logic:present>

<logic:notPresent name="urlHelpOnLine">
	<logic:present name="chaveUrlHelpOnLine">
		<span style="color: #696969;"
			title="<bean:write name="chaveUrlHelpOnLine" />"><img
			src="<bean:message key="caminho.imagens"/>help.png" border="0"></span>
	</logic:present>
	<logic:notPresent name="chaveUrlHelpOnLine">
		<span style="color: #696969;"><a
			href="javascript:abrirPopupHelp('<bean:message key="caminho.helpgsan.html"/>default.htm', 500, 700);"><img
			title="Ajuda<logic:present name="nomeFuncionalidadeHelpOnLine"><bean:write name="nomeFuncionalidadeHelpOnLine" /></logic:present>"
			src="<bean:message key="caminho.imagens"/>help.png" border="0"></a></span>
	</logic:notPresent>
</logic:notPresent>