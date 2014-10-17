<%@page import="gcom.util.ConstantesSistema"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<html:html>
<link rel="stylesheet"	href="<bean:message key="caminho.css"/>EstilosCompesa.css"	type="text/css">
<script language="JavaScript"	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript"	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"	formName="FiltrarContratoArrecadadorActionForm" />

<SCRIPT LANGUAGE="JavaScript">
<!--

	//Recupera Dados Popup
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	    var form = document.forms[0];
		if (tipoConsulta == 'cliente') {
	        form.idCliente.value = codigoRegistro;
	        form.nomeCliente.value = descricaoRegistro;
	     }
	     if(tipoConsulta == 'imovel'){
	        form.idImovel.value = codigoRegistro;
	        form.inscricaoImovel.value = descricaoRegistro;
    	}
	}	
	
	// Chama Popup
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){
		if(objetoRelacionado.disabled != true){
			if (objeto == null || codigoObjeto == null){
				abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
			}
			else{
				if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
					alert(msg);
				}
				else{
					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisaMunicipio=" + tipo, altura, largura);
				}
			}
		}
	}

	function limparForm(tipo){
      var form = document.forms[0];
	    if(tipo == 'imovel')
	    {
	        form.idImovel.value = "";
	        form.inscricaoImovel.value = "";
		}
		if(tipo == 'cliente')
	    { 
	        form.idCliente.value = "";
	        form.nomeCliente.value = "";
		}
	}	

	function validarForm(formulario){
   			submeterFormPadrao(formulario);
	}
	
	function verificarChecado(valor){
		form = document.forms[0];
		if(valor == "1"){
		 	form.indicadorAtualizar.checked = true;
		 }else{
		 	form.indicadorAtualizar.checked = false;
		}
	}
	
	
	
//-->
</SCRIPT>
</head>

<body leftmargin="5" topmargin="5"
	onLoad="verificarChecado('${indicadorAtualizar}');">

<html:form action="/filtrarContratoArrecadadorAction"
	name="FiltrarContratoArrecadadorActionForm"
	type="gcom.gui.arrecadacao.FiltrarContratoArrecadadorActionForm" method="post"
	onsubmit="">

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

			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Filtrar Contrato de Arrecadador</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">

				<tr>
					<td width="100%" colspan="3">
					<table width="100%">
						<tr>
							<td width="80%">Para filtar o(s) Contratos de Arrecadador, informe os
							dados abaixo:</td>
							<td align="right"><input type="checkbox"
								name="indicadorAtualizar" value="1"
								onclick="" /><strong>Atualizar</strong>
							</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>

			<table width="100%" border="0">
				<tr>
					<td width="139" height="22"><strong>Código do Arrecadador:<font
						color="#FF0000"></font></strong></td>

					<td width="406"><strong> <html:text property="idArrecadador" size="4"
						maxlength="3" onkeyup="javascript:verificaNumeroInteiro(this);" />
					</strong></td>
				</tr>

				<tr>
					<td width="139" height="22"><strong>Número do Contrato:<font
						color="#FF0000"></font></strong></td>

					<td width="506"><strong> <html:text property="numeroContrato" size="4"
						maxlength="10" onkeyup="javascript:verificaNumeroInteiro(this);" />
					</strong></td>
				</tr>

				<tr>
					<td><strong>Cliente:<font color="#FF0000"></font></strong></td>
					<td><strong> <html:text property="idCliente" size="10"
						maxlength="10" onkeyup="javascript:verificaNumeroInteiro(this);"
						onkeypress="javascript:return validaEnter(event, 'exibirFiltrarContratoArrecadadorAction.do?objetoConsulta=1', 'idCliente');" />
					</strong> <a
						href="javascript:chamarPopup('exibirPesquisarClienteAction.do', 'cliente', null, null, 275, 480, '',document.forms[0].idCliente.value);"
						alt="Pesquisar Cliente"> <img width="23" height="21"
						src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" /></a>
					<logic:present name="existeCliente">
						<logic:equal name="existeCliente" value="exception">
							<html:text property="nomeCliente" size="35" maxlength="35"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="existeCliente" value="exception">
							<html:text property="nomeCliente" size="35" maxlength="35"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent name="existeCliente">
						<logic:empty name="FiltrarContratoArrecadadorActionForm"
							property="idCliente">
							<html:text property="nomeCliente" size="35" value=""
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:empty>
						<logic:notEmpty name="FiltrarContratoArrecadadorActionForm"
							property="idCliente">
							<html:text property="nomeCliente" size="35" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent> <a href="javascript:limparForm('cliente');"> <img
						border="0"
						src="<bean:message key='caminho.imagens'/>limparcampo.gif"
						style="cursor: hand;" /> </a></td>
				</tr>
				
				<logic:present name="colecaoConcessionaria">
					<tr>
						<td><strong>Concessionária:</strong></td>
						<td>
							<html:select property="idConcessionaria" style="width: 200px;" tabindex="3">
								<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
								<html:options collection="colecaoConcessionaria" labelProperty="nome" property="id"/>
							</html:select>
						</td>
					</tr>
				</logic:present>

				<tr>
					<td><input name="Button" type="button" class="bottonRightCol"
						value="Limpar" align="left"
						onclick="window.location.href='/gsan/exibirFiltrarContratoArrecadadorAction.do?menu=sim'"
						tabindex="8"></td>
					<td align="right" colspan="2">&nbsp;</td>
					<td width="65" align="right"><input name="Button2" type="button"
						class="bottonRightCol" value="Filtrar" align="right"
						onClick="javascript:validarForm(document.forms[0]);" tabindex="9"></td>
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

