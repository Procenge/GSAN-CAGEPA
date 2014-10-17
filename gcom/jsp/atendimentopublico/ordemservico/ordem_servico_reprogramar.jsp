<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="displaytag" %>
<link rel="stylesheet" href="<bean:message key="caminho.css"/>displaytag.css" type="text/css">	

<%@ page import="gcom.atendimentopublico.ordemservico.Atividade"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<script>
	function init() {
	
	<logic:present name="fecharPopup" scope="request">
			<logic:present name="caminhoRetorno" scope="request">
				chamarReload('${requestScope.caminhoRetorno}');
				window.close();
			</logic:present>
	</logic:present>
	
		  var form = document.forms[0];
	}

//-----------------------------------------------------------
//-----------------------------------------------------------
function validarSelecionarEquipe(){
	var form = document.forms[0];
    var selecionados = form.elements['equipe'];
    var retorno;
	var jaSelecionado = false;

	if(selecionados.length == 1){
		retorno = selecionados.options[0].value;
	}else {
		for (i=0; i< selecionados.length; i++) {
			if(selecionados.options[i].selected && i != 0){
					jaSelecionado = true;
			}
		}
	}
	if(jaSelecionado == false && selecionados.length != 1){
		alert('É necessário selecionar uma equipe.');
		//return false;
	}

	if(jaSelecionado == true){
		form.submit();
	}
}

</script>

<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/engine.js'> </script>
<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/util.js'> </script>
<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/interface/AjaxService.js'> </script>

<script language="JavaScript">

</script>

</head>

<body leftmargin="5" topmargin="5" onload="init();">

<html:form action="/reprogramarOrdemServicoAction" method="post">
<table width="530" border="0" cellspacing="5" cellpadding="0">
  <tr>
    <td width="530" valign="top" class="centercoltext">
    <table height="100%">
        <tr>
          <td></td>
        </tr>
      </table>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img  border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
          <td class="parabg">Reprogramar Ordem de Serviço</td>
          <td width="11"><img  border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
        </tr>
      </table>
      <p>&nbsp;</p>
      <table width="100%" border="0">
        <tr>
          <td colspan="4">
          </td>
        </tr>
        <tr>
          <td width="30%" valign="top"><strong>Equipes Disponíveis: </strong></td>
          <td width="70%" colspan="3">
										<html:select property="equipe" size="6" style="width:230px">
											<option value=""> </option>
											<html:options collection="colecaoEquipes" 
												labelProperty="nome" 
												property="id" />
										</html:select>
          </td>
        </tr>
        <tr>
          <td width="31%"><strong>Data Final da Programação: </strong></td>
          <td width="69%" colspan="3">
				<input type="text"
					id="data"
					name="novaDataRoteiro"
					size="8" 
					maxlength="10" 
					tabindex="3" 
					onkeyup="mascaraData(this, event);"
					value=""/>
				
				<a href="javascript:abrirCalendario('ReprogramarOrdemServicoActionForm','novaDataRoteiro');">
					<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" 
						height="15" 
						border="0" 
						alt="Exibir Calendário" 
						tabindex="4"/>
				</a>
          </td>
        </tr>
        <tr>
          <td width="30%"><strong>Selecionadas: </strong></td>
          <td width="70%" colspan="3">
								<html:text property="selecionadas" 
									readonly="true"
									style="background-color:#EFEFEF; border:0;" 
									size="3"
									maxlength="3" />
          </td>
        </tr>
        <tr>
          <td colspan="4" height="10"></td>
        </tr>
        
       	<tr>
          <td colspan="4" height="10">
          	<hr>
          </td>
        </tr>
        
        <tr>
          <td colspan="3" height="20" align="left"><input type="button" class="bottonRightCol" value="Fechar"
			style="width: 80px" onclick="window.close();"></td>
			
		  <td align="right">
		  	<input type="button" class="bottonRightCol" value="Reprogramar" style="width: 80px" onclick="this.form.submit();">&nbsp;&nbsp;&nbsp;
		  </td>
        </tr>
      </table>
	</td>
  </tr>
</table>
</html:form>
</body>
</html:html>