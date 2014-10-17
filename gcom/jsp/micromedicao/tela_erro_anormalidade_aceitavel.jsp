<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page isErrorPage="true" isELIgnored="false"
	import="gcom.gui.ActionServletException, gcom.util.FachadaException, java.io.*"%>



<%@page import="gcom.util.ControladorException"%><html:html>


<head>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>

EstilosCompesa.css"
	type="text/css">

<script type="text/javascript">
<!--
function toggleBox(szDivID, iState) // 1 visible, 0 hidden
{
    if(document.layers)	   //NN4+
    {
       document.layers[szDivID].visibility = iState ? "show" : "hide";
    }
    else if(document.getElementById)	  //gecko(NN6) + IE 5+
    {
        var obj = document.getElementById(szDivID);
        obj.style.visibility = iState ? "visible" : "hidden";
    }
    else if(document.all)	// IE 4
    {
        document.all[szDivID].style.visibility = iState ? "visible" : "hidden";
    }
}
// -->
</script>

<script language="JavaScript">



function verificarVoltar(){

	var botaoBack = document.getElementById('botaoBack');
	var inicioHistorico;
	//caso não possa voltar para nenhum lugar
	  //IE	 
	  if (document.all) {
		inicioHistorico = 0;
	  //Netscape	
      } else {
		inicioHistorico = 1;
      }
	if(window.history.length > inicioHistorico) {
		<% Exception controladorException = (Exception) session.getAttribute("exception");
			if (controladorException instanceof ActionServletException) {
			ActionServletException actionServletException = (ActionServletException) controladorException;
		
			if (actionServletException.getUrlBotaoVoltar() != null && 
				!actionServletException.getUrlBotaoVoltar().equalsIgnoreCase("")){	%>
				
				botaoBack.innerHTML = '<br><input name="botaoVoltar" class="bottonRightCol" value="Voltar" onclick="urlVoltar()" type="button">';
    	<%	}
    		else{	%>
    		
    			botaoBack.innerHTML = '<br><input name="botaoVoltar" class="bottonRightCol" value="Voltar" onclick="javascript:history.back();" type="button">';
    	<%	}		
    	}else{	
    		if (controladorException instanceof FachadaException) {
    			FachadaException fachadaException = (FachadaException) controladorException;
    			
    			if (fachadaException.getUrlBotaoVoltar() != null && 
    					!fachadaException.getUrlBotaoVoltar().equalsIgnoreCase("")){	%>
    					botaoBack.innerHTML = '<br><input name="botaoVoltar" class="bottonRightCol" value="Voltar" onclick="urlVoltar()" type="button">';
    	    	<%	}
    	    		else{	%>
    	    			botaoBack.innerHTML = '<br><input name="botaoVoltar" class="bottonRightCol" value="Voltar" onclick="javascript:history.back();" type="button">';
    	    	<%	}
    			
    		}else{
    	%>
    		botaoBack.innerHTML = '<br><input name="botaoVoltar" class="bottonRightCol" value="Voltar" onclick="javascript:history.back();" type="button">';
    	<%	}
    	
    	}%>
    	botaoBack.innerHTML = botaoBack.innerHTML + ' <input type="button" onclick="javascript:relatorio();" value="Imprimir Relatório Anormalidades Leitura" class="bottonRightCol">';
		return true; 
	} else {
		botaoBack.innerHTML = '<br><input name="botaoFechar" class="bottonRightCol" value="Fechar" onclick="javascript:window.close();" type="button">';
		botaoBack.innerHTML = botaoBack.innerHTML + ' <input type="button" onclick="javascript:relatorio();" value="Imprimir Relatório Anormalidades Leitura" class="bottonRightCol">';
 		return false;
	}

	
}

function urlVoltar(){

	window.location.href = document.getElementById("url").value;
	
}

function relatorio(){
	window.location.href='gerarRelatorioAnormalidadesLeiturasAction.do';
}


</script>


</head>

<body leftmargin="5" topmargin="5" onload="verificarVoltar();toggleBox('demodiv',0);">

<%if (controladorException instanceof ActionServletException) {
	ActionServletException actionServletException = (ActionServletException) controladorException; 
	
	if (actionServletException.getUrlBotaoVoltar() != null && 
		!actionServletException.getUrlBotaoVoltar().equalsIgnoreCase("")){%>
		
		<input type="hidden" id="url" value="<%="" + actionServletException.getUrlBotaoVoltar()%>">
	
	<%}%>
<%}else if(controladorException instanceof FachadaException){
	FachadaException fachadaException = (FachadaException) controladorException; 
	
	if (fachadaException.getUrlBotaoVoltar() != null && 
		!fachadaException.getUrlBotaoVoltar().equalsIgnoreCase("")){%>
		
		<input type="hidden" id="url" value="<%="" + fachadaException.getUrlBotaoVoltar()%>">
	<%}

  }
	%>	

<table width="100%" border="0" cellspacing="5" cellpadding="0">
	<tr>
		<td width="100%" valign="top" class="centercoltext">
		<table height="100%">
			<tr>
				<td></td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="17"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
				<% if (controladorException.getMessage().startsWith(padraoErro)) {%>
				<td width="100%" class="parabg">Erro</td>
				<% } else if (controladorException.getMessage().startsWith(padraoAtencao)){%>
				<td width="100%" class="parabg">Atenção</td>
				<% } %>

				<td width="3%" class="parabg">&nbsp;</td>
				<td width="4%"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
			</tr>
		</table>
		<table width="100%" border="0">
			<tr>
				<td width="6%" align="left"><%! String padraoErro = "erro";
	      		String padraoAtencao = "atencao";%> <%-- Parte que seta o ícone de erro ou de atenção de acordo com a chave da mensagem de erro--%>
				<% if (controladorException.getMessage().startsWith(padraoErro)) {%> <img
					src="<bean:message key="caminho.imagens"/>erro.gif" /> <%} else if (controladorException.getMessage().
							startsWith(padraoAtencao)) {%>
				<img src="<bean:message key="caminho.imagens"/>alerta.gif" /> <% } %>
				</td>

				<td><span style="font-size:12px;font-weight: bold;"> <%--Tenta recuperar um parâmetro da exceção para complementar a mensagem de erro--%>
				<% 
				int tamanhoColecao = 0;	
				if (controladorException instanceof ActionServletException) {
					ActionServletException actionServletException = (ActionServletException) controladorException;
					tamanhoColecao = actionServletException.getParametroMensagem().size();

			if (tamanhoColecao > 0) {
			%> <bean:message key="${exception.message}"
					arg0="<%=(tamanhoColecao - 1) >= 0 ? actionServletException.getParametroMensagem(0) : "" %>"
					arg1="<%=(tamanhoColecao - 1) >= 1 ? actionServletException.getParametroMensagem(1) : "" %>"
					arg2="<%=(tamanhoColecao - 1) >= 2 ? actionServletException.getParametroMensagem(2) : "" %>"
					arg3="<%=(tamanhoColecao - 1) >= 3 ? actionServletException.getParametroMensagem(3) : "" %>"
					arg4="<%=(tamanhoColecao - 1) >= 4 ? actionServletException.getParametroMensagem(4) : "" %>"
					 /><br>
				<% } else {%> <bean:message
					key="${exception.message}" /><br>
				<% } %> <% } else if (controladorException instanceof FachadaException){
		    	FachadaException fachadaException = (FachadaException) controladorException;
		    	tamanhoColecao = fachadaException.getParametroMensagem().size();
				if (tamanhoColecao > 0) {
                    %> <bean:message
					key="${exception.message}"
					arg0="<%=(tamanhoColecao - 1) >= 0 ? fachadaException.getParametroMensagem(0) : ""%>"
					arg1="<%=(tamanhoColecao - 1) >= 1 ? fachadaException.getParametroMensagem(1) : ""%>"
					arg2="<%=(tamanhoColecao - 1) >= 2 ? fachadaException.getParametroMensagem(2) : ""%>"
					arg3="<%=(tamanhoColecao - 1) >= 3 ? fachadaException.getParametroMensagem(3) : ""%>"
					arg4="<%=(tamanhoColecao - 1) >= 4 ? fachadaException.getParametroMensagem(4) : ""%>"
					 /><br>

				<% } else { %> <bean:message
					key="${exception.message}" /><br>
				<% }
		       } %> </span></td>
			</tr>
			<tr>
				<td colspan="2">
				<div id="botaoBack"></div>
				</td>
			</tr>
			<% if (controladorException.getMessage().startsWith(padraoErro)) { %>
			<tr>
				<td colspan="2" align="right"><font
					onclick="toggleBox('demodiv',0);"
					onmouseover="toggleBox('demodiv',1);"><strong>Visualizar Log
				Servidor</strong></font>
				</td>
			</tr>
			<% } %>
		</table>
		</td>
	</tr>
</table>
<div ID="demodiv">
<% if (controladorException.getMessage().startsWith(padraoErro)) { %>
	<p>&nbsp;</p>
	<table border="0" style="position: absolute; left: 10px; width: 133px; height: 70px; border: 1px solid #000000; background-color: #cbe5fe; z-index: 2; overflow: auto;">
		<tr>
			<td><b><%= exception.getLocalizedMessage() %></b><br>
			<p><b>With the following stack trace:</b>
			</td>
		</tr>
		<tr>
			<td>
			<textarea rows="15" cols="120">
<% ByteArrayOutputStream baos = new ByteArrayOutputStream();
controladorException.printStackTrace(new PrintStream(baos));
out.print(baos); %>
			</textarea>
			</td>
		</tr>
	</table>
<% } %>
</div>		
</body>
</html:html>



