<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<%--@ page import="gcom.util.ConstantesSistema"--%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript">
	
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
	
	function baixarArquio(idAtendimento){
		var form = document.forms[0];
		
		form.action = "exibirConsultarDadosAtendimentoPopupAction.do?idAtendimentoDownload=" + idAtendimento;
		form.submit();
	}
</script>



</head>

<body leftmargin="5" topmargin="5"
	onload="window.focus();resizePageSemLink(700, 530);javascript:setarFoco('${requestScope.nomeCampo}');">

<html:form action="/exibirConsultarDadosAtendimentoPopupAction" 
	name="ConsultarDadosAtendimentoPopupActionForm"
	type="gcom.gui.atendimentopublico.Atendimento.ConsultarDadosAtendimentoPopupActionForm"
	method="post">

	<table width="100%" border="0" cellspacing="5" cellpadding="0">

		<tr>

			<td width="100%" valign="top" class="centercoltext">

				<table height="100%">
					<tr>
						<td></td>
					</tr>
				</table>
	
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<td width="11">
							<img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" />
						</td>
						<td class="parabg">Consultar Atendimento</td>
						<td width="11">
							<img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
						</td>
					</tr>
				</table>
			
				<p>&nbsp;</p>

				<table width="100%" border="0">
				<tr>
					<td align="right"></td>
				</tr>
				</table>

				<!--Inicio da Tabela Dados Gerais da Ordem de Serviço -->
            	<table width="100%" border="0">
            	

				 
				<tr>
					<html:hidden property="idAtendimento"/>
					<td><strong>Procedimento de Atendimento:</strong></td>
					<td colspan="3"> 
					<html:text property="descricaoAtendimentoProcedimento" size="45"
						maxlength="45" readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" />
				</tr>	            	

				<tr>
					<td><strong>Funcionalidade:</strong></td>
					<td colspan="3">
					<html:text property="descricaoFuncionalidade" size="45"
						maxlength="45" readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" />
				</tr>	
				
				<tr> 
					<td><strong>Tipo/Especificação:</strong></td>
					<td>
					<html:text property="descricaoTipoEspecificacao" size="45"
						maxlength="45" readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" />
					</td>
				</tr>
				
				<logic:present name="exibirImovel" scope="session" >
				<tr> 
					<td width="30%">
						<strong>Matr&iacute;cula do Im&oacute;vel:</strong>
					</td>
					<td>
						<html:text property="idImovel" maxlength="9" size="9" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
		                    <html:text property="inscricaoImovel" size="31" 
			                  maxlength="31" readonly="true"
			                  style="background-color:#EFEFEF; border:0; color: #000000" />
					</td>
				</tr>
				</logic:present>
				
				
				<logic:present name="exibirCliente" scope="session" >			       
				<tr>
					<td><strong>Cliente:<font color="#FF0000">*</font></strong></td>
					<td>
						<html:text property="idCliente" size="9" maxlength="9" readonly="true" />
						<html:text property="nomeCliente" size="30" maxlength="30"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
					</td>
				</tr>			
				</logic:present>
				
				<tr> 
					<td><strong>Usuário:</strong></td>
					<td>
					<html:text property="nomeUsuario" size="45"
						maxlength="45" readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" />
					</td>
				</tr>		
				
				<tr> 
					<td><strong>Data do Atendimento:</strong></td>
					<td>
					<html:text property="dataInicioAtendimento" size="15"
						maxlength="45" readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" />
					</td>
				</tr>	
				
				
				<tr> 
					<td><strong>Permite prosseguir o atendimento sem a documentação obrigatória:</strong></td>
					<td>
					<html:text property="indicadorDispensadoDocumentacaoObrigatoria" size="5"
						maxlength="5" readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" />
					</td>
				</tr>				
									
				
				<tr>
					<td colspan="2">
					<hr>
					</td>
				</tr>
				
				<tr>
					<td colspan="2"><strong>Relação dos documentos vinculados ao atendimento:</strong></td>
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
							<logic:iterate name="ConsultarDadosAtendimentoPopupActionForm" property="colecaoAtendimentoDocumentacao" id="atendimentoDocumentacao" indexId="index" >
								<%cont = cont + 1;
							if (cont % 2 == 0) {%>
								<tr bgcolor="#FFFFFF">
									<%} else {

							%>
								<tr bgcolor="#cbe5fe">
									<%}%>

									<td width="5%">
									<div align="center"><font color="#333333"> 
										<html:checkbox name="atendimentoDocumentacao" property="indicadorDocumentoApresentado" indexed="true" value="1" disabled="true" ></html:checkbox>
									</font></div>
									</td>
									
									<td width="40%">
										<div align="center">
											<bean:write name="atendimentoDocumentacao"	property="atendProcDocumentoPessoaTipo.atendimentoDocumentoTipo.descricao"  /> / <bean:write name="atendimentoDocumentacao"	property="atendProcDocumentoPessoaTipo.atendimentoPessoaTipo.descricao"  />										
										</div>
									</td>
									<td width="25%">
									<div align="center">
										<bean:write name="atendimentoDocumentacao"	property="identificadorDocumentoApresentado"  />							
									</div>
									</td>									
									<td width="10%">
									<div align="center">
									
									  <logic:equal name="atendimentoDocumentacao" property="atendProcDocumentoPessoaTipo.indicadorDocumentoObrigatorio" value="1" >     
									     Sim
									  </logic:equal>     
 
									  <logic:notEqual name="atendimentoDocumentacao" property="atendProcDocumentoPessoaTipo.indicadorDocumentoObrigatorio" value="1" >     
									     Não
									  </logic:notEqual>   		   
									
							    	</div>
							    	</td>
							    	
									<td width="10%">
									<div align="center"><font color="#333333"> 
										
									     <input type="button" name="downloadArquivo" id="downloadArquivo" value="Download" onclick="baixarArquio('<bean:write name="atendimentoDocumentacao"	property="id"  />')">
																				
										</font></div>
									</td>							    	
								</tr>
							</logic:iterate>
					</table>
					</td>
				</tr>	

				<tr>
					<td>
						<table width="100%">
			              	<tr>
			            		<td>
		              				<div align="left">
	                  					<input name="ButtonFechar" 
	                  						type="button" 
	                  						class="bottonRightCol" 
	                  						value="Fechar" 
	                  						onclick="window.close();" style="width: 70px;">
		              				</div>
			              		</td>
				          	</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<logic:present name="naoExisteArquivo" scope="request" >
	<script type="text/javascript">
	<!--
    	alert("Não existe arquivo vinculado a este Documento.");
    -->
	</script>
</logic:present>
</html:form>
</body>
</html:html>