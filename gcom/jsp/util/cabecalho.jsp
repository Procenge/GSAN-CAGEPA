<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<table width="770" border="0" cellspacing="4" cellpadding="0">
	<tr>
		<td height="59" valign="top" class="topstrip2">
		<table width="100%" height="0" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td height="0" colspan="0" valign="bottom">
					<img src="<bean:message key="caminho.imagens"/>${applicationScope.logoMarca}" border="0" width="140" height="59">
				</td>
				<td width="35%" height="0" colspan="1" valign="middle" align="center">
					<marquee bgcolor="#CBE5FE" title="titulo" valign="middle" loop="true" scrollamount="4" behavior="scroll" direction="left">
						<font color="black"><strong>${requestScope.mensagemAviso}</strong></font>
					</marquee>
				</td>
				<td align="right" >
					<br><br>
					<%@ include file="/jsp/util/include_url_help_online.jsp"%>
				</td>		
				<td width="10%" valign="bottom" align="right">
					<!-- <img src="<bean:message key="caminho.imagens"/>solvi_procenge_qdr.gif" border="0">  -->
					<img src="<bean:message key="caminho.imagens"/>${applicationScope.logoCabecalho}" border="0" width="84" height="59">
				</td> <%-- width="65" height="40" --%>
						
			</tr>
		</table>
		</td>
	</tr>
</table>
