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

<html:javascript staticJavascript="false"  formName="FiltrarEntidadeBeneficenteContratoActionForm" />

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
 <script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script> 
 
<script>
function validarForm(form){
	var form = document.forms[0];
	
	if(validateFiltrarEntidadeBeneficenteContratoActionForm(form)){
		if (validarDatas(form.dataInicialInicioContrato.value, form.dataFinalInicioContrato.value, "Período do Início do Contrato") 
			 && validarDatas(form.dataInicioFimContrato.value, form.dataFinalFimContrato.value, "Período do Fim do Contrato")) {
			form.submit();
		}
	}

}

function validarDatas(dataInicial, dataFinal, nomeCampo) {
	//Verificando as datas iniciais e finais.
	retorno = true;
	if(dataInicial != '' && dataFinal != '') {
		if(comparaDatas(dataInicial, '<', dataFinal) == false) {
			retorno = false;
			alert('Data inicial não pode ser maior que a data final.('+ nomeCampo +')');
		}		
	}
	
	return retorno;
}

function DateValidations () { 
	this.aa = new Array("dataInicialInicioContrato", "Período Inicial da Data de Início do Contrato inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
	this.ab = new Array("dataFinalInicioContrato", "Período Final da Data de Início do Contrato inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
	
	this.ac = new Array("dataInicioFimContrato", "Período Inicial da Data de Fim do Contrato inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
	this.ad = new Array("dataFinalFimContrato", "Período Final da Data de Fim do Contrato inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
	
}

</script>
</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">
<div id="formDiv">
<html:form action="/filtrarEntidadeBeneficenteContratoAction"   
	name="FiltrarEntidadeBeneficenteContratoActionForm"
  	type="gcom.gui.cadastro.entidadebeneficente.FiltrarEntidadeBeneficenteContratoActionForm"
  	method="post"
  	focus="idEntidadeBeneficente">

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
                <td class="parabg">Filtrar Contrato de Entidade Beneficente </td>
                <td width="11" valign="top"><img border="0" src="<bean:message key='caminho.imagens'/>parahead_right.gif"/></td>
              </tr>
            </table>
			<p>&nbsp;</p>
			
            <table width="100%" border="0">
              <tr>
	           <td width="100%" colspan=3>
		          <table width="100%">
		          	<tr>
		          		<td>Para Filtrar a(s) o contrato de Entidade(s) beneficente(s), informe os dados abaixo: </td>
		          		<td width="74" align="right"><html:checkbox property="checkAtualizar" value="1"/><strong>Atualizar</strong></td>
		          	</tr>
		          </table>
	           </td>
	          </tr>
	          
				<tr>
					<td height="29"><strong>Entidade Beneficente:</strong></td>
					<td>
					  <html:select property="idEntidadeBeneficente" tabindex="2">
					 	<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoEntidadeBeneficente" 
						              labelProperty="cliente.nome" 
						              property="id" />
					  </html:select>
					</td>
				</tr>
				
				<tr>
					<td height="29"><strong>Número do Contrato:</strong></td>
					<td>				
						<html:text property="numeroContrato" maxlength="10" size="10"
							onkeypress="" onkeyup="" onchange=""/>
					</td>
				</tr>				

				<tr > 
					<td>
						<strong>Período do Início do Contrato:</strong>
					</td>
					<td>
						<html:text property="dataInicialInicioContrato" size="10" maxlength="10" tabindex="2" onkeyup="mascaraData(document.forms[0].dataInicialInicioContrato,this)"/>
						<a href="javascript:abrirCalendario('FiltrarEntidadeBeneficenteContratoActionForm', 'dataInicialInicioContrato')">
	             			<img border="0" align="top" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="middle" alt="Exibir Calendário"/></a>
	             		<strong>a </strong>
						<html:text property="dataFinalInicioContrato" size="10" maxlength="10" tabindex="2" onkeyup="mascaraData(document.forms[0].dataFinalInicioContrato,this)"/>
						<a href="javascript:abrirCalendario('FiltrarEntidadeBeneficenteContratoActionForm', 'dataFinalInicioContrato')">
	             			<img border="0" align="top" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="middle" alt="Exibir Calendário"/></a> (dd/mm/aaaa)	             			
					</td>
				</tr>
				
				
				<tr> 
					<td>
						<strong>Período do Fim do Contrato:</strong>
					</td>
					<td>
						<html:text property="dataInicioFimContrato" size="10" maxlength="10" tabindex="2" onkeyup="mascaraData(document.forms[0].dataInicioFimContrato,this)"/>
						<a href="javascript:abrirCalendario('FiltrarEntidadeBeneficenteContratoActionForm', 'dataInicioFimContrato')">
	             			<img border="0" align="top" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="middle" alt="Exibir Calendário"/></a>
						<strong>a </strong>
						<html:text property="dataFinalFimContrato" size="10" maxlength="10" tabindex="2" onkeyup="mascaraData(document.forms[0].dataFinalFimContrato,this)"/>
						<a href="javascript:abrirCalendario('FiltrarEntidadeBeneficenteContratoActionForm', 'dataFinalFimContrato')">
	             			<img border="0" align="top" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="middle" alt="Exibir Calendário"/></a> (dd/mm/aaaa)	             			
					</td>
				</tr>

			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>
					<input name="Button" type="button" class="bottonRightCol" value="Limpar" align="left"
					onclick="window.location.href='<html:rewrite page="/exibirFiltrarEntidadeBeneficenteContratoAction.do?desfazer=S"/>'">
				</td>
				<td align="right"></td>
				<td align="right">
					<gcom:controleAcessoBotao name="Button" value="Filtrar"
											  onclick="javascript:validarForm(document.forms[0]);" 
											  url="filtrarEntidadeBeneficenteContratoAction.do" />
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