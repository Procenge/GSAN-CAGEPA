<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

</head>

<html:form
  action="/pesquisarAtividadeAction"
  method="post"
>

<body leftmargin="5" topmargin="5">
<table width="635" border="0" cellspacing="5" cellpadding="0">
  <tr>
    <td width="100%" valign="top" class="centercoltext"> <table height="100%">
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
      <table width="100%" border="0">
        <tr>
          <td colspan="2">Preencha os campos para pesquisar uma atividade:</td>
        </tr>
        <tr>
          <td><strong>C&oacute;digo:</strong></td>
          <td>
            <html:text property="id"/><font size="1">&nbsp;(somente números)</font>
	    <br><font color="red"><html:errors property="id"/></font>
          </td>
        </tr>
        <tr>
          <td width="15%"><strong>Descri&ccedil;&atilde;o:</strong></td>
          <td width="85%"><html:text maxlength="60" property="descricao" size="60"/> </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td><html:submit styleClass="bottonRightCol" value="Pesquisar"/></td>
          <td>&nbsp;</td>
        </tr>
      </table>
    <p>&nbsp;</p></td>
  </tr>
</table>

</body>

</html:form>
</html:html>