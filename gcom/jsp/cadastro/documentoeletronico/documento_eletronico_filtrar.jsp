<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="FiltrarDocumentoEletronicoActionForm" />

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">

	function validarForm(form){

			submeterFormPadrao(form);

	}

	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){
		if(objetoRelacionado.disabled != true){
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
	}
	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	    var form = document.forms[0];
		
	    if (tipoConsulta == 'imovel') {
	    	  form.idImovel.value = codigoRegistro;
		      form.inscricaoImovel.value = descricaoRegistro;
		      form.action = 'exibirFiltrarDocumentoEletronicoAction.do?carregarImovel=S';
		      form.submit();
	    }
	    
	      
        if (tipoConsulta == 'cliente') {
	  		form.idCliente.value = codigoRegistro;
	  		form.nomeCliente.value = descricaoRegistro;
	        form.action = 'exibirFiltrarDocumentoEletronicoAction.do?carregarCliente=S'
	  	    form.submit();
	  	}	    
	}
	
	 function limparInscricaoImovel(){
		 	var form = document.forms[0];
		 	form.inscricaoImovel.value = ""; 		
	 }
	 
	 function limparImovel(){
		 	var form = document.forms[0];
		 	
		 	form.idImovel.value = ""; 
		 	form.inscricaoImovel.value = ""; 			
	 }
	 
	 function limparNomeCliente(){
		 	var form = document.forms[0];
		 	form.nomeCliente.value = ""; 		
	 }
	 
	 function limparCliente(){
		 	var form = document.forms[0];

		 	form.idCliente.value = ""; 
		 	form.nomeCliente.value = ""; 		 	
	 }

</script>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('${requestScope.nomeCampo}');">

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
			<td width="600" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>

			<!--Início Tabela Reference a Páginação da Tela de Processo-->
			<table>
				<tr>
					<td></td>

				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Filtrar Documento Eletrônico</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>

			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			
			<html:form action="filtrarDocumentoEletronicoAction" method="post"
				name="FiltrarDocumentoEletronicoActionForm"
				type="gcom.gui.cadastro.documentoeletronico.FiltrarDocumentoEletronicoActionForm"
				onsubmit="return validateFiltrarDocumentoEletronicoActionForm(this);">
				
			<table width="100%" border="0" >
				<tr>
					<td colspan="4">Para manter a(s) Documento(s) Eletrônico(s), informe os
					dados abaixo:</td>
					<td width="80" align="right"><html:checkbox property="checkConsultar" value="1"/><strong>Consultar</strong></td>
     			</tr>
				
				<tr> 
					<td width="30%" >
						<strong>Matr&iacute;cula do Im&oacute;vel:</strong>
					</td>
					<td colspan="4">
						<html:text property="idImovel" maxlength="9" size="9"
							onkeypress="validaEnterComMensagem(event, 'exibirFiltrarDocumentoEletronicoAction.do?carregarImovel=S', 'idImovel','Matrícula do Imóvel');" 
							onkeyup="verificaNumeroInteiro(this);limparInscricaoImovel();" />
						<a href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
							<img width="23" height="21" src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" /></a>	
							<logic:present name="idImovelNaoEncontrado">
			                  <logic:equal name="idImovelNaoEncontrado" value="exception">
				                    <html:text property="inscricaoImovel" size="28"
					                   maxlength="28" readonly="true"
					                   style="background-color:#EFEFEF; border:0; color: #ff0000" />
			                  </logic:equal>
			                  <logic:notEqual name="idImovelNaoEncontrado" value="exception">
				                   <html:text property="inscricaoImovel" size="28"
					                maxlength="28" readonly="true"
					                style="background-color:#EFEFEF; border:0; color: #000000" />
			                  </logic:notEqual>
		                   </logic:present> 
		                   <logic:notPresent name="idImovelNaoEncontrado">
			                  <logic:empty name="FiltrarDocumentoEletronicoActionForm" property="inscricaoImovel">
				                    <html:text property="inscricaoImovel" size="30" value = ""
					                  maxlength="30" readonly="true"
					                  style="background-color:#EFEFEF; border:0; color: #ff0000" />
			                  </logic:empty>
			               <logic:notEmpty name="FiltrarDocumentoEletronicoActionForm" property="inscricaoImovel">
				                   <html:text property="inscricaoImovel" size="30"
					                  maxlength="30" readonly="true"
					                  style="background-color:#EFEFEF; border:0; color: #000000" />
			               </logic:notEmpty>
			               </logic:notPresent>
							
						<img border="0" src="<bean:message key='caminho.imagens'/>limparcampo.gif" onclick="limparImovel();" style="cursor: hand;" />
					</td>
				</tr>
			       
	       
				<tr>
					<td ><strong>Cliente:</strong></td>
					<td colspan="4">
						<html:text property="idCliente" size="9" maxlength="9"
							onkeypress="validaEnterComMensagem(event, 'exibirFiltrarDocumentoEletronicoAction.do?carregarCliente=S', 'idCliente','Cliente');"
							onkeyup="verificaNumeroInteiro(this);limparNomeCliente();" />
						
						<a href="javascript:abrirPopup('exibirPesquisarClienteAction.do', 400, 800);" >
							<img width="23" height="21"	src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" />
						</a>
						
						<logic:present name="clienteInexistente" scope="request">
							<html:text property="nomeCliente" size="30" maxlength="30"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:present> 
						<logic:notPresent name="clienteInexistente"
							scope="request">
							<html:text property="nomeCliente" size="30" maxlength="30"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notPresent>
						
						<a href="javascript:limparCliente();">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" />
						</a>
					</td>
				</tr>			

				<tr> 
					<td><strong>Tipo de Documento:</strong></td>
					<td>
						<html:select property="idTipoDocumento">
							<html:option value="-1">&nbsp;</html:option>
							<html:options collection="colecaoAtendimentoDocumentoTipo" labelProperty="descricao" property="id" />
						</html:select>
					</td>
				</tr>              	

				<tr>

				</tr>

				<tr>
					<td colspan="4"><input name="button" type="button" class="bottonRightCol"
						value="Limpar"
						onclick="window.location.href='<html:rewrite page="/exibirFiltrarDocumentoEletronicoAction.do?desfazer=S"/>'">&nbsp;
					</td>

					<td align="right"><input name="botaoFiltrar" type="button"
						class="bottonRightCol" align="right" value="Filtrar"
						onclick="validarForm(document.forms[0]);" tabindex="19"
						style="width: 70px;"></td>
				</tr>				
				
				
			</table>
			</html:form>
			<p>&nbsp;</p>
		</tr>
	</table>
	<tr>
		<td colspan="3"><%@ include file="/jsp/util/rodape.jsp"%>
	</tr>
</body>
</html:html>

