<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@page import="gcom.cobranca.bean.CobrancaDocumentoHelper"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
<html:html>
<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>

</head>

		<body leftmargin="5" topmargin="5">

<form>

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

			<td valign="top" class="centercoltext">
		
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
				<td class="parabg">Consultar Atendimentos Realizados</td>
				<td width="11"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
			</tr>
		</table>



		<table width="590" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td colspan="4" height="23">
				<font color="#000000" style="font-size: 10px"
					face="Verdana, Arial, Helvetica, sans-serif">
				<strong>Atendimentos encontrados:</strong>
				</font>
				</td>
			</tr>

			<tr>
				<td bgcolor="#000000" height="2"></td>
			</tr>

			<tr>
				<td>
				<table width="590" bgcolor="#99CCFF">
					<tr bgcolor="#99CCFF">
            				<td width="32%"><div align="center"><strong>Procedimento de Atedimento</strong></div></td>
            				<td width="32%"><div align="center"><strong>Funcionalidade</strong></div></td>
            				<td width="18%"><div align="center"><strong>Usuário</strong></div></td>
            				<td width="18%"><div align="center"><strong>Data Atendimento</strong></div></td>
					</tr>
					
					
				</table>
				</td>
			</tr>
			<tr>
				<td>

				<table width="590" bgcolor="#99CCFF">

					<%--Esquema de paginação--%>
					<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
							export="currentPageNumber=pageNumber;pageOffset"
							maxPageItems="10" items="${sessionScope.totalRegistros}">
							<pg:param name="pg" />
							<pg:param name="q" />
						<logic:present name="colecaoAtendimento">
							<%int cont = 1;%>
							<logic:iterate id="atendimento" name="colecaoAtendimento" type="gcom.cadastro.atendimento.Atendimento" scope="session">
								<pg:item>
								<%	cont = cont + 1;
									if (cont % 2 == 0) {	%>
										<tr bgcolor="#FFFFFF">
								<%	} else {	%>
										<tr bgcolor="#cbe5fe">
								<%	}	%>

							        <td bordercolor="#90c7fc" width="32%">
							        	<div align="center">
					                    	<a href="javascript:abrirPopup('exibirConsultarDadosAtendimentoPopupAction.do?idAtendimento='+${atendimento.id}, 400, 800);">
						                    	<bean:write name="atendimento" property="atendimentoProcedimento.descricao"/>
							                </a>											        	
							            </div>
							        </td>
							        <td bordercolor="#90c7fc" width="32%">
							        	<div align="center">
							            	<bean:write name="atendimento" property="atendimentoProcedimento.funcionalidade.descricao"/>
							           	</div>
							       	</td>
							      	<td bordercolor="#90c7fc" width="18%">
							         	<div align="center">
							         		<bean:write name="atendimento" property="usuario.login"/>
							          	</div>											      	
							       	</td>
							       	<td bordercolor="#90c7fc" width="18%">
							         	<div align="center">								                        
					                      	<bean:write name="atendimento" property="dataInicioAtendimento" format="dd/MM/yyyy HH:mm"/>
							         	</div>											       	
							     	</td>
										
									</tr>
								</pg:item>
							</logic:iterate>
						</logic:present>
				</table>

				</td>
			</tr>
			<tr bordercolor="#90c7fc">
				<td>
				<table width="100%">
					<tr>
						<td><input name="Button" type="button" class="bottonRightCol"
							value="Voltar Filtro"
							onclick="window.location.href='/gsan/exibirFiltrarAtendimentosRealizadosAction.do'"
							align="left" style="width: 80px;"></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>

		<table width="100%" border="0">
			<tr><td>
				<div align="center"><strong><%@ include
						file="/jsp/util/indice_pager_novo.jsp"%></strong></div>
					</td>
			</tr>
		</table>

		<br>
		</pg:pager></td>
	</tr>
</table>
	<logic:notPresent name="ehPopup">
		<%@ include file="/jsp/util/rodape.jsp"%>
	</logic:notPresent>		
</form>
</body>
</html:html>
