<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="gcom.atendimentopublico.bean.ImovelComentarioHelper"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js" ></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript formName="InformarImovelComentarioActionForm"/>

<script language="JavaScript">
function validarFormulario(){
	var form = document.forms[0];
	
	if (validateInformarImovelComentarioActionForm(form)){
		
		if (form.comentario.value == ''){
			
			alert("Informe Comentário");
			return;
		}else{
			
			form.action = '/gsan/informarImovelComentarioAction.do';
	  		form.submit();
		}
	}
}

function limpaCampos(){
	var form = document.forms[0];
	
	form.idImovel.value = "";
	form.inscricaoImovel.value = "";
	form.comentario.value = "";
	
	form.action = '/gsan/exibirInformarImovelComentarioAction.do?limpar=1';
  	form.submit();
}

function extendeTabela(tabela,display){
	var form = document.forms[0];

	if(display){
		eval('layerHide'+tabela).style.display = 'none';
		eval('layerShow'+tabela).style.display = 'block';
	}else{
		eval('layerHide'+tabela).style.display = 'block';
		eval('layerShow'+tabela).style.display = 'none';
	}
}

function limparImovel(){
	var form = document.forms[0];
	     	
	form.idImovel.value = "";
	form.inscricaoImovel.value = "";
	form.action = '/gsan/exibirInformarImovelComentarioAction.do?limpar=1';
  	form.submit();
}

function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg){
	
	var form = document.forms[0];
	
	if (objeto == null || codigoObjeto == null){
 		
		if(tipo == "" ){
  			
			abrirPopup(url,altura, largura);
 		}else{
  			
 			abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
 		}
	}else{
			
		if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
			
			alert(msg);
		}else{
			
			abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto, altura, largura);
		}
	}
}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

	var form = document.forms[0];
	
	if (tipoConsulta == 'imovel') {

	     form.idImovel.value = codigoRegistro;
	     form.inscricaoImovel.value = descricaoRegistro;
	     form.inscricaoImovel.style.color = "#000000";
	     
	     form.action = '/gsan/exibirInformarImovelComentarioAction.do?objetoConsulta=1';
		 form.submit();
    }
}
	
</script>

</head>

<body leftmargin="5" topmargin="5">


<html:form action="/exibirInformarImovelComentarioAction" 
	name="InformarImovelComentarioActionForm"
	type="gcom.gui.atendimentopublico.InformarImovelComentarioActionForm" 
	method="post">


<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<table width="770" border="0" cellspacing="4" cellpadding="0">
	<tr>
		<td width="149" valign="top" class="leftcoltext">
		    <div align="center">
		
				<%@ include file="/jsp/util/informacoes_usuario.jsp"%>
		
				<%@ include file="/jsp/util/mensagens.jsp"%>
		    </div>
	    </td>
	
		<td width="600" valign="top" class="centercoltext">
			<table height="100%">
		        <tr>
		          <td></td>
		        </tr>
		    </table>
	
		    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		      <tr>
		        <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
		        <td class="parabg">Comentários do Imóvel</td>
		        <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
		      </tr>
		    </table>
	      	<p>&nbsp;</p>
	
		    <table width="100%" border="0">
			     <tr>
			    	 <td colspan="2">Nesta tela, o usuário pode informar ou consultar comentários do imóvel:</td>
			     </tr>
			
			  	 <tr>
			     	<td colspan="2">
						<table width="100%" align="center" bgcolor="#90c7fc" border="0">
							<tr bgcolor="#cbe5fe">
								<td width="100%" align="center">
									<table width="100%" border="0">
								 		<tr>
											<td><strong>Im&oacute;vel:</strong>
												<font color="#FF0000">*</font>
											</td>
																
											<td>
						           		
							  					<html:text maxlength="9" 
													property="idImovel" 
													size="9"
													onkeyup="javascript:verificaNumeroInteiro(this);"
													onkeypress="validaEnterComMensagem(event, 'exibirInformarImovelComentarioAction.do?objetoConsulta=1','idImovel','Imóvel');"
													/>	
												
												<a href="javascript:chamarPopup('exibirPesquisarImovelAction.do', 'imovel', null, null, 275, 480, '', document.forms[0].idImovel);">
												<img width="23" 
													height="21" 
													border="0"
													src="<bean:message key="caminho.imagens"/>pesquisa.gif"
													title="Pesquisar Imóvel" /></a> 
												
												<logic:present name="imovelEncontrado" scope="request">
													<html:text property="inscricaoImovel" 
														size="30"
														maxlength="30" 
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: #000000" />
												</logic:present> 
						
												<logic:notPresent name="imovelEncontrado" scope="request">
													<html:text property="inscricaoImovel" 
														size="30"
														maxlength="30" 
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: red" />
												</logic:notPresent>
												
												<a href="javascript:limparImovel();"> 
												<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
													border="0" 
													title="Apagar" />
												</a>
											</td>
										</tr>
										<tr>
											<td>
												<strong>Comentário:<font color="#FF0000">*</font></strong><br>
											</td>										
											<td>
												<html:textarea property="comentario" style="width: 480" rows="10" onkeyup=" validarTamanhoMaximoTextArea(this,1000);"/>
											</td>
										</tr>
										<tr>
											<td colspan="4">
												<div id="layerHideComentarios" style="display:block">
													<table width="100%" border="0" bgcolor="#99CCFF" style="table-layout: auto;">
														<tr bgcolor="#99CCFF">
															<td height="18" colspan="4" align="center">
																<span class="style2"> 
																	<a href="javascript:extendeTabela('Comentarios',true);"> <b>Comentários do Imóvel</b></a>
																</span>
															</td>
														</tr>
													</table>
												</div>
												<div id="layerShowComentarios" style="display:none">
													<table width="100%" border="0" bgcolor="#99CCFF" style="table-layout: auto;">
														<tr bgcolor="#99CCFF">
															<td height="18" colspan="4" align="center">
																<span class="style2"> 
																	<a href="javascript:extendeTabela('Comentarios',false);"> 
																		<b>Comentários do Imóvel</b>
																	</a>
																</span>
															</td>
														</tr>
														<tr bordercolor="#000000">
															<td width="10%" bgcolor="#90c7fc"  style="max-width: 100">
																<div align="left"><strong>Comentário</strong></div>
															</td>
								
															<td width="12%" bgcolor="#90c7fc">
																<div align="center"><strong>Sequencial</strong></div>
															</td>
															
															<td width="12%" bgcolor="#90c7fc">
																<div align="center"><strong>Data Inclusão</strong></div>
															</td>
															
															<td width="26%" bgcolor="#90c7fc">
																<div align="center"><strong>Usuário</strong></div>
															</td>
															
														</tr>
														
														<logic:present name="colecaoImovelComentarioHelper">
															<%int cont = 0;%>
															<logic:iterate name="colecaoImovelComentarioHelper" id="imovelComentarioHelper" type="ImovelComentarioHelper">
																<%cont = cont + 1;
																if (cont % 2 == 0) {%>
																<tr bgcolor="#cbe5fe">
																	<%} else {
								
																%>
																<tr bgcolor="#FFFFFF">
																<%}%>
																	<td width="50%" align="left" style="word-wrap: break-word;overflow-wrap: break-word;max-width: 280;">
																																	
																			<logic:equal name="imovelComentarioHelper" property="usuarioPossuiPermissaoAlteracao" value="1">
																				<html:link href="/gsan/exibirAtualizarImovelComentarioAction.do" paramId="idRegistroAtualizacao" 
																					paramProperty="id" paramName="imovelComentarioHelper" title="Atualizar Comentário do Imóvel">
																						<bean:write name="imovelComentarioHelper" property="descricao" />
																				</html:link>
																			</logic:equal>
																			<logic:equal name="imovelComentarioHelper" property="usuarioPossuiPermissaoAlteracao" value="2">
																					<bean:write name="imovelComentarioHelper" property="descricao" />
																			</logic:equal>
																																				
																	</td>
																	<td width="12%" align="center">${imovelComentarioHelper.sequencialInclusao}</td>
																	<td width="12%" align="center">${imovelComentarioHelper.dataInclusao}</td>
																	<td width="24%" align="center">${imovelComentarioHelper.usuario}</td>		
																</tr>
															</logic:iterate>
														</logic:present>
													</table>
												</div>
											</td>
										</tr>
				                	</table>
								</td>
					    	</tr>
						</table>		
				 	</td>
				 </tr>             
			     <tr>
			    	<td colspan="2" height="10"></td>
			     </tr>
			     <tr>
					 <td colspan="2">
						<table width="100%">
						    <tr>
								<td colspan="2"><div align="left"><strong><font color="#FF0000">*</font></strong>Campos obrigat&oacute;rios</div></td>
							</tr>
							<tr>
			    	      		<td colspan="2" height="10"></td>
						    </tr>
							<tr>
								<td>				
									<input name="Submit22"
									 class="bottonRightCol" value="Limpar" type="button"
									 onclick="javascript:limpaCampos();">	
									<input name="Submit23" class="bottonRightCol" value="Cancelar"
											type="button"
											onclick="window.location.href='/gsan/telaPrincipal.do'"> 
								</td>
								<td align="right">	
										<input name="Submit24"
											 class="bottonRightCol" value="Inserir Comentário" type="button"
											 onclick="javascript:validarFormulario();">	
								</td>		  
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

