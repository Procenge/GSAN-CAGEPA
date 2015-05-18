<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@page import="gcom.gui.GcomAction"%>
<%@page import="gcom.gui.cadastro.atendimento.InserirAtendimentoActionForm"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<SCRIPT LANGUAGE="JavaScript">
	function validarForm(form){
		
		if (form.indicadorClienteImovel.value == 'I' || form.indicadorClienteImovel.value == 'C') {
			var flagValidado = true; 
			
			if (form.indicadorClienteImovel.value == 'I') {
				if (form.idImovel.value == null || form.idImovel.value == "") {
					flagValidado = false;
					alert("Não foi informado o Imóvel do Atendimento.")
				}
			} else if (form.indicadorClienteImovel.value == 'C') {
				if (form.idCliente.value == null || form.idCliente.value == "") {
					flagValidado = false;
					alert("Não foi informado o Cliente do Atendimento.")
				}				
			}
			
			if (flagValidado) { 
				form.action = 'inserirAtendimentoAction.do';
	    		form.submit();
			}
		} else {
			alert("Informe se o Atendimento esta relacionado a um Imóvel ou Cliente.")
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
		      form.action = 'exibirInserirAtendimentoAction.do?carregarImovel=S';
		      form.submit();
	    }
	    
	      
        if (tipoConsulta == 'cliente') {
	  		form.idCliente.value = codigoRegistro;
	  		form.nomeCliente.value = descricaoRegistro;
	        form.action = 'exibirInserirAtendimentoAction.do?carregarCliente=S'
	  	    form.submit();
	  	}	    
	}
	
     function modificarEspecificacao() {
		var form = document.forms[0];
		
        form.action = 'exibirInserirAtendimentoAction.do?carregarEspecificacaoAtendimento=S'
  	    form.submit();    	 
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
	 
	 function adicionarDocumentoTipoPessoaImagem(index){
		 	var form = document.forms[0];
		 	
	        form.action = 'exibirInserirAtendimentoAction.do?carregarImagem=' + index;
	      	form.submit();   
	 }	 
	 
	 function visualizarNormaProcedimental(idNorma){
		 	var form = document.forms[0];

		 	form.action = "exibirInserirAtendimentoAction.do?visualizarNorma=" + idNorma;
		 	form.submit();
	 }		 
	 
</SCRIPT>		

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="InserirAtendimentoActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>



</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');">
<html:form action="/inserirAtendimentoAction.do" method="post"
	name="InserirAtendimentoActionForm"
	type="gcom.gui.cadastro.atendimento.InserirAtendimentoActionForm"
	enctype="multipart/form-data">
	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<html:hidden property="indicadorClienteImovel"/>
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
					<td class="parabg">Inserir Atendimento</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>

			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para adicionar o Atendimento, informe os dados abaixo:</td>
				</tr>
				
				<tr>
					<td><strong>Funcionalidade:</strong></td>
					<td colspan="3"><html:hidden property="idFuncionalidade"/> 
					<html:text property="descricaoFuncionalidade" size="45"
						maxlength="45" readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" />
				</tr>					
				
				<tr> 
					<td><strong>Tipo/Especificação:</strong></td>
					<td>
						<html:select property="idAtendimentoProcedimento" onchange="javascript:modificarEspecificacao();">
							<html:options collection="colecaoAtendimentoProcedimento" labelProperty="descricaoTipoAndEspecificacao" property="id" />
						</html:select>
					</td>
				</tr>				
				
				<logic:present name="exibirImovel" scope="session" >
				<tr> 
					<td width="30%">
						<strong>Matr&iacute;cula do Im&oacute;vel:<font color="#FF0000">*</font></strong>
					</td>
					<td>
						<html:text property="idImovel" maxlength="9" size="9"
							onkeypress="validaEnterComMensagem(event, 'exibirInserirAtendimentoAction.do?carregarImovel=S', 'idImovel','Matrícula do Imóvel');" 
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
			                  <logic:empty name="InserirAtendimentoActionForm" property="inscricaoImovel">
				                    <html:text property="inscricaoImovel" size="30" value = ""
					                  maxlength="30" readonly="true"
					                  style="background-color:#EFEFEF; border:0; color: #ff0000" />
			                  </logic:empty>
			               <logic:notEmpty name="InserirAtendimentoActionForm" property="inscricaoImovel">
				                   <html:text property="inscricaoImovel" size="30"
					                  maxlength="30" readonly="true"
					                  style="background-color:#EFEFEF; border:0; color: #000000" />
			               </logic:notEmpty>
			               </logic:notPresent>
							
						<img border="0" src="<bean:message key='caminho.imagens'/>limparcampo.gif" onclick="limparImovel();" style="cursor: hand;" />
					</td>
				</tr>
				</logic:present>
			       
			       
				<logic:present name="exibirCliente" scope="session" >			       
				<tr>
					<td><strong>Cliente:<font color="#FF0000">*</font></strong></td>
					<td>
						<html:text property="idCliente" size="9" maxlength="9"
							onkeypress="validaEnterComMensagem(event, 'exibirInserirAtendimentoAction.do?carregarCliente=S', 'idCliente','Cliente');"
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
				</logic:present>


				
				
				<tr>
					<td colspan="2">
					<hr>
					</td>
				</tr>
				
				<tr>
					<td colspan="2"><strong>Relação dos documentos previstos para o procedimento:</strong></td>
					<td align="right">
					</td>
				</tr>				
				
				<tr>
					<td colspan="2">
					<table width="100%" bgcolor="#99CCFF">


						<tr bordercolor="#000000">
							<td align="center" width="5%" bgcolor="#90c7fc"><strong></strong></td>
							<td width="40%" bgcolor="#90c7fc"><strong>Documento</strong></td>
							<td width="25%" bgcolor="#90c7fc"><strong>Dados do Documento</strong></td>
							<td width="10%" bgcolor="#90c7fc" align="center"><strong>Obrigatório</strong></td>
							<td width="10%" bgcolor="#90c7fc" align="center" ><strong></strong></td>
						</tr>
						<%--Esquema de paginação--%>

							<%int cont = 0;%>
							<logic:iterate name="InserirAtendimentoActionForm" property="colecaoAtendimentoDocumentacao" id="atendimentoDocumentacao" indexId="index" >
								<%cont = cont + 1;
							if (cont % 2 == 0) {%>
								<tr bgcolor="#FFFFFF">
									<%} else {

							%>
								<tr bgcolor="#cbe5fe">
									<%}%>

									<td width="5%">
									<div align="center"><font color="#333333"> 
										<html:checkbox name="atendimentoDocumentacao" property="indicadorDocumentoApresentado" indexed="true" value="1"></html:checkbox>
										<html:hidden name="atendimentoDocumentacao" property="indicadorDocumentoApresentado" indexed="true" value="2"/>
									</font></div>
									</td>
									
									<td width="40%">
										<div align="center">
											<bean:write name="atendimentoDocumentacao"	property="descricaoEspecificacao"  />											
										</div>
									</td>
									<td width="25%">
									<div align="center">
									
									<html:text name="atendimentoDocumentacao" property="identificadorDocumentoApresentado" indexed="true"/> 
									
									</div>
									</td>									
									<td width="10%">
									<div align="center">
									
									  <logic:equal name="atendimentoDocumentacao" property="indicadorDocumentoObrigatorio" value="1" >     
									     Sim
									  </logic:equal>     
 
									  <logic:notEqual name="atendimentoDocumentacao" property="indicadorDocumentoObrigatorio" value="1" >     
									     Não
									  </logic:notEqual>     
									
							    	</div>
							    	</td>
							    	
									<td width="10%">
									<div align="center"><font color="#333333"> 
										<html:file property="conteudoArquivoUpload[${index}]" style="width: 115;" onchange="adicionarDocumentoTipoPessoaImagem(${index})" />										
										</font></div>
									</td>							    	
								</tr>
							</logic:iterate>
					</table>
					</td>
				</tr>				
				
				<tr>
					<td colspan="2">
					<hr>
					</td>
				</tr>		
				
				<tr>
					<td colspan="2"><strong>Relação das normas procedimentais para o procedimento:</strong></td>
					<td align="right">
					</td>
				</tr>							
				
				<tr>
					<td colspan="2">
					<table width="100%" bgcolor="#99CCFF">
						<tr bordercolor="#000000">
							<td width="100%" bgcolor="#90c7fc"><strong>Norma Procedimental</strong></td>
						</tr>
						<%--Esquema de paginação--%>

						<logic:present name="colecaoAtendProcNormaProcedimental" scope="session">
							<%cont = 0;%>
							<logic:iterate id="atendProcNormaProcedimental"
								name="colecaoAtendProcNormaProcedimental">
								<%cont = cont + 1;
							if (cont % 2 == 0) {%>
								<tr bgcolor="#FFFFFF">
									<%} else {

							%>
								<tr bgcolor="#cbe5fe">
									<%}%>

									<td>
										<div align="center">
											<a href="javascript:visualizarNormaProcedimental('<bean:write name="atendProcNormaProcedimental"
																					property="normaProcedimental.id"/>');">											
												<bean:write name="atendProcNormaProcedimental"
														property="normaProcedimental.descricao" />
											</a>
										</div>
										
									</td>
								</tr>
							</logic:iterate>

						</logic:present>
					</table>
					</td>
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
						value="Desfazer" align="left" onclick="window.location.href='<html:rewrite page="/exibirInserirAtendimentoAction.do?desfazer=S"/>'" > <input name="Button" type="button"
						class="bottonRightCol" value="Cancelar" align="left"
						onclick="window.location.href='/gsan/telaPrincipal.do'"></td>
					<td align="right"><input name="Button" type="button"
						class="bottonRightCol" value="Avançar" align="right"
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

