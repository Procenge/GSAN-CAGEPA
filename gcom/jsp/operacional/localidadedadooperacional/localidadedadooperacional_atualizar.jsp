<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="gcom.util.ConstantesSistema"%>
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false" formName="AtualizarLocalidadeDadoOperacionalActionForm" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">
<!--

function validarForm(formulario){
	if (validateAtualizarLocalidadeDadoOperacionalActionForm(formulario)){
		submeterFormPadrao(formulario);
	}
}

//-->
</SCRIPT>

</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">
<html:form action="/atualizarLocalidadeDadoOperacionalAction" 
	name="AtualizarLocalidadeDadoOperacionalActionForm"
	type="gcom.gui.operacional.localidadedadooperacional.AtualizarLocalidadeDadoOperacionalActionForm"
	method="post">

<input type="hidden" name="atualizouPagina" id="atualizouPagina" value="1">

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<table width="770" border="0" cellspacing="5" cellpadding="0">
  <tr>
    <td width="130" valign="top" class="leftcoltext">
      <div align="center">
        <p align="left">&nbsp;</p>
        <p align="left">&nbsp;</p>
        <p align="left">&nbsp;</p>

	<%@ include file="/jsp/util/informacoes_usuario.jsp"%>

        <p align="left">&nbsp;</p>
        <p align="left">&nbsp;</p>
        <p align="left">&nbsp;</p>
        <p align="left">&nbsp;</p>
        <p align="left">&nbsp;</p>
        <p align="left">&nbsp;</p>
        <p align="left">&nbsp;</p>
        <p align="left">&nbsp;</p>
        <p align="left">&nbsp;</p>
        <p align="left">&nbsp;</p>
        <p align="left">&nbsp;</p>
        <p align="left">&nbsp;</p>

	<%@ include file="/jsp/util/mensagens.jsp"%>

        <p align="left">&nbsp;</p>
        <p align="left">&nbsp;</p>
        <p align="left">&nbsp;</p>
        <p align="left">&nbsp;</p>
        <p align="left">&nbsp;</p>
        <p align="left">&nbsp;</p>
        <p align="left">&nbsp;</p>
      	</div>
      	</td>

	<td width="625" valign="top" class="centercoltext">

        <table height="100%">
        <tr>
          <td></td>
        </tr>
      	</table>

      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
          <td class="parabg">Atualizar Dados Operacionais da Localidade</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>

      <table width="100%" border="0">
      <tr>
      	<td colspan="2">Para alterar os dados operacionais da localidade, informe os dados abaixo:</td>
      	<td align="right"></td>
      </tr>
      </table>
    
      <table width="100%" border="0">
      	<tr>
			<td><strong>Refer&ecirc;ncia: <font	color="#FF0000">*</font></strong></td>
			<td><html:text property="mesAnoReferencia" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" size="8" maxlength="7" />&nbsp;mm/aaaa</td>
		</tr>
		<tr>
	  		<td width="30%"><strong>Localidade:<font color="#FF0000">*</font></strong></td>
			<td width="70%" height="24" colspan="2">
				<html:text maxlength="3" property="idLocalidade" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" size="3"  tabindex="1" />&nbsp;&nbsp;
				<html:text property="descricaoLocalidade" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
			</td>
	    </tr>
		
		<tr>
			<td><strong>Volume Produzido:</strong></td>
			<td><html:text property="volumeProduzido" maxlength="9" size="9" onkeypress="return isCampoNumerico(event);" />&nbsp;m3</td>
		</tr>
		<tr>
			<td><strong>Extensão da Rede de &Aacute;gua:</strong></td>
			<td><html:text property="extensaoRedeAgua" maxlength="9" size="9" onkeypress="return isCampoNumerico(event);" />&nbsp;Km</td>
		</tr>
		<tr>
			<td><strong>Extensão da Rede de Esgoto:</strong></td>
			<td><html:text property="extensaoRedeEsgoto" maxlength="9" size="9" onkeypress="return isCampoNumerico(event);" />&nbsp;Km</td>
		</tr>
		<tr>
			<td><strong>Num. Funcion&aacute;rios da Adm.:</strong></td>
			<td><html:text property="qtdFuncionariosAdministracao" maxlength="6" size="9" onkeypress="return isCampoNumerico(event);" /></td>
		</tr>
		<tr>
			<td><strong>Num. Funcion&aacute;rios da Adm. Terceiros:</strong></td>
			<td><html:text property="qtdFuncionariosAdministracaoTercerizados" maxlength="6" size="9" onkeypress="return isCampoNumerico(event);" /></td>
		</tr>
		<tr>
			<td><strong>Num. Funcion&aacute;rios da Oper.:</strong></td>
			<td><html:text property="qtdFuncionariosOperacao" maxlength="6" size="9" onkeypress="return isCampoNumerico(event);" /></td>
		</tr>
		<tr>
			<td><strong>Num. Funcion&aacute;rios da Oper. Terceiros:</strong></td>
			<td><html:text property="qtdFuncionariosOperacaoTercerizados" maxlength="6" size="9" onkeypress="return isCampoNumerico(event);" /></td>
		</tr>
		<tr>
			<td><strong>Qtd. Sulfato de Aluminio:</strong></td>
			<td><html:text property="qtdSulfatoAluminio" maxlength="9" size="9" onkeypress="return isCampoNumerico(event);" />&nbsp;T</td>
		</tr>
		<tr>
			<td><strong>Qtd. Cloro Gasoso:</strong></td>
			<td><html:text property="qtdCloroGasoso" maxlength="9" size="9" onkeypress="return isCampoNumerico(event);" />&nbsp;T</td>
		</tr>
		<tr>
			<td><strong>Qtd. Hipocl. de Sodio:</strong></td>
			<td><html:text property="qtdHipocloritoSodio" maxlength="9" size="9" onkeypress="return isCampoNumerico(event);" />&nbsp;T</td>
		</tr>
		<tr>
			<td><strong>Qtd. Fluor:</strong></td>
			<td><html:text property="quantidadeFluor" maxlength="9" size="9" onkeypress="return isCampoNumerico(event);" />&nbsp;T</td>
		</tr>
		<tr>
			<td><strong>Consumo de Energia:</strong></td>
			<td><html:text property="qtdConsumoEnergia" maxlength="12" size="12" onkeyup="javascript:formataValorDecimal(this, 2, event);" onblur="javascript:formataValorDecimal(this, 2, null);"/>&nbsp;Kwh</td>
		</tr>
		<tr>
			<td><strong>Qtd. Horas Paralisadas:</strong></td>
			<td><html:text property="qtdHorasParalisadas" maxlength="12" size="12" onkeyup="javascript:formataValorDecimal(this, 2, event);" onblur="javascript:formataValorDecimal(this, 2, null);"/></td>
		</tr>
		
		<tr>
        <td height="30"><strong>Indicador de uso:</strong></td>
        <td>
			<html:radio property="indicadorUso" value="<%="" + ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>"/><strong>Ativo&nbsp;
			<html:radio property="indicadorUso" value="<%="" + ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>"/>Inativo</strong>
		</td>
      </tr>
		
      <tr>
      	<td><strong> <font color="#FF0000"></font></strong></td>
        <td align="right"><div align="left"><strong><font color="#FF0000">*</font></strong>
                    Campos obrigat&oacute;rios</div></td>
      </tr>
      <tr>
      <html:hidden name="AtualizarLocalidadeDadoOperacionalActionForm" property="idLocalidadeDadoOperacional" />
      	<td colspan="2" widt="100%">
      		<table width="100%">
				<tr>
					<td> 
		      			<logic:present name="voltar">
							<logic:equal name="voltar" value="filtrar">
								<input name="Button" type="button" class="bottonRightCol"
								value="Voltar" align="left"
								onclick="window.location.href='<html:rewrite page="/exibirAtualizarLocalidadeDadoOperacionalAction.do?objetoConsulta=1"/>'">
							</logic:equal>
							<logic:equal name="voltar" value="filtrarNovo">
								<input name="Button" type="button" class="bottonRightCol"
								value="Voltar" align="left"
								onclick="window.location.href='<html:rewrite page="/exibirFiltrarLocalidadeDadoOperacionalAction.do"/>'">
							</logic:equal>
							<logic:equal name="voltar" value="manter">
								<input name="Button" type="button" class="bottonRightCol"
								value="Voltar" align="left"
								onclick="window.history.back();">
							</logic:equal>
						</logic:present>
						<logic:notPresent name="voltar">
							<input name="Button" type="button" class="bottonRightCol"
							value="Voltar" align="left"
							onclick="window.location.href='<html:rewrite page="/exibirAtualizarLocalidadeDadoOperacionalAction.do"/>'">
						</logic:notPresent>
						<input name="Submit22"
							class="bottonRightCol" value="Desfazer" type="button"
							onclick="window.location.href='/gsan/exibirAtualizarLocalidadeDadoOperacionalAction.do?desfazer=S&localidadeDadoOperacionalID=<bean:write name="AtualizarLocalidadeDadoOperacionalActionForm" property="idLocalidadeDadoOperacional" />';">
						<input name="Submit23" class="bottonRightCol" value="Cancelar"
							type="button"
							onclick="window.location.href='/gsan/telaPrincipal.do'">
	      			</td>
	      			<td align="right">
	      			<gcom:controleAcessoBotao name="Button" value="Atualizar" onclick="javascript:validarForm(document.forms[0]);" url="atualizarLocalidadeDadoOperacionalAction.do"/>
	      			<%--<INPUT type="button" class="bottonRightCol" value="Atualizar" tabindex="5" onclick="validarForm(document.forms[0]);" style="width: 70px">--%></td>
	      		</tr>
      		</table>
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
