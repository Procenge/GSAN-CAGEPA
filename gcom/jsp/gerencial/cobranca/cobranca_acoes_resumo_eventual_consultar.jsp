<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ page import="gcom.cobranca.CobrancaAcao" %>
<%@ page import="gcom.cobranca.CobrancaDebitoSituacao" %>
<%@ page import="gcom.cobranca.CobrancaAcaoSituacao" %>
<%@ page import="java.util.Locale"%>

<% session.setAttribute("currentLocale", new Locale("pt", "BR")); %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<%@ page import="gcom.gerencial.bean.CobrancaAcaoDebitoHelper,gcom.gerencial.bean.CobrancaAcaoSituacaoHelper"%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

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
	
</script>
</head>

<body leftmargin="5" topmargin="5">

<html:form action="/consultarResumoAcaoCobrancaEventualWizardAction" 
	name="ResumoAcaoCobrancaActionForm"
	type="gcom.gui.gerencial.cobranca.ResumoAcaoCobrancaActionForm"
	method="post">
	
		<jsp:include
		page="/jsp/util/wizard/navegacao_abas_wizard_consulta.jsp?numeroPagina=2" />
	
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

			<td width="615" valign="top" class="centercoltext">

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
						<td class="parabg">Consultar Resumo das A��es de Cobran�a Eventuais</td>
						<td width="11">
							<img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
						</td>
					</tr>
				</table>
			
				<p>&nbsp;</p>

				<table width="100%">
				   <tr>
					  <td align="center"><strong>A��es de Cobran�a</strong></td>
					  
					
					  
					</tr>	
				  <logic:iterate name="colecaoResumoAcaoCobranca"
					id="resumoAcaoCobranca">
					<tr>
					  <td>
					   <div id='layerHide<bean:write name="resumoAcaoCobranca" property="id"/>' style="display:block">
           					<table width="100%" border="0" bgcolor="#79bbfd">
	    						<tr bgcolor="#79bbfd">
                     				<td>             
                     					<a href="javascript:extendeTabela('<bean:write name="resumoAcaoCobranca" property="id"/>',true);"/>
                     						<strong><bean:write name="resumoAcaoCobranca"
											property="descricao" /></strong> - Per�odo de Execu��o da A��o: <strong><bean:write name="resumoAcaoCobranca"
											property="realizacaoEmitir" /></strong> a <strong><bean:write name="resumoAcaoCobranca"
											property="realizacaoEncerrar" /> - <bean:write name="resumoAcaoCobranca"
											property="indicadorDefinitivo" /> </strong>
                     					</a>
                     					
                     					
                     					
                     		   <logic:notEmpty name="resumoAcaoCobranca" property="indicadorEntregaDocumento">
                     		    	<logic:equal name= "resumoAcaoCobranca" property="indicadorEntregaDocumento" value="1">
                     		       
                     				
                     		
                     					 <a href="javascript:abrirPopup('exibirConsultarResumoAcaoCobrancaEventualMotNaoEntregaPopupAction.do?idCobrancaAcao=<bean:write name="resumoAcaoCobranca" property="id"/>
								  							&quantidadeTotal=<bean:write name="resumoAcaoCobranca" property="somatorioQuantidadesDocumentos"/>&valorTotal=<bean:write name="resumoAcaoCobranca" property="somatorioValoresDocumentos"/>', 400, 600);">
								  							 <img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>treeLeafIcon.gif" title="Pesquisar Motivo Doc. N�o Entrega" />
										 </a> 
                     		   		 
                     		   	 </logic:equal>
                     		    </logic:notEmpty>
                     					
                     				</td>
                     				
                     		  
                     		  
                     		   
                     		
                     				
                    			</tr>
                   			</table>
           				</div>
           				<div id='layerShow<bean:write name="resumoAcaoCobranca" property="id"/>' style="display:none">
					    <table width="100%" bgcolor="#79bbfd">						
						  <tr bgcolor="#79bbfd">
						    <td bgcolor="#79bbfd">
						    <a href="javascript:extendeTabela('<bean:write name="resumoAcaoCobranca" property="id"/>',false);"/>
							<strong><bean:write name="resumoAcaoCobranca"
								property="descricao" /></strong> - Per�odo de Execu��o da A��o: <strong><bean:write name="resumoAcaoCobranca"
								property="realizacaoEmitir" /></strong> a <strong><bean:write name="resumoAcaoCobranca"
								property="realizacaoEncerrar" /> - <bean:write name="resumoAcaoCobranca"
											property="indicadorDefinitivo" /> </strong>
							</a>
							
							
							  <logic:notEmpty name="resumoAcaoCobranca" property="indicadorEntregaDocumento">
                     		    	<logic:equal name= "resumoAcaoCobranca" property="indicadorEntregaDocumento" value="1">
                     		       
                     				
                     		
                     					 <a href="javascript:abrirPopup('exibirConsultarResumoAcaoCobrancaEventualMotNaoEntregaPopupAction.do?idCobrancaAcao=<bean:write name="resumoAcaoCobranca" property="id"/>
								  							&quantidadeTotal=<bean:write name="resumoAcaoCobranca" property="somatorioQuantidadesDocumentos"/>&valorTotal=<bean:write name="resumoAcaoCobranca" property="somatorioValoresDocumentos"/>', 400, 600);">
								  							 <img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>treeLeafIcon.gif" title="Pesquisar Motivo Doc. N�o Entrega" />
										 </a> 
                     		   		
                     		   	 </logic:equal>
                     		    </logic:notEmpty>
							
							
							
							</td>
							
							
							
						  </tr>
					<tr>
					  <td>
					    <table width="100%" bgcolor="#90c7fc">
						  <logic:notEmpty name="resumoAcaoCobranca"
							property="colecaoCobrancaAcaoSituacao">
							
							 <tr bgcolor="#cbe5fe">
						      <td bgcolor="#cbe5fe" rowspan="2" width="32%">
						        <a href="javascript:abrirPopup('exibirConsultarResumoAcaoCobrancaEventualPopupAction.do?idCobrancaAcao=<bean:write name="resumoAcaoCobranca" property="id"/>
								  							&quantidadeTotal=<bean:write name="resumoAcaoCobranca" property="somatorioQuantidadesDocumentos"/>&valorTotal=<bean:write name="resumoAcaoCobranca" property="somatorioValoresDocumentos"/>&exibePercentualRemuneracao=true', 400, 600);">
								  							<strong>EMITIDOS</strong>
								</a> 
								
								
							  </td>
							  
							  
						      <td bgcolor="#cbe5fe" align="right"><strong> Quantidade</strong></td>
						      <td bgcolor="#cbe5fe" align="right"><strong> Percentual</strong> </td>
							  <td bgcolor="#cbe5fe" align="right"><strong> Valor     </strong> </td>
							  <td bgcolor="#cbe5fe" align="right"><strong> Percentual</strong> </td>
							 </tr>
						     <tr>
							   <td bgcolor="#FFFFFF" width="17%" align="right">
									<bean:write name="resumoAcaoCobranca"
									property="somatorioQuantidadesDocumentos" formatKey="number.format" locale="currentLocale"/>
							   </td>
							   <td bgcolor="#FFFFFF" width="17%" align="right">
									100,00
							   </td>
							   <td bgcolor="#FFFFFF" width="17%" align="right">
								    <bean:write name="resumoAcaoCobranca"
									property="somatorioValoresDocumentos" formatKey="money.format" locale="currentLocale"/>
							   </td>
							   <td bgcolor="#FFFFFF" width="17%" align="right">
									 100,00
							   </td>										    
						    </tr>		
						  
						  <logic:iterate name="resumoAcaoCobranca"
							property="colecaoCobrancaAcaoSituacao" id="cobrancaAcaoSituacao">
						  <tr bgcolor="#90c7fc">
						    <td bgcolor="#90c7fc" align="center" colspan="5"><strong> Situa��o de A��o </strong></td>
						  </tr>
						  <tr>
						    <td rowspan="2" bgcolor="#cbe5fe" width="32%">
							  <a href="javascript:abrirPopup('exibirConsultarResumoAcaoCobrancaEventualPopupAction.do?idCobrancaAcao=<bean:write name="resumoAcaoCobranca" property="id"/>&idCobrancaAcaoSituacao=<bean:write name="cobrancaAcaoSituacao" property="id"/>
								  							&cobrancaAcaoSituacao=<bean:write name="cobrancaAcaoSituacao" property="descricao"/>
								  							&quantidadeTotal=<bean:write name="cobrancaAcaoSituacao" property="quantidadeDocumento"/>&valorTotal=<bean:write name="cobrancaAcaoSituacao" property="valorDoumento"/>&exibePercentualRemuneracao=true', 400, 600);">
								<strong><bean:write name="cobrancaAcaoSituacao"property="descricao"/></strong>
							  </a>
						    </td>
							<td bgcolor="#cbe5fe" align="right"><strong> Quantidade</strong></td>
							<td bgcolor="#cbe5fe" align="right"><strong> Percentual</strong> </td>
							<td bgcolor="#cbe5fe" align="right"><strong> Valor     </strong> </td>
							<td bgcolor="#cbe5fe" align="right"><strong> Percentual</strong> </td>
						  </tr>
						  <tr>
							<td bgcolor="#FFFFFF" width="17%" align="right">
									<bean:write name="cobrancaAcaoSituacao"
									property="quantidadeDocumento" formatKey="number.format" locale="currentLocale"/>
							</td>
							<td bgcolor="#FFFFFF" width="17%" align="right">
									<bean:write name="cobrancaAcaoSituacao"
									property="porcentagemQuantidade" formatKey="money.format" locale="currentLocale"/>
							</td>
							<td bgcolor="#FFFFFF" width="17%" align="right">
								    <bean:write name="cobrancaAcaoSituacao"
									property="valorDoumento" formatKey="money.format" locale="currentLocale"/>
							</td>
							<td bgcolor="#FFFFFF" width="17%" align="right">
									 <bean:write name="cobrancaAcaoSituacao"
									property="porcentagemValor" formatKey="money.format" locale="currentLocale"/>
							</td>										    
						  </tr>											
						  <logic:notEmpty name="cobrancaAcaoSituacao"
						    property="colecaoCobrancaAcaoDebito">
						  <tr>
						    <td bgcolor="#cbe5fe" align="center" colspan="5"><strong> Situa��o do D�bito </strong></td>
						  </tr>
						  <%String cor = "#FFFFFF";%>		
						  <logic:iterate name="cobrancaAcaoSituacao"
							property="colecaoCobrancaAcaoDebito" id="cobrancaAcaoDebito">  															
							<%if (cor.equalsIgnoreCase("#FFFFFF")) {
								cor = "#cbe5fe";%>
							<tr bgcolor="#FFFFFF">
							<%} else {
								cor = "#FFFFFF";%>
							<tr bgcolor="#cbe5fe">
							<%}%>
							  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							    <a href="javascript:abrirPopup('exibirConsultarResumoAcaoCobrancaEventualPopupAction.do?idCobrancaAcao=<bean:write name="resumoAcaoCobranca" property="id"/>&idCobrancaAcaoSituacao=<bean:write name="cobrancaAcaoSituacao" property="id"/>
							  						&cobrancaAcaoSituacao=<bean:write name="cobrancaAcaoSituacao" property="descricao"/>
							  						&idCobrancaAcaoDebito=<bean:write name="cobrancaAcaoDebito" property="id"/>&cobrancaAcaoDebito=<bean:write name="cobrancaAcaoDebito" property="descricaoIndicador"/>
							  						&quantidadeTotal=<bean:write name="cobrancaAcaoDebito" property="quantidadeDocumento"/>&valorTotal=<bean:write name="cobrancaAcaoDebito" property="valorDocumento"/>
							  						&idIndicador=<bean:write name="cobrancaAcaoDebito" property="indicadorAntesApos"/>', 400, 600);">
								<strong><bean:write name="cobrancaAcaoDebito"
								  property="descricaoIndicador"/></strong>
							   </a>
							  </td>
							  <td width="17%" align="right">
							    <bean:write name="cobrancaAcaoDebito"
											property="quantidadeDocumento" formatKey="number.format" locale="currentLocale"/> 		
							  </td>
							  <td width="17%" align="right">
							    <%=((CobrancaAcaoDebitoHelper)cobrancaAcaoDebito).getPercentualQuantidade(""+((CobrancaAcaoSituacaoHelper)cobrancaAcaoSituacao).getQuantidadeDocumento()) %>
							  </td>
							  <td width="17%" align="right">
								 <logic:notEqual name= "resumoAcaoCobranca" property="id" value="<%=""+CobrancaAcao.AVISO_CORTE%>">
								  <logic:notEqual name="cobrancaAcaoSituacao" property="id" value="<%=""+CobrancaAcaoSituacao.PENDENTE%>">
									<logic:notEqual name="cobrancaAcaoDebito" property="id" value="<%=""+CobrancaDebitoSituacao.PENDENTE%>">
									 <a href="javascript:abrirPopup('exibirConsultarResumoAcaoCobrancaEventualSituacaoPagoPopupAction.do?idCobrancaAcao=<bean:write name="resumoAcaoCobranca" property="id"/>&idCobrancaAcaoSituacao=<bean:write name="cobrancaAcaoSituacao" property="id"/>
								  						&cobrancaAcaoSituacao=<bean:write name="cobrancaAcaoSituacao" property="descricao"/>
								  						&idCobrancaAcaoDebito=<bean:write name="cobrancaAcaoDebito" property="id"/>&cobrancaAcaoDebito=<bean:write name="cobrancaAcaoDebito" property="descricaoIndicador"/>
								  						&quantidadeTotal=<bean:write name="cobrancaAcaoDebito" property="quantidadeDocumento"/>&valorTotal=<bean:write name="cobrancaAcaoDebito" property="valorDocumento"/>
								  						&idIndicador=<bean:write name="cobrancaAcaoDebito" property="indicadorAntesApos"/>', 300, 600);">
								  		<bean:write name="cobrancaAcaoDebito"
										  property="valorDocumento"  formatKey="money.format" locale="currentLocale"/>
									 </a> 	  
									</logic:notEqual>
									<logic:equal name="cobrancaAcaoDebito" property="id" value="<%=""+CobrancaDebitoSituacao.PENDENTE%>">
									<bean:write name="cobrancaAcaoDebito"
										  property="valorDocumento"  formatKey="money.format" locale="currentLocale"/>
									</logic:equal>
								  </logic:notEqual>	
								  <logic:equal name="cobrancaAcaoSituacao" property="id" value="<%=""+CobrancaAcaoSituacao.PENDENTE%>">
									<bean:write name="cobrancaAcaoDebito"
										  property="valorDocumento"  formatKey="money.format" locale="currentLocale"/>
								  </logic:equal>
								  	
								</logic:notEqual>
								<logic:equal name= "resumoAcaoCobranca" property="id" value="<%=""+CobrancaAcao.AVISO_CORTE%>">
								 <bean:write name="cobrancaAcaoDebito"
									  property="valorDocumento"  formatKey="money.format" locale="currentLocale"/>
								</logic:equal>
							  	  
							  </td>																											
							  <td width="17%" align="right">
								<%=((CobrancaAcaoDebitoHelper)cobrancaAcaoDebito).getPercentualValor(""+((CobrancaAcaoSituacaoHelper)cobrancaAcaoSituacao).getValorDoumento()) %> 
							  </td>									
						    </tr>
							</logic:iterate>
						   </logic:notEmpty>
						 </logic:iterate> 
						 </logic:notEmpty>						  
						</table>
					  </td>
				    </tr>
				  </table>
				 </div>
			    </td>
			   </tr>					
			  </logic:iterate>
			</table>
			<table width="100%" border="0">
			  <tr>
				<td colspan="2">
				  <div align="right"><jsp:include
					page="/jsp/util/wizard/navegacao_botoes_wizard_consulta.jsp?numeroPagina=2" /></div>
				</td>
			  </tr>	
	        </table>
	      </td>
		</tr>												
	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>