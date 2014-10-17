<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js.util"/>util.js"></script>

</head>

<body leftmargin="5" topmargin="5">

<table width="100%" border="0" cellspacing="5" cellpadding="0">
  <tr>
    <td width="100%" valign="top" class="centercoltext">
      <table height="100%">
        <tr>
          <td></td>
        </tr>
      </table>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
          <td class="parabg">Pesquisa de Atividade</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>
      <table width="100%" border="0" cellpadding="2" cellspacing="0" class="centercoltext">
        <tr align="left" bgcolor="#90c7fc">
          <td><strong>C&oacute;digo</strong></td>
          <td><strong>Atividade</strong></td>
        </tr>

      <%--Esquema de paginação--%>
      <pg:pager maxIndexPages="10" export="currentPageNumber=pageNumber"
	index="center" maxPageItems="10">
      <pg:param name="pg"/>
      <pg:param name="q"/>

      <logic:iterate name="colecaoTabelasAuxiliares" id="tabelaAuxiliar">
        <pg:item>
	<tr align="left" class="linhaBaixoTD" >
          <td height="16" class="linhaBaixoTD">
            <bean:write name="atividade" property="id"/>
	  </td>
          <td class="linhaBaixoTD">
            <a href="javascript:enviarDados('<bean:write name="tabelaAuxiliar" property="id"/>', '<bean:write name="tabelaAuxiliar" property="descricao"/>', 'tabelaAuxiliar');">
              <bean:write name="tabelaAuxiliar" property="descricao"/>
            </a>
          </td>
        </tr>
	</pg:item>
      </logic:iterate>

        <tr align="left">
          <td><div align="center"></div></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </table>
      <p>&nbsp;</p>
      <table width="100%" border="0">
        <tr>
          <td>
            <div align="center">
              <strong><%@ include file="/jsp/util/indice_pager.jsp"%></strong>
            </div>
          </td>
        </tr>
        <tr>
          <td height="24"><html:button styleClass="bottonRightCol" value="Pesquisar novamente" property="Button" onclick="javascript:history.back()"/></td>
        </tr>
      </table>
      </pg:pager>
      <%-- Fim do esquema de paginação --%>
    </td>
  </tr>
</table>

</body>
</html:html>
