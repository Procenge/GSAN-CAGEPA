<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="EfetuarAlteracaoSenhaSimplificadaActionForm" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<script language="JavaScript">
	function validarForm(form) {
	  var novaSenha = form.novaSenha.value;
	  if(novaSenha.length < 13){
		  if(validateEfetuarAlteracaoSenhaSimplificadaActionForm(form)){
		  	form.submit();
		  }
	  } else{
	     alert('Senha tem que possuir no máximo 12 caracteres.');
	     form.novaSenha.focus();
	  }
	}
	
	function verifica(){
		
		var form = document.forms[0];
		senha = form.novaSenha.value;
		forca = 0;
		mostra = document.getElementById("mostra");
		if((senha.length >= 4) && (senha.length <= 7)){
			forca += 10;
		}else if(senha.length>7){
			forca += 25;
		}
		if(senha.match(/[a-z]+/)){
			forca += 10;
		}
		if(senha.match(/[A-Z]+/)){
			forca += 20;
		}
		if(senha.match(/\d+/)){
			forca += 20;
		}
		if(senha.match(/\W+/)){
			forca += 25;
		}
		return mostra_res();
	}

	function mostra_res(){
		if(forca < 30){
			mostra.innerHTML = '<tr><td bgcolor="red" width="'+forca+'"></td><td>Fraca </td></tr>';
		}else if((forca >= 30) && (forca < 60)){
			mostra.innerHTML = '<tr><td bgcolor="yellow" width="'+forca+'"></td><td>Justa </td></tr>';;
		}else if((forca >= 60) && (forca < 85)){
			mostra.innerHTML = '<tr><td bgcolor="blue" width="'+forca+'"></td><td>Forte </td></tr>';
		}else{
			mostra.innerHTML = '<tr><td bgcolor="green" width="'+forca+'"></td><td>Excelente </td></tr>';
		}
	}
	
</script>

</head>

<body leftmargin="5" topmargin="5"	onLoad="document.EfetuarAlteracaoSenhaSimplificadaActionForm.senha.focus();verifica();">

<html:form action="/efetuarAlteracaoSenhaSimplificadaAction.do" 
           method="post" 
           name="EfetuarAlteracaoSenhaSimplificadaActionForm" 
           type="gcom.gui.seguranca.acesso.EfetuarAlteracaoSenhaSimplificadaActionForm">
	
	
  <%@ include file="/jsp/util/cabecalho.jsp"%>
  <%@ include file="/jsp/util/menu.jsp"%>

  <table width="770" border="0" cellspacing="4" cellpadding="0">
	<tr>
	  <td width="149" valign="top" class="leftcoltext">
		<div align="center">
		
		  <%--<p align="left">&nbsp;</p>--%>
		  <%--<p align="left">&nbsp;</p>--%>
		  <%--<p align="left">&nbsp;</p>--%>

		  <%@ include file="/jsp/util/informacoes_usuario.jsp"%>

		  <%--<p align="left">&nbsp;</p>--%>
		  <%--<p align="left">&nbsp;</p>--%>
		  <%--<p align="left">&nbsp;</p>--%>
		  <%--<p align="left">&nbsp;</p>--%>
		  <%--<p align="left">&nbsp;</p>--%>
		  <%--<p align="left">&nbsp;</p>--%>
		  <%--<p align="left">&nbsp;</p>--%>
		  <%--<p align="left">&nbsp;</p>--%>
		  <%--<p align="left">&nbsp;</p>--%>
		  <%--<p align="left">&nbsp;</p>--%>
		  <%--<p align="left">&nbsp;</p>--%>
		  <%--<p align="left">&nbsp;</p>--%>

		  <%@ include file="/jsp/util/mensagens.jsp"%> 

		  <%--<p align="left">&nbsp;</p>--%>
		  <%--<p align="left">&nbsp;</p>--%>
		  <%--<p align="left">&nbsp;</p>--%>
		  <%--<p align="left">&nbsp;</p>--%>
		  <%--<p align="left">&nbsp;</p>--%>
		  <%--<p align="left">&nbsp;</p>--%>
		  <%--<p align="left">&nbsp;</p>--%>
		</div>
	  </td>
	  <td width="625" valign="top" class="centercoltext">
		<!--Início Tabela Reference a Páginação da Tela de Processo-->
		<table>
		  <tr>
			<td></td>
		  </tr>
		</table>
		
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
			<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
			<td class="parabg">Alterar Senha</td>
			<td width="11" valign="top"><img border="0"	src="imagens/parahead_right.gif" /></td>
		  </tr>
		</table>
		
		<!--Fim Tabela Reference a Páginação da Tela de Processo-->
		
		<p>&nbsp;</p>
		<table width="100%" border="0">
		  <tr>
			<td colspan="2">Para alterar a senha, informe os dados abaixo:</td>
		  </tr>
		  
		  <tr> 
            <td><strong>Senha:<font color="#FF0000">*</font></strong></td>
            <td colspan="3"> 
              <html:password name="EfetuarAlteracaoSenhaSimplificadaActionForm" property="senha" size="8" maxlength="12" style="text-transform: none;" />
            </td>
          </tr>
		  
		  <tr> 
            <td><strong>Lembrete Senha:<font color="#FF0000">*</font></strong></td>
            <td colspan="3"> 
              <input type="text" name="lembreteSenha" value="<%=request.getAttribute("lembreteSenha")%>" size="30" maxlength="30" style="text-transform: none;"/>
            </td>
          </tr>

		  <tr> 
            <td><strong>Nova Senha:<font color="#FF0000">*</font></strong></td>
            <td colspan="3"> 
              <html:password name="EfetuarAlteracaoSenhaSimplificadaActionForm" property="novaSenha" size="8" maxlength="12" style="text-transform: none;" onkeyup="javascript:verifica()"/>
              <table id="mostra"></table>
            </td>
          </tr>
          
          <tr> 
            <td><strong>Confirmação da Nova Senha:<font color="#FF0000">*</font></strong></td>
            <td colspan="3"> 
              <html:password name="EfetuarAlteracaoSenhaSimplificadaActionForm" property="confirmacaoNovaSenha" size="8" maxlength="12" style="text-transform: none;" />
            </td>
          </tr>
          
          <tr> 
            <td valign="top"><strong>Nova senha deve conter no mínimo:</strong></td>
            <td colspan="3">
            	<logic:present name="quantidadeMinimaCaracteres" scope="session">
            		<strong><bean:write name="quantidadeMinimaCaracteres" scope="session" /></strong> Caracteres
            		<br>
            	</logic:present>
            	<logic:present name="quantidadeMinimaLetrasMinusculas" scope="session">
            		<strong><bean:write name="quantidadeMinimaLetrasMinusculas" scope="session" /></strong> Letra(s) Minúscula(s)
            		<br>
            	</logic:present>
            	<logic:present name="quantidadeMinimaLetrasMaiusculas" scope="session">
            		<strong><bean:write name="quantidadeMinimaLetrasMaiusculas" scope="session" /></strong> Letra(s) Maiúscula(s)
            		<br>
            	</logic:present>
            	<logic:present name="quantidadeMinimaNumeros" scope="session">
            		<strong><bean:write name="quantidadeMinimaNumeros" scope="session" /></strong> Número(s)
            		<br>
            	</logic:present>
            	<logic:present name="quantidadeMinimaCaracteresEspeciais" scope="session">
            		<strong><bean:write name="quantidadeMinimaCaracteresEspeciais" scope="session" /></strong> Caracter(es) Especial(is)
            		<br>
            	</logic:present>
            	<logic:present name="quantidadeHistorico" scope="session">
            		<strong><bean:write name="quantidadeHistorico" scope="session" /></strong> últimas senhas não podem se repetir
            		<br>
            	</logic:present>
            </td>
          </tr>
            
          <tr> 
            <td colspan="4">&nbsp;<font color="#FF0000">*</font>Campo Obrigat&oacute;rio</td>
          </tr>	
            	
		  <tr> 
            <td colspan="4"> 
              <div align="right"> 
                <input name="Button" type="button" class="bottonRightCol" value="Alterar" onclick="validarForm(document.forms[0]);">
              </div>
            </td>
          </tr>
	  </table>
	  
	  <p>&nbsp;</p>
	  </td>
	</tr>
  </table>

  <%@ include file="/jsp/util/rodape.jsp"%>
  
</html:form>
</body>
</html:html>

