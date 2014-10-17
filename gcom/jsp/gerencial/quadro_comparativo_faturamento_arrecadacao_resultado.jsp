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

<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.gerencial.bean.QuadroComparativoFaturamentoArrecadacaoHelper"%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"  formName="ConsultarQuadroComparativoFaturamentoArrecadacaoActionForm"/>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<script language="JavaScript">
	
	function imprimir() {
		var form = document.forms[0];
		form.action='gerarRelatorioQuadroComparativoFaturamentoArrecadacaoAction.do';
	    form.submit();
	}
</script>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:habilitaDesabilitaFormulario(false);setarFoco('${requestScope.mesAnoReferencia}');">

<html:form action="/consultarQuadroComparativoFaturamentoArrecadacaoAction.do"
	name="ConsultarQuadroComparativoFaturamentoArrecadacaoActionForm"
	type="gcom.gui.gerencial.ConsultarQuadroComparativoFaturamentoArrecadacaoActionForm"
	method="post">

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<html:hidden property="opcaoTotalizacao"/>
<html:hidden property="mesAnoReferencia"/>
<html:hidden property="gerenciaRegional"/>
<html:hidden property="localidade"/>

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

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Quadro Comparativo Faturamento e Arrecada&ccedil;&atilde;o </td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			
			<table width="100%" border="0">
				<tr>
					<td colspan="4">
						<table width="100%" align="center" bgcolor="#90c7fc" border="0">
							<tr bordercolor="#79bbfd">
								<td colspan="9" align="center" bgcolor="#79bbfd"><strong>Quadro Comparativo Faturamento e Arrecada&ccedil;&atilde;o</strong></td>
							</tr>
							<tr bordercolor="#000000">
								<td width="11%" bgcolor="#90c7fc" align="center">
								<div class="style9"><font color="#000000" style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <strong></strong> </font></div>
								</td>
								
								<logic:notEmpty name="colecaoQuadroComparativoFaturamentoArrecadacao">
									<logic:iterate name="colecaoQuadroComparativoFaturamentoArrecadacao" 
									    id="registroQuadroComparativoFaturamentoArrecadacao"
										type="QuadroComparativoFaturamentoArrecadacaoHelper">
								
										<td width="11%" bgcolor="#90c7fc" align="center">
											<div class="style9">
											  <font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
												<strong>
												  <bean:write name="registroQuadroComparativoFaturamentoArrecadacao" property="descricaoCategoria" />
												</strong>
											  </font>
											</div>
										</td>
										
									</logic:iterate>
								</logic:notEmpty>
								
							</tr>
							
							<tr bgcolor="#FFFFFF">
								<td width="16%" align="left">
									<div class="style9"><font color="#000000"
										style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Faturamento</strong></font>
									</div>
								</td>
								<logic:notEmpty name="colecaoQuadroComparativoFaturamentoArrecadacao">
									<logic:iterate name="colecaoQuadroComparativoFaturamentoArrecadacao" 
									    id="registroQuadroComparativoFaturamentoArrecadacao"
										type="QuadroComparativoFaturamentoArrecadacaoHelper">
								
										<td width="10%" align="left">
											<div class="style9"><font color="#000000"
												style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
												<bean:write name="registroQuadroComparativoFaturamentoArrecadacao" property="valorContaFaturada" formatKey="money.format"/>
											    </font>
											</div>
										</td>
										
									</logic:iterate>
								</logic:notEmpty>
							</tr>
							
							<tr bgcolor="#cbe5fe">
								<td width="16%" align="left">
									<div class="style9"><font color="#000000"
										style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Inclusões</strong></font>
									</div>
								</td>
								<logic:notEmpty name="colecaoQuadroComparativoFaturamentoArrecadacao">
									<logic:iterate name="colecaoQuadroComparativoFaturamentoArrecadacao" 
									    id="registroQuadroComparativoFaturamentoArrecadacao"
										type="QuadroComparativoFaturamentoArrecadacaoHelper">
								
										<td width="10%" align="left">
											<div class="style9"><font color="#000000"
												style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
												<bean:write name="registroQuadroComparativoFaturamentoArrecadacao" property="valorContaIncluida" formatKey="money.format"/>
											    </font>
											</div>
										</td>
										
									</logic:iterate>
								</logic:notEmpty>							
							</tr>
							
							<tr bgcolor="#FFFFFF">
								<td width="16%" align="left">
									<div class="style9"><font color="#000000"
										style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Parcelamento Cobrado</strong></font>
									</div>
								</td>
								<logic:notEmpty name="colecaoQuadroComparativoFaturamentoArrecadacao">
									<logic:iterate name="colecaoQuadroComparativoFaturamentoArrecadacao" 
									    id="registroQuadroComparativoFaturamentoArrecadacao"
										type="QuadroComparativoFaturamentoArrecadacaoHelper">
								
										<td width="10%" align="left">
											<div class="style9"><font color="#000000"
												style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
												<bean:write name="registroQuadroComparativoFaturamentoArrecadacao" property="valorParcelamentoCobradoContaFaturada" formatKey="money.format"/>
											    </font>
											</div>
										</td>
										
									</logic:iterate>
								</logic:notEmpty>							
							</tr>
							
							<tr bgcolor="#cbe5fe">
								<td width="16%" align="left">
									<div class="style9"><font color="#000000"
										style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Cancelamentos</strong></font>
									</div>
								</td>
								<logic:notEmpty name="colecaoQuadroComparativoFaturamentoArrecadacao">
									<logic:iterate name="colecaoQuadroComparativoFaturamentoArrecadacao" 
									    id="registroQuadroComparativoFaturamentoArrecadacao"
										type="QuadroComparativoFaturamentoArrecadacaoHelper">
								
										<td width="10%" align="left">
											<div class="style9"><font color="#000000"
												style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
												<bean:write name="registroQuadroComparativoFaturamentoArrecadacao" property="valorContaCancelada" formatKey="money.format"/>
											    </font>
											</div>
										</td>
										
									</logic:iterate>
								</logic:notEmpty>							
							</tr>
							
							<tr bgcolor="#FFFFFF">
								<td width="16%" align="left">
									<div class="style9"><font color="#000000"
										style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Cancel. p/Parc.</strong></font>
									</div>
								</td>
								<logic:notEmpty name="colecaoQuadroComparativoFaturamentoArrecadacao">
									<logic:iterate name="colecaoQuadroComparativoFaturamentoArrecadacao" 
									    id="registroQuadroComparativoFaturamentoArrecadacao"
										type="QuadroComparativoFaturamentoArrecadacaoHelper">
								
										<td width="10%" align="left">
											<div class="style9"><font color="#000000"
												style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
												<bean:write name="registroQuadroComparativoFaturamentoArrecadacao" property="valorContaCanceladaPorParcelamento" formatKey="money.format"/>
											    </font>
											</div>
										</td>
										
									</logic:iterate>
								</logic:notEmpty>							
							</tr>
							
							<tr bgcolor="#cbe5fe">
								<td width="16%" align="left">
									<div class="style9"><font color="#000000"
										style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Arrecadável</strong></font>
									</div>
								</td>
								<logic:notEmpty name="colecaoQuadroComparativoFaturamentoArrecadacao">
									<logic:iterate name="colecaoQuadroComparativoFaturamentoArrecadacao" 
									    id="registroQuadroComparativoFaturamentoArrecadacao"
										type="QuadroComparativoFaturamentoArrecadacaoHelper">
								
										<td width="10%" align="left">
											<div class="style9"><font color="#000000"
												style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
												<bean:write name="registroQuadroComparativoFaturamentoArrecadacao" property="valorArrecadavel" formatKey="money.format"/>
											    </font>
											</div>
										</td>
										
									</logic:iterate>
								</logic:notEmpty>							
							</tr>
							
							<tr bgcolor="#FFFFFF">
								<td width="16%" align="left">
									<div class="style9"><font color="#000000"
										style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Recebimento</strong></font>
									</div>
								</td>
								<logic:notEmpty name="colecaoQuadroComparativoFaturamentoArrecadacao">
									<logic:iterate name="colecaoQuadroComparativoFaturamentoArrecadacao" 
									    id="registroQuadroComparativoFaturamentoArrecadacao"
										type="QuadroComparativoFaturamentoArrecadacaoHelper">
								
										<td width="10%" align="left">
											<div class="style9"><font color="#000000"
												style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
												<bean:write name="registroQuadroComparativoFaturamentoArrecadacao" property="valorContaPaga" formatKey="money.format"/>
											    </font>
											</div>
										</td>
										
									</logic:iterate>
								</logic:notEmpty>							
							</tr>
							
							<tr bgcolor="#cbe5fe">
								<td width="16%" align="left">
									<div class="style9"><font color="#000000"
										style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Pendência</strong></font>
									</div>
								</td>
								<logic:notEmpty name="colecaoQuadroComparativoFaturamentoArrecadacao">
									<logic:iterate name="colecaoQuadroComparativoFaturamentoArrecadacao" 
									    id="registroQuadroComparativoFaturamentoArrecadacao"
										type="QuadroComparativoFaturamentoArrecadacaoHelper">
								
										<td width="10%" align="left">
											<div class="style9"><font color="#000000"
												style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
												<bean:write name="registroQuadroComparativoFaturamentoArrecadacao" property="valorPendencia" formatKey="money.format"/>
											    </font>
											</div>
										</td>
										
									</logic:iterate>
								</logic:notEmpty>							
							</tr>
							
							<tr bgcolor="#FFFFFF">
								<td width="16%" align="left">
									<div class="style9"><font color="#000000"
										style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Perc. Recebimentos</strong></font>
									</div>
								</td>
								<logic:notEmpty name="colecaoQuadroComparativoFaturamentoArrecadacao">
									<logic:iterate name="colecaoQuadroComparativoFaturamentoArrecadacao" 
									    id="registroQuadroComparativoFaturamentoArrecadacao"
										type="QuadroComparativoFaturamentoArrecadacaoHelper">
								
										<td width="10%" align="left">
											<div class="style9"><font color="#000000"
												style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
												<bean:write name="registroQuadroComparativoFaturamentoArrecadacao" property="percentualRecebimentos" formatKey="money.format"/>
											    </font>
											</div>
										</td>
										
									</logic:iterate>
								</logic:notEmpty>							
							</tr>
							
							<tr bgcolor="#cbe5fe">
								<td width="16%" align="left">
									<div class="style9"><font color="#000000"
										style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Perc. Receb. em Dia</strong></font>
									</div>
								</td>
								<logic:notEmpty name="colecaoQuadroComparativoFaturamentoArrecadacao">
									<logic:iterate name="colecaoQuadroComparativoFaturamentoArrecadacao" 
									    id="registroQuadroComparativoFaturamentoArrecadacao"
										type="QuadroComparativoFaturamentoArrecadacaoHelper">
								
										<td width="10%" align="left">
											<div class="style9"><font color="#000000"
												style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
												<bean:write name="registroQuadroComparativoFaturamentoArrecadacao" property="percentualRecebimentoEmDia" formatKey="money.format"/>
											    </font>
											</div>
										</td>
										
									</logic:iterate>
								</logic:notEmpty>							
							</tr>
							
						</table>
						</td>
					</tr>
					
			           <!-- Ações -->
			           
			        <tr>
			            <td colspan="3" align="right" valign="top">
							<div align="right"><a href="javascript:imprimir();">
						  	  <img border="0" src="<bean:message key="caminho.imagens"/>print.gif" title="Imprimir Quadro Comparativo" /></a>
						  	</div>
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
