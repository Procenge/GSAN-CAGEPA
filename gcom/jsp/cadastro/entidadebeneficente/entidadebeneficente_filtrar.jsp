<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<html:javascript staticJavascript="false"  formName="FiltrarEntidadeBeneficenteActionForm" />

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script>
function validarForm(form){

	if (validateFiltrarEntidadeBeneficenteActionForm(form)) {
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
<html:form action="/filtrarEntidadeBeneficenteAction"   
	name="FiltrarEntidadeBeneficenteActionForm"
  	type="org.apache.struts.action.DynaActionForm"
  	method="post"
  	focus="idCliente">

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

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
		<td width="655" valign="top" class="centercoltext">
			<!--Início Tabela Reference a Páginação da Tela de Processo-->
            <table>
              <tr>
                <td></td>
              </tr>
            </table>
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="11"><img border="0" src="<bean:message key='caminho.imagens'/>parahead_left.gif"/></td>
                <td class="parabg">Filtrar Entidade Beneficente </td>
                <td width="11" valign="top"><img border="0" src="<bean:message key='caminho.imagens'/>parahead_right.gif"/></td>
              </tr>
            </table>
			<p>&nbsp;</p>
			
            <table width="100%" border="0">
              <tr>
	           <td width="100%" colspan=2>
		          <table width="100%">
		          	<tr>
		          		<td>Para Filtrar a(s) entidade(s) beneficente(s), informe os dados abaixo: </td>
		          		<td width="74" align="right"><html:checkbox property="checkAtualizar" value="1"/><strong>Atualizar</strong></td>
		          	</tr>
		          </table>
	           </td>
	          </tr>
              <tr>
					<td>
						<strong>Cliente:</strong>
					</td>
					
					<td  width="82%" height="24">
						<html:text property="idCliente" 
						maxlength="9" 
						size="9"
						onkeyup="validaEnterComMensagem(event, 'exibirAtualizarEntidadeBeneficenteAction.do?pesquisarClienteEnter=1&filtrar=1','idCliente','Cliente');" />
						
						<a href="javascript:abrirPopup('exibirPesquisarClienteAction.do');">
							<img width="23" 
								height="21"
								src="<bean:message key='caminho.imagens'/>pesquisa.gif" 
								border="0" /></a>						

						<html:text property="nomeCliente"
							readonly="true" style="background-color:#EFEFEF; border:0; color: ${requestScope.corFonteCliente}"
							size="43" maxlength="45" />						
						
						<a href="javascript:limparCliente();">
							<img border="0"
							src="<bean:message key='caminho.imagens'/>limparcampo.gif"
							style="cursor: hand;" /></a>
					</td>
				</tr>

			  <tr>
				  <td><strong>Tipo de Débito:</strong></td>
				  <td width="82%" height="24">
				  
				    <html:text maxlength="9" tabindex="6" property="idDebitoTipo" size="9" onkeypress="validaEnterComMensagem(event, 'exibirAtualizarEntidadeBeneficenteAction.do?pesquisarDebitoTipoEnter=1&filtrar=1','idDebitoTipo','Tipo do Débito');"/> 
				    <a href="javascript:abrirPopup('exibirPesquisarTipoDebitoAction.do', 580, 800);" >
						<img width="23" height="21"	src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" title="Pesquisar Tipo de Débito" /></a>	

					<html:text property="descricaoDebitoTipo" size="43"	readonly="true"
								style="background-color:#EFEFEF; border:0; color: ${requestScope.corFonteDebitoTipo}" />

					
					<a href="javascript:limparDebitoTipo();"> 
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"	border="0" align="absmiddle" title="Limpar Tipo de Débito" />
					</a>
				  </td>
				</tr>
			  
			  <tr>
				<td><strong>Indicador de Uso:</strong></td>
				<td align="right">
				  <div align="left">
					<html:radio property="indicadorUso" tabindex="9" value="<%=ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>" /><strong>Ativo</strong>
					<html:radio property="indicadorUso"	tabindex="9" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>" /><strong>Inativo</strong>
					<html:radio property="indicadorUso"	tabindex="9" value="" /><strong>Todos</strong>
				  </div>
				</td>
			  </tr>

			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>
					<input name="Button" type="button" class="bottonRightCol" value="Limpar" align="left"
					onclick="window.location.href='<html:rewrite page="/exibirFiltrarEntidadeBeneficenteAction.do?desfazer=S"/>'">
				</td>
				<td align="right"></td>
				<td align="right">
					<gcom:controleAcessoBotao name="Button" value="Filtrar"
											  onclick="javascript:validarForm(document.forms[0]);" 
											  url="filtrarEntidadeBeneficenteAction.do" />
				</td>
			</tr>

            </table>
          <p>&nbsp;</p></tr>
		<tr>
		  	<td colspan="3">
			</td>
		</tr>
</table>

<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</div>
</body>

<%@ include file="/jsp/util/telaespera.jsp"%>

<script>

</script>



</html:html>