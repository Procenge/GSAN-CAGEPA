<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ page import="java.util.Collection,java.util.Iterator,java.util.Map" %>
<%@ page import="java.math.BigDecimal,gcom.arrecadacao.ArrecadacaoDadosDiarios,gcom.util.Util" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
<html:html>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="FiltrarDadosDiariosArrecadacaoActionForm" dynamicJavascript="false" />
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">
<!-- Begin
function avancar(){
	document.forms[0].proximoPasso.value = 'avancar';
	enviar();
}

-->
</script>
</head>
<body leftmargin="5" topmargin="5">
<html:form action="/consultarDadosDiariosArrecadacaoWizardAction" 
	name="FiltrarDadosDiariosArrecadacaoActionForm"
	type="gcom.gui.arrecadacao.FiltrarDadosDiariosArrecadacaoActionForm"
	method="post">
	<%String nuPagina = (String) session.getAttribute("nuPaginaArrecadador"); %>
	<jsp:include
		page='<%="/jsp/util/wizard/navegacao_abas_wizard_filtro.jsp?numeroPagina=" + nuPagina%>' />	

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
	
	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<input type="hidden" name="numeroPagina" value="3" />
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
			<td valign="top" class="centercoltext">
				<table height="100%">
					<tr><td></td></tr>
				</table>
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<td width="11">
							<img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" />
						</td>
						<td class="parabg">Consultar Dados Diários</td>
						<td width="11">
							<img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
						</td>
					</tr>
				</table>
				<!-- Início do Corpo - Fernanda Paiva -->
        	    <!--header da tabela interna -->
				<br>
                <table width="80%" align="center" bgcolor="#99CCFF" dwcopytype="CopyTableRow">
                	<tr bordercolor="#FFFFFF" bgcolor="#90c7fc""> 
				    	<td  width="15%"> <div align="center" class="style9"><strong>M&ecirc;s/Ano</strong></div></td>
				        <td width="21%"> <div align="center" class="style9"><strong>Valor</strong></div></td>
				        <td width="32%"><div align="center"><strong>Evolução M&ecirc;s Anterior</strong></div></td>
						<td width="32%"><div align="center"><strong>Percentual Per&iacute;odo</strong></div></td>
			        </tr>
				    <% 
	                
	                Map<Integer,Map<BigDecimal,Collection>> map = 
	                	(Map<Integer,Map<BigDecimal,Collection>>)
			                	session.getAttribute("dadosArrecadacaoArrecadador");
	                
	                BigDecimal valorTotal = (BigDecimal)session.getAttribute("valordadosArrecadacaoArrecadador");
	                
	                BigDecimal percentual = new BigDecimal("0.00");
	                BigDecimal valorPercentualMesAnteriorParaCalculo = new BigDecimal("0.00");
	                BigDecimal percentualEvolucao = new BigDecimal("0.00");
	                
	                int contador = 0;
	                
					Iterator iteratorColecaoDadosDiarios = map.keySet().iterator();
					String cor = "#cbe5fe";
					while (iteratorColecaoDadosDiarios.hasNext()) {
						Integer chave  = (Integer) iteratorColecaoDadosDiarios
								.next();

						Map<BigDecimal,Collection> mapDadosDiarios  = map.get(chave);
						
						Iterator iteratorValorAcumulado = mapDadosDiarios.keySet().iterator();

						while (iteratorValorAcumulado.hasNext()) {
							
							BigDecimal valor  = (BigDecimal) iteratorValorAcumulado
							.next();
							
							Collection dadosDiarios  = mapDadosDiarios.get(valor);
							
							Iterator iteratorColecaoArrecadacaoDadosDiarios = dadosDiarios.iterator();
							boolean primeiraVez = true;
							
							while(iteratorColecaoArrecadacaoDadosDiarios.hasNext()){
								contador++;
								
								ArrecadacaoDadosDiarios arrecadacaoDadosDiarios = (ArrecadacaoDadosDiarios) iteratorColecaoArrecadacaoDadosDiarios.next();
								
								if (arrecadacaoDadosDiarios.getArrecadador() != null )
								{
									if(primeiraVez)
									{
										BigDecimal valorMultiplicacaoPercentual = valor.multiply(new BigDecimal("100.00"));
										
										percentual = valorMultiplicacaoPercentual.divide(
												valorTotal,2,BigDecimal.ROUND_HALF_UP);
										
										if(contador != 1)
						                {
											BigDecimal valorSubtracao = valor.subtract(valorPercentualMesAnteriorParaCalculo);
											
											BigDecimal valorMultiplicacaoParaCalculoEvolucao = valorSubtracao.multiply(new BigDecimal("100.00"));
											
											percentualEvolucao = valorMultiplicacaoParaCalculoEvolucao.divide(
												valorPercentualMesAnteriorParaCalculo,2,BigDecimal.ROUND_HALF_UP);
						                }	
										if (cor.equalsIgnoreCase("#cbe5fe")) {
												cor = "#FFFFFF";%>
										<tr bgcolor="#FFFFFF">
										<%} else {
											cor = "#cbe5fe";%>
										<tr bgcolor="#cbe5fe">
										<%}%>
						                    <td height="17">
						                    	<a href="javascript:abrirPopup('exibirConsultarDadosDiariosAgenteArrecadadorAction.do?referencia=<%=arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao()%>', 475, 600);">
						                    		<%=Util.formatarAnoMesParaMesAno(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao())%>
						                    	</a>
						                    </td>
						                    <td>
						                    	<a href="javascript:abrirPopup('exibirConsultarDadosDiariosValoresDiariosAction.do?referencia=<%=arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao()%>', 475, 600);">
						                    		<div align="right">
						                    			<%= Util.formatarMoedaReal(valor) %>
							                    	</div>
							                    </a>
						                    </td>
						                 <% 
						                 if(contador == 1)
						                 {
										 %>
						                 <td><div align="right"></div></td>
						                 <% 
						                 }else{
						                 %>   
						                   <td><div align="right"><%= Util.formatarMoedaReal(percentualEvolucao)%> %</div></td>
						                 <% 
						                 }
						                 %>   
						                    <td><div align="right"><%= Util.formatarMoedaReal(percentual)%> %</div></td>
						                </tr>
				            		    <%
				            		    primeiraVez = false;
				            		    valorPercentualMesAnteriorParaCalculo = valor;
			            		    }
								}
							}
						}
					}
	              %>
	            </table>
		        <p>&nbsp;</p>
	            <table width="80%" border="0" align="center">
                	<tr>
                        <td width="9%" height="17">&nbsp;</td>
	                    <td width="73%"><div align="right"><strong>Total do Per&iacute;odo:</strong></div>
	                    </td>
        		        <td width="18%" bgcolor="#90c7fc">
		                    <div align="right">
   		              			<strong><%= Util.formatarMoedaReal(valorTotal) %></strong>
		                    </div>
                  		</td>
                	</tr>
              	</table>
              	<p>&nbsp;</p>
				<table width="100%" border="0" align="center">
					<tr>
						<td colspan="3">
							<div align="right"><jsp:include
								page='<%="/jsp/util/wizard/navegacao_botoes_wizard_consulta.jsp?numeroPagina=" + nuPagina%>' /></div>
						</td>
					</tr>
	            </table>
				<!-- Fim do Corpo - Fernanda Paiva  15/05/2006  -->
				<p>&nbsp;</p>
			</td>
		</tr>
	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>