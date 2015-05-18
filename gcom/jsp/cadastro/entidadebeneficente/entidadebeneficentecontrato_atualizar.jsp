<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="gcom.util.Util" %>
<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

 <%@ include file="/jsp/util/titulo.jsp"%>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

 <html:javascript staticJavascript="false"  formName="AtualizarEntidadeBeneficenteContratoActionForm" />

 <script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
 <script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
 <script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script> 
 <script>

function validarForm() {
  	var form = document.forms[0];
  	var formValidacao = document.forms[0];
 	if(validateAtualizarEntidadeBeneficenteContratoActionForm(form)){
		submeterFormPadrao(form);
 	}
}

function DateValidations () { 
	this.aa = new Array("dataInicioContrato", "Data de Início do Contrato inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
	this.ab = new Array("dataFimContrato", "Data de Fim do Contrato inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
	this.ac = new Array("dataEncerramento", "Data de Encerramento do Contrato inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
}

 </script>
</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">
<div id="formDiv">
<html:form action="/atualizarEntidadeBeneficenteContratoAction.do" method="post"
	name="AtualizarEntidadeBeneficenteContratoActionForm"
	enctype="multipart/form-data"
	type="gcom.gui.cadastro.entidadebeneficente.AtualizarEntidadeBeneficenteContratoActionForm">

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
			<td width="655" valign="top" class="centercoltext"><!--Início Tabela Reference a Páginação da Tela de Processo-->
			<table>
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="<bean:message key='caminho.imagens'/>parahead_left.gif" /></td>
					<td class="parabg">Atualizar Contrato de Entidade Beneficente</td>
					<td width="11" valign="top"><img border="0" src="<bean:message key='caminho.imagens'/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			 
			 <table width="100%" border="0">
			 	<tr> 
                	<td colspan="2">Para atualizar a Contrato de Entidade beneficente, informe os dados abaixo:</td>
              	</tr>
              	
				<tr>
					<td height="29"><strong>Entidade Beneficente:<font color="#FF0000">*</font></strong></td>
					<td>
					  <html:hidden property="idEntidadeBeneficente"/>
					  <html:select property="idEntidadeBeneficente" tabindex="2" disabled="true" >
					 	<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoEntidadeBeneficente" 
						              labelProperty="cliente.nome" 
						              property="id" />
					  </html:select>
					</td>
				</tr>
				
				<tr>
					<td height="29"><strong>Número do Contrato:<font color="#FF0000">*</font></strong></td>
					<td>	
						<html:hidden property="numeroContrato"/>			
						<html:text property="numeroContrato" maxlength="15" size="15" disabled="true" />
					</td>
				</tr>
				
				<tr> 
					<td width="45%"><br>
						<strong>Data Início do Contrato:<font color="#FF0000">*</font></strong>
					</td>
					<td><br>
						<html:hidden property="dataInicioContrato"/>
						<html:text property="dataInicioContrato" size="10" maxlength="10" tabindex="2" disabled="true" onkeyup="mascaraData(document.forms[0].dataInicioContrato,this)"/>
						dd/mm/aaaa
					</td>
				</tr>
				
				<tr> 
					<td width="45%"><br>
						<strong>Data Fim do Contrato:</strong>
					</td>
					<td><br>
						<logic:present name="desabilitarAtualizar" scope="session">
							<html:text property="dataFimContrato" disabled="true" size="10" maxlength="10" tabindex="2" onkeyup="mascaraData(document.forms[0].dataFimContrato,this)"/>
						</logic:present>


						<logic:notPresent name="desabilitarAtualizar" scope="session">
							<html:text property="dataFimContrato" size="10" maxlength="10" tabindex="2" onkeyup="mascaraData(document.forms[0].dataFimContrato,this)"/>
							<a href="javascript:abrirCalendario('AtualizarEntidadeBeneficenteContratoActionForm', 'dataFimContrato')">
		             			<img border="0" align="top" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="middle" alt="Exibir Calendário"/></a> 
	             		</logic:notPresent>
	             		dd/mm/aaaa
					</td>
				</tr>		
				
				<tr> 
					<td width="45%"><br>
						<strong>Data Encerramento do Contrato:</strong>
					</td>
					<td><br>
					
						<logic:present name="desabilitarAtualizar" scope="session">
							<html:text property="dataEncerramento" disabled="true" size="10" maxlength="10" tabindex="2" onkeyup="mascaraData(document.forms[0].dataEncerramento,this)"/>
		             	</logic:present>
		             	
		             	<logic:notPresent name="desabilitarAtualizar" scope="session">
							<html:text property="dataEncerramento" size="10" maxlength="10" tabindex="2" onkeyup="mascaraData(document.forms[0].dataEncerramento,this)"/>
							<a href="javascript:abrirCalendario('AtualizarEntidadeBeneficenteContratoActionForm', 'dataEncerramento')">
		             			<img border="0" align="top" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="middle" alt="Exibir Calendário"/></a> 
		             	</logic:notPresent>
		             	dd/mm/aaaa
					</td>
				</tr>									
														
														
				<tr>
					<td height="31">
					  <strong> Percentual Remuneração:<font color="#FF0000">*</font></strong>
					</td>
					<td>
						<logic:present name="desabilitarAtualizar" scope="session">
							  <html:text property="percentualRemuneracao" 
							             maxlength="9" disabled="true"
							             size="9"
							             onkeyup="formataValorMonetario(this, 9)" 
							             tabindex="3"/>
						</logic:present>
						
						<logic:notPresent name="desabilitarAtualizar" scope="session">
							  <html:text property="percentualRemuneracao" 
							             maxlength="9" 
							             size="9"
							             onkeyup="formataValorMonetario(this, 9)" 
							             tabindex="3"/>
						</logic:notPresent>						
					</td>
				</tr>
				
				<tr>
					<td height="31">
					  <strong> Valor Remuneração:</strong>
					</td>
					<td>
						<logic:present name="desabilitarAtualizar" scope="session">	
							  <html:text property="valorRemuneracao" disabled="true"
							             maxlength="9" 
							             size="9"
							             onkeyup="formataValorMonetario(this, 9)" 
							             tabindex="3"/>
						</logic:present>
						
						<logic:notPresent name="desabilitarAtualizar" scope="session">	
							  <html:text property="valorRemuneracao" 
							             maxlength="9" 
							             size="9"
							             onkeyup="formataValorMonetario(this, 9)" 
							             tabindex="3"/>
						</logic:notPresent>						
					</td>
				</tr>
				
				<tr>
					<td height="31">
					  <strong> Valor Mínimo Doação:<font color="#FF0000">*</font></strong>
					</td>
					<td>
						<logic:present name="desabilitarAtualizar" scope="session">	
							  <html:text property="valorMinimoDoacao" disabled="true"
							             maxlength="9" 
							             size="9"
							             onkeyup="formataValorMonetario(this, 9)" 
							             tabindex="3"/>
						</logic:present>
						
						<logic:notPresent name="desabilitarAtualizar" scope="session">	
							  <html:text property="valorMinimoDoacao" 
							             maxlength="9" 
							             size="9"
							             onkeyup="formataValorMonetario(this, 9)" 
							             tabindex="3"/>
						</logic:notPresent>						
					</td>
				</tr>	
				
				<tr>
					<td height="31">
					  <strong> Valor Máximo Doação:</strong>
					</td>
					<td>
						<logic:present name="desabilitarAtualizar" scope="session">	
							  <html:text property="valorMaximoDoacao" 
							             maxlength="9" disabled="true"
							             size="9"
							             onkeyup="formataValorMonetario(this, 9)" 
							             tabindex="3"/>
						</logic:present>
						
						<logic:notPresent name="desabilitarAtualizar" scope="session">	
							  <html:text property="valorMaximoDoacao" 
							             maxlength="9" 
							             size="9"
							             onkeyup="formataValorMonetario(this, 9)" 
							             tabindex="3"/>
						</logic:notPresent>						
					</td>
				</tr>
             	
              	<tr> 
                	<td><strong> <font color="#FF0000"></font></strong></td>
                	<td align="right"> <div align="left"><strong><font color="#FF0000">*</font></strong>Campos obrigat&oacute;rios</div></td>
              	</tr>
              	
              	<table width="100%" border="0">
	              <tr>
					<td>
						<logic:present name="manter" scope="session">
							<input type="button" name="ButtonReset" class="bottonRightCol" value="Voltar"
								onClick="javascript:window.location.href='/gsan/exibirManterEntidadeBeneficenteContratoAction.do'">
						</logic:present>

						<logic:notPresent name="manter" scope="session">
							<input type="button" name="ButtonReset"  class="bottonRightCol" value="Voltar"
								onClick="javascript:window.location.href='/gsan/exibirFiltrarEntidadeBeneficenteContratoAction.do'">
						</logic:notPresent> 
					
					<input name="Button" type="button" class="bottonRightCol"
							value="Cancelar" align="left"
							onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"></td>
					<td align="right">
					
					<logic:present name="desabilitarAtualizar" scope="session">
						<input name="Button" type="button" disabled="disabled"
							class="bottonRightCol" value="Atualizar" align="right"
							onClick="javascript:validarForm(document.forms[0]);">
					</logic:present>
					
					<logic:notPresent name="desabilitarAtualizar" scope="session">
						<input name="Button" type="button"
							class="bottonRightCol" value="Atualizar" align="right"
							onClick="javascript:validarForm(document.forms[0]);">
					</logic:notPresent>				
					
					
					</td>
				</tr>
	              
             	</table>
              	
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