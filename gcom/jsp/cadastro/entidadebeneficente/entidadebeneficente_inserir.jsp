<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.util.Util" %>
<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
 <%@ include file="/jsp/util/titulo.jsp"%>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

 <html:javascript staticJavascript="false"  formName="InserirEntidadeBeneficenteActionForm" />

 <script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
 <script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
 <script>
	
 function validaForm() {
	  	var form = document.forms[0];
	  	var formValidacao = document.forms[0];
	 	if(validateInserirEntidadeBeneficenteActionForm(form)){
			submeterFormPadrao(form);
	 	}
	}
 
 //Recebe os dados do(s) popup(s)
 function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

   var form = document.forms[0];

    if (tipoConsulta == 'cliente') {
     form.idCliente.value = codigoRegistro;
     form.nomeCliente.value = descricaoRegistro;
   }
    
    if (tipoConsulta == 'tipoDebito') { 	
   	  form.idDebitoTipo.value = codigoRegistro;
   	  form.descricaoDebitoTipo.value = descricaoRegistro; 
   	}
 }
 
 function limparCliente(){
		var form = document.forms[0];
		form.idCliente.value = "";
	     form.nomeCliente.value = "";
	}
 
 function limparDebitoTipo(){
		var form = document.forms[0];
		form.idDebitoTipo.value = "";
	   	  form.descricaoDebitoTipo.value = ""; 
	}
	
 </script>
</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">
<div id="formDiv">
<html:form 	action="/inserirEntidadeBeneficenteAction" enctype="multipart/form-data"
			method="post" focus="idCliente">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<input type="hidden" name="tipoPesquisa" />

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
			<td width="655" valign="top" class="centercoltext"><!--Início Tabela Reference a Páginação da Tela de Processo-->
			<table>
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key='caminho.imagens'/>parahead_left.gif" /></td>
					<td class="parabg">Inserir Entidade Beneficente</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key='caminho.imagens'/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">

				<tr>
					<td colspan="2">Para adicionar uma entidade beneficente, informe os dados
					abaixo:</td>
				</tr>
				<tr>
					<td>
						<strong>Cliente:<font color="#FF0000">*</font></strong>
					</td>
					
					<td  width="82%" height="24">
						<html:text property="idCliente" 
						maxlength="9" 
						size="9"
						onkeyup="return validaEnterComMensagem(event, 'exibirInserirEntidadeBeneficenteAction.do', 'idCliente', 'Código do Cliente');" />
						
						<a href="javascript:abrirPopup('exibirPesquisarClienteAction.do');">
							<img width="23" 
								height="21"
								src="<bean:message key='caminho.imagens'/>pesquisa.gif" 
								border="0" /></a>						

						<html:text property="nomeCliente"
							readonly="true" style="background-color:#EFEFEF; border:0; color: ${requestScope.corFonteCliente}"
							size="45" maxlength="45" />						
						
						<a href="javascript:limparCliente();">
							<img border="0"
							src="<bean:message key='caminho.imagens'/>limparcampo.gif"
							style="cursor: hand;" /></a>
					</td>
				</tr>
				
				<tr>
				  <td><strong>Tipo de Débito:<font color="#FF0000">*</font></strong></td>
				  <td width="82%" height="24">
				  
				    <html:text maxlength="9" tabindex="6" property="idDebitoTipo" size="9" onkeypress="validaEnterComMensagem(event, 'exibirInserirEntidadeBeneficenteAction.do','idDebitoTipo','Tipo do Débito');"/> 
				    <a href="javascript:abrirPopup('exibirPesquisarTipoDebitoAction.do', 580, 800);" >
						<img width="23" height="21"	src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" title="Pesquisar Tipo de Débito" /></a>	

					<html:text property="descricaoDebitoTipo" size="45"	readonly="true"
								style="background-color:#EFEFEF; border:0; color: ${requestScope.corFonteDebitoTipo}" />

					
					<a href="javascript:limparDebitoTipo();"> 
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"	border="0" align="absmiddle" title="Limpar Tipo de Débito" />
					</a>
				  </td>
				</tr>
				
				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>
				<tr>
					<td><strong> <font color="#FF0000"> <input
						name="btLimpar" class="bottonRightCol" value="Limpar"
						type="button"
						onclick="window.location.href='/gsan/exibirInserirEntidadeBeneficenteAction.do?menu=sim';">
					<input type="button" name="Button" class="bottonRightCol"
						value="Cancelar" tabindex="6"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
						style="width: 80px" /> </font></strong></td>
					<td align="right"><input type="button" onclick="javascript:validaForm();" name="botaoInserir"
						class="bottonRightCol" value="Inserir"></td>
				</tr>
				
			</table>
			<p>&nbsp;</p>
		</tr>
		<tr>
			<td colspan="3"></td>
		</tr>
	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</div>
</body>

<%@ include file="/jsp/util/telaespera.jsp"%>

</html:html>