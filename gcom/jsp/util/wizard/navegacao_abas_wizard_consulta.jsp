<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="gcom.gui.*"%>

<%String numeroPagina = request.getParameter("numeroPagina");
String nomeUnicoWizard = null;
if (session.getAttribute("nomeUnicoWizard") != null){
	nomeUnicoWizard = (String) session.getAttribute("nomeUnicoWizard");
}	
StatusWizard statusWizard = null;
if (nomeUnicoWizard != null){
	  statusWizard = (StatusWizard) session.getAttribute(nomeUnicoWizard);
}else{
	  statusWizard = (StatusWizard) session.getAttribute("statusWizard");
}
			StatusWizard.StatusWizardItem itemWizardAba = statusWizard.retornarItemWizard(Integer.parseInt(numeroPagina));
			pageContext.setAttribute("statusWizard", statusWizard);
			pageContext.setAttribute("numeroPagina", numeroPagina);
			pageContext.setAttribute("caminhoActionFinalPagina", itemWizardAba.getCaminhoActionFinal());
			pageContext.setAttribute("caminhoActionInicialPagina",
					itemWizardAba.getCaminhoActionInicial());
			String hint = statusWizard.gerarHint();
			pageContext.setAttribute("hint", hint);

			int qtdAbas = statusWizard.getRelacaoNumeroPaginaCaminho().size();
			
			//Variável gerada para realização da condicional que verifica a existência de hint para funcionalidade
			// Ficou constatado que apenas os tipos primitivos do Java podem ser manipulados via Javascript
			
			int condicaoHint = 0;
			
			if (hint.trim().length() > 0){
				condicaoHint = 1;
			}

			%>

<logic:present name="montarPopUp">
<div id='Layer1'
	style='position:absolute; left:0px; top:14px; width:633px; height:40px; z-index:1'>
</logic:present>	

<logic:notPresent name="montarPopUp">
<div id='Layer1'
	style='position:absolute; left:166px; top:78px; width:633px; height:40px; z-index:1'>
</logic:notPresent>

	
	
	
<table align="right" cellpadding="0" cellspacing="0">
	<tr>
		<%int contador = 1;%>
		<logic:notEmpty name="hint">
			<td valign="center"><img border="0" width="25" height="25"
				src="<bean:message key="caminho.imagens"/>informacao.gif"
				onmouseover="this.T_BGCOLOR='whitesmoke';this.T_LEFT=true;return escape('<%=hint%>')" />&nbsp;&nbsp;&nbsp;
			</td>
		</logic:notEmpty>
		<logic:iterate name="statusWizard"
			property="relacaoNumeroPaginaCaminho" id="itemWizard">
			<td valign="top"><logic:notEqual name="itemWizard"
				property="value.numeroPagina" value="${pageScope.numeroPagina}">
				<a
					href="javascript:window.location.href='/gsan/<bean:write name="statusWizard" property="caminhoActionPrincipalWizard"/>.do?destino=<%=contador%>&action=<bean:write name="itemWizard" property="value.caminhoActionInicial" />';">
				<img id="<%=contador%>" border="0"
					src="<bean:message key="caminho.imagens"/><bean:write name="itemWizard" property="value.imagemNaoSelecionada"/>" />
				</a>
			</logic:notEqual> <logic:equal name="itemWizard"
				property="value.numeroPagina" value="${pageScope.numeroPagina}">
				<img id="<%=contador%>" border="0"
					src="<bean:message key="caminho.imagens"/><bean:write name="itemWizard" property="value.imagemSelecionada"/>" />
			</logic:equal></td>
			<%contador++;%>
		</logic:iterate>
	</tr>
</table>
</div>

<SCRIPT LANGUAGE="JavaScript">
<!--
	var padrao = 136;

	if (document) {
	
		var qtdAbas = <%=""+qtdAbas%>;
		var hint = <%=""+condicaoHint%>;
			
		 switch (qtdAbas){
			case 2: 
				document.getElementById("Layer1").style.left = padrao;
				break;
			case 3: 
				document.getElementById("Layer1").style.left = padrao;
				break;
			case 4: 
				document.getElementById("Layer1").style.left = padrao;
				break;
			case 5: 
				
				if (hint == 0){
					document.getElementById("Layer1").style.left = padrao;
				}
				else{
					document.getElementById("Layer1").style.left = padrao;
				}
				
				break;
			case 6: 
				
				if (hint == 0){
					document.getElementById("Layer1").style.left = padrao;
				}
				else{
					document.getElementById("Layer1").style.left = padrao;
				}
				
				break;
			case 7: 
			
				if (hint == 0){
					document.getElementById("Layer1").style.left = padrao;
				}
				else{
					document.getElementById("Layer1").style.left = padrao;
				}
				
				break;
			case 10: 
			
				if (hint == 0){
					document.getElementById("Layer1").style.left = 190;
				}
				else{
					document.getElementById("Layer1").style.left = 190;
				}
				
				break;
				
			case 11: 
			
				if (hint == 0){
					document.getElementById("Layer1").style.left = 166;
				}
				else{
					document.getElementById("Layer1").style.left = 166;
				}
				
				break;
			default : 
		}
	}
	else{
		document.getElementById("Layer1").style.left = padrao;
	}

//-->
</SCRIPT>
