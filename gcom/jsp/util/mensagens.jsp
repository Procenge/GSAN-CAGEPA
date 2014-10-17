<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld"  %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<logic:messagesPresent>
	<table border="0" class="tableinlayermensagens">
	<html:messages id="error">
          <tr>
            <td>
		<li>
		<font color="red">
			<strong>
				<em>
					<bean:write name="error"/><br>
				</em>
			</strong>
		</font>
		</li>
            </td>
          </tr>
        </html:messages>
        </table>
</logic:messagesPresent>

