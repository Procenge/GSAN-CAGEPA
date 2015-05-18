<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@page import="gcom.gui.GcomAction"%>
<%@page import="gcom.gui.cadastro.documentoeletronico.InserirDocumentoEletronicoActionForm"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<SCRIPT LANGUAGE="JavaScript">
	
	function validarForm(form)  {
		var flagValidado = true;
		var msgAlert = "";
		
		if ((form.idImovel.value == null || form.idImovel.value == "")  && 	(form.idCliente.value == null || form.idCliente.value == "")) {
			flagValidado = false;
			msgAlert = "Informe um Imovel ou Cliente.\n";
		}		
	
		if (form.idTipoDocumento.value == null || form.idTipoDocumento.value == "" || form.idTipoDocumento.value == "-1") {
			flagValidado = false;
			msgAlert = msgAlert + "Informe um Tipo de Documento.\n";
		}	
		
		if (form.conteudoArquivoUpload.value == null || form.conteudoArquivoUpload.value == "") {
			flagValidado = false;
			msgAlert = msgAlert + "Informe o Documento.\n";
		}				
		
		if (flagValidado) {
			form.action = 'inserirDocumentoEletronicoAction.do';
			form.submit();
		} else {
			alert(msgAlert);
		}
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
		      form.action = 'exibirInserirDocumentoEletronicoAction.do?carregarImovel=S';
		      form.submit();
	    }
	    
	      
        if (tipoConsulta == 'cliente') {
	  		form.idCliente.value = codigoRegistro;
	  		form.nomeCliente.value = descricaoRegistro;
	        form.action = 'exibirInserirDocumentoEletronicoAction.do?carregarCliente=S'
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

</SCRIPT>		

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="InserirDocumentoEletronicoActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>



</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');">
<html:form action="/inserirDocumentoEletronicoAction.do" method="post"
	name="InserirDocumentoEletronicoActionForm"
	type="gcom.gui.cadastro.documentoEletronico.InserirDocumentoEletronicoActionForm"
	enctype="multipart/form-data">
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
					<td class="parabg">Inserir Documento Eletrônico</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>

			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para adicionar o Documento Eletrônico, informe os dados abaixo:</td>
				</tr>
				
			
				<tr> 
					<td width="30%">
						<strong>Matr&iacute;cula do Im&oacute;vel:<font color="#FF0000">*</font></strong>
					</td>
					<td>
						<html:text property="idImovel" maxlength="9" size="9"
							onkeypress="validaEnterComMensagem(event, 'exibirInserirDocumentoEletronicoAction.do?carregarImovel=S', 'idImovel','Matrícula do Imóvel');" 
							onkeyup="verificaNumeroInteiro(this);limparInscricaoImovel();" />
						<a href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
							<img width="23" height="21" src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" /></a>	
							<logic:present name="idImovelNaoEncontrado">
			                  <logic:equal name="idImovelNaoEncontrado" value="exception">
				                    <html:text property="inscricaoImovel" size="30"
					                   maxlength="30" readonly="true"
					                   style="background-color:#EFEFEF; border:0; color: #ff0000" />
			                  </logic:equal>
			                  <logic:notEqual name="idImovelNaoEncontrado" value="exception">
				                   <html:text property="inscricaoImovel" size="30"
					                maxlength="30" readonly="true"
					                style="background-color:#EFEFEF; border:0; color: #000000" />
			                  </logic:notEqual>
		                   </logic:present> 
		                   <logic:notPresent name="idImovelNaoEncontrado">
			                  <logic:empty name="InserirDocumentoEletronicoActionForm" property="inscricaoImovel">
				                    <html:text property="inscricaoImovel" size="30" value = ""
					                  maxlength="30" readonly="true"
					                  style="background-color:#EFEFEF; border:0; color: #ff0000" />
			                  </logic:empty>
			               <logic:notEmpty name="InserirDocumentoEletronicoActionForm" property="inscricaoImovel">
				                   <html:text property="inscricaoImovel" size="30"
					                  maxlength="30" readonly="true"
					                  style="background-color:#EFEFEF; border:0; color: #000000" />
			               </logic:notEmpty>
			               </logic:notPresent>
							
						<img border="0" src="<bean:message key='caminho.imagens'/>limparcampo.gif" onclick="limparImovel();" style="cursor: hand;" />
					</td>
				</tr>
			       
	       
				<tr>
					<td><strong>Cliente:<font color="#FF0000">*</font></strong></td>
					<td>
						<html:text property="idCliente" size="9" maxlength="9"
							onkeypress="validaEnterComMensagem(event, 'exibirInserirDocumentoEletronicoAction.do?carregarCliente=S', 'idCliente','Cliente');"
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
					<td><strong>Tipo de Documento:<font color="#FF0000">*</font></strong></td>
					<td>
						<html:select property="idTipoDocumento">
							<html:option value="-1">&nbsp;</html:option>
							<html:options collection="colecaoAtendimentoDocumentoTipo" labelProperty="descricao" property="id" />
						</html:select>
					</td>
				</tr>
				
				<tr>
					<td align="left"><strong> Documento: <font color="#FF0000">*</font></strong></td>
					<td colspan="3" align="left"><input type="file" style=""
						name="conteudoArquivoUpload" size="50" /></td>
					<td></td>
				</tr>
																	
				<tr>
					<td colspan="2">
					<hr>
					</td>
				</tr>
			
				<tr>
					<td height="19"><strong> <font color="#FF0000"></font></strong></td>
					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>
				<tr>
					<td><input name="Button" type="button" class="bottonRightCol"
						value="Desfazer" align="left" onclick="window.location.href='<html:rewrite page="/exibirInserirDocumentoEletronicoAction.do?desfazer=S"/>'" > <input name="Button" type="button"
						class="bottonRightCol" value="Cancelar" align="left"
						onclick="window.location.href='/gsan/telaPrincipal.do'"></td>
					<td align="right"><input name="Button" type="button"
						class="bottonRightCol" value="Inserir" align="right"
						onClick="javascript:validarForm(document.forms[0]);"></td>
				</tr>
			</table>
			<p>&nbsp;</p>
		</tr>
	</table>
	<tr>
		<td colspan="3"><%@ include file="/jsp/util/rodape.jsp"%>
	</tr>
</html:form>
</body>
</html:html>

