<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<%@ page import="gcom.util.ConstantesSistema"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"
	formName="EmitirDeclaracaoAnualQuitacaoDebitosActionForm" />

<script>
	function validarForm(form) {
		
		if(validateEmitirDeclaracaoAnualQuitacaoDebitosActionForm(form)) {
			form.action = "/gsan/emitirDeclaracaoAnualQuitacaoDebitosAction.do";
			submeterFormPadrao(form);
		}
	}
	
	function limparImovel() {

    	var form = document.forms[0];

    	form.idImovel.value = "";
    	form.inscricaoImovel.value = "";
    	form.inscricaoImovel.style.color = "#000000";  
    	
     	form.action = "/gsan/exibirEmitirDeclaracaoAnualQuitacaoDebitosAction.do";
		form.submit();
  	}
	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	    
		var form = document.forms[0];
	    
	    if (tipoConsulta == 'imovel') {

	      form.idImovel.value = codigoRegistro;
	      form.inscricaoImovel.value = descricaoRegistro;
	      form.inscricaoImovel.style.color = "#000000";
	    }
	    
	   
	    form.action = "/gsan/exibirEmitirDeclaracaoAnualQuitacaoDebitosAction.do";
		form.submit();
		
	}
	
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){
		
		if (objeto == null || codigoObjeto == null){
			abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
		} else{
			if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
				alert(msg);
			} else{
				abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
			}
		}
	}
</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/exibirEmitirDeclaracaoAnualQuitacaoDebitosAction" name="EmitirDeclaracaoAnualQuitacaoDebitosActionForm"
	type="gcom.gui.faturamento.EmitirDeclaracaoAnualQuitacaoDebitosActionForm">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

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

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Emitir Declaração Anual de Quitação de Débitos</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td>Para emitir uma declaração, informe os dados abaixo:</td>
					<td align="right"></td>
				</tr>
			</table>

			<table width="100%" border="0">
			
				<tr>
					<td><strong>Ano Base Declaração:</strong></td>
					
					<td>
						
						<html:text maxlength="4" 
							property="anoBaseDeclaracaoInformado"							
							size="4"
							onkeyup="javascript:verificaNumeroInteiro(this);"
							/>(aaaa)
					</td>
				</tr>
				<tr>
					<td><strong>Im&oacute;vel:</strong></td>
										
					<td>
						<html:text maxlength="9" 
							property="idImovel" 
							size="9"
							onkeyup="javascript:verificaNumeroInteiro(this);"
							onkeypress="validaEnterComMensagem(event, 'exibirEmitirDeclaracaoAnualQuitacaoDebitosAction.do?reload=true','idImovel','Imóvel');"/>
									
							<a href="javascript:chamarPopup('exibirPesquisarImovelAction.do', 'imovel', null, null, 275, 480, '', document.forms[0].idImovel);">
							<img width="23" 
								height="21" 
								border="0"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Imóvel" /></a> 

							<logic:present name="imovelExistente" scope="request">
								<html:text property="inscricaoImovel" 
									size="30"
									maxlength="30" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:present> 

							<logic:present name="imovelInexistente" scope="request">
								<html:text property="inscricaoImovel" 
									size="30"
									maxlength="30" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: red" />
							</logic:present>
							
							<a href="javascript:limparImovel();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" />
							</a>
					</td>
				</tr>
				<tr>
					<td><strong>Grupo de Faturamento:</strong></td>
					<td><html:select property="idFaturamentoGrupo" style="width: 200px;" tabindex="4">
						<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoFaturamentoGrupo">
							<html:options collection="colecaoFaturamentoGrupo" labelProperty="descricao" property="id" />
						</logic:present>
					</html:select></td>
				</tr>
				<tr>
					<td><input type="button" name="Button"
						class="bottonRightCol" value="Limpar" tabindex="13"
						onClick="javascript:window.location.href='/gsan/exibirEmitirDeclaracaoAnualQuitacaoDebitosAction.do?desfazer=S'"
						style="width: 80px" />&nbsp;
						<input type="button" name="Button"
						class="bottonRightCol" value="Cancelar" tabindex="14"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
						style="width: 80px" /></td>
					<td align="right"><gcom:controleAcessoBotao name="Button"
						value="Emitir" tabindex="15"
						onclick="javascript:validarForm(document.forms[0]);"
						url="emitirDeclaracaoAnualQuitacaoDebitosAction.do" /></td>
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

