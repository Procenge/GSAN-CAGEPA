<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<table width="770" cellspacing="4" cellpadding="0">
  <tr>
    <td  >
      <table width="100%" cellpadding="2" class="footer">
        <tr>
          <td  align="left"> <logic:present scope="application" name="dataUltimaAlteracao"> 
		Banco: ${applicationScope.dataUltimaAlteracao}
	</logic:present>  <logic:notPresent scope="application" name="dataUltimaAlteracao"> 
		PMSS
	</logic:notPresent>
	</td>
          <td align="right">Aplica&ccedil;&atilde;o: [versaoApp] </td>
        </tr>
      </table>
	</td>
  </tr>
</table>