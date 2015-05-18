<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@ page import="gcom.util.Util" %>
<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
 <%@ include file="/jsp/util/titulo.jsp"%>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

 <html:javascript staticJavascript="false"  formName="InserirEntidadeBeneficenteContratoActionForm" />

 <script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
 <script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
 <script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script> 
 
<script>	
 function validaForm() {
	  	var form = document.forms[0];
	  	var formValidacao = document.forms[0];
	 	if(validateInserirEntidadeBeneficenteContratoActionForm(form)){
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
<html:form 	action="/inserirEntidadeBeneficenteContratoAction" enctype="multipart/form-data"
			method="post" focus="idEntidadeBeneficente">

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
					<td class="parabg">Inserir Contrato de Entidade Beneficente</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key='caminho.imagens'/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">

				<tr>
					<td colspan="2">Para adicionar um Contrato de Entidade beneficente, informe os dados
					abaixo:</td>
				</tr>
					
				<tr>
					<td height="29"><strong>Entidade Beneficente:<font color="#FF0000">*</font></strong></td>
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
					<td height="29"><strong>Número do Contrato:<font color="#FF0000">*</font></strong></td>
					<td>				
						<html:text property="numeroContrato" maxlength="10" size="10"
							onkeypress="" onkeyup="" onchange=""/>
					</td>
				</tr>
				
				<tr> 
					<td width="45%"><br>
						<strong>Data Início do Contrato:<font color="#FF0000">*</font></strong>
					</td>
					<td><br>
						<html:text property="dataInicioContrato" size="10" maxlength="10" tabindex="2" onkeyup="mascaraData(document.forms[0].dataInicioContrato,this)"/>
						<a href="javascript:abrirCalendario('InserirEntidadeBeneficenteContratoActionForm', 'dataInicioContrato')">
	             			<img border="0" align="top" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="middle" alt="Exibir Calendário"/></a> dd/mm/aaaa
					</td>
				</tr>
				
				<tr> 
					<td width="45%"><br>
						<strong>Data Fim do Contrato:</strong>
					</td>
					<td><br>
						<html:text property="dataFimContrato" size="10" maxlength="10" tabindex="2" onkeyup="mascaraData(document.forms[0].dataFimContrato,this)"/>
						<a href="javascript:abrirCalendario('InserirEntidadeBeneficenteContratoActionForm', 'dataFimContrato')">
	             			<img border="0" align="top" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="middle" alt="Exibir Calendário"/></a> dd/mm/aaaa
					</td>
				</tr>		
				
				<tr> 
					<td width="45%"><br>
						<strong>Data Encerramento do Contrato:</strong>
					</td>
					<td><br>
						<html:text property="dataEncerramento" size="10" maxlength="10" tabindex="2" onkeyup="mascaraData(document.forms[0].dataEncerramento,this)"/>
						<a href="javascript:abrirCalendario('InserirEntidadeBeneficenteContratoActionForm', 'dataEncerramento')">
	             			<img border="0" align="top" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="middle" alt="Exibir Calendário"/></a> dd/mm/aaaa
					</td>
				</tr>									
														
														
				<tr>
					<td height="31">
					  <strong>Percentual da Remuneração:<font color="#FF0000">*</font></strong>
					</td>
					<td>
					  <html:text property="percentualRemuneracao" 
					             maxlength="9" 
					             size="9"
					             onkeyup="formataValorMonetario(this, 9)" 
					             tabindex="3"/>
					</td>
				</tr>
				
				<tr>
					<td height="31">
					  <strong> Valor da Remuneração:</strong>
					</td>
					<td>
					  <html:text property="valorRemuneracao" 
					             maxlength="9" 
					             size="9"
					             onkeyup="formataValorMonetario(this, 9)" 
					             tabindex="3"/>
					</td>
				</tr>
				
				<tr>
					<td height="31">
					  <strong> Valor Mínimo da Doação:<font color="#FF0000">*</font></strong>
					</td>
					<td>
					  <html:text property="valorMinimoDoacao" 
					             maxlength="9" 
					             size="9"
					             onkeyup="formataValorMonetario(this, 9)" 
					             tabindex="3"/>
					</td>
				</tr>	
				
				<tr>
					<td height="31">
					  <strong> Valor Máximo da Doação:</strong>
					</td>
					<td>
					  <html:text property="valorMaximoDoacao" 
					             maxlength="9" 
					             size="9"
					             onkeyup="formataValorMonetario(this, 9)" 
					             tabindex="3"/>
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
						onclick="window.location.href='/gsan/exibirInserirEntidadeBeneficenteContratoAction.do?desfazer=S';">
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